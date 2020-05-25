package wxm.example.comical_music_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wxm.example.comical_music_server.dao.SongDao;
import wxm.example.comical_music_server.dao.SongListDao;
import wxm.example.comical_music_server.dao.TagDao;
import wxm.example.comical_music_server.entity.music.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */
@Service
public class SongListService {

    @Autowired
    private SongListDao songListDao;

    @Autowired
    private FileService fileService;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private SongDao songDao;

    public boolean hasSongList(long id){
        return songListDao.existsById(id);
    }

    public SongList getSongList(long id){
        return songListDao.findById(id).orElse(null);
    }

    public SongList addSongList(String name, String introduction, Set<String> tagNames, Set<Long> songIds, MultipartFile imgFile){
        Set<Tag> tags=new HashSet<>();
        for(String tagName:
                tagNames){
            Tag tag=tagDao.findByName(tagName);
            if(tag==null){
                return null;
            }
            tags.add(tag);
        }

        Set<Song> songs=new HashSet<>();
        for (Long songId:
                songIds) {
            Song song=songDao.findById(songId).orElse(null);
            if (song==null){
                return null;
            }
            songs.add(song);
        }

        Image img=fileService.uploadImg(imgFile);
        if (img==null){
            return null;
        }

        SongList songList =new SongList(name,tags,introduction,songs,img);

        return songListDao.saveAndFlush(songList);
    }
}
