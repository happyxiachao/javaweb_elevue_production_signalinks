package com.javaweb.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.javaweb.*"})
@MapperScan("com.javaweb.**.mapper")
@EnableTransactionManagement
// 开启定时任务支持
@EnableScheduling
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  JavaWeb_EleVue_Pro前后端分离旗舰版API启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
