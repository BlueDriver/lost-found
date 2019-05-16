package cpwu.ecut.web.controller;

import cpwu.ecut.common.constant.annotation.ActionLog;
import cpwu.ecut.common.constant.annotation.ServiceEnum;
import cpwu.ecut.common.constant.enums.ActionEnum;
import cpwu.ecut.common.constant.enums.ErrorEnum;
import cpwu.ecut.common.utils.ExceptionUtils;
import cpwu.ecut.service.dto.req.StudentRecognizeReq;
import cpwu.ecut.service.dto.req.UserLoginReq;
import cpwu.ecut.service.dto.resp.StudentRecognizeResp;
import cpwu.ecut.service.dto.resp.base.ResponseDTO;
import cpwu.ecut.service.inter.SchoolService;
import cpwu.ecut.service.inter.UserService;
import cpwu.ecut.service.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * lost-found
 * cpwu.ecut.web.controller.common
 * 开放接口
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/08 14:47 Monday
 */
@Controller
@Validated
@RequestMapping("/api/v1/public")
public class PublicController {
    @Autowired
    private SchoolService schoolService;

    @Autowired
    private VerifyCodeUtils verifyCodeUtils;

    @GetMapping("/schools")
    @ResponseBody
    public ResponseDTO schools() {
        return ResponseDTO.successObj("schools", schoolService.getSchools());
    }

    /**
     * 验证码长度
     */
    private static final int codeLength = 5;

    /**
     * 生成随机验证码
     * http://localhost:8080//api/v1/common/verifyCode
     */
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletResponse response, HttpSession session) throws IOException {
        String code = verifyCodeUtils.getRandomNum(codeLength);
        //将code存入session，用于后期校验
        session.setAttribute("code", code);
        BufferedImage image = verifyCodeUtils.getImage(code);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setDateHeader(HttpHeaders.EXPIRES, -1L);
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");//http 1.1
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");//http 1.0
        //将图片写入响应输出流
        ImageIO.write(image, "jpg", response.getOutputStream());
    }

    @Autowired
    private UserService userService;

    /**
     * 认证/登录
     */
    @PostMapping("/recognize")
    @ResponseBody
    @ActionLog(service = ServiceEnum.USER_REG, action = ActionEnum.CREATE)
    public ResponseDTO studentRecognize(@Valid @RequestBody StudentRecognizeReq req, HttpSession session) throws Exception {
        checkVerifyCode(session, req.getCode());
        StudentRecognizeResp resp = userService.recognizeStudent(req, session);
        session.removeAttribute("code");
        return ResponseDTO.successObj("user", resp);
    }

    /**
     * 登录
     */
    @ActionLog(service = ServiceEnum.USER_LOGIN, action = ActionEnum.CREATE)
    @PostMapping("/login")
    @ResponseBody
    public ResponseDTO userLogin(@Valid @RequestBody UserLoginReq req, HttpSession session) throws Exception {
        checkVerifyCode(session, req.getCode());
        StudentRecognizeResp resp = userService.loginUser(req, session);
        session.removeAttribute("code");
        return ResponseDTO.successObj("user", resp);
    }

    /**
     * 激活账号
     */
    @RequestMapping(value = "/activate/{code}", method = RequestMethod.GET)
    public String activate(@NotBlank(message = "激活码不能为空") @PathVariable("code") String code) {
        return "/static/activate/" + userService.activateUser(code);
    }


    /**
     * 校验验证码
     */
    private void checkVerifyCode(HttpSession session, String code) throws Exception {
        if (!code.equals(session.getAttribute("code"))) {
            throw ExceptionUtils.createException(ErrorEnum.VERIFY_CODE_ERROR);
        }
    }


}
