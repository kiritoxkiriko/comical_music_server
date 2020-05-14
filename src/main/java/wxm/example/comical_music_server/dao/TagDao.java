package wxm.example.comical_music_server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wxm.example.comical_music_server.entity.music.Tag;

/**
 * @author Alex Wang
 * @date 2020/05/13
 */

@Repository
public interface TagDao extends JpaRepository<Tag, Long> {

    Tag findByName(String name);
}
