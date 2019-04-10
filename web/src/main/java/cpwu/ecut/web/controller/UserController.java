package cpwu.ecut.web.controller;

import cpwu.ecut.common.constant.enums.ErrorEnum;
import cpwu.ecut.common.utils.ExceptionUtils;
import cpwu.ecut.service.dto.req.StudentRecognizeReq;
import cpwu.ecut.service.dto.req.UserLoginReq;
import cpwu.ecut.service.dto.resp.StudentRecognizeResp;
import cpwu.ecut.service.dto.resp.base.ResponseDTO;
import cpwu.ecut.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

@Controller
@Validated
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 认证/登录
     */
    @PostMapping("/recognize")
    @ResponseBody
    public ResponseDTO recognize(@Valid @RequestBody StudentRecognizeReq req, HttpSession session) throws Exception {
        checkCode(session, req.getCode());
        StudentRecognizeResp resp = userService.recognizeStudent(req, session);
        session.removeAttribute("code");
        return ResponseDTO.successObj("user", resp);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseDTO recognize(@Valid @RequestBody UserLoginReq req, HttpSession session) throws Exception {
        checkCode(session, req.getCode());
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
     * todo: 4/10/2019,010 09:37 PM
     * 重置密码
     */

    /**
     * 校验验证码
     */
    private void checkCode(HttpSession session, String code) throws Exception {
        if (!code.equals(session.getAttribute("code"))) {
            throw ExceptionUtils.createException(ErrorEnum.VERIFY_CODE_ERROR);
        }
    }

}
