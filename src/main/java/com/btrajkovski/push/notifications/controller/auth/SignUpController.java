package com.btrajkovski.push.notifications.controller.auth;

import com.btrajkovski.push.notifications.controller.auth.utils.SecurityUtil;
import com.btrajkovski.push.notifications.model.auth.User;
import com.btrajkovski.push.notifications.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class SignUpController {

    private ProviderSignInUtils providerSignInUtils;

    @Value("${application.url}")
    private String redirectURL;

    @Autowired
    private UserService userService;

    @Autowired
    public SignUpController(ConnectionFactoryLocator connectionFactoryLocator,
                            UsersConnectionRepository connectionRepository) {
        this.providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
    }

    @RequestMapping(value = "/signup")
    public String redirectRequestToRegistrationPage(WebRequest request) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        if (connection != null) {
            User registered = createUserAccount(connection);
            SecurityUtil.logInUser(registered);
            providerSignInUtils.doPostSignUp(registered.getEmail(), request);
        }
        return "redirect:" + redirectURL;
    }

    private User createUserAccount(Connection<?> connection) {
        return userService.registerNewUserAccount(connection);
    }

}