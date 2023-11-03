package andrey.java.tgbot.dao;

import andrey.java.tgbot.entity.AppDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppDocumentDAO extends JpaRepository<AppDocument, Long> {
}
