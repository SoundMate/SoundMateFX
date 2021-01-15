/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 15/01/21, 14:42
 * Last edited: 15/01/21, 14:42
 */

package it.soundmate.controller.database;


import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.database.Connector;
import it.soundmate.database.searchengine.SearchSolo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdvancedSearchTest {

    private static final Logger logger = LoggerFactory.getLogger(AdvancedSearchTest.class);
    private final String[] advancedFilters = {"JAZZ", "", ""};

    @Test
    void advancedSearchByGenreTest() throws SQLException {
        Connection connection = Connector.getInstance().getConnection();
        SearchSolo searchSolo = new SearchSolo("", connection, advancedFilters);
        List<SoloResultBean> result = searchSolo.advancedSearchWithNoName("JAZZ");
        assertFalse(result.isEmpty());
        logger.info("Results: {}, {}", result.get(0).getFirstName(), result.get(0).getGenreList());
    }


}
