package it.sgp.middleware.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import it.sgp.middleware.security.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class AppWebConfiguration {

	//MI SERVE PER L'AUTH REST BEARER, SENNO' MI RIMANDA AL FORM DELLA LOGIN
	@Bean
	public AuthenticationEntryPoint restAuthenticationEntryPoint() {
	    return (request, response, authException) -> {
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.setContentType("application/json");
	        response.getWriter().write("{\"error\": \"Unauthorized\"}");
	    };
	}
	
	@Bean
	@Order(1)
	public SecurityFilterChain restSecurityFilterChain(HttpSecurity http, LoginService loginservice) throws Exception {

		http.securityMatcher("/rest-services/**").csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authz -> authz.anyRequest().permitAll())
				.addFilterBefore(new CustomTokenAuthenticationFilter(loginservice),
						org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(ex -> ex.authenticationEntryPoint(restAuthenticationEntryPoint()));

		return http.build();
	}
	//END
	
    @Bean
    @Order(2)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	// Customize the application security ...
	// http.requiresChannel((channel) -> channel.anyRequest().requiresSecure());
	// http.csrf(Customizer.withDefaults()) //
	http.csrf((csrf) -> csrf.ignoringRequestMatchers("/services/**", "/services-v2/**", //
		"/actuator/**") /* BOOT-DOC-ADD DISABILITA CORS SU CHIAMATE SOAP IGNORA I PERCORSI DOVE SONO ESPOSTI GLI ENDPOINT */
	)//
		.authorizeHttpRequests((authorize) -> authorize //
			.requestMatchers("/login").permitAll() //
			.requestMatchers("/css/**").permitAll() //
			.requestMatchers("/images/**").permitAll() //
			.requestMatchers("/scripts/**").permitAll() //
			.requestMatchers("/index.jsp").permitAll() //
			.requestMatchers("/welcome/login.htm").permitAll() //
			.requestMatchers("/services/**").permitAll() //
			.requestMatchers("/services-v2/**").permitAll() //
			.requestMatchers("/axisservices/**").permitAll() //
			.requestMatchers("/servlet/**").permitAll() //
			.requestMatchers("/upgrade/**").permitAll() //
			.requestMatchers("/scripts-upgr/**").permitAll() //
			.requestMatchers("/css-upgr/**").permitAll() //
			.requestMatchers("/actuator/**").permitAll() //
			// .requestMatchers("/**.htm").authenticated() //
			.anyRequest().authenticated() //
		) //
		.headers(header -> header.xssProtection(Customizer.withDefaults())
		// .contentSecurityPolicy(cs -> cs.policyDirectives("script-src 'self' 'sha256-UEBQPz1gmQ2pXvXQlW97uypj7e1gO/fS+eKaAyDb0PQ='"))
		)
		//.httpBasic(Customizer.withDefaults()) //
		.formLogin(Customizer.withDefaults());
	return http.build();
    }

    /*
     * TODO ATTIVANDO QUESTO FILTRO ESISTE IL PROBLEMA CHE POI QUANDO SALVO MI FA ESCAPE DI & CON &amp; 
     *
    //    @Bean
    //    FilterRegistrationBean<XSSFilter> filterRegistrationBean() {
    //    
    //    FilterRegistrationBean<XSSFilter> registrationBean = new FilterRegistrationBean<>();
    //    registrationBean.setFilter(new XSSFilter());
    //    registrationBean.addUrlPatterns("/*");
    //    return registrationBean;
    //    }
    */
    @Bean
    PasswordEncoder passwordEncoder() {

	return new MessageDigestPasswordEncoder("md5");
    }

    @Bean
    AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {

    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }
}
