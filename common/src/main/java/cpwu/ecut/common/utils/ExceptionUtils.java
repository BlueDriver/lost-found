package cpwu.ecut.common.utils;

import cpwu.ecut.common.constant.clazz.LostFoundException;
import cpwu.ecut.common.constant.enums.ErrorEnum;
import org.slf4j.helpers.MessageFormatter;

import java.io.PrintWriter;
import java.io.StringWriter;

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
        return new LostFoundException(errorEnum.getCode(), MessageFormatter.arrayFormat(errorEnum.getDesc(), args).getMessage());
    }

    /**
     * 获取异常的堆栈信息
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}