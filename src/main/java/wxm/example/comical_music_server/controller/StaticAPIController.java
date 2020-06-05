package wxm.example.comical_music_server.controller;

import io.netty.util.internal.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wxm.example.comical_music_server.constant.Constant;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.exception.NotFoundException;
import wxm.example.comical_music_server.service.FileService;
import wxm.example.comical_music_server.service.SongService;
import wxm.example.comical_music_server.service.UserService;
import wxm.example.comical_music_server.utility.FileUtil;
import wxm.example.comical_music_server.utility.JWTUtil;

import java.io.IOException;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */
@RestController
@RequestMapping(Constant.STATIC_URL_PATH)
public class StaticAPIController {
    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private SongService songService;

    @GetMapping("/{type}/{name}")
    public ResponseEntity<Resource> getStatic(@PathVariable String type, @PathVariable String name, @RequestParam(required = false) String token) throws NotFoundException {
        Subject subject=SecurityUtils.getSubject();
        try {
            if (type.equals("audio")){//如果是音频，检查是否有权限
                if(!StringUtil.isNullOrEmpty(token)){
                    Long userId=JWTUtil.getUserId(token);
                    if (userId==null){
                        throw new AuthorizationException("无用户信息");
                    }
                    User user= userService.getUser(userId);
                    if (user==null){
                        throw new AuthorizationException("无用户信息");
                    }
                    if(!user.getPermission().contains("view")){
                        throw new AuthorizationException("无音频权限");
                    }
                    songService.addPlayCount(name);
                    songService.addTagCount(name,user);

                } else if(!subject.isPermitted("view")){
                    throw new AuthorizationException("无音频权限");
                }
            }
            Resource resource=fileService.loadFileAsResource("/"+type+"/"+name);
            String contentType=FileUtil.getMimeType(resource.getFile());
            if(contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (IOException e) {
            //e.printStackTrace();
            throw new NotFoundException();
        } catch (Exception e){
            throw e;
        }
    }
}
