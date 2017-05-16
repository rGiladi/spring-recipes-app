package com.roy.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.roy.models.Search;
import com.roy.models.User;
import com.roy.security.Services.SecurityService;
import com.roy.security.Services.UserService;
import com.roy.security.Validator.UserValidator;



@Controller
public class SecurityController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserValidator userValidator;
	
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
		if ( SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken ) {
        model.addAttribute("userForm", new User());

        return "security_register";

		}
		return "redirect:/";
	}
	
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@Valid @ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "security_register";
        }
 
        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
    	if ( SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken ) {
	        if (error != null) {
	            model.addAttribute("error", "שם המשתמש או הסיסמא לא נכונים");
	        }
	
	        if (logout != null) {
	            model.addAttribute("message", "להתראות");
	        }
	
	        return "security_login";
    	}
    	return "redirect:/";
    }
}
