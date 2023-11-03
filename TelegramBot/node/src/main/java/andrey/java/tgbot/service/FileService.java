package andrey.java.tgbot.service;

import andrey.java.tgbot.entity.AppDocument;
import andrey.java.tgbot.entity.AppPhoto;
import andrey.java.tgbot.service.enums.LinkType;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface FileService {
    AppDocument processDoc(Message telegramMessage);
    AppPhoto processPhoto(Message telegramMessage);
    String generateLink(Long docId, LinkType linkType);
}
