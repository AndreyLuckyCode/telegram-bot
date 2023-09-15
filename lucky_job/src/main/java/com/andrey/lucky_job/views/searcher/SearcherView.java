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

    private final VerticalLayout content;
    private final VacancyService vacancyService;


    //Даем доступ к сервису
    @Autowired
    public SearcherView(VacancyService vacancyService) {
        this.vacancyService = vacancyService;

        content = getContent();
        content.setHeightFull();
        content.setWidthFull();

        loadAndDisplayCards();
    }


    //Подгружаем существующие карточки из БД и отображаем их
    private void loadAndDisplayCards() {
        List<Vacancy> vacancies = vacancyService.getAllVacancies();

        for (Vacancy vacancy : vacancies) {
            addCardData(vacancy);
        }
    }


    //Заполняем очередную карточку инфой из полей
    public void addCardData(Vacancy vacancy) {
        SearcherViewCard card = new SearcherViewCard(
                vacancy.getCompany(),
                vacancy.getRequirements(),
                vacancy.getResponsibilities(),
                vacancy.getSalary(),
                vacancy.getId(),
                vacancyService
        );
        content.addComponentAtIndex(0, card); // Добавление новой карточки сверху
    }


    //Этот вложенный ивент класс нужен для передачи данных между компонентами
    //Триггерится при регистрации новых значений полей
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


    //Слушатель, который реалирует на ивент
    public Registration addAddCardListener(ComponentEventListener<AddCardEvent> listener) {
        return addListener(AddCardEvent.class, listener);
    }
}