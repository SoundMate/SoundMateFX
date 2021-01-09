/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 09/01/21, 20:08
 * Last edited: 09/01/21, 20:05
 */

package it.soundmate.database.searchengine;

import java.util.List;

public interface SearchEngine<T> {
    List<T> searchForName(String name);
}
