package com.andrey.lucky_job.views.profile;

import com.andrey.lucky_job.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.andrey.lucky_job.models.Employer;

@PageTitle("My Profile")
@Route(value = "employer-profile", layout = MainLayout.class)
@Component
@VaadinSessionScope
@Scope("prototype")
public class EmployerProfileView extends VerticalLayout {

        private final TextField firstNameField = new TextField("First Name");
        private final TextField lastNameField = new TextField("Last Name");
        private final TextField emailField = new TextField("Email");
        private final TextField dateOfBirthField = new TextField("Date of birth");
        private final TextField phoneNumberField = new TextField("Phone number");
        private final Button updateProfileInfoButton = new Button("update");
        private final Button logoutButton = new Button ("Log out");



        // Поле для хранения информации о текущем пользователе
        private Employer currentUser;

        public EmployerProfileView() {
                this.add(firstNameField, lastNameField, emailField, dateOfBirthField, phoneNumberField, updateProfileInfoButton);
        }

        // Определить текущего пользователя
        public void setCurrentUser(Employer currentUser) {
                this.currentUser = currentUser;

                updateProfileFields();
        }

        private void updateProfileFields() {
                if (currentUser != null) {
                        firstNameField.setValue(currentUser.getName());
                        lastNameField.setValue(currentUser.getSurname());
                        emailField.setValue(currentUser.getEmail());
                        // значение для дополнительных полей
                } else {
                        // или отсутствие такого пользователя
                }
        }
}
