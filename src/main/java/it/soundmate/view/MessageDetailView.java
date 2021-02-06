/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 03/02/21, 18:30
 * Last edited: 03/02/21, 18:30
 */

package it.soundmate.view;

import it.soundmate.constants.Style;
import it.soundmate.controller.logic.MessagesController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Message;
import it.soundmate.model.User;
import it.soundmate.view.main.MessagesView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageDetailView extends Pane {

    private static final Logger logger = LoggerFactory.getLogger(MessageDetailView.class);
    private final User user;
    private final Message message;
    private final VBox contentVBox;
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());
    private final MessagesView messagesView;
    private final TextArea replyTextArea = new TextArea();

    public VBox getContentVBox() {
        return contentVBox;
    }

    public MessageDetailView(Message message, MessagesView messagesView, User user) {
        UIUtils.setBackgroundPane("#232323", this);
        this.message = message;
        this.user = user;
        this.contentVBox = buildContentVBox(message);
        this.messagesView = messagesView;
    }

    private VBox buildContentVBox(Message message) {
        VBox vBox = new VBox();
        UIUtils.setBackgroundPane("#232323", vBox);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(25));
        Label messageFrom = new Label("Message from: "+message.getUserMessageBean().getName());
        messageFrom.setStyle(Style.HEADER_TEXT);
        Label subject = new Label("Subject: "+message.getSubject());
        subject.setStyle(Style.HIGH_LABEL);
        Label body = new Label(message.getBody());
        body.setStyle(Style.MID_LABEL);
        vBox.getChildren().addAll(messageFrom, subject, body);
        Label replyLabel = new Label("Reply");
        replyLabel.setStyle(Style.HIGH_LABEL);
        Button replyBtn = UIUtils.createStyledButton("Reply", new ReplyAction());
        vBox.getChildren().addAll(replyTextArea, replyBtn, backBtn);
        return vBox;
    }


    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            messagesView.setMessagePage(new MessagesView(user).getContentVBox());
        }
    }

    private class ReplyAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Message reply = new Message(user.getId(), message.getIdSender(), message.getSubject(), replyTextArea.getText(), user.getUserType());
            try {
                MessagesController messagesController = new MessagesController();
                messagesController.sendMessage(reply);
                Alert confirmedDialog = new Alert(Alert.AlertType.INFORMATION);
                confirmedDialog.setTitle("Message sent");
                confirmedDialog.setHeaderText(null);
                confirmedDialog.setContentText("Message has been sent to "+message.getUserMessageBean().getName());
                confirmedDialog.showAndWait();
            } catch (RepositoryException | InputException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
