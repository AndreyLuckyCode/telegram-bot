package com.andrey.lucky_job.views.searcher;

import com.andrey.lucky_job.models.CV;
import com.andrey.lucky_job.service.CVService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.Date;

@Route("cv-chat")
public class CVChatView extends VerticalLayout {

    private final CVService cvService;
    private final VerticalLayout cvListLayout;
    private final TextField authorField;
    private final TextField titleField;
    private final TextArea textField;

    public CVChatView(CVService cvService) {
        this.cvService = cvService;

        cvListLayout = new VerticalLayout();
        cvListLayout.getStyle().set("align-items", "flex-start"); // Выравнивание сверху
        cvListLayout.getStyle().set("margin", "auto"); // Центрирование
        cvListLayout.setMaxWidth("600px");
        add(cvListLayout);


        // Создаем компоненты для ввода CV-сообщений
        FormLayout cvForm = new FormLayout();
        authorField = new TextField("Author");
        titleField = new TextField("Title");
        textField = new TextArea("CV");
        Button submitButton = new Button("send");
        cvForm.add(authorField, titleField, textField, submitButton);

        // Добавляем слушателя события на кнопку "Отправить"
        submitButton.addClickListener(event -> {
            String author = authorField.getValue();
            String title = titleField.getValue();
            String text = textField.getValue();

            if (!author.isEmpty() && !title.isEmpty() && !text.isEmpty()) {
                Date currentDate = new Date();

                CV cv = new CV(author, title, text, currentDate);

                Div cvMessage = new Div();
                cvMessage.addClassName("cv-message");

                Paragraph dateParagraph = new Paragraph("Date of publication : " + cv.getDateOfPublication());
                Paragraph authorParagraph = new Paragraph("Author: " + cv.getAuthor());
                Paragraph titleParagraph = new Paragraph("Title: " + cv.getTitle());
                Paragraph textParagraph = new Paragraph("CV: " + cv.getText());

                cvMessage.add(dateParagraph, authorParagraph, titleParagraph, textParagraph);
                cvListLayout.addComponentAsFirst(cvMessage);

                cvService.saveCV(cv);

                authorField.clear();
                titleField.clear();
                textField.clear();
            }
        });

        cvForm.setMaxWidth("600px");
        cvForm.getStyle().set("margin", "auto"); // Центрирование
        add(cvForm);
    }
}