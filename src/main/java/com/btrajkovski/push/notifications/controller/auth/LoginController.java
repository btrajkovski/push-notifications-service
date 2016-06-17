package com.btrajkovski.push.notifications.controller.auth;

import com.btrajkovski.push.notifications.controller.auth.utils.SecurityUtil;
import com.btrajkovski.push.notifications.model.auth.User;
import com.btrajkovski.push.notifications.model.exception.UserNotAuthenticated;
import com.btrajkovski.push.notifications.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bojan on 28.3.16.
 */
@RestController
@RequestMapping(path = "/api")
public class LoginController {
    private UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(path = "/user")
    public User currentUser() throws UserNotAuthenticated {
        return userRepository.findOne(SecurityUtil.getUserDetails().getId());
    }
}