package wxm.example.comical_music_server.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import wxm.example.comical_music_server.annotation.ParamCheck;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.entity.music.SongComment;
import wxm.example.comical_music_server.entity.respone.PageResponseData;
import wxm.example.comical_music_server.entity.respone.ResponseData;
import wxm.example.comical_music_server.service.SongCommentService;

/**
 * @author Alex Wang
 * @date 2020/05/14
 */
@RestController
@RequestMapping("/api/songComment")
public class SongCommentAPIController {

    @Autowired
    private SongCommentService songCommentService;

    @GetMapping("/song/{songId}")
    @RequiresPermissions("view")
    public ResponseData getCommentBySongId(@PathVariable long songId,
                                           @RequestParam(required = false, defaultValue = "1") Integer page,
                                           @RequestParam(required = false, defaultValue = "20")Integer size){
        Page<SongComment> songComments=songCommentService.getSongCommentsBySongId(songId,page-1,size);
        return PageResponseData.success(songComments);
    }

    @GetMapping("/{id}")
    @RequiresPermissions("view")
    public ResponseData getComment(@PathVariable long id){
        SongComment songComment=songCommentService.getSongComment(id);
        if (songComment==null){
            return ResponseData.of(StatusCode.NO_SUCH_SONG_COMMENT);
        }
        return ResponseData.success(songComment);
    }

    @PostMapping("")
    @RequiresPermissions("reply")
    public ResponseData comment(@RequestParam @ParamCheck String content,
                                @RequestParam @ParamCheck Long songId, Long replyTo){
        SongComment songComment=songCommentService.addSongComment(songId, content, replyTo);
        if (songComment==null){
            return ResponseData.failed();
        }
        return ResponseData.success(songComment);
    }


    @GetMapping("/count/song/{songId}")
    @RequiresPermissions("view")
    public ResponseData count(@PathVariable long songId){
        try {
            return ResponseData.success(songCommentService.count(songId));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseData.failed();
        }
    }

}
