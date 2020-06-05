package wxm.example.comical_music_server.entity.bbs;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import wxm.example.comical_music_server.entity.music.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @author Alex Wang
 * @date 2020/05/04
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreatedDate
    private Date time;

    @NotNull
    @Column
    private String content;

    @ManyToMany
    private Set<Song> sharedSongs;

    @ManyToMany
    public Set<Image> sharedImages;

    @ManyToMany
    private Set<SongList> sharedSongLists;


    @NotNull
    @CreatedBy
    @ManyToOne
    private User poster;

    @NotNull
    @ManyToOne
    private Board postedBoard;

    @Column
    private long likeCount=0;

    @Column
    private boolean exist=true;

    /**动态类型
     * 1. 文字
     * 2. 音乐
     * 3. 歌单
     */
    @Column
    private int type=1;

    @Transient
    private Integer replyCount;


    public Post() {
    }

    public Post(@NotNull String content, Set<Song> sharedSongs, Set<Image> sharedImages, Set<SongList> sharedSongLists, @NotNull User poster, @NotNull Board postedBoard) {
        this.content = content;
        this.sharedSongs = sharedSongs;
        this.sharedImages = sharedImages;
        this.sharedSongLists = sharedSongLists;
        this.poster = poster;
        this.postedBoard = postedBoard;
    }

    public Post(@NotNull String content, Set<Song> sharedSongs, Set<Image> sharedImages, Set<SongList> sharedSongLists, @NotNull Board postedBoard) {
        this.content = content;
        this.sharedSongs = sharedSongs;
        this.sharedImages = sharedImages;
        this.sharedSongLists = sharedSongLists;
        this.postedBoard = postedBoard;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }

    public Board getPostedBoard() {
        return postedBoard;
    }

    public void setPostedBoard(Board postedBoard) {
        this.postedBoard = postedBoard;
    }

    public Set<Song> getSharedSongs() {
        return sharedSongs;
    }

    public void setSharedSongs(Set<Song> sharedSongs) {
        this.sharedSongs = sharedSongs;
    }

    public Set<Image> getSharedImages() {
        return sharedImages;
    }

    public void setSharedImages(Set<Image> sharedImages) {
        this.sharedImages = sharedImages;
    }

    public Set<SongList> getSharedSongLists() {
        return sharedSongLists;
    }

    public void setSharedSongLists(Set<SongList> sharedSongLists) {
        this.sharedSongLists = sharedSongLists;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
