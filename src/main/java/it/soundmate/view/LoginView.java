/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 08/01/21, 12:08
 * Last edited: 08/01/21, 12:03
 */

package it.soundmate.view;

import it.soundmate.bean.LoginBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.LoginGraphicController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.database.dbexceptions.UserNotFoundException;
import it.soundmate.model.User;
import it.soundmate.view.main.MainView;
import it.soundmate.view.registerview.ChooseRegisterView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Login View
 *
 * Classe View di interazione con l'utente in ambiente desktop.
 * L'esecuzione del caso d'uso "Login" viene delegata al controller "LoginController",
 * Il passaggio di dati tra la LoginView e il LoginController viene fatto attraverso
 * la classe LoginBean.
 * */

public class LoginView extends BorderPane {

    private final Logger logger = LoggerFactory.getLogger(LoginView.class);

    //UI Elements
    private TextField emailTextField;
    private PasswordField passwordField;
    private final Label errorLabel = new Label("Login Error");

    public LoginView() {
        //General
        this.addBackground();
        VBox left = new VBox();
        VBox right = new VBox();
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);

        buildLeftPane(left);
        buildRightPane(right);
        buildBottomPane(bottom);

        //Setup BorderPane
        this.setLeft(left);
        this.setRight(right);
        this.setBottom(bottom);
    }


    private void buildBottomPane(HBox bottom) {
        bottom.setPadding(new Insets(0,0,50,0));
        bottom.getChildren().add(errorLabel);
    }

    private void buildRightPane(VBox right) {
        right.setPadding(new Insets(50));
        UIUtils.addRegion(right, null);
        this.addTextFieldWithLabel(right, "Email");
        this.addTextFieldWithLabel(right, "Password");
        UIUtils.addSizedRegion(right, 30, (int) USE_COMPUTED_SIZE);
        UIUtils.addStyledButton("Login", new LoginAction(), right);
        this.errorLabel.setStyle(Style.ERROR_TEXT);
        this.errorLabel.setVisible(false);
        this.errorLabel.setPadding(new Insets(30,0,0,0));
        UIUtils.addRegion(right, null);
    }

    private void buildLeftPane(VBox left) {
        left.setPadding(new Insets(50));
        ImageView imageView = this.logoImage();
        left.getChildren().add(imageView);
        UIUtils.addRegion(left, null);
        this.addHeaderText(left);
        UIUtils.addStyledButton("Join Now", new JoinAction(), left);
        UIUtils.addRegion(left, null);
    }


    private void addTextFieldWithLabel(VBox vBox, String label) {
        Label labelField = new Label(label);
        labelField.setStyle(Style.TEXT_FIELD_LABEL);
        labelField.setPadding(new Insets(10,0,5,0));
        TextField textField;
        if (label.equals("Email")) {
            this.emailTextField = new TextField();
            textField = this.emailTextField;
        } else {
            this.passwordField = new PasswordField();
            textField = this.passwordField;
        }
        textField.setPrefWidth(260);
        textField.setPrefHeight(USE_COMPUTED_SIZE);
        textField.setPadding(new Insets(5));
        textField.setStyle(Style.TEXT_FIELD);
        vBox.getChildren().addAll(labelField, textField);
    }


    private void addHeaderText(VBox vBox) {
        Label header = new Label("Join our community\n" + "Music begins with you.");
        header.setStyle(Style.HEADER_TEXT);
        header.setPadding(new Insets(10,0,10,0));
        vBox.getChildren().add(header);
    }

    private void addBackground() {
        Image background = new Image("soundmate/images/bg.png");
        UIUtils.setBackgroundImagePane(background, this);
    }

    private ImageView logoImage() {
        Image image =  new Image("soundmate/images/logo-h.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private class LoginAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Login Action Button");
            LoginBean loginBean = new LoginBean(emailTextField.getText(), passwordField.getText());
            LoginGraphicController loginGraphicController = new LoginGraphicController();
            try {
                User loggedUser = loginGraphicController.login(loginBean);
                //View Update (qua o nel controller?)
                if (loggedUser != null) {
                    logger.info("Fields Okay");
                    logger.info("Logged in: {} {}", loggedUser.getEmail(), loggedUser.getPassword());
                    Parent mainScreen = new MainView(loggedUser).getBorderPane();
                    Stage stage = (Stage) emailTextField.getScene().getWindow();
                    Scene scene = new Scene(mainScreen, 800, 600);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    logger.info("One or more fields are empty");
                    errorLabel.setVisible(true);
                }
            } catch (RepositoryException | UserNotFoundException repositoryException) {
                errorLabel.setText(repositoryException.getMessage());
                errorLabel.setVisible(true);
            }

        }
    }

    private class JoinAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Join Action Button");
            Stage stage = (Stage) emailTextField.getScene().getWindow();
            Parent registerView = new ChooseRegisterView().getBorderPane();
            Scene scene = new Scene(registerView, 800, 600);
            stage.setScene(scene);
            stage.show();
        }
    }
}
