package it.soundmate.controller;

import it.soundmate.database.dao.SoloDao;
import it.soundmate.model.Solo;

import java.util.List;

public class SoloProfileSoloController {


    public List<String> getSoloInstruments(Solo solo) {
        return solo.getInstruments();
    }

    public boolean addGenre(String s, Solo soloUser) {
        return false;
    }
}
