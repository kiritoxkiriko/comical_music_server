package wxm.example.comical_music_server.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wxm.example.comical_music_server.annotation.ParamCheck;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.entity.music.Song;
import wxm.example.comical_music_server.entity.respone.ResponseData;
import wxm.example.comical_music_server.service.FileService;
import wxm.example.comical_music_server.service.SongService;

import java.util.Set;

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
    public ResponseData addSong(@RequestParam @ParamCheck String name, @RequestParam Set<String> tags,
                                @RequestParam Set<Long> singerIds, @RequestParam long albumId,
                                @RequestParam MultipartFile audio, MultipartFile lrc){
        Song song=songService.addSong(name, tags, singerIds, albumId, audio, lrc);
        if (song==null){
            return ResponseData.failed();
        }
        return ResponseData.success(song);
    }

    @GetMapping("/{id}")
    @RequiresPermissions("view")
    public ResponseData getById(@PathVariable long id){
        Song song=songService.getSong(id);
        if (song==null||!song.isExist()){
            return ResponseData.of(StatusCode.NO_SUCH_SONG);
        }
        return ResponseData.success(song);
    }

}
