package com.iktpreobuka.e_diary.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity (securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationEntryPoint authEntryPoint;

	@Autowired
	private DataSource dataSource;

	@Value("${spring.queries.users-query}")
	private String userQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic()
				.authenticationEntryPoint(authEntryPoint);
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.inMemoryAuthentication().withUser("user").password("pass").roles("admin");

		auth.jdbcAuthentication().usersByUsernameQuery(userQuery).authoritiesByUsernameQuery(rolesQuery)
				.passwordEncoder(passwordEncoder()).dataSource(dataSource);

	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}
