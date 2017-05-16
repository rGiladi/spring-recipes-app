package com.roy.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ingridients")
public class Ingridient implements Comparable<Ingridient> {
	
	@Id @GeneratedValue
	private long id;
	private String name;
	
	@Column(name = "price_oz", precision=2, scale=2)
	public Double priceoz;
	
	@Column(name = "price_amount", precision=2, scale=2)
	public Double priceamount;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ingridient_category_id")
	private IngridientCategory ingridientCategory;
	
	public Ingridient(long id, String name, IngridientCategory ingridientCategory, Double priceoz, Double priceamount) {
		this.id = id;
		this.name = name;
		this.ingridientCategory = ingridientCategory;
		this.priceoz = priceoz;
		this.priceamount = priceamount;
	}
	
	public Ingridient(){}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPriceoz() {
		return priceoz;
	}

	public void setPriceoz(Double priceoz) {
		this.priceoz = priceoz;
	}

	public Double getPriceamount() {
		return priceamount;
	}

	public void setPriceamount(Double priceamount) {
		this.priceamount = priceamount;
	}

	public IngridientCategory getIngridientCategory() {
		return ingridientCategory;
	}

	public void setIngridientCategory(IngridientCategory ingridientCategory) {
		this.ingridientCategory = ingridientCategory;
	}

	@Override
	public int compareTo(Ingridient o) {
		// TODO Auto-generated method stub
		return this.getId() > o.getId() ? -1 : 1;
	}
	
}
