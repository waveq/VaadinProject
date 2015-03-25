package com.waveq.view;

import com.vaadin.client.ui.VGridLayout;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClassResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ImageRenderer;
import com.waveq.entity.Auction;
import com.waveq.service.AuctionService;

import java.util.Locale;

/**
 * Created by Szymon on 2015-03-14.
 */
public class SimpleLoginMainView extends CustomComponent implements View {

	public static final String NAME = "";
	private AuctionService auctionService;
	private Grid auctionsGrid;
	private Table auctionsTable;

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
			Auction a = new Auction(300+i*10, "Auction name"+i, "Description "+i, "hhead.png");
			if(!auctionService.contains(a)) {
				auctionService.addAuction(a);
			}
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
		rightPanel.setWidth("1400px");

		BeanItemContainer<Auction> container =
				new BeanItemContainer<Auction>(Auction.class, auctionService.getAuctions());
		auctionsGrid = new Grid(container);
		auctionsGrid.setWidth("1400");
		auctionsGrid.setHeight("850");
		auctionsGrid.setStyleName("gridwithpics128px");
//		auctionsGrid.setCellStyleGenerator(cell ->
//				"picture".equals(cell.getPropertyId())?
//						"imagecol" : null);

		auctionsGrid.setCellStyleGenerator(new Grid.CellStyleGenerator() {
			public String getStyle(com.vaadin.ui.Grid.CellReference cellReference) {
				return cellReference.getPropertyId().equals("image") ? "imagecol" : null;}
		});

//		table.setCellStyleGenerator(new Table.CellStyleGenerator() {
//			public String getStyle(Object itemId, Object propertyId) {
//				int row = ((Integer) itemId).intValue();
//				int col = Integer.parseInt((String) propertyId);
//
//				if (table.getItem(row).getItemProperty(col).getValue.equals("ANOM1") {
//					return "red"
//				}
//				return "normal";
//			}
//		});

//		auctionsGrid.addColumn("Price", Integer.class);
//		auctionsGrid.addColumn("Name", String.class);
//		auctionsGrid.addColumn("Description", String.class);
//		auctionsGrid.addColumn("Picture", String.class);

		// Set the image renderer
		auctionsGrid.getColumn("image").setRenderer(new ImageRenderer(),
				new Converter<Resource, String>() {
					@Override
					public String convertToModel(Resource value,
					                             Class<? extends String> targetType, Locale l)
							throws Converter.ConversionException {
						return "not needed";
					}

					@Override
					public Resource convertToPresentation(String value,
					                                      Class<? extends Resource> targetType, Locale l)
							throws Converter.ConversionException {
						return new ThemeResource("img/" + value);
					}

					@Override
					public Class<String> getModelType() {
						return String.class;
					}

					@Override
					public Class<Resource> getPresentationType() {
						return Resource.class;
					}
				});

//		for(Auction a : auctionService.getAuctions()) {
//			auctionsGrid.addRow(a.getPrice(), a.getName(), a.getDescription(), a.getImage());
////			auctionsGrid.addRow(200, "Robak", "Zielony Robak", "hhead.png");
//		}


		auctionsGrid.setColumnOrder("image", "name");
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
