/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 15/01/21, 13:10
 * Last edited: 15/01/21, 13:10
 */

package it.soundmate.controller.graphic.search;

import it.soundmate.bean.searchbeans.UserResultBean;
import it.soundmate.controller.logic.SearchController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DefaultSearchGraphicController {

    private final SearchController searchController = new SearchController();
    private static final Logger logger = LoggerFactory.getLogger(DefaultSearchGraphicController.class);
    private final boolean[] filters;
    private final String[] advancedFilters;

    public DefaultSearchGraphicController(List<RadioButton> filters, List<ComboBox<Label>> advancedFilters) {
        this.filters = getFilterValues(filters);
        this.advancedFilters = getAdvancedFilterValues(advancedFilters);
    }

    private String[] getAdvancedFilterValues(List<ComboBox<Label>> advancedFilters) {
        String[] advancedFilterValues = new String[3];
        for (int i = 0; i < 3; i++) {
            if (advancedFilters.get(i).getSelectionModel().getSelectedItem() == null) {
                advancedFilterValues[i] = "";
                continue;
            }
            advancedFilterValues[i] = advancedFilters.get(i).getSelectionModel().getSelectedItem().getText();
        }
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

    public List<UserResultBean> performSearch(String searchString) {
        return searchController.performSearch(searchString, filters, advancedFilters);
    }
}
