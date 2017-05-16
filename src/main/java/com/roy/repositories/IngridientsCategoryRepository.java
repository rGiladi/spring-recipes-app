package com.roy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.roy.models.Ingridient;
import com.roy.models.IngridientCategory;
import com.roy.models.Recipe;

public interface IngridientsCategoryRepository extends CrudRepository<IngridientCategory, Long>{
	public IngridientCategory findByName(String name);
}
