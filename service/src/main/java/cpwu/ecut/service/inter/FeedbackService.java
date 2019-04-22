package cpwu.ecut.service.inter;

import cpwu.ecut.service.dto.req.FeedbackAddReq;

import javax.servlet.http.HttpSession;

/**
 * lost-found
 * cpwu.ecut.service.inter
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/22 10:59 Monday
 */
public interface FeedbackService {
    /**
     * 新增反馈
     */
    void addFeedback(FeedbackAddReq req, HttpSession session) throws Exception;
}
