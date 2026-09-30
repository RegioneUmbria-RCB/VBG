package it.gruppoinit.pal.gp.backoffice.ws;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.AnagrafeGiuridicaType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.AnagrafeType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.ErroreType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.InserimentoAnagrafeRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.InserimentoAnagrafeResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.InserimentoPersonaGiuridicaRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.RiferimentiAnagrafeType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.CategorieEventiBaseType;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.FoRichieste;
import it.gruppoinit.pal.gp.core.domain.Formegiuridiche;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.areariservata.IVerticalizzazioneAreaRiservataService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.FoRichiesteService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.rules.AnagrafeBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

@WebService(serviceName = "AnagrafeService", portName = "AnagrafeSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/anagrafe", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.anagrafe.Anagrafe")
public class AnagrafeWS extends BaseWS implements it.gruppoinit.pal.gp.backoffice.definitions.anagrafe.Anagrafe {

    private static final String AUTH_TYPE_REG = "AUTH_TYPE_REG";
    public static final String AUTH_TYPE_REG_SC = "AUTH_TYPE_REG_SC";
    private static final Logger log = LoggerFactory.getLogger(AnagrafeWS.class);
    private AnagrafeService anagrafeService;
    private MailtipoService mailtipoService;
    private IstanzeeventiService istanzeeventiService;
    private FoRichiesteService foRichiesteService;
    private OggettiService oggettiService;
    private IVerticalizzazioneAreaRiservataService verticalizzazioneAreaRiservataService;

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setFoRichiesteService(FoRichiesteService foRichiesteService) {

	this.foRichiesteService = foRichiesteService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setVerticalizzazioneAreaRiservataService(IVerticalizzazioneAreaRiservataService verticalizzazioneAreaRiservataService) {

	this.verticalizzazioneAreaRiservataService = verticalizzazioneAreaRiservataService;
    }

    private String recuperaListaMittentiInvioMailReg() {

	if (this.verticalizzazioneAreaRiservataService.isAttiva()) {
	    log.debug("verificaInvioMailReg: areariservata attiva");
	    String listaIdCaller = this.verticalizzazioneAreaRiservataService.getIDCallersPerInvioMailNuovoUtenteRegistrato();
	    if (StringUtils.isNotBlank(listaIdCaller)) {
		return listaIdCaller.trim();
	    }
	}
	return null;
    }

    private String recuperaMailtipoInvioMailReg() {

	if (this.verticalizzazioneAreaRiservataService.isAttiva()) {
	    log.debug("verificaInvioMailReg: areariservata attiva");
	    Mailtipo mail = this.verticalizzazioneAreaRiservataService.getModelloPerInvioMailNuovoUtenteRegistrato();
	    if (mail != null) {
		return mail.getId().getCodice().toString();
	    }
	}
	return null;
    }

    private boolean isInvioMail(InserimentoAnagrafeRequest inserimentoAnagrafeRequest) {

	if (AUTH_TYPE_REG.equalsIgnoreCase(inserimentoAnagrafeRequest.getTipoInserimento())
		|| AUTH_TYPE_REG_SC.equalsIgnoreCase(inserimentoAnagrafeRequest.getTipoInserimento())) {
	    boolean inviaMail = this.verticalizzazioneAreaRiservataService.isAttiva()
		    && this.verticalizzazioneAreaRiservataService.isInvioMailNuovoUtenteRegistrato();
	    if (inviaMail) {
		Properties props = externalDBResolver.checkToken(inserimentoAnagrafeRequest.getToken());
		if (props != null) {
		    String tokenUserid = props.getProperty(WebConstants.USER_ID);
		    String tokenContesto = props.getProperty(WebConstants.TOKEN_INFO_CONTESTO);
		    String listaMittenti = recuperaListaMittentiInvioMailReg();
		    if (StringUtils.isNotBlank(listaMittenti) && WebConstants.TOKEN_INFO_CONTESTO_APP.equalsIgnoreCase(tokenContesto)
			    && listaMittenti.toUpperCase().indexOf(tokenUserid) >= 0) {
			String codiceMailTipo = StringUtils.defaultString(recuperaMailtipoInvioMailReg()).trim();
			if (StringUtils.isNotBlank(codiceMailTipo) && Utilities.isInteger(codiceMailTipo)) {
			    Integer codMail = Integer.parseInt(codiceMailTipo);
			    Mailtipo m = mailtipoService.findById(new PkId(codMail));
			    if (m != null) {
				return true;
			    }
			}
		    }
		}
	    }
	}
	return false;
    }

    public InserimentoAnagrafeResponse inserimentoAnagrafe(InserimentoAnagrafeRequest inserimentoAnagrafeRequest) {

	log.debug("inserimentoAnagrafeResponse: Richiesta di inserimento/aggiornamento anagrafe");
	setORMHelper(WebConstants.SOFTWARE_TT, inserimentoAnagrafeRequest.getToken());
	InserimentoAnagrafeResponse response = new InserimentoAnagrafeResponse();
	log.debug("inserimentoAnagrafeResponse: Costruisco le business rules");
	AnagrafeBusinessRules anagrafeBusinessRules = new AnagrafeBusinessRules(true, true);
	SigeproBusinessRules.setClassRules(AnagrafeBusinessRules.class, anagrafeBusinessRules);
	Anagrafe entity = null;
	boolean isInvioMail = isInvioMail(inserimentoAnagrafeRequest);
	try {
	    log.debug("inserimentoAnagrafeResponse: eseguo il DTO");
	    entity = requestToEntityDTO(inserimentoAnagrafeRequest);
	    String cf = entity.getCodicefiscale();
	    log.debug("inserimentoAnagrafeResponse: inserisco / aggiorno");
	    if (isInvioMail) {
		// in caso di invio mail verifico che l'utente non sia già registrato.
		// se già registrato devo verificare che la mail non sia preente altrimenti mando uno schianto
		boolean rilanciaEccezione = true;
		String messaggioEccezione = "Non sono state indicati i riferimenti mail per la registrazione";
		String mailRegistrazione = StringUtils.defaultString(inserimentoAnagrafeRequest.getDatiAnagrafici().getEmail());
		String pecRegistrazione = StringUtils.defaultString(inserimentoAnagrafeRequest.getDatiAnagrafici().getPec());
		if (!(StringUtils.isBlank(mailRegistrazione) && StringUtils.isBlank(pecRegistrazione))) {
		    messaggioEccezione = "Il CODICE FISCALE Non può essere nullo";
		    if (StringUtils.isNotBlank(cf)) {
			List<Anagrafe> anas = anagrafeService.findByCf(cf, WebConstants.PERSONA_FISICA, true);
			if (anas.isEmpty()) {
			    rilanciaEccezione = false; // utente non registrato posso proseguire
			} else {
			    if (anas.size() == 1) {
				String email = StringUtils.defaultString(anas.get(0).getEmail());
				String pec = StringUtils.defaultString(anas.get(0).getPec());
				if (StringUtils.isNotBlank(pec)
					&& (pec.equalsIgnoreCase(mailRegistrazione) || pec.equalsIgnoreCase(pecRegistrazione))) {
				    rilanciaEccezione = false;
				}
				if (StringUtils.isNotBlank(email)
					&& (email.equalsIgnoreCase(mailRegistrazione) || email.equalsIgnoreCase(pecRegistrazione))) {
				    rilanciaEccezione = false;
				}
				messaggioEccezione = "L'utente risulta già registrato con una mail differente. Rivolgersi all'ente.";
				if (StringUtils.isBlank(pec) && StringUtils.isBlank(email)) {
				    messaggioEccezione = "L'utente risulta già registrato nella banca dati. Rivolgersi all'ente per farsi rilasciare le credenziali.";
				}
			    } else {
				messaggioEccezione = "L'utente risulta già registrato nella banca dati dell'ente. Rivolgersi all'ente.";
			    }
			}
		    }
		}
		if (rilanciaEccezione) {
		    throw new SecurityException(messaggioEccezione);
		}
	    }
	    boolean inserisci = false;
	    if (StringUtils.isNotBlank(cf)) {
		List<Anagrafe> anags = anagrafeService.findByCf(cf, WebConstants.PERSONA_FISICA, true);
		if (anags.isEmpty()) {
		    // non ho trovato anagrafiche abilitate con quel CF
		    // la inserisco
		    inserisci = true;
		}
		if (inserisci && entity.getFlagDisabilitato() > 0) {
		    log.error("inserimentoAnagrafeResponse: L'anagrafica {} e' gia' disabilitata", entity);
		    ErroreType errore = new ErroreType();
		    errore.setNumeroErrore("00099");
		    errore.setDescrizione("L'anagrafica " + entity + " e' gia' disabilitata");
		    response.setErrori(errore);
		    resetThreadLocalVars();
		    return response;
		}
	    }
	    if (inserisci) {
		anagrafeService.insert(entity);
	    } else {
		entity = anagrafeService.bindDomainObject(entity, PkId.class, "id.codice");
	    }
	    if (entity != null) {
		log.debug("inserimentoAnagrafeResponse: inserita aggiornata l'anagrafe [{}]", entity.getId());
		RiferimentiAnagrafeType id = new RiferimentiAnagrafeType();
		id.setCodiceanagrafe(BigInteger.valueOf(entity.getId().getCodice()));
		id.setIdcomune(entity.getId().getIdcomune());
		response.setRiferimentiAnagrafe(id);
	    } else {
		log.error("inserimentoAnagrafeResponse: il binddomainobject ha tornato null");
		ErroreType errore = new ErroreType();
		errore.setNumeroErrore("-1");
		errore.setDescrizione("Non è stato possibile aggiornare l'anagrafica");
		response.setErrori(errore);
	    }
	} catch (Exception e) {
	    log.error("inserimentoAnagrafeResponse: {}", e.getMessage());
	    ErroreType errore = new ErroreType();
	    StringBuilder dettaglioErrore = new StringBuilder();
	    if (e instanceof BaseValidationException) {
		List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
		for (InvalidValue invalidValue : ivs) {
		    String messaggio = invalidValue.getPropertyName() + " " + invalidValue.getMessage();
		    dettaglioErrore.append(messaggio).append("\n");
		}
		errore.setNumeroErrore("01");
	    } else {
		dettaglioErrore.append(e.getMessage());
		errore.setNumeroErrore("02");
	    }
	    log.error("inserimentoAnagrafeResponse: dettaglio Errore {}", dettaglioErrore);
	    errore.setDescrizione(dettaglioErrore.toString());
	    response.setErrori(errore);
	}
	if (response.getErrori() == null && AUTH_TYPE_REG.equalsIgnoreCase(inserimentoAnagrafeRequest.getTipoInserimento())
		|| AUTH_TYPE_REG_SC.equalsIgnoreCase(inserimentoAnagrafeRequest.getTipoInserimento())) {
	    boolean inserisceFoRichiesta = true;
	    boolean inviaMail = this.verticalizzazioneAreaRiservataService.isAttiva()
		    && this.verticalizzazioneAreaRiservataService.isInvioMailNuovoUtenteRegistrato();
	    if (inviaMail) {
		Properties props = externalDBResolver.checkToken(inserimentoAnagrafeRequest.getToken());
		if (props != null) {
		    String tokenUserid = props.getProperty(WebConstants.USER_ID);
		    String tokenContesto = props.getProperty(WebConstants.TOKEN_INFO_CONTESTO);
		    String listaMittenti = recuperaListaMittentiInvioMailReg();
		    if (StringUtils.isNotBlank(listaMittenti) && WebConstants.TOKEN_INFO_CONTESTO_APP.equalsIgnoreCase(tokenContesto)
			    && listaMittenti.toUpperCase().indexOf(tokenUserid) >= 0) {
			String codiceMailTipo = StringUtils.defaultString(recuperaMailtipoInvioMailReg()).trim();
			if (StringUtils.isNotBlank(codiceMailTipo) && Utilities.isInteger(codiceMailTipo)) {
			    Integer codMail = Integer.parseInt(codiceMailTipo);
			    Mailtipo m = mailtipoService.findById(new PkId(codMail));
			    if (m != null) {
				Integer codiceAnagrafe = entity.getId().getCodice();
				boolean forzaCreazionePassword = true;
				boolean mailInviata = false;
				try {
				    mailInviata = anagrafeService.inviaMailUtente(m, codiceAnagrafe, forzaCreazionePassword);
				    log.debug("mail inviata? {}", mailInviata);
				    if (mailInviata) {
					inserisceFoRichiesta = !mailInviata;
				    }
				} catch (Exception e) {
				    log.error("Si è verificato un errore nell'invio mail all'utente " + entity.toString() + ". {}", e);
				    try {
					istanzeeventiService.insertEventoBackoffice("Si è verificao un errore nell'invio mail all'utente " +
						entity.toString() +
						". \n\n" +
						e.getMessage(), CategorieEventiBaseType.MAIL.name(), ORMHelper.getSoftware());
				    } catch (Exception e1) {
					log.error("Errore nella registrazione dell' evento " + entity.toString() + ". {}", e1);
				    }
				}
			    }
			}
		    }
		}
	    }
	    if (inserisceFoRichiesta) {
		log.debug("Entro in inserimento di richiesta FO per anagrafe {}", entity);
		if (StringUtils.isNotBlank(inserimentoAnagrafeRequest.getXmlDatiAnagrafici())) {
		    FoRichieste forr = new FoRichieste();
		    forr.setAnagrafe(entity);
		    forr.setCodicerichiesta(2); // 1 = RICHIESTA MODIFICA DATI, 2 = RICHIESTA NUOVA REGISTRAZIONE
		    forr.setDatarichiesta(Calendar.getInstance().getTime());
		    forr.setFlagLetto(Boolean.FALSE);
		    log.debug("Inserisco l'oggetto della richiesta per anagrafe {}", entity);
		    Oggetti o = new Oggetti();
		    o.setNomefile("Datianagrafica.xml");
		    o.setOggetto(inserimentoAnagrafeRequest.getXmlDatiAnagrafici().getBytes());
		    oggettiService.insert(o);
		    forr.setOggetto(o);
		    log.debug("Inserisco la richiesta per anagrafe {}", entity);
		    foRichiesteService.insert(forr);
		} else {
		    log.error("Non è stato possibile generare la richiesta di inserimento anagrafe per {}.", entity);
		    try {
			istanzeeventiService.insertEventoBackoffice(
				"Non è stato possibile generare la richiesta di inserimento anagrafe per " + entity + ".",
				CategorieEventiBaseType.AR_ALTRO.name(), ORMHelper.getSoftware());
		    } catch (Exception e1) {
			log.error("Errore nella registrazione dell' evento " + entity.toString() + ". {}", e1);
		    }
		}
	    }
	}
	resetThreadLocalVars();
	return response;
    }

    private Anagrafe requestToEntityDTO(InserimentoAnagrafeRequest request) {

	if (request == null || request.getDatiAnagrafici() == null) {
	    throw new IllegalArgumentException("Il parametro inserimentoAnagrafeRequest è nullo");
	}
	AnagrafeType anagrafeType = request.getDatiAnagrafici();
	Anagrafe result = new Anagrafe();
	result.setNome(anagrafeType.getNome());
	result.setNominativo(anagrafeType.getCognome());
	result.setCodicefiscale(anagrafeType.getCodiceFiscale());
	result.setPartitaiva(anagrafeType.getPartitaIva());
	result.setSesso(anagrafeType.getSesso());
	result.setTipoanagrafe(WebConstants.PERSONA_FISICA);
	result.setTipologia(0);
	if (BooleanUtils.isTrue(anagrafeType.isTecnico())) {
	    result.setTipologia(-1);
	}
	result.setStrongAuthId(anagrafeType.getStrongAuthId());
	result.setPassword(anagrafeType.getPassword());
	result.setTelefono(anagrafeType.getTelefono());
	result.setFax(anagrafeType.getFax());
	result.setEmail(anagrafeType.getEmail());
	result.setPec(anagrafeType.getPec());
	if (anagrafeType.getDataNascita() != null) {
	    result.setDatanascita(anagrafeType.getDataNascita().toGregorianCalendar().getTime());
	}
	if (anagrafeType.getComuneNascita() != null) {
	    Comuni comune = new Comuni();
	    comune.setCf(anagrafeType.getComuneNascita().getCodiceCatastale());
	    comune.setCodiceistat(anagrafeType.getComuneNascita().getCodiceIstat());
	    comune.setComune(anagrafeType.getComuneNascita().getComune());
	    result.setComuneNascita(comune);
	}
	if (anagrafeType.getResidenza() != null) {
	    result.setIndirizzo(anagrafeType.getResidenza().getIndirizzo());
	    result.setCap(anagrafeType.getResidenza().getCap());
	    result.setProvincia(StringUtils.left(anagrafeType.getResidenza().getProvincia(), 2));
	    result.setCitta(anagrafeType.getResidenza().getLocalita());
	    if (anagrafeType.getResidenza().getComune() != null) {
		Comuni comune = new Comuni();
		comune.setCf(anagrafeType.getResidenza().getComune().getCodiceCatastale());
		comune.setCodiceistat(anagrafeType.getResidenza().getComune().getCodiceIstat());
		comune.setComune(anagrafeType.getResidenza().getComune().getComune());
		result.setComuneResidenza(comune);
	    }
	}
	if (anagrafeType.getCorrispondenza() != null) {
	    result.setIndirizzocorrispondenza(anagrafeType.getCorrispondenza().getIndirizzo());
	    result.setCapcorrispondenza(anagrafeType.getCorrispondenza().getCap());
	    result.setProvinciacorrispondenza(StringUtils.left(anagrafeType.getCorrispondenza().getProvincia(), 2));
	    result.setCittacorrispondenza(anagrafeType.getCorrispondenza().getLocalita());
	    if (anagrafeType.getCorrispondenza().getComune() != null) {
		Comuni comune = new Comuni();
		comune.setCf(anagrafeType.getCorrispondenza().getComune().getCodiceCatastale());
		comune.setCodiceistat(anagrafeType.getCorrispondenza().getComune().getCodiceIstat());
		comune.setComune(anagrafeType.getCorrispondenza().getComune().getComune());
		result.setComunecorrispondenza(comune);
	    }
	}
	result.setNote(anagrafeType.getNote());
	result.setFlagDisabilitato(Integer.valueOf(0));
	result.setDataDisabilitato(null);
	if (BooleanUtils.isTrue(anagrafeType.isDisabilitato())) {
	    result.setFlagDisabilitato(Integer.valueOf(1));
	    if (anagrafeType.getDataDisabilitato() != null) {
		result.setDataDisabilitato(anagrafeType.getDataDisabilitato().toGregorianCalendar().getTime());
	    }
	}
	return result;
    }

    @Override
    @WebResult(name = "InserimentoAnagrafeResponse", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/anagrafe", partName = "InserimentoAnagrafeResponse")
    @WebMethod(operationName = "InserimentoPersonaGiuridica", action = "InserimentoPersonaGiuridica")
    public InserimentoAnagrafeResponse inserimentoPersonaGiuridica(
	    @WebParam(partName = "InserimentoPersonaGiuridicaRequest", name = "InserimentoPersonaGiuridicaRequest", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/anagrafe") InserimentoPersonaGiuridicaRequest inserimentoPersonaGiuridicaRequest) {

	log.debug("inserimentoAnagrafeResponse: Richiesta di inserimento/aggiornamento anagrafe");
	setORMHelper(WebConstants.SOFTWARE_TT, inserimentoPersonaGiuridicaRequest.getToken());
	InserimentoAnagrafeResponse response = new InserimentoAnagrafeResponse();
	log.debug("inserimentoAnagrafeResponse: Costruisco le business rules");
	AnagrafeBusinessRules anagrafeBusinessRules = new AnagrafeBusinessRules(true, true);
	SigeproBusinessRules.setClassRules(AnagrafeBusinessRules.class, anagrafeBusinessRules);
	try {
	    log.debug("inserimentoAnagrafeResponse: eseguo il DTO");
	    Anagrafe entity = requestToEntityDTO(inserimentoPersonaGiuridicaRequest);
	    log.debug("inserimentoAnagrafeResponse: inserisco / aggiorno");
	    entity = anagrafeService.bindDomainObject(entity, PkId.class, "id.codice");
	    if (entity != null) {
		log.debug("inserimentoAnagrafeResponse: inserita aggiornata l'anagrafe [{}]", entity.getId());
		RiferimentiAnagrafeType id = new RiferimentiAnagrafeType();
		id.setCodiceanagrafe(BigInteger.valueOf(entity.getId().getCodice()));
		id.setIdcomune(entity.getId().getIdcomune());
		response.setRiferimentiAnagrafe(id);
	    } else {
		log.error("inserimentoAnagrafeResponse: il binddomainobject ha tornato null");
		ErroreType errore = new ErroreType();
		errore.setNumeroErrore("-1");
		errore.setDescrizione("Non è stato possibile aggiornare l'anagrafica");
		response.setErrori(errore);
	    }
	} catch (Exception e) {
	    log.error("inserimentoAnagrafeResponse: {}", e.getMessage());
	    ErroreType errore = new ErroreType();
	    String dettaglioErrore = "";
	    if (e instanceof BaseValidationException) {
		List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
		for (InvalidValue invalidValue : ivs) {
		    String messaggio = invalidValue.getPropertyName() + " " + invalidValue.getMessage();
		    dettaglioErrore += messaggio + "\n";
		}
		errore.setNumeroErrore("01");
	    } else {
		dettaglioErrore = e.getMessage();
		errore.setNumeroErrore("02");
	    }
	    log.error("inserimentoAnagrafeResponse: dettaglio Errore {}", dettaglioErrore);
	    errore.setDescrizione(dettaglioErrore);
	    response.setErrori(errore);
	} finally {
	    resetThreadLocalVars();
	}
	return response;
    }

    private Anagrafe requestToEntityDTO(InserimentoPersonaGiuridicaRequest request) {

	if (request == null) {
	    throw new IllegalArgumentException("Il parametro InserimentoPersonaGiuridicaRequest è nullo");
	}
	if (request.getDatiAnagrafici() == null) {
	    throw new IllegalArgumentException("Il parametro InserimentoPersonaGiuridicaRequest è nullo");
	}
	AnagrafeGiuridicaType pg = request.getDatiAnagrafici();
	if (StringUtils.isBlank(pg.getCodiceFiscale()) && StringUtils.isBlank(pg.getPartitaIva())) {
	    throw new IllegalArgumentException("Non è possibile inserire / aggiornare una anagrafica con codice fiscale e partita nulli");
	}
	Anagrafe result = new Anagrafe();
	result.setNominativo(pg.getDenominazione());
	result.setCodicefiscale(pg.getCodiceFiscale());
	result.setPartitaiva(pg.getPartitaIva());
	result.setTipoanagrafe(WebConstants.PERSONA_GIURIDICA);
	result.setStrongAuthId(pg.getStrongAuthId());
	result.setPassword(pg.getPassword());
	result.setTelefono(pg.getTelefono());
	result.setTelefonocellulare(pg.getCellulare());
	result.setFax(pg.getFax());
	result.setEmail(pg.getEmail());
	result.setPec(pg.getPec());
	result.setReferente(pg.getReferente());
	if (StringUtils.isNotBlank(pg.getFormaGiuridica())) {
	    Formegiuridiche fg = new Formegiuridiche();
	    fg.setFormagiuridica(pg.getFormaGiuridica());
	    result.setFormagiuridica(fg);
	}
	if (pg.getDataCostituzione() != null) {
	    result.setDatanominativo(pg.getDataCostituzione().toGregorianCalendar().getTime());
	}
	if (pg.getSedeLegale() != null) {
	    result.setIndirizzo(pg.getSedeLegale().getIndirizzo());
	    result.setCap(pg.getSedeLegale().getCap());
	    result.setProvincia(StringUtils.left(pg.getSedeLegale().getProvincia(), 2));
	    result.setCitta(pg.getSedeLegale().getLocalita());
	    if (pg.getSedeLegale().getComune() != null) {
		Comuni comune = new Comuni();
		comune.setCf(pg.getSedeLegale().getComune().getCodiceCatastale());
		comune.setCodiceistat(pg.getSedeLegale().getComune().getCodiceIstat());
		comune.setComune(pg.getSedeLegale().getComune().getComune());
		result.setComuneResidenza(comune);
	    }
	}
	if (pg.getCorrispondenza() != null) {
	    result.setIndirizzocorrispondenza(pg.getCorrispondenza().getIndirizzo());
	    result.setCapcorrispondenza(pg.getCorrispondenza().getCap());
	    result.setProvinciacorrispondenza(StringUtils.left(pg.getCorrispondenza().getProvincia(), 2));
	    result.setCittacorrispondenza(pg.getCorrispondenza().getLocalita());
	    if (pg.getCorrispondenza().getComune() != null) {
		Comuni comune = new Comuni();
		comune.setCf(pg.getCorrispondenza().getComune().getCodiceCatastale());
		comune.setCodiceistat(pg.getCorrispondenza().getComune().getCodiceIstat());
		comune.setComune(pg.getCorrispondenza().getComune().getComune());
		result.setComunecorrispondenza(comune);
	    }
	}
	result.setRegditte(pg.getNrCCIAA());
	if (pg.getDataCCIAA() != null) {
	    result.setDataregditte(pg.getDataCCIAA().toGregorianCalendar().getTime());
	}
	if (pg.getComuneCCIAA() != null) {
	    Comuni comune = new Comuni();
	    comune.setCf(pg.getComuneCCIAA().getCodiceCatastale());
	    comune.setCodiceistat(pg.getComuneCCIAA().getCodiceIstat());
	    comune.setComune(pg.getComuneCCIAA().getComune());
	    result.setComunecomregditte(comune);
	}
	result.setRegtrib(pg.getNrTRIB());
	if (pg.getDataTRIB() != null) {
	    result.setDataregtrib(pg.getDataTRIB().toGregorianCalendar().getTime());
	}
	if (pg.getComuneTRIB() != null) {
	    Comuni comune = new Comuni();
	    comune.setCf(pg.getComuneTRIB().getCodiceCatastale());
	    comune.setCodiceistat(pg.getComuneTRIB().getCodiceIstat());
	    comune.setComune(pg.getComuneTRIB().getComune());
	    result.setComuneregtrib(comune);
	}
	result.setNumiscrrea(pg.getNrREA());
	result.setProvinciarea(pg.getProvinciaREA());
	if (pg.getDataREA() != null) {
	    result.setDataiscrrea(pg.getDataREA().toGregorianCalendar().getTime());
	}
	if (StringUtils.isNotBlank(pg.getNote())) {
	    result.setNote(pg.getNote());
	}
	if (pg.isDisabilitato() != null) {
	    if (BooleanUtils.isTrue(pg.isDisabilitato())) {
		result.setFlagDisabilitato(Integer.valueOf(1));
		if (pg.getDataDisabilitato() != null) {
		    result.setDataDisabilitato(pg.getDataDisabilitato().toGregorianCalendar().getTime());
		}
	    } else {
		result.setFlagDisabilitato(Integer.valueOf(0));
		result.setDataDisabilitato(null);
	    }
	}
	return result;
    }
}
