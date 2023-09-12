package com.andrey.lucky_job.views.searcher;

import com.andrey.lucky_job.models.Vacancy;
import com.andrey.lucky_job.service.VacancyService;
import com.andrey.lucky_job.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@PageTitle("Searcher")
@Route(value = "searcher", layout = MainLayout.class)
@Component
public class SearcherView extends Composite<VerticalLayout> {

    private VerticalLayout content;
    private final VacancyService vacancyService;

    @Autowired
    public SearcherView(VacancyService vacancyService) {
        this.vacancyService = vacancyService;

        content = getContent();
        content.setHeightFull();
        content.setWidthFull();

// Загрузите и отобразите все карточки из базы данных при инициализации представления
        loadAndDisplayCards();
    }

    // Метод для загрузки и отображения карточек из базы данных
    private void loadAndDisplayCards() {
        List<Vacancy> vacancies = vacancyService.getAllVacancies();
        for (Vacancy vacancy : vacancies) {
            addCardData(vacancy.getCompany(), vacancy.getRequirements()
                    , vacancy.getResponsibilities(), vacancy.getSalary()); // Замените "URL_КАРТИНКИ" на реальный URL из Vacancy, если у вас есть такое поле.
        }
    }

    // Метод для добавления данных для создания карточки
    public void addCardData(String company, String requirements, String responsibilities, int salary) {
        SearcherViewCard card = new SearcherViewCard(company, requirements, responsibilities, salary);
        content.addComponentAtIndex(0, card); // Добавить карточку в начало VerticalLayout
    }
}