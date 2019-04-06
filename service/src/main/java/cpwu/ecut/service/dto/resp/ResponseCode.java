package cpwu.ecut.service.dto.resp;

/**
 * parent
 * demo.web.controller.base
 * 返回状态码
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/01 10:16 Monday
 */
public interface ResponseCode {
    /**
     * 成功success
     */
    int SUCCESS = 0;
    /**
     * 异常Exception
     */
    int EXCEPTION = 1;
    /**
     * 参数错误Parameter error
     */
    int PARAM_INVALID = 2;
    /**
     * 无权限NO permission
     */
    int NO_PERMISSION = 3;
    /**
     * 状态异常
     */
    int STATUS_ERROR = 4;
}
