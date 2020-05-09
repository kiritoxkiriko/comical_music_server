package wxm.example.comical_music_server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.entity.music.SongList;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
@Repository
public interface SongListDao extends JpaRepository<SongList,Long> {

    SongList findByNameIsLike(String name);

    SongList findByName(String name);

    SongList findByCreator(User user);

    SongList findByCreator_Id(long userId);

}
