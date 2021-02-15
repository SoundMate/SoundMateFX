/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 02/02/21, 23:17
 * Last edited: 02/02/21, 23:17
 */

package it.soundmate.view;
import it.soundmate.bean.searchbeans.UserResultBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.logic.MessagesController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Message;
import it.soundmate.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NewMessageView extends Pane {

    private final User user;
    private static final Logger logger = LoggerFactory.getLogger(NewMessageView.class);
    private final UserResultBean userResultBean;

    //UI
    private VBox contentVBox;
    private final TextField subjectTextField = new TextField("Subject");
    private final TextArea messageTextArea = new TextArea("Message");
    private final Button sendBtn = UIUtils.createStyledButton("Send", new SendAction());

    public NewMessageView(User user, UserResultBean userResultBean) {
        this.userResultBean = userResultBean;
        this.user = user;
        buildContentVBox();
    }

    private void buildContentVBox() {
        this.contentVBox = new VBox();
        Label messageTo = new Label("New Message to: "+userResultBean.getName());
        messageTo.setStyle(Style.HEADER_TEXT);
        this.contentVBox.getChildren().addAll(messageTo);
        this.contentVBox.getChildren().addAll(subjectTextField);
        this.contentVBox.getChildren().addAll(messageTextArea);
        this.contentVBox.getChildren().add(sendBtn);
    }

    public VBox getContentVBox() {
        return contentVBox;
    }

    private class SendAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                MessagesController messagesController = new MessagesController();
                Message message = new Message(user.getId(), userResultBean.getId(), subjectTextField.getText(),messageTextArea.getText(), user.getUserType());
                messagesController.sendMessage(message);
                Alert confirmedDialog = new Alert(Alert.AlertType.INFORMATION);
                confirmedDialog.setTitle("Message sent");
                confirmedDialog.setHeaderText(null);
                confirmedDialog.setContentText("Message has been sent to "+userResultBean.getName());
                confirmedDialog.showAndWait();
            } catch (RepositoryException repositoryException) {
                Alert confirmedDialog = new Alert(Alert.AlertType.INFORMATION);
                confirmedDialog.setTitle("Message NOT sent");
                confirmedDialog.setHeaderText(null);
                confirmedDialog.setContentText("Message has not been sent because of Repository Exception: "+repositoryException.getMessage());
                confirmedDialog.showAndWait();
                logger.error("Repository Exception: {}", repositoryException.getMessage());
            }
        }
    }

}
