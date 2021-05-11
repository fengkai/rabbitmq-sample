package org.example.rabbitmq.pictures;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  发送图片给rabbitmq ，测试前端网页效果
 */
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        new SpringApplication(App.class).run(args);
    }
}
