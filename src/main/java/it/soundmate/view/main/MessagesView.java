package it.soundmate.view.main;

import it.soundmate.constants.Style;
import it.soundmate.controller.logic.MessagesController;
import it.soundmate.controller.logic.NotificationsController;
import it.soundmate.model.*;
import it.soundmate.view.UIUtils;
import it.soundmate.view.search.MessagesResults;
import it.soundmate.view.search.NotificationsResults;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessagesView extends Pane {

    private static final Logger logger = LoggerFactory.getLogger(MessagesView.class);


    public VBox getContentVBox() {
        return contentVBox;
    }

    private final VBox contentVBox = new VBox();
    private final BorderPane messagesBorderPane = new BorderPane();
    private final MessagesResults messageResults;
    private final NotificationsResults notificationsResults = new NotificationsResults();
    private final NotificationsController notificationsController = new NotificationsController();
    private final MessagesController messagesController = new MessagesController();

    //UI
    private final Tab notificationsTab = new Tab("Notifications");
    private final Tab messagesTab = new Tab("Messages");
    private final TabPane messagesTabPane = new TabPane();

    public MessagesView(User user){
        this.messageResults = new MessagesResults(this, user);
        Node top = buildTopNode();
        //Style
        UIUtils.setBackgroundPane("#232323", this.contentVBox);

        this.messagesBorderPane.setTop(top);
        buildCenterNode(user);
        this.contentVBox.getChildren().add(this.messagesBorderPane);
    }

    private void buildCenterNode(User user) {
        this.messagesTabPane.getTabs().addAll(messagesTab, notificationsTab);
        buildNotificationsTab(user);
        buildMessagesTab(user);
        this.messagesBorderPane.setCenter(this.messagesTabPane);
    }


    private void buildMessagesTab(User user) {
        this.messagesTab.setClosable(false);
        List<Message> messageList = messagesController.getMessagesForUser(user);
        ObservableList<Message> messageObservableList = FXCollections.observableArrayList(messageList);
        this.messageResults.setItems(messageObservableList);
        this.messageResults.setStyle("-fx-background-color: #232323; -fx-border-color: #232323");
        this.messagesTab.setContent(this.messageResults);
    }

    private void buildNotificationsTab(User user) {
        List<Notification> notificationList = notificationsController.getMessagesForUser(user);
        ObservableList<Notification> observableList = FXCollections.observableArrayList(notificationList);
        this.notificationsResults.setItems(observableList);
        this.notificationsResults.setStyle("-fx-background-color: #232323; -fx-border-color: #232323");
        this.messagesBorderPane.setCenter(this.notificationsResults);
        this.notificationsTab.setClosable(false);
        this.notificationsTab.setContent(this.notificationsResults);
    }


    private Node buildTopNode() {
        Label title = new Label("Messages");
        title.setStyle(Style.HEADER_TEXT);
        title.setPadding(new Insets(25, 0, 25, 15));
        return title;
    }

    public void setMessagePage(Pane messagePage) {
        this.contentVBox.getChildren().set(0, messagePage);
        logger.info("Message Page Set");
    }


}
