package cpwu.ecut.web.controller;

import cpwu.ecut.common.constant.annotation.AuthCheck;
import cpwu.ecut.common.constant.enums.UserKindEnum;
import cpwu.ecut.service.dto.req.CategoryAddReq;
import cpwu.ecut.service.dto.req.FeedbackReplyReq;
import cpwu.ecut.service.dto.req.NoticeAddReq;
import cpwu.ecut.service.dto.req.UserInfoListReq;
import cpwu.ecut.service.dto.resp.base.ResponseDTO;
import cpwu.ecut.service.inter.CategoryService;
import cpwu.ecut.service.inter.FeedbackService;
import cpwu.ecut.service.inter.NoticeService;
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

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 新增通知
     */
    @PostMapping("/noticeAdd")
    public ResponseDTO addNotice(@RequestBody @Valid NoticeAddReq req, HttpSession session) throws Exception {
        noticeService.noticeAdd(req, session);
        return ResponseDTO.successObj();
    }

    /**
     * 删除通知
     */
    @PostMapping("/noticeDelete")
    public ResponseDTO deleteNotice(@NotBlank(message = "通知id不能为空") @RequestParam String id) throws Exception {
        noticeService.noticeDelete(id);
        return ResponseDTO.successObj();
    }

    /**
     * 通知置顶切换
     */
    @PostMapping("/noticeSwitch")
    public ResponseDTO switchNotice(@NotBlank(message = "通知id不能为空") @RequestParam String id) throws Exception {
        noticeService.noticeSwitch(id);
        return ResponseDTO.successObj();
    }


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
     * 用户设置为管理员
     */
    @PostMapping("/setAsAdmin")
    public ResponseDTO setAsAdmin(@NotBlank(message = "用户id不能空") @RequestParam String userId) throws Exception {
        userService.setAsAdmin(userId);
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


    /**
     * 反馈列表查询
     */
    @PostMapping("/listFeedback")
    public ResponseDTO listFeedback(HttpSession session) throws Exception {
        return ResponseDTO.successObj("list", feedbackService.listFeedback(session));
    }

    /**
     * 回复反馈
     */
    @PostMapping("/replyFeedback")
    public ResponseDTO replyFeedback(@Valid @RequestBody FeedbackReplyReq req, HttpSession session) throws Exception {
        feedbackService.replyFeedback(req, session);
        return ResponseDTO.successObj();
    }

    /**
     * 反馈标记已读
     */
    @PostMapping("/markFeedback")
    public ResponseDTO markFeedback(@NotBlank(message = "反馈id不能为空") @RequestParam String id, HttpSession session)
            throws Exception {
        feedbackService.markFeedback(id, session);
        return ResponseDTO.successObj();
    }

    /**
     * 删除反馈
     */
    @PostMapping("/deleteFeedback")
    public ResponseDTO deleteFeedback(@NotBlank(message = "反馈id不能为空") @RequestParam String id, HttpSession session)
            throws Exception {
        feedbackService.deleteFeedback(id, session);
        return ResponseDTO.successObj();
    }

}
