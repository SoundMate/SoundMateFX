/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 17/01/21, 23:54
 * Last edited: 17/01/21, 23:54
 */

package it.soundmate.view.profiles.renter;

import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.RoomRenterProfileGraphicController;
import it.soundmate.exceptions.InputException;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.Booking;
import it.soundmate.model.Room;
import it.soundmate.model.RoomRenter;
import it.soundmate.utils.Cache;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class RenterProfileView extends VBox {

    private final RoomRenterProfileGraphicController roomRenterProfileGraphicController = new RoomRenterProfileGraphicController();
    private final ProfileView profileView;
    private final RoomRenter roomRenter;
    private static final Logger logger = LoggerFactory.getLogger(RenterProfileView.class);

    //UI
    private final Rectangle coverImg = new Rectangle();
    private StackPane stackPane;
    private final Button addCoverImgBtn = UIUtils.createStyledButton("Add cover image", new AddImageAction());
    private final Button manageRoomsBtn = UIUtils.createStyledButton("Manage Rooms", new ManageRoomsAction());


    public RenterProfileView(ProfileView profileView, RoomRenter roomRenter) {
        this.profileView = profileView;
        this.roomRenter = roomRenter;
        buildRenterProfileView(this.roomRenter);
    }

    private void buildRenterProfileView(RoomRenter roomRenter) {
        this.stackPane = this.profileView.buildStackPane(roomRenter, addCoverImgBtn, coverImg);
        HBox userInfoVBox = buildUserInfoVBox(roomRenter);
        HBox photosHBox = this.profileView.buildMediaHBox(roomRenter, new ManageMediaAction());
        HBox roomsHBox = buildRoomsHBox(roomRenter);
        this.getChildren().addAll(stackPane, userInfoVBox, photosHBox, roomsHBox);
    }

    private HBox buildRoomsHBox(RoomRenter roomRenter) {
        HBox[] roomsHBoxes = profileView.buildProfileSection("Rooms", "Add rooms to let users book them", manageRoomsBtn);

        HBox mainHBox = roomsHBoxes[0];
        HBox roomsHBox = roomsHBoxes[1];

        if (roomRenter.getRooms() != null && !roomRenter.getRooms().isEmpty()) {
            roomsHBox.getChildren().remove(0);
            for (Room room : roomRenter.getRooms()) {
                RoomUI roomUI = new RoomUI(room, roomRenter.getId());
                roomsHBox.getChildren().add(roomUI);
            }
        }
        return mainHBox;
    }

    private HBox buildUserInfoVBox(RoomRenter roomRenter) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefWidth(USE_COMPUTED_SIZE);
        hBox.setPrefHeight(USE_COMPUTED_SIZE);
        hBox.setPadding(new Insets(25));

        //Name and address VBox
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setSpacing(20);

        //Name
        Label nameLabel = new Label(roomRenter.getName());
        nameLabel.setStyle(Style.HEADER_TEXT);

        //City & Address
        Label cityAndAddressLabel = new Label(roomRenter.getCity() + ", "+ roomRenter.getAddress());
        cityAndAddressLabel.setStyle(Style.HIGH_LABEL);

        //Buttons
        VBox buttonsVBox = new VBox();
        buttonsVBox.setPrefWidth(USE_COMPUTED_SIZE);
        buttonsVBox.setPrefHeight(USE_COMPUTED_SIZE);
        buttonsVBox.setSpacing(20);
        buttonsVBox.setAlignment(Pos.CENTER);
        Button bookingsBtn = UIUtils.createStyledButton("BOOKINGS", new BookingsAction());
        Button editProfileBtn = UIUtils.createStyledButton("Edit profile info", new EditProfileAction());
        buttonsVBox.getChildren().addAll(bookingsBtn, editProfileBtn);

        vBox.getChildren().add(nameLabel);
        vBox.getChildren().add(cityAndAddressLabel);

        hBox.getChildren().add(vBox);
        UIUtils.addRegion(null, hBox);
        hBox.getChildren().add(buttonsVBox);
        return hBox;
    }

    private class AddImageAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Add image btn clicked");
            try {
                roomRenterProfileGraphicController.updateProfilePic(coverImg, roomRenter);
                coverImg.setFill(new ImagePattern(new Image(Cache.getInstance().getProfilePicFromCache(roomRenter.getId()))));
            } catch (UpdateException updateException) {
                logger.error("Update Exception: {}", updateException.getMessage());
                updateUIErrorImage();
            } catch (InputException inputException) {
                logger.error("Input Exception: {}", inputException.getMessage());
                updateUIErrorImage();
            }
        }

        private void updateUIErrorImage() {
            stackPane.getChildren().remove(addCoverImgBtn);
            VBox errorVBox = new VBox();
            errorVBox.setAlignment(Pos.CENTER);
            Label errorLabel = new Label("Error uploading image, try again");
            errorLabel.setStyle(Style.ERROR_TEXT);
            errorVBox.getChildren().addAll(errorLabel, addCoverImgBtn);
            stackPane.getChildren().add(errorVBox);
        }
    }

    private class EditProfileAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Edit profile click");
            roomRenterProfileGraphicController.navigateToEditView(profileView, roomRenter);
        }
    }

    private static class ManageMediaAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Manage media click");
        }
    }

    private class ManageRoomsAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Manage Rooms click");
            profileView.setProfilePage(new ManageRoomsView(profileView, roomRenter));
        }
    }

    private class BookingsAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Bookings click");
            List<Booking> bookings = roomRenterProfileGraphicController.getBookings(roomRenter);
            profileView.setProfilePage(new BookingsView(profileView, roomRenter, bookings));
        }
    }
}
