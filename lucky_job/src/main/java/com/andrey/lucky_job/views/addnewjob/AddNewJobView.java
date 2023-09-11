package com.andrey.lucky_job.views.addnewjob;

import com.andrey.lucky_job.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Add New Job")
@Route(value = "post-new-job", layout = MainLayout.class)
@Uses(Icon.class)
public class AddNewJobView extends Composite<VerticalLayout> {

    public AddNewJobView() {
        // Создаем компоненты интерфейса
        Button addButton = createButton("Add");
        TextArea companyTextArea = createTextArea("Company");
        TextArea requirementsTextArea = createTextArea("Requirements");
        TextArea responsibilitiesTextArea = createTextArea("Responsibilities");
        TextArea salaryTextArea = createTextArea("Salary");

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
