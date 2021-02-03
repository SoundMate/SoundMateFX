/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 02/02/21, 23:50
 * Last edited: 02/02/21, 23:50
 */

package it.soundmate.controller.graphic;

import it.soundmate.view.NewMessageView;
import javafx.scene.layout.VBox;

public class MessagesGraphicController {

    private final VBox contentVBox;

    public MessagesGraphicController(VBox contentVBox) {
        this.contentVBox = contentVBox;
    }

    public void setNewMessageView(NewMessageView newMessageView) {
        this.contentVBox.getChildren().set(0, newMessageView.getContentVBox());
    }
}
