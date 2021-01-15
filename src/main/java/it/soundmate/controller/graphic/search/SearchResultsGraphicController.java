/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 15/01/21, 12:41
 * Last edited: 13/01/21, 15:23
 */

package it.soundmate.controller.graphic.search;

import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.view.main.SearchView;
import it.soundmate.view.search.SoloSearchView;

public class SearchResultsGraphicController {

    public void navigateToSoloResult(SoloResultBean soloResultBean, SearchView searchView) {
        SoloSearchView soloResultView = new SoloSearchView(soloResultBean);
        searchView.setDetailViewSolo(soloResultView);
    }

}
