package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiDCritassDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDCritass;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.MercatiDCritassService;
import it.gruppoinit.pal.gp.core.service.MercatiDService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class MercatiDCritassServiceImpl extends BaseServiceImpl<MercatiDCritass, PkId> implements MercatiDCritassService {

    private MercatiDCritassDAO mercatidcritassDAO;
    private Dyn2CampiService dyn2CampiService;
    private Dyn2ModellitService dyn2ModellitService;
    private MercatiDService mercatiDService;

    @Autowired
    public void setMercatiDCritassDAO(MercatiDCritassDAO mercatidcritassDAO) {

	this.mercatidcritassDAO = mercatidcritassDAO;
    }

    @Autowired
    public void setDyn2CampiService(Dyn2CampiService dyn2CampiService) {

	this.dyn2CampiService = dyn2CampiService;
    }

    @Autowired
    public void setDyn2ModellitService(Dyn2ModellitService dyn2ModellitService) {

	this.dyn2ModellitService = dyn2ModellitService;
    }

    @Autowired
    public void setMercatiDService(MercatiDService mercatiDService) {

	this.mercatiDService = mercatiDService;
    }

    @Override
    protected Class<MercatiDCritass> getEntityClass() {

	return MercatiDCritass.class;
    }

    @Override
    public List<MercatiDCritass> findAll(Integer firstResult, Integer maxResult) {

	return mercatidcritassDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(MercatiDCritass entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatidcritassDAO.insert(entity);
	}
    }

    @Override
    public MercatiDCritass findById(PkId id) {

	return mercatidcritassDAO.findById(id);
    }

    @Override
    public void update(MercatiDCritass entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatidcritassDAO.update(entity);
	}
    }

    @Override
    public void delete(MercatiDCritass entity) {

	if (isDeleteAllowed(entity)) {
	    mercatidcritassDAO.delete(entity);
	}
    }

    @Override
    public List<MercatiDCritass> findByPosteggio(Integer codicePosteggio) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codicePosteggio, "mercatiD", Integer.class));
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("nomecampo", "dyn2Campi"));
	return mercatidcritassDAO.findByFilterTable(filterTable);
    }

    private void validaCampoDinamico(MercatiDCritass entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// String err = dyn2ModellitService.validaCampo(null, entity.getDyn2Campi().getId().getCodice(), entity.getValore());
	//	if (StringUtils.isNotBlank(err)) {
	//	    _ivs.add(new InvalidValue(err, MercatiDCritass.class, "valore", new MercatiDCritass(), entity));
	//	}
	//	if (entity.getDyn2Campi().getTipodato().equalsIgnoreCase("NumericoIntero")
	//		|| entity.getDyn2Campi().getTipodato().equalsIgnoreCase("NumericoDouble")) {
	//	    try {
	//		Integer.parseInt(entity.getValore());
	//	    } catch (NumberFormatException ne) {
	//		_ivs.add(new InvalidValue("Il valore deve essere numerico", MercatiDCritass.class, "valore", new MercatiDCritass(), entity));
	//	    }
	//	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
    }

    private void dataIntegration(MercatiDCritass entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il MercatiD2cAssegnaz passato è nullo");
	}
	if (entity.getFlagConsentito() == null) {
	    entity.setFlagConsentito(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(MercatiDCritass entity) {

	Dyn2Campi dyn2Campi = dyn2CampiService.bindDomainObject(entity.getDyn2Campi(), PkId.class, "id.codice");
	entity.setDyn2Campi(dyn2Campi);
	MercatiD mercatid = mercatiDService.bindDomainObject(entity.getMercatiD(), PkId.class, "id.codice");
	entity.setMercatiD(mercatid);
    }

    protected boolean isDeleteAllowed(MercatiDCritass entity) {

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
