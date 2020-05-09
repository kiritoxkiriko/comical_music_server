package wxm.example.comical_music_server.entity.music;

import wxm.example.comical_music_server.entity.bbs.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @author Alex Wang
 * @date 2020/05/05
 */
@Entity
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
    private String realPath;

    @Column(unique = true)
    private String lrcPath;

    @ManyToOne
    private User uploader;

    @Column
    private Date uploadDate;

    public Song() {
        this.uploadDate=new Date(System.currentTimeMillis());
    }

    public Song(@NotNull String name, Language language, Genre genre, @NotNull Set<Singer> singers, @NotNull Album album, @NotNull String realPath, User uploader) {
        this.name = name;
        this.language = language;
        this.genre = genre;
        this.singers = singers;
        this.album = album;
        this.realPath = realPath;
        this.uploader = uploader;
        this.uploadDate=new Date(System.currentTimeMillis());
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

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
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

    public String getLrcPath() {
        return lrcPath;
    }

    public void setLrcPath(String lrcPath) {
        this.lrcPath = lrcPath;
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
