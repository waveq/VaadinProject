package com.waveq.form;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	 */
	public VerticalLayout getLoginForm() {
		loginUser.setWidth("300px");
		loginUser.setRequired(true);
		loginUser.setInputPrompt("Your username");
		loginUser.setInvalidAllowed(false);
		loginUser.setImmediate(true);

		// Create the password input field
		loginPassword.setWidth("300px");
		loginPassword.addValidator(new UsernameAndPasswordValidator(true, "Password must be at least 6 characters and contain at least 1 digit."));
		loginPassword.setRequired(true);
		loginPassword.setValue("");
		loginPassword.setNullRepresentation("");
		loginPassword.setImmediate(true);

		VerticalLayout loginForm = new VerticalLayout(loginUser, loginPassword, loginButton);

		loginForm.setCaption("Please login to access the application. (test/passw0rd)");
		loginForm.setSpacing(true);
		loginForm.setMargin(new MarginInfo(true, true, true, false));
		loginForm.setSizeUndefined();

		return loginForm;
	}

	/**
	 * component 0 - username
	 * component 1 - mail
	 * component 2 - year of born
	 * component 3 - password
	 * component 4 - button
	 */
	public VerticalLayout getRegisterForm() {
		registerUser.setWidth("300px");
		registerUser.setRequired(true);
		registerUser.setInputPrompt("Your username");
		registerUser.setInvalidAllowed(false);
		registerUser.addValidator(new UsernameAndPasswordValidator(false, "Username must be at least 6 characters."));
		registerUser.setImmediate(true);

		registerMail.setWidth("300px");
		registerMail.setRequired(true);
		registerMail.addValidator(new EmailValidator(
				"This is not valid email address."));
		registerMail.setInputPrompt("Your mail (eg. joe@email.com)");
		registerMail.setInvalidAllowed(false);
		registerMail.setImmediate(true);

		registerYOB.setWidth("300px");
		registerYOB.setRequired(true);
		registerYOB.addValidator(new yobValidator("You date of birth must be before today."));
		registerYOB.setDateFormat("dd-MM-yyyy");
		registerYOB.setImmediate(true);

		registerPassword.setWidth("300px");
		registerPassword.addValidator(new UsernameAndPasswordValidator(true, "Password must be at least 6 characters and contain at least 1 digit."));
		registerPassword.setRequired(true);
		registerPassword.setValue("");
		registerPassword.setNullRepresentation("");
		registerPassword.setImmediate(true);


		VerticalLayout registerForm = new VerticalLayout(registerUser, registerMail, registerYOB, registerPassword, registerButton);
		registerForm.setCaption("Register here");
		registerForm.setSpacing(true);
		registerForm.setMargin(new MarginInfo(true, true, true, false));
		registerForm.setSizeUndefined();

		return registerForm;
	}

	private static final class UsernameAndPasswordValidator extends AbstractValidator<String> {
		private boolean isPassword;

		public UsernameAndPasswordValidator(boolean isPassword, String message) {
			super(message);
			this.isPassword = isPassword;
		}

		@Override
		protected boolean isValidValue(String value) {
			if (isPassword) {
				if (value != null && (value.length() < 6 || !value.matches(".*\\d.*"))) {
					return false;
				}
			} else {
				if (value != null && (value.length() < 6)) {
					return false;
				}
			}
			return true;
		}

		@Override
		public Class<String> getType() {
			return String.class;
		}
	}

	private static final class yobValidator extends AbstractValidator<Date> {
		public yobValidator(String message) {
			super(message);
		}

		@Override
		protected boolean isValidValue(Date value) {
			if (value != null && value.after(new Date())) {
				return false;
			}
			return true;
		}

		@Override
		public Class<Date> getType() {
			return Date.class;
		}
	}
}
