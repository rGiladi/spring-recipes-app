package com.roy.models;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import com.google.gson.Gson;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="recipes")
public class Recipe {
	
	@Id @GeneratedValue
	private long id;
	
	@Length(max=120)
	@Column(name="title", length=120)
	private String title;
	
	@Length(max=600) 
	@Column(name="freetext", length=600)
	private String freetext;
	private String recipetime;
	private String recipetype;
	private boolean vegetarian;
	private boolean vegan;
	private boolean kosher;
	private boolean spicy;
	private boolean gluten;
	private String image;
	private java.sql.Date dateField;
	private boolean reported;
	
	@Column(name="price", precision=2, scale=2)
	private Double price;
	
	@Column(name="time", precision=2, scale=2)
	private Double time;
	
	@Column(name="amount", precision=2, scale=2)
	private Double amount;
	
	@ElementCollection
	private Set<Long> usersLiked;
	
	@ElementCollection
	private List<String> products;
	
	@ElementCollection
	private List<String> prices;
	
	@ElementCollection
	private List<String> options;
	
	@ElementCollection
	private Set<Long> usersReported;
	
	@ManyToOne
	private User user;

	public Recipe(long id, String title, String freetext, String recipetime, String recipetype, boolean vegetarian, boolean vegan,
			boolean kosher, boolean spicy, boolean gluten, String image, Date dateField, boolean reported, Double amount, Double price, Double time,
			Set<Long> usersLiked, List<String> products, List<String> prices, List<String> options,
			Set<Long> usersReported, User user) {	
		this.id = id;
		this.title = title;
		this.freetext = freetext;
		this.recipetime = recipetime;
		this.recipetype = recipetype;
		this.vegetarian = vegetarian;
		this.vegan = vegan;
		this.kosher = kosher;
		this.spicy = spicy;
		this.gluten = gluten;
		this.image = image;
		this.dateField = dateField;
		this.price = price;
		this.time = time;
		this.amount = amount;
		this.usersLiked = usersLiked;
		this.products = products;
		this.prices = prices;
		this.options = options;
		this.usersReported = usersReported;
		this.user = user;
		this.reported = reported;
	}
	
	public Recipe() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFreetext() {
		return freetext;
	}

	public void setFreetext(String freetext) {
		this.freetext = freetext;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public java.sql.Date getDateField() {
		return dateField;
	}

	public void setDateField(java.sql.Date dateField) {
		this.dateField = dateField;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public Set<Long> getUsersLiked() {
		return usersLiked;
	}

	public void setUsersLiked(Set<Long> usersLiked) {
		this.usersLiked = usersLiked;
	}

	public List<String> getProducts() {
		return products;
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}

	public List<String> getPrices() {
		return prices;
	}

	public void setPrices(List<String> prices) {
		this.prices = prices;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public Set<Long> getUsersReported() {
		return usersReported;
	}

	public void setUsersReported(Set<Long> usersReported) {
		this.usersReported = usersReported;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isReported() {
		return reported;
	}

	public void setReported(boolean reported) {
		this.reported = reported;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
}
