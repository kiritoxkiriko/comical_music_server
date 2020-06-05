package wxm.example.comical_music_server.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wxm.example.comical_music_server.annotation.ParamCheck;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.entity.music.Album;
import wxm.example.comical_music_server.entity.respone.PageResponseData;
import wxm.example.comical_music_server.entity.respone.ResponseData;
import wxm.example.comical_music_server.service.AlbumService;
import wxm.example.comical_music_server.service.FileService;

import java.util.List;

/**
 * @author Alex Wang
 * @date 2020/05/14
 */
@RestController
@RequestMapping("/api/album")
public class AlbumAPIController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private FileService fileService;

    @RequiresPermissions("view")
    @GetMapping("/{albumId}")
    public ResponseData getById(@PathVariable long albumId){
        Album album=albumService.getById(albumId);
        if (album==null){
            return new ResponseData(StatusCode.NO_SUCH_ALBUM);
        }
        return new ResponseData(StatusCode.FAILED);
    }

    @RequiresPermissions("post")
    @PostMapping("")
    public ResponseData add(@RequestParam @ParamCheck String name, @RequestParam MultipartFile image,
                            @RequestParam long singerId, Integer year){
        Album album=albumService.addAlbum(name, image, singerId, year);
        if (album==null){
            return ResponseData.failed();
        }
        return ResponseData.of(StatusCode.SUCCESS,album);
    }
}
