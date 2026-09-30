package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocD2modtattDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocD2modtatt;
import it.gruppoinit.pal.gp.core.domain.AlberoprocD2modtattId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocD2modtattService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class AlberoprocD2modtattServiceImpl extends BaseServiceImpl<AlberoprocD2modtatt, AlberoprocD2modtattId> implements AlberoprocD2modtattService {

    private AlberoprocD2modtattDAO alberoprocd2modtattDAO;

    @Autowired
    public void setAlberoprocD2modtattDAO(AlberoprocD2modtattDAO alberoprocd2modtattDAO) {

	this.alberoprocd2modtattDAO = alberoprocd2modtattDAO;
    }

    @Override
    protected Class<AlberoprocD2modtatt> getEntityClass() {

	return AlberoprocD2modtatt.class;
    }

    @Override
    public List<AlberoprocD2modtatt> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocd2modtattDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AlberoprocD2modtatt entity) {

	if (validateEntity(entity)) {
	    alberoprocd2modtattDAO.insert(entity);
	}
    }

    @Override
    public AlberoprocD2modtatt findById(AlberoprocD2modtattId id) {

	return alberoprocd2modtattDAO.findById(id);
    }

    @Override
    public void update(AlberoprocD2modtatt entity) {

	if (validateEntity(entity)) {
	    alberoprocd2modtattDAO.update(entity);
	}
    }

    @Override
    public void delete(AlberoprocD2modtatt entity) {

	if (isDeleteAllowed(entity)) {
	    alberoprocd2modtattDAO.delete(entity);
	}
    }

    @Override
    public List<AlberoprocD2modtatt> findByAlberoProc(Integer codiceAlberoproc) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAlberoproc, "alberoproc", Integer.class));
	ft.addRestriction(fr);
	return alberoprocd2modtattDAO.findByFilterTable(ft);
    }
    //    protected boolean isDeleteAllowed(AlberoprocD2modtatt entity) {
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
