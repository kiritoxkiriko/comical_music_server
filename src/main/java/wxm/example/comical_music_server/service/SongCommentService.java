package wxm.example.comical_music_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wxm.example.comical_music_server.dao.SongCommentDao;
import wxm.example.comical_music_server.dao.SongDao;
import wxm.example.comical_music_server.entity.music.Song;
import wxm.example.comical_music_server.entity.music.SongComment;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Alex Wang
 * @date 2020/05/13
 */
@Service
public class SongCommentService {

    @Autowired
    private SongCommentDao songCommentDao;

    @Autowired
    private SongDao songDao;

    public Page<SongComment> getSongCommentsBySongId(long songId, int page, int size){
        Pageable pageable= PageRequest.of(page,size, Sort.Direction.DESC, "time");
        return songCommentDao.findAllBySongId(songId, pageable);
    }

    public SongComment getSongComment(long id){
        return songCommentDao.findById(id).orElse(null);
    }

    public SongComment addSongComment(long songId, @NotEmpty String content, Long commentId){
        if(!songDao.existsById(songId)){
            return null;
        }

        Song song=new Song();
        song.setId(songId);

        SongComment replyTo=null;

        if (commentId!=null){
            replyTo=songCommentDao.findById(commentId).orElse(null);
        }

        SongComment songComment=new SongComment(content,replyTo,song);
        return songCommentDao.saveAndFlush(songComment);
    }

    public boolean deleteById(long id){
        try {
            songCommentDao.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int count(long songId){
        return songCommentDao.countBySongId(songId);
    }

    public List<Song> addCountToSongs(List<Song> songs){
        for (Song s:
             songs) {
            int count=count(s.getId());
            s.setCommentCount(count);
        }
        return songs;
    }
    public Song addCountToSong(Song s){

        int count=count(s.getId());
        s.setCommentCount(count);

        return s;
    }
}
