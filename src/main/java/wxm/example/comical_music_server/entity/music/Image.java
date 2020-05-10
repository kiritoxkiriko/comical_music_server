package wxm.example.comical_music_server.entity.music;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */
@Entity
public class Image {
    @Id
    private String realPath;

    @CreatedDate
    @Column
    @JsonIgnore
    private Date date;

    public Image() {
    }

    public Image(String realPath) {
        this.realPath = realPath;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(realPath, image.realPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(realPath);
    }

    @Override
    public String toString() {
        return "Image{" +
                "realPath='" + realPath + '\'' +
                ", date=" + date +
                '}';
    }
}
