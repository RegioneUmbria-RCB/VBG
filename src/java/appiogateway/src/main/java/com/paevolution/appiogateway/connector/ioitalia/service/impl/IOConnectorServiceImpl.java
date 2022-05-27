package com.paevolution.appiogateway.connector.ioitalia.service.impl;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.paevolution.appiogateway.connector.ioitalia.client.IOClient;
import com.paevolution.appiogateway.connector.ioitalia.client.model.InlineResponse201;
import com.paevolution.appiogateway.connector.ioitalia.client.model.LimitedProfile;
import com.paevolution.appiogateway.connector.ioitalia.client.model.MessageResponseWithContent;
import com.paevolution.appiogateway.connector.ioitalia.service.IOConnectorService;
import com.paevolution.appiogateway.core.dto.MessaggiDTO;
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
public class IOConnectorServiceImpl extends IOConnectorServiceImplBase implements IOConnectorService {

    private WebClient webClient;
    private IOClient ioClient;

    public IOConnectorServiceImpl() {

    }

    @Autowired
    public IOConnectorServiceImpl(@Qualifier("ioAPIBackendWebClient") WebClient webClient) {

	setWebClient(webClient);
	this.ioClient = new IOClient(this.webClient);
    }

    public void setWebClient(WebClient webClient) {

	this.webClient = webClient;
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

    public void tryToPostNewMessage(MessaggiDTO message) {

	try {
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

    @Override
    public InlineResponse201 directPOSTMessage(MessaggiDTO message) throws WebClientExceptionsWrapper, Exception {

	InlineResponse201 result = null;
	Boolean isProfileOK = profileStep(message);
	if (isProfileOK != null && isProfileOK.booleanValue()) {
	    try {
		result = postNewMessageWithResponse(message, this.ioClient, httpHeaders(message.getChiavePrimaria()));
	    } catch (Throwable ex) {
		///throwsWebClientExceptionsWrapper(ex);
		throwsException(ex);
	    }
	}
	return result;
    }

    @Override
    public MessageResponseWithContent directGETMessage(MessaggiDTO message) {

	// TODO Auto-generated method stub
	return null;
    }

    private Boolean profileStep(MessaggiDTO msgDTO) throws WebClientExceptionsWrapper, Exception {

	Boolean result = null;
	try {
	    LimitedProfile profile = ioClient.getUserProfileByFiscalCodeAsPathParameter(msgDTO.getFiscalCode(),
		    httpHeaders(msgDTO.getChiavePrimaria()));
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
	    if (ex instanceof MessageNotFoundException) {
		log.error("profileStep# {}", ex.getMessage());
	    }
	    //throwsWebClientExceptionsWrapper(ex);
	    throwsException(ex);
	}
	return result;
    }

    private void postNewMessageStep(MessaggiDTO msgDTO) throws WebClientExceptionsWrapper, Exception {

	try {
	    /*
	     * NewMessage newMessageToPost =
	     * ModelMapperUtils.newMessageFromMessaggiDTO(msgDTO); InlineResponse201
	     * response =
	     * ioClient.postNewMessageWithFiscalCodeInRequestBody(newMessageToPost,
	     * httpHeaders(msgDTO.getChiavePrimaria()));
	     * msgDTO.setIdTransazione(response.getId());
	     * msgDTO.setStatoMessaggio(StatiMessaggioEnum.CONSEGNATO.name());
	     * updateMessage(msgDTO, new FieldEnum[] { FieldEnum.ID_TRANSAZIONE,
	     * FieldEnum.STATO_MESSAGGIO });
	     */
	    postNewMessageWithResponse(msgDTO, this.ioClient, httpHeaders(msgDTO.getChiavePrimaria()));
	} catch (Throwable ex) {
	    if (ex instanceof MessageNotFoundException) {
		log.error("postNewMessageStep# {}", ex.getMessage());
	    }
	    ////throwsWebClientExceptionsWrapper(ex);
	    throwsException(ex);
	}
    }

    private MessaggiDTO getMessageStep(MessaggiDTO messageDTO) {

	try {
	    // Start Test Errore 401 Unauthorized
	    // messageDTO.setChiavePrimaria("f1ed6d519b3e4374a0193d4a80cb79f5");
	    // End Test Errore 401 Unauthorized
	    MessageResponseWithContent responseWithContent = this.ioClient.getMessage(messageDTO.getFiscalCode(), messageDTO.getIdTransazione(),
		    httpHeaders(messageDTO.getChiavePrimaria()));
	    ModelMapperUtils.updateMessaggiDTOWithNotificationStatus(messageDTO, responseWithContent);
	} catch (Throwable ex) {
	    if (ex instanceof WebClientResponseException) {
		// Aggiorna lo stato del Messaggio e inserisce un record nella tabella Problem
		manageWebClientException(messageDTO, (WebClientResponseException) ex);
		throw new WebClientResponseRuntimeException((WebClientResponseException) ex, messageDTO);
	    }
	}
	return messageDTO;
    }

    @Override
    public Map<String, String> httpHeaders(String ocpApim) {

	Map<String, String> headersMap = new HashedMap<String, String>();
	headersMap.put(WebConstants.OCP_APIM_SUBSCRIPTION_KEY_HEADER, ocpApim);
	return headersMap;
    }
}
