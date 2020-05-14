package wxm.example.comical_music_server.service;

import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wxm.example.comical_music_server.dao.*;
import wxm.example.comical_music_server.entity.music.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
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


    public boolean hasSong(long id){
        return songDao.existsById(id);
    }

    public Song getSong(long id){
        return songDao.findById(id).orElse(null);
    }

    public Page<Song> getSongsByNameLike(String name, int page, int size){
        Pageable pageable= PageRequest.of(page,size, Sort.Direction.DESC,"uploadDate");
        return songDao.findAllByNameIsLike("%"+name+"%",pageable);
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

}
