package com.roy.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.roy.models.Menu;

@Component
public class MenusValidator {

	public String validateMenuForm(Menu menu) {
		List<String> errors = new ArrayList<String>();
		
		if ( menu.getTitle() == null || menu.getTitle().trim().isEmpty() ) {
			errors.add(".add_menu_title");
		}
		
		List<String> recipe1 = menu.getProductsRecipe1();
		List<String> recipe1p = menu.getPricesRecipe1();
		List<String> recipe2 = menu.getProductsRecipe2();
		List<String> recipe2p = menu.getPricesRecipe2();
		List<String> recipe3 = menu.getProductsRecipe3();
		List<String> recipe3p = menu.getPricesRecipe3();
		List<String> recipe4 = menu.getProductsRecipe4();
		List<String> recipe4p = menu.getPricesRecipe4();
		List<String> recipe5 = menu.getProductsRecipe5();
		List<String> recipe5p = menu.getPricesRecipe5();
		List<String> recipe6 = menu.getProductsRecipe6();
		List<String> recipe6p = menu.getPricesRecipe6();
		List<String> recipe7 = menu.getProductsRecipe7();
		List<String> recipe7p = menu.getPricesRecipe7();
		List<String> recipe8 = menu.getProductsRecipe8();
		List<String> recipe8p = menu.getPricesRecipe8();
		
		validateLists(recipe1, recipe1p, errors, 1);
		validateLists(recipe2, recipe2p, errors, 2);
		validateLists(recipe3, recipe3p, errors, 3);
		validateLists(recipe4, recipe4p, errors, 4);
		validateLists(recipe5, recipe5p, errors, 5);
		validateLists(recipe6, recipe6p, errors, 6);
		validateLists(recipe7, recipe7p, errors, 7);
		validateLists(recipe8, recipe8p, errors, 8);
		
		return String.join(", ", errors);
	}
	
	private void validateLists(List<String> prodList, List<String> pricesList, List<String> errors, int ind) {
		if ( !prodList.isEmpty() || ind < 4 ) {
			if ( prodList.size() > 0 && pricesList.size() > 0 && prodList.size() == pricesList.size() ) {
				try { 
					for (int i=0; i < prodList.size(); i++) {
						if ( prodList.get(i).trim().isEmpty() ||  Double.parseDouble(pricesList.get(i)) < 1 ) {
							errors.add(".mm" + ind);
							break;
						}
					}
				}
				catch(Exception ex) {
					errors.add(".mm" + ind);
				}
			}
			else {
				errors.add(".mm" + ind);
				if ( prodList.size() != 0 || pricesList.size() != 0 ) {
					errors.add("equality");
					if ( ind < 4 ) {
						errors.add("first3");
					}
				}
			}
		}
	}
}
