/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 21/01/21, 17:36
 * Last edited: 21/01/21, 17:36
 */

package it.soundmate.bean;

import it.soundmate.exceptions.InputException;

public class AddRoomBean {

    private String name;
    private int price;
    private String description;
    private String encodedImg;

    public AddRoomBean(){}

    public AddRoomBean(String name, String price, String description, String encodedImg) {
        this.name = name;
        this.description = description;
        this.encodedImg = encodedImg;
        if (!this.checkFields()) throw new InputException("Some fields are empty");
        if (!this.convertToInt(price)) throw new InputException("Price is not a number");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEncodedImg() {
        return encodedImg;
    }

    public void setEncodedImg(String encodedImg) {
        this.encodedImg = encodedImg;
    }

    private boolean checkFields(){
        return !"".equals(this.name) && !"".equals(this.description);
    }

    private boolean convertToInt(String price) {
        try {
            this.price = Integer.parseInt(price);
            return true;
        } catch (NumberFormatException ne) {
            return false;
        }
    }
}
