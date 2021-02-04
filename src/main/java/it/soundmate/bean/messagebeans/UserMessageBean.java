/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 04/02/21, 18:23
 * Last edited: 04/02/21, 18:21
 */

package it.soundmate.bean.messagebeans;

import it.soundmate.utils.Cache;
import javafx.scene.image.Image;

import java.io.InputStream;

public abstract class UserMessageBean {

    private final int id;
    private final String email;
    private final String name;
    private final InputStream profileImgIs;
    private final Image profileImg;

    protected UserMessageBean(int id, String email, String name, String encodedImg) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profileImgIs = Cache.getInstance().buildProfileImg(id, encodedImg);
        this.profileImg = new Image(this.profileImgIs);
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public InputStream getProfileImgIs() {
        return profileImgIs;
    }

    public Image getProfileImg() {
        return profileImg;
    }

}
