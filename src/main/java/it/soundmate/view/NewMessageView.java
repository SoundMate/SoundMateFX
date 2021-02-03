/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 02/02/21, 23:17
 * Last edited: 02/02/21, 23:17
 */

package it.soundmate.view;

import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.logic.MessagesController;
import it.soundmate.controller.logic.SearchController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Message;
import it.soundmate.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NewMessageView extends Pane {

    private final User sender;
    private final SearchController searchController = new SearchController();
    private static final Logger logger = LoggerFactory.getLogger(NewMessageView.class);
    private final SoloResultBean receiver;

    //UI
    private VBox contentVBox;
    private final TextField subjectTextField = new TextField();
    private final TextArea messageTextArea = new TextArea();
    private final Button sendBtn = UIUtils.createStyledButton("Send", new SendAction());

    public NewMessageView(User sender, SoloResultBean receiver) {
        this.sender = sender;
        this.receiver = receiver;
        buildContentVBox();
    }

    private void buildContentVBox() {
        this.contentVBox = new VBox();
        this.contentVBox.setPadding(new Insets(25));
        this.contentVBox.setSpacing(10);
        Label messageTo = new Label("New Message to "+this.receiver.getName());
        messageTo.setStyle(Style.HEADER_TEXT);

        Label subjectLabel = new Label("Subject");
        subjectLabel.setStyle(Style.HIGH_LABEL);
        Label bodyLabel = new Label("Message");
        bodyLabel.setStyle(Style.HIGH_LABEL);

        this.contentVBox.getChildren().addAll(messageTo, subjectLabel, subjectTextField, bodyLabel, messageTextArea, sendBtn);
    }

    public VBox getContentVBox() {
        return contentVBox;
    }

    private class SendAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                MessagesController messagesController = new MessagesController();
                Message message = new Message(sender.getId(), receiver.getId(), subjectTextField.getText(), messageTextArea.getText());
                messagesController.sendMessage(message);
                Alert confirmedDialog = new Alert(Alert.AlertType.INFORMATION);
                confirmedDialog.setTitle("Message sent!");
                confirmedDialog.setHeaderText(null);
                confirmedDialog.setContentText("Message has been sent to "+receiver.getName());
                confirmedDialog.showAndWait();
            } catch (RepositoryException | InputException exception) {
                logger.error("Repository Exception: {}", exception.getMessage());
                Alert confirmedDialog = new Alert(Alert.AlertType.ERROR);
                confirmedDialog.setTitle("Message has not been sent!");
                confirmedDialog.setHeaderText(null);
                confirmedDialog.setContentText(exception.getMessage());
                confirmedDialog.showAndWait();
            }
        }
    }

}
