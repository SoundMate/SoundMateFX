/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 09/01/21, 20:09
 * Last edited: 09/01/21, 20:09
 */

package it.soundmate.database.searchengine;

import it.soundmate.model.Band;

import java.util.ArrayList;
import java.util.List;

public class SearchBand implements SearchEngine<Band> {
    @Override
    public List<Band> searchForName(String name) {
        return new ArrayList<>();
    }
}
