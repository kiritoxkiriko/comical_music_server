package wxm.example.comical_music_server.entity.music;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import wxm.example.comical_music_server.constant.Constant;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    @JsonIgnore
    private String realName;

    @CreatedDate
    @Column
    @JsonIgnore
    private Date time;

    public Image() {
    }

    public Image(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realPath) {
        this.realName = realPath;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date date) {
        this.time = date;
    }

    @JsonGetter
    public String getPath(){
        return Constant.DOMAIN_URL+Constant.STATIC_URL_PATH+Constant.IMG_PATH+"/"+getRealName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(realName, image.realName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(realName);
    }

    @Override
    public String toString() {
        return "Image{" +
                "realPath='" + realName + '\'' +
                ", date=" + time +
                '}';
    }
}
