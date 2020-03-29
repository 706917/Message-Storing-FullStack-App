/*
 * The class to configure the authentication and authorization
 */

package alexlab.MessageStoringFullStackApp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true, proxyTargetClass=true)
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{
	
	// Inject spring-core-interface instance to provide predefined methods to loads users data
	@Autowired
	private UserDetailsService customUserDetailsService;
	
	// Inject javax-interface instance to provide predefined method of connection to the database
	@Autowired
	private DataSource dataSource;
	
	// Define method that implements interface for password encoding
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	};
	
	//Configure an Authentication manager out of custom UserDetailsService and PasswordEncoder
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth
		.userDetailsService(customUserDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	
	
	// Configure Authorization for http requests
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.headers()//Adds the Security headers to the response.
			.frameOptions().sameOrigin() // Specify to allow any request that comes from the same origin to frame this application.
				.and() //Return the SecurityBuilder when done using the SecurityConfigurer. This is useful for method chaining.
					.authorizeRequests() //Allows restricting access based upon the HttpServletRequest using. 
						.antMatchers("/resources/**", "/webjars/**", "/assets/**").permitAll() // Specifies that URLs are allowed by anyone.
						.antMatchers("/").permitAll()
						.antMatchers("/admin/**").hasRole("ADMIN")
						.anyRequest().authenticated() // Specifies that URLs are allowed by any authenticated user.
						.and()
					.formLogin() //Specifies to support form based authentication.
						.loginPage("/login") // the URL to send users to if login is required.
						.defaultSuccessUrl("/home") //Specifies where users will go after authenticating successfully
						.failureUrl("/login?error") //The URL to send users if authentication fails
						.permitAll()
						.and()
					.logout() //logout support.
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //The RequestMatcher that triggers log out to occur. 
						.logoutSuccessUrl("/login?logout") //The RequestMatcher that triggers log out to occur. 
						.deleteCookies("my-remember-me-cookie") // Specifies the names of cookies to be removed on logout success. 
							.permitAll()
							.and()
						.rememberMe() //Allows configuring of Remember Me authentication. 
							.rememberMeCookieName("my-remember-me-cookie") //The name of cookie which store the token for remember me authentication
							.tokenRepository(persistentTokenRepository()) // Specifies the PersistentTokenRepository to use. 
							.tokenValiditySeconds(24*60*60) // Specifies how long (in seconds) a token is valid for
							.and()
						.exceptionHandling();
	}

	// JDBC based persistent login token repository implementation.
	private PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		tokenRepositoryImpl.setDataSource(dataSource);
		return tokenRepositoryImpl;
	}	
	

}
