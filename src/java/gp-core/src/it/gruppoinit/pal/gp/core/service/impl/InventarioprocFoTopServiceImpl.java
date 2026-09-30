package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocFoTopDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.InventarioprocFoTop;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.InventarioprocFoTopService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.helper.FlagPubblicaEnum;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class InventarioprocFoTopServiceImpl extends BaseServiceImpl<InventarioprocFoTop, PkId> implements InventarioprocFoTopService {

    private InventarioprocFoTopDAO inventarioprocfotopDAO;
    private SoftwareService softwareService;
    private InventarioprocedimentiService inventarioprocedimentiService;

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setInventarioprocFoTopDAO(InventarioprocFoTopDAO inventarioprocfotopDAO) {

	this.inventarioprocfotopDAO = inventarioprocfotopDAO;
    }

    @Override
    protected Class<InventarioprocFoTop> getEntityClass() {

	return InventarioprocFoTop.class;
    }

    @Override
    public List<InventarioprocFoTop> findAll(Integer firstResult, Integer maxResult) {

	return inventarioprocfotopDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(InventarioprocFoTop entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    inventarioprocfotopDAO.insert(entity);
	}
    }

    @Override
    public InventarioprocFoTop findById(PkId id) {

	return inventarioprocfotopDAO.findById(id);
    }

    @Override
    public void update(InventarioprocFoTop entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    inventarioprocfotopDAO.update(entity);
	}
    }

    private void dataIntegration(InventarioprocFoTop entity) {

	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(InventarioprocFoTop entity) {

	Inventarioprocedimenti ip = inventarioprocedimentiService.bindDomainObject(entity.getInventarioprocedimenti(), PkId.class, "id.codice");
	entity.setInventarioprocedimenti(ip);
	Software s = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(s);
    }

    @Override
    public void delete(InventarioprocFoTop entity) {

	if (isDeleteAllowed(entity)) {
	    inventarioprocfotopDAO.delete(entity);
	}
    }

    @Override
    public List<IdentificativoDescrizioneBean> findProcedimenti(Integer firstResult, Integer maxResult) {

	List<IdentificativoDescrizioneBean> result = new ArrayList<IdentificativoDescrizioneBean>();
	List<InventarioprocFoTop> list = this.findAllOrderedByCodiceAndDescrizione(firstResult, maxResult, FlagPubblicaEnum.DA_PUBBLICARE);
	int pos = 0;
	for (InventarioprocFoTop aft : list) {
	    if (aft.getInventarioprocedimenti() != null) {
		IdentificativoDescrizioneBean idb = new IdentificativoDescrizioneBean();
		idb.setId(aft.getInventarioprocedimenti().getId().getCodice());
		idb.setDescrizione(aft.getDescrizione());
		result.add(pos, idb);
		pos++;
	    }
	}
	return result;
    }

    @Override
    public InventarioprocFoTop findByIntevento(Inventarioprocedimenti inventarioprocedimenti) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", inventarioprocedimenti.getId().getCodice(), "inventarioprocedimenti", Integer.class));
	fr.addFilterField(FilterUtils.equals("codice", inventarioprocedimenti.getSoftware().getCodice(), "software", Integer.class));
	ft.addRestriction(fr);
	List<InventarioprocFoTop> list = new ArrayList<InventarioprocFoTop>();
	list = inventarioprocfotopDAO.findByFilterTable(ft);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    private List<InventarioprocFoTop> findAllOrderedByCodiceAndDescrizione(Integer firstResult, Integer maxResult, FlagPubblicaEnum pubblicaEnum) {

	Boolean pubblica = null;
	switch (pubblicaEnum) {
	case DA_PUBBLICARE:
	    pubblica = Boolean.TRUE;
	    break;
	case NON_PUBBLICARE:
	    pubblica = Boolean.FALSE;
	    break;
	default:
	    break;
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	if (pubblica != null) {
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("flagPubblica", pubblica, "inventarioprocedimenti", Boolean.class));
	    ft.addRestriction(fr);
	}
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return inventarioprocfotopDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public List<InventarioprocFoTop> findBySoftware(String codicesoftware) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("codice", codicesoftware, "software", String.class));
	return inventarioprocfotopDAO.findByFilterTable(ft);
    }

    protected boolean isDeleteAllowed(InventarioprocFoTop entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
