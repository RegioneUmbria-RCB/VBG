package com.paevolution.appiogateway.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class AppIOProperties {

    @Value("${user-services.vbg.username}")
    private String vbgUsername;
    @Value("${user-services.vbg.password}")
    private String vbgPassword;
    /// Per i test impostare una primary-key di IOItalia
    private String ioAPIPrimaryKey;
    @Value("${developers-italia.server-endpoint-url}")
    private String ioAPIEndpointURL;
    @Value("${ud-connector.sandbox-endpoint-url}")
    private String udEndpointURL;
    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {

	this.environment = environment;
    }

    public String vbgUsername() {

	return this.vbgUsername;
    }

    public String vbgPassword() {

	return this.vbgPassword;
    }

    public String getIOAPIPrimaryKey() {

	return this.ioAPIPrimaryKey;
    }

    public String getIOAPIEndpointURL() {

	return this.ioAPIEndpointURL;
    }

    public String getUdEndpointURL() {

	return udEndpointURL;
    }
}
