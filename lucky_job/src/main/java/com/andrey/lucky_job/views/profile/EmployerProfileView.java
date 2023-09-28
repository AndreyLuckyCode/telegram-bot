package com.andrey.lucky_job.views.profile;

import com.andrey.lucky_job.views.MainLayout;
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

        private TextField firstNameField = new TextField("First Name");
        private TextField lastNameField = new TextField("Last Name");
        private TextField emailField = new TextField("Email");
        // Добавьте дополнительные поля, которые вы хотите отображать на странице профиля

        // Используйте это поле для хранения информации о текущем пользователе
        private Employer currentUser;

        public EmployerProfileView() {
                this.add(firstNameField, lastNameField, emailField); // Добавьте дополнительные поля, которые вы хотите отображать на странице профиля
        }

        // Установите текущего пользователя с помощью этого метода
        public void setCurrentUser(Employer currentUser) {
                this.currentUser = currentUser;

                updateProfileFields();
        }

        private void updateProfileFields() {
                if (currentUser != null) {
                        firstNameField.setValue(currentUser.getName());
                        lastNameField.setValue(currentUser.getSurname());
                        emailField.setValue(currentUser.getEmail());
                        // Установите значение для дополнительных полей
                } else {
                        // Обработайте отсутствие текущего пользователя, если необходимо
                }
        }
}
