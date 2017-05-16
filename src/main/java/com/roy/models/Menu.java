package com.roy.models;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="menus")
public class Menu {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String title;
	private String freeText;	
	private int published;
	private java.sql.Date pub_date;
	private boolean reported;
	
	@Column(name="price", precision=2, scale=2)
	private Double price;
	
	@ElementCollection
	private Set<Long> usersLiked;
	
	@ElementCollection
	private Set<Long> usersReported;
	
	@ElementCollection
	private List<String> productsRecipe1;
	
	@ElementCollection
	private List<String> pricesRecipe1;
	
	@ElementCollection
	private List<String> productsRecipe2;
	
	@ElementCollection
	private List<String> pricesRecipe2;
	
	@ElementCollection
	private List<String> productsRecipe3;
	
	@ElementCollection
	private List<String> pricesRecipe3;
	
	@ElementCollection
	@Column(nullable=true)
	private List<String> productsRecipe4;
	
	@ElementCollection
	@Column(nullable=true)
	private List<String> pricesRecipe4;
	
	@ElementCollection
	@Column(nullable=true)
	private List<String> productsRecipe5;
	
	@ElementCollection
	@Column(nullable=true)
	private List<String> pricesRecipe5;
	
	@ElementCollection
	@Column(nullable=true)
	private List<String> productsRecipe6;
	
	@ElementCollection
	@Column(nullable=true)
	private List<String> pricesRecipe6;
	
	@ElementCollection
	@Column(nullable=true)
	private List<String> productsRecipe7;
	
	@ElementCollection
	@Column(nullable=true)
	private List<String> pricesRecipe7;
	
	@ElementCollection
	@Column(nullable=true)
	private List<String> productsRecipe8;
	
	@ElementCollection
	@Column(nullable=true)
	private List<String> pricesRecipe8;
	
	@ManyToOne
	private User user;

	public Menu(long id, String title, String freeText, int published, Date pub_date, Double price,
			Set<Long> usersLiked, Set<Long> usersReported, List<String> productsRecipe1, List<String> pricesRecipe1,
			List<String> productsRecipe2, List<String> pricesRecipe2, List<String> productsRecipe3, List<String> pricesRecipe3,
			List<String> productsRecipe4, List<String> pricesRecipe4, List<String> productsRecipe5, List<String> pricesRecipe5,
			List<String> productsRecipe6, List<String> pricesRecipe6, List<String> productsRecipe7, List<String> pricesRecipe7,
			List<String> productsRecipe8, List<String> pricesRecipe8, User user, boolean reported) {
		super();
		this.id = id;
		this.title = title;
		this.freeText = freeText;
		this.published = published;
		this.pub_date = pub_date;
		this.price = price;
		this.usersLiked = usersLiked;
		this.usersReported = usersReported;
		this.productsRecipe1 = productsRecipe1;
		this.pricesRecipe1 = pricesRecipe1;
		this.productsRecipe2 = productsRecipe2;
		this.pricesRecipe2 = pricesRecipe2;
		this.productsRecipe3 = productsRecipe3;
		this.pricesRecipe3 = pricesRecipe3;
		this.productsRecipe4 = productsRecipe4;
		this.pricesRecipe4 = pricesRecipe4;
		this.productsRecipe5 = productsRecipe5;
		this.pricesRecipe5 = pricesRecipe5;
		this.productsRecipe6 = productsRecipe6;
		this.pricesRecipe6 = pricesRecipe6;
		this.productsRecipe7 = productsRecipe7;
		this.pricesRecipe7 = pricesRecipe7;
		this.productsRecipe8 = productsRecipe8;
		this.pricesRecipe8 = pricesRecipe8;
		this.user = user;
		this.reported = reported;
	}

	public Menu() {}

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

	public String getFreeText() {
		return freeText;
	}

	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}

	public int getPublished() {
		return published;
	}

	public void setPublished(int published) {
		this.published = published;
	}

	public java.sql.Date getPub_date() {
		return pub_date;
	}

	public void setPub_date(java.sql.Date pub_date) {
		this.pub_date = pub_date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Set<Long> getUsersLiked() {
		return usersLiked;
	}

	public void setUsersLiked(Set<Long> usersLiked) {
		this.usersLiked = usersLiked;
	}

	public Set<Long> getUsersReported() {
		return usersReported;
	}

	public void setUsersReported(Set<Long> usersReported) {
		this.usersReported = usersReported;
	}

	public List<String> getProductsRecipe1() {
		return productsRecipe1;
	}

	public void setProductsRecipe1(List<String> productsRecipe1) {
		this.productsRecipe1 = productsRecipe1;
	}

	public List<String> getPricesRecipe1() {
		return pricesRecipe1;
	}

	public void setPricesRecipe1(List<String> pricesRecipe1) {
		this.pricesRecipe1 = pricesRecipe1;
	}

	public List<String> getProductsRecipe2() {
		return productsRecipe2;
	}

	public void setProductsRecipe2(List<String> productsRecipe2) {
		this.productsRecipe2 = productsRecipe2;
	}

	public List<String> getPricesRecipe2() {
		return pricesRecipe2;
	}

	public void setPricesRecipe2(List<String> pricesRecipe2) {
		this.pricesRecipe2 = pricesRecipe2;
	}

	public List<String> getProductsRecipe3() {
		return productsRecipe3;
	}

	public void setProductsRecipe3(List<String> productsRecipe3) {
		this.productsRecipe3 = productsRecipe3;
	}

	public List<String> getPricesRecipe3() {
		return pricesRecipe3;
	}

	public void setPricesRecipe3(List<String> pricesRecipe3) {
		this.pricesRecipe3 = pricesRecipe3;
	}

	public List<String> getProductsRecipe4() {
		return productsRecipe4;
	}

	public void setProductsRecipe4(List<String> productsRecipe4) {
		this.productsRecipe4 = productsRecipe4;
	}

	public List<String> getPricesRecipe4() {
		return pricesRecipe4;
	}

	public void setPricesRecipe4(List<String> pricesRecipe4) {
		this.pricesRecipe4 = pricesRecipe4;
	}

	public List<String> getProductsRecipe5() {
		return productsRecipe5;
	}

	public void setProductsRecipe5(List<String> productsRecipe5) {
		this.productsRecipe5 = productsRecipe5;
	}

	public List<String> getPricesRecipe5() {
		return pricesRecipe5;
	}

	public void setPricesRecipe5(List<String> pricesRecipe5) {
		this.pricesRecipe5 = pricesRecipe5;
	}

	public List<String> getProductsRecipe6() {
		return productsRecipe6;
	}

	public void setProductsRecipe6(List<String> productsRecipe6) {
		this.productsRecipe6 = productsRecipe6;
	}

	public List<String> getPricesRecipe6() {
		return pricesRecipe6;
	}

	public void setPricesRecipe6(List<String> pricesRecipe6) {
		this.pricesRecipe6 = pricesRecipe6;
	}

	public List<String> getProductsRecipe7() {
		return productsRecipe7;
	}

	public void setProductsRecipe7(List<String> productsRecipe7) {
		this.productsRecipe7 = productsRecipe7;
	}

	public List<String> getPricesRecipe7() {
		return pricesRecipe7;
	}

	public void setPricesRecipe7(List<String> pricesRecipe7) {
		this.pricesRecipe7 = pricesRecipe7;
	}

	public List<String> getProductsRecipe8() {
		return productsRecipe8;
	}

	public void setProductsRecipe8(List<String> productsRecipe8) {
		this.productsRecipe8 = productsRecipe8;
	}

	public List<String> getPricesRecipe8() {
		return pricesRecipe8;
	}

	public void setPricesRecipe8(List<String> pricesRecipe8) {
		this.pricesRecipe8 = pricesRecipe8;
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
	
	

}