package cpwu.ecut.dao.inter;

import cpwu.ecut.dao.entity.Category;
import cpwu.ecut.dao.entity.CategoryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * lost-found
 * cpwu.ecut.dao.inter
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/11 14:20 Thursday
 */
@Repository
public interface CategoryDAO extends JpaRepository<Category, CategoryKey> {

    Category findByNameEquals(String name);
}
