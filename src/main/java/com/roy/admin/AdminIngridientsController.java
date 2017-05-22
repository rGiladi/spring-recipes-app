package com.roy.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.roy.models.Ingridient;
import com.roy.models.IngridientCategory;
import com.roy.models.Search;
import com.roy.repositories.IngridientsCategoryRepository;
import com.roy.repositories.IngridientsRepository;
import com.roy.services.IngridientsCategoryService;

@Controller
public class AdminIngridientsController {
	
	@Autowired
	private IngridientsCategoryService ingCatService;
	
	@Autowired
	private IngridientsCategoryRepository ingCatRepo;
	
	@Autowired
	private IngridientsRepository ingRepo;
	
	@RequestMapping(value="/admin/ingridients", method=RequestMethod.GET)
	public String editIngridients(Model model) {
		model.addAttribute("categories", ingCatService.getAllIngridients() );
		model.addAttribute("search", new Search());
		return "admin/ingridients";
	}
	
	@RequestMapping(value="/admin/add_category", method=RequestMethod.POST)
	@ResponseBody IngridientCategory addCategory(@RequestBody IngridientCategory ic) {
		IngridientCategory ic_dupl = ingCatRepo.findByName(ic.getName());
		if ( ic_dupl == null ) {
			ingCatService.addCategory(ic);
			return ingCatRepo.findByName(ic.getName());
		}
		else {
			return null;
		}
	}
	
	@RequestMapping(value="/admin/add_ingridient", method=RequestMethod.POST)
	@ResponseBody Ingridient addIngridient(@RequestBody Ingridient ing, @RequestParam(name="category") long category) {
		ing.setIngridientCategory(ingCatRepo.findOne(category));
		if (ing.getIngridientCategory() != null) {
			ingRepo.save(ing);
			ing.setIngridientCategory(null);
			return ing;
		} else {
			return null;
		}
		
	}
	
	@RequestMapping(value="/admin/handleRemoveCategory", method=RequestMethod.POST)
	@ResponseBody Boolean handleIngRemove(@RequestParam(name="name") String name) {
		List<Ingridient> ingridients = ingRepo.findByIngridientCategory(ingCatRepo.findByName(name));
		if (!ingridients.isEmpty()) {
			for(Ingridient i : ingridients ) {
				ingRepo.delete(i);
			}
		}
		ingCatService.deleteCategory(name);
		return true;
	}
	
	@RequestMapping(value="/admin/handleRemoveIngridient", method=RequestMethod.POST)
	@ResponseBody Boolean handleIngRemoveIngridient(@RequestParam(name="id") long id) {
		ingRepo.customDelete(id);
		return true;
	}
	
}
