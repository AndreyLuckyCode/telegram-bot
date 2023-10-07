package com.andrey.lucky_job.views.mainpage;

import com.andrey.lucky_job.views.MainLayout;
import com.andrey.lucky_job.views.employer.EmployerView;
import com.andrey.lucky_job.views.searcher.SearcherView;
import com.andrey.lucky_job.views.signin.SigninView;
import com.andrey.lucky_job.views.signup.SignupView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

@PageTitle("Main Page")
@Route(value = "main", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class MainPageView extends Composite<VerticalLayout> {

    public MainPageView() {
        VerticalLayout content = getContent();
        content.setSizeFull();

        HorizontalLayout layoutRow = createHorizontalLayout(Gap.MEDIUM, JustifyContentMode.END);
        Button buttonPrimary = createButton("Job");

        buttonPrimary.addClickListener(event -> {
            // Переход на страницу "Searcher"
            UI.getCurrent().navigate(SearcherView.class);
        });

        layoutRow.add(buttonPrimary);

        HorizontalLayout layoutRow2 = createHorizontalLayout(Gap.MEDIUM, JustifyContentMode.START);
        layoutRow.add(layoutRow2);

        HorizontalLayout layoutRow3 = createHorizontalLayout(Gap.XSMALL, JustifyContentMode.START);
        Button buttonPrimary2 = createButton("Employer");

        buttonPrimary2.addClickListener(event -> {
            // Переход на страницу "Employer"
            UI.getCurrent().navigate(EmployerView.class);
        });

        Button buttonPrimary3 = createButton("Searcher");

        buttonPrimary3.addClickListener(event -> {
            // Переход на страницу "Searcher"
            UI.getCurrent().navigate(SearcherView.class);
        });

        layoutRow2.add(layoutRow3, buttonPrimary2, buttonPrimary3);

        VerticalLayout layoutColumn2 = createVerticalLayout();
        layoutRow2.add(layoutColumn2);

        HorizontalLayout layoutRow4 = createHorizontalLayout(Gap.MEDIUM, JustifyContentMode.END);
        Button buttonPrimary4 = createButton("Sign Up");

        buttonPrimary4.addClickListener(event -> {
            Object currentUser = VaadinSession.getCurrent().getAttribute("user");
            if(currentUser == null){
                // Переход на страницу "Sign Up"
                UI.getCurrent().navigate(SignupView.class);
            } else {
                Notification.show("If you want create new account please logout first");
            }
        });

        Button buttonPrimary5 = createButton("Sign In");

        buttonPrimary5.addClickListener(event -> {
            Object currentUser = VaadinSession.getCurrent().getAttribute("user");
            if(currentUser == null){
                // Переход на страницу "Sign In"
                UI.getCurrent().navigate(SigninView.class);
            } else {
                Notification.show("If you want change your account please logout first");
            }

        });

        layoutRow4.add(buttonPrimary4, buttonPrimary5);
        layoutRow2.add(layoutRow4);

        VerticalLayout layoutColumn3 = createVerticalLayout();
        content.add(layoutRow, layoutColumn3);

        // Set flex growth
        content.setFlexGrow(1.0, layoutColumn3);
        layoutRow2.setFlexGrow(1.0, layoutColumn2);
        layoutRow2.setFlexGrow(1.0, layoutRow3);
        layoutRow2.setFlexGrow(1.0, buttonPrimary2);
        layoutRow2.setFlexGrow(1.0, buttonPrimary3);
        layoutRow2.setFlexGrow(1.0, layoutRow4);
    }

    private HorizontalLayout createHorizontalLayout(String className, JustifyContentMode justifyContent) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addClassName(className);
        layout.setWidthFull();
        layout.setAlignItems(Alignment.START);
        layout.setJustifyContentMode(justifyContent);
        return layout;
    }

    private VerticalLayout createVerticalLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth(null);
        return layout;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }
}