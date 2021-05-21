package com.nur_hidayat_agung.bkmmobile.model.help;

import java.io.Serializable;
import java.util.Date;

public class Help implements Serializable {
    private String id;
    private String title;
    private String help_code;
    private String value;
    private String updated_by;
    private Date updated_date;
    private String created_by;
    private Date created_date;
    private String isactive;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHelp_code() {
        return help_code;
    }

    public void setHelp_code(String help_code) {
        this.help_code = help_code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }
}
