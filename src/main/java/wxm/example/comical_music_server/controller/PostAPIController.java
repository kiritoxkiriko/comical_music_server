package wxm.example.comical_music_server.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.entity.ResponseData;
import wxm.example.comical_music_server.entity.bbs.Board;
import wxm.example.comical_music_server.entity.bbs.Post;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.entity.music.Image;
import wxm.example.comical_music_server.entity.music.Song;
import wxm.example.comical_music_server.entity.music.SongList;
import wxm.example.comical_music_server.service.BoardService;
import wxm.example.comical_music_server.service.PostService;
import wxm.example.comical_music_server.utility.JWTUtil;

import java.util.HashSet;
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

    @RequiresPermissions("view")
    @GetMapping("/board/{boardId}")
    public ResponseData getByBoardId(@PathVariable long boardId, Integer page, Integer size){
        if(page==null){
            page=0;
        }else {
            page-=1;
        }
        if (size==null){
            size=10;
        }
        return new ResponseData(StatusCode.SUCCESS, postService.getByBoardId(boardId, page,size));
    }

    @RequiresPermissions("view")
    @GetMapping("/all")
    public ResponseData getAll(Integer page, Integer size){
        if(page==null){
            page=0;
        }else {
            page-=1;
        }
        if (size==null){
            size=10;
        }
        return new ResponseData(StatusCode.SUCCESS, postService.getAll(page,size).get());
    }

    @RequiresPermissions("post")
    @PostMapping("")
    public ResponseData post(@RequestParam String content, @RequestParam long boardId){
        if (!boardService.hasBoard(boardId)){
            return new ResponseData(StatusCode.NO_SUCH_BOARD,null);
        }
        User user= JWTUtil.getCurrentUser();
        if(user.isBan()){
            return new ResponseData(StatusCode.USER_HAS_BAN,null);
        }
        Board board=new Board();
        board.setId(boardId);
        Set<Image> images= new HashSet<>();
        Set<SongList> songLists= new HashSet<>();
        Set<Song> songs= new HashSet<>();
        Post post=postService.add(content, songs, images, songLists, board);
        if(post==null){
            return new ResponseData(StatusCode.FAILED,null);
        }
        return new ResponseData(StatusCode.SUCCESS,post);
    }


}
