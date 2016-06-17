package com.btrajkovski.push.notifications.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;

/**
 * Created by bojan on 26.8.15.
 */
@Entity
public class AppleCertificate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonIgnore
    private Blob file;

    private String password;

    private boolean isProduction;

    @OneToOne(mappedBy = "appleCertificate")
    @JsonIgnore
    private Application application;
    
    public AppleCertificate() {}

    public AppleCertificate(Blob file, String password, boolean isProduction) {
        this.file = file;
        this.password = password;
        this.isProduction = isProduction;
    }

//    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsProduction() {
        return isProduction;
    }

    public void setIsProduction(boolean isProduction) {
        this.isProduction = isProduction;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Blob getFile() {
        return file;
    }

    public void setFile(Blob file) {
        this.file = file;
    }

}
