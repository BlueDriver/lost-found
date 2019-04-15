package cpwu.ecut.web.config.interceptor;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * parent
 * demo.web.config.interceptor
 * 默认拦截器，所有请求
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/03/27 20:15 Wednesday
 */
public class DefaultInterceptor extends HandlerInterceptorAdapter {
    /**
     * 文件上传最大尺寸，此为一次请求所有文件（单个或多个）的总最大尺寸
     */
    @Value("${max.upload.size}")
    private DataSize maxUploadSize;

    /**
     * 预处理回调方法，实现处理器的预处理（如检查登陆），第三个参数为响应的处理器，自定义Controller
     * 返回值：true表示继续流程（如调用下一个拦截器或处理器）；false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，
     * 此时我们需要通过response来产生响应；
     */
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
        //resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        //System.out.println("preHandler1 uri: " + req.getRequestURI());
        /**
         *  文件上传大小校验
         *  https://my.oschina.net/scjelly/blog/523705
         */
        if (req != null && ServletFileUpload.isMultipartContent(req)) {
            ServletRequestContext ctx = new ServletRequestContext(req);
            if (ctx.contentLength() > maxUploadSize.toBytes()) {
                /**
                 * 将被统一异常处理捕获
                 * @see cpwu.ecut.web.handler.GlobalExceptionHandler#paramHandler(MaxUploadSizeExceededException)
                 */
                throw new MaxUploadSizeExceededException(maxUploadSize.toMegabytes());
            }
        }
        /**
         * todo: 4/15/2019,015 08:52 PM
         * will err
         */
       /* else if(req.getContentLength() > maxUploadSize.toBytes()){
            throw new MaxUploadSizeExceededException(maxUploadSize.toMegabytes());
        }*/

        return true;
    }


    /**
     * 后处理回调方法，实现处理器的后处理（但在渲染视图之前），此时我们可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理，
     * modelAndView也可能为null。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("postHandler1");
    }

    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，如性能监控中我们可以在此记录结束时间并输出消耗时间，还可以进行一些资源清理，
     * 类似于try-catch-finally中的finally，但仅调用处理器执行链中
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //System.out.println("afterCompletion1");
    }
}
