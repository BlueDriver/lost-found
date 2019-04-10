package cpwu.ecut.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 * 业务错误枚举
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 18:52 Friday
 */
@Getter
@AllArgsConstructor
public enum ErrorEnum {
    //{}为占位符
    //用户相关
    PASSWORD_USERNAME_ERROR(2000, "账号{}或密码错误"),
    STUDENT_NUM_NO_USER_EXISTS(2001, "学号{}对应的用户{}不存在"),
    USER_STATUS_ERROR(2002, "用户{}状态{}未知错误"),
    USER_HAS_FREEZE(2003, "用户{}已被冻结"),
    VERIFY_CODE_ERROR(2004, "验证码错误"),
    USER_NOT_EXISTS(2005, "用户{}不存在"),
    //学校相关
    SCHOOL_NOT_EXISTS(2101, "学校{}不存在"),
    SCHOOL_NOT_SUPPORT_REG(2102, "学校{}暂不不支持认证"),;

    private Integer code;
    private String desc;

}
