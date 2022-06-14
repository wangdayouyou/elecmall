package com.sdxx.purchaser;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class ServicePurchaserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicePurchaserApplication.class, args);
	}

}
