package wxm.example.comical_music_server.shiro;


import com.auth0.jwt.exceptions.TokenExpiredException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wxm.example.comical_music_server.entity.bbs.Role;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.service.UserService;
import wxm.example.comical_music_server.utility.JWTUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class MyRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LogManager.getLogger(MyRealm.class);

    @Autowired
    private UserService userService;


    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws AuthenticationException{
//        String username = JWTUtil.getUsername(principals.toString());
//        User user = userService.getUser(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        Role role=user.getRole();
        User user= (User) principals.getPrimaryPrincipal();
        Role role= user.getRole();
        if (role!=null){
            simpleAuthorizationInfo.addRole(role.getName());
            Set<String> permission = new HashSet<String>(Arrays.asList(user.getPermission().split(",")));
            simpleAuthorizationInfo.addStringPermissions(permission);
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得userId，用于和数据库进行对比
        Long userId = JWTUtil.getUserId(token);
        if (userId == null) {
            LOGGER.error("token invalid");
            throw new AuthenticationException( "token invalid");
        }

        User user = userService.getUser(userId);
        if (user == null) {
            throw new AuthenticationException("User didn't existed!");
        }

        try {
            if (! JWTUtil.verify(token, userId, user.getPassword())) {
                throw new AuthenticationException("Username or password error");
            }
        } catch (TokenExpiredException e) {
            //TokenExpiredException将转换为自己实现的类
            throw new wxm.example.comical_music_server.exception.TokenExpiredException(e.getMessage());
        }
        //credential= token, principal=User 传给doGetAuthorizationInfo继续验证角色
        return new SimpleAuthenticationInfo(user, token, "my_realm");
    }
}
