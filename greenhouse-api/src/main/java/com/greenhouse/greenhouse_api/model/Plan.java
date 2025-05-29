package com.greenhouse.greenhouse_api.model;

public class Plan {
	
	private int ID;
	private String DOB;
	private boolean dependency;
	private String gender;
	private int income;
	private int userID;
	
	public int getID() {
		return ID;
	}
	public String getDOB() {
		return DOB;
	}
	public boolean isDependency() {
		return dependency;
	}
	public void setDependency(boolean dependency) {
		this.dependency = dependency;
	}
	public String getGender() {
		return gender;
	}
	public int getIncome() {
		return income;
	}
	public int getUserID() {
		return userID;
	}
	
	public Plan() {}
	
	public Plan(int ID, String Dob, boolean dependency, String gender, int income, int userID) {
		this.ID = ID;
		this.DOB = Dob;
		this.dependency = dependency;
		this.gender = gender;
		this.income = income;
		this.userID = userID;
	}
}