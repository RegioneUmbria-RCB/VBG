package it.alveo.ricalcoloaree.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import it.alveo.ricalcoloaree.utils.SoapLoggingInterceptor;

@Configuration
public class SigeproSecurityConfiguration {

    @Bean(name = "securityMarshaller")
    public Jaxb2Marshaller securityMarshaller() {

	Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	marshaller.setContextPath("it.alveo.ricalcoloaree.sigeprosecurity");
	return marshaller;
    }

    @Bean(name = "securityFileWebServiceTemplate")
    @Scope("prototype") // Ogni chiamata creerà una nuova istanza
    public WebServiceTemplate securityFileWebServiceTemplate(String securityUrl) {

	WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
	webServiceTemplate.setMarshaller(securityMarshaller());
	webServiceTemplate.setUnmarshaller(securityMarshaller());
	webServiceTemplate.setInterceptors(new ClientInterceptor[] { new SoapLoggingInterceptor() });
	webServiceTemplate.setDefaultUri(securityUrl);
	return webServiceTemplate;
    }
}
