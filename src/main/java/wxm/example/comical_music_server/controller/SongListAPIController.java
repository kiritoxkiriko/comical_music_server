package wxm.example.comical_music_server.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wxm.example.comical_music_server.annotation.ParamCheck;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.entity.bbs.Board;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.entity.music.Image;
import wxm.example.comical_music_server.entity.music.Singer;
import wxm.example.comical_music_server.entity.music.Song;
import wxm.example.comical_music_server.entity.music.SongList;
import wxm.example.comical_music_server.entity.respone.ResponseData;
import wxm.example.comical_music_server.service.PostService;
import wxm.example.comical_music_server.service.SongListService;
import wxm.example.comical_music_server.utility.JWTUtil;

import java.util.HashSet;
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
    @Autowired
    private PostService postService;

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
        HashSet<SongList> set=new HashSet();
        set.add(songList);

        postService.add("我上传了新的歌单："+songList.getName(),
                new HashSet<Song>(),new HashSet<Image>(),set,new Board(2),3);
        return ResponseData.success(songList);
    }

    @RequiresAuthentication
    @DeleteMapping("/{id}")
    public ResponseData deleteById(@PathVariable long id){
        User user=JWTUtil.getCurrentUser();
        if (user.getPermission().contains("admin")){
            SongList songList=songListService.getSongList(id);
            if(songList==null){
                return ResponseData.of(StatusCode.NO_SUCH_SONG_LIST);
            }
            if (!songList.getCreator().equals(user)){
                return ResponseData.of(StatusCode.UNAUTHORIZED);
            }
            var result=songListService.delete(id);
            if(!result){
                return ResponseData.failed();
            }
            return ResponseData.success(result);
        }
        return ResponseData.of(StatusCode.UNAUTHORIZED);
    }

    @PostMapping("/addSong")
    public ResponseData addSong(@RequestParam long songListId, @RequestParam long songId){
        User user=JWTUtil.getCurrentUser();
        SongList songlist=songListService.getSongList(songListId);
        if(songlist==null){
            return ResponseData.failed();
        }
        if(songlist.getCreator().equals(user)){
            return ResponseData.success(songListService.addSongToSongList(songListId,songId));
        }else {
            return ResponseData.of(StatusCode.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/deleteSong")
    public ResponseData deleteSong(@RequestParam long songListId, @RequestParam long songId){
        User user=JWTUtil.getCurrentUser();
        SongList songlist=songListService.getSongList(songListId);
        if(songlist==null){
            return ResponseData.failed();
        }
        if(songlist.getCreator().equals(user)){
            return ResponseData.success(songListService.deleteSongFromSongList(songListId,songId));
        }else {
            return ResponseData.of(StatusCode.UNAUTHORIZED);
        }
    }

    @GetMapping("/mySongList")
    @RequiresPermissions("view")
    public ResponseData getCurrentUserSongList(){
        User user=JWTUtil.getCurrentUser();
        return ResponseData.success(songListService.getSongListByUserId(user.getId()));
    }

}
