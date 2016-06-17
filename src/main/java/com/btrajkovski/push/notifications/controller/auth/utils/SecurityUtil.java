package com.btrajkovski.push.notifications.controller.auth.utils;

import com.btrajkovski.push.notifications.model.auth.User;
import com.btrajkovski.push.notifications.model.enums.SocialMediaService;
import com.btrajkovski.push.notifications.model.exception.UserNotAuthenticated;
import com.btrajkovski.push.notifications.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.google.api.Google;
import org.springframework.social.twitter.api.Twitter;

public class SecurityUtil {

    public static void logInUser(User user) {
        User userDetails = User.getBuilder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .role(user.getRole())
                .socialSignInProvider(user.getSocialSignInProvider())
                .username(user.getEmail())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static User getUserDetails() throws UserNotAuthenticated {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        } else {
            throw new UserNotAuthenticated();
        }
    }
}
