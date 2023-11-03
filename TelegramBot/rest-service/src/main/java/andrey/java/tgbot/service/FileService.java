package andrey.java.tgbot.service;

import andrey.java.tgbot.entity.AppDocument;
import andrey.java.tgbot.entity.AppPhoto;
import andrey.java.tgbot.entity.BinaryContent;
import org.springframework.core.io.FileSystemResource;

public interface FileService {
    AppDocument getDocument(String id);
    AppPhoto getPhoto(String id);
    FileSystemResource getFileSystemResource(BinaryContent binaryContent);
}
