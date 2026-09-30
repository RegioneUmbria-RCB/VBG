/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.pay.dao.PayStatoPagamentiDAO;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.NotificheBackendService;
import it.gruppoinit.pal.gp.pay.service.NotificheRabbitService;
import it.gruppoinit.pal.gp.pay.service.PagoPAService;
import it.gruppoinit.pal.gp.pay.service.PayDocumentiService;
import it.gruppoinit.pal.gp.pay.service.PayPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayRichiesteService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.RiferimentiPosizioniDebitorieHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;

/**
 * @author francol
 *
 */
@Service
public class PayStatoPagamentiServiceImpl extends BaseServiceImpl<PayStatoPagamenti, PkId> implements PayStatoPagamentiService {

    private static final Logger log = LoggerFactory.getLogger(PayStatoPagamentiServiceImpl.class);
    @Autowired
    private PayStatoPagamentiDAO payStatoPagamentiDAO;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PayPagamentiService payPagamentiService;
    @Autowired
    private PayRichiesteService payRichiesteService;
    @Autowired
    private PagoPAService pagoPAService;
    @Autowired
    private PayDocumentiService payDocumentiService;
    @Autowired
    private NotificheRabbitService notificheRabbitService;
    @Autowired
    private NotificheBackendService notificheBackendService;

    @Override
    public void insert(PayStatoPagamenti entity) {

	if (validateEntity(entity)) {
	    this.payStatoPagamentiDAO.insert(entity);
	    log.debug("eseguo il primo flush {}", entity.getId());
	    this.payStatoPagamentiDAO.flush(); // DEVO COMMITTARE  E MANDARE LE OPERAZIONI A db.
	    log.debug("eseguo la commit {}", entity.getId());
	    this.payStatoPagamentiDAO.commit(); //le notifiche vanno su altro thread e devo rendere visibile lo stato all'altro thread
	    log.debug("eseguo il secondo flush {}", entity.getId());
	    this.payStatoPagamentiDAO.flush(); // DEVO COMMITTARE  E MANDARE LE OPERAZIONI A db.
	    // SE SONO IN QUESTO STADIO IL CONNETTORE MI HA INFORMATO DELLO STATO E NON DEVO ROLLBACKARE
	    this.notificaCambiamentoDiStato(entity);
	}
    }

    private void notificaCambiamentoDiStato(PayStatoPagamenti entity) {

	Integer idPosizione = entity.getPosizioneDebitoria().getId().getCodice();
	String cfCodiceProfilo = PayConfigurationHelper.getProfiloEnteCreditore().getCfCodiceProfilo();
	try {
	    log.debug("prima di notificaApiBackend {}", entity.getId());
	    notificheBackendService.notificaApiBackend(idPosizione, cfCodiceProfilo);
	} catch (Exception e1) {
	    String message = MessageFormat.format(
		    "Errore durante la notifica del cambiamento dello stato a API-BACKEND di una posizione debitoria: {0} per la posizione {1}",
		    e1.getMessage(), idPosizione);
	    log.error(message, e1);
	}
	try {
	    log.debug("prima di notificaStatoRabbitMQ {}", entity.getId());
	    notificheRabbitService.notificaStatoRabbitMQ(idPosizione, cfCodiceProfilo, entity.getId().getCodice());
	} catch (Exception e1) {
	    String message = MessageFormat.format(
		    "Errore durante la notifica del cambiamento dello stato a NODO-PAGAMENTI-RABBIT-MQ di una posizione debitoria: {0} per la posizione {1}",
		    e1.getMessage(), idPosizione);
	    log.error(message, e1);
	}
    }

    @Override
    public void update(PayStatoPagamenti entity) {

	if (validateEntity(entity)) {
	    this.payStatoPagamentiDAO.update(entity);
	}
    }

    @Override
    public void delete(PayStatoPagamenti entity) {

	if (isDeleteAllowed(entity)) {
	    this.payStatoPagamentiDAO.delete(entity);
	}
    }

    @Override
    public List<PayStatoPagamenti> findAll(Integer firstResult, Integer maxResult) {

	return this.payStatoPagamentiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public PayStatoPagamenti findById(PkId id) {

	return this.payStatoPagamentiDAO.findById(id);
    }

    @Override
    public List<PayStatoPagamenti> getCronologiaPosizioneDebitoria(Integer posDebId) {

	List<PayStatoPagamenti> payHistory = new ArrayList<>();
	if (posDebId != null) {
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterField<Integer> ff = (FilterField<Integer>) FilterUtils.equals("id.codice", posDebId, "posizioneDebitoria",
		    PayPosizioniDebitorie.class);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(ff);
	    ft.addRestriction(fr);
	    ft.addOrder(FilterUtils.order("dataEvento", OrderTypeEnum.DESC));
	    ft.addOrder(FilterUtils.order("id.codice", OrderTypeEnum.DESC));
	    payHistory = this.payStatoPagamentiDAO.findByFilterTable(ft);
	}
	return payHistory;
    }

    @Override
    protected Class<PayStatoPagamenti> getEntityClass() {

	return PayStatoPagamenti.class;
    }

    @Override
    public PayStatoPagamenti getStatoPosizioneDebitoria(PayPosizioniDebitorie posDeb) {

	PayStatoPagamenti actualStatus = null;
	List<PayStatoPagamenti> posHistory = this.getCronologiaPosizioneDebitoria(posDeb.getId().getCodice());
	if (!posHistory.isEmpty()) {
	    actualStatus = posHistory.get(0);
	}
	return actualStatus;
    }

    @Override
    public PayStatoPagamenti registraStatoPosizioneDebitoria(EsitoOperazionePosizioneDebitoriaType statoPosDeb, PayPosizioniDebitorie posDeb)
	    throws PayException {

	if (log.isDebugEnabled()) {
	    log.debug("registraStatoPosizioneDebitoria {}", statoPosDeb.getIdPosizione().intValue());
	}
	StatoPagamentoType statoPsp = statoPosDeb.getStato();
	StatiPagamento statoNodo = StatiPagamento.fromValue(statoPsp.name());
	if (posDeb == null) {
	    if (statoPosDeb.getIdPosizione() != null) {
		posDeb = this.payPosizioniDebitorieService.findById(new PkId(statoPosDeb.getIdPosizione().intValue()));
	    } else if (StringUtils.isNotBlank(statoPosDeb.getIUV())) {
		posDeb = this.payPosizioniDebitorieService.findByIUV(statoPosDeb.getIUV());
	    }
	    if (posDeb.recuperaStatoCorrente() == null) {
		posDeb.impostaStatoCorrente(this.getStatoPosizioneDebitoria(posDeb));
	    }
	}
	boolean updatePos = false;
	if (StringUtils.isNotBlank(statoPosDeb.getIUV()) && StringUtils.isBlank(posDeb.getIuv())) {
	    posDeb.setIuv(statoPosDeb.getIUV());
	    updatePos = true;
	    if (log.isInfoEnabled()) {
		log.info("registraStatoPosizioneDebitoria {} acquisito IUV {}", statoPosDeb.getIdPosizione().intValue(), statoPosDeb.getIUV());
	    }
	}
	if (StringUtils.isNotBlank(statoPosDeb.getCodiceAvviso()) && StringUtils.isBlank(posDeb.getCodiceAvviso())) {
	    posDeb.setCodiceAvviso(statoPosDeb.getCodiceAvviso());
	    updatePos = true;
	    if (log.isInfoEnabled()) {
		log.info("registraStatoPosizioneDebitoria {} acquisito codice avviso {}", statoPosDeb.getIdPosizione().intValue(),
			statoPosDeb.getCodiceAvviso());
	    }
	    //se l'esito non contiene il qrcode lo calcolo dal codice avviso
	    if (StringUtils.isBlank(statoPosDeb.getQrCode())) {
		statoPosDeb.setQrCode(this.pagoPAService.generaQRCode(posDeb));
	    }
	}
	if (StringUtils.isNotBlank(statoPosDeb.getQrCode()) && StringUtils.isBlank(posDeb.getQrCode())) {
	    posDeb.setQrCode(statoPosDeb.getQrCode());
	    updatePos = true;
	    if (log.isInfoEnabled()) {
		log.info("registraStatoPosizioneDebitoria {} acquisito qrcode {}", statoPosDeb.getIdPosizione().intValue(), statoPosDeb.getQrCode());
	    }
	}
	if (statoPsp.equals(StatoPagamentoType.TRASMESSO_A_PSP)) {
	    posDeb.setDataInvioAPsp(new Date());
	    updatePos = true;
	    if (log.isInfoEnabled()) {
		log.info("registraStatoPosizioneDebitoria {} aggiornata data invio a psp", statoPosDeb.getIdPosizione().intValue());
	    }
	} else if (statoPsp.equals(StatoPagamentoType.ANNULLATO) || statoPsp.equals(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO)) {
	    posDeb.setDataAnnullamento(new Date());
	    updatePos = true;
	    if (log.isInfoEnabled()) {
		log.info("registraStatoPosizioneDebitoria {} aggiornata data annullamento", statoPosDeb.getIdPosizione().intValue());
	    }
	} else if (statoPsp.equals(StatoPagamentoType.NOTIFICATO_DA_PSP) || statoPsp.equals(StatoPagamentoType.RENDICONTATO_DA_IC)
		|| statoPsp.equals(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO) || statoPsp.equals(StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE)) {
	    // se lo stato da registrare è NOTIFICATO_DA_PSP o RENDICONTATO_DA_IC o pagato offline recupero i dati del pagamento castando statoPosDeb a StatoPosizioneType
	    if (statoPosDeb instanceof StatoPosizioneType) {
		StatoPosizioneType datiStatoPos = (StatoPosizioneType) statoPosDeb;
		PayPagamenti payPag = PayPagamentiServiceImpl.populateDomainObject(datiStatoPos.getDatiPagamento(), posDeb, statoPsp);
		this.payPagamentiService.inserisciDatiPagamento(payPag, posDeb);
		if (log.isInfoEnabled()) {
		    log.info("registraStatoPosizioneDebitoria inseriti dati di pagamento per la posizione {} ",
			    statoPosDeb.getIdPosizione().intValue());
		}
		if (datiStatoPos.getDatiPagamento() != null && datiStatoPos.getDatiPagamento().getRicevutaXml() != null) {
		    payDocumentiService.salvaRicevutaXMLPerPosizioneDebitoria(posDeb, datiStatoPos.getDatiPagamento().getRicevutaXml());
		}
		if (log.isInfoEnabled()) {
		    log.info("registraStatoPosizioneDebitoria acquisita la ricevuta XML per la posizione {}",
			    statoPosDeb.getIdPosizione().intValue());
		}
	    }
	    //se mancano i dati di pagamento non ha senso settare lo stato della posizione debitoria su pagato (NOTIFICATO_DA_PSP o RENDICONTATO_DA_IC)
	    else {
		String msg = "Impossibile impostare lo stato della posizione debitoria " +
			     RiferimentiPosizioniDebitorieHelper.riferimentoPosizioneToString(statoPosDeb) + " su " + statoPsp.name() +
			     ". Mancano i dati del pagamento.";
		log.error(msg);
		throw new PayException(msg);
	    }
	}
	if (updatePos) {
	    this.payPosizioniDebitorieService.update(posDeb);
	}
	PayStatoPagamenti statoPos = new PayStatoPagamenti();
	PayStatoPagamenti oldStato = posDeb.recuperaStatoCorrente();
	boolean changedStatus = true;
	if (oldStato != null && StringUtils.isNotBlank(oldStato.getStato())) {
	    changedStatus = !statoNodo.equals(StatiPagamento.fromValue(oldStato.getStato()));
	}
	if (changedStatus) {
	    statoPos.setDataEvento(new Date());
	    statoPos.setPosizioneDebitoria(posDeb);
	    statoPos.setStato(statoNodo.name());
	    String descStato = StringUtils.isBlank(statoPosDeb.getMessaggio()) ? statoNodo.description() : statoPosDeb.getMessaggio();
	    if (descStato.length() > 500) {
		descStato = descStato.substring(0, 500);
	    }
	    statoPos.setDescStato(descStato);
	    this.insert(statoPos);
	    log.debug("registraStatoPosizioneDebitoria {} pay_stato_pagamenti aggiornata con stato {}", statoPosDeb.getIdPosizione().intValue(),
		    statoNodo.name());
	}
	boolean esitoDefinitivo = !statoPosDeb.isErroreTemporaneo();
	if (posDeb.recuperaRichiestaCorrente() != null && posDeb.recuperaRichiestaCorrente().getId() != null
		&& posDeb.recuperaRichiestaCorrente().getId().getCodice() != null) {
	    //aggiornamento del numero chiamate in PAY_RICHIESTE e data conclusione se esito OK o KO definitivo
	    this.payRichiesteService.aggiornaEsitoRichiesta(posDeb.recuperaRichiestaCorrente(), esitoDefinitivo);
	}
	return statoPos;
    }

    @Override
    public void salvaStatoNativoSuStatoCorrente(PayPosizioniDebitorie payPos, String statoPagamentoNativo) {

	PayStatoPagamenti statoPosizioneDebitoria = getStatoPosizioneDebitoria(payPos);
	statoPosizioneDebitoria.setStatoPagamentoNativo(statoPagamentoNativo);
	this.update(statoPosizioneDebitoria);
    }
    
    @Override
    public PayStatoPagamenti.StatiPagamento findStatoByPosizioneDebitoria(PayPosizioniDebitorie posizioneDebitoria){
	return payStatoPagamentiDAO.findStatoByPosizioneDebitoria(posizioneDebitoria);
    }
}
