package wxm.example.comical_music_server.entity.bbs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    @JsonIgnore
    @ManyToOne
    private Post post;

    @NotEmpty
    private String content;

    @CreatedBy
    @ManyToOne
    private User replier;

    @ManyToOne
    private Reply replyTo;

    @CreatedDate
    @Column
    private Date time;

    @Column
    private long likeCount=0;

    @Column
    private boolean exist=true;


    public Reply() {
    }

    public Reply(@NotNull Post post, @NotEmpty String content, Reply replyTo) {
        this.post = post;
        this.content = content;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date date) {
        this.time = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
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
