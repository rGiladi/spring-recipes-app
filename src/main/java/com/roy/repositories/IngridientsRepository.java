package com.roy.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.roy.models.Ingridient;
import com.roy.models.IngridientCategory;

public interface IngridientsRepository extends CrudRepository<Ingridient, Long>{
	public List<Ingridient> findByIngridientCategory(IngridientCategory ingridientCategory);
	
	@Modifying
	@Transactional
	@Query(value = "delete from ingridients where id = :id", nativeQuery=true)
	public void customDelete(@Param("id") long id );
}
