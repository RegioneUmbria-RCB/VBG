package com.paevolution.appiogateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.context.request.RequestContextListener;

import com.paevolution.appiogateway.configuration.AppIOProperties;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AppIOProperties properties;
    private ClientRegistrationRepository clientRegistratioRepository;
    private OAuth2AuthorizedClientService clientService;

    @Autowired
    public void setClientRegistratioRepository(
	    @Qualifier("udClientRegistrationRepository") ClientRegistrationRepository clientRegistratioRepository) {

	this.clientRegistratioRepository = clientRegistratioRepository;
    }

    @Autowired
    public void setClientService(@Qualifier("udAuthorizedClientService") OAuth2AuthorizedClientService clientService) {

	this.clientService = clientService;
    }

    @Autowired
    public void setProperties(AppIOProperties properties) {

	this.properties = properties;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	auth.inMemoryAuthentication().withUser(properties.vbgUsername()).password("{noop}" + properties.vbgPassword()).authorities("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

	// http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic().and()
	// .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	http.csrf().disable().authorizeRequests().antMatchers("/messaggi/").permitAll().anyRequest().authenticated().and().httpBasic().and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.oauth2Client(oauth2 -> oauth2.clientRegistrationRepository(clientRegistratioRepository).authorizedClientService(clientService));
    }

    @Bean
    public RequestContextListener requestContextListener() {

	return new RequestContextListener();
    }
}
