package com.andrey.lucky_job.views.addnewjob;

import com.andrey.lucky_job.models.Vacancy;
import com.andrey.lucky_job.service.VacancyService;
import com.andrey.lucky_job.views.MainLayout;
import com.andrey.lucky_job.views.searcher.SearcherView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Add New Job")
@Route(value = "post-new-job", layout = MainLayout.class)
@Uses(Icon.class)
public class AddNewJobView extends Composite<VerticalLayout> {

    @Autowired
    public AddNewJobView(SearcherView searcherView, VacancyService vacancyService) {

        // Создаем компоненты интерфейса
        Button addButton = createButton("Add");
        TextArea companyTextArea = createTextArea("Company");
        TextArea requirementsTextArea = createTextArea("Requirements");
        TextArea responsibilitiesTextArea = createTextArea("Responsibilities");
        TextArea salaryTextArea = createTextArea("Salary");

        addButton.addClickListener(event -> {
            // Получите значения из текстовых полей
            String company = companyTextArea.getValue();
            String requirements = requirementsTextArea.getValue();
            String responsibilities = responsibilitiesTextArea.getValue();
            String salaryText = salaryTextArea.getValue(); // Получите текст из поля salary

            if (!salaryText.isEmpty()) {
                try {
                    int salary = Integer.parseInt(salaryText);

                    // Создайте объект Vacancy
                    Vacancy vacancy = new Vacancy(company, requirements, responsibilities, salary);

                    // Сохраните объект Vacancy в базе данных с помощью VacancyService
                    vacancyService.saveVacancy(vacancy);

                    // Добавьте данные для создания карточки в SearcherView
                    searcherView.addCardData(company, "URL_КАРТИНКИ");

                    // Очистите текстовые поля после добавления
                    companyTextArea.clear();
                    requirementsTextArea.clear();
                    responsibilitiesTextArea.clear();
                    salaryTextArea.clear();

                } catch (NumberFormatException e) {
                    // Обработка ошибки, если введенное значение не является числом
                    // Выведите сообщение об ошибке или выполните другие действия по вашему усмотрению.
                    Notification.show("Ошибка: Значение поля 'salary' должно быть числом.");
                }
            } else {
                // Обработка ошибки, если текстовое поле salary пустое
                // Выведите сообщение об ошибке или выполните другие действия по вашему усмотрению.
                Notification.show("Ошибка: Поле 'salary' не может быть пустым.");
            }
        });

// Устанавливаем ширину и выравнивание элементов
        companyTextArea.setWidthFull();
        requirementsTextArea.setWidthFull();
        responsibilitiesTextArea.setWidthFull();
        salaryTextArea.setWidthFull();

// Добавляем компоненты на вертикальный макет
        VerticalLayout content = getContent();
        content.setHeightFull();
        content.setWidthFull();
        content.add(addButton, companyTextArea, requirementsTextArea, responsibilitiesTextArea, salaryTextArea);
        content.setAlignSelf(FlexComponent.Alignment.START, addButton);
        content.setAlignSelf(FlexComponent.Alignment.START, companyTextArea);
    }


    private Button createButton(String text) {
        Button button = new Button(text);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    private TextArea createTextArea(String label) {
        TextArea textArea = new TextArea();
        textArea.setLabel(label);
        return textArea;
    }
}