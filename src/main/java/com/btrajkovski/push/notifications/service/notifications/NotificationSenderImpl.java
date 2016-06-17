package com.btrajkovski.push.notifications.service.notifications;

import com.btrajkovski.push.notifications.model.Application;
import com.btrajkovski.push.notifications.model.Device;
import com.btrajkovski.push.notifications.model.Notification;
import com.btrajkovski.push.notifications.model.enums.MobileOS;
import com.btrajkovski.push.notifications.repository.DeviceRepository;
import com.btrajkovski.push.notifications.service.NotificationSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bojan on 13.5.16.
 */
@Service
public class NotificationSenderImpl implements NotificationSender {
    private DeviceRepository deviceRepository;
    private ApnsSender apnsSender;
    private GCMSender gcmSender;

    @Autowired
    public NotificationSenderImpl(DeviceRepository deviceRepository, ApnsSender apnsSender, GCMSender gcmSender) {
        this.deviceRepository = deviceRepository;
        this.apnsSender = apnsSender;
        this.gcmSender = gcmSender;
    }

    @Override
    @Async
    public void sendToAll(Notification notification) {

        if (notification.getApplication().getAppleCertificate() != null) {
            apnsSender.sendToAll(getDevicesByPlatform(notification.getPlatform(), MobileOS.ios, notification.getApplication()), notification);
        }

        gcmSender.sendToAll(getDevicesByPlatform(notification.getPlatform(), MobileOS.android, notification.getApplication()), notification);
    }

    private List<Device> getDevicesByPlatform(String platform, MobileOS senderType, Application application) {

        if (senderType.equals(MobileOS.ios) && (platform.equals("ios") || platform.equals("both"))) {
            return deviceRepository.findByMobileOSAndApplicationAndReceiveNotification(MobileOS.ios, application, true);
        }

        if (senderType.equals(MobileOS.android) && (platform.equals("android") || platform.equals("both"))) {
            return deviceRepository.findByMobileOSAndApplicationAndReceiveNotification(MobileOS.android, application, true);
        }

        return new ArrayList<>();
    }
}
