package wxm.example.comical_music_server.entity.music;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import wxm.example.comical_music_server.entity.bbs.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * @author Alex Wang
 * @date 2020/05/05
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
public class SongComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String content;

    @CreatedBy
    @ManyToOne
    private User replier;

    @ManyToOne
    private SongComment replyTo;

    @ManyToOne
    private Song song;

    @Column
    @CreatedDate
    private Date date;

    @Column
    private long likeCount=0;

    public SongComment() {
    }

    public SongComment(@NotNull String content, User replier, SongComment replyTo, Song song) {
        this.content = content;
        this.replier = replier;
        this.replyTo = replyTo;
        this.song = song;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getReplier() {
        return replier;
    }

    public void setReplier(User replier) {
        this.replier = replier;
    }

    public SongComment getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(SongComment replyTo) {
        this.replyTo = replyTo;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
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
        SongComment that = (SongComment) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
