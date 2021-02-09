/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 03/02/21, 21:06
 * Last edited: 03/02/21, 21:06
 */

package it.soundmate.view.search;

import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.logic.ApplicationController;
import it.soundmate.controller.logic.MessagesController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Application;
import it.soundmate.model.JoinRequest;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.SearchingView;
import it.soundmate.view.uicomponents.InstrumentGraphics;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BandSearchView extends Pane {

    private static final Logger logger = LoggerFactory.getLogger(BandSearchView.class);
    private final boolean comingFromSearch;
    private final BandResultBean bandResultBean;
    private final SearchingView searchingView;
    private final ApplicationController applicationController = new ApplicationController();


    //UI
    private final VBox contentVBox;
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());
    private final Button sendMessageBtn = UIUtils.createStyledButton("Send Message", new SendMessageAction());


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
        vBox.setSpacing(10);

        //Profile picture
        vBox.getChildren().add(backBtn);
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(600);
        rectangle.setHeight(150);
        if (bandResultBean.getProfileImg() != null) {
            rectangle.setFill(new ImagePattern(bandResultBean.getProfileImg()));
        }
        vBox.getChildren().add(rectangle);
        HBox nameHBox = new HBox();
        Label nameLabel = new Label(bandResultBean.getBandName());
        nameLabel.setStyle(Style.HEADER_TEXT);
        nameHBox.getChildren().add(nameLabel);
        UIUtils.addRegion(null, nameHBox);
        nameHBox.getChildren().add(sendMessageBtn);
        vBox.getChildren().add(nameHBox);

        Label genresLabel = new Label("Genres");
        genresLabel.setStyle(Style.HIGH_LABEL);
        HBox genresList = buildGenresHBox(bandResultBean);
        vBox.getChildren().addAll(genresLabel, genresList);
        Label applicationsLabel = new Label("Applications");
        applicationsLabel.setStyle(Style.HIGH_LABEL);
        HBox applicationsHBox = buildApplicationsHBox(bandResultBean);
        vBox.getChildren().addAll(applicationsLabel, applicationsHBox);
        return vBox;
    }

    private HBox buildApplicationsHBox(BandResultBean bandResultBean) {
        HBox applicationHBox = new HBox();
        applicationHBox.setSpacing(10);
        List<Application> applicationList = applicationController.getApplicationsForBand(bandResultBean.getId());
        for (int i = 0; i < applicationList.size(); i++) {
            VBox applicationVBox = new VBox();
            applicationVBox.setSpacing(5);
            applicationVBox.setAlignment(Pos.CENTER);
            List<String> instrumentsList = applicationList.get(i).getInstrumentsList();
            Label applicationLabel = new Label("Application #"+(i+1));
            applicationLabel.setStyle(Style.MID_LABEL);
            HBox instrumentsHBox = new HBox();
            instrumentsHBox.setSpacing(10);
            for (String instrument: instrumentsList) {
                VBox instrumentVBox = new VBox();
                instrumentVBox.setAlignment(Pos.CENTER);
                instrumentVBox.setSpacing(5);
                InstrumentGraphics instrumentGraphics = InstrumentGraphics.returnInstrument(instrument);
                Circle circle = new Circle();
                circle.setRadius(15);
                circle.setFill(new ImagePattern(instrumentGraphics.getSource()));
                Label instrumentLabel = new Label(instrumentGraphics.getName());
                instrumentLabel.setStyle(Style.MID_LABEL);
                instrumentVBox.getChildren().addAll(circle, instrumentLabel);
                instrumentsHBox.getChildren().add(instrumentVBox);
            }
            Button sendRequestBtn = UIUtils.createStyledButton("Send Request", new SendRequestAction(applicationList.get(i)));
            applicationVBox.getChildren().addAll(applicationLabel, instrumentsHBox, sendRequestBtn);
            applicationHBox.getChildren().addAll(applicationVBox);
        }
        return applicationHBox;
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

    private class SendRequestAction implements EventHandler<ActionEvent> {
        private final Application application;

        public SendRequestAction(Application application) {
            this.application = application;
        }

        @Override
        public void handle(ActionEvent event) {
            try {
                JoinRequest joinRequest = new JoinRequest();
                logger.info("Band ID: {}, Solo ID: {}", bandResultBean.getId(), bandResultBean.getSearcher().getId());
                joinRequest.setIdBand(bandResultBean.getId());
                joinRequest.setIdSolo(bandResultBean.getSearcher().getId());
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Join Request Dialog");
                dialog.setHeaderText("Join Request");
                dialog.setContentText("Add a message for the band:");
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(joinRequest::setMessage);
                MessagesController messagesController = new MessagesController();
                messagesController.applyForBand(application, joinRequest);
                Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                confirmation.setContentText("You request has been sent");
                confirmation.showAndWait();
            } catch (RepositoryException repositoryException) {
                logger.error("Repository Exception: {}", repositoryException.getMessage());
            }
        }
    }
}
