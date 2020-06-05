package wxm.example.comical_music_server.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wxm.example.comical_music_server.annotation.ParamCheck;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.entity.bbs.Board;
import wxm.example.comical_music_server.entity.music.Image;
import wxm.example.comical_music_server.entity.music.Singer;
import wxm.example.comical_music_server.entity.music.Song;
import wxm.example.comical_music_server.entity.music.SongList;
import wxm.example.comical_music_server.entity.respone.ResponseData;
import wxm.example.comical_music_server.service.FileService;
import wxm.example.comical_music_server.service.PostService;
import wxm.example.comical_music_server.service.SongCommentService;
import wxm.example.comical_music_server.service.SongService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */

@RestController
@RequestMapping("/api/song")
public class SongAPIController {

    @Autowired
    private SongService songService;

    @Autowired
    private FileService fileService;

    @Autowired
    private PostService postService;

    @Autowired
    private SongCommentService songCommentService;

    @PostMapping("")
    @RequiresPermissions("post")
    public ResponseData addSong(@RequestParam @ParamCheck String name, @RequestParam Set<String> tags,
                                @RequestParam Set<Long> singerIds, @RequestParam long albumId,
                                @RequestParam MultipartFile audio, MultipartFile lrc){
        Song song=songService.addSong(name, tags, singerIds, albumId, audio, lrc);
        if (song==null){
            return ResponseData.failed();
        }
        HashSet<Song> set=new HashSet();
        set.add(song);
        Set<Singer> singers=song.getSingers();
        String singerStr="";
        for (Singer s:
             singers) {
            singerStr+="/"+s.getName();
        }
        singerStr=singerStr.replaceFirst("/","");
        postService.add("我上传了新的音乐："+" - "+singerStr,
                set,new HashSet<Image>(),new HashSet<SongList>(),new Board(1),2);
        return ResponseData.success(song);
    }

    @GetMapping("/{id}")
    @RequiresPermissions("view")
    public ResponseData getById(@PathVariable long id){
        Song song=songService.getSong(id);
        if (song==null||!song.isExist()){
            return ResponseData.of(StatusCode.NO_SUCH_SONG);
        }
        songCommentService.addCountToSong(song);
        return ResponseData.success(song);
    }

    @DeleteMapping("/{id}")
    public ResponseData deleteById(@PathVariable long id){
        var result=songService.delete(id);
        if(!result){
            return ResponseData.failed();
        }
        return ResponseData.success(result);
    }

}
