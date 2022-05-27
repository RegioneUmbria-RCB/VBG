package com.paevolution.appiogateway.web.controllers.ui;

import java.sql.Timestamp;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paevolution.appiogateway.connector.ioitalia.client.model.InlineResponse201;
import com.paevolution.appiogateway.connector.ioitalia.service.IOConnectorService;
import com.paevolution.appiogateway.connector.ud.client.service.UDConnectorService;
import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.core.service.MessaggiService;
import com.paevolution.appiogateway.events.interfaces.IEventPublisher;
import com.paevolution.appiogateway.exceptions.DataIntegrationException;
import com.paevolution.appiogateway.exceptions.WebClientExceptionsWrapper;
import com.paevolution.appiogateway.utils.Utils;
import com.paevolution.appiogateway.web.controllers.ui.model.UIMessageRequest;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/connector")
@Slf4j
public class ConnectorController {

    private MessaggiService messaggiService;
    private UDConnectorService udConnectorService;
    private IOConnectorService ioConnectorService;
    private IEventPublisher publisher;

    @Autowired
    public void setMessaggiService(MessaggiService messaggiService) {

	this.messaggiService = messaggiService;
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
    public void setPublisher(@Qualifier("eventPublisher") IEventPublisher publisher) {

	this.publisher = publisher;
    }

    @GetMapping
    public String connectorForm(Model model) {

	model.addAttribute("message", new UIMessageRequest());
	return "connectorhome";
    }

    @GetMapping("/search-form")
    public String searchMessageForm(Model model) {

	return "connectorsearch";
    }

    @PostMapping
    public String postNewMessage(@Valid @ModelAttribute UIMessageRequest message, BindingResult result, Model model)
	    throws DataIntegrationException, WebClientExceptionsWrapper, Exception {

	validateDueDate(message);
	log.info("Nuovo Messaggio da inviare: {}", message);
	if (result.hasErrors()) {
	    // TODO: completare la validazione degli errori aggiungere una pagina di errori
	    // o redirect alla stessa pagina
	    return "redirect:/connector";
	}
	/// 1. Mapping del messaggio di richiesta con MessaggiDTO
	ModelMapper mapper = new ModelMapper();
	mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	mapper.addConverter(new AbstractConverter<String, Timestamp>() {

	    @Override
	    protected Timestamp convert(String source) {

		return StringUtils.isNotBlank(source) ? Utils.convertStringToSQLTimestamp(source) : null;
	    }
	});
	MessaggiDTO msgDTO = mapper.map(message, MessaggiDTO.class);
	log.info("Mapped DTO: {}", msgDTO);
	/// 2. creazione e memorizzazione del messaggio sul db
	MessaggiDTO createdMessage = messaggiService.createMessage(msgDTO);
	/// publisher.publish(new ReceivedMessageEvent(createdMessage));
	/// 3. scelta del servizio a cui inviare il messaggio
	InlineResponse201 response = post(createdMessage);
	model.addAttribute("inlineResponse201", response);
	return "display-id-result";
    }

    private void validateDueDate(UIMessageRequest message) {

	/// from: 2021-02-24T10:02:55.5555[+01:00]
	/// to: 2021-02-24T10:02:55.5555[Z]
	final String REGEX = "([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?$";
	if (StringUtils.isNotBlank(message.getDueDate())) {
	    log.info("Due Date Before replace First: {}", message.getDueDate());
	    String dueDate = message.getDueDate();
	    dueDate = dueDate.replaceFirst(REGEX, "Z");
	    message.setDueDate(dueDate);
	    log.info("Due Date after replace First: {}", message.getDueDate());
	}
    }

    private InlineResponse201 post(MessaggiDTO message) throws WebClientExceptionsWrapper, Exception {

	if (StringUtils.isNoneBlank(message.getConnettore())) {
	    switch (message.getConnettore()) {
	    case "IOITALIA":
		return ioConnectorService.directPOSTMessage(message);
	    case "UD":
		return udConnectorService.directPOSTMessage(message);
	    default:
		break;
	    }
	}
	throw new RuntimeException("Il messaggio con [id=" + message.getMessageId() + "] non e' stato inviato al connettore con [codice="
		+ message.getConnettore() + "]");
    }
}
