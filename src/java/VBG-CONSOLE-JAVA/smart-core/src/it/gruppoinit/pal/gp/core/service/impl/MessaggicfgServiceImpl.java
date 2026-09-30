package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MessaggicfgDAO;
import it.gruppoinit.pal.gp.core.domain.Messaggicfg;
import it.gruppoinit.pal.gp.core.domain.Messaggicfgbase;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.MessaggicfgService;
import it.gruppoinit.pal.gp.core.service.MessaggicfgbaseService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessaggicfgServiceImpl extends BaseServiceImpl<Messaggicfg, PkId> implements MessaggicfgService {

    private MessaggicfgDAO messaggicfgDAO;
    private SoftwareService softwareService;
    private MessaggicfgbaseService messaggicfgbaseService;

    @Autowired
    public void setMessaggicfgDAO(MessaggicfgDAO messaggicfgDAO) {

	this.messaggicfgDAO = messaggicfgDAO;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setMessaggicfgbaseService(MessaggicfgbaseService messaggicfgbaseService) {

	this.messaggicfgbaseService = messaggicfgbaseService;
    }

    @Override
    protected Class<Messaggicfg> getEntityClass() {

	return Messaggicfg.class;
    }

    @Override
    public List<Messaggicfg> findAll(Integer firstResult, Integer maxResult) {

	return messaggicfgDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Messaggicfg entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertOrUpdateAllowed(entity)) {
	    messaggicfgDAO.insert(entity);
	}
    }

    @Override
    public Messaggicfg findById(PkId id) {

	return messaggicfgDAO.findById(id);
    }

    @Override
    public void update(Messaggicfg entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertOrUpdateAllowed(entity)) {
	    messaggicfgDAO.update(entity);
	}
    }

    @Override
    public void delete(Messaggicfg entity) {

	if (isDeleteAllowed(entity)) {
	    messaggicfgDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Messaggicfg entity) {

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
    public Messaggicfg findBySoftware(Software software) {

	return messaggicfgDAO.findBySoftware(software);
    }

    private void dataIntegration(Messaggicfg entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro Messaggicfg è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Messaggicfg entity) {

	Messaggicfgbase messaggicfgbase = messaggicfgbaseService.bindDomainObject(entity.getMessaggicfgbase(), String.class, "contesto");
	entity.setMessaggicfgbase(messaggicfgbase);
	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
    }

    private boolean isInsertOrUpdateAllowed(Messaggicfg entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getMessaggicfgbase() != null && !entity.getMessaggicfgbase().getContesto().equals(WebConstants.INVIO_ISTANZA_BACKOFFICE)
		&& StringUtils.isBlank(entity.getCorpo())) {
	    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", getEntityClass(), "corpo", entity.getCorpo(), entity));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
