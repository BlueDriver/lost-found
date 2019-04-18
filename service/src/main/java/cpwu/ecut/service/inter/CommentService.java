package cpwu.ecut.service.inter;

import cpwu.ecut.service.dto.req.CommentAddReq;
import cpwu.ecut.service.dto.resp.PublicationComment;
import cpwu.ecut.service.dto.resp.UserMessage;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * lost-found
 * cpwu.ecut.service.inter
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/17 11:15 Wednesday
 */
public interface CommentService {
    /**
     * 查询评论列表
     */
    List<PublicationComment> listComment(String id);

    /**
     * 发布评论
     */
    void commentAdd(CommentAddReq req, HttpSession session) throws Exception;


    /**
     *  用户消息（评论）
     */
    List<UserMessage> listMessage(HttpSession session) throws Exception;

}
