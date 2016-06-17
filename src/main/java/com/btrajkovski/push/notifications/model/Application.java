package com.btrajkovski.push.notifications.model;

import com.btrajkovski.push.notifications.model.auth.User;
import com.btrajkovski.push.notifications.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bojan on 21.7.15.
 */
@Entity
public class Application extends BaseEntity<Long> implements Serializable {

    private String name;

    private String appIdentifier;

    @Transient
    public static Integer appIdentifierLength = 20;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "appleCertificate_ID")
    private AppleCertificate appleCertificate;

    @ManyToMany
    @JoinTable(name = "applications_users")
    private List<User> users;

    @OneToMany(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Device> devices;

    @OneToMany(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notification> notifications;

    @Column(name = "GCMKey")
    private String GCMKey;

    public Application() {
    }

    public Application(String name, String appIdentifier, String GCMKey) {
        this.name = name;
        this.appIdentifier = appIdentifier;
        this.GCMKey = GCMKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public String getAppIdentifier() {
        return appIdentifier;
    }

    public void setAppIdentifier(String appIdentifier) {
        this.appIdentifier = appIdentifier;
    }

    @Override
    public String toString() {
        return String.format("%d %s", super.getId(), name);
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public AppleCertificate getAppleCertificate() {
        return appleCertificate;
    }

    public void setAppleCertificate(AppleCertificate appleCertificate) {
        this.appleCertificate = appleCertificate;
    }

    public String getGCMKey() {
        return GCMKey;
    }

    public void setGCMKey(String GCMKey) {
        this.GCMKey = GCMKey;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
