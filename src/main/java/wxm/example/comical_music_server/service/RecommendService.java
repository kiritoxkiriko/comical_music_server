package wxm.example.comical_music_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wxm.example.comical_music_server.constant.Constant;
import wxm.example.comical_music_server.dao.SongDao;
import wxm.example.comical_music_server.dao.SongListDao;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.entity.music.Song;
import wxm.example.comical_music_server.entity.music.Tag;
import wxm.example.comical_music_server.utility.RedisUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alex Wang
 * @date 2020/06/05
 */
@Service
public class RecommendService {
    @Autowired
    private SongListDao songListDao;

    @Autowired
    private SongDao songDao;

    @Autowired
    private RedisUtil redisUtil;

    public List<Song> getGeneralRecommendSong(){
        Pageable page= PageRequest.of(0,8, Sort.Direction.DESC,"playCount");
        return songDao.findAllByExist(true,page).getContent();
    }

//    public List<Song> getUserRecommandSong(User user){
//        String key= Constant.PREFIX_USER_TAG+user.getId();
//        List<Song> songs= songDao.findAllByExist(true);
//        Map<Object,Object> map=redisUtil.hmget(key);
//        Map<Song,Integer> scores=new HashMap<>();
//
////        for (O t:s.getTags()){
////            t.getId()
////        }
//
//    }
}
