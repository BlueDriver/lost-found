package cpwu.ecut.web.controller.common;

import cpwu.ecut.dao.entity.User;
import cpwu.ecut.service.dto.resp.CategoryListResp;
import cpwu.ecut.service.dto.resp.base.ResponseDTO;
import cpwu.ecut.service.inter.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * lost-found
 * cpwu.ecut.web.controller.common
 * 管理员和用户公用接口
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/11 15:37 Thursday
 * @see cpwu.ecut.web.config.interceptor.LoginInterceptor
 */
@RestController
@Validated
@RequestMapping("/api/v1/common")
public class CommonController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public ResponseDTO categoryList(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<CategoryListResp> list = categoryService.getCategoryList(user.getSchoolId());
        return ResponseDTO.successObj("list", list);
    }

}
