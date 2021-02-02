package it.soundmate.view.main;

import it.soundmate.constants.Style;
import it.soundmate.controller.logic.NotificationsController;
import it.soundmate.model.Notification;
import it.soundmate.model.User;
import it.soundmate.view.UIUtils;
import it.soundmate.view.search.NotificationsResults;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessagesView extends Pane {

    private static final Logger logger = LoggerFactory.getLogger(MessagesView.class);

    private final BorderPane messagesBorderPane = new BorderPane();
    private final NotificationsResults notificationsResults = new NotificationsResults();
    private final NotificationsController notificationsController = new NotificationsController();

    public MessagesView(User user){
        Node top = buildTopNode();
        Node bottom = buildBottomNode();
        buildCenterNode(user);

        //Style
        UIUtils.setBackgroundPane("#232323", this.messagesBorderPane);

        this.messagesBorderPane.setTop(top);
        this.messagesBorderPane.setBottom(bottom);
    }

    private void buildCenterNode(User user) {
        List<Notification> notificationList = notificationsController.getMessagesForUser(user);
        ObservableList<Notification> observableList = FXCollections.observableArrayList(notificationList);
        this.notificationsResults.setItems(observableList);
        this.notificationsResults.setStyle("-fx-background-color: #232323; -fx-border-color: #232323");
        this.messagesBorderPane.setCenter(this.notificationsResults);
    }


    private Node buildBottomNode() {
        HBox bottomHBox = new HBox();
        bottomHBox.setAlignment(Pos.CENTER_RIGHT);
        bottomHBox.setPadding(new Insets(25));

        Button newMessageBtn = UIUtils.createStyledButton("New Message", new NewMessageAction());
        bottomHBox.getChildren().add(newMessageBtn);
        return bottomHBox;
    }


    private Node buildTopNode() {
        Label title = new Label("Messages");
        title.setStyle(Style.HEADER_TEXT);
        title.setPadding(new Insets(25, 0, 25, 15));
        return title;
    }

    public BorderPane getMessagesBorderPane() {
        return messagesBorderPane;
    }


    private class NewMessageAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("New Message Btn Clicked");
        }
    }
}
