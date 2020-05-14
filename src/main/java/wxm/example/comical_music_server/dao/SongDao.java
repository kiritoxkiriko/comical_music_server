package wxm.example.comical_music_server.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wxm.example.comical_music_server.entity.music.Song;

import java.util.List;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
@Repository
public interface SongDao extends JpaRepository<Song, Long> {

    Song findByName(String name);

    List<Song> findAllByNameIsLike(String name);

    Page<Song> findAllByNameIsLike(String name, Pageable pageable);

    Page<Song> findAllByNameIsLikeAndDeleteEqualsFalse(String name, Pageable pageable);
}
