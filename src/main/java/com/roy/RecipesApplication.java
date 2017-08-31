package com.roy;

import java.io.File;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.roy.models.Role;
import com.roy.models.User;
import com.roy.repositories.RoleRepository;
import com.roy.security.Services.UserService;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class RecipesApplication extends SpringBootServletInitializer implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(RecipesApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(RecipesApplication.class);
	}
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... arg0) throws Exception {
		manageFiles();
		createRoles();
	}
	
private void createRoles() {
		
		Role user_role = new Role(1, "ROLE_USER");
		Role admin_role = new Role(2, "ROLE_ADMIN");
		
		roleRepository.save(user_role);
		roleRepository.save(admin_role);
		
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
}
