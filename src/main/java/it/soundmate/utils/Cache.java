/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 08/01/21, 13:36
 * Last edited: 08/01/21, 13:36
 */

package it.soundmate.utils;

import it.soundmate.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;

public class Cache {

    private static Cache instance;
    private static final Logger logger = LoggerFactory.getLogger(Cache.class);

    public static Cache getInstance() {
        if (instance == null) {
            instance = new Cache();
        }
        return instance;
    }

    public String buildProfilePicCacheName(int id) {
        return "src/main/resources/soundmate/userCache/" + id + "_profilePic.png";
    }

    public boolean saveProfilePicToCache(User user, File image) {
        String cacheName = buildProfilePicCacheName(user.getId());
        try (InputStream initialStream = new FileInputStream(image)) {
            byte[] buffer = new byte[initialStream.available()];
            if (initialStream.read(buffer) > 0){
                File targetFile = new File(cacheName);
                try (OutputStream outStream = new FileOutputStream(targetFile)) {
                    outStream.write(buffer);
                }
                return true;
            }
            return false;
        } catch (IOException e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    public InputStream getProfilePicFromCache(int id) {
        String cacheName = buildProfilePicCacheName(id);
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(cacheName));
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkProfilePicInCache(User user) {
        String cacheName = buildProfilePicCacheName(user.getId());
        File fileTest = new File(cacheName);
        return fileTest.exists();
    }

    public InputStream buildProfileImg(int id, String encodedProfileImg) {
        try {
            ImgBase64Repo.decode(encodedProfileImg, Path.of(Cache.getInstance().buildProfilePicCacheName(id)));
            return Cache.getInstance().getProfilePicFromCache(id);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}