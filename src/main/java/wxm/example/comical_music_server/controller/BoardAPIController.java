package wxm.example.comical_music_server.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.entity.respone.ResponseData;
import wxm.example.comical_music_server.service.BoardService;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */
@RestController
@RequestMapping("/api/board")
public class BoardAPIController {

    @Autowired
    private BoardService boardService;

    @RequestMapping("/all")
    @RequiresPermissions("view")
    public ResponseData getAll(){
        return new ResponseData(StatusCode.SUCCESS,boardService.getAll());
    }


}
