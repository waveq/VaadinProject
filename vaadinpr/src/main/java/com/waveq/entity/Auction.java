package com.waveq.entity;

import com.vaadin.server.ThemeResource;

/**
 * Created by Szymon on 2015-03-24.
 */
public class Auction {

	private double price;
	private String name;
	private String description;
	private String image;


	public Auction(int price, String name, String description, String image) {
		this.price = price;
		this.name = name;
		this.description = description;
		this.image = image;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
