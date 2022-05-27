package com.paevolution.appioproducer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class AppIOProperties {

    @Value("${user-services.vbg.username}")
    private String vbgUsername;
    @Value("${user-services.vbg.password}")
    private String vbgPassword;
    @Value("${appiogateway.url-base}")
    private String appiogatewayURL;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    public String vbgUsername() {

	return this.vbgUsername;
    }

    public String vbgPassword() {

	return this.vbgPassword;
    }

    public String appiogatewayURL() {

	return appiogatewayURL;
    }

    public String getDriverClassName() {

	return driverClassName;
    }
}
