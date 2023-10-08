package com.andrey.lucky_job.views.signup;

import com.andrey.lucky_job.models.Employer;
import com.andrey.lucky_job.models.Searcher;
import com.andrey.lucky_job.service.EmployerService;
import com.andrey.lucky_job.service.SearcherService;
import com.andrey.lucky_job.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@PageTitle("Sign up")
@Route(value = "registration", layout = MainLayout.class)
public class SignupView extends Composite<VerticalLayout> {

    private final EmployerService employerService;
    private final SearcherService searcherService;
    Pattern namePattern = Pattern.compile("^[a-zA-Z]+$");
    Pattern phonePattern = Pattern.compile("^\\d{11}$");
    Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z]).{7,}$");

    public SignupView(EmployerService employerService, SearcherService searcherService) {
        this.employerService = employerService;
        this.searcherService = searcherService;
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
        HorizontalLayout row3 = createRow();
        HorizontalLayout row4 = createRow();

        TextField firstNameField = createTextField("First Name");
        DatePicker birthdayDatePicker = createDatePicker("Birthday");
        EmailField emailField = createEmailField("Email");
        TextField lastNameField = createTextField("Last Name");
        TextField phoneNumberField = createTextField("Phone Number");
        PasswordField passwordField = createPasswordField("Password");

        RadioButtonGroup<String> occupationRadioGroup = new RadioButtonGroup<>();
        occupationRadioGroup.setLabel("Choose your role");
        occupationRadioGroup.setItems("Searcher", "Employer");

        Button saveButton = createButton("Save", ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(event -> {
            LocalDate dateOfBirth = birthdayDatePicker.getValue();
            String firstName = firstNameField.getValue();
            String lastName = lastNameField.getValue();
            String email = emailField.getValue();
            String phoneNumber = phoneNumberField.getValue();
            String role = occupationRadioGroup.getValue();
            String password = passwordField.getValue();

            if (firstName.isEmpty() || lastName.isEmpty() || dateOfBirth == null || email.isEmpty() || phoneNumber.isEmpty() || role == null) {
                Notification.show("Fields cannot be empty");
                return;
            }
            if (!namePattern.matcher(firstName).matches()) {
                Notification.show("First name can contain only English letters.");
                return;
            }
            if (!namePattern.matcher(lastName).matches()) {
                Notification.show("Last name can contain only English letters.");
                return;
            }
            if (Period.between(dateOfBirth, LocalDate.now()).getYears() < 18) {
                Notification.show("You must be at least 18 years old.");
                return;
            }
            if (!phonePattern.matcher(phoneNumber).matches()) {
                Notification.show("Phone number should contain exactly 11 digits.");
                return;
            }
            if (!passwordPattern.matcher(password).matches()) {
                Notification.show("Password must contain 7 or more characters with at least 1 uppercase letter and 1 digit.");
                return;
            }

            saveUser(firstName, lastName, dateOfBirth, email, phoneNumber, role, password, false);
        });

        Button cancelButton = createButton("Cancel");
        cancelButton.addClickListener(event -> {
            firstNameField.clear();
            lastNameField.clear();
            emailField.clear();
            phoneNumberField.clear();
            birthdayDatePicker.clear();
            occupationRadioGroup.clear();
        });

        layout.add(heading, row1, row2, row3, row4);

        row1.add(createColumn(firstNameField, birthdayDatePicker, emailField));
        row1.add(createColumn(lastNameField, phoneNumberField, passwordField));
        row2.add(createColumn(occupationRadioGroup));
        row4.add(saveButton, cancelButton);

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

    private PasswordField createPasswordField(String label){
        PasswordField passwordField = new PasswordField(label);
        passwordField.setWidthFull();
        return passwordField;
    }

    private Button createButton(String text, ButtonVariant... variants) {
        Button button = new Button(text);
        button.addThemeVariants(variants);
        return button;
    }

    private void saveUser(String name, String surname, LocalDate dateOfBirth, String email, String phoneNumber, String role, String password, Boolean approved) {

        if (!isPasswordUnique(password)) {
            Notification.show("Please use a different password.");
            return;
        }

        if (!isEmailUnique(email)){
            Notification.show("This email is already in use");
            return;
        }

        if (role.equals("Employer")) {
            saveEmployer(name, surname, dateOfBirth, email, phoneNumber, role, password);
        } else {
            saveSearcher(name, surname, dateOfBirth, email, phoneNumber, role, password, false);
        }
        UI.getCurrent().navigate("login");
    }

    private void saveEmployer(String name, String surname, LocalDate dateOfBirth, String email, String phoneNumber, String role, String password) {
        try {
            Employer employer = new Employer(name, surname, dateOfBirth, email, phoneNumber, role, password);
            boolean isSaved = employerService.saveEmployer(employer);
            if (isSaved) {
                Notification.show("New employer account is created!");
            } else {
                Notification.show("Error: Could not save employer. Please check the entered data.");
            }
        } catch (Exception e) {
            Notification.show("Error: Could not save employer. Please check the entered data.");
        }
    }

    private void saveSearcher(String name, String surname, LocalDate dateOfBirth, String email, String phoneNumber, String role, String password, Boolean approved) {
        try {
            Searcher searcher = new Searcher(name, surname, dateOfBirth, email, phoneNumber, role, password, false);
            boolean isSaved = searcherService.saveSearcher(searcher);
            if (isSaved) {
                Notification.show("New searcher account is created!");
            } else {
                Notification.show("Error: Could not save searcher. Please check the entered data.");
            }
        } catch (Exception e) {
            Notification.show("Error: Could not save searcher. Please check the entered data.");
        }
    }

    private boolean isPasswordUnique(String password) {
        return employerService.isPasswordUnique(password) && searcherService.isPasswordUnique(password);
    }
    private boolean isEmailUnique(String email){
        return employerService.isEmailUnique(email) && searcherService.isEmailUnique(email);
    }
}
