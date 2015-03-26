package com.waveq;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.waveq.view.AddAdvertView;
import com.waveq.view.SimpleLoginMainView;
import com.waveq.view.SimpleLoginView;

/**
 *
 */
@Theme("mytheme")
@Widgetset("com.waveq.MyAppWidgetset")
public class MyUI extends UI {

	@Override
	protected void init(VaadinRequest request) {
		new Navigator(this, this);
		getNavigator().addView(SimpleLoginView.LOGGED_OUT, SimpleLoginView.class);//

		getNavigator().addView(SimpleLoginMainView.LOGGED_IN,
				SimpleLoginMainView.class);

		getNavigator().addView(AddAdvertView.ADD_ADV, AddAdvertView.class);

		getNavigator().addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {

				// Check if a user has logged in
				boolean isLoggedIn = getSession().getAttribute("user") != null;
				boolean isLoginView = event.getNewView() instanceof SimpleLoginView;

				if (!isLoggedIn && !isLoginView) {
					// Redirect to login view always if a user has not yet
					// logged in
					getNavigator().navigateTo(SimpleLoginView.LOGGED_OUT);
					return false;

				} else if (isLoggedIn && isLoginView) {
					// If someone tries to access to login view while logged in,
					// then cancel
					return false;
				}

				return true;
			}

			@Override
			public void afterViewChange(ViewChangeListener.ViewChangeEvent event) {

			}
		});
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(widgetset = "com.waveq.MyAppWidgetset", ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
