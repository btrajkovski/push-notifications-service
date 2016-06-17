package com.btrajkovski.push.notifications.service.scheduling;

import com.btrajkovski.push.notifications.model.Notification;
import com.btrajkovski.push.notifications.service.NotificationSender;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * Created by bojan on 4.8.15.
 */
@Component
public class ScheduledSender implements Job {
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Notification notification = (Notification) jobExecutionContext.getJobDetail().getJobDataMap().get("notification");
        NotificationSender notificationSender = (NotificationSender) jobExecutionContext.getJobDetail().getJobDataMap().get("sender");
        notificationSender.sendToAll(notification);
    }
}
