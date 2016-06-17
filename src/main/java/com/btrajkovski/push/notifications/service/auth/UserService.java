package com.btrajkovski.push.notifications.service.auth;

import com.btrajkovski.push.notifications.model.auth.User;
import com.btrajkovski.push.notifications.model.enums.Role;
import com.btrajkovski.push.notifications.model.enums.SocialMediaService;
import com.btrajkovski.push.notifications.repository.UserRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User registerNewUserAccount(Connection<?> connection) {// UserProfile userAccountData, ConnectionKey connectionKey) {

        UserProfile userAccountData = connection.fetchUserProfile();
        ConnectionKey connectionKey = connection.getKey();

        String email = userAccountData.getEmail();

        if (email == null) {
            email = connectionKey.getProviderUserId() + "@" + connectionKey.getProviderId() + ".com";
        }

        User.Builder user = User.getBuilder()
                .username(email)
                .email(email)
                .firstName(userAccountData.getFirstName())
                .lastName(userAccountData.getLastName())
                .password("SocialPassword")
                .role(Role.ROLE_USER)
                .imageUrl(connection.getImageUrl());

        user.socialSignInProvider(SocialMediaService.valueOf(connectionKey.getProviderId().toUpperCase()));

        User registered = user.build();

        if (!emailExist(registered.getEmail())) {
            if (registered.getApiKey() == null) {
                registered.setApiKey(generateApiKey());
            }
            return userRepository.save(registered);
        } else {
            return userRepository.findByEmail(registered.getEmail());
        }
    }

    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);

        return user != null;
    }

    private String generateApiKey() {
        String apiKey;

        do {
            apiKey = RandomStringUtils.randomAlphanumeric(User.apiKeyLength);
        } while (userRepository.countByApiKey(apiKey) > 0);

        return apiKey;
    }

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }
}
