package com.roy.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roy.models.Ingridient;
import com.roy.repositories.IngridientsRepository;

@Service
public class IngridientsService {

	@Autowired
	private IngridientsRepository ingRepo;

	public List<Ingridient> getAllIngridients() {
		List<Ingridient> ingridients = new ArrayList<>();
		ingRepo.findAll().forEach(ingridients::add);
		return ingridients;
	}
	
}
