package cpwu.ecut.service.inter.impl;

import cpwu.ecut.common.constant.enums.ErrorEnum;
import cpwu.ecut.common.constant.enums.RecordStatusEnum;
import cpwu.ecut.common.constant.enums.UserKindEnum;
import cpwu.ecut.common.utils.CommonUtils;
import cpwu.ecut.common.utils.ExceptionUtils;
import cpwu.ecut.dao.entity.Comment;
import cpwu.ecut.dao.entity.LostFound;
import cpwu.ecut.dao.entity.User;
import cpwu.ecut.dao.inter.CommentDAO;
import cpwu.ecut.dao.inter.LostFoundDAO;
import cpwu.ecut.dao.inter.UserDAO;
import cpwu.ecut.service.dto.req.CommentAddReq;
import cpwu.ecut.service.dto.resp.PublicationComment;
import cpwu.ecut.service.dto.resp.UserMessage;
import cpwu.ecut.service.inter.CommentService;
import cpwu.ecut.service.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * lost-found
 * cpwu.ecut.service.inter.impl
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/17 13:46 Wednesday
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private LostFoundDAO lostFoundDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<PublicationComment> listComment(String id) {
        Comment commentEx = new Comment();
        commentEx.setLostFoundId(id)
                .setRecordStatus(RecordStatusEnum.EXISTS.getCode());
        List<Comment> commentList = commentDAO.findAll(Example.of(commentEx),
                new Sort(Sort.Direction.DESC, "createTime"));

        List<PublicationComment> list = new ArrayList<>(commentList.size());
        PublicationComment publicationComment;
        for (Comment comment : commentList) {
            publicationComment = new PublicationComment();
            publicationComment.setId(comment.getId())
                    .setTime(comment.getCreateTime())
                    .setContent(comment.getContent());
            Optional<User> userOptional1 = userDAO.findById(comment.getUserId());
            if (userOptional1.isPresent()) {
                User user = userOptional1.get();
                publicationComment.setUsername(user.getUsername())
                        .setIcon(user.getIcon());
            }
            list.add(publicationComment);
        }
        return list;
    }

    @Override
    public void commentAdd(CommentAddReq req, HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        if (!lostFoundDAO.existsById(req.getTargetId())) {
            throw ExceptionUtils.createException(ErrorEnum.LOST_FOUND_NOT_EXISTS, req.getTargetId());
        }
        Comment comment = new Comment();
        comment.setId(CommonUtils.getUUID())
                .setLostFoundId(req.getTargetId())
                .setUserId(user.getId())
                .setContent(req.getContent().trim())
                .setCreateTime(new Date())
                .setRecordStatus(RecordStatusEnum.EXISTS.getCode());

        commentDAO.saveAndFlush(comment);
    }

    @Override
    public List<UserMessage> listMessage(HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);

        LostFound lostFoundEx = new LostFound();
        lostFoundEx.setUserId(user.getId())
                .setRecordStatus(RecordStatusEnum.EXISTS.getCode());
        List<LostFound> lostFoundList = lostFoundDAO.findAll(Example.of(lostFoundEx));

        List<String> lostIdList = new ArrayList<>(lostFoundList.size());
        lostFoundList.forEach(item -> lostIdList.add(item.getId()));
        List<Comment> commentList = commentDAO.findAllByLostFoundIdIn(lostIdList);//查出来的包括状态为已删除的

        List<String> userIdList = new ArrayList<>(commentList.size());
        commentList.forEach(item -> userIdList.add(item.getUserId()));
        List<User> userList = userDAO.findAllById(userIdList);
        userList.stream().collect(Collectors.toMap(User::getId, u -> u));

        Collections.sort(commentList, (c1, c2) -> {//评论按时间降序
            if (c1.getCreateTime().after(c2.getCreateTime())) {
                return -1;
            }
            return 1;
        });

        Map<String, LostFound> lostFoundMap = toLostFoundMap(lostFoundList);
        Map<String, User> userMap = toUserMap(userList);
        LostFound l;
        User u;
        List<UserMessage> list = new ArrayList<>(commentList.size());
        UserMessage message;
        for (Comment comment : commentList) {
            if (RecordStatusEnum.DELETED.equals(comment.getRecordStatus())) {
                continue;
            }
            l = lostFoundMap.get(comment.getLostFoundId());
            u = userMap.get(comment.getUserId());
            message = new UserMessage();
            message.setId(comment.getId())
                    .setUserId(u.getId())
                    .setIcon(u.getIcon())
                    .setUsername(u.getUsername())
                    .setTime(comment.getCreateTime())
                    .setTitle(l.getTitle())
                    .setContent(comment.getContent());
            list.add(message);
        }

        return list;
    }


    private Map<String, LostFound> toLostFoundMap(List<LostFound> list) {
        Map<String, LostFound> map = new HashMap(list.size());
        list.forEach(item -> map.put(item.getId(), item));
        return map;
    }

    private Map<String, User> toUserMap(List<User> list) {
        Map<String, User> map = new HashMap(list.size());
        list.forEach(item -> map.put(item.getId(), item));
        return map;
    }

    @Override
    public void removeComment(List<String> idList, HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        List<Comment> commentList = commentDAO.findAllById(idList);
        if (CollectionUtils.isEmpty(commentList)) {
            return;
        }
        //管理员
        if (UserKindEnum.MANAGER.equals(user.getKind())) {
            commentList.forEach(item -> item.setRecordStatus(RecordStatusEnum.DELETED.getCode()));
            commentDAO.saveAll(commentList);
            return;
        }
        //学生，只能操作自己的评论，以及是自己发布的招领的评论
        List<Comment> mine = new ArrayList<>(commentList.size());

        Set<String> lostIdSet = new HashSet<>(commentList.size());
        commentList.forEach(item -> lostIdSet.add(item.getLostFoundId()));
        List<LostFound> lostFoundList = lostFoundDAO.findAllById(lostIdSet);

        Map<String, LostFound> lostFoundMap = new HashMap<>(lostFoundList.size());
        lostFoundList.forEach(item -> lostFoundMap.put(item.getId(), item));

        commentList.forEach(item -> {
            if (item.getUserId().equals(user.getId()) || //自己发布的评论
                    lostFoundMap.get(item.getLostFoundId()) != null &&  //是自己的招领
                            lostFoundMap.get(item.getLostFoundId()).getUserId().equals(user.getId())) {
                mine.add(item.setRecordStatus(RecordStatusEnum.DELETED.getCode()));
            }
        });
        if (CollectionUtils.isEmpty(mine)) {
            return;
        }
        commentDAO.saveAll(mine);
    }
}
