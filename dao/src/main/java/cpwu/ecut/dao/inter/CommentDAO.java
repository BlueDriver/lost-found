package cpwu.ecut.dao.inter;

import cpwu.ecut.dao.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * lost-found
 * cpwu.ecut.dao.inter
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 10:53 Monday
 */
@Repository
public interface CommentDAO extends JpaRepository<Comment, String> {
    List<Comment> findAllByLostFoundIdIn(Iterable<String> list);

    /**
     * 根据启事id查询相关的评论的id（用户统计评论数量）
     */
    @Query("select c.lostFoundId from Comment c where c.lostFoundId in(:idList) and c.recordStatus = :status")
    List<String> findCommentIdInAndRecordStatusEquals(@Param("idList") Set<String> lostFoundIdList,
                                                      @Param("status") Integer status);
}
