/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/01/21, 13:34
 * Last edited: 12/01/21, 13:34
 */

package it.soundmate.bean.searchbeans;

import it.soundmate.model.UserType;
import it.soundmate.utils.Cache;

import java.io.InputStream;

public class UserResultBean {

    private final int id;
    private final String email;
    private final InputStream profileImg;
    private final UserType userType;

    public UserResultBean(int id, String email, String encodedImg, UserType userType) {
        this.id = id;
        this.email = email;
        this.profileImg = Cache.getInstance().buildProfileImg(id, encodedImg);
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public InputStream getProfileImg() {
        return profileImg;
    }

    public UserType getUserType() {
        return userType;
    }
}
