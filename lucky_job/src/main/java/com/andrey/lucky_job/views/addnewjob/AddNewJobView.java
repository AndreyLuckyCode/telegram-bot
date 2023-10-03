package com.andrey.lucky_job.views.addnewjob;

import com.andrey.lucky_job.models.Employer;
import com.andrey.lucky_job.models.Vacancy;
import com.andrey.lucky_job.service.VacancyService;
import com.andrey.lucky_job.views.MainLayout;
import com.andrey.lucky_job.views.searcher.SearcherView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@PageTitle("Add New Job")
@Route(value = "post-new-job", layout = MainLayout.class)
@Component
@UIScope
@Scope("prototype")
public class AddNewJobView extends Composite<VerticalLayout> implements BeforeEnterObserver {

    private final VacancyService vacancyService;
    private final SearcherView searcherView;
    private final Button addButton;
    private final TextArea companyTextArea;
    private final TextArea requirementsTextArea;
    private final TextArea responsibilitiesTextArea;
    private final TextField salaryTextField;

    @Autowired
    public AddNewJobView(VacancyService vacancyService, SearcherView searcherView) {
        this.vacancyService = vacancyService;
        this.searcherView = searcherView;

        // Инициализация интерфейсных компонентов
        addButton = createAddButton();
        companyTextArea = createTextArea("Company");
        requirementsTextArea = createTextArea("Requirements");
        responsibilitiesTextArea = createTextArea("Responsibilities");
        salaryTextField = createTextField("Salary $");

        // Обработка нажатия кнопки "Add"
        addButton.addClickListener(event -> handleAddButtonClick());

        // Установка ширины компонентов
        setComponentWidths();

        // Добавление компонентов в макет
        VerticalLayout content = getContent();
        content.setHeightFull();
        content.setWidthFull();
        content.add(addButton, companyTextArea, requirementsTextArea, responsibilitiesTextArea, salaryTextField);
        content.setAlignSelf(FlexComponent.Alignment.START, addButton);
    }

    private Button createAddButton() {
        Button addButton = new Button("Add", new Icon(VaadinIcon.PLUS));
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return addButton;
    }

    private TextArea createTextArea(String label) {
        TextArea textArea = new TextArea(label);
        textArea.setWidthFull();
        return textArea;
    }

    private TextField createTextField(String label) {
        TextField textField = new TextField(label);
        textField.setWidthFull();
        return textField;
    }

    private void setComponentWidths() {
        companyTextArea.setWidthFull();
        requirementsTextArea.setWidthFull();
        responsibilitiesTextArea.setWidthFull();
        salaryTextField.setWidthFull();
    }

    private void handleAddButtonClick() {
        String company = companyTextArea.getValue();
        String requirements = requirementsTextArea.getValue();
        String responsibilities = responsibilitiesTextArea.getValue();
        String salaryText = salaryTextField.getValue();

        if (!salaryText.isEmpty()) {
            try {
                int salary = Integer.parseInt(salaryText);
                Employer currentUser = (Employer) VaadinSession.getCurrent().getAttribute("user");
                Long employerId = currentUser.getId();
                Vacancy vacancy = new Vacancy(company, requirements, responsibilities, salary, employerId);
                vacancyService.saveVacancy(vacancy);

                // Триггер для AddCardEvent в SearcherView
                SearcherView.AddCardEvent addCardEvent = new SearcherView.AddCardEvent(searcherView, company, requirements, responsibilities, salary);
                searcherView.getElement().executeJs("this.$server.addCardEvent($0)", addCardEvent);

                // Очистка полей после нажатия кнопки
                companyTextArea.clear();
                requirementsTextArea.clear();
                responsibilitiesTextArea.clear();
                salaryTextField.clear();

            } catch (NumberFormatException e) {
                Notification.show("Error: Salary must be a number.");
            }
        } else {
            Notification.show("Error: Salary field cannot be empty.");
        }
    }

    //Проверка роли пользователя текущей сессии (Employer only)
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Object user = VaadinSession.getCurrent().getAttribute("user");
        if (!(user instanceof Employer)) {
            // перемещение пользователя на страницу входа
            event.rerouteTo("main");
            Notification.show("This page is available only for Employers");
        }
    }
}