package wxm.example.comical_music_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import wxm.example.comical_music_server.utility.Constant;
import wxm.example.comical_music_server.utility.PhoneCheckUtil;
import wxm.example.comical_music_server.utility.RedisUtil;
import wxm.example.comical_music_server.utility.SMSUtil;

import java.util.Random;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */

@Service
public class SMSService {

    @Autowired
    private RedisUtil redisUtil;

    public boolean sendSMSCode(String phoneNumber, String code) {
        if (!PhoneCheckUtil.isChinaPhoneLegal(phoneNumber)) {
            return false;
        }

        if (redisUtil.set(Constant.PREFIX_PHONE + phoneNumber, code, Constant.SMS_EXPIRE_TIME)) {
            return SMSUtil.sendSms(phoneNumber, code);
        }
        return false;
    }

    public boolean sendSMSCode(String phoneNumber) {
        String randomCode = String.valueOf(new Random().nextInt(900000) + 100000);
        return sendSMSCode(phoneNumber, randomCode);
    }

    public boolean verifyCode(String phoneNumber, String code) {
        return redisUtil.hasKey(Constant.PREFIX_PHONE + phoneNumber);
    }

}
