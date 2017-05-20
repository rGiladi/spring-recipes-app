package com.roy.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String registration(@Valid @ModelAttribute("userForm") User userForm, BindingResult bindingResult
    		,@RequestParam("g-recaptcha-response") String recaptcha) {
    	
    	userForm.setUsername(userForm.getUsername().trim());
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "security_register";
        }
    	
    	if ( verifyRecaptcha(recaptcha) ) {
	        userService.save(userForm);
	        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
	        return "redirect:/";
    	}
    	else {
    		return "security_register";
    	}
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
    
    
    private Boolean verifyRecaptcha(String g_response) {
    	
    	try {
    	String USER_AGENT = "Mozilla/5.0";
    	URL url = new URL("https://www.google.com/recaptcha/api/siteverify");
    	String SECRET = "6LeqzSEUAAAAAIl5-_uEBVn1TvC-_0-NS_KgaOnj";
    	String POST_PARAMS = "secret=" + SECRET + "&response=" + g_response;
    	
    	HttpURLConnection con = (HttpURLConnection) url.openConnection();
    	con.setRequestMethod("POST");
    	con.setRequestProperty("User-Agent", USER_AGENT);
    	con.setDoOutput(true);
    	OutputStream os = con.getOutputStream();
    	os.write(POST_PARAMS.getBytes());
    	os.flush();
    	os.close();
    	
    	int responseCode = con.getResponseCode();
    	
    	
    	if ( responseCode == HttpURLConnection.HTTP_OK) {
    		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    		String inputLine;
    		StringBuffer response = new StringBuffer();
    		
    		while ((inputLine = in.readLine()) != null) {
    			response.append(inputLine);
    		}
    		
    		in.close();
    		
    		try {
    			JSONObject json = new JSONObject(response.toString());
    			if (json.getBoolean("success")) {
    				return true;
    			}
    			else {
    				return false;
    			}
    		}catch(Exception ex){
    			return false;
    		}
    	}
    	
    	return false;
    	}catch(Exception ex) {
    		return false;
    	}
    }
}
