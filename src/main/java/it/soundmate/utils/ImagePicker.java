package it.soundmate.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import java.io.File;

public class ImagePicker {
    private final FileChooser fileChooser;

    public ImagePicker() {
        this.fileChooser = new FileChooser();
        configureFileChooser(this.fileChooser);
    }

    public File chooseImage(ImageView imageView) {
        File file = this.fileChooser.showOpenDialog(imageView.getScene().getWindow());
        if (file != null) {
            Image chosenImage = new Image(file.toURI().toString());
            imageView.setImage(chosenImage);
            return file;
        } else return null;
    }

    public File chooseImage(Circle circle) {
        return this.fileChooser.showOpenDialog(circle.getScene().getWindow());
    }


    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Choose an image for your band profile");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }
}