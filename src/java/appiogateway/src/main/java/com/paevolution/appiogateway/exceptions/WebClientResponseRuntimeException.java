package com.paevolution.appiogateway.exceptions;

import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.paevolution.appiogateway.core.dto.MessaggiDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WebClientResponseRuntimeException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 6485492442388441029L;
    private MessaggiDTO messageDTO;
    private WebClientResponseException webClientResponseException;

    public WebClientResponseRuntimeException(WebClientResponseException webClientResponseException, MessaggiDTO messageDTO) {

	super();
	setWebClientResponseException(webClientResponseException);
	setMessageDTO(messageDTO);
    }

    public String getDetailMessage() {

	return errorMessageBuilder(this.webClientResponseException, this.messageDTO);
    }

    private String errorMessageBuilder(WebClientResponseException ex, MessaggiDTO messageDTO) {

	StringBuilder stringBuilder = new StringBuilder();
	String messageID = messageDTO.getMessageId();
	stringBuilder = stringBuilder.append("Non è possibile recuperare lo stato del messaggio con [id=").append(messageID)
		.append("], per l'utente [fiscalCode= ").append(messageDTO.getFiscalCode()).append("] e [idTransazione=")
		.append(messageDTO.getIdTransazione()).append("]. Causa: ").append(ex.getMessage());
	return new String(stringBuilder);
    }
}
