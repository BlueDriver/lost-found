package cpwu.ecut.web.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cpwu.ecut.common.utils.ExceptionUtils;
import cpwu.ecut.service.utils.MailSenderService;
import cpwu.ecut.web.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.mail.MessagingException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * parent
 * demo.web.config
 * 自定义异步任务配置
 * https://www.cnblogs.com/yangfanexp/p/7747225.html
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/03/28 09:52 Thursday
 */
@Configuration
@EnableAsync
@Slf4j
public class TaskExecutorConfig implements AsyncConfigurer {

    @Value("${spring.task.execution.pool.core-size}")
    private int coreSize;
    @Value("${spring.task.execution.pool.max-size}")
    private int maxSize;
    @Value("${spring.task.execution.pool.queue-capacity}")
    private int queueCapacity;
    @Value("${spring.task.execution.thread-name-prefix}")
    private String prefix;

    @Value("${admin.emails}")
    private List<String> adminEmailList;

    @Value("${system.email}")
    private String systemEmail;


    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private CommonUtils commonUtils;
    @Autowired
    private MailSenderService mailSenderService;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(coreSize);
        executor.setMaxPoolSize(maxSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(prefix);
        executor.initialize();
        return executor;
    }

    /**
     * 指定异常处理类
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SpringAsyncExceptionHandler();
    }

    /**
     * 自定义异常处理类
     */
    class SpringAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        //用户对象转json串
        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
            log.error("异步任务异常：", throwable);
            //以下为：邮件通知管理员异常信息
            //入参
            StringBuffer args = new StringBuffer();
            args.append("[");
            Parameter[] parameters = method.getParameters();
            Parameter parameter;
            Object value;
            try {
                //组合入参字符串
                for (int i = 0; i < obj.length && i < obj.length; i++) {
                    parameter = parameters[i];
                    value = obj[i];
                    args.append(parameter.getName()).append("=").append(mapper.writeValueAsString(value));
                    if (i == obj.length - 1) {//last
                        args.append("]");
                    } else {//not last
                        args.append(", ");
                    }
                }
                if (obj.length == 0) {
                    args.append("]");
                }
            } catch (JsonProcessingException e) {
                args.setLength(0);
                for (Object o : obj) {
                    args.append(o.toString())
                            .append(", ");
                }
            }
            //异常类名
            String exName = throwable.getClass().getName();
            //堆栈信息
            String trace = ExceptionUtils.getStackTrace(throwable);
            //当前时间
            String now = commonUtils.getFormatDateTimeNow();
            //模板参数
            Map<String, Object> param = new HashMap<>(4);
            param.put("args", args.toString());
            param.put("exName", exName);
            param.put("trace", trace);
            param.put("now", now);
            param.put("app", appName);
            try {
                //向管理员邮箱发送异常提醒邮件
                for (String adminEmail : adminEmailList) {
                    mailSenderService.sendTemplateMessage(adminEmail, param, "templates/mail/taskError.html",
                            appName + "系统邮件", systemEmail, appName);
                }
            } catch (IOException | MessagingException e) {
                log.error("模板邮件发送异常", e);
            }
        }
    }
}