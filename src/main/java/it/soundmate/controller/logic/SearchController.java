/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/01/21, 15:22
 * Last edited: 12/01/21, 15:01
 */

package it.soundmate.controller.logic;

import it.soundmate.bean.searchbeans.UserResultBean;
import it.soundmate.model.SearchModel;
import it.soundmate.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    private final SearchModel searchModel;

    public SearchController() {
        this.searchModel = new SearchModel();
    }

    public List<UserResultBean> performSearch(String searchString, boolean[] filters, String[] advancedFilters, User user) {
        List<UserResultBean> results = new ArrayList<>();
        if ((filtersAllTrue(filters) || filtersAllFalse(filters))) {
            results.addAll(this.searchModel.searchByName(searchString, advancedFilters));
        } else {
            if (filters[0]) {
                logger.info("Searching solos...");
                results.addAll(this.searchModel.searchSolos(searchString, advancedFilters));
            }
            if (filters[1]) {
                logger.info("Searching bands...");
                results.addAll(this.searchModel.searchBands(searchString, advancedFilters[0], advancedFilters[2]));
            }
            if (filters[2]) {
                logger.info("Searching rooms...");
                results.addAll(this.searchModel.searchRooms(searchString, advancedFilters[2]));
            }
        }
        for (UserResultBean resultBean : results) {
            resultBean.setSearcher(user);
        }
        return results;
    }

    private boolean filtersAllTrue(boolean[] array) {
        for(boolean b : array) if(!b) return false;
        return true;
    }

    private boolean filtersAllFalse(boolean[] array) {
        for(boolean b : array) if(b) return false;
        return true;
    }

}
