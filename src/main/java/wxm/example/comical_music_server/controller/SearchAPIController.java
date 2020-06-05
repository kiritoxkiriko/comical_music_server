package wxm.example.comical_music_server.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wxm.example.comical_music_server.entity.respone.PageResponseData;
import wxm.example.comical_music_server.entity.respone.ResponseData;
import wxm.example.comical_music_server.service.SongListService;
import wxm.example.comical_music_server.service.SongService;
import wxm.example.comical_music_server.service.UserService;

/**
 * @author Alex Wang
 * @date 2020/05/14
 */
@RestController
@RequestMapping("/api/search")
public class SearchAPIController {
    @Autowired
    private SongService songService;

    @Autowired
    private SongListService songListService;

    @Autowired
    private UserService userService;

    @GetMapping("/song")
    @RequiresPermissions("view")
    public ResponseData searchSong(@RequestParam String name,
                                   @RequestParam(defaultValue = "1",required = false)Integer page,
                                   @RequestParam(defaultValue = "10",required = false)Integer size){
        return PageResponseData.success(songService.getSongsByNameLike(name,page-1,size));
    }

    @GetMapping("/songList")
    @RequiresPermissions("view")
    public ResponseData searchSongList(@RequestParam String name,
                                       @RequestParam(defaultValue = "1",required = false)Integer page,
                                       @RequestParam(defaultValue = "10",required = false)Integer size){
        return PageResponseData.success(songListService.getSongListByNameLike(name,size,page-1));
    }


}
