package cpwu.ecut.web.handler;

import cpwu.ecut.common.constant.clazz.LostFoundException;
import cpwu.ecut.web.config.interceptor.MyInterceptor1;
import cpwu.ecut.web.dto.resp.ResponseCode;
import cpwu.ecut.web.dto.resp.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;


/**
 * parent
 * demo.web.handler
 * 全局Runtime异常处理类
 * 可处理来自Interceptor和Controller抛出的异常
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/03/27 22:23 Wednesday
 */

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * 处理多参校验异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    private ResponseDTO paramHandler(Exception e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(false)
                .setCode(ResponseCode.PARAM_INVALID);
        FieldError error;
        //入参为对象(json)
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            error = ex.getBindingResult().getFieldError();//如果错误不止一个，亲测好象是随机获取一个错误
        } else {// if (e instanceof BindException) {
            //入参为键值对
            BindException ex = (BindException) e;
            error = ex.getBindingResult().getFieldError();
        }
        responseDTO.setMsg("参数非法：" + error.getDefaultMessage() + ": " +
                "[" + error.getField() + "=" + error.getRejectedValue() + "]");
        responseDTO.setExt(e.getClass().getName());
        return responseDTO;
    }

    /**
     * 单个参数校验（键值对)
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseDTO paramHandler(ConstraintViolationException e) {
        //log.error("单个参数校验异常", e);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(false)
                .setCode(ResponseCode.PARAM_INVALID)
                .setMsg("参数非法：" + e.getMessage())
                .setExt(e.getClass().getName());
        return responseDTO;
    }


    /**
     * 文件上传大小超限制
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseDTO paramHandler(MaxUploadSizeExceededException e) {
        //log.error("上传文件过大异常", e);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(false)
                .setCode(ResponseCode.PARAM_INVALID)
                .setMsg("上传文件过大，限制为" + e.getMaxUploadSize() + "MB")
                .setExt(e.getClass().getName());
        return responseDTO;
    }

    /**
     * 针对@RequestParam注解修饰的参数不存在的情况
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseDTO paramHandler(MissingServletRequestParameterException e) {
        //log.error("缺少参数异常", e);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(false)
                .setCode(ResponseCode.PARAM_INVALID)
                .setMsg("缺少参数：" + e.getParameterName())
                .setExt(e.getClass().getName());
        return responseDTO;
    }


    /**
     * 失物招领系统异常
     */
    @ExceptionHandler(value = LostFoundException.class)
    public ResponseDTO systemErrorHandler(LostFoundException e) {
        //log.error("失物招领系统异常", e);
        return ResponseDTO.sysErrorObj(e);
    }

    /**
     * 默认异常处理，入参需要哪些参数可根据需求而定
     */
    @ExceptionHandler(value = Exception.class)
    private ResponseDTO defaultExceptionHandler(HttpServletRequest req, HttpServletResponse resp,
                                                HttpSession session, Exception e) {
        log.error("发生异常: ", e);
        return ResponseDTO.exceptionObj(e);
    }
}