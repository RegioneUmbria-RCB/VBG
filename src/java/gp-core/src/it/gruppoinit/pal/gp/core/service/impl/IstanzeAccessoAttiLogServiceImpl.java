package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.IstanzeAccessoAttiLogDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiLog;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiLogId;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeAccessoAttiFilter;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiLogService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiTService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

/**
 * 
 * @author
 */
@Service
public class IstanzeAccessoAttiLogServiceImpl extends BaseServiceImpl<IstanzeAccessoAttiLog, IstanzeAccessoAttiLogId>
	implements IstanzeAccessoAttiLogService {

    private static final Logger log = LoggerFactory.getLogger(IstanzeAccessoAttiLogServiceImpl.class);
    private IstanzeAccessoAttiLogDAO istanzeaccessoattilogDAO;
    private IstanzeAccessoAttiTService istanzeAccessoAttiTService;
    private IstanzeService istanzeService;
    private AnagrafeService anagrafeService;

    @Autowired
    public void setIstanzeAccessoAttiLogDAO(IstanzeAccessoAttiLogDAO istanzeaccessoattilogDAO) {

	this.istanzeaccessoattilogDAO = istanzeaccessoattilogDAO;
    }

    @Override
    protected Class<IstanzeAccessoAttiLog> getEntityClass() {

	return IstanzeAccessoAttiLog.class;
    }

    @Override
    public List<IstanzeAccessoAttiLog> findAll(Integer firstResult, Integer maxResult) {

	return istanzeaccessoattilogDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IstanzeAccessoAttiLog entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeaccessoattilogDAO.insert(entity);
	}
    }

    @Override
    public IstanzeAccessoAttiLog findById(IstanzeAccessoAttiLogId id) {

	return istanzeaccessoattilogDAO.findById(id);
    }

    @Override
    public void update(IstanzeAccessoAttiLog entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeaccessoattilogDAO.update(entity);
	}
    }

    @Override
    public void delete(IstanzeAccessoAttiLog entity) {

	if (isDeleteAllowed(entity)) {
	    istanzeaccessoattilogDAO.delete(entity);
	}
    }

    private void dataIntegration(IstanzeAccessoAttiLog entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'istanza di accesso agli atti log è nulla");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(IstanzeAccessoAttiLog entity) {

	IstanzeAccessoAttiT istAccT = istanzeAccessoAttiTService.bindDomainObject(entity.getIstanzeAccessoAttiT(), PkId.class, "id.codice");
	entity.setIstanzeAccessoAttiT(istAccT);
	Istanze ist = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(ist);
	Anagrafe anagrafe = anagrafeService.bindDomainObject(entity.getAnagrafe(), PkId.class, "id.codice");
	entity.setAnagrafe(anagrafe);
    }

    protected boolean isDeleteAllowed(IstanzeAccessoAttiLog entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	/*TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	 */
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<IstanzeAccessoAttiLog> findByIstanzeAccessoAttiT(Integer codice, Integer firstResult, Integer maxResult) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "istanzeAccessoAttiT", Integer.class));
	filterTable.addRestriction(fr);
	return istanzeaccessoattilogDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<IstanzeAccessoAttiLog> findByIstanza(Integer codiceistanza, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceistanza, "istanze", Integer.class));
	ft.addRestriction(fr);
	return istanzeaccessoattilogDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public int countByFilter(IstanzeAccessoAttiFilter filter) {

	return istanzeaccessoattilogDAO.countByFilter(filter);
    }

    @Override
    public List<IstanzeAccessoAttiLog> findIstanzeAccessoAttiLogByFilter(IstanzeAccessoAttiFilter filter, Integer firstResult, Integer maxResult) {

	return istanzeaccessoattilogDAO.findIstanzeAccessoAttiLogByFilter(filter, firstResult, maxResult);
    }

    @Override
    public void clear() {

	istanzeaccessoattilogDAO.clear();
    }
}
