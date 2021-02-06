/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 13/01/21, 15:09
 * Last edited: 13/01/21, 15:09
 */

package it.soundmate.view.search;

import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.constants.Style;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.SearchingView;
import it.soundmate.view.uicomponents.InstrumentGraphics;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class SoloSearchView extends Pane {

    private final boolean comingFromSearch;
    private final SoloResultBean soloResultBean;
    private final SearchingView searchingView;

    //UI
    private final VBox contentVBox;
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());

    public SoloSearchView(SoloResultBean soloResultBean, SearchingView searchingView, boolean comingFromSearch) {
        this.searchingView = searchingView;
        this.soloResultBean = soloResultBean;
        this.comingFromSearch = comingFromSearch;
        UIUtils.setBackgroundPane("#232323", this);
        this.contentVBox = buildResultView(soloResultBean);
    }

    private VBox buildResultView(SoloResultBean soloResultBean) {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(25));
        vBox.setSpacing(10);
        HBox userInfoHBox = buildUserInfoHBox(soloResultBean);
        HBox genresHBox = buildGenresHBox(soloResultBean);
        Label genresLabel = new Label("Preferred Genres");
        genresLabel.setStyle(Style.HIGH_LABEL);
        Label instrumentLabel = new Label("Instruments");
        instrumentLabel.setStyle(Style.HIGH_LABEL);
        HBox instrumentHBox = buildInstrumentsHBox(soloResultBean);
        vBox.getChildren().addAll(userInfoHBox,genresLabel, genresHBox, instrumentLabel, instrumentHBox);
        Button newMessageBtn = UIUtils.createStyledButton("New Message", new NewMessageAction());
        vBox.getChildren().addAll(newMessageBtn, backBtn);
        return vBox;
    }

    private HBox buildInstrumentsHBox(SoloResultBean soloResultBean) {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        List<InstrumentGraphics> instrumentGraphicsList = new ArrayList<>();
        List<VBox> nameAndGraphic = new ArrayList<>();
        for (String instrument : soloResultBean.getInstrumentList()) {
            InstrumentGraphics instrumentGraphics = InstrumentGraphics.returnInstrument(instrument);
            instrumentGraphicsList.add(instrumentGraphics);
        }
        for (InstrumentGraphics instrumentGraphics : instrumentGraphicsList) {
            VBox instrumentVBox = new VBox();
            instrumentVBox.setSpacing(10);
            Circle image = new Circle();
            image.setRadius(25);
            image.setFill(new ImagePattern(instrumentGraphics.getSource()));
            Label name = new Label(instrumentGraphics.getName());
            name.setStyle(Style.MID_LABEL);
            instrumentVBox.getChildren().addAll(image, name);
            nameAndGraphic.add(instrumentVBox);
        }
        hBox.getChildren().addAll(nameAndGraphic);
        return hBox;
    }

    private HBox buildGenresHBox(SoloResultBean soloResultBean) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(buildGenres(soloResultBean));
        return hBox;
    }

    private List<Label> buildGenres(SoloResultBean soloResultBean) {
        return BandSearchView.getGenresHBox(soloResultBean.getGenreList());
    }

    private HBox buildUserInfoHBox(SoloResultBean soloResultBean) {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        Circle image = new Circle();
        image.setRadius(60);
        if (soloResultBean.getProfileImgIs() != null) {
            ImagePattern imagePattern = new ImagePattern(soloResultBean.getProfileImg());
            image.setFill(imagePattern);
        }
        Label nameLabel = new Label(soloResultBean.getFirstName()+" "+soloResultBean.getLastName());
        nameLabel.setStyle(Style.HEADER_TEXT);
        hBox.getChildren().addAll(image, nameLabel);
        return hBox;
    }

    public VBox getContentVBox() {
        return contentVBox;
    }

    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (comingFromSearch) searchingView.backToSearchView();
            else searchingView.backToHomeView();
        }
    }

    private class NewMessageAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            searchingView.setNewMessageView(soloResultBean);
        }
    }
}
