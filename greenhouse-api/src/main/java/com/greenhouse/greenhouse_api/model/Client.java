package com.greenhouse.greenhouse_api.model;


public class Client {
	
	private int userID;
	private int id;
	private double premium;
	private double cover;
	
	public int getUserID() {
		return userID;
	}
	public int getID() {
		return id;
	}
	public double getPremium() {
		return premium;
	}
	public double getCover() {
		return cover;
	}

	public Client() {}
	
	public Client(double cover, double premium, int userID) {
		this.userID = userID;
		this.premium = premium;
		this.cover = cover;
	}
}