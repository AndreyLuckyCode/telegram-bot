package com.andrey.lucky_job.views.searcher;

import com.andrey.lucky_job.models.Vacancy;
import com.andrey.lucky_job.service.VacancyService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility.*;

public class SearcherViewCard extends ListItem {
    private Span header;
    private Span subtitle;
    private Paragraph description;
    private Span badge;

    public SearcherViewCard(String company, String requirements, String responsibilities, int salary, Long vacancyId, VacancyService vacancyService) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("185px");

        Image image = new Image("https://img.reg.ru/news/823dd699786133.Y3JvcCwxMTUwLDkwMCwyMjUsMA.gif", "Company Logo");
        image.setWidth("100%");

        div.add(image);

        // Создаем элементы header, subtitle, description, badge
        header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(company);

        subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText(requirements);

        description = new Paragraph(responsibilities);
        description.addClassName(Margin.Vertical.MEDIUM);

        badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        badge.setText(String.format("%d $", salary));

        Button updateButton = new Button("Update");
        updateButton.addClickListener(event -> {
            createEditDialog(company, requirements, responsibilities, salary, vacancyId, vacancyService);
        });

        Button deleteButton = new Button("Delete");
        deleteButton.addClickListener(event -> {
            vacancyService.deleteVacancy(vacancyId);
            this.setVisible(false);
        });

        HorizontalLayout buttonLayout = new HorizontalLayout(updateButton, deleteButton);
        buttonLayout.setSpacing(true);

        add(div, header, subtitle, description, badge, buttonLayout);
    }


    // Диалоговое окно для изменения карточки
    private void createEditDialog(String company, String requirements, String responsibilities, int salary, Long vacancyId, VacancyService vacancyService) {
        Dialog editDialog = new Dialog();

        Vacancy vacancy = vacancyService.getVacancy(vacancyId);

        TextField editedCompany = new TextField("Edited Company");
        editedCompany.setValue(vacancy.getCompany());

        TextField editedRequirements = new TextField("Edited Requirements");
        editedRequirements.setValue(vacancy.getRequirements());

        TextField editedResponsibilities = new TextField("Edited Responsibilities");
        editedResponsibilities.setValue(vacancy.getResponsibilities());

        TextField editedSalary = new TextField("Edited Salary");
        editedSalary.setValue(String.valueOf(vacancy.getSalary()));

        Button saveButton = new Button("Save");
        saveButton.addClickListener(event -> {
            // Достаем изменения из полей
            String updatedCompany = editedCompany.getValue();
            String updatedRequirements = editedRequirements.getValue();
            String updatedResponsibilities = editedResponsibilities.getValue();
            int updatedSalary = Integer.parseInt(editedSalary.getValue());

            Vacancy updatedVacancy = new Vacancy();
            updatedVacancy.setId(vacancyId);
            updatedVacancy.setCompany(updatedCompany);
            updatedVacancy.setRequirements(updatedRequirements);
            updatedVacancy.setResponsibilities(updatedResponsibilities);
            updatedVacancy.setSalary(updatedSalary);

            // Обновляем инфу на беке через сервис
            vacancyService.updateVacancy(vacancyId, updatedVacancy);

            Vacancy updatedData = vacancyService.getVacancy(vacancyId);

            // Обновляем отображение на фронте
            updateCardData(updatedData.getCompany(), updatedData.getRequirements(), updatedData.getResponsibilities(), updatedData.getSalary());

            editDialog.close();
        });

        editDialog.add(editedCompany, editedRequirements, editedResponsibilities, editedSalary, saveButton);
        editDialog.open();
    }


    // Метод для обновления данных в карточке
    public void updateCardData(String company, String requirements, String responsibilities, int salary) {
        header.setText(company);
        subtitle.setText(requirements);
        description.setText(responsibilities);
        badge.setText(String.valueOf(salary) + " $");
    }
}