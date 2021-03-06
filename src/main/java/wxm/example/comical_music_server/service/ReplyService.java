package wxm.example.comical_music_server.service;

import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wxm.example.comical_music_server.dao.PostDao;
import wxm.example.comical_music_server.dao.ReplyDao;
import wxm.example.comical_music_server.entity.bbs.Post;
import wxm.example.comical_music_server.entity.bbs.Reply;

import java.util.List;

/**
 * @author Alex Wang
 * @date 2020/05/13
 */
@Service
public class ReplyService {

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private PostDao postDao;

    public Page<Reply> getByPostId(long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "time");
        return replyDao.findAllByPostId(postId, pageable);
    }

    public Reply getReply(long id) {
        return replyDao.findById(id).orElse(null);
    }

    public Reply addReply(long postId, String content, Long replyId) {
        if (StringUtil.isNullOrEmpty(content)) {
            return null;
        }
        if (!postDao.existsById(postId)) {
            return null;
        }
        Post post = new Post();
        post.setId(postId);
        Reply replyTo = null;
        if (replyId != null) {
            replyTo = replyDao.findById(replyId).orElse(null);
        }
        Reply reply = new Reply(post, content, replyTo);
        return replyDao.saveAndFlush(reply);
    }

    public int getCount(long postId) {
        return replyDao.countByPostId(postId);
    }

    public List<Post> addCountToPosts(List<Post> posts) {
        for (Post p :
                posts) {
            int count = getCount(p.getId());
            p.setReplyCount(count);
        }
        return posts;
    }

    public Post addCountToPost(Post p) {

        int count = getCount(p.getId());
        p.setReplyCount(count);

        return p;
    }

    public boolean delete(long replyId) {
        try {
            replyDao.deleteById(replyId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
