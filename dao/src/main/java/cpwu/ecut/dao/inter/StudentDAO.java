package cpwu.ecut.dao.inter;

import cpwu.ecut.dao.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * lost-found
 * cpwu.ecut.inter.inter
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/07 15:12 Sunday
 */
@Repository
public interface StudentDAO extends JpaRepository<Student, String> {
}