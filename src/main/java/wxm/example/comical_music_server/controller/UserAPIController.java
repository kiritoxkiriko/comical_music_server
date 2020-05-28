package wxm.example.comical_music_server.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.entity.respone.ResponseData;
import wxm.example.comical_music_server.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alex Wang
 * @date 2020/05/28
 */
@RestController
@RequestMapping("/api/user")
public class UserAPIController {

    @Autowired
    private UserService userService;

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
}
