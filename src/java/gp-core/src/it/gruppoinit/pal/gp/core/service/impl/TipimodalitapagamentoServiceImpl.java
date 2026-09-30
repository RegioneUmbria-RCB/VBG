/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipimodalitapagamentoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniInOut;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class TipimodalitapagamentoServiceImpl extends BaseServiceImpl<Tipimodalitapagamento, PkId> implements TipimodalitapagamentoService {

    private TipimodalitapagamentoDAO tipimodalitapagamentoDAO;

    @Autowired
    public void setTipimodalitapagamentoDAO(TipimodalitapagamentoDAO tipimodalitapagamentoDAO) {

	this.tipimodalitapagamentoDAO = tipimodalitapagamentoDAO;
    }

    @Override
    protected Class<Tipimodalitapagamento> getEntityClass() {

	return Tipimodalitapagamento.class;
    }

    @Override
    public void delete(Tipimodalitapagamento entity) {

	if (isDeleteAllowed(entity)) {
	    tipimodalitapagamentoDAO.delete(entity);
	}
    }

    @Override
    public List<Tipimodalitapagamento> findAll(Integer firstResult, Integer maxResult) {

	return tipimodalitapagamentoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public List<Tipimodalitapagamento> findAll(Integer firstResult, Integer maxResult, boolean mostraDisabilitati) {

	if (mostraDisabilitati) {
	    return tipimodalitapagamentoDAO.findAll(firstResult, maxResult);
	} else {
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("flagDisabilitato", false, Boolean.class));
	    fr.setAndOrRestriction(AndOrRestriction.OR);
	    fr.addFilterField(FilterUtils.isNull("flagDisabilitato"));
	    ft.addRestriction(fr);
	    ft.addOrder(FilterUtils.orderAsc("mpDescrestesa"));
	    List<Tipimodalitapagamento> ris = tipimodalitapagamentoDAO.findByFilterTable(ft, null, null);
	    return ris;
	}
    }

    @Override
    public Tipimodalitapagamento findById(PkId id) {

	return tipimodalitapagamentoDAO.findById(id);
    }

    @Override
    public void insert(Tipimodalitapagamento entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipimodalitapagamentoDAO.insert(entity);
	}
    }

    @Override
    public void update(Tipimodalitapagamento entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipimodalitapagamentoDAO.update(entity);
	}
    }

    private void dataIntegration(Tipimodalitapagamento entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il Tipo modalità pagamento passato è nullo");
	}
	if (entity.getFlagDisabilitato() == null) {
	    entity.setFlagDisabilitato(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Tipimodalitapagamento entity) {

    }

    @Override
    public List<Tipimodalitapagamento> findByMpDescrestesa(String mpDescrestesa, boolean mostraDisabilitati) {

	return tipimodalitapagamentoDAO.findByMpDescrestesa(mpDescrestesa, mostraDisabilitati);
    }

    protected boolean isDeleteAllowed(Tipimodalitapagamento entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Set<RegistrazioniInOut> registrazioniInOuts = entity.getRegistrazioniInOuts();
	if (!registrazioniInOuts.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "REGISTRAZIONI_IN_OUT", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Tipimodalitapagamento> findByDescrizioneEsatta(String descrizione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("mpDescrestesa", descrizione));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("mpDescrestesa"));
	return tipimodalitapagamentoDAO.findByFilterTable(ft);
    }
}
