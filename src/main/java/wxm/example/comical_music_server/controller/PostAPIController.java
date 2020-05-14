package wxm.example.comical_music_server.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wxm.example.comical_music_server.annotation.ParamCheck;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.entity.respone.PageResponseData;
import wxm.example.comical_music_server.entity.respone.ResponseData;
import wxm.example.comical_music_server.entity.bbs.Board;
import wxm.example.comical_music_server.entity.bbs.Post;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.entity.music.Image;
import wxm.example.comical_music_server.entity.music.Song;
import wxm.example.comical_music_server.entity.music.SongList;
import wxm.example.comical_music_server.service.*;
import wxm.example.comical_music_server.utility.JWTUtil;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */
@RestController
@RequestMapping("/api/post")
public class PostAPIController {

    @Autowired
    private PostService postService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SongListService songListService;

    @Autowired
    private SongService songService;

    @RequiresPermissions("view")
    @GetMapping("/board/{boardId}")
    public ResponseData getByBoardId(@PathVariable long boardId, @RequestParam(defaultValue = "1",required = false)Integer page, @RequestParam(defaultValue = "10",required = false)Integer size){
        return new PageResponseData(StatusCode.SUCCESS, postService.getByBoardId(boardId, page-1,size));
    }

    @RequiresPermissions("view")
    @GetMapping("/all")
    public ResponseData getAll(@RequestParam(defaultValue = "1",required = false)Integer page, @RequestParam(defaultValue = "10",required = false)Integer size){
        return new PageResponseData(StatusCode.SUCCESS, postService.getAll(page-1,size));
    }

    @RequiresPermissions("view")
    @GetMapping("/{id}")
    public ResponseData getById(@PathVariable long id){
        Post post=postService.getPost(id);
        if (post==null){
            return ResponseData.of(StatusCode.NO_SUCH_POST);
        }
        return ResponseData.success(post);
    }

    @RequiresPermissions("post")
    @PostMapping("")
    public ResponseData post(@RequestParam @ParamCheck String content, @RequestParam @ParamCheck Long boardId,
                             List<MultipartFile> files, Long songId, Long songListId){
        if (!boardService.hasBoard(boardId)){
            return new ResponseData(StatusCode.NO_SUCH_BOARD,null);
        }
        Board board=new Board();
        board.setId(boardId);

        User user= JWTUtil.getCurrentUser();
        if(user.isBan()){
            return new ResponseData(StatusCode.USER_HAS_BAN,null);
        }

        Set<Image> images=new HashSet<>();
        Set<SongList> songLists= new HashSet<>();
        Set<Song> songs= new HashSet<>();

        Song song=null;
        SongList songList=null;

        if(songId!=null){
            song=songService.getSong(songId);
        }

        if (songListId!=null){
            songList=songListService.getSongList(songListId);
        }

        if (song!=null){
            songs.add(song);
        }
        if (songList!=null){
            songLists.add(songList);
        }

        if(files!=null&&files.size()>0){
            for (MultipartFile file:
                 files) {
                Image image=fileService.uploadImg(file);
                if(image!=null){
                    images.add(image);
                }
            }
        }

        Post post=postService.add(content, songs, images, songLists, board);
        if(post==null){
            return new ResponseData(StatusCode.FAILED,null);
        }
        return new ResponseData(StatusCode.SUCCESS,post);
    }

}
