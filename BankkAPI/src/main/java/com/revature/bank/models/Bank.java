package com.revature.bank.models;

public class Bank {

	private int id;
	private String title;
	private double price;
	private boolean available;
	private long returnDate;

	public Bank() {
		super();
	}

	public Bank(int id, String title, double price, boolean available, long returnDate) {
		this();
		this.id = id;
		this.title = title;
		this.price = price;
		this.available = available;
		this.returnDate = returnDate;
	}

	public Bank(String title, double price, boolean available, long returnDate) {
		super();
		this.title = title;
		this.price = price;
		this.available = available;
		this.returnDate = returnDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public long getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(long returnDate) {
		this.returnDate = returnDate;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", price=" + price + ", available=" + available
				+ ", returnDate=" + returnDate + "]";
	}
}
