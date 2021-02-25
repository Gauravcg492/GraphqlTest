package com.backend.test.models;

public class InsertTodoInput {
	private String title;
	private String description;
	private String Category;

	public InsertTodoInput(String title, String description, String category) {
		super();
		this.title = title;
		this.description = description;
		Category = category;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getCategory() {
		return Category;
	}

	@Override
	public String toString() {
		return "InsertTodoInput [title=" + title + ", description=" + description + ", Category=" + Category + "]";
	}

}
