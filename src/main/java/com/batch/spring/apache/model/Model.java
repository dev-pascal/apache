package com.batch.spring.apache.model;

public class Model {

	private String name;
	private String email;
	
	public Model() { }
	
	public Model(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	
	public final String getName() {
		return name;
	}
	public final String getEmail() {
		return email;
	}
	public final void setName(String name) {
		this.name = name;
	}
	public final void setEmail(String email) {
		this.email = email;
	}
	
}
