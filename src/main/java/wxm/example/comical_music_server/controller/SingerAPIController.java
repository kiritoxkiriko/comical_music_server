package wxm.example.comical_music_server.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wxm.example.comical_music_server.annotation.ParamCheck;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.entity.music.Singer;
import wxm.example.comical_music_server.entity.respone.ResponseData;
import wxm.example.comical_music_server.service.SingerService;

/**
 * @author Alex Wang
 * @date 2020/05/14
 */
@RestController
@RequestMapping("/api/singer")
public class SingerAPIController {

    @Autowired
    private SingerService singerService;

    @RequiresPermissions("view")
    @GetMapping("/{id}")
    public ResponseData getById(@PathVariable long id){
        Singer singer=singerService.getBySingerId(id);
        if (singer==null){
            return ResponseData.of(StatusCode.NO_SUCH_SINGER);
        }
        return ResponseData.success(singer);
    }

    @RequiresPermissions("post")
    @PostMapping("")
    public ResponseData add(@RequestParam @ParamCheck String name, @RequestParam @ParamCheck String introduction, @RequestParam MultipartFile image){
        Singer singer=singerService.addSinger(name, introduction, image);
        if (singer==null){
            return ResponseData.failed();
        }
        return ResponseData.success(singer);
    }
}
