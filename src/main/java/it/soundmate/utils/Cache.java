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
        return "src/main/resources/soundmate/userCache/" + user.getUserID() + "_profilePic.png";
    }

    public boolean saveProfilePicToCache(User user) {
        String cacheName = buildProfilePicCacheName(user);
        try {
            InputStream initialStream = user.getProfilePic();
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

    public InputStream getProfilePicFromCache(User user) {
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
