package com.btrajkovski.push.notifications.service;

import com.btrajkovski.push.notifications.model.Application;
import com.btrajkovski.push.notifications.model.auth.User;
import com.btrajkovski.push.notifications.model.base.BaseEntity;
import com.btrajkovski.push.notifications.model.exception.UserNotAuthenticated;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

/**
 * Created by bojan on 2.4.16.
 */
public interface ApplicationService extends BaseService {
    public List<Application> findApplicationsByUser(User user);

    @Override
    BaseEntity save(BaseEntity entity) throws UserNotAuthenticated;

    BaseEntity delete(Long id) throws UserNotAuthenticated, AccessDeniedException;

    BaseEntity edit(Application applicationObject) throws UserNotAuthenticated;
}
