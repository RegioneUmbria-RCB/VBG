package it.alveo.segnalazioniproxy.configurations;

import it.alveo.segnalazioniproxy.utils.SoapLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

@Configuration
public class StcClientConfiguration {

    @Bean(name = "stcMarshaller")
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setMtomEnabled(true);
        marshaller.setContextPath("it.alveo.stc");
        return marshaller;
    }

    @Bean(name = "stcWebServiceTemplate")
    @Scope("prototype") // Ogni chiamata creerà una nuova istanza
    public WebServiceTemplate stcWebServiceTemplate(String stcUrl) {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(marshaller());
        webServiceTemplate.setUnmarshaller(marshaller());
        webServiceTemplate.setInterceptors(new ClientInterceptor[]{new SoapLoggingInterceptor()});
        webServiceTemplate.setDefaultUri(stcUrl);
        return webServiceTemplate;
    }

}
