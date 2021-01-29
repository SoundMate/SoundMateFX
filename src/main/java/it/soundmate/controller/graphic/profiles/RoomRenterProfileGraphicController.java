/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 18/01/21, 18:07
 * Last edited: 18/01/21, 18:07
 */

package it.soundmate.controller.graphic.profiles;

import it.soundmate.bean.AddRoomBean;
import it.soundmate.bean.MapBean;
import it.soundmate.controller.logic.profiles.RoomRenterProfileController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.Booking;
import it.soundmate.model.Room;
import it.soundmate.model.RoomRenter;
import it.soundmate.view.main.ProfileView;
import it.soundmate.view.profiles.renter.EditRenterView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;


public class RoomRenterProfileGraphicController extends EditGraphicController {

    private static final Logger logger = LoggerFactory.getLogger(RoomRenterProfileGraphicController.class);
    private final RoomRenterProfileController roomRenterProfileController = new RoomRenterProfileController();

    public void navigateToEditView(ProfileView profileView, RoomRenter roomRenter) {
        profileView.setProfilePage(new EditRenterView(roomRenter, profileView));
    }

    public int addRoom(AddRoomBean addRoomBean, RoomRenter roomRenter) {
        try {
            return roomRenterProfileController.addRoom(addRoomBean, roomRenter);
        } catch (UpdateException updateException) {
            throw new UpdateException(updateException.getMessage());
        }
    }

    public MapBean displayMap(String city, String address) {
        try {
            double[] coordinates = roomRenterProfileController.getRenterMap(city,address);
            logger.info("Result coordinates: {}, {}", coordinates[0], coordinates[1]);
            return new MapBean(coordinates[0], coordinates[1]);
        } catch (InputException inputException) {
            throw new InputException(inputException.getMessage());
        }
    }

    public void checkRoomAvailability(LocalDate date, String startTime, String endTime, Room room) {
        if (date == null || startTime.equals("") || endTime.equals("")) {
            throw new InputException("Please select time and date");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new InputException("Date is in the past");
        }
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        if (start.isAfter(LocalTime.of(20, 0)) || start.isBefore(LocalTime.of(8, 0))) {
            throw new InputException("Room Renter is closed at those hours");
        }
        if (end.isAfter(LocalTime.of(20, 0)) || end.isBefore(LocalTime.of(8, 0))) {
            throw new InputException("Room Renter is closed at those hours");
        }
        if (start.until(end, MINUTES) < 60) {
            throw new InputException("You have to book at least for an hour");
        }
        try {
            roomRenterProfileController.checkAvailability(date, start, end, room);
        } catch (InputException inputException) {
            throw new InputException(inputException.getMessage());
        }
    }

    public void bookRoom(Booking booking) {
        try {
            roomRenterProfileController.bookRoom(booking);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }
}
