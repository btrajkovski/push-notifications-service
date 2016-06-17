package com.btrajkovski.push.notifications.service.auth;

import com.btrajkovski.push.notifications.model.auth.User;
import com.btrajkovski.push.notifications.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class RepositoryUserDetailsService implements UserDetailsService {

    private UserRepository repository;

    @Autowired
    public RepositoryUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        return User.getBuilder()
                .firstName(user.getFirstName())
                .id(user.getId())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .role(user.getRole())
                .socialSignInProvider(user.getSocialSignInProvider())
                .username(user.getEmail())
                .build();
    }
}
