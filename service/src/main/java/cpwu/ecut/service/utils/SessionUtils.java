package cpwu.ecut.service.utils;

import cpwu.ecut.common.constant.enums.ErrorEnum;
import cpwu.ecut.common.utils.ExceptionUtils;
import cpwu.ecut.dao.entity.User;

import javax.servlet.http.HttpSession;

/**
 * lost-found
 * cpwu.ecut.service.utils
 * session工具类哦
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/16 19:35 Tuesday
 */
public class SessionUtils {
    public static User getUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    public static User checkAndGetUser(HttpSession session) throws Exception {
        User user = getUser(session);
        if (user == null) {
            throw ExceptionUtils.createException(ErrorEnum.USER_LOGIN_ERROR);
        }
        return user;
    }
}
