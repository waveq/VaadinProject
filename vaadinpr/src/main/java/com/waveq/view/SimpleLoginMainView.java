package com.waveq.view;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.waveq.entity.Auction;
import com.waveq.service.AuctionService;

/**
 * Created by Szymon on 2015-03-14.
 */
public class SimpleLoginMainView extends CustomComponent implements View {

	public static final String NAME = "";
	private AuctionService auctionService;
	private Grid auctionsGrid;

	Button logout = new Button("Logout", new Button.ClickListener() {

		@Override
		public void buttonClick(Button.ClickEvent event) {

			// "Logout" the user
			getSession().setAttribute("user", null);

			// Refresh this view, should redirect to loggedIn view
			getUI().getNavigator().navigateTo(NAME);
		}
	});
	Label text = new Label();
	Panel topPanel = new Panel();

	public SimpleLoginMainView() {
		auctionService = new AuctionService();
		prepareAuctions();
		VerticalLayout content = new VerticalLayout();
		setCompositionRoot(content);
		content.addComponent(topPanel);
		topPanel.setWidth("500px");
		prepareView(content);
	}

	private void prepareAuctions() {
		for(int i=0;i<10;i++) {
			auctionService.addAuction(new Auction(300+i*10, "Auction name"+i, "Description "+i, "path"));
		}

	}

	private HorizontalLayout prepareMedium(VerticalLayout content) {
		HorizontalLayout medium = new HorizontalLayout();
		medium.setSizeUndefined();
		content.addComponent(medium);
		return medium;
	}

	private void addLeftBox(HorizontalLayout medium) {
		VerticalLayout leftPanel = new VerticalLayout();
		medium.addComponent(leftPanel);

		leftPanel.addComponent(new Tree("Menu"));
		leftPanel.setWidth("300px");
		leftPanel.addComponent(logout);
	}

	private void addRightBox(HorizontalLayout medium) {
		VerticalLayout rightPanel = new VerticalLayout();
		medium.addComponent(rightPanel);

		rightPanel.addComponent(new Tree("Auctions"));
		rightPanel.setWidth("600px");

		BeanItemContainer<Auction> container =
				new BeanItemContainer<Auction>(Auction.class, auctionService.getAuctions());
		auctionsGrid = new Grid(container);
		auctionsGrid.setColumnOrder("name", "price");
		rightPanel.addComponent(auctionsGrid);
	}

	private void prepareView(VerticalLayout content) {
		content.setComponentAlignment(topPanel, Alignment.TOP_CENTER);
		HorizontalLayout medium = prepareMedium(content);
		addLeftBox(medium);
		addRightBox(medium);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		// Get the user name from the session
		String username = String.valueOf(getSession().getAttribute("user"));
		String email = String.valueOf(getSession().getAttribute("email"));
		String yob = String.valueOf(getSession().getAttribute("yob"));

		topPanel.setCaption("Hello " + username + ", " + email + " you were born " + yob);

		text.setValue("Hello " + username + ", " + email + " you were born " + yob);
	}
}
