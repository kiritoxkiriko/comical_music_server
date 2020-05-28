package wxm.example.comical_music_server.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wxm.example.comical_music_server.entity.bbs.Post;
import wxm.example.comical_music_server.entity.bbs.Reply;

import java.util.List;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
@Repository
public interface ReplyDao extends JpaRepository<Reply, Long> {

    List<Reply> findAllByPost(Post post);

    List<Reply> findAllByPostId(long postId);

    Page<Reply> findAllByPost(Post post, Pageable pageable);

    Page<Reply> findAllByPostId(long postId, Pageable pageable);

    int countByPostId(long postId);
}
