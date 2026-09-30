package it.gruppoinit.pal.gp.pay.connector.mip.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.AttualizzaAvvisoDati;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.AttualizzaAvvisoResp;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.DatiDebitore;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.DatiPagamento;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.DatiSingoloVersamento;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.DatiVersamento;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.IstitutoAttestante;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.NotificaStatoPagamentoDati;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.StatoDebito;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.PayPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.service.impl.PayPagamentiServiceImpl;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;

@Service
public class MIPBackendServiceImpl implements MIPBackendService {

    private static final Logger log = LoggerFactory.getLogger(MIPBackendServiceImpl.class);
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PayPagamentiService payPagamentiService;

    @Override
    public boolean gestisciNotificaStatoPagamento(NotificaStatoPagamentoDati request) {

	log.debug("gestisciNotificaEsitiPagamento - request {}", request);
	PayPosizioniDebitorie payPos = payPosizioniDebitorieService.findByCodiceAvviso(request.getCodiceAvviso());
	if (payPos != null) {
	    StatoPosizioneType gestisciStatoPagamento = gestisciStatoPagamento(payPos, request);
	    if (!gestisciStatoPagamento.getStato().equals(StatoPagamentoType.NOTIFICATO_DA_PSP)) {
		// l'esito pagamento non è una notifica di pagamento eseguito
		log.warn("gestisciNotificaEsitiPagamento stato posizione {}. stato {}, Request {}",
			new Object[] { payPos.getId(), gestisciStatoPagamento.getStato(), request });
		return true;
	    }
	    PayPagamenti datiPag = this.payPagamentiService.getPagamentoByPosizioneDebitoria(payPos);
	    if (datiPag != null) {
		log.warn("gestisciNotificaEsitiPagamento dati pagamento già registrati per la posizione debitoria {}. Request {}", payPos.getId(),
			request);
		return true;
	    }
	    String rtDecoded = null;
	    DatiPagamentoType datiPagamento = gestisciStatoPagamento.getDatiPagamento();
	    datiPagamento.setIuv(request.getDatiPagamento().getIuv());
	    datiPagamento.setIdPSP(payPos.getIdPosizionePsp());
	    Date d = Utilities.getDate(request.getDatiPagamento().getDatiSingoloPagamento().getDataEsitoSingoloPagamento(), "yyyy-MM-dd+HH:mm");
	    XMLGregorianCalendar xmlData = Utilities.getXMLGregorianCalendar(d);
	    datiPagamento.setDataOraPagamento(xmlData);
	    StatoPagamentoType statoPag = StatoPagamentoType.NOTIFICATO_DA_PSP;
	    PayPagamenti payPagamenti = PayPagamentiServiceImpl.populateDomainObject(datiPagamento, payPos, statoPag);
	    payPagamenti.setDataSistema(new Date());
	    if (request.getDatiPagamento().getDatiSingoloPagamento().getDataEsitoSingoloPagamento() != null) {
		payPagamenti.setDataPagamento(Utilities.getDate(xmlData));
	    }
	    if (StringUtils.isBlank(request.getDatiPagamento().getIuv())) {
		payPagamenti.setIuv(request.getDatiPagamento().getIuv());
	    }
	    try {
		this.payPagamentiService.registraAvvenutoPagamento(payPagamenti, payPos, rtDecoded);
		return true;
	    } catch (PayException e) {
		log.error("MIP gestitsciNotificaStatoPagamento -> registraAvvenutoPagamento:{} ", e.getMessage());
		return false;
	    }
	} else {
	    String msg = "MIP gestitsciNotificaStatoPagamento -> posizione per il codiceavviso" + request.getCodiceAvviso() + " non trovato ";
	    log.error(msg);
	}
	return false;
    }

    private StatoPosizioneType gestisciStatoPagamento(PayPosizioniDebitorie payPos, NotificaStatoPagamentoDati notificaStatoPagamentoDati) {

	StatoPosizioneType retStatus = new StatoPosizioneType();
	PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(retStatus, payPos,
		payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
	DatiPagamentoType pagamenti = popolaDatiPagamento(notificaStatoPagamentoDati, payPos);
	retStatus.setIdPosizione(BigInteger.valueOf(payPos.getId().getCodice()));
	retStatus.setIdRegistrazioneContabile(BigInteger.valueOf(payPos.getRegistrazioneContabile().getId().getCodice()));
	retStatus.setCodiceAvviso(notificaStatoPagamentoDati.getCodiceAvviso());
	retStatus.setIUV(notificaStatoPagamentoDati.getDatiPagamento().getIuv());
	retStatus.setDatiPagamento(pagamenti);
	retStatus.setEsito(true);
	switch (notificaStatoPagamentoDati.getDatiPagamento().getEsitoEnum()) {
	case PAGAMENTO_ESEGUITO:
	    retStatus.setStato(StatoPagamentoType.NOTIFICATO_DA_PSP);
	    break;
	case PAGAMENTO_NON_ESEGUITO:
	case PAGAMENTO_PARZIALMENTE_ESEGUITO:
	case DECORRENZA_TERMINI:
	case DECORRENZA_TERMINI_PARZIALE:
	    retStatus.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
	    break;
	default:
	    break;
	}
	return retStatus;
    }

    private DatiPagamentoType popolaDatiPagamento(NotificaStatoPagamentoDati notificaStatoPagamentoDati, PayPosizioniDebitorie payPos) {

	DatiPagamentoType pagamenti = new DatiPagamentoType();
	DatiPagamento datiPagamento = notificaStatoPagamentoDati.getDatiPagamento();
	Integer importoTotalePagato = datiPagamento.getImportoTotalePagato();
	pagamenti.setImportoPagato(getImportoInCentesimi(importoTotalePagato));
	IstitutoAttestante istitutoAttestante = notificaStatoPagamentoDati.getIstitutoAttestante();
	if (istitutoAttestante != null && istitutoAttestante.getIdentificativo() != null) {
	    pagamenti.setIdPSP(istitutoAttestante.getIdentificativo());
	    pagamenti.setRagioneSocialePSP(istitutoAttestante.getDenominazione());
	}
	pagamenti.setIuv(datiPagamento.getIuv());
	pagamenti.setIur(datiPagamento.getDatiSingoloPagamento().getIur());
	pagamenti.setDescrizioneCausale(datiPagamento.getDatiSingoloPagamento().getCausale());
	SoggettoDebitoreType paySoggettiDebitori = new SoggettoDebitoreType();
	DatiDebitore datiDebitore = notificaStatoPagamentoDati.getDatiDebitore();
	paySoggettiDebitori.setCfpi(datiDebitore.getIdentificativoUtente());
	paySoggettiDebitori.setNome(datiDebitore.getNome());
	paySoggettiDebitori.setCognome(datiDebitore.getCognome());
	paySoggettiDebitori.setEmail(datiDebitore.getEmail());
	pagamenti.setSoggettoPagatore(paySoggettiDebitori);
	return pagamenti;
    }

    private BigDecimal getImportoInCentesimi(Integer importo) {

	if (importo != null && importo != 0) {
	    BigDecimal bd = new BigDecimal(importo);
	    bd = bd.movePointLeft(2);
	    return bd;
	}
	return null;
    }

    @Override
    public void gestisciattualizzaAvviso(AttualizzaAvvisoDati request) {

	log.debug("gestisciAttualizzazione - request {}", request);
	PayPosizioniDebitorie payPos = payPosizioniDebitorieService.findByCodiceAvviso(request.getCodiceAvviso());
	Integer importo = null;
	BigDecimal imp2 = new BigDecimal(0);
	if (payPos != null) {
	    AttualizzaAvvisoResp resp = new AttualizzaAvvisoResp();
	    resp.setIdEnte(request.getIdEnte());
	    for (PayDettaglioImporti imp : payPos.getDettagliImporto()) {
		imp2.add(imp.getImporto());
	    }
	    importo = imp2.intValue();
	    DatiVersamento datiVersamento = new DatiVersamento();
	    datiVersamento.setImportoTotale(importo);
	    datiVersamento.setDescrizionePagamento(payPos.getCodiceAvviso());
	    //datiVersamento.setTassonomiaAvviso();
	    DatiSingoloVersamento datiSingoloVersamento = new DatiSingoloVersamento();
	    datiSingoloVersamento.setCausale(payPos.getDescrizioneCausale());
	    datiSingoloVersamento.setImporto(importo.intValue());
	    datiSingoloVersamento.setStato(Integer.parseInt(StatoDebito.PAGABILE.toString()));
	    if (payPos.getDataAnnullamento() != null) {
		datiSingoloVersamento.setStato(Integer.parseInt(StatoDebito.ANNULLATO.toString()));
	    }
	    if (payPos.getDataScadenza() != null) {
		datiSingoloVersamento.setStato(Integer.parseInt(StatoDebito.SCADUTO.toString()));
	    }
	    for (PayPagamenti pag : payPos.getPagamenti()) {
		datiVersamento.setTipoPagamento(Integer.valueOf(pag.getModalitaPagamento()));
		if (pag.getDataPagamento() != null && pag.getImportoPagato() != null) {
		    datiSingoloVersamento.setStato(Integer.parseInt(StatoDebito.PAGATO.toString()));
		}
	    }
	}
    }
}
