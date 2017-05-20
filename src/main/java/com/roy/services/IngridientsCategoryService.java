package com.roy.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roy.models.IngridientCategory;
import com.roy.repositories.IngridientsCategoryRepository;

@Service
public class IngridientsCategoryService {

	@Autowired
	private IngridientsCategoryRepository ingCategoryRepo;

	public List<IngridientCategory> getAllIngridients() {
		List<IngridientCategory> cat = new ArrayList<>();
		ingCategoryRepo.findAll().forEach(cat::add);
		return cat;
	}
	
	public void addCategory(IngridientCategory ic) {
		ingCategoryRepo.save(ic);
	}
	
	public void deleteCategory(String name) {
		ingCategoryRepo.delete(ingCategoryRepo.findByName(name));
	}
	
}
