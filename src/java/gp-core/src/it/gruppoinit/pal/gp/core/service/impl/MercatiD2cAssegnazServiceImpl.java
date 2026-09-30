package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiD2cAssegnazDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD2cAssegnaz;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.MercatiD2cAssegnazService;
import it.gruppoinit.pal.gp.core.service.MercatiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class MercatiD2cAssegnazServiceImpl extends BaseServiceImpl<MercatiD2cAssegnaz, PkId> implements MercatiD2cAssegnazService {

    private MercatiD2cAssegnazDAO mercatid2cassegnazDAO;
    private Dyn2CampiService dyn2CampiService;
    private MercatiService mercatiService;

    @Autowired
    public void setMercatiD2cAssegnazDAO(MercatiD2cAssegnazDAO mercatid2cassegnazDAO) {

	this.mercatid2cassegnazDAO = mercatid2cassegnazDAO;
    }

    @Autowired
    public void setDyn2CampiService(Dyn2CampiService dyn2CampiService) {

	this.dyn2CampiService = dyn2CampiService;
    }

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Override
    protected Class<MercatiD2cAssegnaz> getEntityClass() {

	return MercatiD2cAssegnaz.class;
    }

    @Override
    public List<MercatiD2cAssegnaz> findAll(Integer firstResult, Integer maxResult) {

	return mercatid2cassegnazDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(MercatiD2cAssegnaz entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatid2cassegnazDAO.insert(entity);
	}
    }

    @Override
    public MercatiD2cAssegnaz findById(PkId id) {

	return mercatid2cassegnazDAO.findById(id);
    }

    @Override
    public void update(MercatiD2cAssegnaz entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatid2cassegnazDAO.update(entity);
	}
    }

    @Override
    public void delete(MercatiD2cAssegnaz entity) {

	if (isDeleteAllowed(entity)) {
	    if (EntityUtils.getNestedProperty(entity.getMercati().getDyn2Campi(), "id.codice") != null) {
		entity.getMercati().setDyn2Campi(null);
		mercatiService.update(entity.getMercati());
	    }
	    mercatid2cassegnazDAO.delete(entity);
	}
    }

    @Override
    public List<MercatiD2cAssegnaz> findByMercato(Integer codiceMercato) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceMercato, "mercati", Integer.class));
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("nomecampo", "dyn2Campi"));
	return mercatid2cassegnazDAO.findByFilterTable(filterTable);
    }

    private void dataIntegration(MercatiD2cAssegnaz entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il MercatiD2cAssegnaz passato è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(MercatiD2cAssegnaz entity) {

	Dyn2Campi dyn2Campi = dyn2CampiService.bindDomainObject(entity.getDyn2Campi(), PkId.class, "id.codice");
	entity.setDyn2Campi(dyn2Campi);
	Mercati mercati = mercatiService.bindDomainObject(entity.getMercati(), PkId.class, "id.codice");
	entity.setMercati(mercati);
    }

    protected boolean isDeleteAllowed(MercatiD2cAssegnaz entity) {

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
