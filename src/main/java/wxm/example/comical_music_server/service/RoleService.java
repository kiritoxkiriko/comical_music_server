package wxm.example.comical_music_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wxm.example.comical_music_server.dao.RoleDao;
import wxm.example.comical_music_server.entity.bbs.Role;

import java.util.List;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public List<Role> getAll(){
        return roleDao.findAll();
    }



    public Role getRoleByName(String roleName){
        return roleDao.findRoleByName(roleName);
    }
    public Role getRoleById(Long roleId){
        return roleDao.findById(roleId).orElse(null);
    }
}
