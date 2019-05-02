package cpwu.ecut.service.inter.impl;

import cpwu.ecut.common.constant.enums.ErrorEnum;
import cpwu.ecut.common.constant.enums.FeedbackKindEnum;
import cpwu.ecut.common.constant.enums.MessageStatusEnum;
import cpwu.ecut.common.constant.enums.RecordStatusEnum;
import cpwu.ecut.common.utils.CommonUtils;
import cpwu.ecut.common.utils.ExceptionUtils;
import cpwu.ecut.dao.entity.Feedback;
import cpwu.ecut.dao.entity.User;
import cpwu.ecut.dao.inter.FeedbackDAO;
import cpwu.ecut.dao.inter.UserDAO;
import cpwu.ecut.service.dto.req.FeedbackAddReq;
import cpwu.ecut.service.dto.req.FeedbackReplyReq;
import cpwu.ecut.service.inter.FeedbackService;
import cpwu.ecut.service.utils.MailSenderService;
import cpwu.ecut.service.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

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

    @Value("${system.email}")
    private String systemEmail;

    @Value("${app.chinese.name}")
    private String appName;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private UserDAO userDAO;

    /**
     * 新增反馈
     */
    @Override
    public void addFeedback(FeedbackAddReq req, HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        Feedback feedback = new Feedback();
        feedback.setId(CommonUtils.getUUID())
                .setKind(FeedbackKindEnum.USAGE.getCode())
                .setSchoolId(user.getSchoolId())
                .setUserId(user.getId())
                .setUsername(user.getUsername())
                .setRealName(user.getRealName())
                .setSubject(req.getSubject())
                .setContent(req.getContent())
                .setCreateTime(new Date())
                .setStatus(MessageStatusEnum.UNREAD.getCode())
                .setRecordStatus(RecordStatusEnum.EXISTS.getCode());
        feedbackDAO.saveAndFlush(feedback);
    }

    /**
     * 查看反馈列表
     */
    @Override
    public List<Feedback> listFeedback(HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        String schoolId = user.getSchoolId();
        return feedbackDAO.listFeedback(schoolId);
    }

    /**
     * 回复反馈
     */
    @Override
    public void replyFeedback(FeedbackReplyReq req, HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        Optional<Feedback> feedbackOptional = feedbackDAO.findById(req.getId());
        if (!feedbackOptional.isPresent()) {//不存在
            throw ExceptionUtils.createException(ErrorEnum.FEEDBACK_NOT_EXISTS, req.getId());
        }
        Feedback feedback = feedbackOptional.get();
        feedback.setHandlerId(user.getId())
                .setHandlerName(user.getRealName())
                .setHandlerEmail(user.getEmail())
                .setHandlerTime(new Date())
                .setStatus(MessageStatusEnum.HAS_READ.getCode())
                .setAnswer(req.getContent().trim());
        feedbackDAO.saveAndFlush(feedback);

        Optional<User> userOptional = userDAO.findById(feedback.getUserId());
        if (userOptional.isPresent()) {
            //邮件提醒回复
            sendReplyEmail(userOptional.get().getEmail(), feedback, user);
        }
    }

    /**
     * 邮件提醒回复
     */
    private void sendReplyEmail(String email, Feedback feedback, User handler) throws IOException, MessagingException {
        Map<String, Object> param = new HashMap<>();
        param.put("realName", feedback.getRealName());
        param.put("createTime", CommonUtils.getFormatDateTime(feedback.getCreateTime()));
        param.put("subject", feedback.getSubject());
        param.put("content", feedback.getContent());
        param.put("handlerName", handler.getRealName());
        param.put("handlerEmail", handler.getEmail());
        param.put("answer", feedback.getAnswer());
        param.put("handlerTime", CommonUtils.getFormatDateTime(feedback.getHandlerTime()));
        param.put("appName", appName);
        mailSenderService.sendTemplateMessage(email, param,
                "templates/mail/feedbackReply.html", "用户反馈通知",
                systemEmail, appName);
    }

    /**
     * 反馈标记为已读
     */
    @Override
    public void markFeedback(String id, HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        Optional<Feedback> feedbackOptional = feedbackDAO.findById(id);
        if (!feedbackOptional.isPresent()) {//不存在
            throw ExceptionUtils.createException(ErrorEnum.FEEDBACK_NOT_EXISTS, id);
        }
        Feedback feedback = feedbackOptional.get();
        feedback.setHandlerId(user.getId())
                .setHandlerName(user.getRealName())
                .setHandlerEmail(user.getEmail())
                .setHandlerTime(new Date())
                .setStatus(MessageStatusEnum.HAS_READ.getCode());
        feedbackDAO.saveAndFlush(feedback);
    }

    /**
     * 删除反馈
     */
    @Override
    public void deleteFeedback(String id, HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        Optional<Feedback> feedbackOptional = feedbackDAO.findById(id);
        if (!feedbackOptional.isPresent()) {//不存在
            throw ExceptionUtils.createException(ErrorEnum.FEEDBACK_NOT_EXISTS, id);
        }
        Feedback feedback = feedbackOptional.get();
        feedback.setRecordStatus(RecordStatusEnum.DELETED.getCode())
                .setHandlerId(user.getId())
                .setHandlerName(user.getRealName())
                .setHandlerEmail(user.getEmail())
                .setHandlerTime(new Date())
                .setStatus(MessageStatusEnum.HAS_READ.getCode());
        feedbackDAO.saveAndFlush(feedback);
    }
}
