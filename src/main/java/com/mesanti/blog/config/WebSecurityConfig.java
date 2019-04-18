package com.mesanti.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("com.mesanti.blog.config")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	  web.ignoring().antMatchers("/js/**","/css/**");
//	  web.ignoring().antMatchers("/css/**");
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	  return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests()
	    .antMatchers("/").permitAll()
	    .antMatchers("/controlPage/")
	    .hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
	    .and()
	  .formLogin().loginPage("/login").permitAll()
	    .defaultSuccessUrl("/controlPage")
	    .failureUrl("/login?error=true")
	    .and()
	  .logout().permitAll()
	  	.logoutSuccessUrl("/login?logout=true");
	}
	
	// create users and admin
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

	  BCryptPasswordEncoder encoder = passwordEncoder();
	  auth.inMemoryAuthentication() .passwordEncoder(encoder)
	    .withUser("blogUser1").password(encoder.encode("password")).authorities("ROLE_USER")
	    .and()
	    .withUser("blogUser2").password(encoder.encode("password")).authorities("ROLE_USER")
	    .and()
	    .withUser("blogAdmin").password(encoder.encode("password")).authorities("ROLE_ADMIN");
	}
	
}
