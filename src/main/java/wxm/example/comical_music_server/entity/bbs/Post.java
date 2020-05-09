package wxm.example.comical_music_server.entity.bbs;

import wxm.example.comical_music_server.entity.music.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @author Alex Wang
 * @date 2020/05/04
 */

@Entity
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column
    private Date date;

    @NotNull
    @Column
    private String content;

    @ManyToMany
    private Set<Song> sharedSongs;

    @ManyToMany
    private Set<Image> sharedImages;

    @ManyToMany
    private Set<SongList> sharedSongLists;


    @NotNull
    @ManyToOne
    private User poster;

    @NotNull
    @ManyToOne
    private Board postedBoard;

    private long likeCount=0;


    public Post() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
