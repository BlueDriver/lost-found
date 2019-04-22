package cpwu.ecut.service.inter.impl;

import cpwu.ecut.common.constant.enums.ErrorEnum;
import cpwu.ecut.common.constant.enums.LevelEnum;
import cpwu.ecut.common.constant.enums.RecordStatusEnum;
import cpwu.ecut.common.utils.ExceptionUtils;
import cpwu.ecut.common.utils.FieldUtils;
import cpwu.ecut.dao.entity.Category;
import cpwu.ecut.dao.entity.CategoryKey;
import cpwu.ecut.dao.entity.User;
import cpwu.ecut.dao.inter.CategoryDAO;
import cpwu.ecut.dao.inter.LostFoundDAO;
import cpwu.ecut.service.dto.req.CategoryAddReq;
import cpwu.ecut.service.dto.resp.CategoryListResp;
import cpwu.ecut.service.inter.CategoryService;
import cpwu.ecut.service.utils.SessionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * lost-found
 * cpwu.ecut.service.inter.impl
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/11 14:41 Thursday
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private LostFoundDAO lostFoundDAO;

    /**
     * 增加类别
     */
    @Override
    public void addCategory(CategoryAddReq req, User user) throws Exception {
        //set key
        CategoryKey key = new CategoryKey();
        key.setName(req.getName().trim())
                .setLevel(LevelEnum.SCHOOL.getCode())//default
                .setTargetId(user.getSchoolId());

        Optional<Category> categoryOptional = categoryDAO.findById(key);
        if (categoryOptional.isPresent()) {
            throw ExceptionUtils.createException(ErrorEnum.CATEGORY_EXISTS, req.getName());
        }
        //save
        Category category = new Category();
        BeanUtils.copyProperties(key, category);
        category.setAbout(req.getAbout())
                .setCreateTime(new Date())
                .setCreatorId(user.getId())
                .setImage(null)//no need
                .setRecordStatus(RecordStatusEnum.EXISTS.getCode());
        categoryDAO.saveAndFlush(category);
    }

    /**
     * 类别列表
     */
    @Override
    public List<CategoryListResp> getCategoryList(String targetId) {
        Category categoryEx = new Category();
        categoryEx.setTargetId(targetId)
                .setRecordStatus(RecordStatusEnum.EXISTS.getCode());
        List<Category> categoryList = categoryDAO.findAll(Example.of(categoryEx),
                new Sort(Sort.Direction.DESC, "createTime"));

        Set<String> idSet = new HashSet<>(categoryList.size());
        categoryList.forEach(item -> idSet.add(item.getName()));

        List<String> idList = lostFoundDAO.findAllByCategoryIdInAAndRecordStatusEquals(idSet,
                RecordStatusEnum.EXISTS.getCode());

        Map<String, Long> idMap = new HashMap<>(categoryList.size());
        for (String id : idList) {
            if (idMap.containsKey(id)) {
                idMap.put(id, idMap.get(id) + 1);
            } else {
                idMap.put(id, 1L);
            }
        }

        List<CategoryListResp> resp = FieldUtils.copyProperties(categoryList, CategoryListResp.class);
        for (CategoryListResp c : resp) {
            c.setCount(
                    idMap.get(c.getName()) == null ? 0L : idMap.get(c.getName()));
        }

        return resp;
    }

    /**
     * 删除类别
     */
    @Override
    public void deleteCategory(String name, HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        Category category = categoryDAO.findByNameEqualsAndTargetIdEquals(name, user.getSchoolId());
        if (category == null) {
            throw ExceptionUtils.createException(ErrorEnum.CATEGORY_NOT_EXISTS, name);
        }
        categoryDAO.delete(category);
    }
}
