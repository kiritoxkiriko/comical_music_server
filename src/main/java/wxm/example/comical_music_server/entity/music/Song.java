package wxm.example.comical_music_server.entity.music;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import wxm.example.comical_music_server.entity.bbs.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class Song implements Serializable, Shareable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column
    private String name;

    @ManyToOne
    private Language language;

    @ManyToOne
    private Genre genre;

    @NotNull
    @ManyToMany
    private Set<Singer> singers;

    @NotNull
    @ManyToOne
    private Album album;

    @NotNull
    @Column(unique = true)
    private String realName;

    @Column(unique = true)
    private String lrcName;

    @CreatedBy
    @ManyToOne
    private User uploader;

    @CreatedDate
    @Column
    private Date uploadDate;

    public Song(){
    }

    public Song(@NotNull String name, Language language, Genre genre, @NotNull Set<Singer> singers, @NotNull Album album, @NotNull String realName, User uploader) {
        this.name = name;
        this.language = language;
        this.genre = genre;
        this.singers = singers;
        this.album = album;
        this.realName = realName;
        this.uploader = uploader;
    }

    public Song(@NotEmpty String name, Language language, Genre genre, @NotNull Set<Singer> singers, @NotNull Album album, @NotNull String realName, String lrcName) {
        this.name = name;
        this.language = language;
        this.genre = genre;
        this.singers = singers;
        this.album = album;
        this.realName = realName;
        this.lrcName = lrcName;
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

    public Set<Singer> getSingers() {
        return singers;
    }

    public void setSingers(Set<Singer> singers) {
        this.singers = singers;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realPath) {
        this.realName = realPath;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getLrcName() {
        return lrcName;
    }

    public void setLrcName(String lrcPath) {
        this.lrcName = lrcPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id == song.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
