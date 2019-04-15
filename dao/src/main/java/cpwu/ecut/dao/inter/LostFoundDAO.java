package cpwu.ecut.dao.inter;

import cpwu.ecut.dao.entity.LostFound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
