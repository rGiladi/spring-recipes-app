package com.roy.repositories;


import org.springframework.data.repository.CrudRepository;
import com.roy.models.IngridientCategory;

public interface IngridientsCategoryRepository extends CrudRepository<IngridientCategory, Long>{
	public IngridientCategory findByName(String name);
}
