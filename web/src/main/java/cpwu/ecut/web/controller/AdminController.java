package cpwu.ecut.web.controller;

import cpwu.ecut.common.constant.annotation.AuthCheck;
import cpwu.ecut.common.constant.enums.UserKindEnum;
import cpwu.ecut.service.dto.req.CategoryAddReq;
import cpwu.ecut.service.dto.req.UserInfoListReq;
import cpwu.ecut.service.dto.resp.base.ResponseDTO;
import cpwu.ecut.service.inter.CategoryService;
import cpwu.ecut.service.inter.UserService;
import cpwu.ecut.service.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * lost-found
 * cpwu.ecut.web.controller
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/20 14:43 Saturday
 */
@RestController
@Validated
@RequestMapping("/api/v1/admin")
@AuthCheck(level = UserKindEnum.MANAGER)
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 查看用户信息
     */
    @PostMapping("/userInfo")
    public ResponseDTO userInfo(@NotBlank(message = "用户id不能空") @RequestParam String userId) throws Exception {
        return ResponseDTO.successObj("user", userService.userInfo(userId));
    }

    /**
     * 查看用户信息列表
     */
    @PostMapping("/userList")
    public ResponseDTO userInfo(@Valid @RequestBody UserInfoListReq req, HttpSession session) throws Exception {
        return ResponseDTO.successObj("page", userService.userList(req, session));
    }

    /**
     * 冻结用户
     */
    @PostMapping("/freezeUser")
    public ResponseDTO freezeUser(@NotBlank(message = "用户id不能空") @RequestParam String userId) throws Exception {
        userService.freezeUser(userId);
        return ResponseDTO.successObj();
    }

    /**
     * 解冻用户
     */
    @PostMapping("/unfreezeUser")
    public ResponseDTO unfreezeUser(@NotBlank(message = "用户id不能空") @RequestParam String userId) throws Exception {
        userService.unfreezeUser(userId);
        return ResponseDTO.successObj();
    }


    /**
     * 新增类别
     */
    @PostMapping("/addCategory")
    public ResponseDTO addCategory(@Valid @RequestBody CategoryAddReq req, HttpSession session) throws Exception {
        categoryService.addCategory(req, SessionUtils.checkAndGetUser(session));
        return ResponseDTO.successObj();
    }

    /**
     * 删除类别
     */
    @PostMapping("/deleteCategory")
    public ResponseDTO addCategory(@NotBlank(message = "类别名不能为空") @RequestParam String name, HttpSession session)
            throws Exception {
        categoryService.deleteCategory(name, session);
        return ResponseDTO.successObj();
    }


}
