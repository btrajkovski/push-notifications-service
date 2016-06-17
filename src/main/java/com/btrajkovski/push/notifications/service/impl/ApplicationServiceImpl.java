package com.btrajkovski.push.notifications.service.impl;

import com.btrajkovski.push.notifications.controller.auth.utils.SecurityUtil;
import com.btrajkovski.push.notifications.model.AppleCertificate;
import com.btrajkovski.push.notifications.model.Application;
import com.btrajkovski.push.notifications.model.auth.User;
import com.btrajkovski.push.notifications.model.base.BaseEntity;
import com.btrajkovski.push.notifications.model.exception.UserNotAuthenticated;
import com.btrajkovski.push.notifications.repository.AppleCertificateRepository;
import com.btrajkovski.push.notifications.repository.ApplicationRepository;
import com.btrajkovski.push.notifications.service.ApplicationService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bojan on 2.4.16.
 */
@Service
public class ApplicationServiceImpl extends BaseServiceImpl implements ApplicationService {
    private ApplicationRepository applicationRepository;
    private AppleCertificateRepository appleCertificateRepository;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository, AppleCertificateRepository appleCertificateRepository) {
        this.applicationRepository = applicationRepository;
        this.appleCertificateRepository = appleCertificateRepository;
    }

    @Override
    @PostConstruct
    public void init() {
        super.setRepository(applicationRepository);
    }

    @Override
    public BaseEntity save(BaseEntity entity) throws UserNotAuthenticated {
        Application application = (Application) entity;
        AppleCertificate appleCertificate = application.getAppleCertificate();

        //add currently logged in user
        User currentUser = SecurityUtil.getUserDetails();
        List<User> users = new ArrayList<>();
        if (application.getUsers() != null) {
            users.addAll(application.getUsers());
            if (!application.getUsers().contains(currentUser)) {
                users.add(currentUser);
            }
        } else {
            users.add(currentUser);
        }

        //generate unique app identifier
        String appIdentifier;
        do {
            appIdentifier = RandomStringUtils.randomAlphanumeric(Application.appIdentifierLength);
        } while (applicationRepository.countByAppIdentifier(appIdentifier) > 0);

        application.setAppIdentifier(appIdentifier);
        application.setUsers(users);

        if (appleCertificate != null) {
            appleCertificateRepository.save(appleCertificate);
        }

        return super.save(entity);
    }

    @Transactional
    @Override
    public BaseEntity delete(Long id) throws UserNotAuthenticated, AccessDeniedException {
        User currentUser = SecurityUtil.getUserDetails();
        Application application = applicationRepository.findOne(id);

        if (!application.getUsers().contains(currentUser)) {
            throw new AccessDeniedException("You cannot perform this opperation");
        }

        return loadAndDelete(id);

    }

    @Override
    public BaseEntity edit(Application newApplication) throws UserNotAuthenticated {
        Application application = applicationRepository.findOne(newApplication.getId());
        AppleCertificate appleCertificate = newApplication.getAppleCertificate();

        List<User> users = new ArrayList<>();
        if (newApplication.getUsers() != null) {
            users.addAll(newApplication.getUsers());
        }
        if (!newApplication.getUsers().contains(SecurityUtil.getUserDetails())) {
            users.add(SecurityUtil.getUserDetails());
        }
        application.setUsers(users);
        application.setGCMKey(newApplication.getGCMKey());

        if (appleCertificate != null) {
            appleCertificateRepository.save(appleCertificate);
            application.setAppleCertificate(appleCertificate);
        }
        application.setName(newApplication.getName());

        return super.save(application);
    }

    @Override
    public List<Application> findApplicationsByUser(User user) {
        return applicationRepository.findByUser(user);
    }
}
