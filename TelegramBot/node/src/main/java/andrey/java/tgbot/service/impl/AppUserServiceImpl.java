package andrey.java.tgbot.service.impl;

import andrey.java.tgbot.dao.AppUserDAO;
import andrey.java.tgbot.dto.MailParams;
import andrey.java.tgbot.entity.AppUser;
import andrey.java.tgbot.entity.enums.UserState;
import andrey.java.tgbot.service.AppUserService;
import andrey.java.tgbot.utils.CryptoTool;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static andrey.java.tgbot.entity.enums.UserState.BASIC_STATE;
import static andrey.java.tgbot.entity.enums.UserState.WAIT_FOR_EMAIL_STATE;

@Log4j
@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserDAO appUserDAO;
    private final CryptoTool cryptoTool;
    @Value("${service.mail.uri}")
    private String mailServiceUri;

    public AppUserServiceImpl(AppUserDAO appUserDAO, CryptoTool cryptoTool) {
        this.appUserDAO = appUserDAO;
        this.cryptoTool = cryptoTool;
    }

    @Override
    public String registerUser(AppUser appUser) {
        if (appUser.getIsActive()) {
            return "Вы уже зарегистрированы!";
        } else if (appUser.getEmail() != null) {
            return "Mei Mei уже отправляла вам письмо на почту. "
                    + "Перейдите по ссылке в письме для подтверждения регистрации.";
        }
        appUser.setState(WAIT_FOR_EMAIL_STATE);
        appUserDAO.save(appUser);
        return "Mei Mei нужен ваш email для регистрации:";
    }

    @Override
    public String setEmail(AppUser appUser, String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException e) {
            return "Mei Mei не нравится ваш email. Введите корректный email. Для отмены команды введите /cancel";
        }
        var optional = appUserDAO.findByEmail(email);
        if (optional.isEmpty()) {
            appUser.setEmail(email);
            appUser.setState(BASIC_STATE);
            appUser = appUserDAO.save(appUser);

            var cryptoUserId = cryptoTool.hashOf(appUser.getId());
            var response = sendRequestToMailService(cryptoUserId, email);
            if (response.getStatusCode() != HttpStatus.OK) {
                var msg = String.format("Отправка эл. письма на почту %s не удалась.", email);
                log.error(msg);
                appUser.setEmail(null);
                appUserDAO.save(appUser);
                return msg;
            }
            return "Mei Mei отправила письмо вам на почту."
                    + "Перейдите по ссылке в письме для подтверждения регистрации.";
        } else {
            return "Mei Mei уже видела этот email. Введите корректный email."
                    + " Для отмены команды введите /cancel";
        }
    }

    private ResponseEntity<String> sendRequestToMailService(String cryptoUserId, String email) {
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var mailParams = MailParams.builder()
                .id(cryptoUserId)
                .emailTo(email)
                .build();
        var request = new HttpEntity<>(mailParams, headers);
        return restTemplate.exchange(mailServiceUri,
                HttpMethod.POST,
                request,
                String.class);
    }
}
