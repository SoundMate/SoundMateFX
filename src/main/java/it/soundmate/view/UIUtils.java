package it.soundmate.view;

import it.soundmate.constants.Style;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;


public class UIUtils {

    private UIUtils(){}

    public static void addSizedRegion(Pane vBox, int verticalSize, int horizontalSize) {
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

    public static Button createStyledButton(String text, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(text);
        button.setStyle(Style.BTN_STYLE_1);
        button.addEventHandler(ActionEvent.ACTION, eventHandler);
        return button;
    }

    public static void addStyledButtonWidth(String text, EventHandler<ActionEvent> eventHandler, Pane pane, double width) {
        Button button = new Button(text);
        button.setStyle(Style.BTN_STYLE_1);
        button.setPrefWidth(width);
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

    public static VBox textFieldWithLabel(String label, TextField textField) {
        VBox labelAndTextField = new VBox();
        Label labelTop = new Label(label);
        textField.setStyle(Style.TEXT_FIELD);
        textField.setPrefWidth(Region.USE_COMPUTED_SIZE);
        labelTop.setStyle(Style.TEXT_FIELD_LABEL);
        labelAndTextField.getChildren().addAll(labelTop, textField);
        return labelAndTextField;
    }

    public static Button createIconButton(String icon, EventHandler<ActionEvent> handler) {
        Button btn = new Button();
        Image iconImg = new Image(icon);
        btn.setGraphic(new ImageView(iconImg));
        btn.setOnAction(handler);
        btn.setStyle(Style.BTN_STYLE_1);
        return btn;
    }

    public static RadioButton createRadioButton(String text) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setStyle(Style.RADIO_BTN);
        return radioButton;
    }

    public static void styleHBoxAndVBoxMessageResults(HBox hBox, VBox vBox) {
        vBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        hBox.setPadding(new Insets(10, 25, 0, 25));
        vBox.setPadding(new Insets(10));
        hBox.setSpacing(10);
    }
}
