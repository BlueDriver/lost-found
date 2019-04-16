package cpwu.ecut.service.inter.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import cpwu.ecut.common.constant.enums.*;
import cpwu.ecut.common.utils.CommonUtils;
import cpwu.ecut.common.utils.EnumUtils;
import cpwu.ecut.common.utils.ExceptionUtils;
import cpwu.ecut.dao.entity.Category;
import cpwu.ecut.dao.entity.Comment;
import cpwu.ecut.dao.entity.LostFound;
import cpwu.ecut.dao.entity.User;
import cpwu.ecut.dao.inter.CategoryDAO;
import cpwu.ecut.dao.inter.CommentDAO;
import cpwu.ecut.dao.inter.LostFoundDAO;
import cpwu.ecut.dao.inter.UserDAO;
import cpwu.ecut.service.dto.req.PublicationAddReq;
import cpwu.ecut.service.dto.req.PublicationListReq;
import cpwu.ecut.service.dto.resp.PublicationItem;
import cpwu.ecut.service.dto.resp.PublicationPageResp;
import cpwu.ecut.service.inter.LostFoundService;
import cpwu.ecut.service.utils.ImageUtils;
import cpwu.ecut.service.utils.SessionUtils;
import jetbrick.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * lost-found
 * cpwu.ecut.service.inter.impl
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 21:09 Monday
 */
@Service
public class LostFoundServiceImpl implements LostFoundService {

    @Autowired
    private ImageUtils imageUtils;
    @Autowired
    private LostFoundDAO lostFoundDAO;
    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CommentDAO commentDAO;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void add(PublicationAddReq req, HttpSession session) throws Exception {
        //校验申请类型
        EnumUtils.checkAndGetCode(req.getApplyKind(), ApplyKindEnum.values());

        User user = SessionUtils.checkAndGetUser(session);
        //用户状态是否正常
        if (!AccountStatusEnum.NORMAL.equals(user.getStatus())) {
            throw ExceptionUtils.createException(ErrorEnum.USER_STATUS_ERROR, user.getUsername(), user.getStatus());
        }

        List<String> imageList = new ArrayList<>(req.getImages().size());
        Base64.Decoder decoder = Base64.getDecoder();

        for (String img : req.getImages()) {
            String image = imageUtils.getBase64Image(img);
            if (StringUtils.isEmpty(image)) {
                continue;
            }
            byte[] bytes = decoder.decode(image);
            String fileName = imageUtils.copyFileToResource(bytes).getFilename();
            imageList.add(fileName);
        }

        String json = mapper.writeValueAsString(imageList);

        LostFound lostFound = new LostFound();
        lostFound.setId(CommonUtils.getUUID())
                .setUserId(user.getId())
                .setCampusId(user.getCampusId())
                .setKind(req.getApplyKind())
                .setTitle(req.getTitle())
                .setAbout(req.getAbout())
                .setLocation(req.getLocation())
                .setImages(json)
                .setCategoryId(req.getCategoryName())
                .setFixTop(YesNoEnum.NO.getCode())
                .setLookCount(0)
                .setCreateTime(new Date())
                .setStatus(PublicationStatusEnum.FINDING.getCode())
                .setRecordStatus(RecordStatusEnum.EXISTS.getCode());

        lostFoundDAO.saveAndFlush(lostFound);
    }

    @Override
    public PublicationPageResp page(PublicationListReq req, HttpSession session) throws Exception {
        LostFound lostFoundEx = new LostFound();
        //kind
        if (ApplyKindEnum.LOST_PUBLISH.equals(req.getKind()) || ApplyKindEnum.FOUND_PUBLISH.equals(req.getKind())) {
            lostFoundEx.setKind(req.getKind());
        }
        //category
        if (!StringUtils.isBlank(req.getCategory())) {
            Category category = categoryDAO.findByNameEquals(req.getCategory());
            if (category != null) {
                lostFoundEx.setCategoryId(req.getCategory());
            }
        }
        //keyword
        ExampleMatcher matcher = null;
        if (!StringUtils.isBlank(req.getKeyword())) {
            matcher = ExampleMatcher.matching()
                    .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains());//模糊查询，即%{title}%
            lostFoundEx.setTitle(req.getKeyword());
        }
        //同校区的信息
        User user = SessionUtils.checkAndGetUser(session);
        lostFoundEx.setRecordStatus(RecordStatusEnum.EXISTS.getCode())
                .setCampusId(user.getCampusId());

        //some one, can only find self
        if (!StringUtils.isBlank(req.getUsername())) {
            lostFoundEx.setUserId(user.getId());
        }

        Example<LostFound> lostFoundExample =
                matcher == null ? Example.of(lostFoundEx) : Example.of(lostFoundEx, matcher);


        Page<LostFound> page = lostFoundDAO.findAll(lostFoundExample,
                PageRequest.of(req.getPageNum() < 0 ? 0 : req.getPageNum(), req.getPageSize(),
                        new Sort(Sort.Direction.DESC, "createTime", "fixTop")));

        PublicationPageResp resp = new PublicationPageResp();
        resp.setPageNum(page.getNumber())
                .setTotal(page.getTotalElements())
                .setTotalPage(page.getTotalPages())
                .setPageSize(page.getSize());
        resp.setList(convert(page.getContent()));

        return resp;
    }


    private List<PublicationItem> convert(List<LostFound> lostFoundList) throws IOException {
        List<PublicationItem> list = new ArrayList<>(lostFoundList.size());
        PublicationItem item;
        Optional<User> userOptional;
        User user;
        for (LostFound lostFound : lostFoundList) {
            item = new PublicationItem();
            item.setId(lostFound.getId());
            userOptional = userDAO.findById(lostFound.getUserId());
            if (userOptional.isPresent()) {
                user = userOptional.get();
                item.setIcon(user.getIcon())
                        .setUsername(user.getUsername());
            }
            item.setKind(lostFound.getKind())
                    .setTime(lostFound.getCreateTime())
                    .setLocation(lostFound.getLocation())
                    .setTitle(lostFound.getTitle())
                    .setAbout(lostFound.getAbout())
                    .setImages(mapper.readValue(lostFound.getImages(), List.class))
                    .setCategory(lostFound.getCategoryId())
                    .setLookCount(lostFound.getLookCount());

            Comment commentEx = new Comment();
            commentEx.setLostFoundId(lostFound.getId())
                    .setRecordStatus(RecordStatusEnum.EXISTS.getCode());
            item.setCommentCount(commentDAO.count(Example.of(commentEx)));

            list.add(item);
        }
        return list;
    }
}
