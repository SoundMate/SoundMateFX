/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 03/02/21, 18:30
 * Last edited: 03/02/21, 18:30
 */

package it.soundmate.view;

import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.MessagesGraphicController;
import it.soundmate.model.Message;
import it.soundmate.model.User;
import it.soundmate.view.main.MessagesView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MessageDetailView extends Pane {

    private final User user;
    private final Message message;
    private VBox contentVBox;
    private final MessagesGraphicController messagesGraphicController = new MessagesGraphicController();
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());
    private final MessagesView messagesView;

    public VBox getContentVBox() {
        return contentVBox;
    }

    public MessageDetailView(Message message, MessagesView messagesView, User user) {
        this.message = message;
        this.user = user;
        this.contentVBox = buildContentVBox(message);
        this.messagesView = messagesView;
    }

    private VBox buildContentVBox(Message message) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(25));
        Label body = new Label(message.getBody());
        body.setStyle(Style.MID_LABEL);
        vBox.getChildren().add(body);
        vBox.getChildren().add(backBtn);
        return vBox;
    }


    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            messagesView.setMessagePage(new MessagesView(user).getContentVBox());
        }
    }
}
