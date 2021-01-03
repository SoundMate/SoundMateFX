package it.soundmate.view.main;

import it.soundmate.constants.Style;
import it.soundmate.model.User;
import it.soundmate.view.UIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessagesView extends Pane {

    private static final Logger logger = LoggerFactory.getLogger(MessagesView.class);

    private final BorderPane messagesBorderPane = new BorderPane();
    private final TabPane messagesTabPane;

    public MessagesView(User user){
        Node top = buildTopNode(user);
        Node bottom = buildBottomNode();
        this.messagesTabPane = buildTabPane(user);

        //Style
        UIUtils.setBackgroundPane("#232323", this.messagesBorderPane);
        this.messagesTabPane.setStyle("-fx-background: #232323;");

        this.messagesBorderPane.setTop(top);
        this.messagesBorderPane.setCenter(this.messagesTabPane);
        this.messagesBorderPane.setBottom(bottom);
    }

    private TabPane buildTabPane(User user) {
        TabPane tabPane = new TabPane();
        tabPane.setPrefWidth(USE_COMPUTED_SIZE);
        tabPane.setPrefHeight(USE_COMPUTED_SIZE);

        Tab requestsTab = new Tab("Requests");
        Tab chatTab = new Tab("Chat");
        requestsTab.setClosable(false);
        chatTab.setClosable(false);

        tabPane.getTabs().addAll(requestsTab, chatTab);

        return tabPane;
    }

    private Node buildBottomNode() {
        HBox bottomHBox = new HBox();
        bottomHBox.setAlignment(Pos.CENTER_RIGHT);
        bottomHBox.setPadding(new Insets(25));

        Button newMessageBtn = UIUtils.createStyledButton("New Message", new NewMessageAction());
        bottomHBox.getChildren().add(newMessageBtn);
        return bottomHBox;
    }


    private Node buildTopNode(User user) {
        Label title = new Label("Messages for "+user.getFirstName()+" "+user.getLastName());
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
