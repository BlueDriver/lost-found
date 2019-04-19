package cpwu.ecut.web.config;

import cpwu.ecut.web.config.interceptor.DefaultInterceptor;
import org.springframework.context.annotation.Bean;
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

    @Bean
    public DefaultInterceptor defaultInterceptor() {
        return new DefaultInterceptor();
    }

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有
        registry.addInterceptor(defaultInterceptor()).addPathPatterns("/**");
        //拦截指定前缀
        //管理员
        //registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/api/v1/admin/**");
        //必须登录的
        //registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/api/v1/common/**", "/api/v1/user/**");

        //公开的：api/v1/public/**
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