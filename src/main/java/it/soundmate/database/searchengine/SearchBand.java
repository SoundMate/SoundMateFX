/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 09/01/21, 20:09
 * Last edited: 09/01/21, 20:09
 */

package it.soundmate.database.searchengine;

import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.bean.searchbeans.UserResultBean;
import it.soundmate.model.Band;

import java.util.ArrayList;
import java.util.List;

public class SearchBand implements SearchEngine<BandResultBean> {
    @Override
    public List<BandResultBean> searchForName(String name) {
        return new ArrayList<>();
    }
}
