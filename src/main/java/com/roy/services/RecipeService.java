package com.roy.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.roy.models.Recipe;
import com.roy.repositories.RecipeRepository;

@Service
public class RecipeService {

	@Autowired
	private RecipeRepository recipesRepo;
	
	public Iterable<Recipe> getAllRecipes() {
		return recipesRepo.findAll();
	}
	
	public List<Recipe> getUserRecipesLiked(long id) {
		List<Recipe> recipes = new ArrayList<>();
		recipesRepo.findByUsersLiked(id).forEach(recipes::add);
		return recipes;
	}
	
	public Iterable<Recipe> getSearchRecipes(int price, String recipetime, String recipetype, String freetext, int time,
			boolean vegetarian, boolean vegan, boolean kosher, boolean spicy, boolean gluten) {
		return recipesRepo.Search(price, recipetime, recipetype, freetext, time, vegetarian, vegan, kosher, spicy, gluten);
	}
	
	public void saveRecipe(Recipe recipe) {
		recipesRepo.save(recipe);
	}
	
	public Recipe findRecipeById(long id) {
		return recipesRepo.findOne(id);
	}
	
	public List<Recipe> getAllUserRecipes(long id) {
		return recipesRepo.findByUserId(id);
	}
	
	public void deleteRecipe(long id) {
		recipesRepo.delete(id);
	}
	
	public List<Recipe> getAllReportedRecipes() {
		List<Recipe> recipes = new ArrayList<>();
		recipesRepo.findByReported(true).forEach(recipes :: add);
		return recipes;
	}
	
}
