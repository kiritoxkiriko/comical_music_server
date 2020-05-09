package wxm.example.comical_music_server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wxm.example.comical_music_server.entity.bbs.User;

/**
 * @author Alex Wang
 * @date 2020/05/07
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findUserById(Long userId);

    User findUserByUsername(String userName);

    int deleteByUsername(String userName);

    User findUserByPhone(String phoneNumber);

    User findUserByEmail(String email);

    boolean existsByUsername(String userName);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByUsernameOrEmailOrPhone(String username, String email,String phone);
}
