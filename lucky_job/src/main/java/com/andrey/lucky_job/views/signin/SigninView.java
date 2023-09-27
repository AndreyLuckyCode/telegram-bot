package com.andrey.lucky_job.views.signin;

import com.andrey.lucky_job.models.Employer;
import com.andrey.lucky_job.models.Searcher;
import com.andrey.lucky_job.service.EmployerService;
import com.andrey.lucky_job.service.SearcherService;
import com.andrey.lucky_job.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Sign in")
@Route(value = "login", layout = MainLayout.class)
@Uses(Icon.class)
public class SigninView extends Composite<VerticalLayout> {

    @Autowired
    private EmployerService employerService;
    @Autowired
    private SearcherService searcherService;
    public SigninView(EmployerService employerService, SearcherService searcherService) {
        this.employerService = employerService;
        this.searcherService = searcherService;

        LoginForm loginForm = new LoginForm();
        getContent().setHeightFull();
        getContent().setWidthFull();
        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, loginForm);
        getContent().add(loginForm);

        loginForm.addLoginListener(event -> authenticate(event.getUsername(), event.getPassword(), event));
    }



    private void authenticate(String name, String password, LoginForm.LoginEvent event) {
        Employer employer = employerService.findEmployerByNameAndPassword(name, password);
        Searcher searcher = searcherService.findSearcherByNameAndPassword(name, password);

        if (employer != null) {
            // Вход выполнен успешно, редирект к странице работодателя
            UI.getCurrent().navigate("employer");
            Notification.show("Welcome back, dear employer!");
        } else if (searcher != null) {
            // Вход выполнен успешно, редирект к странице соискателя
            UI.getCurrent().navigate("searcher");
            Notification.show("Welcome back, dear searcher!");
        } else {
            Notification.show("Invalid credentials. Please enter correct data");
            event.getSource().setEnabled(true);
        }
    }
}

