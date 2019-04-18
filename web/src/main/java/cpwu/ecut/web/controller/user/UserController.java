package cpwu.ecut.web.controller.user;

import cpwu.ecut.service.dto.req.CommentAddReq;
import cpwu.ecut.service.dto.req.PublicationAddReq;
import cpwu.ecut.service.dto.req.PublicationListReq;
import cpwu.ecut.service.dto.req.PublicationRemoveReq;
import cpwu.ecut.service.dto.resp.PublicationPageResp;
import cpwu.ecut.service.dto.resp.base.ResponseDTO;
import cpwu.ecut.service.inter.CommentService;
import cpwu.ecut.service.inter.LostFoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * lost-found
 * cpwu.ecut.web.controller
 * 用户相关
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/06 22:03 Saturday
 */

@RestController
@Validated
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private LostFoundService lostFoundService;

    @Autowired
    private CommentService commentService;

    /**
     * 发布启事
     */
    @PostMapping("/pub")
    public ResponseDTO publicationAdd(@Valid @RequestBody PublicationAddReq req, HttpSession session) throws Exception {
        lostFoundService.add(req, session);
        return ResponseDTO.successObj();
    }

    /**
     * 分页查询启事
     */
    @PostMapping("/page")
    public ResponseDTO publicationPage(@Valid @RequestBody PublicationListReq req, HttpSession session) throws Exception {
        PublicationPageResp resp = lostFoundService.page(req, session);
        return ResponseDTO.successObj("page", resp);
    }

    /**
     * 启事详情
     */
    @PostMapping("/detail")
    public ResponseDTO publicationDetail(@NotBlank(message = "启事id不能为空") @RequestParam String id) throws Exception {
        return ResponseDTO.successObj("item", lostFoundService.detail(id));
    }

    /**
     * 启事评论列表
     */
    @PostMapping("/comments")
    public ResponseDTO commentList(@NotBlank(message = "启事id不能为空") @RequestParam String id) {
        return ResponseDTO.successObj("comments", commentService.listComment(id));
    }

    /**
     * 发布评论
     */
    @PostMapping("/comment")
    public ResponseDTO commentAdd(@Valid @RequestBody CommentAddReq req, HttpSession session) throws Exception {
        commentService.commentAdd(req, session);
        return ResponseDTO.successObj();
    }

    /**
     * 查寻用户消息（与我相关的评论）
     */
    @PostMapping("/messages")
    public ResponseDTO messages(HttpSession session) throws Exception {
        return ResponseDTO.successObj("list", commentService.listMessage(session));
    }

    /**
     * 删除启事
     */
    @PostMapping("/removeLost")
    public ResponseDTO removeLostFound(@Valid @RequestBody PublicationRemoveReq req, HttpSession session) throws Exception {
        lostFoundService.removeLostFound(req.getIdList(), session);
        return ResponseDTO.successObj();
    }

    /**
     * 删除评论
     */
    @PostMapping("/removeComment")
    public ResponseDTO removeComment(@Valid @RequestBody PublicationRemoveReq req, HttpSession session) throws Exception {
        commentService.removeComment(req.getIdList(), session);
        return ResponseDTO.successObj();
    }

}
