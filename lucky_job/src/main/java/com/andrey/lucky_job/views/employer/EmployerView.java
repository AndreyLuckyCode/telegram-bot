package com.andrey.lucky_job.views.employer;

import com.andrey.lucky_job.models.Employer;
import com.andrey.lucky_job.models.Searcher;
import com.andrey.lucky_job.service.SearcherService;
import com.andrey.lucky_job.views.MainLayout;
import com.andrey.lucky_job.views.addnewjob.AddNewJobView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Employer")
@Route(value = "employer", layout = MainLayout.class)
@Uses(Icon.class)
public class EmployerView extends Composite<VerticalLayout> implements BeforeEnterObserver {
    private final SearcherService searcherService;

    @Autowired
    public EmployerView(SearcherService searcherService) {
        this.searcherService = searcherService;

        Button buttonPrimary = new Button();
        H3 h3 = new H3();
        Grid<Searcher> basicGrid = new Grid<>(Searcher.class);
        basicGrid.setColumns("name", "surname", "dateOfBirth", "phoneNumber", "email", "role");

        getContent().setHeightFull();
        getContent().setWidthFull();
        buttonPrimary.setText("One more vacancy?");
        buttonPrimary.addClickListener(event -> {
            // Переход на страницу "Add new Job"
            UI.getCurrent().navigate(AddNewJobView.class);
        });

        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        h3.setText("Candidates");
        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, h3);

        setGridSearcherData(basicGrid);
        getContent().add(buttonPrimary);
        getContent().add(h3);
        getContent().add(basicGrid);
    }

    private void setGridSearcherData(Grid<Searcher> grid) {
        // Использование вашего текущего сервиса для установки данных в таблицу
        grid.setItems(searcherService.getAllSearchers());
    }

    //Проверка роли пользователя текущей сессии (Employer only)
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Object user = VaadinSession.getCurrent().getAttribute("user");
        if (!(user instanceof Employer)) {
            // перемещение пользователя на страницу входа
            event.rerouteTo("main");
            Notification.show("This page is available only for Employers");
        }
    }
}