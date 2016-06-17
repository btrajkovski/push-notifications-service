package com.btrajkovski.push.notifications.service.impl;

import com.btrajkovski.push.notifications.controller.auth.utils.SecurityUtil;
import com.btrajkovski.push.notifications.model.Notification;
import com.btrajkovski.push.notifications.model.NotificationType;
import com.btrajkovski.push.notifications.model.auth.User;
import com.btrajkovski.push.notifications.model.base.BaseEntity;
import com.btrajkovski.push.notifications.model.exception.UserNotAuthenticated;
import com.btrajkovski.push.notifications.repository.ApplicationRepository;
import com.btrajkovski.push.notifications.repository.DeviceRepository;
import com.btrajkovski.push.notifications.repository.NotificationRepository;
import com.btrajkovski.push.notifications.service.NotificationSender;
import com.btrajkovski.push.notifications.service.NotificationService;
import com.btrajkovski.push.notifications.service.scheduling.NotificationScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by bojan on 2.4.16.
 */
@Service
public class NotificationServiceImpl extends BaseServiceImpl implements NotificationService {
    private NotificationRepository notificationRepository;
    private ApplicationRepository applicationRepository;
    private DeviceRepository deviceRepository;
    private NotificationSender notificationSender;
    private NotificationScheduler notificationScheduler;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, ApplicationRepository applicationRepository,
                                   DeviceRepository deviceRepository, NotificationSender notificationSender,
                                   NotificationScheduler notificationScheduler) {
        this.notificationRepository = notificationRepository;
        this.applicationRepository = applicationRepository;
        this.deviceRepository = deviceRepository;
        this.notificationSender = notificationSender;
        this.notificationScheduler = notificationScheduler;
    }

    @Override
    @PostConstruct
    public void init() {
        super.setRepository(notificationRepository);
    }

    @Override
    public List<Notification> findNotificationsByUser(User user) {
        return notificationRepository.findByUser(user);
    }

    @Override
    public BaseEntity saveNotificationByPlatform(Notification notification, String platform) throws UserNotAuthenticated {

        //add currently logged in user
        notification.setUser(SecurityUtil.getUserDetails());

        if (notification.getApplication().getId() != null) {
            notification.setApplication(applicationRepository.findOne(notification.getApplication().getId()));
        }

        notification.setPlatform(platform);

        if (notification.getNotificationType() == NotificationType.single) {
            notificationSender.sendToAll(notification);
        } else {
            notificationScheduler.schedule(notification);
        }

        return super.save(notification);
    }

    @Override
    public BaseEntity delete(Long id) throws UserNotAuthenticated {
        User currentUser = SecurityUtil.getUserDetails();
        Notification notification = notificationRepository.findOne(id);

        if (!notification.getApplication().getUsers().contains(currentUser)) {
            throw new AccessDeniedException("You cannot perform this opperation");
        }

        return loadAndDelete(id);
    }
}
