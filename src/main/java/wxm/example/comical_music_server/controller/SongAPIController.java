package wxm.example.comical_music_server.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import wxm.example.comical_music_server.entity.ResponseData;
import wxm.example.comical_music_server.service.FileService;
import wxm.example.comical_music_server.service.SongService;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */

@Service
@RequestMapping("/api/song")
public class SongAPIController {

    @Autowired
    private SongService songService;

    @Autowired
    private FileService fileService;

    @RequestMapping("")
    @RequiresPermissions("post")
    public ResponseData addSong(){
        //TODO 上传歌曲
        return null;
    }
}
