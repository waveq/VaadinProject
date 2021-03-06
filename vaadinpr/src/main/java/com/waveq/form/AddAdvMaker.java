package com.waveq.form;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Szymon on 2015-03-26.
 */
public class AddAdvMaker {

	private final TextField name;
	private final TextField description;
	private final TextField price;
	private final Upload upload;
	private final Button addButton;

	Upload.Receiver receiver;
	Upload.SucceededListener listener;

	public AddAdvMaker(Upload.Receiver receiver, Upload.SucceededListener listener, Button.ClickListener clickListener) {
		name = new TextField("Name: ");
		description = new TextField("Description: ");
		price = new TextField(("Price: "));
		upload = new Upload("Upload Image Here", receiver);
		addButton = new Button("Add advert", clickListener);
		this.receiver = receiver;
		this.listener = listener;
	}

	/**
	 * component 0 - name
	 * component 1 - description
	 * component 2 - price
	 * component 3 - upload
	 */
	public VerticalLayout getAdvForm() {
		name.setWidth("300px");
		name.setRequired(true);
		name.addValidator(new AdvValidator(false, "Name must be at least 3 characters."));
		name.setInputPrompt("Name of your ad");
		name.setInvalidAllowed(false);
		name.setImmediate(true);

		description.setWidth("500px");
		description.setRequired(false);
		description.setValue("");
		description.setNullRepresentation("");
		description.setImmediate(true);

		price.setWidth("300px");
		price.setRequired(true);
		price.addValidator(new AdvValidator(true, "Price must be a number."));
		price.setInputPrompt("Price of your item");
		price.setInvalidAllowed(false);
		price.setImmediate(true);

		upload.setButtonCaption("Start Upload");
		upload.addSucceededListener(listener);

		VerticalLayout advForm = new VerticalLayout(name, description, price, upload, addButton);

		advForm.setCaption("You can add advert here: ");
		advForm.setSpacing(true);
		advForm.setMargin(new MarginInfo(true, true, true, false));
		advForm.setSizeUndefined();

		return advForm;
	}

	private static final class AdvValidator extends AbstractValidator<String> {
		private boolean isPrice;

		public AdvValidator(boolean isPrice, String message) {
			super(message);
			this.isPrice = isPrice;
		}

		public static boolean isNumeric(String str) {
			try {
				double d = Double.parseDouble(str);
			}
			catch(NumberFormatException nfe) {
				return false;
			}
			return true;
		}

		@Override
		protected boolean isValidValue(String value) {
			if (isPrice) {
				if(!isNumeric(value)) {
					return false;
				}
			} else {
				if (value != null && (value.length() < 3)) {
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
}
