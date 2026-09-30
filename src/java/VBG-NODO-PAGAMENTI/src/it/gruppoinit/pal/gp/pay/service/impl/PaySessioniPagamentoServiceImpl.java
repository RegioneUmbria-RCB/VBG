/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.pay.service.PayConnectorService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.RiferimentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.dao.PaySessioniPagamentoDAO;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.exception.PayInvalidRequestException;

/**
 * @author francol
 *
 */
@Service
public class PaySessioniPagamentoServiceImpl extends BaseServiceImpl<PaySessioniPagamento, PkId> implements PaySessioniPagamentoService {

    @Autowired
    private PaySessioniPagamentoDAO paySessioniPagamentoDAO;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PayConnectorService payConnectorService;
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;

    @Override
    public void insert(PaySessioniPagamento entity) {

	if (this.validateEntity(entity)) {
	    this.paySessioniPagamentoDAO.insert(entity);
	}
    }

    @Override
    public void update(PaySessioniPagamento entity) {

	if (this.validateEntity(entity)) {
	    this.paySessioniPagamentoDAO.update(entity);
	}
    }

    @Override
    public void delete(PaySessioniPagamento entity) {

	if (this.isDeleteAllowed(entity)) {
	    this.paySessioniPagamentoDAO.delete(entity);
	}
    }

    @Override
    public PaySessioniPagamento findById(PkId id) {

	return this.paySessioniPagamentoDAO.findById(id);
    }

    @Override
    public List<PaySessioniPagamento> findAll(Integer firstResult, Integer maxResult) {

	return this.paySessioniPagamentoDAO.findAll(firstResult, maxResult);
    }

    @Override
    protected Class<PaySessioniPagamento> getEntityClass() {

	return PaySessioniPagamento.class;
    }

    @Override
    public PaySessioniPagamento creaSessionePagamento(AttivaSessionePagamentoResponseType sesData, List<PayPosizioniDebitorie> pos)
	    throws PayException {

	PaySessioniPagamento sex = null;
	if (sesData == null) {
	    throw new PayException("errore nella registrazione della sessione di pagamento: dati della sessione mancanti");
	}
	if (pos == null || pos.isEmpty()) {
	    throw new PayException("errore nella registrazione della sessione di pagamento: riferimento alla posizione debitoria mancante");
	}
	for (PayPosizioniDebitorie payPosizioniDebitorie : pos) {
	    //inserimento record in PaySessioniPagamento
	    sex = new PaySessioniPagamento();
	    sex.setDataInizio(new Date());
	    sex.setPosizioneDebitoria(payPosizioniDebitorie);
	    sex.setDigestSicurezza(sesData.getSecurityDigest());
	    sex.setUrlRedirectEsito(sesData.getPayUrl());
	    sex.setIdSessionePagamento(sesData.getIdSessione());
	    this.paySessioniPagamentoDAO.insert(sex);
	}
	return sex;
    }

    public void validateSessionePagamento(PayPosizioniDebitorie pos) throws PayInvalidRequestException {

	PayStatoPagamenti statoPos = this.payStatoPagamentiService.getStatoPosizioneDebitoria(pos);
	if (statoPos == null) {
	    throw new PayInvalidRequestException("Lo stato della posizione debitoria non è definito. Impossibile attivare la sessione di pagamento");
	}
	/*
	 * if (StringUtils.isBlank(pos.getIuv())) { throw new PayInvalidRequestException(
	 * "La posizione debitoria non è ancora ssociata ad un IUV. Impossibile attivare la sessione di pagamento"); }
	 */
	StatoPagamentoType status = StatoPagamentoType.fromValue(statoPos.getStato());
	if (status != StatoPagamentoType.ATTIVATO_IN_PSP) {
	    throw new PayInvalidRequestException(
		    "Lo stato della posizione debitoria " + statoPos.getStato() + " non consente l'attivazione della sessione di pagamento");
	}
    }

    @Override
    public List<PaySessioniPagamento> findBySessionId(String sexId) {

	return this.paySessioniPagamentoDAO.findByIdSessione(sexId);
    }

    @Override
    public List<PaySessioniPagamento> findByDigest(String digest) {

	return this.paySessioniPagamentoDAO.findByDigest(digest);
    }

    @Override
    public List<PaySessioniPagamento> findSessioniAttivePerPosizioneDebitoria(Integer idPos) {

	return this.paySessioniPagamentoDAO.findSessioniAttivePerPosizioneDebitoria(idPos);
    }

    @Override
    public List<PaySessioniPagamento> findSessioniPerPosizioneDebitoria(Integer idPos) {

	return this.paySessioniPagamentoDAO.findSessioniPerPosizioneDebitoria(idPos);
    }
}
