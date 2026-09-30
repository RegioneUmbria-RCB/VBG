package it.alveo.segnalazioniproxy.configurations;

import it.alveo.segnalazioniproxy.clients.GestoreFileClient;
import it.alveo.segnalazioniproxy.utils.SoapLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

@Configuration
public class GestoreFileConfiguration {

    @Value("${gestorefile.url}")
    private String urlOggettiService;


    @Bean
    public GestoreFileClient gestoreFileClient(@Qualifier("gestoreFileWebServiceTemplate") WebServiceTemplate gestoreFileWebServiceTemplate) {
        GestoreFileClient gestoreFileClient = new GestoreFileClient();
        gestoreFileClient.setWebServiceTemplate(gestoreFileWebServiceTemplate); // Imposta il template specifico
        return gestoreFileClient;
    }

    @Bean(name = "gestoreFileMarshaller")
    public Jaxb2Marshaller gestoreFileMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // Package che deve corrispondere alla configurazione del contesto JAX-WS
        marshaller.setContextPath("it.alveo.oggetti");
        marshaller.setMtomEnabled(true);
        return marshaller;
    }

    @Bean(name = "gestoreFileWebServiceTemplate")
    public WebServiceTemplate gestoreFileWebServiceTemplate(@Qualifier("gestoreFileMarshaller") Jaxb2Marshaller gestoreFileMarshaller) {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(gestoreFileMarshaller);
        webServiceTemplate.setUnmarshaller(gestoreFileMarshaller);
        webServiceTemplate.setInterceptors(new ClientInterceptor[]{new SoapLoggingInterceptor()});
        webServiceTemplate.setDefaultUri(urlOggettiService);
        return webServiceTemplate;
    }

}
