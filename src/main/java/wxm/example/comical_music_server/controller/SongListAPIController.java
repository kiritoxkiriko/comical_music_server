package wxm.example.comical_music_server.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wxm.example.comical_music_server.annotation.ParamCheck;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.entity.music.SongList;
import wxm.example.comical_music_server.entity.respone.ResponseData;
import wxm.example.comical_music_server.service.SongListService;

import java.util.Set;

/**
 * @author Alex Wang
 * @date 2020/05/14
 */
@RestController
@RequestMapping("/api/songList")
public class SongListAPIController {
    
    @Autowired
    private SongListService songListService;
    
    @GetMapping("/{id}")
    @RequiresPermissions("view")
    public ResponseData getById(@PathVariable long id){
        SongList songList =songListService.getSongList(id);
        if (songList ==null){
            return ResponseData.of(StatusCode.NO_SUCH_SONG_LIST);
        }
        return ResponseData.success(songList);
    }

    @PostMapping("")
    @RequiresPermissions("post")
    public ResponseData addSongList(@RequestParam @ParamCheck String name, @RequestParam @ParamCheck String introduction,
                                    @RequestParam Set<Long> songIds, @RequestParam Set<String> tags, @RequestParam MultipartFile image){
        SongList songList =songListService.addSongList(name,introduction,tags,songIds,image);

        if (songList ==null){
            return ResponseData.failed();
        }
        return ResponseData.success(songList);
    }
}
