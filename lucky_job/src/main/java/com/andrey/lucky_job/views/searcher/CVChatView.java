package com.andrey.lucky_job.views.searcher;

import com.andrey.lucky_job.models.CV;
import com.andrey.lucky_job.models.Vacancy;
import com.andrey.lucky_job.service.CVService;
import com.andrey.lucky_job.service.VacancyService;
import com.andrey.lucky_job.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.vaadin.flow.router.BeforeEvent;

import java.time.LocalDateTime;
import java.util.Date;

@Route(value = "cv-chat", layout = MainLayout.class)
@Component
@VaadinSessionScope
@Scope("prototype")
public class CVChatView extends VerticalLayout implements HasUrlParameter<Long> {

    private final CVService cvService;
    private final VacancyService vacancyService;
    private Vacancy currentVacancy;
    private final VerticalLayout contentLayout;
    private final VerticalLayout messageListLayout;
    private final FormLayout cvForm;
    private FooterLayout bottomLayout;
    private final Button postButton;
    private TextField authorField;
    private TextField titleField;
    private TextField textField;

    private SearcherViewCard vacancyCard;
    private Div cardContainer;


    public CVChatView(@Autowired CVService cvService, @Autowired VacancyService vacancyService) {
        this.cvService = cvService;
        this.vacancyService = vacancyService;

        setClassName("cv-chat-view");
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        contentLayout = new VerticalLayout();

        messageListLayout = new VerticalLayout();
        messageListLayout.getElement().getStyle().set("max-height", "calc(100vh - 400px)");
        messageListLayout.getElement().getStyle().set("overflow-y", "auto");
        messageListLayout.setWidthFull();

        cvForm = createCVForm();
        postButton = new Button("Post");

        cvForm.setVisible(false);

        postButton.addClickListener(event -> {
            postButton.setVisible(false);
            cvForm.setVisible(true);
        });

        bottomLayout = new FooterLayout();
        bottomLayout.add(postButton);

        contentLayout.setFlexGrow(1, messageListLayout);
        contentLayout.setFlexGrow(1, bottomLayout);

        contentLayout.add(messageListLayout, bottomLayout);

        add(contentLayout);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long vacancyId) {
        if (vacancyId != null) {
            currentVacancy = vacancyService.getVacancy(vacancyId);
            vacancyCard = createCard(currentVacancy);
            cardContainer = new Div(vacancyCard);
            displayCard(currentVacancy);
        }
    }

    private SearcherViewCard createCard(Vacancy vacancy) {
        return new SearcherViewCard(
                vacancy.getCompany(),
                vacancy.getRequirements(),
                vacancy.getResponsibilities(),
                vacancy.getSalary(),
                vacancy.getId(),
                vacancyService
        );
    }

    private void displayCard(Vacancy vacancy) {
        SearcherViewCard card = createCard(vacancy);

//        setWidth("20%");
//        setHeight("60%");
//        getElement().getStyle().set("width", "20%");
        getElement().getStyle().set("height", "100%");
        card.setEnabled(false);

        cardContainer.setMaxWidth("22%");
        messageListLayout.setMaxWidth("75%");

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setWidthFull();
        mainLayout.add(cardContainer, messageListLayout);
        mainLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        contentLayout.removeAll();
        contentLayout.add(mainLayout, bottomLayout);
    }

    private FormLayout createCVForm() {
        FormLayout form = new FormLayout();
        form.setWidth("300px");

        form.getElement().getStyle().set("position", "absolute");
        form.getElement().getStyle().set("left", "50%");
        form.getElement().getStyle().set("transform", "translateX(-50%)");

        authorField = new TextField("Author");
        titleField = new TextField("Title");
        textField = new TextField("CV");
        Button submitButton = new Button("Send");

        form.add(authorField, titleField, textField, submitButton);

        submitButton.addClickListener(event -> {
            String author = authorField.getValue();
            String title = titleField.getValue();
            String text = textField.getValue();

            if (!author.isEmpty() && !title.isEmpty() && !text.isEmpty()) {
                LocalDateTime currentDate = LocalDateTime.now();
                Date dateOfPublication = new Date();

                CV cv = new CV(author, title, text, dateOfPublication);

                Paragraph cvMessage = new Paragraph("Author: " + cv.getAuthor() +
                        ", Title: " + cv.getTitle() +
                        ", CV: " + cv.getText() +
                        ", Date of publication: " + cv.getDateOfPublication());

                messageListLayout.add(cvMessage);

                cvService.saveCV(cv);

                authorField.clear();
                titleField.clear();
                textField.clear();

                cvForm.setVisible(false); // Скрыть форму после успешной отправки
                postButton.setVisible(true); // Показать кнопку "Post" снова
            } else {
                Notification.show("Fields cannot be empty");
            }
        });

        return form;
    }

    class FooterLayout extends VerticalLayout {

        public FooterLayout() {
            setJustifyContentMode(JustifyContentMode.CENTER);
            setWidthFull();
            setAlignItems(Alignment.CENTER);
            getStyle().set("position", "fixed");
            getStyle().set("bottom", "0");
            getStyle().set("left", "0");
            getStyle().set("right", "0");

            // Добавьте эту строку, чтобы добавить отступ снизу
            getStyle().set("margin-bottom", "170px");

            add(cvForm);
        }
    }
}