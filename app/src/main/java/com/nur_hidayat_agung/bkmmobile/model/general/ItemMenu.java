package com.nur_hidayat_agung.bkmmobile.model.general;

import java.io.Serializable;

public class ItemMenu  implements Serializable {

    public int order;
    public String menuName;
    public int imageID;
    public String keyWord;


    public ItemMenu(int order, String menuName, int imageID, String keyWord) {
        this.order = order;
        this.menuName = menuName;
        this.imageID = imageID;
        this.keyWord = keyWord;
    }
}
