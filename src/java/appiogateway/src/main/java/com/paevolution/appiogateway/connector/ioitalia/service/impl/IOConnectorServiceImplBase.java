package com.paevolution.appiogateway.connector.ioitalia.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.paevolution.appiogateway.connector.ioitalia.client.IOClient;
import com.paevolution.appiogateway.connector.ioitalia.client.model.InlineResponse201;
import com.paevolution.appiogateway.connector.ioitalia.client.model.NewMessage;
import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.core.service.MessaggiService;
import com.paevolution.appiogateway.core.service.ProblemService;
import com.paevolution.appiogateway.exceptions.WebClientExceptionsWrapper;
import com.paevolution.appiogateway.utils.FieldEnum;
import com.paevolution.appiogateway.utils.ModelMapperUtils;
import com.paevolution.appiogateway.utils.StatiMessaggioEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class IOConnectorServiceImplBase {

    private MessaggiService messaggiService;
    private ProblemService problemService;

    @Autowired
    public final void setMessaggiService(MessaggiService messaggiService) {

	this.messaggiService = messaggiService;
    }

    @Autowired
    public final void setProblemService(ProblemService problemService) {

	this.problemService = problemService;
    }

    protected InlineResponse201 postNewMessageWithResponse(MessaggiDTO msgDTO, IOClient ioClient, Map<String, String> headersMap) throws Throwable {

	InlineResponse201 response = null;
	NewMessage newMessageToPost = ModelMapperUtils.newMessageFromMessaggiDTO(msgDTO);
	response = ioClient.postNewMessageWithFiscalCodeInRequestBody(newMessageToPost, headersMap);
	msgDTO.setIdTransazione(response.getId());
	msgDTO.setStatoMessaggio(StatiMessaggioEnum.CONSEGNATO.name());
	updateMessage(msgDTO, new FieldEnum[] { FieldEnum.ID_TRANSAZIONE, FieldEnum.STATO_MESSAGGIO });
	return response;
    }

    protected void updateMessage(MessaggiDTO messageDTO, FieldEnum[] fields) {

	try {
	    messaggiService.multiFieldUpdate(messageDTO, fields);
	} catch (Exception ex) {
	    log.error("updateMessage# {}", ex.getMessage());
	}
    }

    protected void manageWebClientException(MessaggiDTO msgDTO, WebClientResponseException ex) {

	try {
	    populateDTOWithErrorDetails(msgDTO, ex);
	    problemService.save(msgDTO);
	    updateMessage(msgDTO, new FieldEnum[] { FieldEnum.STATO_MESSAGGIO });
	} catch (Exception e) {
	    log.error("manageWebClientException# {}", ex.getMessage());
	}
    }

    protected void manageExceptionRetryPost(MessaggiDTO msgDTO, Exception ex) {

	try {
	    msgDTO.setTitle("Internal Server Error");
	    msgDTO.setDetail(bonificaLunghezzaMessaggioErrore(ex.getMessage()));
	    msgDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	    msgDTO.setStatoMessaggio(StatiMessaggioEnum.RITENTA_INVIO.name());
	    log.info("Messaggio Errore {}", ex.getMessage());
	    problemService.save(msgDTO);
	    updateMessage(msgDTO, new FieldEnum[] { FieldEnum.STATO_MESSAGGIO });
	} catch (Exception e) {
	    log.error("manageException# {}", ex.getMessage());
	}
    }

    protected void manageException(MessaggiDTO msgDTO, Exception ex) {

	try {
	    populateDTOWithErrorDetails(msgDTO, ex);
	    log.info("Messaggio Errore {}", ex.getMessage());
	    problemService.save(msgDTO);
	    updateMessage(msgDTO, new FieldEnum[] { FieldEnum.STATO_MESSAGGIO });
	} catch (Exception e) {
	    log.error("manageException# {}", ex.getMessage());
	}
    }

    protected void saveProblem(MessaggiDTO msgDTO) {

	try {
	    problemService.save(msgDTO);
	    updateMessage(msgDTO, new FieldEnum[] { FieldEnum.STATO_MESSAGGIO });
	} catch (Exception ex) {
	    log.error("saveProblem# {}", ex.getMessage());
	}
    }

    protected void populateDTOWithErrorDetails(MessaggiDTO msgDTO, WebClientResponseException ex) {

	// msgDTO.setStatoMessaggio(StatiMessaggioEnum.ERRORE.name());
	setStatoMessaggio(msgDTO, ex.getStatusCode());
	msgDTO.setTitle("Errore di comunicazione del client dei servizi web (WebClientResponseException)");
	msgDTO.setDetail(bonificaLunghezzaMessaggioErrore(ex.getMessage()));
	msgDTO.setStatusCode(ex.getStatusCode().value());
    }

    protected void populateDTOWithErrorDetails(MessaggiDTO msgDTO, Exception ex) {

	msgDTO.setTitle("Internal Server Error");
	msgDTO.setDetail(bonificaLunghezzaMessaggioErrore(ex.getMessage()));
	msgDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	msgDTO.setStatoMessaggio(StatiMessaggioEnum.ERRORE.name());
    }

    //// Per i messaggi di errore superiori ai 200 caratteri
    private String bonificaLunghezzaMessaggioErrore(String errorMessage) {

	if (StringUtils.defaultString(errorMessage).length() > 200) {
	    return StringUtils.abbreviate(errorMessage, 200);
	}
	return errorMessage;
    }

    protected void setStatoMessaggio(MessaggiDTO msgDTO, HttpStatus status) {

	switch (status) {
	case TOO_MANY_REQUESTS:
	    break;
	case INTERNAL_SERVER_ERROR:
	    break;
	case UNAUTHORIZED:
	case BAD_REQUEST:
	case FORBIDDEN:
	case NOT_FOUND:
	    msgDTO.setStatoMessaggio(StatiMessaggioEnum.ERRORE.name());
	    break;
	default:
	    break;
	}
    }

    protected void throwsWebClientExceptionsWrapper(Throwable ex) throws WebClientExceptionsWrapper {

	if (ex instanceof WebClientResponseException) {
	    //
	    throw new WebClientExceptionsWrapper((WebClientResponseException) ex);
	}
    }

    protected void throwsException(Throwable ex) throws WebClientExceptionsWrapper, Exception {

	if (ex instanceof WebClientResponseException) {
	    throw new WebClientExceptionsWrapper((WebClientResponseException) ex);
	}
	throw new Exception(ex.getMessage());
    }
}
