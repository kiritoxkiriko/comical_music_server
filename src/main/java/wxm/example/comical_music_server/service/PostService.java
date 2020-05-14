package wxm.example.comical_music_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Pageable pageable=PageRequest.of(page,size, Sort.Direction.DESC, "date");
        return postDao.findAll(pageable);
    }

    public Post getPost(long id){
        return postDao.findById(id).orElse(null);
    }

    public Page<Post> getByBoardId(long boardId, int page,int size){
        Pageable pageable=PageRequest.of(page,size, Sort.Direction.DESC, "date");
        return postDao.findAllByPostedBoardId(boardId,pageable);
    }

    public Post add(String content, Set<Song> sharedSongs, Set<Image> sharedImages, Set<SongList> sharedSongLists, @NotNull Board postedBoard){
        Post post=new Post(content, sharedSongs, sharedImages, sharedSongLists, postedBoard);
        return postDao.saveAndFlush(post);
    }
}
