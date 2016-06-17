package com.btrajkovski.push.notifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class PushNotificationsServiceApplication{
	public static void main(String[] args) {
		SpringApplication.run(PushNotificationsServiceApplication.class, args);
	}
}
