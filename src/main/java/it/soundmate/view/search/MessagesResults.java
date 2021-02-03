/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 02/02/21, 23:04
 * Last edited: 02/02/21, 23:04
 */

package it.soundmate.view.search;

import it.soundmate.constants.Style;
import it.soundmate.controller.logic.MessagesController;
import it.soundmate.model.*;
import it.soundmate.view.UIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MessagesResults extends ListView<Message> {

    private static final Logger logger = LoggerFactory.getLogger(MessagesResults.class);
    private final MessagesController messagesController = new MessagesController();

    public MessagesResults() {
        this.setCellFactory(param -> new MessageResult());
        this.setPrefHeight(500);
        this.setPrefWidth(600);
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
            Label subject = new Label(message.getSubject());
            subject.setStyle(Style.HIGH_LABEL);
            Label body = new Label(message.getBody());
            body.setStyle(Style.LOW_LABEL);
            messageVBox.getChildren().addAll(subject, body);
            Button readBtn = UIUtils.createStyledButton("Read", new ReadAction(message));
            messageHBox.getChildren().addAll(messageVBox, readBtn);
            return messageHBox;
        }

        private class ReadAction implements EventHandler<ActionEvent> {

            private final Message message;

            public ReadAction(Message message) {
                this.message = message;
            }

            @Override
            public void handle(ActionEvent event) {
                //Navigate to read message
            }
        }
    }


}
