package cpwu.ecut.dao.inter;

import cpwu.ecut.dao.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * lost-found
 * cpwu.ecut.dao.inter
 * 公告DAO
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 09:41 Monday
 */
@Repository
public interface NoticeDAO extends JpaRepository<Notice, String> {
    /**
     * 查询公告列表
     */
    @Query("select n from Notice  n where n.status=1 " +
            "and n.recordStatus = 1 order by n.createTime")
    List<Notice> listNotice();
}
