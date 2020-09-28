package com.generateCode.jbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * springboot启动类
 *
 * @author generateCode
 */
@SpringBootApplication
@EnableSwagger2
@EnableAspectJAutoProxy
@MapperScan("com.generateCode.jbot.web.dao")
public class JbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(JbotApplication.class, args);
    }

}
