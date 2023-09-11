package com.andrey.lucky_job.views.signup;

import com.andrey.lucky_job.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

@PageTitle("Sign up")
@Route(value = "registration", layout = MainLayout.class)
public class SignupView extends Composite<VerticalLayout> {

    public SignupView() {
        VerticalLayout layout = createLayout();
        getContent().add(layout);
    }

    private VerticalLayout createLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();
        layout.addClassName(Padding.LARGE);

        H3 heading = new H3("Personal Information");

        HorizontalLayout row1 = createRow();
        HorizontalLayout row2 = createRow();

        TextField firstNameField = createTextField("First Name");
        DatePicker birthdayDatePicker = createDatePicker("Birthday");
        EmailField emailField = createEmailField("Email");
        TextField lastNameField = createTextField("Last Name");
        TextField phoneNumberField = createTextField("Phone Number");

        RadioButtonGroup<String> occupationRadioGroup = new RadioButtonGroup<>();
        occupationRadioGroup.setLabel("Choose your role");
        occupationRadioGroup.setItems("Searcher", "Employer");

        Button saveButton = createButton("Save", ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = createButton("Cancel");

        layout.add(heading, row1, row2);

        row1.add(createColumn(firstNameField, birthdayDatePicker, emailField));
        row1.add(createColumn(lastNameField, phoneNumberField, occupationRadioGroup));

        row2.add(saveButton, cancelButton);

        return layout;
    }

    private HorizontalLayout createRow() {
        HorizontalLayout row = new HorizontalLayout();
        row.setWidthFull();
        row.addClassName(Gap.LARGE);
        return row;
    }

    private VerticalLayout createColumn(Component... components) {
        VerticalLayout column = new VerticalLayout(components);
        column.setWidthFull();
        return column;
    }

    private TextField createTextField(String label) {
        TextField textField = new TextField(label);
        textField.setWidthFull();
        return textField;
    }

    private DatePicker createDatePicker(String label) {
        DatePicker datePicker = new DatePicker(label);
        datePicker.setWidthFull();
        return datePicker;
    }

    private EmailField createEmailField(String label) {
        EmailField emailField = new EmailField(label);
        emailField.setWidthFull();
        return emailField;
    }

    private Button createButton(String text, ButtonVariant... variants) {
        Button button = new Button(text);
        button.addThemeVariants(variants);
        return button;
    }
}
