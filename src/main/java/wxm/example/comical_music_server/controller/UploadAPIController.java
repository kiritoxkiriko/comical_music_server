package wxm.example.comical_music_server.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wxm.example.comical_music_server.constant.Constant;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.entity.ResponseData;
import wxm.example.comical_music_server.service.FileService;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */
@RestController
@RequestMapping("/api/upload")
public class UploadAPIController {

    @Autowired
    private FileService fileService;

    @PostMapping("/img")
    @RequiresPermissions("post")
    public ResponseData uploadImg(@RequestParam MultipartFile file){
        String path= fileService.uploadImg(file);
        if(path==null){
            return new ResponseData(StatusCode.FAILED,null);
        }else if(path.isBlank()){
            return new ResponseData(StatusCode.FILE_TYPE_WRONG,null);
        }else {
            return new ResponseData(StatusCode.SUCCESS, Constant.STATIC_URL_PATH +path);
        }
    }
}
