package com.andrey.lucky_job.views.employer;

import com.andrey.lucky_job.models.ApprovalStatus;
import com.andrey.lucky_job.models.CV;
import com.andrey.lucky_job.models.Employer;
import com.andrey.lucky_job.models.Searcher;
import com.andrey.lucky_job.service.ApprovalStatusService;
import com.andrey.lucky_job.service.CVService;
import com.andrey.lucky_job.service.SearcherService;
import com.andrey.lucky_job.views.MainLayout;
import com.andrey.lucky_job.views.addnewjob.AddNewJobView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Employer")
@Route(value = "employer", layout = MainLayout.class)
@Uses(Icon.class)
public class EmployerView extends Composite<VerticalLayout> implements BeforeEnterObserver {
    private final CVService cvService;
    private final SearcherService searcherService;
    private final ApprovalStatusService approvalStatusService;

    @Autowired
    public EmployerView(SearcherService searcherService, CVService cvService, ApprovalStatusService approvalStatusService) {
        this.searcherService = searcherService;
        this.cvService = cvService;
        this.approvalStatusService = approvalStatusService;

        Button buttonPrimary = new Button();
        H3 h3 = new H3();
        Grid<Searcher> basicGrid = new Grid<>(Searcher.class);
        basicGrid.setColumns("name", "surname", "dateOfBirth", "phoneNumber", "email");

        basicGrid.addComponentColumn(searcher -> {
            Checkbox checkbox = new Checkbox();
            final ApprovalStatus[] approvalStatus = {approvalStatusService.findBySearcherIdAndEmployerId(searcher.getId(), ((Employer) VaadinSession.getCurrent().getAttribute("user")).getId())};
            checkbox.setValue(approvalStatus[0] != null && approvalStatus[0].isApproved());

            checkbox.addClickListener(event -> {
                if (approvalStatus[0] == null) {
                    approvalStatus[0] = new ApprovalStatus(searcher.getId(), ((Employer)VaadinSession.getCurrent().getAttribute("user")).getId(), checkbox.getValue());
                } else {
                    approvalStatus[0].setApproved(checkbox.getValue());
                }
                approvalStatusService.save(approvalStatus[0]);
                setGridSearcherData(basicGrid);
            });

            return checkbox;
        }).setHeader("Approved");

        basicGrid.addComponentColumn(searcher -> {
            Button cvLinkButton = new Button("CV", event -> {
                List<CV> cvs = cvService.findLikedCVsByAuthor(searcher.getEmail());
                CV cvForSearcher = cvs.stream().filter(cv -> searcher.getEmail().equals(cv.getAuthor())).findFirst().orElse(null);

                if (cvForSearcher != null) {
                    StreamResource streamResource = new StreamResource(cvForSearcher.getTitle() + ".png",
                            () -> new ByteArrayInputStream(cvForSearcher.getImageData()));
                    Image cvImage = new Image(streamResource, "");

                    cvImage.setWidth("100%");
                    cvImage.setHeight("100%");
                    Dialog dialog = new Dialog(cvImage);
                    dialog.setWidth("35%");
                    dialog.setHeight("120%");
                    dialog.open();
                } else {
                    Notification.show("CV not found");
                }
            });

            return cvLinkButton;
        }).setHeader("CV");

        getContent().setHeightFull();
        getContent().setWidthFull();
        buttonPrimary.setText("One more vacancy?");
        buttonPrimary.addClickListener(event -> {
            UI.getCurrent().navigate(AddNewJobView.class);
        });

        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        h3.setText("Candidates");
        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, h3);

        setGridSearcherData(basicGrid);
        getContent().add(buttonPrimary);
        getContent().add(h3);
        getContent().add(basicGrid);
    }

    private void setGridSearcherData(Grid<Searcher> grid) {
        // Текущий пользователь из сессии
        Object user = VaadinSession.getCurrent().getAttribute("user");

        if (user instanceof Employer) {
            Employer currentUser = (Employer) user;

            // Получаем список всех резюме, связанных с этим работодателем
            List<CV> cvs = cvService.findAllCVsByEmployerId(currentUser.getId());

            // Список для хранения кандидатов, которые подали резюме
            List<Searcher> cvSearchers = new ArrayList<>();

            for (CV cv : cvs) {
                // Если резюме было лайкнуто работодателем
                if(cv.isLiked()) {
                    // Берем email автора (по сути это и есть email Searcher)
                    String authorEmail = cv.getAuthor();
                    // Ищем Searcher по данному email
                    Searcher s = searcherService.findSearcherByEmail(authorEmail);

                    // Если этот кандидат еще не добавлен в список, добавляем его
                    if (!cvSearchers.contains(s)) {
                        cvSearchers.add(s);
                    }
                }
            }

            // Сортируем список
            cvSearchers.sort((searcher1, searcher2) -> {
                ApprovalStatus status1 = approvalStatusService.findBySearcherIdAndEmployerId(searcher1.getId(), currentUser.getId());
                ApprovalStatus status2 = approvalStatusService.findBySearcherIdAndEmployerId(searcher2.getId(), currentUser.getId());

                boolean approved1 = (status1 != null) ? status1.isApproved() : false;
                boolean approved2 = (status2 != null) ? status2.isApproved() : false;

                return Boolean.compare(approved2, approved1); // Для отображения одобренных сначала
            });

            // Показываем только кандидатов, которые подали резюме
            grid.setItems(cvSearchers);
        } else {
            // Если пользователь не является Employer, показываем пустую таблицу
            grid.setItems(new ArrayList<>());
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