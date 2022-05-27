package com.paevolution.appiogateway.exceptions;

import com.paevolution.appiogateway.core.dto.MessaggiDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author simone.vernata
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProblemFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 4501425004365409378L;
    private MessaggiDTO messageDTO;
    private String errorTitle;

    public ProblemFoundException(String errorTitle, MessaggiDTO messageDTO) {

	super();
	this.messageDTO = messageDTO;
	this.errorTitle = errorTitle;
    }

    public String getErrorDetail() {

	return errorMessageBuilder(this.messageDTO);
    }

    private String errorMessageBuilder(MessaggiDTO messageDTO) {

	StringBuilder stringBuilder = new StringBuilder();
	String messageID = messageDTO.getMessageId();
	stringBuilder = stringBuilder.append("Tentativo di invio del messaggio con [id=").append(messageID)
		.append("] fallito, per l'utente [fiscalCode= ").append(messageDTO.getFiscalCode()).append("]. Causa: ")
		.append(messageDTO.getDetail());
	return new String(stringBuilder);
    }
}
