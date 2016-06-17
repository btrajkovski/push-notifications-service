package com.btrajkovski.push.notifications.controller;

import com.btrajkovski.push.notifications.controller.auth.utils.SecurityUtil;
import com.btrajkovski.push.notifications.model.Notification;
import com.btrajkovski.push.notifications.model.exception.UserNotAuthenticated;
import com.btrajkovski.push.notifications.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by bojan on 2.4.16.
 */
@RestController
@RequestMapping(path = "/api/notifications")
public class NotificationController {
    private NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Notification> listAllNotifications() throws UserNotAuthenticated {
        return notificationService.findNotificationsByUser(SecurityUtil.getUserDetails());
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public Notification addNotification(@RequestBody Notification notification, @PathVariable String platform) throws UserNotAuthenticated {
        return addNotificationByPlatform(notification, "both");
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(path = "/platform/{platform}", method = RequestMethod.POST)
    public Notification addNotificationByPlatform(@RequestBody Notification notification, @PathVariable String platform) throws UserNotAuthenticated {
        notificationService.saveNotificationByPlatform(notification, platform);
        return notification;
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public Notification deleteNotification(@PathVariable Long id) throws UserNotAuthenticated {
        return (Notification) notificationService.delete(id);
    }
}
