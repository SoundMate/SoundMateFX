package it.soundmate.controller.database;

import it.soundmate.utils.ImgBase64Repo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;

class ImgBase64Test {


    @TempDir
    public File folder;
    private Path imgPath;

    @BeforeEach
    public void setUp() throws Exception {
        imgPath = Paths.get(
                Objects.requireNonNull(ImgBase64Test.class.getClassLoader()
                        .getResource("Gpus.jpg"))
                        .toURI()
        );
    }

    @Test
    void encode() throws IOException {

        File created = new File(folder, "gpus");
        String encodedImage = ImgBase64Repo.encode(imgPath);

        byte[] decodedBytes = Base64.getDecoder().decode(encodedImage);
        Files.write(created.toPath(), decodedBytes);

        Assertions.assertThat(created).hasSameBinaryContentAs(imgPath.toFile());
    }

    @Test
    void decode() throws IOException {
        File toWrite = new File(folder, "testImg");
        String encodedImage = ImgBase64Repo.encode(imgPath);

        ImgBase64Repo.decode(encodedImage, toWrite.toPath());

        Assertions.assertThat(toWrite).hasSameBinaryContentAs(imgPath.toFile());
    }



}
