package com.roy.validation;

import java.util.ArrayList;
import java.util.List;

import com.roy.models.Recipe;

public class RecipesValidation {
	
	public String validateRecipeForm(Recipe recipe) {
		/* Validating AddRecipe Form Submission, List contains class names for the relevant form elements */
		
		List<String> arr = new ArrayList<String>();
		
		/* Products Validation */
		List<String> prod = recipe.getProducts();
		List<String> prod_price = recipe.getPrices();

		if ( prod.size() > 0 && prod_price.size() > 0 && prod.size() == prod_price.size() ) {
			try { 
				for (int i=0; i < prod.size(); i++) {
					if ( prod.get(i).trim().isEmpty() ||  Float.parseFloat(prod_price.get(i)) < 1 ) {
						arr.add(".products_col");
						break;
					}
				}
			}catch(Exception ex) {
				arr.add(".products_col");
			}
			
		}
		else {
			arr.add(".products_col");
		}
		
		/* Title Validation */
		
		if ( recipe.getTitle().trim().isEmpty() || recipe.getTitle().length() > 120) {
			arr.add(".add_recipe_title");
		}
		
		/* Freetext Validation */
		
		if ( recipe.getFreetext().length() > 550 ) {
			arr.add(".add_recipe_freetext");
		}
		
		/* Preparation Time Validation */
		
		if (recipe.getTime() == null || recipe.getTime() <= 0 || recipe.getTime() > 2880) {
			arr.add(".add_recipe_preparetime");
		} 
		
		/* Amount Validation */
		
		if (recipe.getAmount() == null || recipe.getAmount() <= 0 || recipe.getAmount() > 100) {
			arr.add(".add_recipe_amount");
		}
		
		return String.join(", ", arr);
	}
	
}
