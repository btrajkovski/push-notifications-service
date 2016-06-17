package com.btrajkovski.push.notifications.service;

import com.btrajkovski.push.notifications.model.Notification;
import com.btrajkovski.push.notifications.model.auth.User;
import com.btrajkovski.push.notifications.model.base.BaseEntity;
import com.btrajkovski.push.notifications.model.exception.UserNotAuthenticated;

import java.util.List;

/**
 * Created by bojan on 2.4.16.
 */
public interface NotificationService extends BaseService {
    public List<Notification> findNotificationsByUser(User user);

    public BaseEntity saveNotificationByPlatform(Notification notification, String platform) throws UserNotAuthenticated;

    BaseEntity delete(Long id) throws UserNotAuthenticated;
}
