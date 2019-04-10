package cpwu.ecut.service.dto.resp.base;

/**
 * parent
 * demo.web.controller.base
 * 返回状态码
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/01 10:16 Monday
 * @see cpwu.ecut.common.constant.enums.ErrorEnum
 */
public interface ResponseCode {
    /**
     * 成功success
     */
    int SUCCESS = 1000;
    /**
     * 异常Exception
     */
    int EXCEPTION = 1001;
    /**
     * 参数错误Parameter error
     */
    int PARAM_INVALID = 1002;
    /**
     * 无权限NO permission
     */
    int NO_PERMISSION = 1003;

}
