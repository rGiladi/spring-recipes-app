package com.roy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.roy.models.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long>{
	
	public List<Recipe> findByUserId(long id);
	public List<Recipe> findByReported(boolean bool);
	public List<Recipe> findByUsersLiked(long id);
	
	@Query(value = "SELECT * FROM recipes WHERE (title LIKE %" + ":freetext" + "% OR :freetext = '')"
			+ " AND (price <= :price) AND (recipetime = :recipetime OR :recipetime = '' OR :recipetime is null)"
			+ " AND (recipetype = :recipetype OR :recipetype = '' OR :recipetype is null) AND (`time` <= :time)"
			+ " AND (vegetarian = :vegetarian OR :vegetarian = 0) AND (vegan = :vegan OR :vegan = 0)"
			+ " AND (kosher = :kosher OR :kosher = 0) AND (spicy = :spicy OR :spicy = 0)"
			+ " AND (gluten = :gluten OR :gluten = 0) ", nativeQuery=true)
	public List<Recipe> Search(@Param("price") int price, @Param("recipetime") String recipetime
			,@Param("recipetype") String recipetype, @Param("freetext") String freetext, @Param("time") int time,
			@Param("vegetarian") boolean vegetarian, @Param("vegan") boolean vegan,
			@Param("kosher") boolean kosher, @Param("spicy") boolean spicy, @Param("gluten") boolean gluten);
	
	@Query(value = "SELECT * FROM recipes ORDER BY date_field LIMIT 20 OFFSET :starting", nativeQuery=true)
	public List<Recipe> AllRecipesPaging(@Param("starting") int se);
	
}
