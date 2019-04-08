package cpwu.ecut.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"cpwu.ecut.*"})
@EnableJpaRepositories({"cpwu.ecut.dao.*"})
@EntityScan({"cpwu.ecut.dao.*"})
//jpa扫描问题：https://blog.csdn.net/oarsman/article/details/85268645
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
