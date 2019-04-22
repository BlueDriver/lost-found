package cpwu.ecut.dao.inter;

import cpwu.ecut.dao.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * lost-found
 * cpwu.ecut.dao.inter
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 12:01 Monday
 */
@Repository
public interface FeedbackDAO extends JpaRepository<Feedback, String> {
    @Query("select f from Feedback f where f.schoolId = :schoolId " +
            "and f.recordStatus = 1 order by f.status asc")
    List<Feedback> listFeedback(@Param("schoolId") String schoolId);
}
