package com.paevolution.appiogateway.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.web.reactive.function.client.WebClient;

import com.paevolution.appiogateway.configuration.AppIOProperties;
import com.paevolution.appiogateway.core.domain.TipoConnettore;
import com.paevolution.appiogateway.core.dto.ServiziDTO;
import com.paevolution.appiogateway.core.repository.TipoConnettoreRepository;
import com.paevolution.appiogateway.core.service.ServiziService;
import com.paevolution.appiogateway.utils.TipiConnettoreEnum;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class OAuth2ClientConfiguration {

    @Value("${ioproxy.scope}")
    private String[] defaultScopes;
    @Value("${ioproxy.token-uri}")
    private String defaultTokenURI;
    @Autowired
    private AppIOProperties properties;
    private TipoConnettoreRepository tipoConnettoreRepository;
    private ServiziService serviziService;

    @Autowired
    public void setServiziService(ServiziService serviziService) {

	this.serviziService = serviziService;
    }

    @Autowired
    public void setTipoConnettoreRepository(TipoConnettoreRepository tipoConnettoreRepository) {

	this.tipoConnettoreRepository = tipoConnettoreRepository;
    }

    @Primary
    @Bean(name = "udAuthorizedClientService")
    public OAuth2AuthorizedClientService udAuthorizedClientService(
	    @Qualifier("udClientRegistrationRepository") ClientRegistrationRepository udClientRegistrationRepository) {

	return new InMemoryOAuth2AuthorizedClientService(udClientRegistrationRepository);
    }

    @Bean
    public OAuth2AuthorizedClientManager udAuthorizedClientManager(
	    @Qualifier("udClientRegistrationRepository") ClientRegistrationRepository cientRegistrationRepository,
	    @Qualifier("udAuthorizedClientService") OAuth2AuthorizedClientService authorizedClientService) {

	OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();
	AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
		cientRegistrationRepository, authorizedClientService);
	authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
	return authorizedClientManager;
    }

    @Bean
    public WebClient udWebClient(WebClient.Builder webClientBuilder,
	    @Qualifier("udAuthorizedClientManager") OAuth2AuthorizedClientManager authorizedClientManager) {

	ServletOAuth2AuthorizedClientExchangeFilterFunction oauthfilter = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
		authorizedClientManager);
	List<MediaType> acceptHeaders = new ArrayList<>();
	acceptHeaders.add(MediaType.APPLICATION_JSON);
	return webClientBuilder.baseUrl(properties.getUdEndpointURL()).filter(oauthfilter).defaultHeaders(httpHeaders -> {
	    httpHeaders.setAccept(acceptHeaders);
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	}).apply(oauthfilter.oauth2Configuration()).build();
    }
}
