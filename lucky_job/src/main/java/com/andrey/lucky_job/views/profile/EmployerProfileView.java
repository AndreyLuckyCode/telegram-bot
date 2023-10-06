package com.andrey.lucky_job.views.profile;

import com.andrey.lucky_job.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.andrey.lucky_job.models.Employer;

@PageTitle("My Profile")
@Route(value = "employer-profile", layout = MainLayout.class)
@Component
@VaadinSessionScope
@Scope("prototype")
public class EmployerProfileView extends VerticalLayout implements AfterNavigationObserver, BeforeEnterObserver {

        private final Paragraph firstNameParagraph = new Paragraph("First Name");
        private final Paragraph lastNameParagraph = new Paragraph("Last Name");
        private final Paragraph emailParagraph = new Paragraph("Email");
        private final Paragraph dateOfBirthParagraph = new Paragraph("Date of birth");
        private final Paragraph phoneNumberParagraph = new Paragraph("Phone number");
        private final Button logoutButton = new Button("Log out", e -> logout());

        // Хранение информации о текущем пользователе
        private Employer currentUser;

        public EmployerProfileView() {
                this.add(
                        firstNameParagraph, lastNameParagraph, emailParagraph,
                        dateOfBirthParagraph, phoneNumberParagraph, logoutButton
                );
        }

        @Override
        public void afterNavigation(AfterNavigationEvent event) {
                System.out.println("afterNavigation called");
                // определить текущего пользователя из сессии
                Employer currentUser = (Employer) VaadinSession.getCurrent().getAttribute("user");
                if(currentUser != null) {
                        setCurrentUser(currentUser);
                }
        }

        // проверка на наличие сессии
        @Override
        public void beforeEnter(BeforeEnterEvent event) {
                Object user = VaadinSession.getCurrent().getAttribute("user");
                if (!(user instanceof Employer)) {
                        event.rerouteTo("login");
                }
        }

        public void setCurrentUser(Employer currentUser) {
                this.currentUser = currentUser;
                updateProfileFields();
        }

        private void updateProfileFields() {
                if (currentUser != null) {
                        firstNameParagraph.setText("Name: " + currentUser.getName());
                        lastNameParagraph.setText("Surname: " + currentUser.getSurname());
                        emailParagraph.setText("Email: " + currentUser.getEmail());
                        dateOfBirthParagraph.setText("Date of birth: " + currentUser.getDateOfBirth().toString());
                        phoneNumberParagraph.setText("Phone number: " + currentUser.getPhoneNumber());
                }
        }

        private void logout() {
                // Инвалидация сессии
                VaadinSession.getCurrent().getSession().invalidate();
                System.out.println("Сессия закрыта (работодатель)");

                getUI().ifPresent(ui -> ui.navigate("login"));
        }
}
