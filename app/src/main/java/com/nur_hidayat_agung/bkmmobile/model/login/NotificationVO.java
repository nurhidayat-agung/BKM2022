package com.nur_hidayat_agung.bkmmobile.model.login;

import java.io.Serializable;

public class NotificationVO implements Serializable {
    private String title;
    private String message;
    private String iconUrl;
    private String action;
    private String actionDestination;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionDestination() {
        return actionDestination;
    }

    public void setActionDestination(String actionDestination) {
        this.actionDestination = actionDestination;
    }
}
