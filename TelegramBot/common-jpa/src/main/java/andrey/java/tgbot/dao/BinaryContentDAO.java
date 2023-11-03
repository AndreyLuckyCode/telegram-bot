package andrey.java.tgbot.dao;

import andrey.java.tgbot.entity.BinaryContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinaryContentDAO extends JpaRepository<BinaryContent, Long> {
}
