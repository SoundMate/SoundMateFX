/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 10/01/21, 23:22
 * Last edited: 10/01/21, 23:22
 */

package it.soundmate.view.registerview;

import it.soundmate.bean.registerbeans.RegisterRenterBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.register.RegisterRenterGraphicController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.User;
import it.soundmate.view.UIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterRoomRenterView extends BorderPane {

    private final TextField emailTextField = new TextField();
    private final TextField passwordTextField = new TextField();
    private final TextField roomRenterNameTextField = new TextField();
    private final TextField firstNameTextField = new TextField();
    private final TextField lastNameTextField = new TextField();
    private final TextField addressTextField = new TextField();
    private final TextField cityTextField = new TextField();

    private static final Logger logger = LoggerFactory.getLogger(RegisterRoomRenterView.class);

    public RegisterRoomRenterView(){
        VBox fieldsVBox = buildFieldsHBox();
        UIUtils.setBackgroundPane("#232323", fieldsVBox);

        //Label
        Label registrationLabel = new Label("Room Renter Registration");
        registrationLabel.setStyle(Style.HIGH_LABEL);
        registrationLabel.setPadding(new Insets(25));

        this.setTop(registrationLabel);
        this.setCenter(fieldsVBox);
    }

    private VBox buildFieldsHBox() {
        VBox allVBox = new VBox();
        allVBox.setAlignment(Pos.CENTER);
        allVBox.setPrefWidth(USE_COMPUTED_SIZE);
        allVBox.setPrefHeight(USE_COMPUTED_SIZE);
        allVBox.setSpacing(30);

        HBox fieldsHBox = new HBox();
        fieldsHBox.setSpacing(20);
        fieldsHBox.setAlignment(Pos.CENTER);

        VBox emailVBox = UIUtils.textFieldWithLabel("Email", this.emailTextField);
        VBox passwordVBox = UIUtils.textFieldWithLabel("Password", this.passwordTextField);
        VBox firstNameVBox = UIUtils.textFieldWithLabel("First Name", this.firstNameTextField);
        VBox lastNameVBox = UIUtils.textFieldWithLabel("Last Name", this.lastNameTextField);

        HBox emailAndPasswordHBox = RegisterView.createEmailAndPasswordHBox(emailVBox, passwordVBox);
        HBox firstAndLastNameHBox = RegisterView.createEmailAndPasswordHBox(firstNameVBox, lastNameVBox);

        //Email, password, first name, last name
        VBox userFields = new VBox();
        userFields.setAlignment(Pos.CENTER);
        userFields.setPrefWidth(USE_COMPUTED_SIZE);
        userFields.setPrefHeight(USE_COMPUTED_SIZE);
        userFields.setSpacing(10);

        //Room renter name, city, address
        VBox addressAndNameVBox = new VBox();
        addressAndNameVBox.setAlignment(Pos.CENTER);
        addressAndNameVBox.setPrefWidth(USE_COMPUTED_SIZE);
        addressAndNameVBox.setPrefHeight(USE_COMPUTED_SIZE);
        addressAndNameVBox.setSpacing(10);

        this.roomRenterNameTextField.setStyle(Style.TEXT_FIELD_REGISTER);
        this.roomRenterNameTextField.setPromptText("Room Renter Name...");
        this.roomRenterNameTextField.setPrefWidth(250);
        this.roomRenterNameTextField.setAlignment(Pos.CENTER);

        VBox cityVBox = UIUtils.textFieldWithLabel("City", this.cityTextField);
        VBox addressVBox = UIUtils.textFieldWithLabel("Address", this.addressTextField);
        HBox cityAndAddressHBox = RegisterView.createEmailAndPasswordHBox(cityVBox, addressVBox);

        addressAndNameVBox.getChildren().addAll(this.roomRenterNameTextField, cityAndAddressHBox);

        //Buttons
        Button registerBtn = UIUtils.createStyledButton("Register", new RegisterAction());
        registerBtn.setPrefWidth(300);

        userFields.getChildren().addAll(emailAndPasswordHBox, firstAndLastNameHBox);
        fieldsHBox.getChildren().addAll(userFields, addressAndNameVBox);
        allVBox.getChildren().addAll(fieldsHBox , registerBtn);
        return allVBox;
    }


    private class RegisterAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            RegisterRenterGraphicController registerRenterGraphicController = new RegisterRenterGraphicController();
            RegisterRenterBean registerRenterBean = new RegisterRenterBean(emailTextField.getText(), passwordTextField.getText(), firstNameTextField.getText(),
                    lastNameTextField.getText(), addressTextField.getText(), roomRenterNameTextField.getText(), cityTextField.getText());
            try {
                User user = registerRenterGraphicController.registerUser(registerRenterBean);
                registerRenterGraphicController.navigateToMainView(user, (Stage) emailTextField.getScene().getWindow());
            } catch (RepositoryException repositoryException) {
                logger.error("Repository Exception: {}", repositoryException.getMessage());
            } catch (InputException inputException) {
                logger.error("Input Exception: {}", inputException.getMessage());
            }
        }
    }
}
