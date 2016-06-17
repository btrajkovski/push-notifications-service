package com.btrajkovski.push.notifications.model;

import com.btrajkovski.push.notifications.model.base.BaseEntity;
import com.btrajkovski.push.notifications.model.enums.MobileOS;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bojan on 15.7.15.
 */
@Entity
public class Device extends BaseEntity<Long> implements Serializable {

    @Column(length = 500)
    private String deviceToken;

    private boolean receiveNotification;

    @Column(length = 500)
    private String deviceID;

    @Enumerated(EnumType.STRING)
    private MobileOS mobileOS;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "application_ID")
    private Application application;

    protected Device() {
    }

    public Device(String deviceToken, String deviceID, MobileOS mobileOS, Application app) {
        this.deviceToken = deviceToken;
        this.deviceID = deviceID;
        this.mobileOS = mobileOS;
        this.receiveNotification = true;
        application = app;
    }

    public boolean getReceiveNotification() {
        return receiveNotification;
    }

    public void setReceiveNotification(boolean receiveNotification) {
        this.receiveNotification = receiveNotification;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public MobileOS getMobileOS() {
        return mobileOS;
    }

    public void setMobileOS(MobileOS mobileOS) {
        this.mobileOS = mobileOS;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
