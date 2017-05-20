package com.roy.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.roy.models.Menu;
import com.roy.repositories.MenusRepository;

@Service
public class MenusService {
	
	@Autowired
	private MenusRepository menusRepo;
	
	public List<Menu> getAllPublicMenus() {
		List<Menu> menus = new ArrayList<>();
		menusRepo.findByPublished(1).forEach(menus::add);
		return menus;
	}
	
	public List<Menu> getUserMenusLiked(long id) {
		List<Menu> menus = new ArrayList<>();
		menusRepo.findByUsersLiked(id).forEach(menus::add);
		return menus;
	}
	
	public List<Menu> getUserMenus(long id) {
		List<Menu> menus = new ArrayList<>();
		menusRepo.findByUserId(id).forEach(menus::add);
		return menus;
	}
	
	public Menu findMenuById(long id) {
		return menusRepo.findOne(id);
	}
	
	public void saveMenu(Menu menu) {
		menusRepo.save(menu);
	}
	
	public void deleteMenu(long id) {
		menusRepo.delete(id);
	}
	
	public List<Menu> getAllReportedMenus() {
		List<Menu> menus = new ArrayList<>();
		menusRepo.findByReported(true).forEach(menus :: add);
		return menus;
	}
	
}
