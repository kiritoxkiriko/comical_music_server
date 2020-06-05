package wxm.example.comical_music_server.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.entity.music.SongList;

import java.util.List;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
@Repository
public interface SongListDao extends JpaRepository<SongList,Long> {

    List<SongList> findAllByExistAndOpen(boolean exist,boolean open);

    Page<SongList> findAllByExistAndOpen(boolean exist,boolean open,Pageable page);

    List<SongList> findAllByNameLikeAndExistAndOpen(String name,boolean exist,boolean open);

    Page<SongList> findAllByNameLikeAndExistAndOpen(String name,boolean exist,boolean open, Pageable pageable);

    SongList findByName(String name);

    List<SongList> findAllByCreator(User user);

    List<SongList> findAllByCreatorId(long userId);

//    List<SongList> findAllByCreatorAndDeleteEqualsFalse(User user);
//
//    Page<SongList> findAllByCreatorAndDeleteEqualsFalse(User user, Pageable pageable);
//
//    Page<SongList> findAllByCreatorIdAndDeleteEqualsFalse(long userId, Pageable pageable);



}
