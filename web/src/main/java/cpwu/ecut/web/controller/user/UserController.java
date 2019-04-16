package cpwu.ecut.web.controller.user;

import cpwu.ecut.service.dto.req.PublicationAddReq;
import cpwu.ecut.service.dto.req.PublicationListReq;
import cpwu.ecut.service.dto.resp.PublicationPageResp;
import cpwu.ecut.service.dto.resp.base.ResponseDTO;
import cpwu.ecut.service.inter.LostFoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

    /**
     * 发布启事
     */
    @PostMapping("/pub")
    public ResponseDTO publicationAdd(@Valid @RequestBody PublicationAddReq req, HttpSession session) throws Exception {
        lostFoundService.add(req, session);
        return ResponseDTO.successObj();
    }

    @PostMapping("/page")
    public ResponseDTO publicationPage(@Valid @RequestBody PublicationListReq req, HttpSession session) throws Exception {
        PublicationPageResp resp = lostFoundService.page(req, session);
        return ResponseDTO.successObj("page", resp);
    }
}
