package wxm.example.comical_music_server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wxm.example.comical_music_server.entity.music.Language;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
@Repository
public interface LanguageDao extends JpaRepository<Language,Long> {
}
