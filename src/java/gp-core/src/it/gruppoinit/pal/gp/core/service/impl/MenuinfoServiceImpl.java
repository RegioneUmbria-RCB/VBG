package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MenuinfoDAO;
import it.gruppoinit.pal.gp.core.domain.Menuinfo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.MenuinfoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class MenuinfoServiceImpl extends BaseServiceImpl<Menuinfo, PkId> implements MenuinfoService {

    private MenuinfoDAO menuinfoDAO;
    private OggettiService oggettiService;

    @Autowired
    public void setMenuinfoDAO(MenuinfoDAO menuinfoDAO) {

	this.menuinfoDAO = menuinfoDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    protected Class<Menuinfo> getEntityClass() {

	return Menuinfo.class;
    }

    @Override
    public List<Menuinfo> findAll(Integer firstResult, Integer maxResult) {

	return menuinfoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Menuinfo entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    menuinfoDAO.insert(entity);
	    childDataInsert(entity);
	}
    }

    @Override
    public Menuinfo findById(PkId id) {

	return menuinfoDAO.findById(id);
    }

    @Override
    public void update(Menuinfo entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    menuinfoDAO.update(entity);
	    childDataUpdate(entity);
	}
    }

    @Override
    public void delete(Menuinfo entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    menuinfoDAO.delete(entity);
	}
    }

    @Override
    public Integer findOrdineMax() {

	return menuinfoDAO.findOrdineMax();
    }

    private void childDataInsert(Menuinfo entity) {

    }

    private void childDataUpdate(Menuinfo entity) {

    }

    @Override
    protected void childDelete(Menuinfo entity) {

    }

    private void dataIntegration(Menuinfo entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il menù info passato è nullo");
	}
	if (entity.getFlagAttivo() == null) {
	    entity.setFlagAttivo(Boolean.valueOf(false));
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Menuinfo entity) {

    }
    //    protected boolean isDeleteAllowed(Menuinfo entity) {
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
