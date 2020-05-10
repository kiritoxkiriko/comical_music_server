package wxm.example.comical_music_server.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wxm.example.comical_music_server.entity.music.Song;
import wxm.example.comical_music_server.entity.music.SongComment;

import java.util.List;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
@Repository
public interface SongCommentDao extends JpaRepository<SongComment,Long> {

    List<SongComment> findAllBySong(Song song);

    List<SongComment> findAllBySongId(Long songId);

    Page<SongComment> findAllBySong(Song song, Pageable pageable);

    Page<SongComment> findAllBySong(String songId, Pageable pageable);

}
