/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 09/01/21, 20:09
 * Last edited: 09/01/21, 20:09
 */

package it.soundmate.database.searchengine;

import it.soundmate.bean.searchbeans.RoomRenterResultBean;


import java.util.ArrayList;
import java.util.List;

public class SearchRenter implements SearchEngine<RoomRenterResultBean> {
    @Override
    public List<RoomRenterResultBean> searchForName(String name) {
        return new ArrayList<>();
    }
}
