/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 28/01/21, 18:15
 * Last edited: 28/01/21, 18:15
 */

package it.soundmate.view.search;

import it.soundmate.bean.searchbeans.RoomRenterResultBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.RoomRenterProfileGraphicController;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Booking;
import it.soundmate.model.Room;
import it.soundmate.utils.Cache;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.SearchingView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;


public class RoomSearchView extends VBox {

    private final RoomRenterProfileGraphicController roomRenterProfileGraphicController = new RoomRenterProfileGraphicController();
    private final SearchingView searchingView;
    private final RoomRenterResultBean roomRenterResultBean;
    private final boolean comingFromSearch;
    private final Room room;

    //UI
    private final DatePicker datePicker = new DatePicker();
    private final TextField startTimeTextField = new TextField();
    private final TextField endTimeTextField = new TextField();
    private final Label errorLabel = new Label();

    //Buttons
    private final Button bookBtn = UIUtils.createStyledButton("Book room", new BookRoomAction());
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());
    private final Button dateBtn = UIUtils.createStyledButton("Select Date", new SelectDateAction());
    private static final Logger logger = LoggerFactory.getLogger(RoomSearchView.class);

    public RoomSearchView(SearchingView searchingView, RoomRenterResultBean roomRenterResultBean, Room room, boolean comingFromSearch) {
        this.searchingView = searchingView;
        this.roomRenterResultBean = roomRenterResultBean;
        this.room = room;
        this.comingFromSearch = comingFromSearch;
        this.buildVBox();
    }

    private void buildVBox() {
        this.setPadding(new Insets(25));

        //Room image
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(500);
        rectangle.setHeight(170);
        if (room.getEncodedImg() != null) {
            rectangle.setFill(new ImagePattern(new Image(Cache.getInstance().getRoomPicFromCache(roomRenterResultBean.getId(), room.getCode()))));
        }
        this.getChildren().add(rectangle);

        //Name and desc
        Label name = new Label(room.getName());
        name.setStyle(Style.HEADER_TEXT);
        Label desc = new Label(room.getDescription());
        desc.setStyle(Style.MID_LABEL);
        Label price = new Label("Price $/hr: "+room.getPrice().toString());
        price.setStyle(Style.LOW_LABEL);
        this.getChildren().addAll(name, desc, price);

        //Booking form
        VBox bookingForm = buildBookingForm();

        this.getChildren().addAll(bookingForm, bookBtn);
        UIUtils.addSizedRegion(this, 100, 100);
        errorLabel.setStyle(Style.ERROR_TEXT);
        errorLabel.setVisible(false);
        this.getChildren().add(errorLabel);
        this.getChildren().add(backBtn);

    }

    private VBox buildBookingForm() {
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(50));

        //Date
        HBox dateHBox = new HBox();
        dateHBox.setSpacing(10);
        datePicker.setPromptText("Choose a date");
        dateHBox.getChildren().addAll(datePicker, dateBtn);

        //Time
        HBox timeHBox = new HBox();
        timeHBox.setSpacing(10);
        Label startTimeLabel = new Label("Start Time");
        startTimeLabel.setStyle(Style.MID_LABEL);
        Label endTimeLabel = new Label("End Time");
        endTimeLabel.setStyle(Style.MID_LABEL);

        startTimeTextField.setStyle(Style.TEXT_FIELD);
        startTimeTextField.setPrefWidth(50);
        endTimeTextField.setStyle(Style.TEXT_FIELD);
        endTimeTextField.setPrefWidth(50);
        timeHBox.getChildren().addAll(startTimeLabel, startTimeTextField, endTimeLabel, endTimeTextField);

        vBox.getChildren().addAll(dateHBox, timeHBox);
        return vBox;
    }

    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            searchingView.setDetailViewRenter(new RenterSearchView(roomRenterResultBean, comingFromSearch, searchingView));
        }
    }

    private class BookRoomAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                roomRenterProfileGraphicController.checkRoomAvailability(datePicker.getValue(), startTimeTextField.getText(), endTimeTextField.getText(), room);
                Booking booking = new Booking(room, roomRenterResultBean.getSearcher().getId(), datePicker.getValue(), LocalTime.parse(startTimeTextField.getText()), LocalTime.parse(endTimeTextField.getText()));
                booking.setIdRenter(roomRenterResultBean.getId());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Are you sure?");
                alert.setContentText("Booking "+room.getName()+" "+datePicker.getValue()+" from "+startTimeTextField.getText()+" to "+endTimeTextField.getText());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    roomRenterProfileGraphicController.bookRoom(booking);
                }
                Alert confirmedDialog = new Alert(Alert.AlertType.INFORMATION);
                confirmedDialog.setTitle("Booking confirmation");
                confirmedDialog.setHeaderText(null);
                confirmedDialog.setContentText("Booking confirmed!\nCheck the messages section to cancel your booking.");

                confirmedDialog.showAndWait();
            } catch (InputException inputException) {
                logger.error("Input Exception: {}", inputException.getMessage());
                errorLabel.setText(inputException.getMessage());
                errorLabel.setVisible(true);
            } catch (DateTimeParseException dateTimeParseException) {
                logger.error("DateTimeParseException: {}", dateTimeParseException.getMessage());
                errorLabel.setText(dateTimeParseException.getMessage());
                errorLabel.setVisible(true);
            }
        }
    }

    private class SelectDateAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Selected date time: {}", datePicker.getValue());
        }
    }

}
