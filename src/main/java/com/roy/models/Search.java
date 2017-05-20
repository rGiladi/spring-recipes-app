package com.roy.models;

public class Search {
	private int price;
	private String recipetime;
	private String recipetype;
	private String freetext;
	private int time;
	private int likes;
	private boolean vegetarian;
	private boolean vegan;
	private boolean kosher;
	private boolean spicy;
	private boolean gluten;
	
	
	public Search(int price, String recipetime, String recipetype, String freetext, int time, int likes, boolean vegetarian, boolean vegan,
			boolean kosher, boolean spicy, boolean gluten) {
		this.price = price;
		this.recipetime = recipetime;
		this.recipetype = recipetype;
		this.freetext = freetext;
		this.time = time;
		this.likes = likes;
		this.vegetarian = vegetarian;
		this.vegan = vegan;
		this.kosher = kosher;
		this.spicy = spicy;
		this.gluten = gluten;
	}
	
	public Search() {}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getRecipetime() {
		return recipetime;
	}

	public void setRecipetime(String recipetime) {
		this.recipetime = recipetime;
	}

	public String getRecipetype() {
		return recipetype;
	}

	public void setRecipetype(String recipetype) {
		this.recipetype = recipetype;
	}

	public String getFreetext() {
		return freetext;
	}

	public void setFreetext(String freetext) {
		this.freetext = freetext;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public boolean isVegetarian() {
		return vegetarian;
	}

	public void setVegetarian(boolean vegetarian) {
		this.vegetarian = vegetarian;
	}

	public boolean isVegan() {
		return vegan;
	}

	public void setVegan(boolean vegan) {
		this.vegan = vegan;
	}

	public boolean isKosher() {
		return kosher;
	}

	public void setKosher(boolean kosher) {
		this.kosher = kosher;
	}

	public boolean isSpicy() {
		return spicy;
	}

	public void setSpicy(boolean spicy) {
		this.spicy = spicy;
	}

	public boolean isGluten() {
		return gluten;
	}

	public void setGluten(boolean gluten) {
		this.gluten = gluten;
	}
	
}