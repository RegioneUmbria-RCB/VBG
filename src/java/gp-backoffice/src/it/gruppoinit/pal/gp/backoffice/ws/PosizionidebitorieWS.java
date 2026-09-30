package it.gruppoinit.pal.gp.backoffice.ws;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.backoffice.definitions.posizionidebitorie.Posizionidebitorie;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.ErroreBackofficeType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.EsitoOperazioneType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.posizionidebitorie.InserisciPosizioneDaOnereRateizzatoRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.posizionidebitorie.InserisciPosizioneDaOnereRateizzatoResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.posizionidebitorie.InserisciPosizioneDaOnereRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.posizionidebitorie.InserisciPosizioneDaOnereResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.posizionidebitorie.PosizioneInseritaPerOnereType;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.PosizioniDebitorieIstanzeoneriBean;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

@javax.jws.WebService(serviceName = "PosizionidebitorieWsService", portName = "PosizionidebitorieSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/posizionidebitorie", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.posizionidebitorie.Posizionidebitorie")
public class PosizionidebitorieWS extends BaseWS implements Posizionidebitorie {

    private static final Logger log = LoggerFactory.getLogger(PosizionidebitorieWS.class);
    @Autowired
    private NodoPagamentiService nodoPagamentiService;
    @Autowired
    private IstanzeoneriService istanzeoneriService;
    @Autowired
    private IstanzeService istanzeService;

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.backoffice.schemas.messages.posizionidebitorie.Posizionidebitorie#inserisciPosizioneDaOnere(it.gruppoinit.sigepro.schemas.messages.posizionidebitorie.InserisciPosizioneDaOnereRequest  inserisciPosizioneDaOnereRequest )*
     */
    @Override
    public it.gruppoinit.pal.gp.backoffice.schemas.messages.posizionidebitorie.InserisciPosizioneDaOnereResponse inserisciPosizioneDaOnere(
	    it.gruppoinit.pal.gp.backoffice.schemas.messages.posizionidebitorie.InserisciPosizioneDaOnereRequest inserisciPosizioneDaOnereRequest) {

	log.debug("inserisciPosizioneDaOnere: entro nel metodo e valido la request");
	try {
	    validateRequest(inserisciPosizioneDaOnereRequest);
	} catch (Exception e) {
	    return erroreValidazioneRequest(e.getMessage());
	}
	log.debug("inserisciPosizioneDaOnere: request validata setto ormhelper");
	setORMHelper(WebConstants.SOFTWARE_TT, inserisciPosizioneDaOnereRequest.getToken());
	log.debug("inserisciPosizioneDaOnere:  ormhelper.idcomune {}. cerco istanze oneri con id {}", ORMHelper.getIdcomune(),
		inserisciPosizioneDaOnereRequest.getRiferimentoOnere().intValue());
	List<BigInteger> l = new ArrayList<BigInteger>();
	l.add(inserisciPosizioneDaOnereRequest.getRiferimentoOnere());
	try {
	    validaEsistenzaIstanzeOneri(l);
	} catch (Exception e) {
	    return erroreValidazioneRequest(e.getMessage());
	}
	log.debug("inserisciPosizioneDaOnere:  ormhelper.idcomune {}. cerco istanze oneri con id {} è presente  ",
		new Object[] { ORMHelper.getIdcomune(), inserisciPosizioneDaOnereRequest.getRiferimentoOnere().intValue() });
	Set<Integer> codiciIstanzeoneri = new HashSet<Integer>();
	codiciIstanzeoneri.add(inserisciPosizioneDaOnereRequest.getRiferimentoOnere().intValue());
	log.debug("inserisciPosizioneDaOnere: inserisco le posizioni debitorie ");
	List<PosizioniDebitorieIstanzeoneriBean> posizioniInserite = null;
	try {
	    posizioniInserite = nodoPagamentiService.inserisciPosizionidebitorieDaIstanzeOneri(codiciIstanzeoneri, false);
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("{}", e.getMessage(), e);
	    return erroreValidazioneRequest("Errore nella creazione delle posizioni debitorie: " + e.getMessage());
	}
	log.debug("inserisciPosizioneDaOnere: posizioni debitorie inserite");
	InserisciPosizioneDaOnereResponse res = new InserisciPosizioneDaOnereResponse();
	PosizioneInseritaPerOnereType p = popolaRiferimento(posizioniInserite.iterator().next());
	res.setIdDettaglioPosizioneDebitoria(p);
	return determinaEsitoNonRateizzato(res);
    }

    private PosizioneInseritaPerOnereType popolaRiferimento(PosizioniDebitorieIstanzeoneriBean next) {

	PosizioneInseritaPerOnereType p = new PosizioneInseritaPerOnereType();
	p.setRiferimentoOnere(BigInteger.valueOf(next.getCodiceIstanzeOneri()));
	if (next.getIdDettPosizioneDebitoria() != null) {
	    p.setRiferimentoPosizioneDebitoria(BigInteger.valueOf(next.getIdDettPosizioneDebitoria()));
	}
	if (StringUtils.isNotBlank(next.getMessaggioErrore())) {
	    p.setMessaggioErrore(next.getMessaggioErrore());
	}
	return p;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.backoffice.schemas.messages.posizionidebitorie.Posizionidebitorie#inserisciPosizioneDaOnereRateizzato(it.gruppoinit.sigepro.schemas.messages.posizionidebitorie.InserisciPosizioneDaOnereRateizzatoRequest  inserisciPosizioneDaOnereRateizzatoRequest )*
     */
    public it.gruppoinit.pal.gp.backoffice.schemas.messages.posizionidebitorie.InserisciPosizioneDaOnereRateizzatoResponse inserisciPosizioneDaOnereRateizzato(
	    it.gruppoinit.pal.gp.backoffice.schemas.messages.posizionidebitorie.InserisciPosizioneDaOnereRateizzatoRequest inserisciPosizioneDaOnereRateizzatoRequest) {

	log.debug("inserisciPosizioneDaOnere: entro nel metodo e valido la request");
	try {
	    validateRequest(inserisciPosizioneDaOnereRateizzatoRequest);
	} catch (Exception e) {
	    return erroreValidazioneRateizzatoRequest(e.getMessage());
	}
	log.debug("inserisciPosizioneDaOnere: request validata setto ormhelper");
	setORMHelper(WebConstants.SOFTWARE_TT, inserisciPosizioneDaOnereRateizzatoRequest.getToken());
	log.debug("inserisciPosizioneDaOnere:  ormhelper.idcomune {}. cerco istanze oneri con id {}", ORMHelper.getIdcomune(),
		inserisciPosizioneDaOnereRateizzatoRequest.getRiferimentoOnere());
	try {
	    validaEsistenzaIstanzeOneri(inserisciPosizioneDaOnereRateizzatoRequest.getRiferimentoOnere());
	} catch (Exception e) {
	    return erroreValidazioneRateizzatoRequest(e.getMessage());
	}
	Set<Integer> codiciIstanzeoneri = new HashSet<Integer>();
	codiciIstanzeoneri.addAll(getListaRiferimenti(inserisciPosizioneDaOnereRateizzatoRequest));
	log.debug("inserisciPosizioneDaOnere: inserisco le posizioni debitorie ");
	Set<PosizioneInseritaPerOnereType> posizioniInseriteBI = new HashSet<PosizioneInseritaPerOnereType>();
	List<PosizioniDebitorieIstanzeoneriBean> posizioniInserite = null;
	try {
	    posizioniInserite = nodoPagamentiService.inserisciPosizionidebitorieDaIstanzeOneri(codiciIstanzeoneri, true);
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("{}", e.getMessage(), e);
	    return erroreValidazioneRateizzatoRequest("Errore nella creazione delle posizioni debitorie: " + e.getMessage());
	}
	for (PosizioniDebitorieIstanzeoneriBean p : posizioniInserite) {
	    posizioniInseriteBI.add(popolaRiferimento(p));
	}
	log.debug("inserisciPosizioneDaOnere: posizioni debitorie inserite");
	InserisciPosizioneDaOnereRateizzatoResponse res = new InserisciPosizioneDaOnereRateizzatoResponse();
	res.getIdDettaglioPosizioneDebitoria().addAll(posizioniInseriteBI);
	return determinaEsito(res);
    }

    private InserisciPosizioneDaOnereResponse determinaEsitoNonRateizzato(InserisciPosizioneDaOnereResponse res) {

	int esito = 0; // tutto ok
	EsitoOperazioneType eop = new EsitoOperazioneType();
	PosizioneInseritaPerOnereType pot = res.getIdDettaglioPosizioneDebitoria();
	if (StringUtils.isNotBlank(pot.getMessaggioErrore())) {
	    esito += 1;
	    ErroreBackofficeType ebt = new ErroreBackofficeType();
	    ebt.setCodice(String.valueOf(pot.getRiferimentoOnere()));
	    ebt.setDescrizione(pot.getMessaggioErrore());
	    eop.getListaErrori().add(ebt);
	}
	eop.setEsito(esito);
	res.setEsito(eop);
	return res;
    }

    private InserisciPosizioneDaOnereRateizzatoResponse determinaEsito(InserisciPosizioneDaOnereRateizzatoResponse res) {

	int esito = 0; // tutto ok
	EsitoOperazioneType eop = new EsitoOperazioneType();
	List<PosizioneInseritaPerOnereType> idDettaglioPosizioneDebitoria = res.getIdDettaglioPosizioneDebitoria();
	for (PosizioneInseritaPerOnereType pot : idDettaglioPosizioneDebitoria) {
	    if (StringUtils.isNotBlank(pot.getMessaggioErrore())) {
		esito += 1;
		ErroreBackofficeType ebt = new ErroreBackofficeType();
		ebt.setCodice(String.valueOf(pot.getRiferimentoOnere()));
		ebt.setDescrizione(pot.getMessaggioErrore());
		eop.getListaErrori().add(ebt);
	    }
	}
	eop.setEsito(esito);
	res.setEsito(eop);
	return res;
    }

    private void validaEsistenzaIstanzeOneri(List<BigInteger> riferimentoOnere) throws Exception {

	for (BigInteger codiceistanzaoneri : riferimentoOnere) {
	    Istanzeoneri istonere = istanzeoneriService.findById(new PkId(codiceistanzaoneri.intValue()));
	    if (istonere == null) {
		throw new IllegalArgumentException("Dati non corretti. Istanze oneri con identificativo " + new PkId(codiceistanzaoneri.intValue())
			+ " non trovata nella base dati");
	    }
	    Istanze istanze = istanzeService.findById(new PkId(istonere.getIstanza().getId().getCodice()));
	    ORMHelper.setSoftware(istanze.getSoftware().getCodice());
	}
    }

    private Set<Integer> getListaRiferimenti(InserisciPosizioneDaOnereRateizzatoRequest inserisciPosizioneDaOnereRateizzatoRequest) {

	Set<Integer> ret = new HashSet<Integer>();
	List<BigInteger> riferimentoOnere = inserisciPosizioneDaOnereRateizzatoRequest.getRiferimentoOnere();
	for (BigInteger bigInteger : riferimentoOnere) {
	    ret.add(bigInteger.intValue());
	}
	return ret;
    }

    private InserisciPosizioneDaOnereRateizzatoResponse erroreValidazioneRateizzatoRequest(String messaggioErrore) {

	InserisciPosizioneDaOnereRateizzatoResponse res = new InserisciPosizioneDaOnereRateizzatoResponse();
	EsitoOperazioneType esito = new EsitoOperazioneType();
	esito.setEsito(500);
	ErroreBackofficeType ebt = new ErroreBackofficeType();
	ebt.setCodice("500");
	ebt.setDescrizione(messaggioErrore);
	esito.getListaErrori().add(ebt);
	res.setEsito(esito);
	return res;
    }

    private InserisciPosizioneDaOnereResponse erroreValidazioneRequest(String messaggioErrore) {

	InserisciPosizioneDaOnereResponse res = new InserisciPosizioneDaOnereResponse();
	EsitoOperazioneType esito = new EsitoOperazioneType();
	esito.setEsito(500);
	ErroreBackofficeType ebt = new ErroreBackofficeType();
	ebt.setCodice("500");
	ebt.setDescrizione(messaggioErrore);
	esito.getListaErrori().add(ebt);
	res.setEsito(esito);
	return res;
    }

    private void validateRequest(InserisciPosizioneDaOnereRateizzatoRequest request) throws Exception {

	if (request == null || StringUtils.isBlank(request.getToken()) || request.getRiferimentoOnere().isEmpty()) {
	    throw new IllegalArgumentException(
		    "Request nulla o non valida " + ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE));
	}
    }

    private void validateRequest(InserisciPosizioneDaOnereRequest request) throws Exception {

	if (request == null || StringUtils.isBlank(request.getToken()) || request.getRiferimentoOnere() == null) {
	    throw new IllegalArgumentException(
		    "Request nulla o non valida " + ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE));
	}
    }
}
