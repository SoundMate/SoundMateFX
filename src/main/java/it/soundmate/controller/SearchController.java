package it.soundmate.controller;

import it.soundmate.bean.searchbeans.UserResultBean;
import it.soundmate.model.SearchModel;
import it.soundmate.model.User;
import javafx.scene.control.RadioButton;
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

    public List<UserResultBean> performSearch(String searchString, List<RadioButton> filters) {
        List<UserResultBean> results = new ArrayList<>();
        boolean[] filterValues = getFilterValues(filters);
        if (filtersAllTrue(filterValues) || filtersAllFalse(filterValues)) {
            results.addAll(this.searchModel.searchByName(searchString));
        } else {
            if (filterValues[0]) {
                logger.info("Searching solos...");
                results.addAll(this.searchModel.searchSolos(searchString));
            }
            if (filterValues[1]) {
                logger.info("Searching bands...");
                results.addAll(this.searchModel.searchBands(searchString));
            }
            if (filterValues[2]) {
                logger.info("Searching rooms...");
                results.addAll(this.searchModel.searchRooms(searchString));
            }
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

    private boolean[] getFilterValues(List<RadioButton> filters) {
        boolean[] values = new boolean[3];
        for (int i = 0; i < 3; i++) {
            values[i] = filters.get(i).isSelected();
        }
        return values;
    }

}
