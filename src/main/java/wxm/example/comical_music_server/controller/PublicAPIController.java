package wxm.example.comical_music_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wxm.example.comical_music_server.entity.ResponseData;
import wxm.example.comical_music_server.service.SMSService;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
@RestController
@RequestMapping("/api")
public class PublicAPIController {

    @Autowired
    private SMSService smsService;

//    @PostMapping("/register")
//    public ResponseData register(@RequestParam String User){
//        return null;
//    }

    @PostMapping("/sendSMS")
    public ResponseData sendSMS(@RequestParam String phone){
        if (smsService.sendSMSCode(phone)){
            return new ResponseData(200, "success", true);
        }else {
            return new ResponseData(200, "failed", false);
        }
    }

    @PostMapping("/verifyCode")
    public ResponseData verifyCode(@RequestParam String phone, @RequestParam String code){
        if (smsService.verifyCode(phone, code)){
            return new ResponseData(200, "success", true);
        }else {
            return new ResponseData(200, "wrong", false);
        }
    }
}
