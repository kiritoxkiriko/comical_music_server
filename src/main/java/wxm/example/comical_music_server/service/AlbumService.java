package wxm.example.comical_music_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wxm.example.comical_music_server.dao.AlbumDao;
import wxm.example.comical_music_server.dao.ImageDao;
import wxm.example.comical_music_server.dao.SingerDao;
import wxm.example.comical_music_server.entity.music.Album;
import wxm.example.comical_music_server.entity.music.Image;
import wxm.example.comical_music_server.entity.music.Singer;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Alex Wang
 * @date 2020/05/12
 */
@Service
public class AlbumService {

    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private SingerDao singerDao;

    @Autowired
    private FileService fileService;

    public Album addAlbum(@NotEmpty String name, String imageName, long singerId, Integer year){
        Image image=imageDao.findById(imageName).orElse(null);
        if (image==null){
            return null;
        }
        Singer singer=singerDao.findById(singerId).orElse(null);
        if (singer==null){
            return null;
        }
        Album album=new Album(name, image, singer, year);
        return albumDao.saveAndFlush(album);
    }

    public Album addAlbum(@NotEmpty String name, MultipartFile imageFile, long singerId, Integer year){
        Image image=fileService.uploadImg(imageFile);
        if (image==null){
            return null;
        }
        Singer singer=singerDao.findById(singerId).orElse(null);
        if (singer==null){
            return null;
        }
        Album album=new Album(name, image, singer, year);
        return albumDao.saveAndFlush(album);
    }

    public Page<Album> getAll(int page, int size){
        Pageable pageable= PageRequest.of(page,size, Sort.Direction.DESC,"year");
        return albumDao.findAll(pageable);
    }

    public Album getById(long id){
        return albumDao.findById(id).orElse(null);
    }

    public Page<Album> getByNameLike(String name, int page, int size){
        Pageable pageable= PageRequest.of(page,size,Sort.Direction.DESC,"year");
        return albumDao.findAllByNameIsLike("%"+name+"%", pageable);
    }
}
