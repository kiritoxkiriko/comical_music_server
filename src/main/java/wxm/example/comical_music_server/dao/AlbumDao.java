package wxm.example.comical_music_server.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wxm.example.comical_music_server.entity.music.Album;
import wxm.example.comical_music_server.entity.music.Singer;

import java.util.List;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
@Repository
public interface AlbumDao extends JpaRepository<Album,Long> {



    List<Album> findAllByName(String name);

    List<Album> findAllByNameIsLike(String name);

    Page<Album> findAllByNameIsLike(String name, Pageable pageable);

    Album findBySinger(Singer singer);

    Album findBySingerId(Singer singerId);


}
