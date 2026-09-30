package com.paevolution.appioproducer.configuration;

import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientCodecCustomizer;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Classe di configurazione di WebClient. Fornisce un meccanismo per personalizzare (customizzare) globalmente tutte le
 * istanze usando l'interfaccia WebClientCustomizer.
 * 
 * Espone un'istanza pre-configurata di WebClient.Builder
 * 
 * @author simone.vernata
 *
 */
//@Configuration(proxyBeanMethods = false)
//@ConditionalOnClass(WebClient.class)
//@AutoConfigureAfter({ CodecsAutoConfiguration.class, ClientHttpConnectorAutoConfiguration.class })
public class WebClientAutoConfiguration {

//    private final WebClient.Builder webClientBuilder;
//
//    /**
//     * Nel costruttore viene iniettata un'istanza di tipo ObjectProvider<WebClientCustomizer>. L'ObjectProvider è in
//     * grado di restiture tutte le istanze degli oggetti dell'interfaccia WebClientCustomizer per personalizzare il
//     * WebClient.Builder.
//     * 
//     * @param customizerProvider
//     */
//    public WebClientAutoConfiguration(ObjectProvider<WebClientCustomizer> customizerProvider) {
//
//	this.webClientBuilder = WebClient.builder();
//	customizerProvider.orderedStream().forEach(customizer -> customizer.customize(this.webClientBuilder));
//    }
//
//    /**
//     * I bean annottati con lo scope di prototype non saranno condivisi dal codice come istanze singole (cioè come
//     * singleton), ma per ogni punto di iniezione, una nuova istanza è restituita.
//     * 
//     * @return
//     */
//    @Bean
//    @Scope("prototype")
//    @ConditionalOnMissingBean
//    public WebClient.Builder webClientBuilder() {
//
//	return this.webClientBuilder.clone();
//    }
//
//    @Configuration(proxyBeanMethods = false)
//    @ConditionalOnBean(CodecCustomizer.class)
//    protected static class WebClientCodecsConfiguration {
//
//	@Bean
//	@ConditionalOnMissingBean
//	@Order(0)
//	public WebClientCodecCustomizer exchangeStrategiesCustomizer(List<CodecCustomizer> codecCustomizers) {
//
//	    return new WebClientCodecCustomizer(codecCustomizers);
//	}
//    }
}
