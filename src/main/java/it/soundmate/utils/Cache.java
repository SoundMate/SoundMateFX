package it.soundmate.utils;

import it.soundmate.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Cache {

    private static Cache instance;
    private static final Logger logger = LoggerFactory.getLogger(Cache.class);

    public static Cache getInstance() {
        if (instance == null) {
            instance = new Cache();
        }
        return instance;
    }

    public String buildProfilePicCacheName(User user) {
        return "../../resources/soundmate/userCache/" + user.getUserID() + "_profilePic.png";
    }

    public boolean saveProfilePicToCache(User user) {
        String cacheName = buildProfilePicCacheName(user);
        try {
            InputStream inputStream = user.getProfilePic();
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            File file = new File(cacheName);
            ImageIO.write(bufferedImage, "png", file);
            return true;
        } catch (IOException e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    public InputStream getProfilePicFromCache(User user){
        String cacheName = buildProfilePicCacheName(user);
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
}
