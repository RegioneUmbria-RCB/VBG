/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.pay.dao.PayProfiliEntiCreditoriDAO;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.features.interfaccia.ConnectorConfigHelper;
import it.gruppoinit.pal.gp.pay.service.PayProfiliEntiCreditoriService;

/**
 * @author francol
 *
 */
@Service
public class PayProfiliEntiCreditoriServiceImpl extends BaseServiceImpl<PayProfiliEntiCreditori, PkId> implements PayProfiliEntiCreditoriService {

    @Autowired
    private PayProfiliEntiCreditoriDAO payProfiliEntiCreditoriDAO;

    @Override
    public void insert(PayProfiliEntiCreditori entity) {

	if (validateEntity(entity)) {
	    this.payProfiliEntiCreditoriDAO.insert(entity);
	}
    }

    @Override
    public void update(PayProfiliEntiCreditori entity) {

	if (validateEntity(entity)) {
	    this.payProfiliEntiCreditoriDAO.update(entity);
	}
    }

    @Override
    public void delete(PayProfiliEntiCreditori entity) {

	if (isDeleteAllowed(entity)) {
	    this.payProfiliEntiCreditoriDAO.delete(entity);
	}
    }

    @Override
    public List<PayProfiliEntiCreditori> findAll(Integer firstResult, Integer maxResult) {

	return this.payProfiliEntiCreditoriDAO.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "cfCodiceProfilo", DAOOrderTypeEnum.ASC);
    }

    @Override
    public PayProfiliEntiCreditori findById(PkId id) {

	return this.payProfiliEntiCreditoriDAO.findById(id);
    }

    @Override
    public PayProfiliEntiCreditori findByCfCodiceProfilo(String codProfiloEnte) throws PayConfigurationException {

	PayProfiliEntiCreditori profiloEnte = null;
	List<PayProfiliEntiCreditori> profs = this.payProfiliEntiCreditoriDAO.findByCfCodiceProfilo(codProfiloEnte);
	if (profs.isEmpty()) {
	    profs = this.payProfiliEntiCreditoriDAO.findByCodiceProfiloPSP(codProfiloEnte);
	}
	if (!profs.isEmpty()) {
	    if (profs.size() == 1) {
		profiloEnte = profs.get(0);
		this.payProfiliEntiCreditoriDAO.evict(profiloEnte);
	    } else if (profs.size() > 1) {
		throw new PayConfigurationException("Esiste più di un profilo di ente creditore associato al codice " + codProfiloEnte);
	    }
	} else {
	    throw new PayConfigurationException("Non esiste nessun profilo di ente creditore associato al codice " + codProfiloEnte);
	}
	return profiloEnte;
    }

    @Override
    public PayProfiliEntiCreditori findByCodiceProfiloPSP(String codProfiloEnte) throws PayConfigurationException {

	PayProfiliEntiCreditori profiloEnte = null;
	List<PayProfiliEntiCreditori> profs = this.payProfiliEntiCreditoriDAO.findByCodiceProfiloPSP(codProfiloEnte);
	if (!profs.isEmpty()) {
	    if (profs.size() == 1) {
		profiloEnte = profs.get(0);
		this.payProfiliEntiCreditoriDAO.evict(profiloEnte);
	    } else if (profs.size() > 1) {
		throw new PayConfigurationException("Esiste più di un profilo di ente creditore associato al codice " + codProfiloEnte);
	    }
	} else {
	    throw new PayConfigurationException("Non esiste nessun profilo di ente creditore associato al codice " + codProfiloEnte);
	}
	return profiloEnte;
    }

    @Override
    protected Class<PayProfiliEntiCreditori> getEntityClass() {

	return PayProfiliEntiCreditori.class;
    }

    @Override
    public PayProfiliEntiCreditori findByIdAppPspAndIdPosizionePsp(String idAppPsp, String IdPosizionePsp) throws PayConfigurationException {

	PayProfiliEntiCreditori ret = this.payProfiliEntiCreditoriDAO.findByIdAppPspAndIdPosizionePsp(idAppPsp, IdPosizionePsp);
	if (ret == null) {
	    throw new PayConfigurationException("Profilo non trovato per idAppPsp: " + idAppPsp + ", IdPosizionePsp: " + IdPosizionePsp);
	}
	return ret;
    }

    @Override
    public PayProfiliEntiCreditori findByConnectorCode(String connectorCode) throws PayConfigurationException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("payConnector.codice", connectorCode, String.class));
	ft.addRestriction(fr);
	List<PayProfiliEntiCreditori> results = this.payProfiliEntiCreditoriDAO.findByFilterTable(ft, 0, 2);
	if (results.size() == 1) {
	    return results.get(0);
	}
	throw new PayConfigurationException("Codice connettore non configurato correttamente " + connectorCode);
    }

    @Override
    public List<ConnectorConfigHelper> findConfigHelperByJavaclass(String javaClass) throws PayConfigurationException {

	List<ConnectorConfigHelper> ret = this.payProfiliEntiCreditoriDAO.findConfigHelperByJavaclass(javaClass);
	if (ret == null) {
	    throw new PayConfigurationException("Classe non presente per: " + javaClass);
	}
	return ret;
    }
}
