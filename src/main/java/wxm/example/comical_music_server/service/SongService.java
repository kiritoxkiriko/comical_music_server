package wxm.example.comical_music_server.service;

import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wxm.example.comical_music_server.constant.Constant;
import wxm.example.comical_music_server.dao.*;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.entity.music.*;
import wxm.example.comical_music_server.utility.RedisUtil;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */
@Service
public class SongService {

    @Autowired
    private SongDao songDao;

    @Autowired
    private SingerDao singerDao;

    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private FileService fileService;

    @Autowired
    private RedisUtil redisUtil;


    public boolean hasSong(long id){
        Song song=songDao.findById(id).orElse(null);
        if(song==null){
            return false;
        }
        return song.isExist();
    }

    public Song getSong(long id){
        return songDao.findById(id).orElse(null);
    }

    public Song getSongByRealAudioName(String realname){
        return songDao.findByRealName(realname);
    }

    public Song addPlayCount(String realname){
        Song song=getSongByRealAudioName(realname);
        if (song==null){
            return null;
        }
        song.setPlayCount(song.getPlayCount()+1);
        Song song1=songDao.saveAndFlush(song);
        return song1;
    }

    public Page<Song> getSongsByNameLike(String name, int page, int size){
        Pageable pageable= PageRequest.of(page,size, Sort.Direction.DESC,"uploadTime");
        return songDao.findAllByNameContainingAndExist(name,true,pageable);
    }

    public Song addSong(@NotEmpty String name, Set<String> tagNames, Set<Long> singerIds, long albumId, @NotNull MultipartFile audioFile, MultipartFile lrcFile){
        Set<Tag> tags=new HashSet<>();
        for(String tagName:
            tagNames){
            Tag tag=tagDao.findByName(tagName);
            if(tag==null){
                return null;
            }
            tags.add(tag);
        }

        Set<Singer> singers=new HashSet<>();
        for (Long singerId:
             singerIds) {
            Singer singer=singerDao.findById(singerId).orElse(null);
            if (singer==null){
                return null;
            }
            singers.add(singer);
        }


        Album album=albumDao.findById(albumId).orElse(null);
        if (album==null){
            return null;
        }

        String realName=fileService.uploadAudio(audioFile);
        if(StringUtil.isNullOrEmpty(realName)){
            return null;
        }
        String lrcName= null;
        if (lrcFile!=null) {
            lrcName = fileService.uploadLrc(lrcFile);
            if (lrcName==null){
                return null;
            }
        }
        Song song=new Song(name,tags,singers,album,realName,lrcName);
        return songDao.saveAndFlush(song);
    }
    public boolean delete(long id){
        Song song=songDao.findById(id).orElse(null);
        if (song==null){
            return false;
        }
        song.setExist(false);
        return songDao.saveAndFlush(song)==null?false:true;
    }

    public boolean addTagCount(String realAudioName, User user){
        String key=Constant.PREFIX_USER_TAG+user.getId();
        Set<Tag> tags=getSongByRealAudioName(realAudioName).getTags();
        Map<Object, Object> map=redisUtil.hmget(key);
        for (Tag t:
            tags){
            if(map.containsKey(t.getName())){
                map.put(t.getName(),(Integer)(map.get(t.getName()))+1);
            }else {
                map.put(t.getName(),1);
            }

        }
        return redisUtil.hmset(key,map);
    }


}
