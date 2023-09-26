package com.andrey.lucky_job.views.searcher;

import com.andrey.lucky_job.models.CV;
import com.andrey.lucky_job.models.Vacancy;
import com.andrey.lucky_job.service.CVService;
import com.andrey.lucky_job.service.VacancyService;
import com.andrey.lucky_job.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.vaadin.flow.router.BeforeEvent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
    private SearcherViewCard vacancyCard;
    private Div cardContainer;
    private Upload upload;
    private MemoryBuffer buffer;

    // Шаблон страницы
    public CVChatView(@Autowired CVService cvService, @Autowired VacancyService vacancyService) {
        this.cvService = cvService;
        this.vacancyService = vacancyService;

        setClassName("cv-chat-view");
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        contentLayout = new VerticalLayout();

        messageListLayout = new VerticalLayout();
        messageListLayout.getElement().getStyle().set("max-height", "calc(100vh - 200px)");
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

    // Заполнение страницы и ссылки
    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long vacancyId) {
        if (vacancyId != null) {
            currentVacancy = vacancyService.getVacancy(vacancyId);
            vacancyCard = createCard(currentVacancy);
            cardContainer = new Div(vacancyCard);
            displayCard(currentVacancy);
            displayCVsForVacancy(currentVacancy.getId());
        }
    }

    // Конструктор карточки
    private SearcherViewCard createCard(Vacancy vacancy) {
        return new SearcherViewCard(
                vacancy.getCompany(),
                vacancy.getRequirements(),
                vacancy.getResponsibilities(),
                vacancy.getSalary(),
                vacancy.getId(),
                vacancyService,
                false,
                cvService
        );
    }

    // Расположение и отображение карточки
    private void displayCard(Vacancy vacancy) {
        SearcherViewCard card = createCard(vacancy);

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

    // Форма для отправки CV
    private FormLayout createCVForm() {
        FormLayout form = new FormLayout();
        form.setWidth("300px");

        buffer = new MemoryBuffer();
        upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png");
        upload.setMaxFileSize(5 * 1024 * 1024); // размер файла до 5 МБ
        upload.setAutoUpload(true);

        form.getElement().getStyle().set("position", "fixed");
        form.getElement().getStyle().set("top", "70%");
        form.getElement().getStyle().set("right", "-5%");
        form.getElement().getStyle().set("transform", "translate(-50%, -50%)");

        authorField = new TextField("Author");
        titleField = new TextField("Title");
        Button submitButton = new Button("Send");
        Button closeButton = new Button("Close");

        form.add(authorField, titleField, upload, submitButton, closeButton);

        submitButton.addClickListener(event -> {
            String author = authorField.getValue();
            String title = titleField.getValue();

            if (!author.isEmpty() && !title.isEmpty() && buffer.getInputStream() != null)  {
                LocalDateTime currentDate = LocalDateTime.now();
                Date dateOfPublication = new Date();

                byte[] imageData = new byte[0];
                try {
                    imageData = buffer.getInputStream().readAllBytes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                CV cv = new CV(author, title, imageData, dateOfPublication, currentVacancy.getId());

                Paragraph cvMessage = createMessageParagraph(cv);

                messageListLayout.add(cvMessage);

                cvService.saveCV(cv);

                authorField.clear();
                titleField.clear();

                cvForm.setVisible(false); // Скрыть форму после успешной отправки
                postButton.setVisible(true); // Показать кнопку "Post" снова
            } else {
                Notification.show("Fields cannot be empty");
            }
        });

        // Обработчик события для кнопки закрытия
        closeButton.addClickListener(event -> {
            cvForm.setVisible(false); // Скрыть форму
            postButton.setVisible(true); // Показать кнопку "Post" снова
        });

        return form;
    }

    // Сообщение
    private Paragraph createMessageParagraph(CV cv) {
        Paragraph cvMessage = new Paragraph();
        Div content = new Div();

        content.getElement().setProperty("innerHTML", "Author: " + cv.getAuthor() + "<br>" +
                "Title: " + cv.getTitle() + "<br>" +
                "Date of publication: " + cv.getDateOfPublication());

        cvMessage.getStyle().set("border", "1px solid black")
                .set("padding", "10px")
                .set("margin", "10px");

        cvMessage.getElement().getStyle().set("background-color", "#333366");
        cvMessage.getElement().getStyle().set("color", "#ffffff");

        cvMessage.getElement().getStyle().set("border-radius", "5px");
        cvMessage.getElement().getStyle().set("padding", "10px");
        cvMessage.getElement().getStyle().set("margin-bottom", "10px");
        cvMessage.getElement().getStyle().set("margin-top", "10px");
        cvMessage.getElement().getStyle().set("width", "fit-content");
        cvMessage.getElement().getStyle().set("max-width", "100%");
        cvMessage.getElement().getStyle().set("word-wrap", "break-word");

        if (cv.getImageData() != null) {
            StreamResource resource = new StreamResource("image.png", () -> new ByteArrayInputStream(cv.getImageData()));
            Image image = new Image(resource, "Uploaded image");
            image.setWidth("100px");

            image.addClickListener(event -> createImageDialog(image));
            cvMessage.add(content, image);
        } else {
            cvMessage.add(content);
        }

        return cvMessage;
    }

    // Достаем и отображаем все имеющиеся CV
    private void displayCVsForVacancy(Long vacancyId) {
        List<CV> cvList = cvService.getCVsForVacancy(vacancyId);

        messageListLayout.removeAll();

        for (CV cv : cvList) {
            Paragraph cvMessage = createMessageParagraph(cv);
            messageListLayout.add(cvMessage);
        }
    }

    // Развернутое изображение
    private void createImageDialog(Image image) {
        Dialog imageDialog = new Dialog();
        Image enlargedImage = new Image(image.getSrc(), String.valueOf(image.getAlt()));

        enlargedImage.setWidth("auto");
        enlargedImage.setHeight("auto");
        enlargedImage.getElement().getStyle().set("max-width", "100%");
        enlargedImage.getElement().getStyle().set("max-height", "100%");
        enlargedImage.getElement().getStyle().set("object-fit", "contain");

        VerticalLayout verticalLayout = new VerticalLayout(enlargedImage);
        verticalLayout.setSizeFull();
        verticalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        verticalLayout.setAlignItems(Alignment.CENTER);
        verticalLayout.getElement().getStyle().set("padding", "0");
        verticalLayout.getElement().getStyle().set("margin", "0");

        imageDialog.add(verticalLayout);
        imageDialog.setWidth("45%");
        imageDialog.setHeight("120%");
        imageDialog.setCloseOnEsc(true);
        imageDialog.setCloseOnOutsideClick(true);
        imageDialog.open();
    }

    class FooterLayout extends VerticalLayout {

        public FooterLayout() {
            setJustifyContentMode(JustifyContentMode.CENTER);
            setWidthFull();
            setAlignItems(Alignment.CENTER);
            getStyle().set("position", "fixed");
            getStyle().set("bottom", "0");
            getStyle().set("left", "0");

            add(cvForm, postButton);
        }
    }

}