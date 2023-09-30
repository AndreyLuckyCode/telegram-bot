package com.andrey.lucky_job.views.profile;

import com.andrey.lucky_job.models.Employer;
import com.andrey.lucky_job.models.Searcher;
import com.andrey.lucky_job.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@PageTitle("My Profile")
@Route(value = "searcher-profile", layout = MainLayout.class)
@Component
@VaadinSessionScope
@Scope("prototype")
public class SearcherProfileView extends VerticalLayout implements AfterNavigationObserver, BeforeEnterObserver {

    private final TextField firstNameField = new TextField("First Name");
    private final TextField lastNameField = new TextField("Last Name");
    private final TextField emailField = new TextField("Email");
    private final TextField dateOfBirthField = new TextField("Date of birth");
    private final TextField phoneNumberField = new TextField("Phone number");
    private final Button updateProfileInfoButton = new Button("update");
    private final Button logoutButton = new Button("Log out", e -> logout());

    // Хранение информации о текущем пользователе
    private Searcher currentUser;

    public SearcherProfileView() {
        this.add(firstNameField, lastNameField, emailField, dateOfBirthField
                , phoneNumberField, updateProfileInfoButton, logoutButton);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        System.out.println("afterNavigation called");
        // определить текущего пользователя из сессии
        Searcher currentUser = (Searcher) VaadinSession.getCurrent().getAttribute("user");
        if(currentUser != null) {
            setCurrentUser(currentUser);
        }
    }

    // проверка на наличие сессии
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Object user = VaadinSession.getCurrent().getAttribute("user");
        if (!(user instanceof Searcher)) {
            event.rerouteTo("login");
        }
    }

    public void setCurrentUser(Searcher currentUser) {
        this.currentUser = currentUser;
        updateProfileFields();
    }

    private void updateProfileFields() {
        if (currentUser != null) {
            firstNameField.setValue(currentUser.getName());
            lastNameField.setValue(currentUser.getSurname());
            emailField.setValue(currentUser.getEmail());
            dateOfBirthField.setValue(currentUser.getDateOfBirth().toString());
            phoneNumberField.setValue(currentUser.getPhoneNumber());
        }
    }

    private void logout() {
        // Инвалидация сессии
        VaadinSession.getCurrent().getSession().invalidate();
        System.out.println("Сессия закрыта (searcher)");

        getUI().ifPresent(ui -> ui.navigate("login"));
    }
}