package com.btrajkovski.push.notifications.service.notifications;

import com.btrajkovski.push.notifications.model.AppleCertificate;
import com.btrajkovski.push.notifications.model.Device;
import com.btrajkovski.push.notifications.model.Notification;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by bojan on 13.5.16.
 */
@Service
public class ApnsSender implements SenderService {

    public ApnsService createService(AppleCertificate appleCertificate) {
        ApnsServiceBuilder apnsServiceBuilder = null;
        try {
            apnsServiceBuilder = APNS.newService().withCert(appleCertificate.getFile().getBinaryStream(), appleCertificate.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (appleCertificate.getIsProduction())
            apnsServiceBuilder.withProductionDestination();
        else
            apnsServiceBuilder.withSandboxDestination();

        return apnsServiceBuilder.build();
    }

    public String createNotification(Notification notification) {
        String payload = APNS.newPayload().alertTitle(notification.getTitle())
                .alertBody(notification.getContent())
//                .customFields(notification.getCustomFields())
                .build();
        System.out.println("IOS payload:" + payload);
        return payload;
    }

    @Override
    public void sendToAll(List<Device> devices, Notification notification) {
        if (devices.size() == 0) {
            return;
        }

        ApnsService apnsService = createService(notification.getApplication().getAppleCertificate());
        String payload = createNotification(notification);
        apnsService.start();
        for (Device device : devices) {
            sendToOne(payload, apnsService, device.getDeviceToken());
        }
        apnsService.stop();
    }

    public void sendToOne(String payload, ApnsService service, String token) {
        service.push(token, payload);
    }
}