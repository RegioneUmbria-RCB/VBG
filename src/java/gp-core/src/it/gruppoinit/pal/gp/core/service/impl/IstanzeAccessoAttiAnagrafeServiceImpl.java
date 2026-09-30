package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeAccessoAttiAnagrafeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiAnagrafe;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiAnagrafeId;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiAnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiTService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class IstanzeAccessoAttiAnagrafeServiceImpl extends BaseServiceImpl<IstanzeAccessoAttiAnagrafe, IstanzeAccessoAttiAnagrafeId> implements
	IstanzeAccessoAttiAnagrafeService {

    private static final Logger log = LoggerFactory.getLogger(IstanzeAccessoAttiAnagrafeServiceImpl.class);
    private IstanzeAccessoAttiAnagrafeDAO istanzeaccessoattianagrafeDAO;
    private IstanzeAccessoAttiTService istanzeAccessoAttiTService;
    private AnagrafeService anagrafeService;

    @Autowired
    public void setIstanzeAccessoAttiAnagrafeDAO(IstanzeAccessoAttiAnagrafeDAO istanzeaccessoattianagrafeDAO) {

	this.istanzeaccessoattianagrafeDAO = istanzeaccessoattianagrafeDAO;
    }

    @Override
    protected Class<IstanzeAccessoAttiAnagrafe> getEntityClass() {

	return IstanzeAccessoAttiAnagrafe.class;
    }

    @Override
    public List<IstanzeAccessoAttiAnagrafe> findAll(Integer firstResult, Integer maxResult) {

	return istanzeaccessoattianagrafeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IstanzeAccessoAttiAnagrafe entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeaccessoattianagrafeDAO.insert(entity);
	}
    }

    @Override
    public IstanzeAccessoAttiAnagrafe findById(IstanzeAccessoAttiAnagrafeId id) {

	return istanzeaccessoattianagrafeDAO.findById(id);
    }

    @Override
    public void update(IstanzeAccessoAttiAnagrafe entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeaccessoattianagrafeDAO.update(entity);
	}
    }

    @Override
    public void delete(IstanzeAccessoAttiAnagrafe entity) {

	if (isDeleteAllowed(entity)) {
	    istanzeaccessoattianagrafeDAO.delete(entity);
	}
    }

    private void dataIntegration(IstanzeAccessoAttiAnagrafe entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'istanza di accesso agli atti anagrafe è nulla");
	}
	//fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(IstanzeAccessoAttiAnagrafe entity) {

	IstanzeAccessoAttiT istAccAttiT = istanzeAccessoAttiTService.bindDomainObject(entity.getIstanzeAccessoAttiT(), PkId.class, "id.codice");
	entity.setIstanzeAccessoAttiT(istAccAttiT);
	Anagrafe anagrafe = anagrafeService.bindDomainObject(entity.getAnagrafe(), PkId.class, "id.codice");
	entity.setAnagrafe(anagrafe);
    }

    protected boolean isDeleteAllowed(IstanzeAccessoAttiAnagrafe entity) {

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

    @Override
    public List<IstanzeAccessoAttiAnagrafe> findByIstanzeAccessoAttiT(Integer codice, Integer firstResult, Integer maxResult) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "istanzeAccessoAttiT", Integer.class));
	filterTable.addRestriction(fr);
	return istanzeaccessoattianagrafeDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<IstanzeAccessoAttiAnagrafe> findByIstanzeAccessoAttiAnagrafeId(IstanzeAccessoAttiAnagrafeId id, Integer firstResult, Integer maxResult) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id", id, IstanzeAccessoAttiAnagrafeId.class));
	filterTable.addRestriction(fr);
	return istanzeaccessoattianagrafeDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<IstanzeAccessoAttiAnagrafe> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	filterTable.addRestriction(fr);
	return istanzeaccessoattianagrafeDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<IstanzeAccessoAttiAnagrafe> findByAnagrafeAndAccessoAttiT(Integer codiceAnagrafe, Integer codiceIstanzaAccessoAttiT) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanzaAccessoAttiT, "istanzeAccessoAttiT", Integer.class));
	filterTable.addRestriction(fr);
	return istanzeaccessoattianagrafeDAO.findByFilterTable(filterTable);
    }
}
