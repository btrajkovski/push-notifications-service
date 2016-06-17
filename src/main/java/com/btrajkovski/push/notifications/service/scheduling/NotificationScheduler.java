package com.btrajkovski.push.notifications.service.scheduling;

import com.btrajkovski.push.notifications.model.Notification;
import com.btrajkovski.push.notifications.service.NotificationSender;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

/**
 * Created by bojan on 4.8.15.
 */
@Component
public class NotificationScheduler {
    private Scheduler scheduler;
    private NotificationSender notificationSender;

    @Autowired
    public NotificationScheduler(Scheduler scheduler, NotificationSender notificationSender) {
        this.scheduler = scheduler;
        this.notificationSender = notificationSender;
    }

    @PostConstruct
    public void onInit() throws SchedulerException {
        scheduler.start();
    }

    public void schedule(Notification notification) {
        Map<String, Object> map = new HashMap<>();
        map.put("notification", notification);
        map.put("sender", notificationSender);

        JobDetail job = newJob(ScheduledSender.class)
                .withIdentity(notification.getTitle(), "notification")
                .usingJobData(new JobDataMap(map))
                .build();

        SimpleTriggerImpl trigger = new SimpleTriggerImpl();
        trigger.setName("ScheduledSender");
        trigger.setStartTime(notification.getSendDate());

        try {
            scheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
