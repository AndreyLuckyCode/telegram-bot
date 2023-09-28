package com.andrey.lucky_job.views.profile;

import com.andrey.lucky_job.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

        // Используйте это поле для хранения информации о текущем пользователе
        private Employer currentUser;

        public EmployerProfileView() {

        }

        // Установите текущего пользователя с помощью этого метода
        public void setCurrentUser(Employer currentUser) {
            this.currentUser = currentUser;
        }
}
