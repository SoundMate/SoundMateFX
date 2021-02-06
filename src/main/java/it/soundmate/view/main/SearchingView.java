/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 25/01/21, 13:50
 * Last edited: 25/01/21, 13:50
 */

package it.soundmate.view.main;

import it.soundmate.bean.searchbeans.UserResultBean;
import it.soundmate.model.User;
import it.soundmate.view.NewMessageView;
import it.soundmate.view.search.BandSearchView;
import it.soundmate.view.search.RenterSearchView;
import it.soundmate.view.search.RoomSearchView;
import it.soundmate.view.search.SoloSearchView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Searching View
 *
 * Sia HomeView che SearchView mostrano al loro interno i risultati di ricerche.
 * Nella SearchView le ricerche sono "personalizzate" con filtri ecc...
 * Nella HomeView le ricerche sono fatte attraverso la posizione (attributo city) dell'utente.
 * Entrambe le view condividono lo stesso stile di risultati mostrati e le view a cui portano
 * tali risultati. Per implementare questo comportamento la SearchingView fa il set del contenuto
 * del VBox primario della view in questione (SearchView o HomeView)
 * Perciò è necessario che entrambe abbiano come elemento di view primario un VBox.
 * Nella HomeView questo VBox avrà come suo primo elemento (index 0) un altro
 * VBox con tutto il contenuto della pagina. Nella SearchView il VBox primario avrà
 * come primo elemento un BorderPane che a sua volta cambia a seconda del tipo di ricerca.
 * */

public abstract class SearchingView extends Pane {

    private final User user;

    protected SearchingView(User user) {
        this.user = user;
    }

    public void setDetailViewSolo(SoloSearchView soloSearchView) {
        getContentVBox().getChildren().set(0, soloSearchView.getContentVBox());
    }

    public void setDetailViewRenter(RenterSearchView renterSearchView) {
        getContentVBox().getChildren().set(0, renterSearchView.getMainVBox());
    }

    public void backToSearchView(){
        getContentVBox().getChildren().set(0, new SearchView(user).getContentVBox());
    }

    public void backToHomeView() {
        getContentVBox().getChildren().set(0, new HomeView(user).getContentVBox());
    }

    public abstract VBox getContentVBox();

    public void setDetailRoom(RoomSearchView roomSearchView) {
        getContentVBox().getChildren().set(0, roomSearchView);
    }

    public void setNewMessageView(UserResultBean soloResultBean) {
        getContentVBox().getChildren().set(0, new NewMessageView(user, soloResultBean).getContentVBox());
    }

    public void setDetailBand(BandSearchView bandSearchView) {
        getContentVBox().getChildren().set(0, bandSearchView.getContentVBox());
    }
}
