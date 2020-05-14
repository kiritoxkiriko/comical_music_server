package wxm.example.comical_music_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wxm.example.comical_music_server.dao.ImageDao;
import wxm.example.comical_music_server.dao.SingerDao;
import wxm.example.comical_music_server.entity.music.Image;
import wxm.example.comical_music_server.entity.music.Singer;

/**
 * @author Alex Wang
 * @date 2020/05/12
 */
@Service
public class SingerService {
    @Autowired
    private SingerDao singerDao;

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private FileService fileService;

    public Singer addSinger(String name, String introduction, String imageName){
        Image image=imageDao.findById(imageName).orElse(null);
        if(image==null){
            return null;
        }
        Singer singer=new Singer(name,introduction,image);
        return singerDao.saveAndFlush(singer);
    }

    public Singer addSinger(String name, String introduction, MultipartFile imageFile){
        Image image=fileService.uploadImg(imageFile);
        if(image==null){
            return null;
        }
        Singer singer=new Singer(name,introduction,image);
        return singerDao.saveAndFlush(singer);
    }

    public Page<Singer> getAll(int page, int size){
        Pageable pageable= PageRequest.of(page,size);
        return singerDao.findAll(pageable);
    }

    public Page<Singer> getAllByNameLike(String name, int page, int size){
        Pageable pageable= PageRequest.of(page,size);
        return singerDao.findAllByNameIsLike(name, pageable);
    }

    public Singer getBySingerId(long id){
        return singerDao.findById(id).orElse(null);
    }


}
