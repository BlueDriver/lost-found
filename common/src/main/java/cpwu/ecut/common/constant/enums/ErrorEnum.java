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
    USER_LOGIN_ERROR(2006, "登录状态异常，请重新登录"),

    //学校相关
    SCHOOL_NOT_EXISTS(2101, "学校{}不存在"),
    SCHOOL_NOT_SUPPORT_REG(2102, "学校{}暂不不支持认证"),
    //类别
    CATEGORY_EXISTS(2201, "类别{}已存在"),

    //权限相关
    NO_PERMISSION(2301, "无此操作权限，请重试或重新登录"),
    NO_ENOUGH_PERMISSION(2302, "权限不足"),
    PERMISSION_NOT_MATCH(2303, "用户权限不匹配"),

    //系统相关
    NO_MATCH_ENUM(2401, "{}中无匹配类型{}"),

    //启事相关
    LOST_FOUND_NOT_EXISTS(2501, "启事{}不存在或已被删除"),
    CATEGORY_NOT_EXISTS(2502, "类别{}不存在或已删除"),

    //通知相关
    NOTICE_NOT_EXISTS(2601, "通知{}不存在或已删除"),

    //反馈相关
    FEEDBACK_NOT_EXISTS(2701, "反馈{}不存在"),;

    private Integer code;
    private String desc;

}
