package it.soundmate.view.main;

import it.soundmate.constants.Style;
import it.soundmate.model.User;
import it.soundmate.view.UIUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HomeView extends Pane {

    private BorderPane homeBorderPane;

    public HomeView(User user) {
        this.homeBorderPane = buildHomeBorderPane(user);
    }

    private BorderPane buildHomeBorderPane(User user) {
        BorderPane borderPane = new BorderPane();
        Node top = buildTopNode(user);
        Node center = buildCenterNode(user);
        borderPane.setTop(top);
        borderPane.setCenter(center);
        UIUtils.setBackgroundPane("#232323", borderPane);
        return borderPane;
    }

    private VBox buildTopNode(User user) {
        VBox topVBox = new VBox();
        topVBox.setAlignment(Pos.CENTER_LEFT);
        topVBox.setPadding(new Insets(25));

        Label homeLabel = new Label("Welcome, "+user.getFirstName());
        homeLabel.setStyle(Style.HEADER_TEXT);
        topVBox.getChildren().add(homeLabel);
        return topVBox;
    }

    private Node buildCenterNode(User user) {
        VBox mainVBox = new VBox();
        mainVBox.setAlignment(Pos.TOP_LEFT);
        mainVBox.setPadding(new Insets(25));
        mainVBox.setSpacing(10);

        Label musiciansLabel = new Label("Musicians you may know");
        musiciansLabel.setStyle(Style.MID_LABEL);
        musiciansLabel.setPadding(new Insets(10,0,10,0));
        HBox musiciansHBox = buildMusiciansHBox(user);
        mainVBox.getChildren().addAll(musiciansLabel, musiciansHBox);

        Label bandsLabel = new Label("Bands you may like");
        bandsLabel.setStyle(Style.MID_LABEL);
        bandsLabel.setPadding(new Insets(10,0,10,0));
        HBox bandsHBox = buildBandsHBox(user);
        mainVBox.getChildren().addAll(bandsLabel, bandsHBox);


        Label roomsLabel = new Label("Rooms near you");
        roomsLabel.setStyle(Style.MID_LABEL);
        roomsLabel.setPadding(new Insets(10,0,10,0));
        HBox roomsHBox = buildRoomsHBox(user);
        mainVBox.getChildren().addAll(roomsLabel, roomsHBox);


        return mainVBox;
    }

    private HBox buildRoomsHBox(User user) {
        HBox roomsHBox = new HBox();
        roomsHBox.setAlignment(Pos.CENTER_LEFT);

        Label noRoomsLabel = new Label("Access position to show nearest Rooms");
        noRoomsLabel.setStyle(Style.LOW_LABEL);
        roomsHBox.getChildren().add(noRoomsLabel);
        return roomsHBox;
    }

    private HBox buildBandsHBox(User user) {
        HBox bandsHBox = new HBox();
        bandsHBox.setAlignment(Pos.CENTER_LEFT);

        Label noBandsLabel = new Label("Not enough data for the user");
        noBandsLabel.setStyle(Style.LOW_LABEL);
        bandsHBox.getChildren().add(noBandsLabel);
        return bandsHBox;
    }

    private HBox buildMusiciansHBox(User user) {
        HBox musiciansHBox = new HBox();
        musiciansHBox.setAlignment(Pos.CENTER_LEFT);

        Label musiciansLabel = new Label("Access position to show Musicians around you");
        musiciansLabel.setStyle(Style.LOW_LABEL);
        musiciansHBox.getChildren().add(musiciansLabel);
        return musiciansHBox;
    }

    public BorderPane getHomeBorderPane() {
        return homeBorderPane;
    }
}
