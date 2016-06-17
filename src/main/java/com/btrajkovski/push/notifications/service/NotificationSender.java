package com.btrajkovski.push.notifications.service;

import com.btrajkovski.push.notifications.model.Notification;

/**
 * Created by bojan on 13.5.16.
 */
public interface NotificationSender {
    void sendToAll(Notification notification);
}
