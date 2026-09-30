package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.VwIAttivitalistaDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwIAttivitalista;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.VwIAttivitalistaService;

/**
 * 
 * @author
 */
@Service
public class VwIAttivitalistaServiceImpl extends BaseServiceImpl<VwIAttivitalista, PkId> implements VwIAttivitalistaService {

    private VwIAttivitalistaDAO vwiattivitalistaDAO;

    @Autowired
    public void setVwIAttivitalistaDAO(VwIAttivitalistaDAO vwiattivitalistaDAO) {

	this.vwiattivitalistaDAO = vwiattivitalistaDAO;
    }

    @Override
    protected Class<VwIAttivitalista> getEntityClass() {

	return VwIAttivitalista.class;
    }

    @Override
    public List<VwIAttivitalista> findAll(Integer firstResult, Integer maxResult) {

	return vwiattivitalistaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(VwIAttivitalista entity) {

	throw new NotImplementedException();
    }

    @Override
    public VwIAttivitalista findById(PkId id) {

	return vwiattivitalistaDAO.findById(id);
    }

    @Override
    public void update(VwIAttivitalista entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(VwIAttivitalista entity) {

	throw new NotImplementedException();
    }

    protected boolean isDeleteAllowed(VwIAttivitalista entity) {

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

    @Override
    public List<VwIAttivitalista> findByFilterTable(FilterTable filterTable) {

	return vwiattivitalistaDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<VwIAttivitalista> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	return vwiattivitalistaDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<Integer> findBydenominazione(String denominazione, String[] software, boolean checkAttiva, Integer firstResult, Integer maxResult) {

	return this.vwiattivitalistaDAO.findBydenominazione(denominazione, software, checkAttiva, firstResult, maxResult);
    }

    @Override
    public List<Integer> findByLocalizzazioni(Set<Istanzestradario> localizzazioni, String[] software, boolean checkAttiva, Integer firstResult,
	    Integer maxResult) {

	List<Integer> retVal = new ArrayList<Integer>();
	for (Istanzestradario localizzazione : localizzazioni) {
	    retVal.addAll(this.vwiattivitalistaDAO.findByLocalizzazione(localizzazione, software, checkAttiva, firstResult, maxResult));
	}
	return retVal;
    }
}
