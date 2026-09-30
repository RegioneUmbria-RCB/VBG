package it.gruppoinit.pal.gp.pay.connector.fvgpay.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gov.digitpa.schemas._2011.pagamenti.v_6_2_0.CtRicevutaTelematica;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.FlussoPagamentoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.ImportoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.IstitutoAttestanteType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.NotificaEsitiPagamentoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.NotificaEsitiPagamentoTypeElenco;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.NotificaEsitiRegistrazioneType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.NotificaEsitiRegistrazioneTypeElenco;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.NotificaEsitiRevocaType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.Problem;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.RecapitoTelematicoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.RicevutaPagamentoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.RicevutaPagamentoType.EsitoPagamentoEnum;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.RichiestaPagamentoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.SoggettoPagatoreType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.StatoPagamentoFVGEnum;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.StatoPagamentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.StatoSingoloPagamentoType;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.PagoPAService;
import it.gruppoinit.pal.gp.pay.service.PayPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.service.helper.RTHelper;
import it.gruppoinit.pal.gp.pay.service.impl.PayPagamentiServiceImpl;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;

@Service
public class FvgPayBackendServiceImpl implements FvgPayBackendService {

    private static final Logger log = LoggerFactory.getLogger(FvgPayBackendServiceImpl.class);
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PagoPAService pagoPAService;
    @Autowired
    private PayPagamentiService payPagamentiService;

    @Override
    public void gestisciEsitoRevocaPagamento(NotificaEsitiRevocaType request) {

	log.debug("gestisciEsitoRevocaPagamento - request {}", request);
	// TODO implementare
    }

    @Override
    public void gestisciNotificaEsitiRegistrazione(NotificaEsitiRegistrazioneType request) {

	log.debug("gestisciNotificaEsitiRegistrazione - request {}", request);
	List<NotificaEsitiRegistrazioneTypeElenco> elenco = request.getElenco();
	for (NotificaEsitiRegistrazioneTypeElenco ner : elenco) {
	    String idPosizionePsp = ner.getIdDebito();
	    Problem problem = ner.getProblem();
	    log.debug("gestisciNotificaEsitiRegistrazione-cerco le posizioni debitorie per id_posizione_psp {}", idPosizionePsp);
	    List<PayPosizioniDebitorie> posizionis = payPosizioniDebitorieService.findAllByIdPosizionePSP(idPosizionePsp);
	    if (problem != null && StringUtils.isNotBlank(problem.getDetail())) {
		// segna la posizione come con errore / non acquisita
		segnaPosizioniConErrore(posizionis, problem);
		continue;
	    }
	    StatoPagamentoPosizioneDebitoriaType sp = ner.getStatoPagamentoPosizioneDebitoria();
	    // trova le posizioni per il campo ID_POSIZIONE_PSP
	    if (posizionis.size() == 0) {
		log.error("gestisciNotificaEsitiRegistrazione - posizione non trovata per id_posizione_psp {}", idPosizionePsp);
		continue;
	    }
	    // la posizione potrebbe essere rateizzata ed allora devo controllare se è stato pagato in unica soluzione
	    // oppure la singola rata.
	    // per noi una rata è una posizione debitoria quindi devo trovare tutte le posizioni debitorie con quel riferimento
	    // idDebito = ID_POSIZIONE_PSP ed aggiornare lo stato pagamento
	    // se pagato con unica soluzione allora aggiorno tutte le posizioni debitorie come pagate
	    // altrimenti segno i riferimenti iuv / avviso delle le rate / singole o tutte
	    if (posizionis.size() == 1) {
		// una posizione debitoria;
		gestisciEsitoRegistrazionePerSingolaPosizione(posizionis.get(0), sp.getStatoPagamentoRate().get(0), idPosizionePsp);// da verificare se rata o rata unica coincidono
	    } else {
		// più posizioni trovate per ID_POSIZIONE_PSP possibile rateizzazione;
		gestisciEsitoRegistrazionePerPosizioniRateizzate(posizionis, sp, idPosizionePsp);
	    }
	}
    }

    @Override
    public void gestisciNotificaEsitiPagamento(NotificaEsitiPagamentoType request) throws PayException {

	log.debug("gestisciNotificaEsitiPagamento - request {}", request);
	List<NotificaEsitiPagamentoTypeElenco> elenco = request.getElenco();
	// trova le posizioni per il campo ID_POSIZIONE_PSP
	// al momento non gestite posizioni rateizzate 
	// idDebito = ID_POSIZIONE_PSP ed aggiornare lo stato pagamento
	// se pagato con unica soluzione allora aggiorno tutte le posizioni debitorie come pagate
	for (NotificaEsitiPagamentoTypeElenco nep : elenco) {
	    String idDebito = nep.getIdDebito();
	    String iuv = nep.getIuv();
	    List<PayPosizioniDebitorie> pos = payPosizioniDebitorieService.findAllByIdPosizionePSP(idDebito);
	    if (nep.getStatoPagamentoPosizioneDebitoria() != null) {
		for (PayPosizioniDebitorie payPos : pos) {
		    StatoPosizioneType gestisciStatoPagamento = gestisciStatoPagamento(payPos, nep.getStatoPagamentoPosizioneDebitoria());
		    if (!gestisciStatoPagamento.isEsito()) {
			log.error("PosizioneDebitoria [{}] con errore {} - {} ", payPos.getId(), gestisciStatoPagamento.getCodiceErrore(),
				nep.getStatoPagamentoPosizioneDebitoria());
		    } else {
			PayPagamenti datiPag = this.payPagamentiService.getPagamentoByPosizioneDebitoria(payPos);
			if (datiPag != null) {
			    //dati di pagamento già presenti
			    StringBuilder sb = new StringBuilder("dati di pagamento già presenti per la posizione ")
				    .append(PkId.toStringId(payPos.getId())).append(" la notifica di pagamento è stata ignorata.");
			    log.warn("FVGPayPayConnector comunicaEsitoPagamentiAttesi: dati di pagamento già presenti per la posizione "
				    + sb.toString());
			} else {
			    String rtDecoded = null;
			    DatiPagamentoType datiPagamento = gestisciStatoPagamento.getDatiPagamento();
			    if (datiPagamento != null && datiPagamento.getRicevutaXml() != null) {
				try {
				    rtDecoded = IOUtils.toString(datiPagamento.getRicevutaXml().getInputStream(), "UTF-8");
				} catch (IOException e) {
				    throw new PayException(e);
				}
				CtRicevutaTelematica rtXml = RTHelper.parseRicevutaTelematica(rtDecoded);
				DatiPagamentoType datiPagXml = RTHelper.popolaDatiPagamentoDaRicevutaTelematica(rtXml, null);
				PayPagamenti payPagamenti = PayPagamentiServiceImpl.populateDomainObject(datiPagXml, payPos,
					gestisciStatoPagamento.getStato());
				this.payPagamentiService.registraAvvenutoPagamento(payPagamenti, payPos, rtDecoded);
			    } else {
				PayPagamenti payPagamenti = PayPagamentiServiceImpl.populateDomainObject(datiPagamento, payPos,
					gestisciStatoPagamento.getStato());
				this.payPagamentiService.registraAvvenutoPagamento(payPagamenti, payPos, rtDecoded);
			    }
			}
		    }
		}
	    }
	}
    }

    private void gestisciEsitoRegistrazionePerPosizioniRateizzate(List<PayPosizioniDebitorie> posizionis, StatoPagamentoPosizioneDebitoriaType sp,
	    String idPosizionePsp) {

	List<StatoSingoloPagamentoType> statoPagamentoRate = sp.getStatoPagamentoRate();
	int numeroRata = 1;
	for (StatoSingoloPagamentoType rata : statoPagamentoRate) {
	    PayPosizioniDebitorie payPos = getEsitoRegistrazionePerPosizioneConRata(posizionis, numeroRata, idPosizionePsp);
	    gestisciEsitoRegistrazionePerSingolaPosizione(payPos, rata, idPosizionePsp);
	    numeroRata++;
	}
    }

    private PayPosizioniDebitorie getEsitoRegistrazionePerPosizioneConRata(List<PayPosizioniDebitorie> posizionis, int numeroRata,
	    String idPosizionePsp) {

	for (PayPosizioniDebitorie payPosizioniDebitorie : posizionis) {
	    if (payPosizioniDebitorie.getNumRata().intValue() == numeroRata) {
		return payPosizioniDebitorie;
	    }
	}
	log.error("Posizione non trovata per il debito {} e numero rata {}", idPosizionePsp, numeroRata);
	throw new RuntimeException("Posizione non trovata per il debito " + idPosizionePsp + "  e numero rata " + numeroRata);
    }

    private void gestisciEsitoRegistrazionePerSingolaPosizione(PayPosizioniDebitorie payPos, StatoSingoloPagamentoType rata, String idPosizionePsp) {

	String iuv = rata.getIuv();
	log.debug("aggiorno i dati della posizione debitoria {} - con i dati dello stato pagamento {}", payPos, rata);
	String codiceAvviso = rata.getCodiceAvvisoPagamento();
	String descStatus = rata.getStatoPagamento();
	boolean doUpdate = false;
	// aggiorna lo stato della posizione debitoria come attivata_in_psp
	if (StringUtils.isNotBlank(payPos.getIuv())) {
	    doUpdate = true;
	    payPos.setIuv(iuv);
	}
	if (StringUtils.isNotBlank(payPos.getCodiceAvviso())) {
	    doUpdate = true;
	    payPos.setCodiceAvviso(codiceAvviso);
	}
	if (StringUtils.isNotBlank(payPos.getQrCode())) {
	    doUpdate = true;
	    payPos.setQrCode(this.pagoPAService.generaQRCode(payPos));
	}
	if (doUpdate) {
	    this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(payPos, StatiPagamento.ATTIVATO_IN_PSP, descStatus);
	}
	log.debug("aggiornamento completato");
    }

    private void segnaPosizioniConErrore(List<PayPosizioniDebitorie> posizionis, Problem problem) {

	for (PayPosizioniDebitorie payPos : posizionis) {
	    log.error("gestisciNotificaEsitiRegistrazione - errore nel caricamento posizione con id {} - problem: {}", payPos.getId(), problem);
	    this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(payPos, StatiPagamento.CON_ERRORE,
		    StringUtils.left(problem.getDetail(), 500));
	}
    }

    @Override
    public StatoPosizioneType gestisciStatoPagamento(PayPosizioniDebitorie payPos, StatoPagamentoPosizioneDebitoriaType spd) throws PayException {

	StatoPosizioneType retStatus = new StatoPosizioneType();
	retStatus.setEsito(false);
	String statoPagamento = spd.getStatoPagamento();
	StatoPagamentoFVGEnum stato = StatoPagamentoFVGEnum.fromValue(statoPagamento);
	log.debug("stato pagamento {}", statoPagamento);
	if (spd != null && spd.getStatoPagamentoUnicaSoluzione() != null) {
	    if (StringUtils.isNotBlank(spd.getStatoPagamentoUnicaSoluzione().getIuv()) && StringUtils.isBlank(payPos.getIuv())
		    && BooleanUtils.isFalse(payPos.getFlagOTF())) {
		// AGGIORNO LO IUV SOLO NEL CASO DI POSIZIONE DEBITORIA NON OTF
		// Siccome FVG NON GESTISCE IL CARRELLO DAL FRONT POSSO creare per più posizioni debitorie un solo
		// debito che avrà lo stesso IUV per più posizioni debitorie
		// quando le posizioni debitorie vengono aggiornate con lo stesso IUV		
		// allora Viene rilanciato errore sulla chiave univoca 
		// PAY_POSIZIONI_DEBITORIE.PAY_POSDEB_UIDX(IDCOMUNE,IUV)
		// Nel caso di OTF salvare lo iuv non è necessario almeno per FVG pay
		// dove lo stato si verifica per id_debito che sarebbe PAY_POSIZIONI_DEBITORIE.ID_POSIZIONE_PSP
		payPos.setIuv(spd.getStatoPagamentoUnicaSoluzione().getIuv());
	    }
	    if (StringUtils.isNotBlank(spd.getStatoPagamentoUnicaSoluzione().getCodiceAvvisoPagamento())
		    && StringUtils.isBlank(payPos.getCodiceAvviso())) {
		payPos.setCodiceAvviso(spd.getStatoPagamentoUnicaSoluzione().getCodiceAvvisoPagamento());
	    }
	}
	PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(retStatus, payPos,
		payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
	switch (stato) {
	case PAGATO:
	case PAGATA:
	case PAGATO_EXTRA_SISTEMA:
	case PAGATO_CON_ERRORE:
	    StatoSingoloPagamentoType s = spd.getStatoPagamentoUnicaSoluzione();
	    List<FlussoPagamentoType> fps = s.getFlussiPagamento();
	    //   0. Pagamento eseguito 
	    //   1. Pagamento non eseguito 
	    //   2. Pagamento parzialmente eseguito 
	    //   3. Decorrenza termini 
	    //   4. Decorrenza termini parziale
	    boolean pagamentoOk = true;
	    RicevutaPagamentoType ricevutaPagamento = null;
	    if (fps != null) {
		for (FlussoPagamentoType fp : fps) {
		    ricevutaPagamento = fp.getRicevutaPagamento();
		}
	    }
	    if (stato.equals(StatoPagamentoFVGEnum.PAGATO_CON_ERRORE)) {
		log.debug("Lo stato è con errore, verifico se esito <> 0");
		if (ricevutaPagamento != null) {
		    log.debug("ricevutaPagamento.getEsitoPagamento() {}", ricevutaPagamento.getEsitoPagamento());
		    if (!EsitoPagamentoEnum._0.value().equals(ricevutaPagamento.getEsitoPagamento())) {
			// stato pagamento con errore
			// il pagamento è con errore e l'esito della ricevuta è <> 0
			//E nello stato della ricevuta_pagamento l'esito è con successo
			//"ricevuta_pagamento": {
			//        "data_ora_messaggio_ricevuta": "2021-11-16",
			//        "identificativo_messaggio_ricevuta": "0e9d65c8dba74159b2ae88140b219b1f",
			//        "esito_pagamento": "0",
			// Esito_pagamento nella ricevuta telematica significa che il pagameto è andato a buon fine
			pagamentoOk = false;
			break;
		    }
		}
	    }
	    DatiPagamentoType pagamenti = null;
	    if (ricevutaPagamento != null) {
		pagamenti = popolaDatiPagamentoDaFlussi(fps, pagamentoOk, payPos);
	    }
	    if (!pagamentoOk) {
		if (ricevutaPagamento != null) {
		    retStatus.setDatiPagamento(pagamenti);
		    retStatus.setStato(StatoPagamentoType.CON_ERRORE);
		} else {
		    retStatus.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		}
		break;
	    } else {
		if (pagamenti != null) {
		    retStatus.setDatiPagamento(pagamenti);
		    retStatus.setStato(StatoPagamentoType.NOTIFICATO_DA_PSP);
		} else {
		    // Il servizio di verifica stato torna pagato o spesso pagato con errore
		    // verificare se segnare pagato solamente quando c'è anche la ricevuta in modo da salvare le informazioni di pagamento
		    retStatus.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		}
	    }
	    break;
	case IN_ATTESA_DI_PAGAMENTO:
	case IN_PAGAMENTO:
	    retStatus.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
	    break;
	case ANNULLATO:
	case CANCELLATO:
	case ABBANDONATO:
	case REVOCATO:
	case PARZIALMENTE_REVOCATO:
	case NON_APPROVATO:
	    retStatus.setStato(StatoPagamentoType.ANNULLATO);
	    if (org.apache.commons.lang.BooleanUtils.isTrue(payPos.getFlagOTF())) {
		// caso OTF se non pagato/annullato/cancellato/abbandonato 
		// torno stato annullato
		retStatus.setStato(StatoPagamentoType.ANNULLATO);
	    } else {
		retStatus.setStato(StatoPagamentoType.ANNULLATO); // se cancellato, annullato
		if (stato.equals(StatoPagamentoFVGEnum.ABBANDONATO) || stato.equals(StatoPagamentoFVGEnum.NON_APPROVATO)) {
		    // se non OTF quindi modello 3 se abbandonato non faccio niente e lo lascio in attivato_psp
		    // nel caso di posizioni debitorie
		    retStatus.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		}
	    }
	    break;
	case ERRORE:
	    retStatus.setStato(StatoPagamentoType.CON_ERRORE);
	    break;
	case IN_ELABORAZIONE:
	case IN_ATTESA_DI_ESITO:
	case PARZIALMENTE_PAGATO:
	case IN_ATTESA_DI_RICHIESTA:
	    retStatus.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
	    break;
	case CHIUSO:
	    break;
	default:
	    break;
	}
	retStatus.setEsito(true);
	return retStatus;
    }

    private DatiPagamentoType popolaDatiPagamentoDaFlussi(List<FlussoPagamentoType> fps, boolean pagamentoOk, PayPosizioniDebitorie payPos)
	    throws PayException {

	if (fps != null) {
	    for (FlussoPagamentoType fp : fps) {
		DatiPagamentoType pagamenti = new DatiPagamentoType();
		RichiestaPagamentoType richiestaPagamento = fp.getRichiestaPagamento();
		RicevutaPagamentoType ricevutaPagamento = fp.getRicevutaPagamento();
		if (richiestaPagamento != null) {
		    // Accediamo al primo elemento della lista perchè paghiamo una sola posizione
		    BigDecimal importo = null;
		    if (ricevutaPagamento != null) {
			ImportoType importoTotalePagato = ricevutaPagamento.getImportoTotalePagato();
			importo = BigDecimal.valueOf(importoTotalePagato.getImporto());
			// pagamenti.setIur(ricevutaPagamento.get);
			IstitutoAttestanteType istitutoAttestante = ricevutaPagamento.getIstitutoAttestante();
			if (istitutoAttestante != null) {
			    if (istitutoAttestante.getIdentificativoUnivocoAttestante() != null) {
				pagamenti.setIdPSP(istitutoAttestante.getIdentificativoUnivocoAttestante().getCodiceIdentificativoUnivoco());
			    }
			    pagamenti.setRagioneSocialePSP(istitutoAttestante.getDenominazioneAttestante());
			}
			String idMesgRicevuta = ricevutaPagamento.getIdentificativoMessaggioRicevuta();
			pagamenti.setRiferimentiPagamento(idMesgRicevuta);
			pagamenti.setIuv(richiestaPagamento.getIdentificativoUnivocoVersamento());
			pagamenti.setModalitaPagamento(richiestaPagamento.getTipoVersamento());
			// Accediamo al primo elemento della lista perchè paghiamo una sola posizione 
			pagamenti.setImportoPagato(importo);
			// pagamenti.setImportoCommissioni(ctDatiSingoloVersamentoRPT.getCommissioneCaricoPA());
			// pagamenti.setDescrizioneCausale(richiestaPagamento.getCausaleVersamento());
			// pagamenti.setNote(ctDatiSingoloVersamentoRPT.getDatiSpecificiRiscossione());
			if (richiestaPagamento.getDataEsecuzionePagamento() != null) {
			    pagamenti.setDataOraPagamento(Utilities.getXMLGregorianCalendar(richiestaPagamento.getDataEsecuzionePagamento()));
			}
			SoggettoDebitoreType paySoggettiDebitori = new SoggettoDebitoreType();
			SoggettoPagatoreType soggettoPagatore = richiestaPagamento.getVersante();
			if (soggettoPagatore.getRecapitoPostale() != null) {
			    paySoggettiDebitori.setCap(soggettoPagatore.getRecapitoPostale().getCap());
			    paySoggettiDebitori.setCivico(soggettoPagatore.getRecapitoPostale().getCivico());
			    paySoggettiDebitori.setLocalita(soggettoPagatore.getRecapitoPostale().getLocalita());
			    paySoggettiDebitori.setProvincia(soggettoPagatore.getRecapitoPostale().getProvincia());
			    paySoggettiDebitori.setStato(soggettoPagatore.getRecapitoPostale().getNazione());
			    paySoggettiDebitori.setVia(soggettoPagatore.getRecapitoPostale().getIndirizzo());
			}
			if (soggettoPagatore.getIdentificativoUnivoco() != null) {
			    paySoggettiDebitori.setCfpi(soggettoPagatore.getIdentificativoUnivoco().getCodiceIdentificativoUnivoco());
			}
			paySoggettiDebitori.setNome(soggettoPagatore.getNome());
			paySoggettiDebitori.setCognome(soggettoPagatore.getCognome());
			if (soggettoPagatore.getRecapitoTelematico() != null && soggettoPagatore.getRecapitoTelematico().getElenco() != null
				&& !soggettoPagatore.getRecapitoTelematico().getElenco().isEmpty()) {
			    List<RecapitoTelematicoType> elenco = soggettoPagatore.getRecapitoTelematico().getElenco();
			    String mail = null;
			    for (RecapitoTelematicoType rtt : elenco) {
				if (StringUtils.defaultString(rtt.getTipo()).equalsIgnoreCase("mail")) {
				    mail = rtt.getRecapito();
				    break;
				}
			    }
			    paySoggettiDebitori.setEmail(mail);
			}
			pagamenti.setSoggettoPagatore(paySoggettiDebitori);
			if (ricevutaPagamento.getXmlRt() != null) {
			    DataSource ds = new ByteArrayDataSource(ricevutaPagamento.getXmlRt(), ContentType.APPLICATION_OCTET_STREAM.getMimeType());
			    DataHandler ricevutaXML = new DataHandler(ds);
			    pagamenti.setRicevutaXml(ricevutaXML);
			    String rtDecoded = null;
			    try {
				rtDecoded = IOUtils.toString(ricevutaPagamento.getXmlRt(), "UTF-8");
				CtRicevutaTelematica rtXml = RTHelper.parseRicevutaTelematica(rtDecoded);
				return RTHelper.popolaDatiPagamentoDaRicevutaTelematica(rtXml, null);
			    } catch (IOException e) {
				throw new PayException(e);
			    }
			}
		    }
		    return pagamenti;
		}
	    }
	}
	return null;
    }
}
