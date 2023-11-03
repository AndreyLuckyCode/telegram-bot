package andrey.java.tgbot.service;

import andrey.java.tgbot.dto.MailParams;

public interface MailSenderService {
    void send(MailParams mailParams);
}
