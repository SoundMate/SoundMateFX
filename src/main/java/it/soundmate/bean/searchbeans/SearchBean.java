/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 08/02/21, 13:17
 * Last edited: 08/02/21, 13:17
 */

package it.soundmate.bean.searchbeans;

import it.soundmate.controller.logic.SearchController;
import it.soundmate.model.User;

import java.util.List;

public class SearchBean {

    private String searchString;
    private String[] advancedFilters;
    private boolean[] filters;
    private User user;

    public SearchBean(){}

    public SearchBean(String searchString, boolean[] filters, String [] advancedFilters, User user) {
        this.searchString = searchString;
        this.advancedFilters = advancedFilters;
        this.filters = filters;
        this.user = user;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String[] getAdvancedFilters() {
        return advancedFilters;
    }

    public void setAdvancedFilters(String[] advancedFilters) {
        this.advancedFilters = advancedFilters;
    }

    public void setFilters(boolean[] filters) {
        this.filters = filters;
    }

    public boolean[] getFilters() {
        return filters;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserResultBean> search() {
        SearchController searchController = new SearchController();
        return searchController.performSearch(this.searchString, this.filters, this.advancedFilters, user);
    }
}
