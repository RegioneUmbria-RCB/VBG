package com.paevolution.appiogateway.connector.ud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Service;

import com.paevolution.appiogateway.core.domain.TipoConnettore;
import com.paevolution.appiogateway.core.dto.ServiziDTO;
import com.paevolution.appiogateway.core.repository.TipoConnettoreRepository;
import com.paevolution.appiogateway.core.service.ServiziService;
import com.paevolution.appiogateway.utils.TipiConnettoreEnum;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@Service("udClientRegistrationRepository")
public class UDClientRegistrationRepository implements ClientRegistrationRepository, Iterable<ClientRegistration>, IReloadableProperties {

    @Value("${ioproxy.scope}")
    private String[] defaultScopes;
    @Value("${ioproxy.token-uri}")
    private String defaultTokenURI;
    private TipoConnettoreRepository tipoConnettoreRepository;
    private ServiziService serviziService;
    private List<ClientRegistration> cachedRegistrationsIterator = null;

    public UDClientRegistrationRepository() {

	log.debug("inizializzazione UDClientRegistrationRepository in corso");
    }

    @Autowired
    public void setServiziService(ServiziService serviziService) {

	this.serviziService = serviziService;
    }

    @Autowired
    public void setTipoConnettoreRepository(TipoConnettoreRepository tipoConnettoreRepository) {

	this.tipoConnettoreRepository = tipoConnettoreRepository;
    }

    @Override
    public Iterator<ClientRegistration> iterator() {

	if (isCachedRegistrationsEmpty()) {
	    Optional<TipoConnettore> opt = tipoConnettoreRepository.findByNome(TipiConnettoreEnum.UD.name());
	    if (opt.isPresent()) {
		List<ServiziDTO> umbriaDigitaleClients = serviziService.findByTipoConnettore(opt.get()).stream()
			.filter(s -> serviziService.isNotBlankOAuth2ClientInfo(s.getClientId(), s.getClientSecret(), s.getClientRegistrationId()))
			.collect(Collectors.toList());
		this.cachedRegistrationsIterator = umbriaDigitaleClients.stream().map(servizio -> getUDClientRegistration(servizio))
			.filter(registration -> registration != null).collect(Collectors.toList());
	    }
	}
	return this.cachedRegistrationsIterator.iterator();
    }

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {

	Iterator<ClientRegistration> iterator = iterator();
	while (iterator.hasNext()) {
	    ClientRegistration cr = iterator.next();
	    if (cr.getRegistrationId().equalsIgnoreCase(registrationId)) {
		return cr;
	    }
	}
	throw new IllegalArgumentException("ClientRegistration non trovata per l'identificativo " + registrationId);
    }

    public ClientRegistration getUDClientRegistration(ServiziDTO servizio) {

	return ClientRegistration.withRegistrationId(servizio.getClientRegistrationId()).clientId(servizio.getClientId())
		.clientSecret(servizio.getClientSecret()).clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
		.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS).scope(defaultScopes).tokenUri(defaultTokenURI).build();
    }

    private boolean isCachedRegistrationsEmpty() {

	if (this.cachedRegistrationsIterator == null) {
	    initializeCachedRegistrationsIterator();
	}
	return this.cachedRegistrationsIterator.isEmpty();
    }

    @Override
    public void clearCachedProperties() {

	initializeCachedRegistrationsIterator();
    }

    private void initializeCachedRegistrationsIterator() {

	this.cachedRegistrationsIterator = new ArrayList<>();
    }
}
