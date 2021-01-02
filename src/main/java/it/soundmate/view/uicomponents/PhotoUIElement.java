package it.soundmate.view.uicomponents;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.InputStream;

public class PhotoUIElement extends Pane {

    public VBox getElementVBox() {
        return elementVBox;
    }

    private VBox elementVBox;
    private Label photoDate;
    private Image photoImage;
    private ImageView photoImageView;

    public PhotoUIElement(Label photoDate, InputStream inputStream) {
        this.photoDate = photoDate;
        this.photoImage = new Image(inputStream);
        this.photoImageView = new ImageView(this.photoImage);
        this.photoImageView.setFitWidth(100);
        this.photoImageView.setFitHeight(70);
        this.elementVBox = buildElementVBox();
    }

    private VBox buildElementVBox() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        vBox.getChildren().addAll(this.photoImageView, this.photoDate);
        return vBox;
    }
}
