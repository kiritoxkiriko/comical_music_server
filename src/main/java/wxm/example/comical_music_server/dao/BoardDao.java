package wxm.example.comical_music_server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wxm.example.comical_music_server.entity.bbs.Board;

import java.util.List;

/**
 * @author Alex Wang
 * @date 2020/05/09
 */
@Repository
public interface BoardDao extends JpaRepository<Board,Long> {
    boolean deleteByName(String name);
}
