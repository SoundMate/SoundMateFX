package it.soundmate.view.main;

import it.soundmate.constants.Style;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MessagesView extends Pane {

    private VBox messagesVBox;

    public MessagesView(){
        this.messagesVBox = new VBox();
        this.messagesVBox.setAlignment(Pos.CENTER);
        Label homeLabel = new Label("Messages View");
        homeLabel.setStyle(Style.HEADER_TEXT);
        this.messagesVBox.getChildren().add(homeLabel);
    }

    public VBox getMessagesVBox() {
        return messagesVBox;
    }
}
