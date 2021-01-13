/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/01/21, 13:53
 * Last edited: 12/01/21, 13:53
 */

package it.soundmate.controller;

import it.soundmate.bean.searchbeans.UserResultBean;
import it.soundmate.controller.logic.SearchController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class SearchControllerTest {

    private final List<UserResultBean> userResultBeanList = new ArrayList<>();
    private final SearchController searchController = new SearchController();
    private static final Logger logger = LoggerFactory.getLogger(SearchControllerTest.class);


    @BeforeAll
    public static void init() {
        logger.info("Initialized result List");
    }

    @Test
    void testPerformSearchSoloByName() {
        boolean[] soloFilter = {true, false, false};
        userResultBeanList.addAll(searchController.performSearch("Lo", soloFilter));
        assertFalse(userResultBeanList.isEmpty());
    }
}