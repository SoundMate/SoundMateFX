/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 21/01/21, 16:42
 * Last edited: 21/01/21, 16:42
 */

package it.soundmate.view.profiles.renter;

import it.soundmate.bean.AddRoomBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.RoomRenterProfileGraphicController;
import it.soundmate.exceptions.InputException;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.RoomRenter;
import it.soundmate.utils.Cache;
import it.soundmate.utils.ImagePicker;
import it.soundmate.utils.ImgBase64Repo;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class AddRoomView extends VBox {

    private final ProfileView profileView;
    private final RoomRenter roomRenter;
    private final RoomRenterProfileGraphicController roomRenterProfileGraphicController = new RoomRenterProfileGraphicController();
    private static final Logger logger = LoggerFactory.getLogger(AddRoomView.class);

    //UI
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());
    private final TextField nameTextField = new TextField();
    private final TextField priceTextField = new TextField();
    private final TextArea descriptionTextArea = new TextArea();
    private final ImageView imageView = new ImageView();
    private File chosenImage = null;

    //Buttons
    private final Button uploadImageBtn = UIUtils.createStyledButton("Upload image", new UploadImageAction());
    private final Button removeImageBtn = UIUtils.createStyledButton("Remove image", new RemoveImageAction());
    private final Button addRoomBtn = UIUtils.createStyledButton("Add Room", new AddRoomAction());

    public AddRoomView(ProfileView profileView, RoomRenter roomRenter) {
        this.profileView = profileView;
        this.roomRenter = roomRenter;
        buildAddRoomVBox(roomRenter);
    }

    private void buildAddRoomVBox(RoomRenter roomRenter) {
        this.setPadding(new Insets(25));
        this.setSpacing(20);

        Label nameLabel = new Label(roomRenter.getName() + " Add New Room");
        nameLabel.setStyle(Style.HEADER_TEXT);
        this.getChildren().add(nameLabel);

        HBox fieldsHBox = buildFieldsHBox();
        this.getChildren().add(fieldsHBox);
        this.getChildren().add(addRoomBtn);
        UIUtils.addSizedRegion(this, 50, 50);
        this.getChildren().add(backBtn);
    }

    private HBox buildFieldsHBox() {
        HBox fieldsHBox = new HBox();
        fieldsHBox.setSpacing(20);
        fieldsHBox.setAlignment(Pos.CENTER);
        fieldsHBox.setPrefHeight(USE_COMPUTED_SIZE);
        fieldsHBox.setPrefWidth(USE_COMPUTED_SIZE);
        VBox fieldsVBox = buildFieldsVBox();
        VBox imageVBox = buildImageVBox();
        fieldsHBox.getChildren().addAll(fieldsVBox, imageVBox);
        return fieldsHBox;
    }

    private VBox buildImageVBox() {
        VBox imageVBox = new VBox();
        imageVBox.setAlignment(Pos.CENTER);
        imageVBox.setSpacing(10);
        imageVBox.setPrefHeight(USE_COMPUTED_SIZE);
        imageVBox.setPrefWidth(USE_COMPUTED_SIZE);

        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        imageVBox.getChildren().addAll(imageView, uploadImageBtn, removeImageBtn);
        return imageVBox;
    }

    private VBox buildFieldsVBox() {
        VBox fieldsVBox = new VBox();
        HBox nameAndPriceHBox = new HBox();
        nameAndPriceHBox.setPrefHeight(USE_COMPUTED_SIZE);
        nameAndPriceHBox.setPrefWidth(USE_COMPUTED_SIZE);
        nameAndPriceHBox.setSpacing(10);

        VBox nameVBox = UIUtils.textFieldWithLabel("Name", nameTextField);
        VBox priceVBox = UIUtils.textFieldWithLabel("Price $/hr", priceTextField);
        priceTextField.setPrefWidth(40);
        nameAndPriceHBox.getChildren().addAll(nameVBox, priceVBox);
        fieldsVBox.setPrefWidth(USE_COMPUTED_SIZE);
        fieldsVBox.setPrefHeight(USE_COMPUTED_SIZE);
        fieldsVBox.setSpacing(10);

        Label descLabel = new Label("Description");
        descLabel.setStyle(Style.TEXT_FIELD_LABEL);
        descriptionTextArea.setStyle(Style.TEXT_FIELD);
        descriptionTextArea.setPrefWidth(USE_COMPUTED_SIZE);
        fieldsVBox.getChildren().addAll(nameAndPriceHBox, descLabel, descriptionTextArea);
        return fieldsVBox;
    }


    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            profileView.setProfilePage(new ManageRoomsView(profileView, roomRenter));
        }
    }

    private class UploadImageAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            ImagePicker imagePicker = new ImagePicker();
            chosenImage = imagePicker.chooseImage(imageView); //If file != null set imageview (Lo fa image picker)
        }
    }

    private class RemoveImageAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            chosenImage = null;
            imageView.setImage(null);
        }
    }

    private class AddRoomAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                String name = nameTextField.getText();
                String price = priceTextField.getText();
                String description = descriptionTextArea.getText();
                AddRoomBean addRoomBean;
                if (chosenImage == null) addRoomBean = new AddRoomBean(name, price, description, null);
                else addRoomBean = new AddRoomBean(name, price, description, ImgBase64Repo.encode(chosenImage.toPath()));
                int roomCode = roomRenterProfileGraphicController.addRoom(addRoomBean, roomRenter);
                if (chosenImage != null) {
                    Cache.getInstance().saveRoomPhotoToCache(roomRenter.getId(), roomCode, chosenImage);
                    logger.info("Saved image to cache with RENTER ID: {}, ROOM CODE: {}", roomRenter.getId(), roomCode);
                }
            } catch (IOException ioException) {
                logger.error("IOException catched", ioException);
            } catch (InputException inputException) {
                logger.error("Input Exception: {}", inputException.getMessage());
            } catch (UpdateException updateException) {
                logger.error("Update Exception: {}", updateException.getMessage());
            }
        }
    }
}
