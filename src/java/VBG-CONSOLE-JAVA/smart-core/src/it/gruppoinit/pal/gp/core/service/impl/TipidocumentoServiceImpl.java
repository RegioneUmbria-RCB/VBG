package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipidocumentoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipidocumento;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.TipidocumentoService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 * @author gianpaolot
 */
@Service
public class TipidocumentoServiceImpl extends BaseServiceImpl<Tipidocumento, PkId> implements TipidocumentoService {

    private TipidocumentoDAO tipidocumentoDAO;

    @Autowired
    public void setTipidocumentoDAO(TipidocumentoDAO tipidocumentoDAO) {

	this.tipidocumentoDAO = tipidocumentoDAO;
    }

    @Override
    protected Class<Tipidocumento> getEntityClass() {

	return Tipidocumento.class;
    }

    @Override
    public List<Tipidocumento> findAll(Integer firstResult, Integer maxResult) {

	return tipidocumentoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Tipidocumento entity) {

	if (validateEntity(entity)) {
	    tipidocumentoDAO.insert(entity);
	}
    }

    @Override
    public Tipidocumento findById(PkId id) {

	return tipidocumentoDAO.findById(id);
    }

    @Override
    public void update(Tipidocumento entity) {

	if (validateEntity(entity)) {
	    tipidocumentoDAO.update(entity);
	}
    }

    @Override
    public void delete(Tipidocumento entity) {

	if (isDeleteAllowed(entity)) {
	    tipidocumentoDAO.delete(entity);
	}
    }

    @Override
    public List<Tipidocumento> findByFilterTable(FilterTable filterTable) {

	return tipidocumentoDAO.findByFilterTable(filterTable);
    }

    protected boolean isDeleteAllowed(Tipidocumento entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//	Set<Anagrafedocumenti> anagrafedocumentis = entity.getAnagrafedocumentis();
	//	if (!anagrafedocumentis.isEmpty()) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ANAGRAFEDOCUMENTI", null));
	//	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Tipidocumento> findByLetteretipo(Letteretipo letteretipo, int firstResult, int maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("letteretipoId", letteretipo.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("documento"));
	return tipidocumentoDAO.findByFilterTable(ft, firstResult, maxResult);
    }
}
