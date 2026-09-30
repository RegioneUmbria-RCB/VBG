package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VwProvinceDAO;
import it.gruppoinit.pal.gp.core.domain.VwProvince;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.VwProvinceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class VwProvinceServiceImpl extends BaseServiceImpl<VwProvince, String> implements VwProvinceService {

    private VwProvinceDAO vwprovinceDAO;

    @Autowired
    public void setVwProvinceDAO(VwProvinceDAO vwprovinceDAO) {

	this.vwprovinceDAO = vwprovinceDAO;
    }

    @Override
    protected Class<VwProvince> getEntityClass() {

	return VwProvince.class;
    }

    @Override
    public List<VwProvince> findAll(Integer firstResult, Integer maxResult) {

	return vwprovinceDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(VwProvince entity) {

	if (validateEntity(entity)) {
	    vwprovinceDAO.insert(entity);
	}
    }

    @Override
    public VwProvince findById(String id) {

	return vwprovinceDAO.findById(id);
    }

    @Override
    public void update(VwProvince entity) {

	if (validateEntity(entity)) {
	    vwprovinceDAO.update(entity);
	}
    }

    @Override
    public void delete(VwProvince entity) {

	if (isDeleteAllowed(entity)) {
	    vwprovinceDAO.delete(entity);
	}
    }

    @Override
    public List<VwProvince> findByFilterTable(FilterTable filterTable) {

	return vwprovinceDAO.findByFilterTable(filterTable);
    }
    // protected boolean isDeleteAllowed(VwProvince entity) {
    //
    // boolean delete = true;
    // List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    // TODO_validare_la_delete
    // // esempio:
    // // if (entity.getList().size() > 0) {
    // // _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    // // }
    // if (!_ivs.isEmpty()) {
    // this.throwValidationMessages(_ivs);
    // }
    // return delete;
    // }
}
