package wxm.example.comical_music_server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wxm.example.comical_music_server.dao.*;
import wxm.example.comical_music_server.entity.bbs.Board;
import wxm.example.comical_music_server.entity.bbs.Role;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.entity.music.Tag;
import wxm.example.comical_music_server.service.SMSService;
import wxm.example.comical_music_server.service.UserService;
import wxm.example.comical_music_server.utility.AuthUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class ComicalSongServerApplicationTests {
    @Autowired
    SMSService smsService;
    @Autowired
    UserService userService;
    @Autowired
    RoleDao roleDao;
    @Autowired
    TagDao tagDao;
    @Autowired
    BoardDao boardDao;
    @Autowired
    PostDao postDao;

    @Test
    void contextLoads() {
        //smsService.sendSMSCode("13072213550", "666666");
        System.out.println(AuthUtil.signPassword("admin"));
        System.out.println(AuthUtil.signPassword("admin"));
    }

    @Test
    void initData(){
        Role roleAdmin=new Role("admin","admin,view,post,reply");
        Role roleUser=new Role("user","view,post,reply");
        roleDao.save(roleAdmin);
        roleDao.save(roleUser);
        roleDao.flush();

        User admin=userService.addUser("admin","","13800000000","admin",roleAdmin);

        Set<Tag> tags=new HashSet<>();
        tags.add(new Tag("华语","language"));
        tags.add(new Tag("粤语","language"));
        tags.add(new Tag("欧美","language"));
        tags.add(new Tag("日语","language"));
        tags.add(new Tag("韩语","language"));

        tags.add(new Tag("流行","genre"));
        tags.add(new Tag("古典","genre"));
        tags.add(new Tag("摇滚","genre"));
        tags.add(new Tag("民族","genre"));
        tags.add(new Tag("电子","genre"));
        tags.add(new Tag("舞曲","genre"));
        tags.add(new Tag("说唱","genre"));
        tags.add(new Tag("轻音乐","genre"));
        tags.add(new Tag("爵士","genre"));
        tags.add(new Tag("乡村","genre"));
        tags.add(new Tag("R&B","genre"));

        tags.add(new Tag("ACG","theme"));
        tags.add(new Tag("影视原声","theme"));
        tags.add(new Tag("校园","theme"));
        tags.add(new Tag("游戏","theme"));
        tags.add(new Tag("网络歌曲","theme"));
        tags.add(new Tag("钢琴","theme"));
        tags.add(new Tag("器乐","theme"));

        tags.add(new Tag("学习","scene"));
        tags.add(new Tag("工作","scene"));
        tags.add(new Tag("休息","scene"));
        tags.add(new Tag("通勤","scene"));

        tagDao.saveAll(tags);
        tagDao.flush();



        List<Board> boards=new ArrayList<>();
        boards.add(new Board("音乐分享"));
        boards.add(new Board("歌单分享"));
        boards.add(new Board("灌水"));
        boardDao.saveAll(boards);
        boardDao.flush();

//        Board board=new Board();
//        board.setId(1);
//        Post post=new Post("123123",null,null,null,admin,board);
//        postDao.saveAndFlush(post);

    }

    @Test
    void generateJSON(){
        ObjectMapper objectMapper=new ObjectMapper();
        System.out.println();
    }

}
