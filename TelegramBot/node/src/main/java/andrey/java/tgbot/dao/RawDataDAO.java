package andrey.java.tgbot.dao;

import andrey.java.tgbot.entity.RawData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawDataDAO extends JpaRepository<RawData, Long> {
}
