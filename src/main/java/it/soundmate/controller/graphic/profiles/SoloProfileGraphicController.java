/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 18/01/21, 18:06
 * Last edited: 14/01/21, 23:03
 */

package it.soundmate.controller.graphic.profiles;

import it.soundmate.controller.logic.profiles.SoloProfileController;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Genre;
import it.soundmate.model.Solo;
import it.soundmate.view.uicomponents.InstrumentGraphics;
import javafx.scene.control.ChoiceDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SoloProfileGraphicController extends EditGraphicController {

    private final SoloProfileController soloProfileController = new SoloProfileController();
    private static final Logger logger = LoggerFactory.getLogger(SoloProfileGraphicController.class);

    public Genre addGenre(Solo solo){
        Genre selectedGenre = favGenreDialog();
        if (selectedGenre == null) {
            throw new InputException("Genre was not selected");
        } else {
            return soloProfileController.addGenre(selectedGenre, solo);
        }
    }

    private Genre favGenreDialog() {
        return BandProfileGraphicController.playedGenreDialog();
    }

    public InstrumentGraphics addInstrument(Solo solo) {
        InstrumentGraphics instrument = instrumentDialog();
        if (instrument == null) throw new InputException("Instrument not selected");
        else {
            try {
                soloProfileController.addInstrument(instrument.getName(), solo);
                return instrument;
            } catch (InputException inputException) {
                throw new InputException(inputException.getMessage());
            }
        }
    }

    public static InstrumentGraphics instrumentDialog() {
        List<String> choices = new ArrayList<>();
        for (InstrumentGraphics instrumentGraphics : InstrumentGraphics.values()) {
            choices.add(instrumentGraphics.name());
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>("INSTRUMENT", choices);
        dialog.setTitle("Add instrument");
        dialog.setHeaderText("Add instrument");
        dialog.setContentText("Choose instrument:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            logger.info("Your choice: {}", result.get());
            return InstrumentGraphics.returnInstrument(result.get());
        }
        return null;
    }
}
