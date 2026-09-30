package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocFoTopDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocFoTop;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocFoTopService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

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
public class AlberoprocFoTopServiceImpl extends BaseServiceImpl<AlberoprocFoTop, PkId> implements AlberoprocFoTopService {

    private AlberoprocFoTopDAO alberoprocfotopDAO;
    private AlberoprocService alberoprocService;
    private SoftwareService softwareService;

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setAlberoprocFoTopDAO(AlberoprocFoTopDAO alberoprocfotopDAO) {

	this.alberoprocfotopDAO = alberoprocfotopDAO;
    }

    @Override
    protected Class<AlberoprocFoTop> getEntityClass() {

	return AlberoprocFoTop.class;
    }

    @Override
    public List<AlberoprocFoTop> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocfotopDAO.findAll(firstResult, maxResult);
    }

    private List<AlberoprocFoTop> findAllOrderedByCodiceAndDescrizione(Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return alberoprocfotopDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public List<IdentificativoDescrizioneBean> findInterventi(Integer firstResult, Integer maxResult) {

	List<IdentificativoDescrizioneBean> result = new ArrayList<IdentificativoDescrizioneBean>();
	List<AlberoprocFoTop> list = this.findAllOrderedByCodiceAndDescrizione(firstResult, maxResult);
	int pos = 0;
	for (AlberoprocFoTop aft : list) {
	    if (aft.getAlberoproc() != null) {
		IdentificativoDescrizioneBean idb = new IdentificativoDescrizioneBean();
		idb.setId(aft.getAlberoproc().getId().getCodice());
		idb.setDescrizione(aft.getDescrizione());
		result.add(pos, idb);
		pos++;
	    }
	}
	return result;
    }

    @Override
    public void insert(AlberoprocFoTop entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    alberoprocfotopDAO.insert(entity);
	}
    }

    @Override
    public AlberoprocFoTop findById(PkId id) {

	return alberoprocfotopDAO.findById(id);
    }

    @Override
    public void update(AlberoprocFoTop entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    alberoprocfotopDAO.update(entity);
	}
    }

    private void dataIntegration(AlberoprocFoTop entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro entity non può essere nullo");
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(AlberoprocFoTop entity) {

	Alberoproc ap = alberoprocService.bindDomainObject(entity.getAlberoproc(), PkId.class, "id.codice");
	entity.setAlberoproc(ap);
	Software s = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(s);
    }

    @Override
    public void delete(AlberoprocFoTop entity) {

	if (isDeleteAllowed(entity)) {
	    alberoprocfotopDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(AlberoprocFoTop entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
