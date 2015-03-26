package com.waveq.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import com.waveq.entity.User;
import com.waveq.form.LoginFormMaker;
import com.waveq.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Szymon on 2015-03-14.
 */
public class SimpleLoginView extends CustomComponent implements View,
		Button.ClickListener {
	public static final String LOGGED_OUT = "login";
	private final TextField loginUser;
	private final PasswordField loginPassword;
	private final Button loginButton;
	private final TextField registerUser;
	private final TextField registerMail;
	private final DateField registerYOB;
	private final PasswordField registerPassword;
	private final Button registerButton;
	private LoginFormMaker loginFormMaker;
	private UserService userService;

	public SimpleLoginView() {
		userService = new UserService();
		userService.addUser(new User("test", "test@test.com", "passw0rd", new Date()));

		setSizeFull();
		loginFormMaker = new LoginFormMaker(this);
		VerticalLayout loginForm = loginFormMaker.getLoginForm();

		loginUser = (TextField) loginForm.getComponent(0);
		loginPassword = (PasswordField) loginForm.getComponent(1);
		loginButton = (Button) loginForm.getComponent(2);

		VerticalLayout registerForm = loginFormMaker.getRegisterForm();

		registerUser = (TextField) registerForm.getComponent(0);
		registerMail = (TextField) registerForm.getComponent(1);
		registerYOB = (DateField) registerForm.getComponent(2);
		registerPassword = (PasswordField) registerForm.getComponent(3);
		registerButton = (Button) registerForm.getComponent(4);
//        PopupView popup = new PopupView("Login", loginForm);
//        popup.setHideOnMouseOut(false);


		HorizontalLayout viewLayout = new HorizontalLayout(loginForm, registerForm);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
		viewLayout.addComponent(new Label(userService.getUsers().size() + " - oto ilość userów"));
		viewLayout.setComponentAlignment(registerForm, Alignment.MIDDLE_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		setCompositionRoot(viewLayout);
	}

	private void addUser(User u) {
		userService.addUser(u);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
	}

	@Override
	public void buttonClick(Button.ClickEvent event) {
		if (event.getButton().getCaption().equals("Login")) {
			loginButton(event);
		} else {
			registerButton(event);
		}
	}

	private void loginButton(Button.ClickEvent event) {
		if (!loginUser.isValid() || !loginPassword.isValid()) {
			return;
		}

		boolean isValid = false;
		User loggedUser = null;
		for (User u : userService.getUsers()) {
			if (u.getUsername().equals(loginUser.getValue()) && u.getPassword().equals(loginPassword.getValue())) {
				isValid = true;
				loggedUser = u;
				break;
			}
		}

		if (isValid) {
			login(loggedUser);
		} else {
			this.loginUser.setValue(null);
			this.loginPassword.setValue(null);
			this.loginUser.focus();
		}
	}

	private void registerButton(Button.ClickEvent event) {
		if (!registerMail.isValid() || !registerPassword.isValid() || !registerUser.isValid() || !registerYOB.isValid()) {
			return;
		}

		User user = new User(registerUser.getValue(), registerMail.getValue(), registerPassword.getValue(), registerYOB.getValue());
		addUser(user);
		login(user);
	}

	private void login(User user) {
		getSession().setAttribute("user", user.getUsername());
		getSession().setAttribute("email", user.getEmail());

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		getSession().setAttribute("yob", sdf.format(user.getYob()));

		getUI().getNavigator().navigateTo(SimpleLoginMainView.LOGGED_IN);
	}
}
