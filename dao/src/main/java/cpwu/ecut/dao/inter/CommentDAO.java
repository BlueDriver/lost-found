package cpwu.ecut.dao.inter;

import cpwu.ecut.dao.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
