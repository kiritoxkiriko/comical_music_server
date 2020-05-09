package wxm.example.comical_music_server.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wxm.example.comical_music_server.entity.bbs.Board;
import wxm.example.comical_music_server.entity.bbs.Post;

import java.util.List;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
@Repository
public interface PostDao extends JpaRepository<Post,Long> {

    List<Post> findAllByPostedBoard(Board board);

    List<Post> findAllByPostedBoardId(long boardId);

    Page<Post> findAllByPostedBoardOrderByDateDesc(Board board, Pageable pagebale);
}
