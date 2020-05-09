package wxm.example.comical_music_server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wxm.example.comical_music_server.entity.bbs.Role;

import java.util.List;
import java.util.Optional;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Role findRoleById(Long roleId);

    Role findRoleByName(String roleName);

}
