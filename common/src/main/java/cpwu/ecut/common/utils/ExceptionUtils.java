package cpwu.ecut.common.utils;

import cpwu.ecut.common.constant.clazz.LostFoundException;
import cpwu.ecut.common.constant.enums.ErrorEnum;
import org.slf4j.helpers.MessageFormatter;

/**
 * lost-found
 * cpwu.ecut.common.utils
 * 异常抛出工具类
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 18:39 Friday
 */
public class ExceptionUtils {
    /**
     * 创建异常
     */
    public static Exception createException(ErrorEnum errorEnum, Object... args) {
        //占位符替换
        return new LostFoundException(MessageFormatter.format(errorEnum.getDesc(), args).getMessage());
    }
}
