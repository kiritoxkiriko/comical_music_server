package wxm.example.comical_music_server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wxm.example.comical_music_server.entity.music.Album;
import wxm.example.comical_music_server.entity.music.Singer;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
@Repository
public interface AlbumDao extends JpaRepository<Album,Long> {

    Album findByName(String name);

    Album findByNameIsLike(String name);

    Album findBySinger(Singer singer);

    Album findBySingerId(Singer singerId);


}
