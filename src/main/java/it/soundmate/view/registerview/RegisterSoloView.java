/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 10/01/21, 23:22
 * Last edited: 10/01/21, 23:22
 */

package it.soundmate.view.registerview;

import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.controller.graphic.register.RegisterSoloGraphicController;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.User;
import it.soundmate.view.UIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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


public class RegisterSoloView extends BorderPane {

    private static final Logger logger = LoggerFactory.getLogger(RegisterSoloView.class);

    private final TextField emailTextField = new TextField();
    private final TextField passwordTextField = new TextField();
    private final TextField firstNameTextField = new TextField();
    private final TextField lastNameTextField = new TextField();
    private final TextField cityTextField = new TextField();


    public RegisterSoloView(){
        VBox fieldsVBox = buildFieldsVBox();
        Label registrationLabel = RegisterBandView.initializeLabel(fieldsVBox, "Solo Registration");
        this.setTop(registrationLabel);
        this.setCenter(fieldsVBox);
    }

    private VBox buildFieldsVBox() {
        VBox fieldsVBox = RegisterView.styleVBoxFields();

        VBox emailVBox = UIUtils.textFieldWithLabel("Email", this.emailTextField);
        VBox passwordVBox = UIUtils.textFieldWithLabel("Password", this.passwordTextField);
        VBox firstNameVBox = UIUtils.textFieldWithLabel("First Name", this.firstNameTextField);
        VBox lastNameVBox = UIUtils.textFieldWithLabel("Last Name", this.lastNameTextField);
        VBox cityVBox = UIUtils.textFieldWithLabel("City", this.cityTextField);

        HBox emailAndPassword = new HBox();
        emailAndPassword.setAlignment(Pos.CENTER);
        emailAndPassword.setSpacing(10);
        emailAndPassword.getChildren().addAll(emailVBox, passwordVBox);

        HBox names = new HBox();
        names.setAlignment(Pos.CENTER);
        names.setSpacing(10);
        names.getChildren().addAll(firstNameVBox, lastNameVBox, cityVBox);

        //Button
        Button registerBtn = UIUtils.createStyledButton("Register", new RegisterAction());
        registerBtn.setPrefWidth(250);
        fieldsVBox.getChildren().addAll(emailAndPassword, names, registerBtn);
        return fieldsVBox;
    }



    private class RegisterAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String email = emailTextField.getText();
            String password = passwordTextField.getText();
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String city = cityTextField.getText();
            try {
                RegisterSoloBean registerSoloBean = new RegisterSoloBean(email, password, firstName, lastName, city);
                RegisterSoloGraphicController registerSoloGraphicController = new RegisterSoloGraphicController();
                User user = registerSoloGraphicController.registerUser(registerSoloBean);
                if (user!=null) {
                    registerSoloGraphicController.navigateToMainView(user, (Stage) emailTextField.getScene().getWindow());
                }
            } catch (InputException inputException) {
                logger.error("Input Exception: {}", inputException.getMessage());
            }
        }
    }
}
