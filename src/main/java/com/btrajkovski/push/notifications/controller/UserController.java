package com.btrajkovski.push.notifications.controller;

import com.btrajkovski.push.notifications.model.auth.User;
import com.btrajkovski.push.notifications.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by bojan on 20.5.16.
 */
@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> listAllUsers() {
        return userService.listAllUsers();
    }
}
