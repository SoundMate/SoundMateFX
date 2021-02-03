/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 03/02/21, 21:06
 * Last edited: 03/02/21, 21:06
 */

package it.soundmate.view.search;

import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.constants.Style;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.SearchingView;
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
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class BandSearchView extends Pane {

    private final boolean comingFromSearch;
    private final BandResultBean bandResultBean;
    private final SearchingView searchingView;

    //UI
    private VBox contentVBox;
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());


    public VBox getContentVBox() {
        return contentVBox;
    }

    public BandSearchView(boolean comingFromSearch, BandResultBean bandResultBean, SearchingView searchingView) {
        this.comingFromSearch = comingFromSearch;
        this.bandResultBean = bandResultBean;
        this.searchingView = searchingView;
        UIUtils.setBackgroundPane("#232323", this);
        this.contentVBox = buildResultView(bandResultBean);
    }

    private VBox buildResultView(BandResultBean bandResultBean) {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(25));

        //Profile picture
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(600);
        rectangle.setHeight(175);
        if (bandResultBean.getProfileImg() != null) {
            rectangle.setFill(new ImagePattern(bandResultBean.getProfileImg()));
        }
        vBox.getChildren().add(rectangle);
        Label nameLabel = new Label(bandResultBean.getName());
        nameLabel.setStyle(Style.HEADER_TEXT);
        vBox.getChildren().add(nameLabel);

        Label genresLabel = new Label("Genres");
        genresLabel.setStyle(Style.HIGH_LABEL);
        HBox genresList = buildGenresHBox(bandResultBean);
        Button sendMessageBtn = UIUtils.createStyledButton("Send Message", new SendMessageAction());
        vBox.getChildren().addAll(genresLabel, genresList);
        vBox.getChildren().add(sendMessageBtn);
        vBox.getChildren().add(backBtn);
        return vBox;
    }

    private HBox buildGenresHBox(BandResultBean soloResultBean) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(buildGenres(soloResultBean));
        return hBox;
    }

    private List<Label> buildGenres(BandResultBean bandResultBean) {
        return getGenresHBox(bandResultBean.getGenreList());
    }

    public static List<Label> getGenresHBox(List<String> genreList) {
        List<Label> genreLabels = new ArrayList<>();
        for (String genre: genreList) {
            Label genreLabel = new Label(genre);
            genreLabel.setStyle(Style.FAV_GENRE_LABEL);
            genreLabel.setPadding(new Insets(5));
            genreLabels.add(genreLabel);
        }
        return genreLabels;
    }


    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (comingFromSearch) searchingView.backToSearchView();
            else searchingView.backToHomeView();
        }
    }

    private class SendMessageAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            searchingView.setNewMessageView(bandResultBean);
        }
    }
}
