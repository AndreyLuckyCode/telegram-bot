package com.andrey.lucky_job.views.employer;

//import com.andrey.lucky_job.data.entity.SamplePerson;
//import com.andrey.lucky_job.data.service.SamplePersonService;
import com.andrey.lucky_job.views.MainLayout;
import com.andrey.lucky_job.views.addnewjob.AddNewJobView;
import com.andrey.lucky_job.views.signin.SigninView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Employer")
@Route(value = "employer", layout = MainLayout.class)
@Uses(Icon.class)
public class EmployerView extends Composite<VerticalLayout> {

    public EmployerView() {
        Button buttonPrimary = new Button();
        H3 h3 = new H3();
//        Grid basicGrid = new Grid(SamplePerson.class);
//        getContent().setHeightFull();
//        getContent().setWidthFull();
        buttonPrimary.setText("One more vacancy?");

        buttonPrimary.addClickListener(event -> {
            // Переход на страницу "Add new Job"
            UI.getCurrent().navigate(AddNewJobView.class);
        });

        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        h3.setText("Candidates");
        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, h3);
//        setGridSampleData(basicGrid);
        getContent().add(buttonPrimary);
        getContent().add(h3);
//        getContent().add(basicGrid);
    }

//    private void setGridSampleData(Grid grid) {
//        grid.setItems(query -> samplePersonService.list(
//                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
//                .stream());
//    }
//
//    @Autowired()
//    private SamplePersonService samplePersonService;
}
