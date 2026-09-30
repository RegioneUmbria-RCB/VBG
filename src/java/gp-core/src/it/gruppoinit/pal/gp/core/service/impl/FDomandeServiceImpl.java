package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FDomandeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FDomande;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FDomandeService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class FDomandeServiceImpl extends BaseServiceImpl<FDomande, PkId> implements FDomandeService {

    private FDomandeDAO fdomandeDAO;

    @Autowired
    public void setFDomandeDAO(FDomandeDAO fdomandeDAO) {

	this.fdomandeDAO = fdomandeDAO;
    }

    @Override
    protected Class<FDomande> getEntityClass() {

	return FDomande.class;
    }

    @Override
    public List<FDomande> findAll(Integer firstResult, Integer maxResult) {

	return fdomandeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FDomande entity) {

	if (validateEntity(entity)) {
	    fdomandeDAO.insert(entity);
	}
    }

    @Override
    public FDomande findById(PkId id) {

	return fdomandeDAO.findById(id);
    }

    @Override
    public void update(FDomande entity) {

	if (validateEntity(entity)) {
	    fdomandeDAO.update(entity);
	}
    }

    @Override
    public void delete(FDomande entity) {

	if (isDeleteAllowed(entity)) {
	    fdomandeDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(FDomande entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<FDomande> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafe: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	filterTable.addRestriction(fr);
	return fdomandeDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<FDomande> findBySocieta(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findBySocieta: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "societa", Integer.class));
	filterTable.addRestriction(fr);
	return fdomandeDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<FDomande> findBySubentro(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findBySubentro: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "subentro", Integer.class));
	filterTable.addRestriction(fr);
	return fdomandeDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }
}
