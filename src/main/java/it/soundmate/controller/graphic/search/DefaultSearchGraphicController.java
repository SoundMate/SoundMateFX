/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 15/01/21, 13:10
 * Last edited: 15/01/21, 13:10
 */

package it.soundmate.controller.graphic.search;

import it.soundmate.bean.searchbeans.UserResultBean;
import it.soundmate.controller.logic.SearchController;
import it.soundmate.model.User;
import javafx.scene.control.RadioButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DefaultSearchGraphicController {

    private final SearchController searchController;
    private static final Logger logger = LoggerFactory.getLogger(DefaultSearchGraphicController.class);
    private final boolean[] filters;
    private final String[] advancedFilters;

    public DefaultSearchGraphicController(List<RadioButton> filters, String genreFilter, String instrumentFilter, String cityFilter) {
        this.searchController = new SearchController();
        this.filters = getFilterValues(filters);
        this.advancedFilters = getAdvancedFilterValues(genreFilter, instrumentFilter, cityFilter);
    }

    private String[] getAdvancedFilterValues(String genreFilter, String instrumentFilter, String cityFilter) {
        String[] advancedFilterValues = new String[3];
        if (genreFilter == null || genreFilter.equals("NONE")) {
            advancedFilterValues[0] = "NONE";
        } else advancedFilterValues[0] = genreFilter;
        if (instrumentFilter == null || instrumentFilter.equals("NONE")) {
            advancedFilterValues[1] = "NONE";
        } else advancedFilterValues[1] = instrumentFilter;
        if (cityFilter == null || cityFilter.equals("")) {
            advancedFilterValues[2] = "";
        } else advancedFilterValues[2] = cityFilter;
        logger.info("Selected values: Genre {}, Instrument {}, City {}", advancedFilterValues[0], advancedFilterValues[1], advancedFilterValues[2]);
        return advancedFilterValues;
    }

    private boolean[] getFilterValues(List<RadioButton> filters) {
        boolean[] values = new boolean[3];
        for (int i = 0; i < 3; i++) {
            values[i] = filters.get(i).isSelected();
        }
        return values;
    }

    public List<UserResultBean> performSearch(String searchString, User user) {
        return searchController.performSearch(searchString, filters, advancedFilters, user);
    }
}
