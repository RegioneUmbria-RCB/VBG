package com.paevolution.appioproducer.core.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paevolution.appioproducer.core.domain.AppIoCoda;
import com.paevolution.appioproducer.core.domain.AppIoCodaId;
import com.paevolution.appioproducer.core.domain.AppIoCodaStati;
import com.paevolution.appioproducer.core.domain.AppIoCodaStatiId;
import com.paevolution.appioproducer.core.domain.helper.MessageToSendHelper;
import com.paevolution.appioproducer.core.repository.AppIoCodaRepository;
import com.paevolution.appioproducer.core.repository.AppIoCodaStatiRepository;
import com.paevolution.appioproducer.core.repository.MovimentiIoComunicazioniRepository;
import com.paevolution.appioproducer.utils.StatoMessaggioEnum;
import com.paevolution.appioproducer.ws.client.AppIOGatewayClient;
import com.paevolution.appioproducer.ws.client.model.CreatedMessageResponse;
import com.paevolution.appioproducer.ws.client.model.ErrorResponse;
import com.paevolution.appioproducer.ws.client.model.MessaggiRequest;
import com.paevolution.appioproducer.ws.client.model.StatusMessageResponse;

import lombok.extern.slf4j.Slf4j;
import net.steppschuh.markdowngenerator.text.Text;

@Service
@Slf4j
public class SenderService implements ISenderService {

    @Autowired
    private MovimentiIoComunicazioniRepository movimentiIoComunicazioniRepository;
    @Autowired
    private AppIoCodaRepository appIoCodaRepository;
    @Autowired
    private AppIoCodaStatiRepository appIoCodaStatiRepository;
    private WebClient webClient;
    private AppIOGatewayClient appIOGatewayClient;

    public SenderService() {

    }

    @Autowired
    public SenderService(@Qualifier("appiogatewayWSClient") WebClient webClient) {

	setWebClient(webClient);
	this.appIOGatewayClient = new AppIOGatewayClient(this.webClient);
    }

    public void setWebClient(WebClient webClient) {

	this.webClient = webClient;
    }

    public void sendMessage(AppIoCoda messageToSend) {

	if (StringUtils.isEmpty(messageToSend.getCodicefiscale())) {
	    messageToSend.setCodicefiscale("C_F_NON_PRESENTE");
	}
	AppIoCodaId appIoCodaId = new AppIoCodaId();
	appIoCodaId.setIdcomune(messageToSend.getId().getIdcomune());
	appIoCodaId.setGuid(messageToSend.getId().getGuid());
	Optional<AppIoCoda> appIoCoda = appIoCodaRepository.findById(appIoCodaId);
	AppIoCodaStati appIoCodaStati = new AppIoCodaStati();
	AppIoCodaStatiId appIoCodaStatiId = new AppIoCodaStatiId();
	appIoCodaStatiId.setIdcomune(appIoCoda.get().getId().getIdcomune());
	appIoCodaStatiId.setFkGuidcoda(appIoCoda.get().getId().getGuid());
	try {
	    // Creazione Request		
	    MessaggiRequest messaggiRequest = new MessaggiRequest();
	    messaggiRequest.setIdcomune(messageToSend.getId().getIdcomune());
	    messaggiRequest.setFiscalCode(messageToSend.getCodicefiscale());
	    messaggiRequest.setSubject(messageToSend.getOggetto());
	    messaggiRequest.setMarkdown(messageToSend.getMessaggio());
	    messaggiRequest.setMessageId(messageToSend.getId().getGuid());
	    messaggiRequest.setIdentificativoServizio(messageToSend.getIdentificativoServizio());
	    // Il campo dueDate non viene ancora gestito
	    // messaggiRequest.setDueDate("2018-10-13T00:00:00.000Z");
	    // Invio Messaggio
	    log.info("sendMessage# Request body: {}", messaggiRequest);
	    // CreatedMessageResponse createdMessageResponse = appIOGatewayClient.postMessage(messaggiRequest);
	    appIOGatewayClient.postMessage(messaggiRequest);
	    appIoCoda.get().setStato(StatoMessaggioEnum.INVIATA_A_GATEWAY.getName());
	    appIoCoda.get().setStatoData(new Date());
	    appIoCodaRepository.save(appIoCoda.get());
	    // Insert in AAP_IO_CODA_STATI
	    appIoCodaStatiId.setStato(StatoMessaggioEnum.INVIATA_A_GATEWAY.getName());
	    appIoCodaStatiId.setData(new Date());
	    appIoCodaStati.setId(appIoCodaStatiId);
	    appIoCodaStatiRepository.save(appIoCodaStati);
	} catch (Throwable ex) {
	    if (ex instanceof WebClientResponseException) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
		    ErrorResponse errorResponse = objectMapper.readValue(((WebClientResponseException) ex).getResponseBodyAsString(),
			    ErrorResponse.class);
		    appIoCoda.get().setStato(StatoMessaggioEnum.ERRORE_GATEWAY.getName());
		    appIoCoda.get().setStatoData(new Date());
		    appIoCoda.get().setStatoMessaggio(errorResponse.getDetails().toString());
		    appIoCodaRepository.save(appIoCoda.get());
		    // Insert in AAP_IO_CODA_STATI
		    appIoCodaStatiId.setStato(StatoMessaggioEnum.ERRORE_GATEWAY.getName());
		    appIoCodaStatiId.setData(new Date());
		    appIoCodaStati.setId(appIoCodaStatiId);
		    appIoCodaStati.setMessaggio(errorResponse.getDetails().toString());
		    appIoCodaStatiRepository.save(appIoCodaStati);
		} catch (JsonProcessingException e) {
		    // Gestione Errori di raggiungibilità del servizio		    
		    e.printStackTrace();
		}
	    } else {
		// Gestione Errori di raggiungibilità del servizio
	    }
	}
    }

    public void getMessage(AppIoCoda messageToNotify) {

	log.info("getMessage: Start");
	StatusMessageResponse statusMessageResponse;
	AppIoCodaId appIoCodaId = new AppIoCodaId();
	appIoCodaId.setIdcomune(messageToNotify.getId().getIdcomune());
	appIoCodaId.setGuid(messageToNotify.getId().getGuid());
	Optional<AppIoCoda> appIoCoda = appIoCodaRepository.findById(appIoCodaId);
	AppIoCodaStati appIoCodaStati = new AppIoCodaStati();
	AppIoCodaStatiId appIoCodaStatiId = new AppIoCodaStatiId();
	appIoCodaStatiId.setIdcomune(appIoCoda.get().getId().getIdcomune());
	appIoCodaStatiId.setFkGuidcoda(appIoCoda.get().getId().getGuid());
	try {
	    statusMessageResponse = appIOGatewayClient.getMessageStatus(messageToNotify.getId().getGuid());
	    appIoCoda.get().setStato(StatoMessaggioEnum.valueOf(statusMessageResponse.getStatus()).getName());
	    appIoCoda.get().setStatoData(new Date());
	    if (statusMessageResponse.getStatus().equals("PROCESSED")) {
		appIoCoda.get().setStatoMessaggio(
			"Il messaggio con [id_messaggio_mittente=" + statusMessageResponse.getMessageId() + "] è stato presentato.");
	    }
	    appIoCodaRepository.save(appIoCoda.get());
	    // Insert in APP_IO_CODA_STATI
	    appIoCodaStatiId.setStato(StatoMessaggioEnum.valueOf(statusMessageResponse.getStatus()).getName());
	    appIoCodaStatiId.setData(new Date());
	    appIoCodaStati.setId(appIoCodaStatiId);
	    appIoCodaStati.setStatoAppIo(statusMessageResponse.getStatus());
	    appIoCodaStatiRepository.save(appIoCodaStati);
	} catch (Throwable ex) {
	    if (ex instanceof WebClientResponseException) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
		    ErrorResponse errorResponse = objectMapper.readValue(((WebClientResponseException) ex).getResponseBodyAsString(),
			    ErrorResponse.class);
		    appIoCoda.get().setStato(StatoMessaggioEnum.ERRORE_GATEWAY.getName());
		    appIoCoda.get().setStatoData(new Date());
		    appIoCoda.get().setStatoMessaggio(errorResponse.getDetails().toString());
		    appIoCodaRepository.save(appIoCoda.get());
		    // Insert in AAP_IO_CODA_STATI
		    appIoCodaStatiId.setStato(StatoMessaggioEnum.ERRORE_GATEWAY.getName());
		    appIoCodaStatiId.setData(new Date());
		    appIoCodaStati.setId(appIoCodaStatiId);
		    appIoCodaStati.setMessaggio(errorResponse.getDetails().toString());
		    appIoCodaStatiRepository.save(appIoCodaStati);
		} catch (JsonProcessingException e) {
		    // Gestione Errori di raggiungibilità del servizio
		}
	    } else {
		// Gestione Errori di raggiungibilità del servizio
	    }
	}
    }

    /*
     *  1. chiama l'API ritentaInvio
     *  2. aggiorna lo stato in INVIATA_A_GATEWAY 
     */
    public void resendMessage(AppIoCoda messageToSend) {

	log.info("resendMessage: Start");
	StatusMessageResponse statusMessageResponse;
	AppIoCodaId appIoCodaId = new AppIoCodaId();
	appIoCodaId.setIdcomune(messageToSend.getId().getIdcomune());
	appIoCodaId.setGuid(messageToSend.getId().getGuid());
	Optional<AppIoCoda> appIoCoda = appIoCodaRepository.findById(appIoCodaId);
	AppIoCodaStati appIoCodaStati = new AppIoCodaStati();
	AppIoCodaStatiId appIoCodaStatiId = new AppIoCodaStatiId();
	appIoCodaStatiId.setIdcomune(appIoCoda.get().getId().getIdcomune());
	appIoCodaStatiId.setFkGuidcoda(appIoCoda.get().getId().getGuid());
	try {
	    // statusMessageResponse = appIOGatewayClient.getMessageStatus(messageToSend.getId().getGuid());
	    statusMessageResponse = appIOGatewayClient.resendMessage(messageToSend.getId().getGuid());
	    appIoCoda.get().setStato(StatoMessaggioEnum.INVIATA_A_GATEWAY.getName());
	    appIoCoda.get().setStatoData(new Date());
	    appIoCodaRepository.save(appIoCoda.get());
	    // Insert in AAP_IO_CODA_STATI
	    appIoCodaStatiId.setStato(StatoMessaggioEnum.INVIATA_A_GATEWAY.getName());
	    appIoCodaStatiId.setData(new Date());
	    appIoCodaStati.setId(appIoCodaStatiId);
	    appIoCodaStati.setStatoAppIo(statusMessageResponse.getStatus());
	    appIoCodaStatiRepository.save(appIoCodaStati);
	} catch (Throwable ex) {
	    if (ex instanceof WebClientResponseException) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
		    ErrorResponse errorResponse = objectMapper.readValue(((WebClientResponseException) ex).getResponseBodyAsString(),
			    ErrorResponse.class);
		    appIoCoda.get().setStato(StatoMessaggioEnum.ERRORE_GATEWAY.getName());
		    appIoCoda.get().setStatoData(new Date());
		    appIoCoda.get().setStatoMessaggio(errorResponse.getDetails().toString());
		    appIoCodaRepository.save(appIoCoda.get());
		    // Insert in AAP_IO_CODA_STATI
		    appIoCodaStatiId.setStato(StatoMessaggioEnum.ERRORE_GATEWAY.getName());
		    appIoCodaStatiId.setData(new Date());
		    appIoCodaStati.setId(appIoCodaStatiId);
		    appIoCodaStati.setMessaggio(errorResponse.getDetails().toString());
		    appIoCodaStatiRepository.save(appIoCodaStati);
		} catch (JsonProcessingException e) {
		    // Gestione Errori di raggiungibilità del servizio
		}
	    } else {
		// Gestione Errori di raggiungibilità del servizio
	    }
	}
    }
}
