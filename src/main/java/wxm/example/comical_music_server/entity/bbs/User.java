package wxm.example.comical_music_server.entity.bbs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import wxm.example.comical_music_server.constant.Constant;
import wxm.example.comical_music_server.entity.music.Image;
import wxm.example.comical_music_server.entity.user.UserSpace;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author Alex Wang
 * @date 2020/05/04
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @NotEmpty
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    @JsonIgnore
    private String email;

    @Column(unique = true)
    @JsonIgnore
    private String phone;

    @NotEmpty
    @Column
    @JsonIgnore
    private String password;

    @JsonIgnore
    @ManyToOne
    private Role role;

    @JsonIgnore
    @Column
    private String permission;

    @Column
    private boolean ban = false;

    @CreatedDate
    @Column
    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    @OneToOne
    private UserSpace userSpace;

    @ManyToOne
    private Image image;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public User() {
    }

    public User(@NotNull String username, String email, String phone, @NotNull String password, @NotNull Role role, boolean ban) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.ban = ban;
        this.image=null;
        this.permission=role.getPermission();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public UserSpace getUserSpace() {
        return userSpace;
    }

    public void setUserSpace(UserSpace userSpace) {
        this.userSpace = userSpace;
    }

    public Image getImage() {
        if(image==null){
            return new Image(Constant.DEFAULT_ICON_NAME);
        }
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", ban=" + ban +
                ", userSpace=" + userSpace +
                '}';
    }
}
