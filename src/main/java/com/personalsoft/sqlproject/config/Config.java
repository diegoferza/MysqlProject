package com.personalsoft.sqlproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class Config implements WebMvcConfigurer {
	
	protected void configure(final HttpSecurity http) throws Exception {
	    http
	        .authorizeRequests().antMatchers("/","/index").permitAll().anyRequest().authenticated().and()
	        .formLogin()
	        .loginPage("/index.html")
	        .failureUrl("/error.html")
	      .and()
	        .logout()
	        .logoutSuccessUrl("/index.html");
	}
	
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/index").setViewName("index");
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}

}
