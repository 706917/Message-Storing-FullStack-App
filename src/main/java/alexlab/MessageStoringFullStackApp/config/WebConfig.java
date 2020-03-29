/*
 * The class to provide MVC configuration
 */

package alexlab.MessageStoringFullStackApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;


@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	// Inject instance of strategy interface for resolving messages, with support for the parameterization and internationalization of such messages.
	@Autowired
	private MessageSource messageSource;

	// Configure simple automated controllers pre-configured with the response status code and/or a view to render the response body. 
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		// Map pre-configured controllers to the given URL path in order to render a response with a pre-configured status code and view. 
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/home").setViewName("userhome");
		registry.addViewController("/admin/home").setViewName("adminhome");
		registry.addViewController("/403").setViewName("403");			
	}
	

	// Implement method to provide validation of message
	@Override
	public Validator getValidator() {

		LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
		factory.setValidationMessageSource(messageSource);
		return factory;
	}
	
	// Register SpringSecurityDialect to enable using the Thymeleaf Spring Security dialect.
	@Bean
	public SpringSecurityDialect securityDialect() {
		return new SpringSecurityDialect();
	}
	
	
	
	
	
}
