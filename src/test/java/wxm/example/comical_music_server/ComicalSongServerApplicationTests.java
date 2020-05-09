package wxm.example.comical_music_server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.service.SMSService;
import wxm.example.comical_music_server.utility.AuthUtil;

@SpringBootTest
class ComicalSongServerApplicationTests {
    @Autowired
    SMSService smsService;

    @Test
    void contextLoads() {
        //smsService.sendSMSCode("13072213550", "666666");
        System.out.println(AuthUtil.signPassword("admin"));
    }

    @Test
    void generateData(){

    }

}
