package com.waveq.form;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

/**
 * Created by Szymon on 2015-03-15.
 */
public class LoginFormMaker {

    private final TextField loginUser;
    private final PasswordField loginPassword;
    private final Button loginButton;

    private final TextField registerUser;
    private final TextField registerMail;
    private final DateField registerYOB;
    private final PasswordField registerPassword;
    private final Button registerButton;


    public LoginFormMaker(Button.ClickListener clickListener) {
        loginUser = new TextField("User:");
        loginPassword = new PasswordField("Password:");
        loginButton = new Button("Login", clickListener);

        registerUser = new TextField("User:");
        registerMail = new TextField("Email");
        registerYOB = new DateField("Year of birth:");
        registerPassword = new PasswordField("Password:");
        registerButton = new Button("Register", clickListener);
    }


    /**
     * component 0 - username
     * component 1 - password
     * component 2 - button
     * @return
     */
    public VerticalLayout getLoginForm() {
        loginUser.setWidth("300px");
        loginUser.setRequired(true);
        loginUser.setInputPrompt("Your username");
        loginUser.setInvalidAllowed(false);

        // Create the password input field
        loginPassword.setWidth("300px");
        loginPassword.addValidator(new PasswordValidator());
        loginPassword.setRequired(true);
        loginPassword.setValue("");
        loginPassword.setNullRepresentation("");

        VerticalLayout loginForm = new VerticalLayout(loginUser, loginPassword, loginButton);

        loginForm.setCaption("Please login to access the application. (test/passw0rd)");
        loginForm.setSpacing(true);
        loginForm.setMargin(new MarginInfo(true, true, true, false));
        loginForm.setSizeUndefined();

        return loginForm;
    }

    public VerticalLayout getRegisterForm() {
        registerUser.setWidth("300px");
        registerUser.setRequired(true);
        registerUser.setInputPrompt("Your username");
        registerUser.setInvalidAllowed(false);

        registerMail.setWidth("300px");
        registerMail.setRequired(true);
        registerMail.addValidator(new EmailValidator(
                "This is not valid email address"));
        registerMail.setInputPrompt("Your mail (eg. joe@email.com)");
        registerMail.setInvalidAllowed(false);

        registerYOB.setWidth("300px");
        registerYOB.setRequired(true);
        registerYOB.setDateFormat("dd-MM-yyyy");

        registerPassword.setWidth("300px");
        registerPassword.addValidator(new PasswordValidator());
        registerPassword.setRequired(true);
        registerPassword.setValue("");
        registerPassword.setNullRepresentation("");

        VerticalLayout registerForm = new VerticalLayout(registerUser, registerMail, registerYOB, registerPassword, registerButton);
        registerForm.setCaption("You can register here");
        registerForm.setSpacing(true);
        registerForm.setMargin(new MarginInfo(true, true, true, false));
        registerForm.setSizeUndefined();

        return registerForm;
    }

    private static final class PasswordValidator extends
            AbstractValidator<String> {

        public PasswordValidator() {
            super("The password provided is not valid");
        }

        @Override
        protected boolean isValidValue(String value) {
            if (value != null
                    && (value.length() < 8 || !value.matches(".*\\d.*"))) {
                return false;
            }
            return true;
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }
    }
}
