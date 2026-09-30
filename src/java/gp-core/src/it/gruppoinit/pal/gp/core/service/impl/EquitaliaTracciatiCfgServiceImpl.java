package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.EquitaliaTracciatiCfgDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.EquitaliaTracciatiCfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.EquitaliaTracciatiCfgService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class EquitaliaTracciatiCfgServiceImpl extends BaseServiceImpl<EquitaliaTracciatiCfg, PkId> implements EquitaliaTracciatiCfgService {

    private EquitaliaTracciatiCfgDAO equitaliatracciaticfgDAO;

    @Autowired
    public void setEquitaliaTracciatiCfgDAO(EquitaliaTracciatiCfgDAO equitaliatracciaticfgDAO) {

	this.equitaliatracciaticfgDAO = equitaliatracciaticfgDAO;
    }

    @Override
    protected Class<EquitaliaTracciatiCfg> getEntityClass() {

	return EquitaliaTracciatiCfg.class;
    }

    @Override
    public List<EquitaliaTracciatiCfg> findAll(Integer firstResult, Integer maxResult) {

	return equitaliatracciaticfgDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(EquitaliaTracciatiCfg entity) {

	if (validateEntity(entity)) {
	    equitaliatracciaticfgDAO.insert(entity);
	}
    }

    @Override
    public EquitaliaTracciatiCfg findById(PkId id) {

	return equitaliatracciaticfgDAO.findById(id);
    }

    @Override
    public void update(EquitaliaTracciatiCfg entity) {

	if (validateEntity(entity)) {
	    equitaliatracciaticfgDAO.update(entity);
	}
    }

    @Override
    public void delete(EquitaliaTracciatiCfg entity) {

	if (isDeleteAllowed(entity)) {
	    equitaliatracciaticfgDAO.delete(entity);
	}
    }

    @Override
    public EquitaliaTracciatiCfg findBySoftware() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	List<EquitaliaTracciatiCfg> cfgs = equitaliatracciaticfgDAO.findByFilterTable(ft);
	if (!cfgs.isEmpty()) {
	    return cfgs.get(0);
	}
	return null;
    }

    protected boolean isDeleteAllowed(EquitaliaTracciatiCfg entity) {

	boolean delete = true;
	//		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//		TODO_validare_la_delete
	//		// esempio:
	//		// if (entity.getList().size() > 0) {
	//		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//		// }
	//		if (!_ivs.isEmpty()) {
	//			this.throwValidationMessages(_ivs);
	//		}
	return delete;
    }
}
