package cpwu.ecut.common.constant.annotation;

import cpwu.ecut.common.constant.enums.UserKindEnum;

import java.lang.annotation.*;

/**
 * lost-found
 * cpwu.ecut.common.constant.annotation
 * 权限校验注解，针对需要权限控制的接口加上此注解
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/19 12:32 星期五
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {
    /**
     * 用户级别
     */
    UserKindEnum level();

    /**
     * 匹配模式
     */
    MatchModeEnum mode() default MatchModeEnum.MIN;

    //String role() default "默认角色";
}
