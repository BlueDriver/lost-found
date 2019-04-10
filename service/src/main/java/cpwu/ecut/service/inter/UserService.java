package cpwu.ecut.service.inter;

import cpwu.ecut.service.dto.req.StudentRecognizeReq;
import cpwu.ecut.service.dto.req.UserLoginReq;
import cpwu.ecut.service.dto.resp.StudentRecognizeResp;

import javax.servlet.http.HttpSession;

/**
 * lost-found
 * cpwu.ecut.service
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 12:20 Friday
 */
public interface UserService {
    /**
     * 认证登录
     */
    StudentRecognizeResp recognizeStudent(StudentRecognizeReq req, HttpSession session) throws Exception;

    /**
     * 激活用户
     */
    String activateUser(String code);

    /**
     * 用户登录
     */
    StudentRecognizeResp loginUser(UserLoginReq req, HttpSession session) throws Exception;


}
