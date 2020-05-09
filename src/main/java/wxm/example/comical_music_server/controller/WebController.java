package wxm.example.comical_music_server.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wxm.example.comical_music_server.entity.ResponseData;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.exception.UnauthorizedException;
import wxm.example.comical_music_server.service.UserService;
import wxm.example.comical_music_server.utility.JWTUtil;

@RestController
public class WebController {

    private static final Logger LOGGER = LogManager.getLogger(WebController.class);

    private UserService userService;

    @Autowired
    public void setService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseData login(@RequestParam String username,
                              @RequestParam String password) {
        //System.out.println(username+" "+password);
        User user = userService.getUser(username);
        //System.out.println(user);
        if (user.getPassword().equals(password)) {
            return new ResponseData(200, "Login success", JWTUtil.sign(username, password));
        } else {
            throw new UnauthorizedException("Wrong Password");
        }
    }

    @GetMapping("/article")
    public ResponseData article() {
        Subject subject = SecurityUtils.getSubject();
        User user= (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (subject.isAuthenticated()) {
            //System.out.println(user);
            return new ResponseData(200, "You are already logged in, "+user.getUsername(), null);
        } else {
            return new ResponseData(200, "You are guest", null);
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public ResponseData requireAuth() {
        return new ResponseData(200, "You are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public ResponseData requireRole() {
        return new ResponseData(200, "You are visiting require_role", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public ResponseData requirePermission() {
        return new ResponseData(200, "You are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseData unauthorized() {
        return new ResponseData(401, "Unauthorized", null);
    }
}
