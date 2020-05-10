package wxm.example.comical_music_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wxm.example.comical_music_server.dao.GenreDao;
import wxm.example.comical_music_server.dao.LanguageDao;
import wxm.example.comical_music_server.dao.SongDao;
import wxm.example.comical_music_server.entity.music.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    private LanguageDao languageDao;

    @Autowired
    private GenreDao genreDao;

    public boolean hasSong(long id){
        return songDao.existsById(id);
    }

    public Song getSong(long id){
        return songDao.findById(id).orElse(null);
    }

    public Song addSong(@NotEmpty String name, String languageName, String genreName, long singerId, long albumId, @NotNull String realName, String lrcName){
        Language language=languageDao.findLanguageByName(languageName);
        if( language==null){
            return null;
        }

        //TODO 上传歌曲服务
        Genre genre=genreDao.findGenreByName(name);
        if(genre==null){
            return null;
        }

        //Song song=new Song(name);
        return null;
    }
}
