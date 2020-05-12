package wxm.example.comical_music_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wxm.example.comical_music_server.constant.Constant;
import wxm.example.comical_music_server.dao.ImageDao;
import wxm.example.comical_music_server.entity.music.Image;
import wxm.example.comical_music_server.utility.FileUtil;

import javax.xml.xpath.XPath;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.UUID;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */
@Service
public class FileService {
    //TODO 可将文件md5存入缓存，避免重复上传

    @Autowired
    private ImageDao imageDao;
    public Image uploadImg(MultipartFile file){
        if(file.isEmpty()){
            return null;
        }

        String fileName = file.getOriginalFilename();
        if(!FileUtil.isImage(fileName)){
            return  null;
        }

        File realFile=FileUtil.upload(file,Constant.RESOURCE_PATH+Constant.IMG_PATH, UUID.randomUUID().toString().replace("-","")+"."+FileUtil.getSuffix(fileName));
        if (realFile==null){
            return null;
        }
        Image image=new Image(realFile.getName());
        image=imageDao.saveAndFlush(image);
        return image;
    }


    public String uploadAudio(MultipartFile file){
        if(file.isEmpty()){
            return null;
        }

        String fileName = file.getOriginalFilename();
        if(!FileUtil.isAudio(fileName)){
            return  "";
        }

        File realFile=FileUtil.upload(file,Constant.RESOURCE_PATH+Constant.AUDIO_PATH, UUID.randomUUID().toString().replace("-","")+"."+FileUtil.getSuffix(fileName));
        if (realFile==null){
            return null;
        }

        return realFile.getName();
    }

    public String uploadLrc(MultipartFile file){
        if(file.isEmpty()){
            return null;
        }

        String fileName = file.getOriginalFilename();
        if(!FileUtil.isLrc(fileName)){
            return  "";
        }

        File realFile=FileUtil.upload(file,Constant.RESOURCE_PATH+Constant.LRC_PATH, UUID.randomUUID().toString().replace("-","")+"."+FileUtil.getSuffix(fileName));
        if (realFile==null){
            return null;
        }

        return realFile.getName();
    }


    public Resource loadFileAsResource(String path) throws IOException{
        String realPath= Constant.RESOURCE_PATH+path;
        try {
            Resource resource = new FileSystemResource(new File(realPath));
            if(resource.exists()) {
                return resource;
            } else {
                throw new IOException("File not found "+ realPath);
            }
        } catch (Exception e){
            throw new IOException("File not found " + realPath);
        }
    }
}
