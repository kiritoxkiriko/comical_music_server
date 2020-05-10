package wxm.example.comical_music_server.entity.bbs;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author Alex Wang
 * @date 2020/05/05
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Reply implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    private Post post;

    @NotNull
    @ManyToOne
    private User replier;

    @ManyToOne
    private Reply replyTo;

    @CreatedDate
    @Column
    private Date date;

    @Column
    private long likeCount=0;


    public Reply() {
    }

    public Reply(@NotNull Post post, @NotNull User replier, Reply replyTo) {
        this.post = post;
        this.replier = replier;
        this.replyTo = replyTo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getReplier() {
        return replier;
    }

    public void setReplier(User replier) {
        this.replier = replier;
    }

    public Reply getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Reply replyTo) {
        this.replyTo = replyTo;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reply reply = (Reply) o;
        return id == reply.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
