package it.soundmate.view.uicomponents;

import it.soundmate.constants.Style;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class NavigationItem extends StackPane {

    private final NavigationType type;
    private boolean isSelected;
    private final HBox hBox;

    public NavigationItem (String text, String imgName, boolean selected, NavigationType navigationType) {
        this.setAlignment(Pos.CENTER);
        this.type = navigationType;
        this.hBox = new HBox();
        this.hBox.setAlignment(Pos.CENTER);
        this.hBox.setSpacing(15);
        Image icon = new Image(imgName);
        ImageView iconImgView = new ImageView(icon);
        iconImgView.setFitWidth(20);
        iconImgView.setFitHeight(20);
        iconImgView.setPreserveRatio(true);
        Label label = new Label(text);
        label.setStyle(Style.TEXT_FIELD_LABEL);
        this.hBox.getChildren().addAll(iconImgView, label);
        this.getChildren().add(hBox);
        isSelected = selected;
        if (isSelected) {
            this.setStyle("-fx-background-color: #00b540");
        }
        this.setPadding(new Insets(15));
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean selected){
        this.isSelected = selected;
        if (selected) this.setStyle("-fx-background-color: #00b540");
        else this.setStyle("-fx-background-color: transparent");
    }

    public HBox gethBox(){
        return this.hBox;
    }

    public NavigationType getType() {
        return this.type;
    }
}
