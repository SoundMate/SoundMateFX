package it.soundmate.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Base64;

public class ImgBase64Repo {

    private static Logger logger = LoggerFactory.getLogger(ImgBase64Repo.class);

    private ImgBase64Repo(){}

    public static String encode(Path pathToImage) throws IOException {
        byte[] fileContent = Files.readAllBytes(pathToImage);
        return Base64.getEncoder().encodeToString(fileContent);
    }


    public static void decode(String encodedImage, Path toWrite) throws IOException {
        if (encodedImage == null) {
            return;
        }
        byte[] decodedBytes = Base64.getDecoder().decode(encodedImage);
        try {
            Files.write(toWrite, decodedBytes);
        } catch (NoSuchFileException noSuchFileException) {
            logger.error("No Such File Exception: {}", noSuchFileException.getMessage());
        }
    }
}