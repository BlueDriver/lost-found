package cpwu.ecut.web.controller;

import cpwu.ecut.common.constant.annotation.AuthCheck;
import cpwu.ecut.common.constant.enums.UserKindEnum;
import cpwu.ecut.dao.entity.User;
import cpwu.ecut.service.dto.resp.CategoryListResp;
import cpwu.ecut.service.dto.resp.base.ResponseDTO;
import cpwu.ecut.service.inter.CategoryService;
import cpwu.ecut.service.inter.NoticeService;
import cpwu.ecut.service.utils.SessionUtils;
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
 */
@RestController
@Validated
@RequestMapping("/api/v1/common")
public class CommonController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private NoticeService noticeService;

    /**
     * 获得物品类别列表
     */
    @PostMapping("/category")
    @AuthCheck(level = UserKindEnum.STUDENT)
    public ResponseDTO categoryList(HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        List<CategoryListResp> list = categoryService.getCategoryList(user.getSchoolId());
        return ResponseDTO.successObj("list", list);
    }

    /**
     * 获取通知列表
     */
    @PostMapping("/noticeList")
    @AuthCheck(level = UserKindEnum.STUDENT)
    public ResponseDTO noticeList(HttpSession session) throws Exception {
        return ResponseDTO.successObj("list", noticeService.noticeList(session));
    }

}
