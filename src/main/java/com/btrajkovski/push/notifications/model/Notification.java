package com.btrajkovski.push.notifications.model;

import com.btrajkovski.push.notifications.model.auth.User;
import com.btrajkovski.push.notifications.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * * Created by bojan on 23.7.15.
 */
@Entity
public class Notification extends BaseEntity<Long> implements Serializable {

    private String title;

    @Column(length = 1000)
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "CET")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Notification_customFields")
    @MapKeyColumn(name = "field_key")
    @Column(name = "field_value", length = 1000)
    private Map<String, String> customFields;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "devices_notifications")
    @JsonIgnore
    private List<Device> devices;

    @ManyToOne
    @JoinColumn(name = "user_ID")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "application_ID")
    private Application application;

    @JsonIgnore
    private String platform;

    public Notification() {
    }

    public Notification(String title, String content, Date sendDate,
                        NotificationType notificationType, User user, Application application) {
        this.title = title;
        this.content = content;
        this.sendDate = sendDate;
        this.notificationType = notificationType;
        this.user = user;
        this.application = application;
    }

    public Notification(String title, String content, Application application) {
        this.title = title;
        this.content = content;
        this.application = application;
    }

    public Notification(String title, String content, Date sendDate,
                        NotificationType notificationType) {
        this.title = title;
        this.content = content;
        this.sendDate = sendDate;
        this.notificationType = notificationType;
    }

    //    @JsonIgnore
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Map<String, String> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Map<String, String> additionalFields) {
        this.customFields = additionalFields;
    }

    public void addCustomField(String key, String value) {
        this.customFields.put(key, value);
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
