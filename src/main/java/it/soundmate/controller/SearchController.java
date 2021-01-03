package it.soundmate.controller;

import it.soundmate.model.SearchEngine;
import it.soundmate.model.User;
import javafx.scene.control.RadioButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    private final SearchEngine searchEngine;

    public SearchController() {
        this.searchEngine = new SearchEngine();
    }

    public List<User> performSearch(String searchString, List<RadioButton> filters) {
        List<User> results = new ArrayList<>();
        boolean[] filterValues = getFilterValues(filters);
        if (filtersAllTrue(filterValues) || filtersAllFalse(filterValues)) {
            results.addAll(this.searchEngine.searchByName(searchString));
        } else {
            if (filterValues[0]) {
                results.addAll(this.searchEngine.searchSolos(searchString));
            }
            if (filterValues[1]) {
                //results.addAll(this.searchEngine.searchBands(searchString));
            }
            if (filterValues[2]) {
                results.addAll(this.searchEngine.searchRooms(searchString));
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
