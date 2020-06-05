package wxm.example.comical_music_server.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wxm.example.comical_music_server.entity.respone.ResponseData;
import wxm.example.comical_music_server.service.RecommendService;

/**
 * @author Alex Wang
 * @date 2020/06/06
 */
@RestController
@RequestMapping("/api/recommend")
public class RecommendAPIController {
    @Autowired
    private RecommendService recommendService;

    @RequiresAuthentication
    @GetMapping("/song")
    public ResponseData getSongRecommend(){
        return ResponseData.success(recommendService.getGeneralRecommendSong());
    }
}
