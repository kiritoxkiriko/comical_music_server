package wxm.example.comical_music_server.entity.music;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import wxm.example.comical_music_server.constant.Constant;
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
    @JsonIgnore
    @Column(unique = true)
    private String realName;

    @Column(unique = true)
    @JsonIgnore
    private String realLrcName;

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

    public Song(@NotEmpty String name, Language language, Genre genre, @NotNull Set<Singer> singers, @NotNull Album album, @NotNull String realName, String realLrcName) {
        this.name = name;
        this.language = language;
        this.genre = genre;
        this.singers = singers;
        this.album = album;
        this.realName = realName;
        this.realLrcName = realLrcName;
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

    public String getRealLrcName() {
        return realLrcName;
    }

    public void setRealLrcName(String lrcPath) {
        this.realLrcName = lrcPath;
    }

    @JsonGetter
    public String getPath(){
        return Constant.DOMAIN_URL+Constant.STATIC_URL_PATH+Constant.AUDIO_PATH+"/"+getRealName();
    }

    @JsonGetter
    public String getLrcPath(){
        return Constant.DOMAIN_URL+Constant.STATIC_URL_PATH+Constant.LRC_PATH+"/"+getRealLrcName();
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
