package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ConcessionicausaliDAO;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.ConcessionicausaliService;

@Service
public class ConcessionicausaliServiceImpl extends BaseServiceImpl<Concessionicausali, PkId> implements ConcessionicausaliService {

    private ConcessionicausaliDAO concessionicausaliDAO;

    @Autowired
    public void setConcessionicausaliDAO(ConcessionicausaliDAO concessionicausaliDAO) {

	this.concessionicausaliDAO = concessionicausaliDAO;
    }

    @Override
    protected Class<Concessionicausali> getEntityClass() {

	return Concessionicausali.class;
    }

    @Override
    public void delete(Concessionicausali entity) {

	if (isDeleteAllowed(entity)) {
	    concessionicausaliDAO.delete(entity);
	}
    }

    @Override
    public List<Concessionicausali> findAll(Integer firstResult, Integer maxResult) {

	return concessionicausaliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Concessionicausali findById(PkId id) {

	return concessionicausaliDAO.findById(id);
    }

    @Override
    public void insert(Concessionicausali entity) {

	if (validateEntity(entity)) {
	    concessionicausaliDAO.insert(entity);
	}
    }

    @Override
    public void update(Concessionicausali entity) {

	if (validateEntity(entity)) {
	    concessionicausaliDAO.update(entity);
	}
    }

    @Override
    public List<Concessionicausali> findByDescrizioneAndFlagStorico(Concessionicausali entity) {

	return concessionicausaliDAO.findByDescrizioneAndFlagStorico(entity);
    }

    @Override
    public List<Concessionicausali> findAllbyCausaleStorico(boolean isStorico) {

	return concessionicausaliDAO.findAllbyCausaleStorico(isStorico);
    }

    protected boolean isDeleteAllowed(Concessionicausali entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getMercatiConfiguraziones().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATI_CONFIGURAZIONE", null));
	}
	// FIXME controllare autorizzazioni,concessioni,subentri collegati!!!!
	// if (entity.getIstanzeconcessionis().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZECONCESSIONI", null));
	// }
	// if (entity.getIstanzeconcessionistoricos().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null,
	// "ISTANZECONCESSIONI.FKCODICECAUSALESTORICO", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Concessionicausali> findAllByAffitto(Concessionicausali concessionicausali) {

	return concessionicausaliDAO.findAllByAffitto(concessionicausali);
    }

    @Override
    public boolean isAffitto(Integer codiceConcCausale) {

	Concessionicausali concessionicausali = this.findById(new PkId(codiceConcCausale));
	return concessionicausali.isFlagCausaliAffitto();
    }
}
