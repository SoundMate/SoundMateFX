/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 13/01/21, 15:06
 * Last edited: 13/01/21, 15:06
 */

package it.soundmate.controller.graphic;

import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.view.main.SearchView;
import it.soundmate.view.search.SoloSearchView;

public class SearchResultsGraphicController {

    public void navigateToSoloResult(SoloResultBean soloResultBean) {
        SoloSearchView soloResultView = new SoloSearchView(soloResultBean);
        SearchView.getInstance().setDetailViewSolo(soloResultView);
    }

}
