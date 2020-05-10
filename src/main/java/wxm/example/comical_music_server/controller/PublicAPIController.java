package wxm.example.comical_music_server.controller;

import org.apache.http.protocol.RequestDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wxm.example.comical_music_server.constant.StatusCode;
import wxm.example.comical_music_server.entity.ResponseData;
import wxm.example.comical_music_server.entity.bbs.Role;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.service.RoleService;
import wxm.example.comical_music_server.service.SMSService;
import wxm.example.comical_music_server.service.UserService;
import wxm.example.comical_music_server.utility.AuthUtil;
import wxm.example.comical_music_server.utility.JWTUtil;
import wxm.example.comical_music_server.utility.SMSUtil;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
@RestController
@RequestMapping("/api")
public class PublicAPIController {

    @Autowired
    private SMSService smsService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @PostMapping("/register")
    public ResponseData register(@RequestParam String username, @RequestParam String password, @RequestParam String phone, @RequestParam String code){
        Role role= roleService.getRoleByName("user");
        if(smsService.verifyCode(phone,code)){
            User user=userService.addUser(username,null, phone, password, role);
            if (user!=null){
                return new ResponseData(StatusCode.SUCCESS, JWTUtil.sign(user.getUsername(),user.getPassword()));
            }else{
                return new ResponseData(StatusCode.REGISTER_FAILED, null);
            }
        }else{
            return new ResponseData(StatusCode.VERIFY_FAILED, null);
        }
    }


    @PostMapping("/hasPhone")
    public ResponseData hasPhone(@RequestParam String phone){
        return new ResponseData(StatusCode.SUCCESS, userService.hasPhone(phone));
    }

    @PostMapping("/hasUsername")
    public ResponseData hasPhoneRegister(@RequestParam String username){
        return new ResponseData(StatusCode.SUCCESS, userService.hasUsername(username));
    }

    @PostMapping("/login")
    public ResponseData loginByUsername(@RequestParam String username, @RequestParam String password){
        if(!userService.verifyPasswordByUsername(username,password)){
            return new ResponseData(StatusCode.FAILED, null);
        }else{
            return new ResponseData(StatusCode.SUCCESS, JWTUtil.sign(username, AuthUtil.signPassword(password)));
        }
    }

    @PostMapping("/loginByPhone")
    public ResponseData loginByPhone(@RequestParam String phone, @RequestParam String password){
        if(!userService.verifyPasswordByUsername(phone,password)){
            return new ResponseData(StatusCode.FAILED, null);
        }else{
            User user=userService.getUserByPhone(phone);
            if (user==null){
                return new ResponseData(StatusCode.NO_SUCH_USER, null);
            }
            return new ResponseData(StatusCode.SUCCESS, JWTUtil.sign(user.getUsername(), user.getPassword()));
        }
    }

    @PostMapping("/quickLogin")
    public ResponseData quickLogin(@RequestParam String phone, String code){
        if (!smsService.verifyCode(phone, code)){
            return new ResponseData(StatusCode.VERIFY_FAILED, null);
        }else {
            User user=userService.getUserByPhone(phone);
            if(user==null){
                return new ResponseData(StatusCode.NO_SUCH_USER,null);
            }
            return new ResponseData(StatusCode.SUCCESS, JWTUtil.sign(user.getUsername(), user.getPassword()));
        }
    }

    @RequestMapping("/sendCode")
    public ResponseData sendSMS(@RequestParam String phone){
        if (smsService.sendSMSCode(phone)){
            return new ResponseData(StatusCode.SUCCESS, true);
        }else {
            return new ResponseData(StatusCode.FAILED, false);
        }
    }

    @PostMapping("/verifyCode")
    public ResponseData verifyCode(@RequestParam String phone, @RequestParam String code){
        if (smsService.verifyCode(phone, code)){
            return new ResponseData(StatusCode.SUCCESS, true);
        }else {
            return new ResponseData(StatusCode.VERIFY_FAILED, false);
        }
    }
}
