/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.pay.dao.PayRegistrazioniCausaliDAO;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniCausali;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.exception.PayInvalidRequestException;
import it.gruppoinit.pal.gp.pay.features.interfaccia.InfoCausaliParam;
import it.gruppoinit.pal.gp.pay.service.PayRegistrazioniCausaliService;
import it.gruppoinit.pal.gp.pay.service.helper.CausaliConnettoreBean;
import it.gruppoinit.pal.gp.pay.service.helper.InfoCausaleBean;
import it.gruppoinit.pal.gp.pay.service.helper.InfoCausaleRestBean;
import it.gruppoinit.pal.gp.pay.service.helper.InfoCausaliPerConnettore;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.CausaleRegistrazioneType;
import net.sf.ehcache.config.InvalidConfigurationException;

/**
 * @author francol
 *
 */
@Service
public class PayRegistrazioniCausaliServiceImpl extends BaseServiceImpl<PayRegistrazioniCausali, PkId> implements PayRegistrazioniCausaliService {

    @Autowired
    private PayRegistrazioniCausaliDAO payRegistrazioniCausaliDAO;

    @Override
    public void insert(PayRegistrazioniCausali entity) {

	if (this.validateEntity(entity)) {
	    this.payRegistrazioniCausaliDAO.insert(entity);
	}
    }

    @Override
    public void update(PayRegistrazioniCausali entity) {

	if (this.validateEntity(entity)) {
	    this.payRegistrazioniCausaliDAO.update(entity);
	}
    }

    @Override
    public void delete(PayRegistrazioniCausali entity) {

	if (this.isDeleteAllowed(entity)) {
	    this.payRegistrazioniCausaliDAO.delete(entity);
	}
    }

    @Override
    public List<PayRegistrazioniCausali> findAll(Integer firstResult, Integer maxResult) {

	return this.payRegistrazioniCausaliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public PayRegistrazioniCausali findById(PkId id) {

	return this.payRegistrazioniCausaliDAO.findById(id);
    }

    @Override
    public PayRegistrazioniCausali findCausaleRegistrazione(CausaleRegistrazioneType regCaus, PayConfigurationHelper payCfg) throws PayException {

	PayRegistrazioniCausali payRegCaus = null;
	if (regCaus != null && StringUtils.isNotBlank(regCaus.getId())) {
	    String codiceVersamento = regCaus.getId();
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equalsIgnoreCase("codiceVersamento", codiceVersamento));
	    ft.addRestriction(fr);
	    List<PayRegistrazioniCausali> regCausaliByCode = this.payRegistrazioniCausaliDAO.findByFilterTable(ft);
	    if (regCausaliByCode.isEmpty()) {
		if (!payCfg.isInserisciCausaliRegistrazione()) {
		    throw new PayInvalidRequestException("Causale di registrazione con codice versamento " + codiceVersamento + " inesistente.");
		}
	    } else if (regCausaliByCode.size() > 1) {
		throw new PayConfigurationException("Esiste più di una causale di registrazione con codice versamento: " + codiceVersamento);
	    } else {
		payRegCaus = regCausaliByCode.get(0);
	    }
	}
	return payRegCaus;
    }

    @Override
    protected Class<PayRegistrazioniCausali> getEntityClass() {

	return PayRegistrazioniCausali.class;
    }

    @Override
    public InfoCausaleBean findInfoCausale(String codiceMappatura) {

	PayRegistrazioniCausali rc = this.findByMappaturaClient(codiceMappatura);
	return InfoCausaleBean.fromPayRegistrazioniCausali(rc);
    }

    @Override
    public PayRegistrazioniCausali findByMappaturaClient(String codiceMappatura) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("mappaturaClient", codiceMappatura));
	ft.addRestriction(fr);
	List<PayRegistrazioniCausali> list = this.payRegistrazioniCausaliDAO.findByFilterTable(ft, 0, 2);
	if (list.size() != 1) {
	    throw new InvalidConfigurationException("Trovate " + list.size() + " registrazionicausali per la mappatura: " + codiceMappatura +
						    " e idcomune: " + ORMHelper.getIdcomune());
	}
	return list.get(0);
    }

    @Override
    public List<InfoCausaliPerConnettore> findCausaliPerConnettore(List<String> cfCodiciProfilo) {

	List<InfoCausaliPerConnettore> ret = new ArrayList<>();
	Map<String, InfoCausaliPerConnettore> m = new HashMap<>();
	List<CausaliConnettoreBean> l = this.payRegistrazioniCausaliDAO.findInfoCausaliPerConnettore(cfCodiciProfilo);
	for (CausaliConnettoreBean c : l) {
	    String key = c.getCfcodiceprofilo();
	    InfoCausaliPerConnettore info = m.get(key);
	    if (info == null) {
		info = new InfoCausaliPerConnettore(c);
	    }
	    // Recupero l'idcomune settato nell'ORMHELPER dal chiamante 
	    String idcomuneOrig = ORMHelper.getIdcomune();
	    // setto l'idcomune del recor della causale
	    ORMHelper.setIdcomune(c.getIdcomune());
	    PayRegistrazioniCausali rc = this.payRegistrazioniCausaliDAO.findById(new PkId(c.getIdcomune(), c.getId()));
	    info.getCausali().add(InfoCausaleRestBean.fromPayRegistrazioniCausali(rc, info.getJavaClass()));
	    m.put(key, info);
	    // riporto l'idcomune settato nell'ORMHELPER dal chiamante
	    ORMHelper.setIdcomune(idcomuneOrig);
	}
	for (Entry<String, InfoCausaliPerConnettore> entry : m.entrySet()) {
	    ret.add(entry.getValue());
	}
	return ret;
    }

    @Override
    public List<InfoCausaliParam> findInfoCausaliRidottePerConnettore(String cfcodprofilo) {

	return this.payRegistrazioniCausaliDAO.findInfoCausaliRidottePerConnettore(cfcodprofilo);
    }
    
    @Override
    public String findMappaturaClientByPosizioneDebitoria(PayPosizioniDebitorie payPos) {

	return this.payRegistrazioniCausaliDAO.findMappaturaClientByPosizioneDebitoria(payPos);
    }
}
