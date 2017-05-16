package com.roy.security;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.multipart.support.MultipartFilter;

@Configuration
public class SpringSecurityConfig extends AbstractSecurityWebApplicationInitializer {
	
	
    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        insertFilters(servletContext, new MultipartFilter());
        // insertFilters(servletContext, new CharacterEncodingFilter());
    }
    
	/*
 	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}
	 */
    
    
}


