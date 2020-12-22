package it.soundmate.view;

import it.soundmate.constants.Style;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;


public class UIUtils {

    private UIUtils(){}

    public static void addSizedRegion(VBox vBox, int verticalSize, int horizontalSize) {
        Region sizedRegion = new Region();
        sizedRegion.setPrefHeight(verticalSize);
        sizedRegion.setPrefWidth(horizontalSize);
        vBox.getChildren().add(sizedRegion);
    }

    public static void addRegion(VBox vBox, HBox hBox) {
        Region expanded = new Region();
        if (vBox == null && hBox != null) {
            hBox.getChildren().add(expanded);
            HBox.setHgrow(expanded, Priority.ALWAYS);
        } else if (vBox != null && hBox == null) {
            vBox.getChildren().add(expanded);
            VBox.setVgrow(expanded, Priority.ALWAYS);
        }
    }

    public static void addStyledButton(String text, EventHandler<ActionEvent> eventHandler, Pane pane) {
        Button button = new Button(text);
        button.setStyle(Style.BTN_STYLE_1);
        button.addEventHandler(ActionEvent.ACTION, eventHandler);
        pane.getChildren().add(button);
    }

    public static void setBackgroundImagePane(Image image, Pane pane) {
        /*
         * BackgroundSize Doc:
         * BackgroundSize(double width, double height, boolean widthAsPercentage, boolean heightAsPercentage,
         * boolean contain, boolean cover)
         * */
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize);
        pane.setBackground(new Background(backgroundImage));
    }

    public static void setBackgroundPane(String color, Pane pane) {
        pane.setStyle("-fx-background-color: "+color+";");
    }
}
