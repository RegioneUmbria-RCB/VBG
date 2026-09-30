package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.DehorsAreeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.DehorsAree;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AreeService;
import it.gruppoinit.pal.gp.core.service.DehorsAreeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class DehorsAreeServiceImpl extends BaseServiceImpl<DehorsAree, PkId> implements DehorsAreeService {

    private DehorsAreeDAO dehorsareeDAO;
    private AreeService areeService;

    @Autowired
    public void setDehorsAreeDAO(DehorsAreeDAO dehorsareeDAO) {

	this.dehorsareeDAO = dehorsareeDAO;
    }

    @Autowired
    public void setAreeService(AreeService areeService) {

	this.areeService = areeService;
    }

    @Override
    protected Class<DehorsAree> getEntityClass() {

	return DehorsAree.class;
    }

    @Override
    public List<DehorsAree> findAll(Integer firstResult, Integer maxResult) {

	return dehorsareeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(DehorsAree entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    dehorsareeDAO.insert(entity);
	}
    }

    @Override
    public DehorsAree findById(PkId id) {

	return dehorsareeDAO.findById(id);
    }

    @Override
    public void update(DehorsAree entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    dehorsareeDAO.update(entity);
	}
    }

    @Override
    public void delete(DehorsAree entity) {

	if (isDeleteAllowed(entity)) {
	    dehorsareeDAO.delete(entity);
	}
    }

    @Override
    public List<DehorsAree> findByArea(Integer codicearea) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("id.codice", codicearea, "aree", Integer.class));
	filterTable.addRestriction(restriction);
	List<DehorsAree> list = dehorsareeDAO.findByFilterTable(filterTable);
	return list;
    }

    private void dataIntegration(DehorsAree entity) {

	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(DehorsAree entity) {

	Aree aree = areeService.bindDomainObject(entity.getAree(), PkId.class, "id.codice");
	entity.setAree(aree);
    }
    //
    //    protected boolean isDeleteAllowed(DehorsAree entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }
}
