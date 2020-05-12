package wxm.example.comical_music_server.entity.music;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Alex Wang
 * @date 2020/05/05
 */

@Entity
public class Album implements Serializable, Shareable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @NotEmpty
    @Column
    private String name;

    @ManyToOne
    private Image image;

    @ManyToOne
    private Singer singer;

    private Integer year;

    public Album() {
    }

    public Album(@NotEmpty String name, Image image, Singer singer, Integer year) {
        this.name = name;
        this.image = image;
        this.singer = singer;
        this.year = year;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return id == album.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
