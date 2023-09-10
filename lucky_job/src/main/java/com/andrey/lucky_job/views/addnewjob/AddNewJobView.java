package com.andrey.lucky_job.views.addnewjob;

import com.andrey.lucky_job.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Add New Job")
@Route(value = "post-new-job", layout = MainLayout.class)
@Uses(Icon.class)
public class AddNewJobView extends Composite<VerticalLayout> {

    public AddNewJobView() {
        Button buttonPrimary = new Button();
        TextArea textArea = new TextArea();
        TextArea textArea2 = new TextArea();
        TextArea textArea3 = new TextArea();
        TextArea textArea4 = new TextArea();
        getContent().setHeightFull();
        getContent().setWidthFull();
        buttonPrimary.setText("Add");
        getContent().setAlignSelf(FlexComponent.Alignment.START, buttonPrimary);
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        textArea.setLabel("Company");
        textArea.setWidthFull();
        getContent().setAlignSelf(FlexComponent.Alignment.START, textArea);
        textArea2.setLabel("Requirements");
        textArea2.setWidthFull();
        textArea3.setLabel("Responsibilities");
        textArea3.setWidthFull();
        textArea4.setLabel("Salary");
        textArea4.setWidthFull();
        getContent().add(buttonPrimary);
        getContent().add(textArea);
        getContent().add(textArea2);
        getContent().add(textArea3);
        getContent().add(textArea4);
    }
}
