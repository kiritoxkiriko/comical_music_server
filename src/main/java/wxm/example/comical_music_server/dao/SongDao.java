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

    Song findByRealName(String name);

    List<Song> findAllByNameContainingAndExist(String name,boolean exist);

    Page<Song> findAllByNameContainingAndExist(String name,boolean exist,Pageable pageable);
    
    Page<Song> findAllByNameLike(String name,Pageable pageable);

    Page<Song> findAllByExist(boolean exist,Pageable pageable);

    List<Song> findAllByExist(boolean exist);



//    Page<Song> findAllByNameIsLikeAndDeleteEqualsFalse(String name, Pageable pageable);
}
