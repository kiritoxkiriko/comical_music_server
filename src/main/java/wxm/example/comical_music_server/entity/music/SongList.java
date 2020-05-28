package wxm.example.comical_music_server.entity.music;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import wxm.example.comical_music_server.entity.bbs.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @author Alex Wang
 * @date 2020/05/05
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SongList implements Serializable, Shareable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotEmpty
    private String name;

    @ManyToMany
    private Set<Tag> tags;

    @Column
    private String introduction;

    @ManyToOne
    @CreatedBy
    private User creator;

    @CreationTimestamp
    private Date time;

    @ManyToMany
    private Set<Song> songs;

    @ManyToOne
    private Image image;

    @Column
    private boolean open= true;

    @JsonIgnore
    @Column
    private boolean exist=true;

    public SongList() {
    }


    public SongList(@NotEmpty String name, Set<Tag> tags, @NotEmpty String introduction, Set<Song> songs, Image image) {
        this.name = name;
        this.tags = tags;
        this.introduction = introduction;
        this.songs = songs;
        this.image = image;
    }

    public SongList(@NotEmpty String name, Set<Tag> tags, @NotEmpty String introduction, Set<Song> songs, User user, Image image) {
        this.name = name;
        this.tags = tags;
        this.introduction = introduction;
        this.songs = songs;
        this.image = image;
        this.creator=user;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
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
        SongList songList = (SongList) o;
        return id == songList.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
