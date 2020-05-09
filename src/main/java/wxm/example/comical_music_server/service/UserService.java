package wxm.example.comical_music_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import wxm.example.comical_music_server.dao.SongListDao;
import wxm.example.comical_music_server.dao.UserDao;
import wxm.example.comical_music_server.dao.UserSpaceDao;
import wxm.example.comical_music_server.entity.bbs.Role;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.entity.music.SongList;
import wxm.example.comical_music_server.entity.user.UserSpace;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Alex Wang
 * @date 2020/05/07
 */

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserSpaceDao userSpaceDao;

    @Autowired
    private SongListDao songListDao;

    public User getUser(Long userId){
        return userDao.findUserById(userId);
    }

    public User getUser(String username){
        //return userDao.findUserByUsername(username);
        if (!DataSource.getData().containsKey(username)){
            return null;
        }
        return DataSource.getData().get(username);
    }

    public List<User> getAllUser(){
        return userDao.findAll();
    }

    public User getUserByPhone(String phone){
        return userDao.findUserByPhone(phone);
    }

    public User addUser(@NotNull String username, String email, String phone, @NotNull String password, Role role){
        if (userDao.existsByUsernameOrEmailOrPhone(username, email, phone)){
            return null;
        }
        User user=new User(username, email, phone, password, role, false);
        user=userDao.saveAndFlush(user);
        if (user==null){
            return null;
        }

        SongList songList=new SongList(user.getUsername()+"'s favorite songs", user, new ConcurrentSkipListSet<>());
        songList=songListDao.saveAndFlush(songList);
        if (songList==null){
            return null;
        }

        UserSpace userSpace=new UserSpace("Here is "+user.getUsername()+"'s space.");
        userSpace.setFavoriteSongs(songList);
        userSpace= userSpaceDao.saveAndFlush(userSpace);
        if(userSpace==null){
            return null;
        }
        user.setUserSpace(userSpace);
        user=userDao.saveAndFlush(user)
        if(user)
        return null;
    }

    //public User re
}
