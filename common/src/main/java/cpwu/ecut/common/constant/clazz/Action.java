package cpwu.ecut.common.constant.clazz;

import cpwu.ecut.common.constant.enums.ActionTypeEnum;

import java.lang.annotation.*;

/**
 * lost-found
 * cpwu.ecut.common.constant.clazz
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 12:25 Monday
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    /**
     * 动作名称
     */
    ActionTypeEnum action();

    /**
     * 目标名称
     */
    String target();

    /**
     * 目标ID
     */
    String targetId() default "";

    /**
     * 说明
     */
    String about() default "";


}
