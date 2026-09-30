/**
 * 
 */
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.service.PagoUmbriaService;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.Pagamento;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.TipoNotifica;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.TipoVoce;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.Transazione;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.Voce;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.esito.Esiti;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.esito.Esito;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.esito.InfoMessaggio;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.esito.StatoMessaggio;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti.TipiNotifica;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.PayPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PaySoggettiDebitoriService;
import it.gruppoinit.pal.gp.pay.ws.schema.RiferimentoPosizioneDebitoriaType;

/**
 * @author francol
 *
 */
@Service
public class PagoUmbriaServiceImpl implements PagoUmbriaService {

    private static final Logger log = LoggerFactory.getLogger(PagoUmbriaServiceImpl.class);
    private static final String TIPO_ID_SOGGETTO_PAGANTE = "CodiceFiscale";
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PayPagamentiService payPagamentiService;
    @Autowired
    private PaySoggettiDebitoriService paySoggettiDebitoriService;

    @Override
    public InfoMessaggio registraNotificaPagamenti(List<Pagamento> datiPag) {

	InfoMessaggio retInfo = new InfoMessaggio();
	Esiti es = new Esiti();
	retInfo.setEsiti(es);
	List<Esito> retEsiti = es.getEsito();
	StatoMessaggio statoMsg = StatoMessaggio.ELABORATO_CORRETTAMENTE;
	for (Pagamento pag : datiPag) {
	    Esito esito = new Esito();
	    esito.setElemento(pag.getRiferimentoPagamento().getIdPagamento());
	    log.debug("Elaboro idPagamento {}", pag.getRiferimentoPagamento().getIdPagamento());
	    try {
		PayPagamenti payPag = populateDomainObject(pag);
		this.payPagamentiService.registraAvvenutoPagamento(payPag, null);
		esito.setCodice(PagoUmbriaService.EsitiPagoUmbria.PA_000.name());
		esito.setDescrizione(PagoUmbriaService.EsitiPagoUmbria.PA_000.description());
	    } catch (PayException e) {
		log.error("PagoUmbriaPayConnector: registraNotificaPagamenti - errore nella ricezione della notifica per la posizione IUV=" +
			  pag.getIdentificativoUnivocoVersamento(),
			e);
		esito.setCodice(PagoUmbriaService.EsitiPagoUmbria.PA_100.name());
		esito.setDescrizione(PagoUmbriaService.EsitiPagoUmbria.PA_100.description());
		esito.setNote(e.getMessage());
		statoMsg = StatoMessaggio.ELABORATO_CON_ERRORI;
	    }
	    retEsiti.add(esito);
	}
	retInfo.setStato(statoMsg);
	return retInfo;
    }

    private PayPagamenti populateDomainObject(Pagamento datiPag) throws PayException {

	PayPagamenti payPag = new PayPagamenti();
	payPag.setDataSistema(new Date());
	payPag.setDataPagamento(Utilities.getDate(datiPag.getDataOraPagamento()));
	payPag.setDescrizioneCausale(datiPag.getDescrizioneCausale());
	TipoNotifica notType = datiPag.getEsito();
	switch (notType) {
	case ESEGUITO:
	    payPag.setFlagNotifica(TipiNotifica.PAGATO.value());
	    break;
	case REGOLATO:
	    payPag.setFlagNotifica(TipiNotifica.REGOLATO.value());
	    break;
	case INCASSO:
	    payPag.setFlagNotifica(TipiNotifica.INCASSATO.value());
	    break;
	default:
	    payPag.setFlagNotifica(TipiNotifica.NON_DEFINITO.value());
	}
	String iuv = datiPag.getRiferimentoPagamento().getIdPagamento();
	payPag.setIuv(iuv);
	log.debug("populateDomainObject cerco la posizione debitoria per IUV {}", iuv);
	PayPosizioniDebitorie payPos = this.payPosizioniDebitorieService.findByIUV(iuv);
	if (payPos == null && NumberUtils.isDigits(iuv)) {
	    log.error("populateDomainObject posizione debitoria per IUV {} null cerco per id", iuv);
	    try {
		Integer idPos = Integer.parseInt(iuv);
		payPos = this.payPosizioniDebitorieService.findById(new PkId(idPos));
	    } catch (NumberFormatException e) {
		//NOOP
	    }
	}
	if (payPos == null) {
	    throw new PayException("Posizione debitoria non trovata, IUV: " + datiPag.getIdentificativoUnivocoVersamento());
	}
	log.debug("populateDomainObject posizione debitoria per IUV {} - {} - {}", iuv, payPos.getId(), payPos.getIdPosizionePsp());
	payPag.setIdPosizionePsp(payPos.getIdPosizionePsp());
	payPag.setPosizioneDebitoria(payPos);
	payPag.setImportoPagato(datiPag.getImporto());
	Transazione trans = datiPag.getTransazione();
	if (trans != null) {
	    payPag.setImportoTransato(trans.getImportoTransato());
	    payPag.setDataOraAutorizzazione(Utilities.getDate(trans.getDataOraAutorizzazione()));
	    payPag.setDataOraInizioTrans(Utilities.getDate(trans.getDataOraTransazione()));
	    payPag.setRifPagamento(trans.getCodiceAutorizzazione());
	    payPag.setIur(trans.getIdTransazione());
	    if (trans.getDettaglioImportoTransato() != null) {
		for (Voce voce : trans.getDettaglioImportoTransato().getVoce()) {
		    TipoVoce tv = voce.getTipo();
		    if (tv != null) {
			if (tv.name().equals(TipoVoce.IMPORTO_COMMISSIONI.name())) {
			    payPag.setImportoCommissioni(voce.getImporto());
			} else if (tv.name().equals(TipoVoce.IMPORTO_TRANSATO.name())) {
			    payPag.setImportoTransato(voce.getImporto());
			}
		    }
		}
	    } else {
		payPag.setImportoCommissioni(BigDecimal.ZERO);
	    }
	    if (trans.getMezzoPagamento() != null) {
		StringBuilder sbMezzo = new StringBuilder();
		if (StringUtils.isNotEmpty(trans.getMezzoPagamento().getTipo())) {
		    sbMezzo.append(trans.getMezzoPagamento().getTipo());
		}
		if (StringUtils.isNotEmpty(trans.getMezzoPagamento().getDescrizione())) {
		    if (sbMezzo.length() > 0) {
			sbMezzo.append(" ");
		    }
		    sbMezzo.append(trans.getMezzoPagamento().getDescrizione());
		}
		payPag.setModalitaPagamento(sbMezzo.toString());
	    }
	    if (trans.getCanalePagamento() != null) {
		payPag.setIdPsp(trans.getCanalePagamento().getTipo());
		payPag.setRagSocPsp(trans.getCanalePagamento().getDescrizione());
	    }
	}
	PaySoggettiDebitori soggDeb = payPos.getSoggettoDebitore();
	if (datiPag.getPagante() != null && TIPO_ID_SOGGETTO_PAGANTE.equals(datiPag.getPagante().getTipo())) {
	    String cfPagante = datiPag.getPagante().getIdPagante();
	    List<PaySoggettiDebitori> soggetti = this.paySoggettiDebitoriService.findByCodiceFiscale(cfPagante, true);
	    if (!soggetti.isEmpty()) {
		soggDeb = soggetti.get(0);
	    } else {
		//se non esiste un anagrafica attiva con quel CF viene inserito il soggetto pagatore come nuova anagrafica
		soggDeb = new PaySoggettiDebitori();
		soggDeb.setCfPi(cfPagante);
		soggDeb.setNome(datiPag.getPagante().getDescrizione());
	    }
	}
	payPag.setSoggettoPagatore(soggDeb);
	payPag.setNote(datiPag.getNote());
	return payPag;
    }
}
