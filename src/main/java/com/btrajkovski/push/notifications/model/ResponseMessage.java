package com.btrajkovski.push.notifications.model;

/**
 * Created by bojan on 27.7.15.
 */
public class ResponseMessage {
    private int status;
    private String data;
    private String message;

    public ResponseMessage() {
    }

    public ResponseMessage(final int inStatus, final String inError, final String inMessage) {
        status = inStatus;
        data = inError;
        message = inMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(final int inStatus) {
        status = inStatus;
    }

    public String getData() {
        return data;
    }

    public void setData(final String inError) {
        data = inError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String inMessage) {
        message = inMessage;
    }
}
