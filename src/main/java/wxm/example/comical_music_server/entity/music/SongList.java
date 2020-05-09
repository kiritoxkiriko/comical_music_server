package wxm.example.comical_music_server.entity.music;

import wxm.example.comical_music_server.entity.bbs.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @author Alex Wang
 * @date 2020/05/05
 */
@Entity
public class SongList implements Serializable, Shareable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String introduction;

    @ManyToOne
    private User creator;

    @Column
    private Date date;

    @ManyToMany
    private Set<Song> songs;

    @Column
    private boolean open= true;

    public SongList() {
        this.date=new Date(System.currentTimeMillis());
    }

    public SongList(String introduction, User creator, @NotEmpty Set<Song> songs) {
        this.introduction = introduction;
        this.creator = creator;
        this.date=new Date(System.currentTimeMillis());
        this.songs = songs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongList songList = (SongList) o;
        return id == songList.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
