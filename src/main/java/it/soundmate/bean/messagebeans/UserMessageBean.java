/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 04/02/21, 18:23
 * Last edited: 04/02/21, 18:21
 */

package it.soundmate.bean.messagebeans;

import it.soundmate.utils.Cache;
import javafx.scene.image.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public abstract class UserMessageBean {

    private static final Logger logger = LoggerFactory.getLogger(UserMessageBean.class);

    private final int id;
    private final String email;
    private final String name;
    private final InputStream profileImgIs;
    private Image profileImg;

    protected UserMessageBean(int id, String email, String name, String encodedImg) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profileImgIs = Cache.getInstance().buildProfileImg(id, encodedImg);
        try {
            this.profileImg = new Image(this.profileImgIs);
        } catch (NullPointerException nullPointerException) {
            logger.error("Unable to fetch image");
        }
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
