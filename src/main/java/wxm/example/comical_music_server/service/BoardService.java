package wxm.example.comical_music_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wxm.example.comical_music_server.dao.BoardDao;
import wxm.example.comical_music_server.entity.bbs.Board;

import java.util.List;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */
@Service
public class BoardService {

    @Autowired
    private BoardDao boardDao;

    public List<Board> getAll(){
        return boardDao.findAll(Sort.by(Sort.Order.asc("Id")));

    }

    public Board addBoard(String name){
        Board board=new Board(name);
        try {
            board=boardDao.saveAndFlush(board);
            return board;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteBoard(long id){
        try {
            boardDao.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteBoard(String name){
        return boardDao.deleteByName(name);
    }

    public boolean hasBoard(long id){
        return boardDao.existsById(id);
    }
}
