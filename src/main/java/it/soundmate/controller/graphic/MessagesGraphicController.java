/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 02/02/21, 23:50
 * Last edited: 02/02/21, 23:50
 */

package it.soundmate.controller.graphic;

import it.soundmate.model.Message;
import it.soundmate.view.MessageDetailView;
import it.soundmate.view.main.MessagesView;

public class MessagesGraphicController {


    public MessagesGraphicController() {
    }

    public void setDetailMessage(MessagesView messagesView, MessageDetailView messageDetailView) {
        messagesView.getContentVBox().getChildren().set(0, messageDetailView);
    }

    public void setMessageView() {

    }
}
