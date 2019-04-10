package cpwu.ecut.service.inter;

import cpwu.ecut.service.dto.req.StudentRecognizeReq;
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

    String activateUser(String code);

//    /**
//     * 学号+密码校园通认证
//     */
//    void recognize(Student student);


}
