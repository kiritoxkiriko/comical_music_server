package wxm.example.comical_music_server.entity.user;

import wxm.example.comical_music_server.entity.music.SongList;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Alex Wang
 * @date 2020/05/05
 */

@Entity
public class UserSpace implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String information;

    @OneToOne
    private SongList favoriteSongs;

    @OneToMany
    private Set<SongList> favoriteSongLists;

    public UserSpace() {
    }

    public UserSpace(String information) {
        this.information = information;
        this.favoriteSongLists = new ConcurrentSkipListSet<SongList>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public SongList getFavoriteSongs() {
        return favoriteSongs;
    }

    public void setFavoriteSongs(SongList favoriteSongs) {
        this.favoriteSongs = favoriteSongs;
    }

    public Set<SongList> getFavoriteSongLists() {
        return favoriteSongLists;
    }

    public void setFavoriteSongLists(Set<SongList> favoriteSongList) {
        this.favoriteSongLists = favoriteSongList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSpace userSpace = (UserSpace) o;
        return id == userSpace.id ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
