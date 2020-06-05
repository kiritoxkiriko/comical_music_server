package wxm.example.comical_music_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wxm.example.comical_music_server.dao.SongDao;
import wxm.example.comical_music_server.dao.SongListDao;
import wxm.example.comical_music_server.dao.TagDao;
import wxm.example.comical_music_server.entity.bbs.User;
import wxm.example.comical_music_server.entity.music.*;
import wxm.example.comical_music_server.entity.music.Image;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public boolean addSongToSongList(long songListId, long songId){
        SongList songList=songListDao.findById(songListId).get();
        Song song=songDao.findById(songId).get();
        if(song==null||songList==null){
            return false;
        }
        Set songs=songList.getSongs();
        if(!songs.add(song)){
            return false;
        }
        songList.setSongs(songs);
        return songListDao.saveAndFlush(songList)==null?false:true;
    }
    public boolean deleteSongFromSongList(long songListId, long songId){
        SongList songList=songListDao.findById(songListId).get();
        Song song=songDao.findById(songId).get();
        if(song==null||songList==null){
            return false;
        }
        Set songs=songList.getSongs();
        if(!songs.remove(song)){
            return false;
        }
        songList.setSongs(songs);
        return songListDao.saveAndFlush(songList)==null?false:true;
    }

    public SongList getSongList(long id){
        return songListDao.findById(id).orElse(null);
    }

    public List<SongList> getSongListByUserId(long userId){
        List<SongList> songLists= songListDao.findAllByCreatorId(userId);
        List<SongList> list=new ArrayList<>();
        for (SongList sl:
             songLists) {
            if (sl.isExist()&&sl.isOpen()){
                list.add(sl);
            }
        }
        return list;
    }

    public Page<SongList> getSongListByNameLike(String name, int size, int page){
        Pageable pageable= PageRequest.of(page,size, Sort.Direction.DESC,"time");
        return songListDao.findAllByNameLikeAndExistAndOpen("%"+name+"%",true,true,pageable);
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

    public boolean delete(long id){
        SongList songList=songListDao.findById(id).orElse(null);
        if (songList==null){
            return false;
        }
        songList.setExist(false);
        return songListDao.saveAndFlush(songList)==null?false:true;
    }
}
