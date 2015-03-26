package com.waveq.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.waveq.entity.Auction;
import com.waveq.entity.User;
import com.waveq.form.AddAdvMaker;
import com.waveq.service.AuctionService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Szymon on 2015-03-26.
 */
public class AddAdvertView extends CustomComponent implements View, Button.ClickListener {

	public static final String ADD_ADV = "add";
	final Embedded image = new Embedded("Uploaded Image");
	ImageUploader receiver = new ImageUploader();

	private final TextField name;
	private final TextField description;
	private final TextField price;
	private final Upload upload;
	private final Button addButton;

	private VerticalLayout advForm;

	Panel topPanel = new Panel();


	private AuctionService auctionService;

	Button back = new Button("Back", new Button.ClickListener() {
		@Override
		public void buttonClick(Button.ClickEvent event) {
			getUI().getNavigator().navigateTo(SimpleLoginMainView.LOGGED_IN);
		}
	});

	Button logout = new Button("Logout", new Button.ClickListener() {
		@Override
		public void buttonClick(Button.ClickEvent event) {
			getSession().setAttribute("user", null);
			getUI().getNavigator().navigateTo(SimpleLoginView.LOGGED_OUT);
		}
	});

	public AddAdvertView() {
		auctionService = new AuctionService();
		AddAdvMaker advFormMaker = new AddAdvMaker(receiver, receiver, this);
		advForm = advFormMaker.getAdvForm();

		name = (TextField) advForm.getComponent(0);
		description = (TextField) advForm.getComponent(1);
		price = (TextField) advForm.getComponent(2);
		upload = (Upload) advForm.getComponent(3);
		addButton = (Button) advForm.getComponent(4);
		advForm.addComponent(image);
		image.setVisible(false);


//		advForm.setSizeFull();
//		setCompositionRoot(advForm);

		VerticalLayout content = new VerticalLayout();
		setCompositionRoot(content);
		content.addComponent(topPanel);
		topPanel.setWidth("500px");
		prepareView(content);

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
		leftPanel.addComponent(back);
	}

	private void addRightBox(HorizontalLayout medium) {
		medium.addComponent(advForm);
		medium.setHeight("1400px");
	}

	private void prepareView(VerticalLayout content) {
		content.setComponentAlignment(topPanel, Alignment.TOP_CENTER);
		HorizontalLayout medium = prepareMedium(content);
		addLeftBox(medium);
		addRightBox(medium);
	}

	private void addAuction(Auction a) {
		auctionService.addAuction(a);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {


	}

	private String uploadedFileName = "";
	class ImageUploader implements Upload.Receiver, Upload.SucceededListener {
		public File file;

		public OutputStream receiveUpload(String filename,
		                                  String mimeType) {
			// Create upload stream
			FileOutputStream fos = null; // Stream to write to
			try {
				// Open the file for writing.

				String workingDir = System.getProperty("user.dir");

				System.out.println(workingDir+"\\src\\main\\webapp\\VAADIN\\themes\\mytheme\\img\\"+filename);
				file = new File(workingDir+"\\src\\main\\webapp\\VAADIN\\themes\\mytheme\\img\\"+filename);
				fos = new FileOutputStream(file);
				uploadedFileName = filename;
			} catch (final java.io.FileNotFoundException e) {
				new Notification("Could not open file<br/>",
						e.getMessage(),
						Notification.Type.ERROR_MESSAGE)
						.show(Page.getCurrent());
				return null;
			}
			return fos; // Return the output stream to write to
		}

		public void uploadSucceeded(Upload.SucceededEvent event) {
			// Show the uploaded file in the image viewer
			image.setVisible(true);
			image.setSource(new FileResource(file));
		}
	};

	@Override
	public void buttonClick(Button.ClickEvent event) {
		Auction a = new Auction(Integer.parseInt(price.getValue()), name.getValue(), description.getValue(), uploadedFileName);
		addAuction(a);
		getUI().getNavigator().navigateTo(SimpleLoginMainView.LOGGED_IN);
	}
}
