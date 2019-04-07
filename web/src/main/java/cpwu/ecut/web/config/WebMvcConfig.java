package cpwu.ecut.web.config;

import cpwu.ecut.web.config.interceptor.MyInterceptor1;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * parent
 * demo.web.config
 * mvc配置类
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/03/27 21:57 Wednesday
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有
        registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/**");
        //拦截指定前缀
        //registry.addInterceptor(new MyInterceptor2()).addPathPatterns(new String[]{"/admin", "/admin/*"});
    }

    /**
     * 允许跨域访问
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//允许跨域访问的路径
                .allowCredentials(true)
                .allowedHeaders("*")
                //.allowedOrigins("http://192.168.1.100:8080")
                .allowedMethods("*")
                .exposedHeaders(HttpHeaders.SET_COOKIE)
                .maxAge(-1L);//单位：秒，-1表示无限制，到关闭浏览器前均有效
    }

}