package com.btrajkovski.push.notifications.service.notifications;

import com.btrajkovski.push.notifications.model.Device;
import com.btrajkovski.push.notifications.model.Notification;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bojan on 13.5.16.
 */
@Service
public interface SenderService {
    void sendToAll(List<Device> devices, Notification notification) throws JSONException;
}
