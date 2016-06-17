package com.btrajkovski.push.notifications.repository;

import com.btrajkovski.push.notifications.model.Notification;
import com.btrajkovski.push.notifications.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by bojan on 27.3.16.
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    public List<Notification> findByUser(User user);
}
