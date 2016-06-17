package com.btrajkovski.push.notifications.repository;

import com.btrajkovski.push.notifications.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bojan on 12.3.16.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    Long countByApiKey(String apiKey);
}
