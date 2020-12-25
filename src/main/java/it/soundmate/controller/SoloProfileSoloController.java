package it.soundmate.controller;

import it.soundmate.database.SoloDao;
import it.soundmate.model.Solo;

import java.util.List;

public class SoloProfileSoloController {

    private final SoloDao soloDao = SoloDao.getInstance();

    public List<String> getSoloInstruments(Solo solo) {
        return solo.getInstrument();
    }

}
