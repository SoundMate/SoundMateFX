/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 10/01/21, 23:22
 * Last edited: 10/01/21, 23:22
 */

package it.soundmate.view.registerview;

import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.register.RegisterBandGraphicController;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.User;
import it.soundmate.view.UIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterBandView extends BorderPane {

    private static final Logger logger = LoggerFactory.getLogger(RegisterBandView.class);

    private final TextField emailTextField = new TextField();
    private final TextField passwordTextField = new TextField();
    private final TextField bandNameTextField = new TextField();
    private final TextField cityTextField = new TextField();

    public RegisterBandView(){
        VBox fieldsVBox = buildFieldsVBox();
        Label registrationLabel = initializeLabel(fieldsVBox, "Band Registration");
        this.setTop(registrationLabel);
        this.setCenter(fieldsVBox);
    }

    public static Label initializeLabel(VBox fieldsVBox, String registrationString) {
        UIUtils.setBackgroundPane("#232323", fieldsVBox);

        //Label
        Label registrationLabel = new Label(registrationString);
        registrationLabel.setStyle(Style.HIGH_LABEL);
        registrationLabel.setPadding(new Insets(25));
        return registrationLabel;
    }

    private VBox buildFieldsVBox() {
        VBox fieldsVBox = RegisterView.styleVBoxFields();

        VBox emailVBox = UIUtils.textFieldWithLabel("Email", this.emailTextField);
        VBox passwordVBox = UIUtils.textFieldWithLabel("Password", this.passwordTextField);
        VBox cityVBox = UIUtils.textFieldWithLabel("City", this.cityTextField);

        this.bandNameTextField.setStyle(Style.TEXT_FIELD_REGISTER);
        this.bandNameTextField.setPromptText("Band Name...");
        this.bandNameTextField.setPrefWidth(250);
        this.bandNameTextField.setAlignment(Pos.CENTER);

        HBox fieldsHBox = new HBox();
        fieldsHBox.setAlignment(Pos.CENTER);
        fieldsHBox.setPrefWidth(USE_COMPUTED_SIZE);
        fieldsHBox.setPrefHeight(USE_COMPUTED_SIZE);
        fieldsHBox.setSpacing(20);
        fieldsHBox.getChildren().addAll(emailVBox, passwordVBox, cityVBox);

        Button registerBtn = UIUtils.createStyledButton("Register", new RegisterAction());
        registerBtn.setPrefWidth(200);
        fieldsVBox.getChildren().addAll(this.bandNameTextField, fieldsHBox, registerBtn);
        return fieldsVBox;
    }

    private class RegisterAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                RegisterBandBean registerBandBean = new RegisterBandBean(emailTextField.getText(), passwordTextField.getText(),
                        bandNameTextField.getText(), cityTextField.getText());
                RegisterBandGraphicController registerBandGraphicController = new RegisterBandGraphicController();
                User user = registerBandGraphicController.registerUser(registerBandBean);
                registerBandGraphicController.navigateToMainView(user, (Stage) bandNameTextField.getScene().getWindow());
            } catch (InputException inputException) {
                Alert confirmedDialog = new Alert(Alert.AlertType.INFORMATION);
                confirmedDialog.setTitle("Registration failed");
                confirmedDialog.setHeaderText(null);
                confirmedDialog.setContentText(inputException.getMessage());
                confirmedDialog.showAndWait();
                logger.error("Input Exception: {}", inputException.getMessage());
            }
        }
    }
}
