package com.andrey.lucky_job.views.searcher;

import com.andrey.lucky_job.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Searcher")
@Route(value = "searcher", layout = MainLayout.class)
public class SearcherView extends Composite<VerticalLayout> {

    public SearcherView() {
        getContent().setHeightFull();
        getContent().setWidthFull();
    }
}
