package wxm.example.comical_music_server;

import org.apache.commons.codec.language.bm.Languages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import wxm.example.comical_music_server.dao.GenreDao;
import wxm.example.comical_music_server.dao.LanguageDao;
import wxm.example.comical_music_server.dao.RoleDao;
import wxm.example.comical_music_server.entity.bbs.Role;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.entity.music.Genre;
import wxm.example.comical_music_server.entity.music.Language;
import wxm.example.comical_music_server.service.SMSService;
import wxm.example.comical_music_server.service.UserService;
import wxm.example.comical_music_server.utility.AuthUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ComicalSongServerApplicationTests {
    @Autowired
    SMSService smsService;
    @Autowired
    UserService userService;
    @Autowired
    RoleDao roleDao;
    @Autowired
    LanguageDao languageDao;
    @Autowired
    GenreDao genreDao;

    @Test
    void contextLoads() {
        //smsService.sendSMSCode("13072213550", "666666");
        System.out.println(AuthUtil.signPassword("admin"));
    }

    @Test
    void initData(){
        Role roleAdmin=new Role("admin","admin,view,post,reply");
        Role roleUser=new Role("user","view,post,reply");
        roleDao.save(roleAdmin);
        roleDao.save(roleUser);
        roleDao.flush();

        User admin=userService.addUser("admin","","13800000000",AuthUtil.signPassword("admin"),roleAdmin);

        List<Language> languages=new ArrayList<>();
        languages.add(new Language("华语"));
        languages.add(new Language("粤语"));
        languages.add(new Language("英语"));
        languages.add(new Language("日语"));
        languages.add(new Language("韩语"));
        languages.add(new Language("其他"));
        languageDao.saveAll(languages);
        languageDao.flush();

        List<Genre> genres=new ArrayList<>();
        genres.add(new Genre("流行"));
        genres.add(new Genre("古典"));
        genres.add(new Genre("摇滚"));
        genres.add(new Genre("民族"));
        genres.add(new Genre("金属"));
        genres.add(new Genre("其他"));
        genreDao.saveAll(genres);
        genreDao.flush();
    }

}
