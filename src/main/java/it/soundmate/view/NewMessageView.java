/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 02/02/21, 23:17
 * Last edited: 02/02/21, 23:17
 */

package it.soundmate.view;

import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.bean.searchbeans.RoomRenterResultBean;
import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.bean.searchbeans.UserResultBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.logic.MessagesController;
import it.soundmate.controller.logic.SearchController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Message;
import it.soundmate.model.User;
import it.soundmate.view.uicomponents.AutocompletionTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NewMessageView extends Pane {

    private final User user;
    private final SearchController searchController = new SearchController();
    private static final Logger logger = LoggerFactory.getLogger(NewMessageView.class);

    //UI
    private VBox contentVBox;
    private final AutocompletionTextField searchTextField = new AutocompletionTextField();
    private final TextField subjectTextField = new TextField("Subject");
    private final TextArea messageTextArea = new TextArea("Message");
    private final Button sendBtn = UIUtils.createStyledButton("Send", new SendAction());

    public NewMessageView(User user) {
        this.user = user;
        buildContentVBox();
    }

    private void buildContentVBox() {
        this.contentVBox = new VBox();
        Label messageTo = new Label("New Message");
        messageTo.setStyle(Style.HEADER_TEXT);
        searchUsers();
        Button searchBtn = UIUtils.createStyledButton("Search", new SearchUserAction());
        HBox searchHBox = new HBox(this.searchTextField, searchBtn);
        searchHBox.setSpacing(10);
        this.contentVBox.getChildren().addAll(messageTo, searchHBox);
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
                List<UserResultBean> userResultBeans = searchController.performSearch(searchTextField.getText(), new boolean[]{false, false, false}, new String[]{"NONE", "NONE", ""}, user);
                UserResultBean userResultBean = userResultBeans.get(0);
                Message message = new Message(user.getId(), userResultBean.getId(), subjectTextField.getText(), messageTextArea.getText());
                messagesController.sendMessage(message);
            } catch (RepositoryException repositoryException) {
                logger.error("Repository Exception: {}", repositoryException.getMessage());
            }
        }
    }

    private class SearchUserAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            searchUsers();
        }
    }

    public void searchUsers() {
        List<UserResultBean> userResultBeans = searchController.performSearch(searchTextField.getText(), new boolean[]{false, false, false}, new String[]{"NONE", "NONE", ""}, user);
        for (UserResultBean userResultBean : userResultBeans) {
            switch (userResultBean.getUserType()){
                case SOLO:
                    SoloResultBean soloResultBean = (SoloResultBean) userResultBean;
                    searchTextField.getEntries().add(soloResultBean.getFirstName()+" "+soloResultBean.getLastName());
                    break;
                case ROOM_RENTER:
                    RoomRenterResultBean roomRenterResultBean = (RoomRenterResultBean) userResultBean;
                    searchTextField.getEntries().add(roomRenterResultBean.getName());
                    break;
                case BAND:
                    BandResultBean bandResultBean = (BandResultBean) userResultBean;
                    searchTextField.getEntries().add(bandResultBean.getBandName());
                    break;
            }
        }
    }
}
