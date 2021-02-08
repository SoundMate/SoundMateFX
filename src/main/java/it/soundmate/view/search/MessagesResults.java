/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 02/02/21, 23:04
 * Last edited: 02/02/21, 23:04
 */

package it.soundmate.view.search;

import it.soundmate.constants.Style;
import it.soundmate.model.*;
import it.soundmate.view.MessageDetailView;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.MessagesView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MessagesResults extends ListView<Message> {

    private static final Logger logger = LoggerFactory.getLogger(MessagesResults.class);
    private final MessagesView messagesView;
    private final User user;

    public MessagesResults(MessagesView messagesView, User user) {
        this.setCellFactory(param -> new MessageResult());
        this.setPrefHeight(500);
        this.setPrefWidth(600);
        this.messagesView = messagesView;
        this.user = user;
    }

    public boolean isEmpty() {
        return this.getItems().isEmpty();
    }

    public int length() {
        return this.getItems().size();
    }

    public class MessageResult extends ListCell<Message> {
        @Override
        protected void updateItem(Message message, boolean empty) {
            super.updateItem(message, empty);
            if (message != null) {
                logger.info("Building message result");
                HBox hBox = buildResultHBox(message);
                this.setStyle("-fx-background-color: #232323;");
                setGraphic(hBox);
            } else {
                this.setStyle("-fx-background-color: #232323");
            }
        }

        @NotNull
        private HBox buildResultHBox(Message message) {
            HBox messageHBox = new HBox();
            VBox messageVBox = new VBox();
            UIUtils.styleHBoxAndVBoxMessageResults(messageHBox, messageVBox);
            Label sender = new Label(message.getSender().getName());
            sender.setStyle(Style.HIGH_LABEL);
            Label body = new Label(message.getBody());
            body.setStyle(Style.LOW_LABEL);
            messageVBox.getChildren().addAll(sender, body);
            Button readBtn = UIUtils.createStyledButton("Read", new ReadAction(message));
            Circle profileImg = new Circle();
            profileImg.setRadius(24);
            if (message.getUserMessageBean().getProfileImgIs() != null) {
                profileImg.setFill(new ImagePattern(message.getSender().getProfileImg()));
            }
            messageHBox.getChildren().addAll(profileImg, messageVBox);
            UIUtils.addRegion(null, messageHBox);
            messageHBox.getChildren().add(readBtn);
            return messageHBox;
        }

        private class ReadAction implements EventHandler<ActionEvent> {

            private final Message message;

            public ReadAction(Message message) {
                this.message = message;
            }

            @Override
            public void handle(ActionEvent event) {
                messagesView.setMessagePage(new MessageDetailView(message, messagesView, user).getContentVBox());
            }
        }
    }


}
