/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
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
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.dao.PayPagamentiDAO;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti.TipiNotifica;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.PayDocumentiService;
import it.gruppoinit.pal.gp.pay.service.PayPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PaySoggettiDebitoriService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;

/**
 * @author francol
 *
 */
@Service
public class PayPagamentiServiceImpl extends BaseServiceImpl<PayPagamenti, PkId> implements PayPagamentiService {

    @Autowired
    private PayPagamentiDAO payPagamentiDAO;
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;
    @Autowired
    private PaySoggettiDebitoriService paySoggettiDebitoriService;
    @Autowired
    private PayDocumentiService payDocumentiService;

    @Override
    public void insert(PayPagamenti entity) {

	dataIntegration(entity);
	if (this.validateEntity(entity)) {
	    this.payPagamentiDAO.insert(entity);
	}
    }

    @Override
    public void update(PayPagamenti entity) {

	dataIntegration(entity);
	if (this.validateEntity(entity)) {
	    this.payPagamentiDAO.update(entity);
	}
    }

    @Override
    public void delete(PayPagamenti entity) {

	if (this.isDeleteAllowed(entity)) {
	    this.payPagamentiDAO.delete(entity);
	}
    }

    @Override
    public List<PayPagamenti> findAll(Integer firstResult, Integer maxResult) {

	return this.payPagamentiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public PayPagamenti findById(PkId id) {

	return this.payPagamentiDAO.findById(id);
    }

    @Override
    protected Class<PayPagamenti> getEntityClass() {

	return PayPagamenti.class;
    }

    private void dataIntegration(PayPagamenti entity) {

    }

    @Override
    public void registraAvvenutoPagamento(PayPagamenti payPag, PayPosizioniDebitorie payPos) throws PayException {

	this.registraAvvenutoPagamento(payPag, payPos, null);
    }

    @Override
    public void registraAvvenutoPagamento(PayPagamenti payPag, PayPosizioniDebitorie payPos, String ricevutaXml) throws PayException {

	this.validaRicezioneNotifica(payPag);
	if (payPos == null) {
	    payPos = payPag.getPosizioneDebitoria();
	}
	if (this.countByIdPosizioneDebitoria(payPos.getId().getCodice()) == 0) {
	    // solo se non già inseriti ad esempio da verificastato
	    this.inserisciDatiPagamento(payPag, payPos);
	}
	if (payPos.getId() != null && payPos.getId().getCodice() != null) {
	    //payPos = this.payPosizioniDebitorieService.findById(payPos.getId());
	    PayStatoPagamenti newStatus = new PayStatoPagamenti();
	    newStatus.setDataEvento(payPag.getDataPagamento() != null ? payPag.getDataPagamento() : new Date());
	    newStatus.setPosizioneDebitoria(payPos);
	    StatiPagamento pagato = StatiPagamento.NOTIFICATO_DA_PSP;
	    newStatus.setStato(pagato.name());
	    newStatus.setDescStato(pagato.description());
	    this.payStatoPagamentiService.insert(newStatus);
	}
	if (StringUtils.isNotBlank(ricevutaXml)) {
	    try {
		DataSource ds = new ByteArrayDataSource(ricevutaXml, ContentType.APPLICATION_OCTET_STREAM.getMimeType());
		DataHandler ricevutaXML = new DataHandler(ds);
		this.payDocumentiService.salvaRicevutaXMLPerPosizioneDebitoria(payPos, ricevutaXML);
	    } catch (IOException e) {
		throw new PayException("errore nel salvataggio della ricevuta xml", e);
	    }
	}
    }

    private void validaRicezioneNotifica(PayPagamenti pag) throws PayException {

	if (pag == null)
	    throw new PayException("Dati del pagamento mancanti");
    }

    @SuppressWarnings("unchecked")
    @Override
    public PayPagamenti getPagamentoByPosizioneDebitoria(PayPosizioniDebitorie payPos) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterField<Integer> ff = (FilterField<Integer>) FilterUtils.equals("id.codice", payPos.getId().getCodice(), "posizioneDebitoria",
		PayPosizioniDebitorie.class);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(ff);
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.order("dataPagamento", OrderTypeEnum.DESC));
	List<PayPagamenti> pags = this.payPagamentiDAO.findByFilterTable(ft, 0, 1);
	if (!pags.isEmpty()) {
	    return pags.get(0);
	} else {
	    return null;
	}
    }

    @Override
    public void inserisciDatiPagamento(PayPagamenti payPag, PayPosizioniDebitorie payPos) throws PayException {

	if (payPag != null) {
	    //gestione posizione debitoria
	    if (payPag.getPosizioneDebitoria() == null || payPag.getPosizioneDebitoria().getId() == null
		    || payPag.getPosizioneDebitoria().getId().getCodice() == null) {
		if (payPos != null && payPos.getId() != null && payPos.getId().getCodice() != null) {
		    payPag.setPosizioneDebitoria(payPos);
		}
	    } else {
		if (!(payPos != null && payPos.getId() != null && payPos.getId().getCodice() != null)) {
		    payPos = payPag.getPosizioneDebitoria();
		}
	    }
	    //gestione soggetto pagatore
	    PaySoggettiDebitori soggPagatore = payPag.getSoggettoPagatore();
	    if ((soggPagatore == null || soggPagatore.getId() == null || soggPagatore.getId().getCodice() == null) && payPos != null) {
		soggPagatore = payPos.getSoggettoDebitore();
		if (soggPagatore == null) {
		    throw new PayException("Impossibile registrare il pagamento " + payPag.getIdPosizionePsp() + " per l'IUV " + payPag.getIuv()
			    + ": dati del soggetto pagatore mancanti");
		}
	    }
	    PaySoggettiDebitori existingSogg = paySoggettiDebitoriService.registraSoggettoDebitore(soggPagatore);
	    payPag.setSoggettoPagatore(existingSogg);
	    this.payPagamentiDAO.insert(payPag);
	}
    }

    //private PayPagamenti 
    public static DatiPagamentoType populateSchemaObject(PayPagamenti payPag, PaySoggettiDebitori pagatore) {

	DatiPagamentoType p = null;
	if (payPag != null) {
	    p = new DatiPagamentoType();
	    if (payPag.getDataPagamento() != null) {
		p.setDataOraPagamento(Utilities.getXMLGregorianCalendar(payPag.getDataPagamento()));
	    }
	    if (payPag.getDataOraInizioTrans() != null) {
		p.setDataOraInizioTransazione(Utilities.getXMLGregorianCalendar(payPag.getDataOraInizioTrans()));
	    }
	    if (payPag.getDataOraAutorizzazione() != null) {
		p.setDataOraAutorizzazione(Utilities.getXMLGregorianCalendar(payPag.getDataOraAutorizzazione()));
	    }
	    p.setDescrizioneCausale(payPag.getDescrizioneCausale());
	    p.setIdPSP(payPag.getIdPsp());
	    p.setImportoCommissioni(payPag.getImportoCommissioni());
	    p.setImportoPagato(payPag.getImportoPagato());
	    p.setImportoTransato(payPag.getImportoTransato());
	    p.setModalitaPagamento(payPag.getModalitaPagamento());
	    p.setNote(payPag.getNote());
	    p.setRagioneSocialePSP(payPag.getRagSocPsp());
	    p.setRiferimentiPagamento(payPag.getRifPagamento());
	    p.setSoggettoPagatore(PaySoggettiDebitoriServiceImpl.populateSchemaObject(pagatore));
	    p.setIuv(payPag.getIuv());
	    p.setIur(payPag.getIur());
	}
	return p;
    }

    public static PayPagamenti populateDomainObject(DatiPagamentoType datiPay, PayPosizioniDebitorie payPos, StatoPagamentoType statoPsp) {

	PayPagamenti retPay = null;
	if (datiPay != null) {
	    retPay = new PayPagamenti();
	    if (datiPay.getDataOraAutorizzazione() != null) {
		retPay.setDataOraAutorizzazione(Utilities.getDate(datiPay.getDataOraAutorizzazione()));
	    }
	    if (datiPay.getDataOraInizioTransazione() != null) {
		retPay.setDataOraInizioTrans(Utilities.getDate(datiPay.getDataOraInizioTransazione()));
	    }
	    if (datiPay.getDataOraPagamento() != null) {
		retPay.setDataPagamento(Utilities.getDate(datiPay.getDataOraPagamento()));
	    }
	    retPay.setDataSistema(new Date());
	    retPay.setDescrizioneCausale(datiPay.getDescrizioneCausale());
	    if (payPos != null) {
		retPay.setIdPosizionePsp(payPos.getIdPosizionePsp());
		retPay.setPosizioneDebitoria(payPos);
	    }
	    retPay.setIdPsp(datiPay.getIdPSP());
	    retPay.setImportoCommissioni(datiPay.getImportoCommissioni());
	    retPay.setImportoPagato(datiPay.getImportoPagato());
	    retPay.setImportoTransato(datiPay.getImportoTransato());
	    if (StringUtils.isNotBlank(datiPay.getIuv())) {
		retPay.setIuv(datiPay.getIuv());
	    } else if (payPos != null && StringUtils.isNotBlank(payPos.getIuv())) {
		retPay.setIuv(payPos.getIuv());
	    }
	    if (statoPsp.equals(StatoPagamentoType.NOTIFICATO_DA_PSP)) {
		retPay.setFlagNotifica(TipiNotifica.PAGATO.value());
	    } else if (statoPsp.equals(StatoPagamentoType.RENDICONTATO_DA_IC)) {
		retPay.setFlagNotifica(TipiNotifica.PAGATO.value());
	    } else {
		retPay.setFlagNotifica(TipiNotifica.NON_DEFINITO.value());
	    }
	    retPay.setModalitaPagamento(datiPay.getModalitaPagamento());
	    retPay.setNote(datiPay.getNote());
	    retPay.setRagSocPsp(datiPay.getRagioneSocialePSP());
	    retPay.setRifPagamento(datiPay.getRiferimentiPagamento());
	    if (StringUtils.isNotBlank(datiPay.getIur())) {
		retPay.setIur(datiPay.getIur());
	    }
	    if (datiPay.getSoggettoPagatore() != null) {
		PaySoggettiDebitori paySogg = PaySoggettiDebitoriServiceImpl.populateDomainObject(datiPay.getSoggettoPagatore());
		retPay.setSoggettoPagatore(paySogg);
	    } else {
		retPay.setSoggettoPagatore(payPos.getSoggettoDebitore());
	    }
	}
	return retPay;
    }

    @Override
    public int countByIdPosizioneDebitoria(Integer idPosizioneDebitoria) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("posizioneDebitoriaId", idPosizioneDebitoria, Integer.class));
	ft.addRestriction(fr);
	return this.payPagamentiDAO.countRecord(ft);
    }

    @Override
    public Set<PayPagamenti> findByIdPosizioneDebitoria(Integer idPosizioneDebitoria) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", idPosizioneDebitoria, "posizioneDebitoria", Integer.class));
	ft.addOrder(FilterUtils.orderDesc("dataPagamento"));
	ft.addRestriction(fr);
	return new HashSet<PayPagamenti>(this.payPagamentiDAO.findByFilterTable(ft));
    }
}
