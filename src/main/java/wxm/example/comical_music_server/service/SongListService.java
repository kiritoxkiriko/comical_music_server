package wxm.example.comical_music_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wxm.example.comical_music_server.dao.SongListDao;
import wxm.example.comical_music_server.entity.music.SongList;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */
@Service
public class SongListService {

    @Autowired
    private SongListDao songListDao;

    public boolean hasSongList(long id){
        return songListDao.existsById(id);
    }

    public SongList getSongList(long id){
        return songListDao.findById(id).orElse(null);
    }
}
