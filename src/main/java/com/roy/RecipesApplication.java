package com.roy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.ServletContextAttributeExporter;

import com.roy.models.Role;
import com.roy.models.Search;
import com.roy.models.User;
import com.roy.repositories.RoleRepository;
import com.roy.repositories.UserRepository;
import com.roy.security.Services.UserService;

@SpringBootApplication
@EnableScheduling
public class RecipesApplication extends SpringBootServletInitializer implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(RecipesApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(RecipesApplication.class);
	}

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserService userService;

	@Override
	public void run(String... arg0) throws Exception {
		for ( File f : new File("images").listFiles()) {
			System.out.println(f.getName());
		}
		
		if ( userService.findByUsername("snakemzm") == null ) {
			createUserWithRoles();
		}
		manageFiles();
	}
	
private void createUserWithRoles() {
		
		Role user_role = new Role(1, "ROLE_USER");
		Role admin_role = new Role(2, "ROLE_ADMIN");
		
		roleRepository.save(user_role);
		roleRepository.save(admin_role);
		
		try {
			User user = userService.findByUsername("snakemzm");
			
			if ( user == null ) {
				user = new User(1, "snakemzm", "123qwaszx", "roi7giladi@gmail.com");
			}
			user.setRoles(new HashSet<>(roleRepository.findAll()));
			user.setPassword("123qwaszx");
			
			userService.save(user);
		} catch(Exception ex) {}
	}
	
	private void manageFiles() {
		
		File dir2 = new File("tmp_images");
		if (dir2.exists())
		try {
			FileUtils.deleteDirectory(dir2);
			dir2.mkdirs();
			}catch (Exception ex) {
				if (!dir2.exists())
					dir2.mkdirs();
			}
		else {
			dir2.mkdirs();
		}
	}
	
	@Controller
	public class errorController implements ErrorController {
		private static final String PATH = "/error";
		
		@RequestMapping(value = PATH)
		public String error(Model model) {
			model.addAttribute("search", new Search());
			try {
				ClassLoader classLoader = getClass().getClassLoader();
				File file = new File(classLoader.getResource("static/photos/error_img_slider").getFile());
				
				final String img_folder = "photos/error_img_slider/";
				
				List<String> images = new ArrayList<String>();
				
				for (File img : file.listFiles() ) {
					images.add(img_folder + img.getName());
				}
				
				if (images.size() != 0) {
					model.addAttribute("images", images);
				}
				
			}catch(Exception ex){}
			
			return "/error/404";
		}
		
		@Override
		public String getErrorPath() {
			return PATH;
		}
		
	}
}
