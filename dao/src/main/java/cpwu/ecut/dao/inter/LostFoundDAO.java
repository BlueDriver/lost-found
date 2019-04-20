package cpwu.ecut.dao.inter;

import cpwu.ecut.dao.entity.LostFound;
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
 * @date 2019/04/15 10:41 Monday
 */
@Repository
public interface LostFoundDAO extends JpaRepository<LostFound, String> {
    @Query("select l.categoryId from LostFound  l where l.categoryId in(:idSet) and l.recordStatus = :recordStatus")
    List<String> findAllByCategoryIdInAAndRecordStatusEquals(@Param("idSet") Set<String> idSet,
                                                             @Param("recordStatus") Integer status);

}
