/**
 * 
 */
package it.gruppoinit.pal.gp.pay.connector.piemontepay.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.service.PiemontepayService;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.EsitoAggiornamentoType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.EsitoInserimentoType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.PosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.SoggettoType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.server.schema.CorpoNotifichePagamentoType.ElencoNotifichePagamento;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.server.schema.DatiTransazionePSPType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.server.schema.NotificaPagamentoType;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti.TipiNotifica;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.AvvisoPagamentoService;
import it.gruppoinit.pal.gp.pay.service.PagoPAService;
import it.gruppoinit.pal.gp.pay.service.PayPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;

/**
 * @author francol
 *
 */
@Service
public class PiemontePayServiceImpl implements PiemontepayService {

    private static final Logger log = LoggerFactory.getLogger(PiemontePayServiceImpl.class);
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;
    @Autowired
    private AvvisoPagamentoService avvisoPagamentoService;
    @Autowired
    private PayPagamentiService payPagamentiService;
    @Autowired
    private PagoPAService pagoPAService;

    @Override
    public EsitiPPAY registraEsitoInserimentoPosizioni(EsitoInserimentoType esitoInserimento) {

	List<PosizioneDebitoriaType> posInserite = esitoInserimento.getElencoPosizioniDebitorieInserite().getPosizioneDebitoriaInserita();
	boolean allDone = true;
	boolean someDone = false;
	Map<PaySoggettiDebitori, List<PayPosizioniDebitorie>> mapDestinatariAvvisi = new HashMap<PaySoggettiDebitori, List<PayPosizioniDebitorie>>();
	for (PosizioneDebitoriaType pos : posInserite) {
	    //PkId idPos = this.piemontePayConnector.parseIdPosizioneDebitoria(pos.getIdPosizioneDebitoria()); //PkId.fromStringId(pos.getIdPosizioneDebitoria());
	    PayPosizioniDebitorie payPos = payPosizioniDebitorieService.findByIdPosizionePSP(pos.getIdPosizioneDebitoria());
	    /*
	    if(payPos.getProfiloEnte() != null && payPos.getProfiloEnte().getId() != null && payPos.getProfiloEnte().getId().getCodice() != null) {
	    this.configurazionePagamentiService.configuraRequestPerEnteCrerditore(payPos.getProfiloEnte());
	    }
	    */
	    if (payPos == null) {
		allDone = allDone && false;
		log.error("errore nella ricezione dell'IUV per la posizione: " + pos.getIdPosizioneDebitoria() + ": posizione inesistente.");
	    } else if (StringUtils.isNotEmpty(payPos.getIuv())) {
		allDone = allDone && false;
		log.error("IUV già ricevuto per la posizione: " + pos.getIdPosizioneDebitoria());
	    } else {
		StatiPagamento newStatus;
		String descStatus = null;
		try {
		    if (StringUtils.isNotBlank(pos.getIUV())) {
			PaySoggettiDebitori soggPos = payPos.getSoggettoDebitore();
			List<PayPosizioniDebitorie> posSogg = mapDestinatariAvvisi.get(soggPos);
			if (posSogg == null) {
			    posSogg = new ArrayList<PayPosizioniDebitorie>();
			    mapDestinatariAvvisi.put(soggPos, posSogg);
			}
			posSogg.add(payPos);
			payPos.setIuv(pos.getIUV());
			payPos.setCodiceAvviso(pos.getCodiceAvviso());
			payPos.setQrCode(this.pagoPAService.generaQRCode(payPos));
			newStatus = StatiPagamento.ATTIVATO_IN_PSP;
		    } else {
			newStatus = StatiPagamento.CON_ERRORE;
			PayStatoPagamenti payStatoPos = payStatoPagamentiService.getStatoPosizioneDebitoria(payPos);
			if (payStatoPos == null || !payStatoPos.getStato().equals(newStatus.name())) {
			    descStatus = pos.getDescrizioneEsito();
			    if (StringUtils.isBlank(descStatus)) {
				EsitiPPAY esitoKo = EsitiPPAY.fromValue(pos.getCodiceEsito());
				descStatus = esitoKo.description();
			    }
			} else {
			    allDone = allDone && false;
			    log.error("La posizione: " + pos.getIdPosizioneDebitoria() + " è già nello stato " + newStatus.name());
			    continue;
			}
		    }
		    this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(payPos, newStatus, descStatus);
		    someDone = someDone || true;
		    allDone = allDone && true;
		} catch (Exception e) {
		    log.error("errore nella ricezione dell'IUV per la posizione: " + pos.getIdPosizioneDebitoria(), e);
		    allDone = allDone && false;
		}
	    }
	}
	for (PaySoggettiDebitori sendTo : mapDestinatariAvvisi.keySet()) {
	    try {
		this.avvisoPagamentoService.inviaAvvisoPagamento(sendTo, mapDestinatariAvvisi.get(sendTo));
	    } catch (PayException e) {
		log.error("errore nell'invio dell'avviso di pagamento a " + sendTo.getNome() + " " + StringUtils.defaultString(sendTo.getCognome()) +
			  ", email: " + sendTo.getEmail());
	    }
	}
	EsitiPPAY retEsito = allDone ? EsitiPPAY.CODE_000 : (someDone ? EsitiPPAY.CODE_050 : EsitiPPAY.CODE_051);
	return retEsito;
    }

    @Override
    public EsitiPPAY registraEsitoAggiornamentoPosizioni(EsitoAggiornamentoType esito) {

	List<PosizioneDebitoriaType> posInserite = esito.getElencoPosizioniDebitorieAggiornate().getPosizioneDebitoriaAggiornata();
	boolean allDone = true;
	boolean someDone = false;
	Map<PaySoggettiDebitori, List<PayPosizioniDebitorie>> mapDestinatariAvvisi = new HashMap<PaySoggettiDebitori, List<PayPosizioniDebitorie>>();
	for (PosizioneDebitoriaType pos : posInserite) {
	    //PkId idPos = this.piemontePayConnector.parseIdPosizioneDebitoria(pos.getIdPosizioneDebitoria());
	    PayPosizioniDebitorie payPos = payPosizioniDebitorieService.findByIdPosizionePSP(pos.getIdPosizioneDebitoria());
	    if (payPos == null) {
		allDone = allDone && false;
		log.error("errore nell'annullamento della posizione: " + pos.getIdPosizioneDebitoria() + ": posizione inesistente.");
	    } else {
		/*
		PayProfiliEntiCreditori profiloEnte = payPos.getProfiloEnte();
		if(profiloEnte != null && profiloEnte.getId() != null && profiloEnte.getId().getCodice() != null) {
		    this.configurazionePagamentiService.configuraRequestPerEnteCrerditore(profiloEnte);
		}
		*/
		/*
		PayStatoPagamenti posStatus = this.payStatoPagamentiService.getStatoPosizioneDebitoria(payPos);
		boolean pagatoOffline = posStatus != null
			&& posStatus.getStato().equals(PayStatoPagamenti.StatiPagamento.PAGATO_OFFLINE_DA_ANNULLARE.name());
			*/
		Boolean daAnnullareOffline = null;
		Boolean annullatoOffline = null;
		List<PayStatoPagamenti> statiPos = this.payStatoPagamentiService.getCronologiaPosizioneDebitoria(payPos.getId().getCodice());
		for (PayStatoPagamenti statoPos : statiPos) {
		    if (statoPos.getStato().equals(PayStatoPagamenti.StatiPagamento.PAGATO_OFFLINE_ANNULLATO.name())) {
			annullatoOffline = Boolean.TRUE;
		    } else if (statoPos.getStato().equals(PayStatoPagamenti.StatiPagamento.ANNULLATO.name())
			    && BooleanUtils.isNotTrue(annullatoOffline)) {
			annullatoOffline = Boolean.FALSE;
		    } else if (statoPos.getStato().equals(PayStatoPagamenti.StatiPagamento.PAGATO_OFFLINE_DA_ANNULLARE.name())
			    && BooleanUtils.isNotTrue(annullatoOffline)) {
			daAnnullareOffline = Boolean.TRUE;
		    } else if (statoPos.getStato().equals(PayStatoPagamenti.StatiPagamento.ANNULLAMENTO_RICHIESTO.name())
			    && BooleanUtils.isNotFalse(annullatoOffline)) {
			daAnnullareOffline = Boolean.FALSE;
		    }
		}
		EsitiPPAY esitoAnnullamento = EsitiPPAY.fromValue(pos.getCodiceEsito());
		if (daAnnullareOffline != null) {
		    if (esitoAnnullamento.equals(EsitiPPAY.CODE_000)) {
			PaySoggettiDebitori soggPos = payPos.getSoggettoDebitore();
			List<PayPosizioniDebitorie> posSogg = mapDestinatariAvvisi.get(soggPos);
			if (posSogg == null) {
			    posSogg = new ArrayList<PayPosizioniDebitorie>();
			    mapDestinatariAvvisi.put(soggPos, posSogg);
			}
			posSogg.add(payPos);
			payPos.setDataAnnullamento(new Date());
			StatiPagamento newStatus = BooleanUtils.isTrue(daAnnullareOffline) ? StatiPagamento.PAGATO_OFFLINE_ANNULLATO
				: StatiPagamento.ANNULLATO;
			try {
			    this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(payPos, newStatus, null);
			    someDone = someDone || true;
			    allDone = allDone && true;
			} catch (Exception e) {
			    log.error("errore nell'annullamento della posizione: " + pos.getIdPosizioneDebitoria(), e);
			    allDone = allDone && false;
			}
		    } else {
			log.error("Il PSP ha restituito un errore nell'operazione di annullamento della posizione debitoria: "
				+ pos.getIdPosizioneDebitoria() + ". codice errore: " + pos.getCodiceEsito() + ", msg: " + pos.getDescrizioneEsito());
		    }
		} else {
		    log.error(
			    "Messaggio di conferma avvenuto annullamento non coerente: nessuna richiesta di annullamento per la posizione debitoria "
				    + pos.getIdPosizioneDebitoria());
		}
	    }
	}
	for (PaySoggettiDebitori sendTo : mapDestinatariAvvisi.keySet()) {
	    try {
		this.avvisoPagamentoService.annullaAvvisoPagamento(sendTo, mapDestinatariAvvisi.get(sendTo));
	    } catch (PayException e) {
		log.error("errore nell'annullamento dell'avviso di pagamento di " + sendTo.getNome() + " "
			+ StringUtils.defaultString(sendTo.getCognome()) + ", email: " + sendTo.getEmail() + ": " + e.getMessage(), e);
	    }
	}
	EsitiPPAY retEsito = allDone ? EsitiPPAY.CODE_000 : (someDone ? EsitiPPAY.CODE_050 : EsitiPPAY.CODE_051);
	return retEsito;
    }

    @Override
    public EsitiPPAY registraNotificaPagamenti(ElencoNotifichePagamento notifichePagamento) {

	boolean allDone = true;
	boolean someDone = false;
	List<NotificaPagamentoType> notificchePag = notifichePagamento.getNotificaPagamento();
	for (NotificaPagamentoType notPag : notificchePag) {
	    PayPagamenti payPag = this.populateDomainObjects(notPag);
	    /*
	    PayPosizioniDebitorie payPos = payPag.getPosizioneDebitoria();
	    if(payPos != null && payPos.getProfiloEnte() != null && payPos.getProfiloEnte().getId().getCodice() != null) {
	    this.configurazionePagamentiService.configuraRequestPerEnteCrerditore(payPos.getProfiloEnte());
	    }
	    */
	    try {
		if (!pagato(payPag, notPag)) {
		    //registrazione di ciascun pagamento in una transazione a parte
		    this.payPagamentiService.registraAvvenutoPagamento(payPag, null);
		}
		allDone = allDone && true;
		someDone = someDone || true;
	    } catch (Exception e) {
		log.error("Errore nella registrazione del pagamento: ", e);
		allDone = allDone && false;
	    }
	}
	EsitiPPAY retEsito = allDone ? EsitiPPAY.CODE_000 : (someDone ? EsitiPPAY.CODE_050 : EsitiPPAY.CODE_051);
	return retEsito;
    }

    private PayPagamenti populateDomainObjects(NotificaPagamentoType notPag) {

	PayPagamenti payPag = new PayPagamenti();
	payPag.setFlagNotifica(TipiNotifica.PAGATO.value());
	payPag.setDataSistema(new Date());
	payPag.setIdPosizionePsp(notPag.getIdPosizioneDebitoria());
	payPag.setDataPagamento(Utilities.getDate(notPag.getDataEsitoPagamento()));
	DatiTransazionePSPType datiTrans = notPag.getDatiTransazionePSP();
	payPag.setModalitaPagamento(decodeModalitaPagamento(datiTrans.getTipoVersamento()));
	payPag.setImportoPagato(notPag.getImportoPagato());
	payPag.setImportoTransato(datiTrans.getImportoTransato());
	payPag.setImportoCommissioni(datiTrans.getImportoCommissioni());
	payPag.setIdFlussoRendicontazione(datiTrans.getIdFlussoRendicontazionePSP());
	payPag.setDescrizioneCausale(notPag.getDescrizioneCausaleVersamento());
	payPag.setIuv(notPag.getIUV());
	payPag.setIur(datiTrans.getIUR());
	payPag.setDataOraInizioTrans(Utilities.getDate(datiTrans.getDataOraAvvioTransazione()));
	payPag.setDataOraAutorizzazione(Utilities.getDate(datiTrans.getDataOraAutorizzazione()));
	payPag.setRifPagamento(notPag.getDatiSpecificiRiscossione());
	payPag.setNote(notPag.getNote());
	payPag.setIdPsp(datiTrans.getIdPSP());
	payPag.setRagSocPsp(datiTrans.getRagioneSocialePSP());
	SoggettoType pagatore = notPag.getSoggettoVersante();
	PayPosizioniDebitorie posDeb = null;
	if (StringUtils.isNotBlank(notPag.getIdPosizioneDebitoria())) {
	    //PkId pkPosDeb = this.piemontePayConnector.parseIdPosizioneDebitoria(notPag.getIdPosizioneDebitoria());
	    posDeb = this.payPosizioniDebitorieService.findByIdPosizionePSP(notPag.getIdPosizioneDebitoria());
	} else if (StringUtils.isNotBlank(notPag.getCodiceAvviso())) {
	    posDeb = this.payPosizioniDebitorieService.findByCodiceAvviso(notPag.getCodiceAvviso());
	} else if (StringUtils.isNotBlank(notPag.getIUV())) {
	    // TODO BOCCI 12/02/2020 (PIEMONTE PAY) Lo IUV potrebbe cambiare al momento del pagamento mentre il codice avviso no
	    // è stato deciso di spostarlo come ultimo criterio
	    posDeb = this.payPosizioniDebitorieService.findByIUV(notPag.getIUV());
	}
	payPag.setPosizioneDebitoria(posDeb);
	PaySoggettiDebitori sogDeb = populateDomainObject(pagatore);
	if (sogDeb == null && posDeb != null) {
	    sogDeb = posDeb.getSoggettoDebitore();
	}
	payPag.setSoggettoPagatore(sogDeb);
	return payPag;
    }

    private PaySoggettiDebitori populateDomainObject(SoggettoType subj) {

	PaySoggettiDebitori paySogg = null;
	if (subj != null) {
	    paySogg = new PaySoggettiDebitori();
	    paySogg.setCap(subj.getCAP());
	    paySogg.setCfPi(subj.getIdentificativoUnivocoFiscale());
	    paySogg.setCivico(subj.getCivico());
	    paySogg.setLocalita(subj.getLocalita());
	    paySogg.setProvincia(subj.getProvincia());
	    paySogg.setStato(subj.getNazione());
	    paySogg.setVia(subj.getIndirizzo());
	    paySogg.setEmail(subj.getEMail());
	    if (subj.getPersonaFisica() != null) {
		paySogg.setNome(subj.getPersonaFisica().getNome());
		paySogg.setCognome(subj.getPersonaFisica().getCognome());
	    } else if (subj.getPersonaGiuridica() != null) {
		paySogg.setNome(subj.getPersonaGiuridica().getRagioneSociale());
	    }
	}
	return paySogg;
    }

    private String decodeModalitaPagamento(String tipoVersamento) {

	return tipoVersamento;
    }

    private boolean pagato(PayPagamenti payPag, NotificaPagamentoType notPag) throws PayException {

	if (payPag.getPosizioneDebitoria() == null) {
	    throw new PayException("Posizione debitoria id: " + notPag.getIdPosizioneDebitoria() + ", IUV: " + notPag.getIUV() + " inesistente.");
	}
	PayStatoPagamenti statoPos = this.payStatoPagamentiService.getStatoPosizioneDebitoria(payPag.getPosizioneDebitoria());
	StatiPagamento statusVal = StatiPagamento.fromValue(statoPos.getStato());
	return (statusVal.equals(StatiPagamento.NOTIFICATO_DA_PSP) || statusVal.equals(StatiPagamento.RENDICONTATO_DA_IC));
    }
}
