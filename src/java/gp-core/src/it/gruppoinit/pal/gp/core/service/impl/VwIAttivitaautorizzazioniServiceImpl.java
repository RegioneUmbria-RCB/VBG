package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VwIAttivitaautorizzazioniDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.VwIAttivitaautorizzazioni;
import it.gruppoinit.pal.gp.core.domain.VwIAttivitaautorizzazioniId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.VwIAttivitaautorizzazioniService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class VwIAttivitaautorizzazioniServiceImpl extends BaseServiceImpl<VwIAttivitaautorizzazioni, VwIAttivitaautorizzazioniId> implements
	VwIAttivitaautorizzazioniService {

    private VwIAttivitaautorizzazioniDAO vwiattivitaautorizzazioniDAO;

    @Autowired
    public void setVwIAttivitaautorizzazioniDAO(VwIAttivitaautorizzazioniDAO vwiattivitaautorizzazioniDAO) {

	this.vwiattivitaautorizzazioniDAO = vwiattivitaautorizzazioniDAO;
    }

    @Override
    protected Class<VwIAttivitaautorizzazioni> getEntityClass() {

	return VwIAttivitaautorizzazioni.class;
    }

    @Override
    public List<VwIAttivitaautorizzazioni> findAll(Integer firstResult, Integer maxResult) {

	return vwiattivitaautorizzazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(VwIAttivitaautorizzazioni entity) {

	throw new NotImplementedException();
    }

    @Override
    public VwIAttivitaautorizzazioni findById(VwIAttivitaautorizzazioniId id) {

	return vwiattivitaautorizzazioniDAO.findById(id);
    }

    @Override
    public int countByAttivita(Integer codiceAttivita) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("iidattivita", codiceAttivita, Integer.class));
	ft.addRestriction(fr);
	int count = vwiattivitaautorizzazioniDAO.countRecord(ft);
	return count;
    }

    @Override
    public void update(VwIAttivitaautorizzazioni entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(VwIAttivitaautorizzazioni entity) {

	throw new NotImplementedException();
    }

    protected boolean isDeleteAllowed(VwIAttivitaautorizzazioni entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

   
}
