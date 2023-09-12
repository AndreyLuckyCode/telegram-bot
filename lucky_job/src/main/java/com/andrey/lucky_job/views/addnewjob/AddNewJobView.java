package com.andrey.lucky_job.views.addnewjob;

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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@PageTitle("Add New Job")
@Route(value = "post-new-job", layout = MainLayout.class)
@Component
@UIScope
@Scope("prototype")
public class AddNewJobView extends Composite<VerticalLayout> {

    private final VacancyService vacancyService;
    private final SearcherView searcherView;

    @Autowired
    public AddNewJobView(VacancyService vacancyService, SearcherView searcherView) {
        this.vacancyService = vacancyService;
        this.searcherView = searcherView;

        // Create interface components
        Button addButton = new Button("Add", new Icon(VaadinIcon.PLUS));
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Define input fields
        TextArea companyTextArea = new TextArea("Company");
        TextArea requirementsTextArea = new TextArea("Requirements");
        TextArea responsibilitiesTextArea = new TextArea("Responsibilities");
        TextField salaryTextField = new TextField("Salary");


        // Handle the button click event
        addButton.addClickListener(event -> {
            String company = companyTextArea.getValue();
            String requirements = requirementsTextArea.getValue();
            String responsibilities = responsibilitiesTextArea.getValue();
            String salaryText = salaryTextField.getValue();

            if (!salaryText.isEmpty()) {
                try {
                    int salary = Integer.parseInt(salaryText);
                    Vacancy vacancy = new Vacancy(company, requirements, responsibilities, salary);
                    vacancyService.saveVacancy(vacancy);

                    // Trigger the AddCardEvent to add a new card in the SearcherView
                    SearcherView.AddCardEvent addCardEvent = new SearcherView.AddCardEvent(searcherView, company, requirements, responsibilities, salary);
                    searcherView.getElement().executeJs("this.$server.addCardEvent($0)", addCardEvent);

                    // Clear input fields after adding
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
        });

        // Set component widths
        companyTextArea.setWidthFull();
        requirementsTextArea.setWidthFull();
        responsibilitiesTextArea.setWidthFull();
        salaryTextField.setWidthFull();

        // Add components to the layout
        VerticalLayout content = getContent();
        content.setHeightFull();
        content.setWidthFull();
        content.add(addButton, companyTextArea, requirementsTextArea, responsibilitiesTextArea, salaryTextField);
        content.setAlignSelf(FlexComponent.Alignment.START, addButton);
    }
}