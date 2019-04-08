package cpwu.ecut.dao.inter;

import cpwu.ecut.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * lost-found
 * cpwu.ecut.inter.inter
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 12:30 Friday
 */
@Repository
public interface UserDAO extends JpaRepository<User, String> {


}