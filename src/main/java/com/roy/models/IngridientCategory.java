package com.roy.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "ingridient_category")
public class IngridientCategory {
	
	@Id @GeneratedValue
	private long id;
	private String name;
	
	@OneToMany(mappedBy = "ingridientCategory", cascade = CascadeType.ALL)
	private Set<Ingridient> ingridients;
	
	public IngridientCategory(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public IngridientCategory(){};
	
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

	
	public Set<Ingridient> getIngridients() {
		return ingridients;
	}
	public void setIngridients(Set<Ingridient> ingridients) {
		this.ingridients = ingridients;
	}
	
}
