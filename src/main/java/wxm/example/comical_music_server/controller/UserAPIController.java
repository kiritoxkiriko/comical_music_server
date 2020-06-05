package wxm.example.comical_music_server.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.dao.SongListDao;
import wxm.example.comical_music_server.dao.UserSpaceDao;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.entity.music.SongList;
import wxm.example.comical_music_server.entity.respone.ResponseData;
import wxm.example.comical_music_server.service.UserService;
import wxm.example.comical_music_server.utility.JWTUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author Alex Wang
 * @date 2020/05/28
 */
@RestController
@RequestMapping("/api/user")
public class UserAPIController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSpaceDao userSpaceDao;

    @Autowired
    private SongListDao songListDao;

    @GetMapping("")
    @RequiresAuthentication
    public ResponseData getCurrentUser(){
        Subject subject = SecurityUtils.getSubject();
        User user= (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        return ResponseData.success(user);
    }

    @GetMapping("/{userId}")
    @RequiresAuthentication
    public ResponseData getUser(@PathVariable long userId){
        User user=userService.getUser(userId);
        if(user==null){
            return ResponseData.of(StatusCode.NO_SUCH_USER);
        }
        return ResponseData.success(user);
    }

    @GetMapping("/userSpace")
    @RequiresAuthentication
    public ResponseData getCurrentUserSpace(){
        User user= JWTUtil.getCurrentUser();
        return ResponseData.success(user.getUserSpace());
    }

    @DeleteMapping("/deleteFavoriteSongList")
    public ResponseData deleteFavoriteSongList(@RequestParam long songListId){
        User user= JWTUtil.getCurrentUser();
        SongList songList=songListDao.findById(songListId).get();
        if (songList==null){
            return ResponseData.failed();
        }
        Set<SongList> songListSet=user.getUserSpace().getFavoriteSongLists();
        boolean success=songListSet.remove(songList);
        userSpaceDao.saveAndFlush(user.getUserSpace());
        return ResponseData.success(success);
    }

}
