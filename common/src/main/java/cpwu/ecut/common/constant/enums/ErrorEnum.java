package cpwu.ecut.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 * 错误枚举
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 18:52 Friday
 */
@Getter
@AllArgsConstructor
public enum ErrorEnum {
    //{}为占位符
    PASSWORD_USERNAME_ERROR(1000, "账号{}或密码错误"),
    ;

    private Integer code;
    private String desc;

}
