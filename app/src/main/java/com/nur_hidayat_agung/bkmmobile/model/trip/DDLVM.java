package com.nur_hidayat_agung.bkmmobile.model.trip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DDLVM implements Serializable {

    private boolean status;
    private List<String> names = new ArrayList<>();
    private List<String> ids = new ArrayList<>();

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
