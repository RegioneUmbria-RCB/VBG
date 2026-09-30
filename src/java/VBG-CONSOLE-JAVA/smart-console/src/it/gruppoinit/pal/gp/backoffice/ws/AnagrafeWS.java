package it.gruppoinit.pal.gp.backoffice.ws;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.AnagrafeGiuridicaType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.AnagrafeType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.ErroreType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.InserimentoAnagrafeRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.InserimentoAnagrafeResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.InserimentoPersonaGiuridicaRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.RiferimentiAnagrafeType;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Formegiuridiche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.rules.AnagrafeBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

import java.math.BigInteger;
import java.util.List;

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

@WebService(serviceName = "AnagrafeService", portName = "AnagrafeSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/anagrafe", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.anagrafe.Anagrafe")
public class AnagrafeWS extends BaseWS implements it.gruppoinit.pal.gp.backoffice.definitions.anagrafe.Anagrafe {

    private static final Logger log = LoggerFactory.getLogger(AnagrafeWS.class);
    private AnagrafeService anagrafeService;

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    public InserimentoAnagrafeResponse inserimentoAnagrafe(InserimentoAnagrafeRequest inserimentoAnagrafeRequest) {

	log.debug("inserimentoAnagrafeResponse: Richiesta di inserimento/aggiornamento anagrafe");
	setORMHelper(WebConstants.SOFTWARE_TT, inserimentoAnagrafeRequest.getToken());
	InserimentoAnagrafeResponse response = new InserimentoAnagrafeResponse();
	log.debug("inserimentoAnagrafeResponse: Costruisco le business rules");
	AnagrafeBusinessRules anagrafeBusinessRules = new AnagrafeBusinessRules(true, true);
	SigeproBusinessRules.setClassRules(AnagrafeBusinessRules.class, anagrafeBusinessRules);
	try {
	    log.debug("inserimentoAnagrafeResponse: eseguo il DTO");
	    Anagrafe entity = requestToEntityDTO(inserimentoAnagrafeRequest);
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

    private Anagrafe requestToEntityDTO(InserimentoAnagrafeRequest request) {

	if (request == null) {
	    throw new IllegalArgumentException("Il parametro inserimentoAnagrafeRequest è nullo");
	}
	if (request.getDatiAnagrafici() == null) {
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
	if (anagrafeType.isTecnico() != null) {
	    if (anagrafeType.isTecnico().booleanValue()) {
		result.setTipologia(-1);
	    } else {
		result.setTipologia(0);
	    }
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
	if (StringUtils.isNotBlank(anagrafeType.getNote())) {
	    result.setNote(anagrafeType.getNote());
	}
	if (anagrafeType.isDisabilitato() != null) {
	    if (BooleanUtils.isTrue(anagrafeType.isDisabilitato())) {
		result.setFlagDisabilitato(Integer.valueOf(1));
		if (anagrafeType.getDataDisabilitato() != null) {
		    result.setDataDisabilitato(anagrafeType.getDataDisabilitato().toGregorianCalendar().getTime());
		}
	    } else {
		result.setFlagDisabilitato(Integer.valueOf(0));
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
	    }
	}
	return result;
    }
}
