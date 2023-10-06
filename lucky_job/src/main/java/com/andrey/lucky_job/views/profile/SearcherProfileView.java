package com.andrey.lucky_job.views.profile;

import com.andrey.lucky_job.models.CV;
import com.andrey.lucky_job.models.Searcher;
import com.andrey.lucky_job.models.Vacancy;
import com.andrey.lucky_job.service.CVService;
import com.andrey.lucky_job.service.VacancyService;
import com.andrey.lucky_job.views.MainLayout;
import com.andrey.lucky_job.views.searcher.CVChatView;
import com.andrey.lucky_job.views.searcher.SearcherViewCard;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

    private final Paragraph firstNameParagraph = new Paragraph("First Name");
    private final Paragraph lastNameParagraph = new Paragraph("Last Name");
    private final Paragraph emailParagraph = new Paragraph("Email");
    private final Paragraph dateOfBirthParagraph = new Paragraph("Date of birth");
    private final Paragraph phoneNumberParagraph = new Paragraph("Phone number");
    private final Button logoutButton = new Button("Log out", e -> logout());
    @Autowired
    private CVService cvService;
    @Autowired
    private VacancyService vacancyService;
    private Searcher currentUser;
    private final VerticalLayout cardsLayout = new VerticalLayout();


    public SearcherProfileView() {
        this.add(
                firstNameParagraph, lastNameParagraph, emailParagraph,
                dateOfBirthParagraph, phoneNumberParagraph, logoutButton
        );
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
            card.setWidth("29%");

            card.addClickListener(event -> {
                UI.getCurrent().navigate(CVChatView.class, currentVacancy.getId());
            });
        }

        // Добавляем карточки вакансий на страницу профиля
        for (SearcherViewCard card : vacancyCards) {
            cardsLayout.add(card);
        }
    }
}