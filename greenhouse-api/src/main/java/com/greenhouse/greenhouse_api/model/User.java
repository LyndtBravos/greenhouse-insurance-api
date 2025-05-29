package com.greenhouse.greenhouse_api.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class User {
	
	private int id;
	private String name;
	private String surname;
	private String email;
	private String password;
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}

	public User() {}
	
	public User(int id, String name, String surname, String email, String password, boolean encrypt) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = encrypt ? sha1(password) :  password;
	}

	private String sha1(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] result = md.digest(input.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (byte b : result) {
				sb.append(String.format("%02x", b)); // convert to hex
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException("SHA-1 hashing error", e);
		}
	}
}