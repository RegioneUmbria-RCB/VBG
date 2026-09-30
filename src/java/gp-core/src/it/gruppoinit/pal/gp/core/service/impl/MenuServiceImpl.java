package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MenuDAO;
import it.gruppoinit.pal.gp.core.domain.Menu;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.MenuService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, PkId> implements MenuService {

    private MenuDAO menuDAO;
    private OggettiService oggettiService;
    private SoftwareService softwareService;

    @Autowired
    public void setMenuDAO(MenuDAO menuDAO) {

	this.menuDAO = menuDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Override
    protected Class<Menu> getEntityClass() {

	return Menu.class;
    }

    @Override
    public List<Menu> findAll(Integer firstResult, Integer maxResult) {

	return menuDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Menu entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    menuDAO.insert(entity);
	    childDataInsert(entity);
	}
    }

    @Override
    public Menu findById(PkId id) {

	return menuDAO.findById(id);
    }

    @Override
    public void update(Menu entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    menuDAO.update(entity);
	    childDataUpdate(entity);
	}
    }

    @Override
    public void delete(Menu entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    menuDAO.delete(entity);
	}
    }

    @Override
    public Integer findOrdineMax() {

	return menuDAO.findOrdineMax();
    }

    private void childDataInsert(Menu entity) {

    }

    private void childDataUpdate(Menu entity) {

    }

    @Override
    protected void childDelete(Menu entity) {

    }

    private void dataIntegration(Menu entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il menù passato è nullo");
	}
	if (entity.getFlagAttivo() == null) {
	    entity.setFlagAttivo(Boolean.valueOf(false));
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Menu entity) {

	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
    }
    //    protected boolean isDeleteAllowed(Menu entity) {
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
