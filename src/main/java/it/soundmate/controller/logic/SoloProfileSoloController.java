/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/01/21, 15:22
 * Last edited: 11/01/21, 20:53
 */

package it.soundmate.controller.logic;

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
