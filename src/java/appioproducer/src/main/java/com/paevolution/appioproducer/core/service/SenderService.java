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
    /*
    public void sendMessage(MessageToSendHelper messageToSendHelper) {
    
    // Inserimento nella tabella MOVIMENTI_IO_COMUNICAZIONI
    // Lo faccio prima in maniera tale che se il salvataggio va a buon fine effettuo il post altrimenti no
    // Evito che vengano inviati infiniti messaggi in caso di problemi con il salvataggio nella tabella MOVIMENTI_IO_COMUNICAZIONI
    // Codice Fiscale Non Presente
    if (StringUtils.isEmpty(messageToSendHelper.getDestinatarioCodicefiscale())) {
        messageToSendHelper.setDestinatarioCodicefiscale("C_F_NON_PRESENTE");
    }
    String messageId = SecurityTools.generateType4UUID().toString();
    MovimentiIoComunicazioniId moComunicazioniId = new MovimentiIoComunicazioniId();
    moComunicazioniId.setIdcomune(messageToSendHelper.getIdcomune());
    moComunicazioniId.setCodicecomune(messageToSendHelper.getCodicecomune());
    moComunicazioniId.setSoftware(messageToSendHelper.getSoftware());
    moComunicazioniId.setCodicemovimento(messageToSendHelper.getCodicemovimento());
    moComunicazioniId.setCodiceanagrafe(messageToSendHelper.getDestinatarioCodiceanagrafe());
    MovimentiIoComunicazioni moIoComunicazioni = new MovimentiIoComunicazioni();
    moIoComunicazioni.setId(moComunicazioniId);
    moIoComunicazioni.setMessageId(messageId);
    moIoComunicazioni.setFiscalCode(messageToSendHelper.getDestinatarioCodicefiscale());
    moIoComunicazioni.setDatainvio(new Date());
    moIoComunicazioni.setSenderAllowed(Boolean.FALSE);
    moIoComunicazioni.setFlagCompletato(Boolean.FALSE);
    movimentiIoComunicazioniRepository.save(moIoComunicazioni);
    try {
        // Creazione Request		
        MessaggiRequest messaggiRequest = new MessaggiRequest();
        messaggiRequest.setIdcomune(messageToSendHelper.getIdcomune());
        messaggiRequest.setCodicecomune(messageToSendHelper.getCodicecomune());
        messaggiRequest.setSoftware(messageToSendHelper.getSoftware());
        messaggiRequest.setFiscalCode(messageToSendHelper.getDestinatarioCodicefiscale());
        messaggiRequest.setSubject("Notifica avanzamento pratica - SUAPE");
        messaggiRequest.setMarkdown(createMarkdown(messageToSendHelper));
        messaggiRequest.setMessageId(messageId);
        // Il campo dueDate non viene ancora gestito
        // messaggiRequest.setDueDate("2018-10-13T00:00:00.000Z");
        // Invio Messaggio
        log.info("sendMessage# Request body: {}", messaggiRequest);
        CreatedMessageResponse createdMessageResponse = appIOGatewayClient.postMessage(messaggiRequest);
    } catch (Throwable ex) {
        if (ex instanceof WebClientResponseException) {
    	ObjectMapper objectMapper = new ObjectMapper();
    	try {
    	    ErrorResponse errorResponse = objectMapper.readValue(((WebClientResponseException) ex).getResponseBodyAsString(),
    		    ErrorResponse.class);
    	    Optional<MovimentiIoComunicazioni> moIoComunicazioniSaved = movimentiIoComunicazioniRepository.findById(moComunicazioniId);
    	    if (moIoComunicazioniSaved.isPresent()) {
    		// Gestione Errori restituiti da appiogateway
    		// Aggiornare MOVIMENTI_IO_COMUNICAZIONI
    		moIoComunicazioniSaved.get().setErrore(errorResponse.getDetails().toString());
    		movimentiIoComunicazioniRepository.save(moIoComunicazioniSaved.get());
    	    }
    	} catch (JsonProcessingException e) {
    	    // Gestione Errori di raggiungibilità del servizio		    
    	    Optional<MovimentiIoComunicazioni> moIoComunicazioniSaved = movimentiIoComunicazioniRepository.findById(moComunicazioniId);
    	    movimentiIoComunicazioniRepository.delete(moIoComunicazioniSaved.get());
    	    e.printStackTrace();
    	}
        } else {
    	// Gestione Errori di raggiungibilità del servizio
    	Optional<MovimentiIoComunicazioni> moIoComunicazioniSaved = movimentiIoComunicazioniRepository.findById(moComunicazioniId);
    	movimentiIoComunicazioniRepository.delete(moIoComunicazioniSaved.get());
        }
    }
    }
    */

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
    //    public void getMessage(MovimentiIoComunicazioni movimentiIoComunicazioni) {
    //
    //	log.info("getMessage: Start");
    //	StatusMessageResponse statusMessageResponse;
    //	try {
    //	    statusMessageResponse = appIOGatewayClient.getMessageStatus(movimentiIoComunicazioni.getMessageId());
    //	    // Update tabella MovimentiIoComunicazioni
    //	    movimentiIoComunicazioni.setSenderAllowed(statusMessageResponse.getSenderAllowed());
    //	    movimentiIoComunicazioni.setCreatedAt(statusMessageResponse.getCreatedAt());
    //	    movimentiIoComunicazioni.setNotificationStatus(statusMessageResponse.getStatus());
    //	    movimentiIoComunicazioni.setNotificationWebhook(statusMessageResponse.getWebhookNotification());
    //	    movimentiIoComunicazioni.setNotificationEmail(statusMessageResponse.getEmailNotification());
    //	    movimentiIoComunicazioni.setFlagCompletato(Boolean.TRUE);
    //	    movimentiIoComunicazioniRepository.save(movimentiIoComunicazioni);
    //	} catch (Throwable ex) {
    //	    if (ex instanceof WebClientResponseException) {
    //		ObjectMapper objectMapper = new ObjectMapper();
    //		try {
    //		    ErrorResponse errorResponse = objectMapper.readValue(((WebClientResponseException) ex).getResponseBodyAsString(),
    //			    ErrorResponse.class);
    //		    Optional<MovimentiIoComunicazioni> moIoComunicazioniSaved = movimentiIoComunicazioniRepository
    //			    .findById(movimentiIoComunicazioni.getId());
    //		    if (moIoComunicazioniSaved.isPresent()) {
    //			// Gestione Errori restituiti da appiogateway
    //			// Aggiornare MOVIMENTI_IO_COMUNICAZIONI
    //			moIoComunicazioniSaved.get().setErrore(errorResponse.getDetails().toString());
    //			movimentiIoComunicazioniRepository.save(moIoComunicazioniSaved.get());
    //		    }
    //		} catch (JsonProcessingException e) {
    //		    // Gestione Errori di raggiungibilità del servizio
    //		}
    //	    } else {
    //		// Gestione Errori di raggiungibilità del servizio
    //	    }
    //	}
    //    }

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
	    appIoCodaRepository.save(appIoCoda.get());
	    // Insert in AAP_IO_CODA_STATI
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
    //    private void populateMessaggioIOComunicazioniWithError(WebClientRequestException exc, MovimentiIoComunicazioni movimentiIoComunicazioni) {
    //
    //	Optional<MovimentiIoComunicazioni> moIoComunicazioniSaved = movimentiIoComunicazioniRepository.findById(movimentiIoComunicazioni.getId());
    //	if (moIoComunicazioniSaved.isPresent()) {
    //	    // Gestione Errori restituiti da appiogateway
    //	    // Aggiornare MOVIMENTI_IO_COMUNICAZIONI
    //	    moIoComunicazioniSaved.get().setErrore(buildWebClientRequestExceptionDetailsMessage(exc));
    //	    movimentiIoComunicazioniRepository.save(moIoComunicazioniSaved.get());
    //	}
    //    }
    //    private String buildWebClientRequestExceptionDetailsMessage(WebClientRequestException exc) {
    //
    //	StringBuilder builder = new StringBuilder();
    //	builder = builder.append("Errore nell'invio del messaggio. ").append(exc.getMessage()).append(" ");
    //	if (exc.getMethod() != null) {
    //	    builder = builder.append("HttpMethod: ").append(exc.getMethod().toString()).append(" ");
    //	}
    //	if (exc.getUri() != null) {
    //	    builder = builder.append("URI: ").append(exc.getUri().toString()).append("");
    //	}
    //	return new String(builder);
    //    }
    //    private void populateMessaggioIOComunicazioniWithError(Throwable ex, MovimentiIoComunicazioni movimentiIoComunicazioni) {
    //
    //	Optional<MovimentiIoComunicazioni> moIoComunicazioniSaved = movimentiIoComunicazioniRepository.findById(movimentiIoComunicazioni.getId());
    //	if (moIoComunicazioniSaved.isPresent()) {
    //	    // Gestione Errori restituiti da appiogateway
    //	    // Aggiornare MOVIMENTI_IO_COMUNICAZIONI
    //	    moIoComunicazioniSaved.get().setErrore("Errore nell'invio del messaggio. " + ex.getMessage());
    //	    movimentiIoComunicazioniRepository.save(moIoComunicazioniSaved.get());
    //	}
    //    }
    //    private String createMarkdown(MessageToSendHelper messageToSendHelper) {
    //
    //	String DATE_PATTERN = "dd/MM/yyyy";
    //	StringBuilder markdown = new StringBuilder();
    //	String numProt = (StringUtils.isNotBlank(messageToSendHelper.getNumeroprotocollo())) ? messageToSendHelper.getNumeroprotocollo() : " ";
    //	String dataProt = messageToSendHelper.getDataprotocollo() != null ? formattedDate(messageToSendHelper.getDataprotocollo(), DATE_PATTERN)
    //		: " ";
    //	String lavori = (StringUtils.isNotBlank(messageToSendHelper.getLavori())) ? messageToSendHelper.getLavori() : " ";
    //	String denominazioneServizio = (StringUtils.isNotBlank(messageToSendHelper.getDenominazioneServizio()))
    //		? messageToSendHelper.getDenominazioneServizio()
    //		: " ";
    //	String movimento = (StringUtils.isNotBlank(messageToSendHelper.getMovimento())) ? messageToSendHelper.getMovimento() : " ";
    //	String dataIstanza = messageToSendHelper.getData() != null ? formattedDate(messageToSendHelper.getData(), DATE_PATTERN) : " ";
    //	String numeroistanza = (StringUtils.isNotBlank(messageToSendHelper.getNumeroistanza())) ? messageToSendHelper.getNumeroistanza() : " ";
    //	String destinatario = (StringUtils.isNotBlank(messageToSendHelper.getDestinatario())) ? messageToSendHelper.getDestinatario() : " ";
    //	String urlPortale = StringUtils.defaultIfEmpty(messageToSendHelper.getUrlPortaleServizi(), "");
    //	String str1 = "Relativamente alla pratica prot. " + numProt + " del " + dataProt;
    //	String str2 = " Istanza: n.  " + numeroistanza + " del " + dataIstanza;
    //	String intervento = "\n\nIntervento: " + messageToSendHelper.getIntervento();
    //	String str3 = "\n\nRichiedente: " + destinatario; // + " - " + lavori + "";
    //	String str4 = "\n\nci sono comunicazioni da parte del: " + denominazioneServizio ;
    //	String str5 = "\n\n**" + movimento + "**\n\n";
    //	String str6 = "Si prega di provvedere tempestivamente alla consultazione accedendo alla pratica dal Portale SUAPE\n\n";
    //	if (StringUtils.isNotBlank(urlPortale)) {
    //	    urlPortale = "[" + urlPortale + "](" + urlPortale + ")";
    //	}
    //	markdown.append(str1).append(str2).append(intervento).append(str3).append(str4).append(str5).append(str6).append(urlPortale);
    //	return markdown.toString();
    //    }
    //    private String generateMarkdown(MessageToSendHelper messageToSendHelper) {
    //
    //	String DATE_PATTERN = "dd/MM/yyyy";
    //	StringBuilder markdown = new StringBuilder();
    //	// solo per test
    //	String numProt = (StringUtils.isNotBlank(messageToSendHelper.getNumeroprotocollo())) ? messageToSendHelper.getNumeroprotocollo() : "N.D";
    //	String dataProt = messageToSendHelper.getDataprotocollo() != null ? formattedDate(messageToSendHelper.getDataprotocollo(), DATE_PATTERN)
    //		: "N.D";
    //	markdown = markdown.append(new Text("Relativamente alla pratica prot.")).append(new Text(numProt)).append(" ").append(new Text(dataProt))
    //		.append(" ")
    //		.append(new Text("Istanza n. " + messageToSendHelper.getNumeroistanza() + " del "
    //			+ formattedDate(messageToSendHelper.getData(), DATE_PATTERN) + "\\"))
    //		.append(new Text("Richiedente: " + messageToSendHelper.getDestinatario() + "-" + messageToSendHelper.getLavori()))
    //		.append(new Text("Ci sono comunicazioni da parte del: " + messageToSendHelper.getDenominazioneServizio() + "\\"))
    //		.append(new Text("Si prega di provvedere tempestivamente alla consulenza accedendo alla pratica dal Portale SUAPE \\\\"))
    //		.append(new Text(messageToSendHelper.getUrlPortaleServizi()));
    //	String md = new String(markdown);
    //	return md;
    //    }
    //    private String formattedDate(Date dateToFormat, String pattern) {
    //
    //	SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    //	return formatter.format(dateToFormat);
    //    }
    //    @Override
    //    public void sendMessage(MessageToSendHelper messageToSendHelper) {
    //
    //	// TODO Auto-generated method stub
    //	
    //    }
}
