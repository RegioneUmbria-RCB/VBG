package com.paevolution.appioproducer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.context.request.RequestContextListener;

import com.paevolution.appioproducer.configuration.AppIOProperties;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AppIOProperties properties;

    @Autowired
    public void setProperties(AppIOProperties properties) {

	this.properties = properties;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	auth.inMemoryAuthentication().withUser(properties.vbgUsername()).password("{noop}" + properties.vbgPassword()).authorities("USER");
    }

    @Bean
    public RequestContextListener requestContextListener() {

	return new RequestContextListener();
    }
}
