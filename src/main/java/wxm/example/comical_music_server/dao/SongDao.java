package wxm.example.comical_music_server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wxm.example.comical_music_server.entity.music.Song;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
@Repository
public interface SongDao extends JpaRepository<Song, Long> {

    Song findByName(String name);

    Song findByNameIsLike(String name);
}
