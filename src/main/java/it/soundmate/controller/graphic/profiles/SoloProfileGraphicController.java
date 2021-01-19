/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 18/01/21, 18:06
 * Last edited: 14/01/21, 23:03
 */

package it.soundmate.controller.graphic.profiles;

import it.soundmate.controller.logic.SoloProfileController;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Genre;
import it.soundmate.model.Solo;
import javafx.scene.control.ChoiceDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SoloProfileGraphicController extends EditGraphicController {

    private final SoloProfileController soloProfileController = new SoloProfileController();
    private static final Logger logger = LoggerFactory.getLogger(SoloProfileGraphicController.class);

    public Genre addGenreDialog(Solo solo){
        Genre selectedGenre = favGenreDialog();
        if (selectedGenre == null) {
            throw new InputException("Genre was not selected");
        } else {
            return soloProfileController.addGenre(selectedGenre, solo);
        }
    }

    private Genre favGenreDialog() {
        List<String> choices = new ArrayList<>();
        for (Genre genre : Genre.values()) {
            choices.add(genre.name());
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>("GENRE", choices);
        dialog.setTitle("Add favourite genre");
        dialog.setHeaderText("Add favourite genre");
        dialog.setContentText("Choose genre:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            logger.info("Your choice: {}", result.get());
            return Genre.returnGenre(result.get());
        }
        return null;
    }
}
