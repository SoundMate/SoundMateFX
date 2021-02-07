/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/01/21, 13:34
 * Last edited: 12/01/21, 13:34
 */

package it.soundmate.bean.searchbeans;

import it.soundmate.model.User;
import it.soundmate.model.UserType;
import it.soundmate.utils.Cache;
import javafx.scene.image.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class UserResultBean {

    private static final Logger logger = LoggerFactory.getLogger(UserResultBean.class);
    private  int id;
    private  String email;
    private  InputStream profileImgIs;
    private  Image profileImg;
    private  UserType userType;
    private  String city;
    private User searcher;
    private static final String EMPTY_NAME = "";

    public UserResultBean() {
    }

    public UserResultBean(int id, String email, String encodedImg, String city, UserType userType) {
        this.id = id;
        this.email = email;
        this.profileImgIs = Cache.getInstance().buildProfileImg(id, encodedImg);
        this.city = city;
        try {
            this.profileImg = new Image(this.profileImgIs);
        } catch (NullPointerException nullPointerException) {
            logger.error("Null Pointer Exception: {}", nullPointerException.getMessage());
        }
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public InputStream getProfileImgIs() {
        return profileImgIs;
    }

    public Image getProfileImg() {
        return profileImg;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getCity() {
        return city;
    }

    public User getSearcher() {
        return searcher;
    }

    public void setSearcher(User searcher) {
        this.searcher = searcher;
    }

    public String getName() {
        return EMPTY_NAME;
    }
}
