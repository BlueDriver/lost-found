package cpwu.ecut.service.inter.impl;

import cpwu.ecut.common.constant.enums.FeedbackKindEnum;
import cpwu.ecut.common.constant.enums.MessageStatusEnum;
import cpwu.ecut.common.constant.enums.RecordStatusEnum;
import cpwu.ecut.common.utils.CommonUtils;
import cpwu.ecut.dao.entity.Feedback;
import cpwu.ecut.dao.entity.User;
import cpwu.ecut.dao.inter.FeedbackDAO;
import cpwu.ecut.service.dto.req.FeedbackAddReq;
import cpwu.ecut.service.inter.FeedbackService;
import cpwu.ecut.service.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * lost-found
 * cpwu.ecut.service.inter.impl
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/22 10:59 Monday
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackDAO feedbackDAO;

    /**
     * 新增反馈
     */
    @Override
    public void addFeedback(FeedbackAddReq req, HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        Feedback feedback = new Feedback();
        feedback.setId(CommonUtils.getUUID())
                .setKind(FeedbackKindEnum.USAGE.getCode())
                .setUserId(user.getId())
                .setSubject(req.getSubject())
                .setContent(req.getContent())
                .setCreateTime(new Date())
                .setStatus(MessageStatusEnum.UNREAD.getCode())
                .setRecordStatus(RecordStatusEnum.EXISTS.getCode());
        feedbackDAO.saveAndFlush(feedback);
    }
}
