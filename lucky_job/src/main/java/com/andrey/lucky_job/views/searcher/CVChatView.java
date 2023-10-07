package com.andrey.lucky_job.views.searcher;

import com.andrey.lucky_job.models.CV;
import com.andrey.lucky_job.models.Employer;
import com.andrey.lucky_job.models.Searcher;
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
import com.vaadin.flow.server.VaadinSession;
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
import java.util.stream.Collectors;

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
    private TextField emailField;
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

            // Получить текущего пользователя
            Object user = VaadinSession.getCurrent().getAttribute("user");

            // Если текущий пользователь существует и он является Searcher
            // тогда устанавливаем его email в поле emailField
            if (user instanceof Searcher) {
                Searcher currentUser = (Searcher) user;
                emailField.setValue(currentUser.getEmail());
            } else {
                Notification.show("Only Searcher can post CVs");
                cvForm.setVisible(false);
                postButton.setVisible(true);
                return;
            }

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
                cvService,
                vacancy);
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

        emailField = new TextField("Email");
        titleField = new TextField("Title");
        Button submitButton = new Button("Send");
        Button closeButton = new Button("Close");

        form.add(emailField, titleField, upload, submitButton, closeButton);

        submitButton.addClickListener(event -> {
            String author = emailField.getValue();
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
                Boolean liked = false;
                CV cv = new CV(author, title, imageData, dateOfPublication, currentVacancy.getId(), liked);

                Paragraph cvMessage = createMessageParagraph(cv, messageListLayout);

                messageListLayout.add(cvMessage);

                cvService.saveCV(cv);

                emailField.clear();
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
    private Paragraph createMessageParagraph(CV cv, VerticalLayout messageListLayout) {
        Paragraph cvMessage = new Paragraph();
        Div content = new Div();

        content.getElement().setProperty("innerHTML", cv.getAuthor() + "<br>" +
                cv.getTitle() + "<br>" +
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

// *******************************************  LIKE BUTTON ******************************************************
// *******************************************  LIKE BUTTON ******************************************************

        Button likeButton = new Button("Like");
        Boolean isLiked = cv.isLiked();

        // Получение текущего пользователя
        Object user = VaadinSession.getCurrent().getAttribute("user");
        Boolean userIsEmployer = user instanceof Employer;

        if (isLiked != null && isLiked) {
            likeButton.addClassName("like-button-clicked");
            likeButton.getElement().getStyle().set("backgroundColor", "red");
        }

        // Кнопка будет активным только для Employer (мб имеет смысл убрать, чтобы срабатывал нотификейшн ниже)
//        likeButton.setEnabled(userIsEmployer);

        likeButton.addClickListener(event -> {
            // Если текущий пользователь не является Employer, то прерываемся
            if (!userIsEmployer) {
                Notification.show("Only Employer can like CVs");
                return;
            }

            Object currentUser = VaadinSession.getCurrent().getAttribute("user");
            if(!((Employer) currentUser).getId().equals(currentVacancy.getEmployerId())){
                Notification.show("Only author of the vacancy can react");
                return;
            }


            Boolean currentIsLiked = cv.isLiked();
            if (currentIsLiked != null && currentIsLiked) {
                cv.setLiked(false);
                likeButton.removeClassName("like-button-clicked");
                likeButton.getElement().getStyle().remove("backgroundColor");
            } else {
                cv.setLiked(true);
                likeButton.addClassName("like-button-clicked");
                likeButton.getElement().getStyle().set("backgroundColor", "red");
            }
            cvService.saveCV(cv);
            loadAndDisplayCVs();
        });

        cvMessage.add(likeButton);

// *******************************************  DISLIKE BUTTON ******************************************************
// *******************************************  DISLIKE BUTTON ******************************************************

        Button dislikeButton = new Button("Dislike");

        Object currentUser = VaadinSession.getCurrent().getAttribute("user");
        if(currentUser != null && currentUser instanceof Employer && ((Employer) currentUser).getId().equals(currentVacancy.getEmployerId())){
            dislikeButton.setVisible(true);
        } else {
            dislikeButton.setVisible(false);
        }

        dislikeButton.addClickListener(event -> {

            Dialog confirmationDialog = new Dialog();
            Button confirmButton = new Button("Confirm", e -> {
                if(((Employer) currentUser).getId().equals(currentVacancy.getEmployerId())){
                    Notification.show("Will be deleted");
                    cvService.deleteCV(cv.getId());
                    messageListLayout.remove(cvMessage);
                    confirmationDialog.close();
                }
            });
            Button cancelButton = new Button("Cancel", e -> confirmationDialog.close());
            confirmationDialog.add(new Paragraph("Are you sure you want to delete this CV?"), confirmButton, cancelButton);

            confirmationDialog.open();
        });

        cvMessage.add(dislikeButton);

        return cvMessage;
    }

    // Достаем и отображаем все имеющиеся CV
    private void displayCVsForVacancy(Long vacancyId) {
        List<CV> cvList = cvService.getCVsForVacancy(vacancyId);

        messageListLayout.removeAll();

        for (CV cv : cvList) {
            Paragraph cvMessage = createMessageParagraph(cv, messageListLayout);
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
    private void loadAndDisplayCVs() {
        List<CV> cvList = cvService.getCVsForVacancy(currentVacancy.getId());
        cvList = cvList.stream()
                .sorted((cv1, cv2) -> Boolean.compare(cv2.isLiked(), cv1.isLiked()))  // сортировка по лайкам
                .collect(Collectors.toList());
        displayCVs(cvList);
    }

    // Новый метод для отображения списка резюме
    private void displayCVs(List<CV> cvList) {
        messageListLayout.removeAll();
        for (CV cv : cvList) {
            Paragraph cvMessage = createMessageParagraph(cv, messageListLayout);
            messageListLayout.add(cvMessage);
        }
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