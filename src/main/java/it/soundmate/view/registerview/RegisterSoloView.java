/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 10/01/21, 23:22
 * Last edited: 10/01/21, 23:22
 */

package it.soundmate.view.registerview;

import it.soundmate.constants.Style;
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


public class RegisterSoloView extends BorderPane {

    private final TextField emailTextField = new TextField();
    private final TextField passwordTextField = new TextField();
    private final TextField firstNameTextField = new TextField();
    private final TextField lastNameTextField = new TextField();

    public RegisterSoloView(){
        VBox fieldsVBox = buildFieldsVBox();
        UIUtils.setBackgroundPane("#232323", fieldsVBox);

        //Label
        Label registrationLabel = new Label("Solo Registration");
        registrationLabel.setStyle(Style.HIGH_LABEL);
        registrationLabel.setPadding(new Insets(25));

        this.setTop(registrationLabel);
        this.setCenter(fieldsVBox);
    }

    private VBox buildFieldsVBox() {
        VBox fieldsVBox = RegisterView.styleVBoxFields();

        VBox emailVBox = UIUtils.textFieldWithLabel("Email", this.emailTextField);
        VBox passwordVBox = UIUtils.textFieldWithLabel("Password", this.passwordTextField);
        VBox firstNameVBox = UIUtils.textFieldWithLabel("First Name", this.firstNameTextField);
        VBox lastNameVBox = UIUtils.textFieldWithLabel("Last Name", this.lastNameTextField);

        HBox emailAndPassword = new HBox();
        emailAndPassword.setAlignment(Pos.CENTER);
        emailAndPassword.setSpacing(10);
        emailAndPassword.getChildren().addAll(emailVBox, passwordVBox);

        HBox names = new HBox();
        names.setAlignment(Pos.CENTER);
        names.setSpacing(10);
        names.getChildren().addAll(firstNameVBox, lastNameVBox);

        //Button
        Button registerBtn = UIUtils.createStyledButton("Register", new RegisterAction());
        registerBtn.setPrefWidth(250);
        fieldsVBox.getChildren().addAll(emailAndPassword, names, registerBtn);
        return fieldsVBox;
    }



    private class RegisterAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            //Controller qua
        }
    }
}
