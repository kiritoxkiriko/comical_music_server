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

import java.util.*;

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

    public List<Song> getGeneralRecommendSong() {
        Pageable page = PageRequest.of(0, 8, Sort.Direction.DESC, "playCount");
        return songDao.findAllByExist(true, page).getContent();
    }

//    public List<Song> getUserRecommandSong(User user) {
//        String key = Constant.PREFIX_USER_TAG + user.getId();
//        List<Song> songs = songDao.findAllByExist(true);
//        Map<Object, Object> map = redisUtil.hmget(key);
//        Map<String, Integer> scores = new HashMap<>();
//        Map<Song, Integer> songScores = new HashMap<>();
//        int total = 0;
//        for (Object o :
//                map.keySet()) {
//            String s = (String) o;
//            Integer num = (Integer) map.get(o);
//            total += num;
//            scores.put(s, num);
//        }
//        for (String s :
//                scores.keySet()) {
//            Integer i = scores.get(s);
//            scores.put(s, i * 10 / total);
//        }
//
//        for (Song s :
//                songs) {
//            Integer score = 0;
//            Set<Tag> ts = s.getTags();
//            for (Tag tag : ts) {
//                Integer sc = scores.get(tag.getName());
//                if (sc != null) {
//                    score += sc;
//                }
//            }
//            score += (int) s.getPlayCount() / 10;
//            songScores.put(s, score);
//        }
//        Map<Song, Integer> sortedScores=sortMapByValue(songScores);
//
//
//    }

//    private Map<Object, Integer> sortMapByValue(Map<, Integer> map) {
//        if (map == null || map.isEmpty()) {
//            return null;
//        }
//        Map<Object, Integer> sortedMap = new LinkedHashMap<>();
//        List<Map.Entry<Object, Integer>> entryList = new ArrayList<Map.Entry<Object, Integer>>(map.entrySet());
//        Collections.sort(entryList, new MapValueComparator());
//        Iterator<Map.Entry<Object, Integer>> iter = entryList.iterator();
//        Map.Entry<Object, Integer> tmpEntry = null;
//        while (iter.hasNext()) {
//            tmpEntry = iter.next();
//            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
//        }
//        return sortedMap;
//    }



}

class MapValueComparator implements Comparator<Map.Entry<Object, Integer>> {
    public int compare(Map.Entry<Object, Integer> me1, Map.Entry<Object, Integer> me2) {
        return me1.getValue().compareTo(me2.getValue())*(-1);
    }
}