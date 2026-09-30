/**
 * 
 */
package it.gruppoinit.pal.gp.pay.connector.mock;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.servlet.ServletContext;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.pay.command.GenerazioneFattureCommand;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaFatturaCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.AbstractPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.exception.PayInvalidRequestException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.service.PayPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.service.helper.ParametriConnettoreHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaPagamentoOnTheFlyResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoDocumentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.FormParamType;
import it.gruppoinit.pal.gp.pay.ws.schema.FormParametersType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;

/**
 * Connettore di pagamento mock per test
 * 
 * @author francol
 *
 */
public class MockPayConnector extends AbstractPayConnector implements IPayConnector {

    private static final Logger log = LoggerFactory.getLogger(MockPayConnector.class);
    @Autowired
    private ServletContext context;
    @Autowired
    private PaySessioniPagamentoService paySessioniPagamentoService;
    @Autowired
    private PayPagamentiService payPagamentiService;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) {

	ElencoPosizioniDebitorieEsitoType results = new ElencoPosizioniDebitorieEsitoType();
	for (PayRegistrazioniContabili regCont : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie posDeb : regCont.getPosizioniDebitorie()) {
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		esitoPos.setEsito(true);
		esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		esitoPos.setMessaggio(StatiPagamento.ATTIVATO_IN_PSP.description());
		esitoPos.setIdPosizione(BigInteger.valueOf(posDeb.getId().getCodice()));
		esitoPos.setIUV(UUID.randomUUID().toString());
		esitoPos.setCodiceAvviso("3-" + esitoPos.getIUV());
		esitoPos.setQrCode("QR-MOCK-" + esitoPos.getCodiceAvviso());
		if (regCont.getId() != null && regCont.getId().getCodice() != null) {
		    esitoPos.setIdRegistrazioneContabile(BigInteger.valueOf(regCont.getId().getCodice()));
		}
		results.getEsitoPosizione().add(esitoPos);
	    }
	}
	return results;
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline) {

	ElencoPosizioniDebitorieEsitoType results = new ElencoPosizioniDebitorieEsitoType();
	for (PayRegistrazioniContabili regCont : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie posDeb : regCont.getPosizioniDebitorie()) {
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		esitoPos.setEsito(true);
		if (pagatoOffline) {
		    esitoPos.setStato(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO);
		    if (datiRegistrazioniCommand.getPagamenti() != null && !datiRegistrazioniCommand.getPagamenti().isEmpty()) {
			DatiPagamentoType datiPagamento = datiRegistrazioniCommand.getPagamenti().iterator().next().getDatiPagamento();
			esitoPos.setMessaggio(
				StringUtils.left("Pagato con modalita': " + StringUtils.defaultString(datiPagamento.getModalitaPagamento()) +
						 ", Rif pagamento: " + StringUtils.defaultString(datiPagamento.getRiferimentiPagamento()) +
						 ", note: " + StringUtils.defaultString(datiPagamento.getNote()),
					2000));
		    }
		} else {
		    esitoPos.setStato(StatoPagamentoType.ANNULLATO);
		    esitoPos.setMessaggio(StatiPagamento.ANNULLATO.description());
		}
		esitoPos.setIdPosizione(BigInteger.valueOf(posDeb.getId().getCodice()));
		esitoPos.setIdRegistrazioneContabile(BigInteger.valueOf(regCont.getId().getCodice()));
		results.getEsitoPosizione().add(esitoPos);
	    }
	}
	return results;
    }

    @Override
    public boolean supportaGenerazioneFattura() {

	return false;
    }

    @Override
    public ElencoDocumentiEsitoType generaFatture(GenerazioneFattureCommand cmd) throws PayException {

	ElencoDocumentiEsitoType retValues = new ElencoDocumentiEsitoType();
	for (RichiestaFatturaCommand rfcmd : cmd.getRichieste()) {
	    EsitoDocumentoPosizioneDebitoriaType esitoFatt = new EsitoDocumentoPosizioneDebitoriaType();
	    esitoFatt.setEsito(true);
	    esitoFatt.setStato(StatoPagamentoType.fromValue(rfcmd.getPosizioneDebitoria().recuperaStatoCorrente().getStato()));
	    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoFatt, rfcmd.getPosizioneDebitoria(),
		    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(rfcmd.getPosizioneDebitoria().getId().getCodice()));
	    esitoFatt.setTipoDocumento(TipoDocumentoType.FATTURA);
	    try {
		URL fileUrl = this.context.getResource("/WEB-INF/applicationContext.xml");
		URLDataSource urlDs = new URLDataSource(fileUrl);
		DataHandler dh = new DataHandler(urlDs);
		esitoFatt.setDocumento(dh);
		esitoFatt.setNomeDocumento("Fattura_" + PkId.toStringId(rfcmd.getPosizioneDebitoria().getId()) + ".xml");
		esitoFatt.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
	    } catch (Exception e) {
		esitoFatt.setEsito(false);
		esitoFatt.setErroreTemporaneo(false);
		esitoFatt.setMessaggio("errore nella generazione della fattura per la posizione " + rfcmd.getPosizioneDebitoria().getId() + ": " + e);
	    }
	    retValues.getEsitoPosizione().add(esitoFatt);
	}
	return retValues;
    }

    @Override
    public boolean supportaAvvisoPagamento() {

	return true;
    }

    @Override
    public ElencoDocumentiEsitoType inviaAvvisiPagamento(PosizioniDebitorieCommand cmd) throws PayException {

	ElencoDocumentiEsitoType retValues = new ElencoDocumentiEsitoType();
	for (PayRegistrazioniContabili regPos : cmd.getRegistrazioniPosizioni()) {
	    for (PayPosizioniDebitorie posDeb : regPos.getPosizioniDebitorie()) {
		EsitoDocumentoPosizioneDebitoriaType esitoFatt = new EsitoDocumentoPosizioneDebitoriaType();
		esitoFatt.setEsito(true);
		PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoFatt, posDeb,
			payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(posDeb.getId().getCodice()));
		esitoFatt.setStato(StatoPagamentoType.fromValue(posDeb.recuperaStatoCorrente().getStato()));
		esitoFatt.setTipoDocumento(TipoDocumentoType.AVVISO);
		try {
		    URL fileUrl = this.context.getResource("/WEB-INF/resources/avviso_vuoto_connettore_mock.pdf");
		    URLDataSource urlDs = new URLDataSource(fileUrl);
		    DataHandler dh = new DataHandler(urlDs);
		    esitoFatt.setDocumento(dh);
		    esitoFatt.setNomeDocumento("Avviso_" + PkId.toStringId(posDeb.getId()) + ".pdf");
		    esitoFatt.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
		} catch (Exception e) {
		    esitoFatt.setEsito(false);
		    esitoFatt.setErroreTemporaneo(false);
		    esitoFatt.setMessaggio("errore nell'invio dell'avviso per la posizione " + posDeb.getId() + ": " + e);
		}
		retValues.getEsitoPosizione().add(esitoFatt);
	    }
	}
	return retValues;
    }

    @Override
    public boolean supportaRicevutaTelematica() {

	return true;
    }

    @Override
    public ElencoDocumentiEsitoType scaricaRicevuteTelematiche(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	ElencoDocumentiEsitoType retValues = new ElencoDocumentiEsitoType();
	for (PayPosizioniDebitorie pos : cmd.getPosizioni()) {
	    EsitoDocumentoPosizioneDebitoriaType esitoFatt = new EsitoDocumentoPosizioneDebitoriaType();
	    esitoFatt.setEsito(true);
	    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esitoFatt, pos,
		    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
	    esitoFatt.setStato(StatoPagamentoType.fromValue(pos.recuperaStatoCorrente().getStato()));
	    esitoFatt.setTipoDocumento(TipoDocumentoType.RICEVUTA);
	    try {
		URL fileUrl = this.context.getResource("/WEB-INF/resources/ricevuta_pagamento_mock.pdf");
		URLDataSource urlDs = new URLDataSource(fileUrl);
		DataHandler dh = new DataHandler(urlDs);
		esitoFatt.setDocumento(dh);
		esitoFatt.setNomeDocumento("Ricevuta_" + PkId.toStringId(pos.getId()) + ".pdf");
		esitoFatt.setStatoDocumento(StatoDocumentoType.DISPONIBILE);
	    } catch (Exception e) {
		esitoFatt.setEsito(false);
		esitoFatt.setErroreTemporaneo(false);
		esitoFatt.setMessaggio("errore nel download della ricevuta per la posizione " + pos.getId() + ": " + e);
	    }
	    retValues.getEsitoPosizione().add(esitoFatt);
	}
	return retValues;
    }

    @Override
    protected String generaDigestSessionePagamento(PayPosizioniDebitorie posDeb, String idSessionePagamento) throws PayException {

	return "thisisasecuritydigest";
    }

    @Override
    protected FormParametersType generaParametriSessionePagamento(PayPosizioniDebitorie posDeb, String idSessione, String digest)
	    throws PayException {

	FormParametersType form = new FormParametersType();
	FormParamType fpt = new FormParamType();
	fpt.setParamName("param1");
	fpt.setValue("value1");
	form.getParam().add(fpt);
	fpt = new FormParamType();
	fpt.setParamName("param_test");
	fpt.setValue("value_test");
	form.getParam().add(fpt);
	return form;
    }

    @Override
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams) {

	String[] idSex = reqParams.get("idSessione");
	String[] esParams = reqParams.get("esito");
	if (idSex != null && idSex.length > 0) {
	    String idSessione = idSex[0];
	    boolean esito = true;
	    if (esParams != null && esParams.length > 0) {
		esito = BooleanUtils.toBoolean(esParams[0]);
	    }
	    if (StringUtils.isNotBlank(idSessione)) {
		List<PaySessioniPagamento> sessioni = this.paySessioniPagamentoService.findBySessionId(idSessione);
		if (!sessioni.isEmpty()) {
		    //se ci sono più sessioni aperte sulla stessa posizione viene aggirornato l'esito della più recente (creata per ultima)		    
		    for (PaySessioniPagamento ps : sessioni) {
			PayPosizioniDebitorie posizioneDebitoria = ps.getPosizioneDebitoria();
			posizioneDebitoria = payPosizioniDebitorieService.findById(new PkId(posizioneDebitoria.getId().getCodice()));
			if (esito) {
			    PayPagamenti datiPag = new PayPagamenti();
			    try {
				BigDecimal importo = BigDecimal.ZERO;
				Set<PayDettaglioImporti> dettagliImporto = posizioneDebitoria.getDettagliImporto();
				for (PayDettaglioImporti di : dettagliImporto) {
				    importo = importo.add(di.getImporto());
				}
				datiPag.setDataSistema(Calendar.getInstance().getTime());
				datiPag.setDataPagamento(Calendar.getInstance().getTime());
				datiPag.setPosizioneDebitoria(posizioneDebitoria);
				datiPag.setImportoPagato(importo);
				datiPag.setIdFlussoRendicontazione(UUID.randomUUID().toString());
				datiPag.setIdPsp(UUID.randomUUID().toString());
				datiPag.setRagSocPsp(UUID.randomUUID().toString());
				datiPag.setIur(UUID.randomUUID().toString());
				payPagamentiService.registraAvvenutoPagamento(datiPag, posizioneDebitoria);
			    } catch (PayException e) {
				log.error("gestisciEsitoSessione - impossibile annullare la posizione a caiusa dell'errore: ", e);
			    }
			} else {
			    if (posizioneDebitoria != null && posizioneDebitoria.getFlagOTF()) {
				//le posizioni OTF con esito negativo vengono messe su stato ANNULLATO
				EsitoOperazionePosizioneDebitoriaType eopd = new EsitoOperazionePosizioneDebitoriaType();
				eopd.setIdPosizione(BigInteger.valueOf(posizioneDebitoria.getId().getCodice()));
				eopd.setStato(StatoPagamentoType.ANNULLATO);
				try {
				    payStatoPagamentiService.registraStatoPosizioneDebitoria(eopd, posizioneDebitoria);
				} catch (PayException e) {
				    log.error("gestisciEsitoSessione - impossibile annullare la posizione a caiusa dell'errore: ", e);
				}
			    }
			}
			ps.setEsito(esito);
			this.paySessioniPagamentoService.update(ps);
		    }
		    return sessioni.get(0);
		}
	    }
	}
	return null;
    }

    @Override
    public boolean supportaPagamentoOnTheFly() {

	return true;
    }

    @Override
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(PosizioniDebitorieCommand cmd) throws PayException {

	PayProfiliEntiCreditori cfgEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	AttivaPagamentoOnTheFlyResponseType retVal = new AttivaPagamentoOnTheFlyResponseType();
	for (PayRegistrazioniContabili prc : cmd.getRegistrazioniPosizioni()) {
	    Set<PayPosizioniDebitorie> posizioniDebitorie = prc.getPosizioniDebitorie();
	    for (PayPosizioniDebitorie payPosizioniDebitorie : posizioniDebitorie) {
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		esitoPos.setEsito(true);
		esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		esitoPos.setIdPosizione(BigInteger.valueOf(payPosizioniDebitorie.getId().getCodice()));
		Set<String> riferimentiClientPosizione = payPosizioniDebitorieService
			.findRiferimentiClientByPosizioneDebitoria(payPosizioniDebitorie.getId().getCodice());
		if (!riferimentiClientPosizione.isEmpty()) {
		    esitoPos.getRiferimentoClient().addAll(riferimentiClientPosizione);
		}
		retVal.getPosizioneInserita().add(esitoPos);
	    }
	}
	AttivaSessionePagamentoResponseType session = new AttivaSessionePagamentoResponseType();
	session.setEsito(true);
	session.setIdSessione("thisisamocknewsessionid-" + System.currentTimeMillis());
	session.setPayUrl(cfgEnte.getPayConnector().getUrlPortalePagamenti() + "?idSessione=" + session.getIdSessione() + "&profilo=" +
			  cfgEnte.getCfCodiceProfilo());
	retVal.setSessionePagamento(session);
	return retVal;
    }

    @Override
    public AttivaSessionePagamentoResponseType attivaSessionePagamento(PayPosizioniDebitorie payPosizioneDebitoria) throws PayException {

	PayProfiliEntiCreditori cfgEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	AttivaSessionePagamentoResponseType sessResp = new AttivaSessionePagamentoResponseType();
	if (payPosizioneDebitoria == null) {
	    throw new PayInvalidRequestException("Dati della richiesta di attivazione della sessione mancanti");
	}
	if (payPosizioneDebitoria.getId() == null || payPosizioneDebitoria.getId().getCodice() == null) {
	    throw new PayInvalidRequestException("Dati della posizione debitoria mancanti");
	}
	sessResp.setEsito(true);
	sessResp.setDescEsito("sessione di pagamento attivata");
	String idSessione = this.generaIdSessionePagamento(payPosizioneDebitoria);
	sessResp.setIdSessione(idSessione);
	sessResp.setSecurityDigest(this.generaDigestSessionePagamento(payPosizioneDebitoria, idSessione));
	sessResp.setFormParams(this.generaParametriSessionePagamento(payPosizioneDebitoria, idSessione, sessResp.getSecurityDigest()));
	sessResp.setPayUrl(cfgEnte.getPayConnector().getUrlPortalePagamenti() + "?idSessione=" + sessResp.getIdSessione() + "&profilo=" +
			   cfgEnte.getCfCodiceProfilo());
	return sessResp;
    }

    @Override
    public void modificaDataScadenzaPosizioneDebitoria(Integer idPosizioneDebitoria, Date nuovaDataScadenza) throws PayException {

	// log.debug("modificaDataScadenzaPosizioneDebitoria {}-{}", idPosizioneDebitoria, nuovaDataScadenza);
    }

    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException {

    }

    @Override
    public boolean supportaAttivaSessionePagamento() {

	return true;
    }

    @Override
    public boolean supportaModificaDataScadenza() {

	return true;
    }

    @Override
    public boolean supportaMolteCausaliRaggruppate() {

	return true;
    }

    @Override
    public List<IParameter> getListaParametriRichiesti() {

	return ParametriConnettoreHelper.getMappaParametriConnettore(this.getClass().getName());
    }

    @Override
    public IUVHelper generaIUV(PayPosizioniDebitorie pos, String identificativoCausalePerCalcolo) {

	return new IUVHelper(StringUtils.leftPad(String.valueOf(System.currentTimeMillis()), 15, "0"));
    }
}
