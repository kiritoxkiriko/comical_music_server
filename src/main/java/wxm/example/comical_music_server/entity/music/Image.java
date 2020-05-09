package wxm.example.comical_music_server.entity.music;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * @author Alex Wang
 * @date 2020/05/05
 */
@Entity
public class Image implements Serializable, StaticResource, Shareable{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonIgnore
    private long id;

    @Column( unique = true)
    private String realPath;

    @Column
    private Date date;

    public Image() {
        this.date=new Date(System.currentTimeMillis());
    }



    public Image(String realPath) {
        this.date=new Date(System.currentTimeMillis());
        this.realPath = realPath;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getRealPath() {
        return realPath;
    }

    @Override
    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.id &&
                Objects.equals(realPath, image.realPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, realPath);
    }


}
