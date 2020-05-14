package wxm.example.comical_music_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wxm.example.comical_music_server.dao.TagDao;
import wxm.example.comical_music_server.entity.music.Tag;

import java.util.List;

/**
 * @author Alex Wang
 * @date 2020/05/13
 */
@Service
public class TagService {
    @Autowired
    private TagDao tagDao;

    public List<Tag> getAll(){
        return tagDao.findAll();
    }

    public List<Tag> getTagsByType(String string){
        return getTagsByType(string);
    }
}
