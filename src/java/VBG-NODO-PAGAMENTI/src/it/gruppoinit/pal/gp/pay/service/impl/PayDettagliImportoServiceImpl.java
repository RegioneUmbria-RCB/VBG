/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.pay.command.CausaliRaggruppateBean;
import it.gruppoinit.pal.gp.pay.dao.PayDettaglioImportiDAO;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniCausali;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.PayDettaglioImportiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayRegistrazioniCausaliService;
import it.gruppoinit.pal.gp.pay.service.helper.InfoCausaleBean;
import it.gruppoinit.pal.gp.pay.ws.schema.DettaglioImportoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ImportoPagamentoType;

/**
 * @author francol
 *
 */
@Service
public class PayDettagliImportoServiceImpl extends BaseServiceImpl<PayDettaglioImporti, PkId> implements PayDettaglioImportiService {

    @Autowired
    private PayDettaglioImportiDAO payDettaglioImportiDAO;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PayRegistrazioniCausaliService payRegistrazioniCausaliService;

    @Override
    public void insert(PayDettaglioImporti entity) {

	if (validateEntity(entity)) {
	    this.payDettaglioImportiDAO.insert(entity);
	}
    }

    @Override
    public void update(PayDettaglioImporti entity) {

	if (validateEntity(entity)) {
	    this.payDettaglioImportiDAO.update(entity);
	}
    }

    @Override
    public void delete(PayDettaglioImporti entity) {

	if (this.isDeleteAllowed(entity)) {
	    this.payDettaglioImportiDAO.delete(entity);
	}
    }

    @Override
    public List<PayDettaglioImporti> findAll(Integer firstResult, Integer maxResult) {

	return this.payDettaglioImportiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public PayDettaglioImporti findById(PkId id) {

	return this.payDettaglioImportiDAO.findById(id);
    }

    @Override
    public void cancellaDettagliImportoPosizioneDebitoria(PayPosizioniDebitorie posDeb) {

	if (posDeb != null) {
	    posDeb = this.payPosizioniDebitorieService.findById(new PkId(posDeb.getId().getCodice()));
	    for (PayDettaglioImporti impDett : posDeb.getDettagliImporto()) {
		this.payDettaglioImportiDAO.delete(impDett);
	    }
	}
    }

    @Override
    public List<PayDettaglioImporti> inserisciDettagliImportoPosizioneDebitoria(PayPosizioniDebitorie posDeb, DettaglioImportoType righeImporto)
	    throws PayException {

	List<PayDettaglioImporti> insertedDetails = new ArrayList<PayDettaglioImporti>();
	if (posDeb != null) {
	    for (ImportoPagamentoType impDett : righeImporto.getComponenteImporto()) {
		PayDettaglioImporti payImpDett = new PayDettaglioImporti();
		payImpDett.setImporto(impDett.getImporto());
		payImpDett.setDescCausale(impDett.getDescrizioneCausale());
		payImpDett.setDatiRiscossione(impDett.getDatiRiscossione());
		payImpDett.setAnnoAccertamento(impDett.getAnnoAccertamento());
		payImpDett.setNumeroAccertamento(impDett.getNumeroAccertamento());
		payImpDett.setNumeroSottoAccertamento(impDett.getNumeroSottoAccertamento());
		payImpDett.setPosizioneDebitoria(posDeb);
		if (StringUtils.isBlank(impDett.getCodiceMappatura())) {
		    throw new PayException("Non è stato fornito il codice mappatura per l'importo");
		}
		PayRegistrazioniCausali rc = payRegistrazioniCausaliService.findByMappaturaClient(impDett.getCodiceMappatura());
		if (rc == null) {
		    throw new PayException("PAY_REGISTRAZIONI_CAUSALI non trovata per la mappatura " + impDett.getCodiceMappatura());
		}
		payImpDett.setCausaleRegistrazione(rc);
		this.payDettaglioImportiDAO.insert(payImpDett);
		insertedDetails.add(payImpDett);
	    }
	}
	return insertedDetails;
    }

    @Override
    protected Class<PayDettaglioImporti> getEntityClass() {

	return PayDettaglioImporti.class;
    }

    @Override
    public Map<CausaliRaggruppateBean, List<InfoCausaleBean>> findMappaCausali(List<PayRegistrazioniContabili> registrazioniPosizioni) {

	List<Integer> idRcs = new ArrayList<Integer>();
	for (PayRegistrazioniContabili prc : registrazioniPosizioni) {
	    idRcs.add(prc.getId().getCodice());
	}
	List<Integer> idCausalis = this.payDettaglioImportiDAO.findCausaliRaggruppate(idRcs);
	return findMappaCausaliDaIdentificativi(idCausalis);
    }

    @Override
    public Map<CausaliRaggruppateBean, List<InfoCausaleBean>> findMappaCausaliPosizioneDebitoria(PayPosizioniDebitorie payPos) {

	List<Integer> idCausalis = this.payDettaglioImportiDAO.findCausaliRaggruppatePosizioneDebitoria(payPos);
	return findMappaCausaliDaIdentificativi(idCausalis);
    }

    private Map<CausaliRaggruppateBean, List<InfoCausaleBean>> findMappaCausaliDaIdentificativi(List<Integer> idCausalis) {

	Map<CausaliRaggruppateBean, List<InfoCausaleBean>> ret = new HashMap<CausaliRaggruppateBean, List<InfoCausaleBean>>();
	for (Integer id : idCausalis) {
	    PayRegistrazioniCausali prc = payRegistrazioniCausaliService.findById(new PkId(id));
	    InfoCausaleBean fromPayRegistrazioniCausali = InfoCausaleBean.fromPayRegistrazioniCausali(prc);
	    CausaliRaggruppateBean cdb = CausaliRaggruppateBean.fromRegistrazioniCausali(prc);
	    List<InfoCausaleBean> list = ret.get(cdb);
	    if (list == null) {
		list = new ArrayList<InfoCausaleBean>();
	    }
	    list.add(fromPayRegistrazioniCausali);
	    ret.put(cdb, list);
	}
	return ret;
    }

    @Override
    public List<PayDettaglioImporti> findByIdPosizioneDebitoria(Integer idPosizioneDebitoria) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("posizioneDebitoriaId", idPosizioneDebitoria, Integer.class));
	ft.addRestriction(fr);
	return payDettaglioImportiDAO.findByFilterTable(ft);
    }
}
