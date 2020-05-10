package wxm.example.comical_music_server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wxm.example.comical_music_server.controller.WebController;
import wxm.example.comical_music_server.dao.SongListDao;
import wxm.example.comical_music_server.dao.UserDao;
import wxm.example.comical_music_server.dao.UserSpaceDao;
import wxm.example.comical_music_server.entity.bbs.Role;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.entity.music.Song;
import wxm.example.comical_music_server.entity.music.SongList;
import wxm.example.comical_music_server.entity.user.UserSpace;
import wxm.example.comical_music_server.utility.AuthUtil;
import wxm.example.comical_music_server.utility.PhoneCheckUtil;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Alex Wang
 * @date 2020/05/07
 */

@Service
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

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
        return userDao.findUserByUsername(username);
//        if (!DataSource.getData().containsKey(username)){
//            return null;
//        }
//        return DataSource.getData().get(username);
    }

    public List<User> getAllUser(){
        return userDao.findAll();
    }

    public User getUserByPhone(String phone){
        return userDao.findUserByPhone(phone);
    }

    public User getUserByEmail(String email){
        return userDao.findUserByEmail(email);
    }

    synchronized public User addUser(@NotNull String username, String email, String phone, @NotNull String password, Role role){
        if (!PhoneCheckUtil.isChinaPhoneLegal(phone)){
            return null;
        }
        if (userDao.existsByUsernameOrPhone(username, phone)){
            return null;
        }
        User user=new User(username, email, phone, AuthUtil.signPassword(password), role, false);
        user=userDao.saveAndFlush(user);
        if (user==null){
            return null;
        }

        SongList songList=new SongList(user.getUsername()+"'s favorite songs", user.getUsername()+"'s favorite songs",user, new HashSet<Song>());
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
        user=userDao.saveAndFlush(user);
        return user;
    }

    public UserSpace getUserSpace(String username){
        return getUser(username).getUserSpace();
    }

    public boolean deleteUser(String username){
        return userDao.deleteByUsername(username)>0;
    }

    public boolean deleteUser(long userId){
        try {
            userDao.deleteById(userId);
        } catch (Exception e) {
            LOGGER.error("deleteUserError");
            return false;
        }
        return true;
    }

    public boolean hasUsername(String username){
        return userDao.existsByUsername(username);
    }

    public boolean hasPhone(String phone){
        return userDao.existsByPhone(phone);
    }

    public boolean verifyPasswordByUsername(String username, String password){
        User user=getUser(username);
        if(user==null) {
            LOGGER.error("无此用户");
            return false;
        }
        return AuthUtil.verifyPassword(password,user.getPassword());
    }

    public boolean verifyPasswordByPhone(String phone, String password){
        User user=getUserByPhone(phone);
        if(user==null) {
            return false;
        }
        return AuthUtil.verifyPassword(password,user.getPassword());
    }

}
