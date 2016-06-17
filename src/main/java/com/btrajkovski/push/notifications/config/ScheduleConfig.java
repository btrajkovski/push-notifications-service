package com.btrajkovski.push.notifications.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Created by bojan on 19.5.16.
 */
@Configuration
@EnableScheduling
@EnableAsync
public class ScheduleConfig {
    @Bean
    public SchedulerFactoryBean scheduler() {
        return new SchedulerFactoryBean();
    }
}
