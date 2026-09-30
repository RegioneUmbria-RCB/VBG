package it.sgp.middleware.security;

import java.io.IOException;

import java.util.List;

import org.apache.wss4j.dom.WSConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurer;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SpringSecurityPasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchemaCollection;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;

import it.sgp.middleware.security.service.UserSecurityService;

@Configuration
@EnableWs
public class AppWebServicesConfigurer implements WsConfigurer {

    @Autowired
    private UserSecurityService userSecurityService;

    @Bean
    SpringSecurityPasswordValidationCallbackHandler callbackHandler(UserSecurityService userSecurityService)
	    throws IOException {

	SpringSecurityPasswordValidationCallbackHandler callbackHandler = new SpringSecurityPasswordValidationCallbackHandler();
	callbackHandler.setUserDetailsService(userSecurityService);
	return callbackHandler;
    }

    @Bean
    PayloadLoggingInterceptor payloadLoggingInterceptor() {

	return new PayloadLoggingInterceptor();
    }

    @Bean
    SpringSecurityPasswordValidationCallbackHandler springSecurityPasswordValidationCallbackHandler() {

	SpringSecurityPasswordValidationCallbackHandler ch = new SpringSecurityPasswordValidationCallbackHandler();
	ch.setUserDetailsService(userSecurityService);
	return ch;
    }

    @Bean
    Wss4jSecurityInterceptor wss4jSecurityInterceptor() {

	Wss4jSecurityInterceptor interceptor = new Wss4jSecurityInterceptor();
	interceptor.setValidationActions(WSConstants.USERNAME_TOKEN_LN);
	interceptor.setValidationCallbackHandler(springSecurityPasswordValidationCallbackHandler());
	return interceptor;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {

	interceptors.add(payloadLoggingInterceptor());
	interceptors.add(wss4jSecurityInterceptor());
    }

    // Soap Factory
    @Bean
    SaajSoapMessageFactory messageFactory() {

	SaajSoapMessageFactory factory = new SaajSoapMessageFactory();
	factory.setSoapVersion(SoapVersion.SOAP_11);
	return factory;
    }

    @Bean
    DefaultWsdl11Definition sigeproSecurity() {

	DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
	definition.setPortTypeName("sigeproSecurity");
	definition.setLocationUri("/services/sigeproSecurity.wsdl");
	definition.setSchemaCollection(schemaCollectionV1());
	return definition;
    }

    @Bean
    XsdSchemaCollection schemaCollectionV1() {

	CommonsXsdSchemaCollection commonsXsdSchemaCollection = new CommonsXsdSchemaCollection(new ClassPathResource("sigeprosecurity.xsd"));
	commonsXsdSchemaCollection.setInline(true);
	return commonsXsdSchemaCollection;
    }

    @Bean
    XsdSchemaCollection schemaCollectionV2() {

	CommonsXsdSchemaCollection commonsXsdSchemaCollection = new CommonsXsdSchemaCollection(new ClassPathResource("sigeprosecurityV2.xsd"));
	commonsXsdSchemaCollection.setInline(true);
	return commonsXsdSchemaCollection;
    }

    @Bean
    DefaultWsdl11Definition sigeproSecurityV2() {

	DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
	definition.setPortTypeName("sigeproSecurityV2");
	definition.setLocationUri("/services-v2/sigeproSecurityV2.wsdl");
	definition.setSchemaCollection(schemaCollectionV2());
	return definition;
    }

    /* BOOT-DOC-ADD Questo serve per attivare la servlet che gestisce gli endpoint di Spring WS */
    @Bean
    ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {

	MessageDispatcherServlet servlet = new MessageDispatcherServlet();
	servlet.setApplicationContext(applicationContext);
	servlet.setTransformWsdlLocations(true);
	return new ServletRegistrationBean<>(servlet, "/services-v2/*", "/services/*", "*.wsdl");
    }
}
