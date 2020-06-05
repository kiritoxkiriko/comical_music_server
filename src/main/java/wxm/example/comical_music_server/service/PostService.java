package wxm.example.comical_music_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import wxm.example.comical_music_server.dao.PostDao;
import wxm.example.comical_music_server.entity.bbs.Board;
import wxm.example.comical_music_server.entity.bbs.Post;
import wxm.example.comical_music_server.entity.music.Image;
import wxm.example.comical_music_server.entity.music.Song;
import wxm.example.comical_music_server.entity.music.SongList;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */
@Service
public class PostService {

    @Autowired
    private PostDao postDao;

    public Page<Post> getAll(int page,int size){
        Pageable pageable=PageRequest.of(page,size, Sort.Direction.DESC, "time");
        return postDao.findAllByExist(pageable, true);
    }

    public Post getPost(long id){
        return postDao.findById(id).orElse(null);
    }

    public Page<Post> getByBoardId(long boardId, int page,int size){
        Pageable pageable=PageRequest.of(page,size, Sort.Direction.DESC, "time");
        return postDao.findAllByPostedBoardIdAndExist(boardId,true,pageable);
    }

    public Post add(String content, Set<Song> sharedSongs, Set<Image> sharedImages, Set<SongList> sharedSongLists, @NotNull Board postedBoard, int type){
        Post post=new Post(content, sharedSongs, sharedImages, sharedSongLists, postedBoard);
        post.setType(type);
        return postDao.saveAndFlush(post);
    }

    public boolean delete(long id){
        Post post=postDao.findById(id).orElse(null);
        if (post==null){
            return false;
        }
        post.setExist(false);
        return postDao.saveAndFlush(post)==null?false:true;
    }
}
