package com.btrajkovski.push.notifications.service.impl;

import com.btrajkovski.push.notifications.model.Application;
import com.btrajkovski.push.notifications.model.Device;
import com.btrajkovski.push.notifications.model.auth.User;
import com.btrajkovski.push.notifications.model.exception.UserNotAuthenticated;
import com.btrajkovski.push.notifications.repository.ApplicationRepository;
import com.btrajkovski.push.notifications.repository.DeviceRepository;
import com.btrajkovski.push.notifications.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by bojan on 2.4.16.
 */
@Service
public class DeviceServiceImpl extends BaseServiceImpl implements DeviceService {
    private DeviceRepository deviceRepository;
    private ApplicationRepository applicationRepository;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository, ApplicationRepository applicationRepository) {
        this.deviceRepository = deviceRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    @PostConstruct
    public void init() {
        super.setRepository(deviceRepository);
    }

    @Override
    public Device save(Device device, String appIdentifier) throws UserNotAuthenticated {
        Device oldDevice = deviceRepository.findByDeviceID(device.getDeviceID());
        if (oldDevice != null) {
            device.setId(oldDevice.getId());
        }

        Application application = applicationRepository.findByAppIdentifier(appIdentifier);
        device.setApplication(application);
        return (Device) super.save(device);
    }

    @Override
    public List<Device> findDevicesByUser(User userDetails) {
        return deviceRepository.findByUser(userDetails);
    }
}