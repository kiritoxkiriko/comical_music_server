package wxm.example.comical_music_server.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import wxm.example.comical_music_server.annotation.ParamCheck;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.entity.bbs.Reply;
import wxm.example.comical_music_server.entity.respone.PageResponseData;
import wxm.example.comical_music_server.entity.respone.ResponseData;
import wxm.example.comical_music_server.service.PostService;
import wxm.example.comical_music_server.service.ReplyService;

/**
 * @author Alex Wang
 * @date 2020/05/14
 */
@RestController
@RequestMapping("/api/reply")
public class ReplyAPIController {
    @Autowired
    private ReplyService replyService;

    @Autowired
    private PostService postService;


    @GetMapping("/post/{postId}")
    @RequiresPermissions("view")
    public ResponseData getByPostId(@PathVariable long postId,
                                    @RequestParam(required = false, defaultValue = "1") Integer page,
                                    @RequestParam(required = false, defaultValue = "10")Integer size){
        Page<Reply> replies=replyService.getByPostId(postId, page-1,size);
        return PageResponseData.success(replies);
    }

    @GetMapping("/{id}")
    @RequiresPermissions("view")
    public ResponseData getById(@PathVariable long id){
        Reply reply=replyService.getReply(id);
        if(reply==null){
            return ResponseData.of(StatusCode.NO_SUCH_REPLY);
        }
        return ResponseData.success(reply);
    }

    @PostMapping("")
    @RequiresPermissions("reply")
    public ResponseData reply(@RequestParam long postId,@RequestParam @ParamCheck String content, Long replyTo){
        Reply reply=replyService.addReply(postId, content, replyTo);
        if (reply==null){
            return ResponseData.failed();
        }
        return ResponseData.success(reply);
    }

    @RequiresPermissions("view")
    @GetMapping("/count/post/{postId}")
    public ResponseData countByPost(@PathVariable long postId){
        try {
            return ResponseData.success(replyService.getCount(postId));
        } catch (Exception exception) {
            return ResponseData.failed();
        }
    }
}
