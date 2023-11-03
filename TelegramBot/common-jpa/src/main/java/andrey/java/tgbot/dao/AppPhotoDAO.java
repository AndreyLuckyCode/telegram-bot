package andrey.java.tgbot.dao;

import andrey.java.tgbot.entity.AppPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppPhotoDAO extends JpaRepository<AppPhoto, Long> {
}
