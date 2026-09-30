package it.gruppoinit.nlapec.service;

import it.gruppoinit.nlapec.service.eventi.EventiWSClient;
import it.gruppoinit.nlapec.service.movimentimail.MovimentiMailWSClient;
import it.gruppoinit.nlapec.service.oggetti.OggettiWSClient;
import it.gruppoinit.nlapec.service.sigepro.SigeproService;
import it.gruppoinit.nlapec.service.stc.StcWebServiceClient;
import it.gruppoinit.nlapec.util.AllegatiUtil;
import it.gruppoinit.nlapec.util.InfoIstanzaBean;
import it.gruppoinit.nlapec.util.PECMessage;
import it.gruppoinit.nlapec.util.Validator;
import it.gruppoinit.sigepro.schemas.messages.movimentimail.MovimentiMailResponse2;
import it.gruppoinit.sigepro.schemas.messages.oggetti.OggettiInsertResponse;
import it.init.sigepro.rte.InserimentoPraticaResponse;
import it.init.sigepro.rte.NotificaAttivitaResponse;
import it.init.sigepro.rte.types.RiferimentiPraticaType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PECManager {

    private static final Logger log = LoggerFactory.getLogger(PECManager.class);
    private PECProcessor processor;
    private MovimentiMailWSClient movimentiMailWebServiceClient;
    private SigeproService sigeproService;
    private OggettiWSClient oggettiWSClient;
    private StcWebServiceClient stcWSClient;
    private EventiWSClient eventiWebServiceClient;

    public InserimentoPraticaResponse analyzer(String idcomuneAlias, String software, String token, PECMessage pecMessage, String tmpPath,
	    Message msg, Map<String, String> parametriTipologieProcessamentoPEC, Map<String, String> altriParametriVerticalizzazione,
	    String stcToken, Properties connectionProps, String urlWsServiceNotificaMail, String urlWsServiceOggetti, String urlWsServiceEventi,
	    BigInteger idAccount) throws Exception {

	InserimentoPraticaResponse response = null;
	String processaComUnica = parametriTipologieProcessamentoPEC.get("PEC_COMUNICA_CAMERACOM");
	String processaPecAreaRiservata = parametriTipologieProcessamentoPEC.get("PEC_AREARISERVATA");
	String processaPecEntiTerzi = parametriTipologieProcessamentoPEC.get("PEC_ENTITERZI");
	String processaPecCittadini = parametriTipologieProcessamentoPEC.get("PEC_CITTADINO");
	String processaPecNonFormattata = parametriTipologieProcessamentoPEC.get("PEC_NON_FORMATTATA");
	String checkReply = parametriTipologieProcessamentoPEC.get("CHECK_REPLY");
	String dettaglioReport = "UNKNOW";
	boolean isProcessed = false;
	log.debug("StringUtils.isNotBlank(processaComUnica) : " + StringUtils.isNotBlank(processaComUnica));
	if (StringUtils.isNotBlank(processaComUnica)) {
	    ArrayList<String> listaFileAttachment = AllegatiUtil.saveAttachment(msg, tmpPath);
	    if (processaComUnica.equalsIgnoreCase("2")) {
		if (isPraticaComunicaCameraLombardia(pecMessage, listaFileAttachment)) {
		    log.debug("PEC CAMERA COMMERCIO LOMBARDIA");
		    response = processor.processPEC_ComUnicaLombardia(pecMessage, tmpPath, listaFileAttachment, stcToken, idcomuneAlias, software);
		    isProcessed = true;
		    dettaglioReport = "[VBG - idPratica: " + response.getDettaglioPratica().getIdPratica() + " - NumPratica: "
			    + response.getDettaglioPratica().getNumeroPratica() + "]";
		}
	    } else if (processaComUnica.equalsIgnoreCase("1")) {
		if (isPraticaComunicaStandard(pecMessage, listaFileAttachment)) {
		    log.debug("PEC CAMERA COMMERCIO STANDARD");
		}
		// processor.processPEC_ComUnicaStandard(pecBodyParts,token,idcomune,software);
	    }
	}
	log.debug("StringUtils.isNotBlank(processaPecAreaRiservata) : " + StringUtils.isNotBlank(processaPecAreaRiservata) + " || isProcessed : "
		+ isProcessed);
	if (StringUtils.isNotBlank(processaPecAreaRiservata) && !isProcessed) {
	    ArrayList<String> listaFileAttachment = AllegatiUtil.saveAttachment(msg, tmpPath);
	    if (processaPecAreaRiservata.equalsIgnoreCase("S") && isPraticaPecAreaRiservata(pecMessage, listaFileAttachment)) {
		isProcessed = true;
	    }
	}
	log.debug("StringUtils.isNotBlank(processaPecCittadini) : " + StringUtils.isNotBlank(processaPecCittadini) + " || isProcessed : "
		+ isProcessed);
	if (StringUtils.isNotBlank(processaPecCittadini) && !isProcessed) {
	    if (processaPecCittadini.equalsIgnoreCase("S") && isPraticaPecCittadini(pecMessage)) {
		log.debug("PEC CITTADINO");
		ArrayList<String> listaFileAttachment = AllegatiUtil.saveAttachment(msg, tmpPath);
		response = processor.processPEC_Cittadino(pecMessage, tmpPath, listaFileAttachment, stcToken, idcomuneAlias, software,
			altriParametriVerticalizzazione, connectionProps);
		isProcessed = true;
		dettaglioReport = "[VBG - idPratica: " + response.getDettaglioPratica().getIdPratica() + " - NumPratica: "
			+ response.getDettaglioPratica().getNumeroPratica() + "]";
	    }
	}
	log.debug("StringUtils.isNotBlank(processaPecNonFormattata) : " + StringUtils.isNotBlank(processaPecNonFormattata) + " || isProcessed : "
		+ isProcessed);
	if (StringUtils.isNotBlank(processaPecNonFormattata) && !isProcessed) {
	    String pecNonFormattataRegularExpression = altriParametriVerticalizzazione.get("PEC_NON_FORMATTATA_FILTRO");
	    if (processaPecNonFormattata.equalsIgnoreCase("S") && pecNonFormattataRegularExpression != null
		    && !pecNonFormattataRegularExpression.trim().equalsIgnoreCase("")
		    && isPecNonFormattata(pecMessage, pecNonFormattataRegularExpression)) {
		log.debug("PEC NON FORMATTATA");
		ArrayList<String> listaFileAttachment = AllegatiUtil.saveAttachment(msg, tmpPath);
		response = processor.processPEC_NonFormattata(pecMessage, tmpPath, listaFileAttachment, stcToken, idcomuneAlias, software,
			altriParametriVerticalizzazione, connectionProps);
		isProcessed = true;
		dettaglioReport = "[VBG - idPratica: " + response.getDettaglioPratica().getIdPratica() + " - NumPratica: "
			+ response.getDettaglioPratica().getNumeroPratica() + "]";
	    }
	}
	log.debug("StringUtils.isNotBlank(processaPecEntiTerzi) : " + StringUtils.isNotBlank(processaPecEntiTerzi) + " || isProcessed : "
		+ isProcessed);
	if (StringUtils.isNotBlank(processaPecEntiTerzi) && !isProcessed) {
	    ArrayList<String> listaFileAttachment = AllegatiUtil.saveAttachment(msg, tmpPath);
	    if (processaPecEntiTerzi.equalsIgnoreCase("S") && isPecEntiTerzi(pecMessage, listaFileAttachment)) {
		isProcessed = true;
	    }
	}
	// verifico se si stratta di un "Reply-to" relativo ad un messaggio inviato da VBG.
	log.debug("StringUtils.isNotBlank(checkReply) : " + StringUtils.isNotBlank(checkReply) + " || isProcessed : " + isProcessed
		+ " || checkReply:" + checkReply);
	if (StringUtils.isNotBlank(checkReply) && checkReply.equalsIgnoreCase("S") && !isProcessed) { // controllo se la verticalizzazione a tale funzione è attiva
	    String[] referencesHeader = AllegatiUtil.getHeader(msg, "References");
	    if (referencesHeader != null && log.isDebugEnabled()) {
		for (int i = 0; i < referencesHeader.length; i++) {
		    log.debug(referencesHeader[i]);
		}
	    }
	    String identificatoreVBG = AllegatiUtil.checkIfReplyVBG(referencesHeader, msg.getSubject());
	    if (StringUtils.isNotBlank(identificatoreVBG)) {
		log.debug("PEC - REPLY di una PEC inviata da VBG");
		ArrayList<String> listaFileAttachment = AllegatiUtil.saveAttachment(msg, tmpPath);
		log.debug("generaContromovimento...");
		String resp = generaContromovimento(connectionProps, identificatoreVBG, listaFileAttachment, tmpPath, token, stcToken, software,
			pecMessage, urlWsServiceOggetti, urlWsServiceNotificaMail, idAccount);
		log.debug("Returned : " + ((resp == null) ? "NULL" : resp));
		if (resp != null) {
		    RiferimentiPraticaType rifPratica = new RiferimentiPraticaType();
		    rifPratica.setNumeroProtocolloGenerale(resp);
		    response = new InserimentoPraticaResponse();
		    response.setDettaglioPratica(rifPratica);
		} else {
		    segnalaEventoAnamalia(connectionProps, token, software, urlWsServiceEventi, identificatoreVBG);
		    response = null;
		}
	    }
	}
	return response;
    }

    private void segnalaEventoAnamalia(Properties connectionProps, String token, String software, String urlWS, String identificatoreVBG) {

	try {
	    InfoIstanzaBean infoIstanza = sigeproService.getInfoPratica(connectionProps, identificatoreVBG, software);
	    String errorMsg = "Errore in fase di creazione di un contromovimento automatico (generato dalla ricezione di una mail di risposta)";
	    eventiWebServiceClient.eventoIstanzaInsert(urlWS, token, software, infoIstanza.getCodiceIstanza(), errorMsg);
	} catch (Exception e) {
	    log.error("Si e' verificato un errore in fase di segnalazione di un evento di anomalia");
	}
    }

    private String generaContromovimento(Properties connectionProps, String identificatoreMsgIdVBG, ArrayList<String> listaFileAttachment,
	    String tmpPath, String token, String stcToken, String software, PECMessage pecMessage, String urlOggettiWS, String urlMovimentiWS,
	    BigInteger idAccount) {

	String ret = null;
	try {
	    InfoIstanzaBean infoIstanza = sigeproService.getInfoPratica(connectionProps, identificatoreMsgIdVBG, software);
	    if (infoIstanza != null) {
		ArrayList<String> idOggettiList = new ArrayList<String>();
		for (Iterator<String> iterator = listaFileAttachment.iterator(); iterator.hasNext();) {
		    String nomeFile = (String) iterator.next();
		    log.debug("oggettiInsert-->" + nomeFile);
		    OggettiInsertResponse response = oggettiWSClient.oggettiInsert(urlOggettiWS, token, tmpPath + nomeFile);
		    idOggettiList.add(response.getId().toString());
		}
		NotificaAttivitaResponse response = stcWSClient.notificaAttivita(stcToken, infoIstanza, pecMessage, listaFileAttachment,
			idOggettiList);
		if (response != null && (response.getDettaglioErrore() == null || response.getDettaglioErrore().size() == 0)) {
		    ret = "Generato contromovimento con id : " + response.getDettaglioattivita().getIdAttivita();
		    log.debug("Generato contromovimento con id : " + response.getDettaglioattivita().getIdAttivita());
		    //		    MovimentiMailResponse resp = movimentiMailWebServiceClient.movimentiMail(urlMovimentiWS, token, software, pecMessage, tmpPath,
		    //			    listaFileAttachment, response.getDettaglioattivita().getIdAttivita(), idOggettiList);
		    MovimentiMailResponse2 resp = movimentiMailWebServiceClient.movimentiMail2(urlMovimentiWS, stcToken, software, idAccount,
			    pecMessage, tmpPath, listaFileAttachment, response.getDettaglioattivita().getIdAttivita(), idOggettiList);
		    log.debug("Inserita la mail che ha generato il contromovimento");
		} else {
		    log.error("Errore nella notifica Attivita'");
		    throw new Exception("Errore nella notifica Attivita'");
		}
	    }
	} catch (Exception e) {
	    log.error("Errore durante la generazione del contromovimento : " + e.getMessage());
	    ret = null;
	}
	return ret;
    }

    private boolean isPecNonFormattata(PECMessage pecMessage, String regularExpression) {

	Validator validator = new Validator();
	boolean isValidSubject = validator.validateMailSubjectPecNonFormatta(pecMessage.getSubject(), regularExpression);
	return isValidSubject;
    }

    private boolean isPraticaPecCittadini(PECMessage pecMessage) {

	Validator validator = new Validator();
	boolean isValidSubject = validator.validateMailSubjectPecCittadini(pecMessage.getSubject());
	return isValidSubject;
    }

    private boolean isPecEntiTerzi(PECMessage pecMessage, ArrayList<String> listaFileAttachment) {

	// TODO Auto-generated method stub
	return false;
    }

    private boolean isPraticaPecAreaRiservata(PECMessage pecMessage, ArrayList<String> listaFileAttachment) {

	// TODO Auto-generated method stub
	return false;
    }

    private boolean isPraticaComunicaCameraLombardia(PECMessage pecMessage, ArrayList<String> listaFileAttachment) {

	// OriginalMessage om = MimeMessageHandler.getOriginalMessage(pecMessage);
	Validator validator = new Validator();
	boolean isValidSubject = validator.validateMailSubjectLombardia(pecMessage.getSubject());
	boolean isValidAllegati = validator.validazionePresenzaAllegatiLombardia(listaFileAttachment);
	if (isValidSubject && isValidAllegati) {
	    return true;
	}
	return false;
    }

    private boolean isPraticaComunicaStandard(PECMessage pecMessage, ArrayList<String> listaFileAttachment) {

	String subject = pecMessage.getSubject();
	String regExpr = "";
	return false;
    }

    public PECProcessor getProcessor() {

	return processor;
    }

    public void setProcessor(PECProcessor processor) {

	this.processor = processor;
    }

    public MovimentiMailWSClient getMovimentiMailWebServiceClient() {

	return movimentiMailWebServiceClient;
    }

    public void setMovimentiMailWebServiceClient(MovimentiMailWSClient movimentiMailWebServiceClient) {

	this.movimentiMailWebServiceClient = movimentiMailWebServiceClient;
    }

    public SigeproService getSigeproService() {

	return sigeproService;
    }

    public void setSigeproService(SigeproService sigeproService) {

	this.sigeproService = sigeproService;
    }

    public OggettiWSClient getOggettiWSClient() {

	return oggettiWSClient;
    }

    public void setOggettiWSClient(OggettiWSClient oggettiWSClient) {

	this.oggettiWSClient = oggettiWSClient;
    }

    public StcWebServiceClient getStcWSClient() {

	return stcWSClient;
    }

    public void setStcWSClient(StcWebServiceClient stcWSClient) {

	this.stcWSClient = stcWSClient;
    }

    public EventiWSClient getEventiWebServiceClient() {

	return eventiWebServiceClient;
    }

    public void setEventiWebServiceClient(EventiWSClient eventiWebServiceClient) {

	this.eventiWebServiceClient = eventiWebServiceClient;
    }
}
