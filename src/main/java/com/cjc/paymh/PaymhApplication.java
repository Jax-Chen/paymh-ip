package com.cjc.paymh;

import com.cjc.paymh.service.PayService;
import com.cjc.paymh.thread.MainThreadIp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PaymhApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PaymhApplication.class, args);
        System.out.println(PayService.ipCount);
        System.out.println(MainThreadIp.ttt);
    }

    @Override//为了打包springboot项目
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

}
