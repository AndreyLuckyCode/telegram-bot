package com.andrey.lucky_job.views.searcher;

import com.andrey.lucky_job.service.VacancyService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.theme.lumo.LumoUtility.*;

public class SearcherViewCard extends ListItem {

    public SearcherViewCard(String company, String requirements, String responsibilities, int salary, Long vacancyId, VacancyService vacancyService) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("400px");

        Image image = new Image("https://img.reg.ru/news/823dd699786133.Y3JvcCwxMTUwLDkwMCwyMjUsMA.gif", "Company Logo");
        image.setWidth("100%");

        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(company);

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText(requirements);

        Paragraph description = new Paragraph(responsibilities);
        description.addClassName(Margin.Vertical.MEDIUM);

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        badge.setText(String.valueOf(salary));

        // Создайте кнопку "Delete"
        Button deleteButton = new Button("Delete");
        deleteButton.addClickListener(event -> {
            // Обработка нажатия на кнопку "Delete"
            vacancyService.deleteVacancy(vacancyId);
            // Обновите интерфейс, чтобы карточка исчезла
            this.setVisible(false);
        });

        add(div, header, subtitle, description, badge, deleteButton);
    }
}
