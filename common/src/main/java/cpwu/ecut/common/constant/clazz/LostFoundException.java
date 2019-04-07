package cpwu.ecut.common.constant.clazz;

import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.clazz
 * 失物招领系统异常
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 19:04 Friday
 */
@Getter
public class LostFoundException extends RuntimeException {
    /**
     * 错误码
     *
     * @see cpwu.ecut.common.constant.enums.ErrorEnum
     */
    private int code;

    public LostFoundException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
