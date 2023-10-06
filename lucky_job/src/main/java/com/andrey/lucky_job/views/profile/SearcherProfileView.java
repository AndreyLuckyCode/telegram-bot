package com.andrey.lucky_job.views.profile;

import com.andrey.lucky_job.models.CV;
import com.andrey.lucky_job.models.Employer;
import com.andrey.lucky_job.models.Searcher;
import com.andrey.lucky_job.models.Vacancy;
import com.andrey.lucky_job.service.CVService;
import com.andrey.lucky_job.service.SearcherService;
import com.andrey.lucky_job.service.VacancyService;
import com.andrey.lucky_job.views.MainLayout;
import com.andrey.lucky_job.views.searcher.SearcherViewCard;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private CVService cvService;
    @Autowired
    private VacancyService vacancyService;
    private Searcher currentUser;
    private final VerticalLayout cardsLayout = new VerticalLayout();


    public SearcherProfileView() {
        this.add(firstNameField, lastNameField, emailField, dateOfBirthField
                , phoneNumberField, updateProfileInfoButton, logoutButton);
        this.add(cardsLayout);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        System.out.println("afterNavigation called");
        // определить текущего пользователя из сессии
        Searcher currentUser = (Searcher) VaadinSession.getCurrent().getAttribute("user");
        if(currentUser != null) {
            setCurrentUser(currentUser);
            displayUserVacancies();
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

    private void displayUserVacancies() {
        cardsLayout.removeAll();
        String currentUserEmail = currentUser.getEmail();

        List<CV> allCVs = cvService.getAllCVs();
        List<CV> allCVsOfTheCurrentUser = new ArrayList<>();
        for (CV cv : allCVs) {
            if (cv.getAuthor().equals(currentUserEmail)) {
                allCVsOfTheCurrentUser.add(cv);
            }
        }
        List<Long> vacancyIds = new ArrayList<>();

        for(CV cv : allCVsOfTheCurrentUser){
                 vacancyIds.add(cv.getVacancyId());
        }

        // Создаем список для карточек вакансий
        List<SearcherViewCard> vacancyCards = new ArrayList<>();

        // Создаем и заполняем карточки вакансий для каждого id из списка
        for (Long vacancyId : vacancyIds) {
            Vacancy currentVacancy = vacancyService.getVacancy(vacancyId);
            SearcherViewCard card = new SearcherViewCard(
                    currentVacancy.getCompany(),
                    currentVacancy.getRequirements(),
                    currentVacancy.getResponsibilities(),
                    currentVacancy.getSalary(),
                    currentVacancy.getId(),
                    vacancyService,
                    true,
                    cvService,
                    currentVacancy);
            vacancyCards.add(card);
        }

        // Добавляем карточки вакансий на страницу профиля
        for (SearcherViewCard card : vacancyCards) {
            cardsLayout.add(card);
        }
    }
}