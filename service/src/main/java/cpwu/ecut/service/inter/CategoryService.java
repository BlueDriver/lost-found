package cpwu.ecut.service.inter;

import cpwu.ecut.dao.entity.User;
import cpwu.ecut.service.dto.req.CategoryAddReq;
import cpwu.ecut.service.dto.resp.CategoryListResp;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * lost-found
 * cpwu.ecut.service.inter.impl
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/11 14:32 Thursday
 */
public interface CategoryService {
    /**
     * 新增类别
     */
    void addCategory(CategoryAddReq req, User user) throws Exception;

    /**
     * 查询类别列表
     */
    List<CategoryListResp> getCategoryList(String targetId);

    /**
     * 删除类别
     */
    void deleteCategory(String name, HttpSession session) throws Exception;
}
