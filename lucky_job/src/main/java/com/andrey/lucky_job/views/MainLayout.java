package com.andrey.lucky_job.views;

import com.andrey.lucky_job.models.Employer;
import com.andrey.lucky_job.models.Searcher;
import com.andrey.lucky_job.views.about.AboutView;
import com.andrey.lucky_job.views.addnewjob.AddNewJobView;
import com.andrey.lucky_job.views.employer.EmployerView;
import com.andrey.lucky_job.views.mainpage.MainPageView;
import com.andrey.lucky_job.views.searcher.SearcherView;
import com.andrey.lucky_job.views.signin.SigninView;
import com.andrey.lucky_job.views.signup.SignupView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;
import com.vaadin.flow.component.icon.Icon;

@PreserveOnRefresh
public class MainLayout extends AppLayout {

    private H2 viewTitle;
    private Icon profileIcon;
    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        // Добавляем иконку профиля.
        profileIcon = VaadinIcon.USER.create();
        profileIcon.addClickListener(click -> {
            // Проверяем, залогинен ли пользователь.
            Object user = VaadinSession.getCurrent().getAttribute("user");
            if (user != null) {
                if (user instanceof Employer) {
                    UI.getCurrent().navigate("employer-profile");
                } else if (user instanceof Searcher) {
                    UI.getCurrent().navigate("searcher-profile");
                }
            } else {
                UI.getCurrent().navigate("login");
            }
        });
        profileIcon.getStyle().set("margin-left", "auto");

        addToNavbar(true, toggle, viewTitle, profileIcon);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Lucky Job");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Main Page", MainPageView.class, LineAwesomeIcon.JAVA.create()));
//        nav.addItem(new SideNavItem("Sing up", SignupView.class, LineAwesomeIcon.USER.create()));
//        nav.addItem(new SideNavItem("Sign in", SigninView.class, LineAwesomeIcon.CHECK_CIRCLE_SOLID.create()));
        nav.addItem(new SideNavItem("Employer", EmployerView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
        nav.addItem(new SideNavItem("Add New Job", AddNewJobView.class, LineAwesomeIcon.GG.create()));
        nav.addItem(new SideNavItem("Vacancies", SearcherView.class, LineAwesomeIcon.TH_LIST_SOLID.create()));
        nav.addItem(new SideNavItem("About", AboutView.class, LineAwesomeIcon.BOOK_SOLID.create()));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
