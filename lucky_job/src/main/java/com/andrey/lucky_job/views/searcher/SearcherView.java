package com.andrey.lucky_job.views.searcher;

import com.andrey.lucky_job.models.Vacancy;
import com.andrey.lucky_job.service.VacancyService;
import com.andrey.lucky_job.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@PageTitle("Searcher")
@Route(value = "searcher", layout = MainLayout.class)
@Component
@VaadinSessionScope
@Scope("prototype")
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
            addCardData(vacancy.getCompany(), vacancy.getRequirements(),
                    vacancy.getResponsibilities(), vacancy.getSalary());
        }
    }

    // Метод для добавления данных для создания карточки
    public void addCardData(String company, String requirements, String responsibilities, int salary) {
        SearcherViewCard card = new SearcherViewCard(company, requirements, responsibilities, salary);
        content.addComponentAtIndex(0, card); // Добавить карточку в начало VerticalLayout
    }

    public static class AddCardEvent extends ComponentEvent<SearcherView> {
        private final String company;
        private final String requirements;
        private final String responsibilities;
        private final int salary;

        public AddCardEvent(SearcherView source, String company, String requirements, String responsibilities, int salary) {
            super(source, false);
            this.company = company;
            this.requirements = requirements;
            this.responsibilities = responsibilities;
            this.salary = salary;
        }

        public String getCompany() {
            return company;
        }

        public String getRequirements() {
            return requirements;
        }

        public String getResponsibilities() {
            return responsibilities;
        }

        public int getSalary() {
            return salary;
        }
    }

    public Registration addAddCardListener(ComponentEventListener<AddCardEvent> listener) {
        return addListener(AddCardEvent.class, listener);
    }
}