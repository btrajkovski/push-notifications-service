package com.btrajkovski.push.notifications.repository;

import com.btrajkovski.push.notifications.model.Application;
import com.btrajkovski.push.notifications.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by bojan on 27.3.16.
 */
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("select a from Application a join a.users au where au=?1")
    List<Application> findByUser(User user);
    Long countByAppIdentifier(String appIdentifier);
    Application findByAppIdentifier(String appIdentifier);
}
