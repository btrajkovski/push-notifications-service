package com.btrajkovski.push.notifications.repository;

import com.btrajkovski.push.notifications.model.Application;
import com.btrajkovski.push.notifications.model.Device;
import com.btrajkovski.push.notifications.model.auth.User;
import com.btrajkovski.push.notifications.model.enums.MobileOS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by bojan on 27.3.16.
 */
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByMobileOSAndApplicationAndReceiveNotification(MobileOS mobileOS, Application application, boolean receiveNotification);
    List<Device> findByMobileOSAndApplication(MobileOS mobileOS, Application application);
    List<Device> findByApplication(Application application);
    List<Device> findByApplicationAndReceiveNotification(Application application, boolean receiveNotification);
    Device findByDeviceID(String deviceID);

    @Query("select d from Device d join d.application.users au where au=?1")
    List<Device> findByUser(User user);
}