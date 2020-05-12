package wxm.example.comical_music_server.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wxm.example.comical_music_server.constant.Constant;
import wxm.example.comical_music_server.exception.NotFoundException;
import wxm.example.comical_music_server.service.FileService;
import wxm.example.comical_music_server.utility.FileUtil;

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

    @GetMapping("/{type}/{name}")
    public ResponseEntity<Resource> getStatic(@PathVariable String type, @PathVariable String name ) throws NotFoundException {
        try {
            if (type.equals("audio")){//如果是音频，检查是否有权限
                if(!SecurityUtils.getSubject().isAuthenticated()){
                    throw new AuthorizationException();
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException();
        }
    }
}
