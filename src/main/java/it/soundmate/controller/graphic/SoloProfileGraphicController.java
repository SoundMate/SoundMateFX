/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 14/01/21, 20:05
 * Last edited: 14/01/21, 20:05
 */

package it.soundmate.controller.graphic;

import it.soundmate.controller.logic.SoloProfileController;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Genres;
import it.soundmate.model.Solo;
import javafx.scene.control.ChoiceDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SoloProfileGraphicController {

    private final SoloProfileController soloProfileController = new SoloProfileController();
    private static final Logger logger = LoggerFactory.getLogger(SoloProfileGraphicController.class);

    public String addGenreDialog(Solo solo){
        String selectedGenre = favGenreDialog();
        if (selectedGenre == null) {
            throw new InputException("Genre was not selected");
        } else {
            return soloProfileController.addGenre(selectedGenre, solo);
        }
    }

    private String favGenreDialog() {
        List<String> choices = new ArrayList<>();
        for (Genres genres : Genres.values()) {
            choices.add(genres.name());
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>("GENRE", choices);
        dialog.setTitle("Add favourite genre");
        dialog.setHeaderText("Add favourite genre");
        dialog.setContentText("Choose genre:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            logger.info("Your choice: {}", result.get());
            return result.get();
        }
        return null;
    }

}
