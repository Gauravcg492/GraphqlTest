package com.backend.test.models;

public class InsertUserInput {

	private String email;
	private String password;

	public InsertUserInput(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "InsertUserInput [email=" + email + ", password=" + password + "]";
	}

}
