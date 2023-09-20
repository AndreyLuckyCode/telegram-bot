package com.andrey.lucky_job.views.searcher;

import com.andrey.lucky_job.models.CV;
import com.andrey.lucky_job.service.CVService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.time.LocalDateTime;
import java.util.Date;

@Route("cv-chat")
public class CVChatView extends VerticalLayout {

    private final CVService cvService;
    private final VerticalLayout contentLayout;
    private final VerticalLayout messageListLayout;
    private final FormLayout cvForm;
    private final FooterLayout bottomLayout;
    private final Button postButton;
    private TextField authorField;
    private TextField titleField;
    private TextField textField;


    public CVChatView(CVService cvService) {
        this.cvService = cvService;

        setClassName("cv-chat-view");
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        contentLayout = new VerticalLayout();

        // Добавляем стили для высоты и прокрутки к messageListLayout
        messageListLayout = new VerticalLayout();
        messageListLayout.getElement().getStyle().set("max-height", "calc(100vh - 400px)");
        messageListLayout.getElement().getStyle().set("overflow-y", "auto");

        cvForm = createCVForm();
        postButton = new Button("Post");

        cvForm.setVisible(false); // Изначально форма скрыта

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

    private FormLayout createCVForm() {
        FormLayout form = new FormLayout();
        form.setWidth("300px");

        // Добавьте эти строки для выравнивания формы по центру
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