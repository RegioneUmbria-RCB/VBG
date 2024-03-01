package com.paevolution.appiogateway.web.controllers.api;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.paevolution.appiogateway.connector.ioitalia.service.IOConnectorService;
import com.paevolution.appiogateway.connector.ud.client.service.UDConnectorService;
import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.core.dto.ProblemDTO;
import com.paevolution.appiogateway.core.service.MessaggiService;
import com.paevolution.appiogateway.core.service.ProblemService;
import com.paevolution.appiogateway.events.ReceivedMessageEvent;
import com.paevolution.appiogateway.events.interfaces.IEventPublisher;
import com.paevolution.appiogateway.exceptions.DataIntegrationException;
import com.paevolution.appiogateway.exceptions.model.ErrorResponse;
import com.paevolution.appiogateway.utils.FieldEnum;
import com.paevolution.appiogateway.utils.ModelMapperUtils;
import com.paevolution.appiogateway.utils.StatiMessaggioEnum;
import com.paevolution.appiogateway.web.model.request.MessaggiRequest;
import com.paevolution.appiogateway.web.model.response.CreatedMessageResponse;
import com.paevolution.appiogateway.web.model.response.StatusMessageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/messages", produces = "application/json")
@CrossOrigin(origins = "*")
public class ApiRESTController {

    private static final String IOITALIA = "IOITALIA";
    private static final String UD = "UD";
    private MessaggiService messaggiService;
    private UDConnectorService udConnectorService;
    private IOConnectorService ioConnectorService;
    private ProblemService problemService;
    private IEventPublisher publisher;

    public ApiRESTController() {

    }

    public ApiRESTController(MessaggiService messaggiService) {

	setMessaggiService(messaggiService);
    }

    @Autowired
    public void setMessaggiService(MessaggiService messaggiService) {

	this.messaggiService = messaggiService;
    }

    @Autowired
    public void setPublisher(@Qualifier("eventPublisher") IEventPublisher publisher) {

	this.publisher = publisher;
    }

    @Autowired
    public void setUdConnectorService(UDConnectorService udConnectorService) {

	this.udConnectorService = udConnectorService;
    }

    @Autowired
    public void setIoConnectorService(IOConnectorService ioConnectorService) {

	this.ioConnectorService = ioConnectorService;
    }

    @Autowired
    public void setProblemService(ProblemService problemService) {

	this.problemService = problemService;
    }

    @Operation(summary = "Submit a Message passing the user fiscal_code in the request body", description = "Submits a message to a user.\r\n"
	    + "On error, the reason is returned in the response payload.\r\n"
	    + "In order to call submitMessageforUser, before sending any message,\r\n"
	    + "the sender MUST call getProfile and check that the profile exists\r\n"
	    + "(for the specified fiscal code) and that the sender_allowed field\r\n" + "of the user's profile it set to true.")
    @ApiResponses(value = {
	    @ApiResponse(responseCode = "201", description = "Message created", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = CreatedMessageResponse.class)) }),
	    @ApiResponse(responseCode = "400", description = "Invalid payload", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
	    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
	    @ApiResponse(responseCode = "429", description = "Too many request", content = @Content),
	    @ApiResponse(responseCode = "500", description = "The message cannot be delivered.", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
    @PostMapping(consumes = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CreatedMessageResponse postMessaggi(@RequestBody @Valid MessaggiRequest messaggi) throws DataIntegrationException {

	ModelMapper mapper = new ModelMapper();
	MessaggiDTO messaggiDTO = ModelMapperUtils.newMessaggiDTOFromMessaggiRequest(messaggi);
	MessaggiDTO createdMessage = messaggiService.createMessage(messaggiDTO);
	publisher.publish(new ReceivedMessageEvent(createdMessage));
	mapper.addMappings(new PropertyMap<MessaggiDTO, CreatedMessageResponse>() {

	    @Override
	    protected void configure() {

		map().setMessageId(source.getMessageId());
	    }
	});
	return mapper.map(createdMessage, CreatedMessageResponse.class);
    }

    @Operation(summary = "Get Message", description = "The previously created message with the provided message ID is returned.")
    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Message Found", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = StatusMessageResponse.class)) }),
	    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
	    @ApiResponse(responseCode = "429", description = "Too many request", content = @Content),
	    @ApiResponse(responseCode = "404", description = "No message found for provided message_id", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping(path = "/{message_id}", produces = "application/json")
    public StatusMessageResponse getMessage(
	    @Parameter(description = "message_id of message to be searched") @PathVariable("message_id") String messageId)
	    throws DataIntegrationException {

	Optional<MessaggiDTO> msgDTOOptional = messaggiService.findByMessageId(messageId);
	if (!msgDTOOptional.isPresent()) {
	    throw new DataIntegrationException("Messaggio con message_id=[" + messageId + "] non trovato.");
	}
	Optional<ProblemDTO> problemOpt = problemService.findById(msgDTOOptional.get().getId());
	StringBuilder stringBuilder = new StringBuilder();
	if (problemOpt.isPresent()) {
	    ModelMapperUtils.updateMessaggiDTO(msgDTOOptional.get(), problemOpt.get());
	    if (problemOpt.get().getStatusCode().equals(404)) {
		stringBuilder = stringBuilder.append("Tentativo di invio del messaggio con [id=").append(messageId)
			.append("] fallito, per l'utente [fiscalCode= ").append(msgDTOOptional.get().getFiscalCode())
			.append("]. Causa: Il cittadino non risulta iscritto ad IO o ha disattivato le comunicazioni del servizio");
	    } else {
		stringBuilder = stringBuilder.append("Tentativo di invio del messaggio con [id=").append(messageId)
			.append("] fallito, per l'utente [fiscalCode= ").append(msgDTOOptional.get().getFiscalCode()).append("]. Causa: ")
			.append(msgDTOOptional.get().getDetail());
	    }
	    log.error(stringBuilder.toString());
	    throw new DataIntegrationException(stringBuilder.toString());
	}
	ModelMapper mapper = new ModelMapper();
	// Se SENDER_ALLOWED=false restituisco un messaggio di errore in conformità a come è stato fatto sopra
	if (!msgDTOOptional.get().getSenderAllowed()) {
	    stringBuilder = stringBuilder.append("Tentativo di invio del messaggio con [id=").append(messageId)
		    .append("] fallito, per l'utente [fiscalCode= ").append(msgDTOOptional.get().getFiscalCode())
		    .append("]. Causa: Il cittadino non risulta iscritto ad IO o ha disattivato le comunicazioni del servizio");
	    log.error(stringBuilder.toString());
	    throw new DataIntegrationException(stringBuilder.toString());
	    // return mapper.map(msgDTOOptional.get(), StatusMessageResponse.class);
	}
	MessaggiDTO msgDTO = getMessageStatusFromConnector(msgDTOOptional.get());
	return mapper.map(msgDTO, StatusMessageResponse.class);
    }

    @Operation(summary = "Put Message", description = "Set the stato_messaggio value to StatiMessaggioEnum.RITENTA_INVIO for the message identified by message_id")
    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Message Found", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = StatusMessageResponse.class)) }),
	    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
	    @ApiResponse(responseCode = "429", description = "Too many request", content = @Content),
	    @ApiResponse(responseCode = "500", description = "Internal Server error", content = @Content),
	    @ApiResponse(responseCode = "404", description = "No message found for provided message_id", content = {
		    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
    @PutMapping(path = "/update-status-to-send/{message_id}", produces = "application/json")
    public StatusMessageResponse ritentaInvio(
	    @Parameter(description = "message_id of message to be searched") @PathVariable("message_id") String messageId)
	    throws DataIntegrationException {

	ModelMapper mapper = new ModelMapper();
	Optional<MessaggiDTO> msgDTOOptional = messaggiService.findByMessageId(messageId);
	if (!msgDTOOptional.isPresent()) {
	    throw new DataIntegrationException("Messaggio con message_id=[" + messageId + "] non trovato.");
	}
	MessaggiDTO msgDTO = getMessageStatusFromConnector(msgDTOOptional.get());
	try {
	    msgDTO.setStatoMessaggio(StatiMessaggioEnum.RITENTA_INVIO.name());
	    messaggiService.updateFromDTOSelectively(msgDTO, FieldEnum.STATO_MESSAGGIO);
	} catch (Exception e) {
	    String message = "Errore nell'aggiornamento dello stato del messaggio con id " + messageId + ". dettagli (" + e.getMessage() + ")";
	    log.error(message);
	    throw new DataIntegrationException(message, e);
	}
	return mapper.map(msgDTO, StatusMessageResponse.class);
    }

    private MessaggiDTO getMessageStatusFromConnector(MessaggiDTO message) {

	if (StringUtils.isNotBlank(message.getConnettore())) {
	    switch (message.getConnettore()) {
	    case IOITALIA:
		return ioConnectorService.getMessageStatus(message);
	    case UD:
		return udConnectorService.getMessageStatus(message);
	    default:
		break;
	    }
	}
	throw new IllegalArgumentException("Connettore non implementato " + message.getConnettore());
    }
}