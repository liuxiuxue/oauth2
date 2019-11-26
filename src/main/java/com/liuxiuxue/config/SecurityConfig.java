package com.liuxiuxue.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("liuxiuxue")
		.password("123456")
		.roles("USER");
		auth.inMemoryAuthentication().withUser("liuxiaomei")
		.password("123456")
		.roles("ADMIN");
		auth.inMemoryAuthentication().withUser("zhangsan")
		.password("123456")
		.roles("ADMIN","USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/login","/oauth/authorize").permitAll() // 开放授权码code请求的路径
			.anyRequest().authenticated()
			.and()
		.formLogin();
	}
	
	


}
