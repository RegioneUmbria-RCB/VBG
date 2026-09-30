package com.paevolution.appiogateway.connector.ud.client.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.paevolution.appiogateway.connector.ioitalia.client.model.InlineResponse201;
import com.paevolution.appiogateway.connector.ioitalia.client.model.LimitedProfile;
import com.paevolution.appiogateway.connector.ioitalia.client.model.MessageResponseWithContent;
import com.paevolution.appiogateway.connector.ioitalia.client.model.NewMessage;
import com.paevolution.appiogateway.connector.ioitalia.service.impl.IOConnectorServiceImplBase;
import com.paevolution.appiogateway.connector.ud.client.UDClient;
import com.paevolution.appiogateway.connector.ud.client.service.UDConnectorService;
import com.paevolution.appiogateway.core.domain.Servizi;
import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.core.repository.ServiziRepository;
import com.paevolution.appiogateway.exceptions.MessageNotFoundException;
import com.paevolution.appiogateway.exceptions.WebClientExceptionsWrapper;
import com.paevolution.appiogateway.exceptions.WebClientResponseRuntimeException;
import com.paevolution.appiogateway.utils.FieldEnum;
import com.paevolution.appiogateway.utils.ModelMapperUtils;
import com.paevolution.appiogateway.utils.StatiMessaggioEnum;
import com.paevolution.appiogateway.utils.WebConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UDConnectorServiceImpl extends IOConnectorServiceImplBase implements UDConnectorService {

    private UDClient udClient;
    private ServiziRepository serviziRepository;
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    public UDConnectorServiceImpl(@Qualifier("udWebClient") WebClient webClient) {

	this.udClient = new UDClient(webClient);
    }

    @Autowired
    public void setServiziRepository(ServiziRepository serviziRepository) {

	this.serviziRepository = serviziRepository;
    }

    @Autowired
    public void setClientRegistrationRepository(
	    @Qualifier("udClientRegistrationRepository") ClientRegistrationRepository clientRegistrationRepository) {

	this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Override
    public void process(MessaggiDTO message) {

	tryToPostNewMessage(message);
    }

    @Override
    public void status(MessaggiDTO message) {

	getMessageStatus(message);
    }

    @Override
    public MessaggiDTO getMessageStatus(MessaggiDTO message) {

	MessaggiDTO updatedMessageDTO = getMessageStep(message);
	updatedMessageDTO.setStatoMessaggio(StatiMessaggioEnum.GET_200.name());
	updateMessage(updatedMessageDTO, new FieldEnum[] { FieldEnum.CREATED_AT, FieldEnum.EMAIL_NOTIFICATION, FieldEnum.WEBHOOK_NOTIFICATION,
		FieldEnum.STATUS, FieldEnum.STATO_MESSAGGIO });
	return updatedMessageDTO;
    }

    @Override
    public InlineResponse201 directPOSTMessage(MessaggiDTO message) throws WebClientExceptionsWrapper, Exception {

	InlineResponse201 response = null;
	Boolean isProfileOK = profileStep(message);
	if (isProfileOK != null && isProfileOK.booleanValue()) {
	    try {
		NewMessage messageToPost = ModelMapperUtils.newMessageFromMessaggiDTO(message);
		response = udClient.postNewMessageWithFiscalCodeInRequestBody(messageToPost, getClientRegistrationId(message),
			ocpApimHeaderMap(message.getChiavePrimaria()));
		message.setIdTransazione(response.getId());
		message.setStatoMessaggio(StatiMessaggioEnum.CONSEGNATO.name());
		updateMessage(message, new FieldEnum[] { FieldEnum.ID_TRANSAZIONE, FieldEnum.STATO_MESSAGGIO });
	    } catch (Throwable ex) {
		throwsException(ex);
	    }
	}
	return response;
    }

    @Override
    public MessageResponseWithContent directGETMessage(MessaggiDTO message) {

	// TODO Auto-generated method stub
	return null;
    }

    public void tryToPostNewMessage(MessaggiDTO message) {

	try {
	    checkIfOAuth2ClientPresent(message);
	    logClientRegistrationInfo(getServizio(message), message);
	    Boolean profileStep = profileStep(message);
	    if (profileStep != null && profileStep.booleanValue()) {
		postNewMessageStep(message);
	    }
	} catch (WebClientExceptionsWrapper exc) {
	    manageWebClientException(message, exc.getWebClientResponseException());
	} catch (Exception ex) {
	    manageExceptionRetryPost(message, ex);
	}
    }

    private Map<String, String> ocpApimHeaderMap(String ocpApim) {

	Map<String, String> headersMap = new HashMap<>();
	headersMap.put(WebConstants.OCP_APIM_SUBSCRIPTION_KEY_HEADER, ocpApim);
	return headersMap;
    }

    private Boolean profileStep(MessaggiDTO msgDTO) throws WebClientExceptionsWrapper, Exception {

	Boolean result = null;
	try {
	    LimitedProfile profile = udClient.getUserProfileByFiscalCodeAsPathParameter(msgDTO.getFiscalCode(), getClientRegistrationId(msgDTO),
		    ocpApimHeaderMap(msgDTO.getChiavePrimaria()));
	    // Start Test sender_allowed=false
	    // profile.setSenderAllowed(false);
	    // End Test sender_allowed=false
	    ModelMapperUtils.updateMessaggiDTO(msgDTO, profile);
	    if (!profile.getSenderAllowed()) {
		msgDTO.setStatoMessaggio(StatiMessaggioEnum.SENDER_NOT_ALLOWED.name());
		updateMessage(msgDTO, new FieldEnum[] { FieldEnum.STATO_MESSAGGIO });
	    }
	    updateMessage(msgDTO, new FieldEnum[] { FieldEnum.SENDER_ALLOWED, FieldEnum.PREFERRED_LANGUAGES });
	    result = profile.getSenderAllowed();
	} catch (Throwable ex) {
	    if (ex instanceof WebClientResponseException) {
		if (((WebClientResponseException) ex).getStatusCode().equals(HttpStatus.NOT_FOUND)) {
		    // Non salvare Errore in Messaggi
		    log.warn("Non viene salvato Errore - status code: " + ((WebClientResponseException) ex).getStatusCode().toString());
		    msgDTO.setStatoMessaggio(StatiMessaggioEnum.RITENTA_INVIO.name());
		    updateMessage(msgDTO, new FieldEnum[] { FieldEnum.STATO_MESSAGGIO });
		} else {
		    log.error("Salvataggio Errore - status code: " + ((WebClientResponseException) ex).getStatusCode().toString());
		    throwsWebClientExceptionsWrapper(ex);
		}
		return result;
	    }
	    if (ex instanceof WebClientRequestException) {
		log.warn("Non viene salvato Errore - Exception message: " + ((WebClientRequestException) ex).getMessage());
		// API U.D. Non Raggiungibile
		// Failed To Resolve (api2)	
		// Non salvare Errore in Messaggi
		msgDTO.setStatoMessaggio(StatiMessaggioEnum.RITENTA_INVIO.name());
		updateMessage(msgDTO, new FieldEnum[] { FieldEnum.STATO_MESSAGGIO });
		return result;
	    }
	}
	return result;
    }

    private void postNewMessageStep(MessaggiDTO msgDTO) throws WebClientExceptionsWrapper, Exception {

	try {
	    InlineResponse201 response = null;
	    NewMessage newMessageToPost = ModelMapperUtils.newMessageFromMessaggiDTO(msgDTO);
	    response = udClient.postNewMessageWithFiscalCodeInRequestBody(newMessageToPost, getClientRegistrationId(msgDTO),
		    ocpApimHeaderMap(msgDTO.getChiavePrimaria()));
	    msgDTO.setIdTransazione(response.getId());
	    msgDTO.setStatoMessaggio(StatiMessaggioEnum.CONSEGNATO.name());
	    updateMessage(msgDTO, new FieldEnum[] { FieldEnum.ID_TRANSAZIONE, FieldEnum.STATO_MESSAGGIO });
	} catch (Throwable ex) {
	    if (ex instanceof MessageNotFoundException) {
		log.error("postNewMessageStep# {}", ex.getMessage());
	    }
	    throwsException(ex);
	}
    }

    private MessaggiDTO getMessageStep(MessaggiDTO messageDTO) {

	try {
	    // Start Test Errore 401 Unauthorized
	    // messageDTO.setChiavePrimaria("f1ed6d519b3e4374a0193d4a80cb79f5");
	    // End Test Errore 401 Unauthorized
	    checkIfOAuth2ClientPresent(messageDTO);
	    logClientRegistrationInfo(getServizio(messageDTO), messageDTO);
	    MessageResponseWithContent responseWithContent = this.udClient.getMessage(messageDTO.getFiscalCode(), messageDTO.getIdTransazione(),
		    getClientRegistrationId(messageDTO), ocpApimHeaderMap(messageDTO.getChiavePrimaria()));
	    ModelMapperUtils.updateMessaggiDTOWithNotificationStatus(messageDTO, responseWithContent);
	} catch (Throwable ex) {
	    if (ex instanceof WebClientResponseException) {
		// Aggiorna lo stato del Messaggio e inserisce un record nella tabella Problem
		manageWebClientException(messageDTO, (WebClientResponseException) ex);
		throw new WebClientResponseRuntimeException((WebClientResponseException) ex, messageDTO);
	    } else if (ex instanceof Exception) {
		manageException(messageDTO, (Exception) ex);
		throw new RuntimeException(ex.getMessage());
	    }
	}
	return messageDTO;
    }

    private String getClientRegistrationId(MessaggiDTO msgDTO) {

	return getServizio(msgDTO).getClientRegistrationId();
    }

    private Servizi getServizio(MessaggiDTO msgDTO) {

//	Optional<Servizi> serviziOpt = this.serviziRepository.findByIdcomuneAndCodicecomuneAndSoftware(msgDTO.getIdcomune(), msgDTO.getCodicecomune(),
//		msgDTO.getSoftware());
	Optional<Servizi> serviziOpt = this.serviziRepository.findByIdServizio(msgDTO.getIdServizio());
	return serviziOpt.get();
    }

    private void checkIfOAuth2ClientPresent(MessaggiDTO msgDTO) throws Exception {

	ClientRegistration cr = this.clientRegistrationRepository.findByRegistrationId(getClientRegistrationId(msgDTO));
	if (cr == null) {
	    throw new Exception("Client OAuth2 con registrationId=" + getClientRegistrationId(msgDTO) + " non trovato.");
	}
    }

    private void logClientRegistrationInfo(Servizi servizio, MessaggiDTO msgDTO) {

	log.info("logClientRegistrationInfo# Messaggio da processare [id={}, idcomune={}]",
		new Object[] { msgDTO.getId(), msgDTO.getIdcomune()});
	log.debug(
		"logClientRegistrationInfo# Servizio recuperato [id={}, idcomune={}, nome_servizio={}, connettore={}, clientRegistrationId={}]",
		new Object[] { servizio.getId(), servizio.getIdcomune(), 
			servizio.getNomeServizio(), servizio.getTipoConnettore().getNome(), servizio.getClientRegistrationId() });
	ClientRegistration cr = this.clientRegistrationRepository.findByRegistrationId(servizio.getClientRegistrationId());
	log.debug("logClientRegistrationInfo# Client Oauth2 recuperato [clientName={}] per il servizio con [clientRegistrationId={}]",
		new Object[] { cr.getClientName(), servizio.getClientRegistrationId() });
    }
}