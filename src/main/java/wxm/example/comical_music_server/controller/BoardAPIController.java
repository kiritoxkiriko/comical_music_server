package wxm.example.comical_music_server.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.entity.ResponseData;
import wxm.example.comical_music_server.entity.bbs.Board;
import wxm.example.comical_music_server.service.BoardService;

import java.util.List;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */
@Controller
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
