package com.btrajkovski.push.notifications.service;

import com.btrajkovski.push.notifications.model.Device;
import com.btrajkovski.push.notifications.model.auth.User;
import com.btrajkovski.push.notifications.model.exception.UserNotAuthenticated;

import java.util.List;

/**
 * Created by bojan on 2.4.16.
 */
public interface DeviceService extends BaseService {
    Device save(Device device, String appIdentifier) throws UserNotAuthenticated;
    List<Device> findDevicesByUser(User userDetails);
}
