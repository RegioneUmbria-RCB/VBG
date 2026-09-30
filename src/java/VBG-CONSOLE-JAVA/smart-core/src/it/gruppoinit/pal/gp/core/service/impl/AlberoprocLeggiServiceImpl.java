package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocLeggiDAO;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocLeggi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AlberoprocLeggiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlberoprocLeggiServiceImpl extends BaseServiceImpl<AlberoprocLeggi, PkId> implements AlberoprocLeggiService {

    private AlberoprocLeggiDAO alberoprocLeggiDAO;
    private AlberoprocService alberoprocService;

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setAlberoprocLeggiDAO(AlberoprocLeggiDAO alberoprocLeggiDAO) {

	this.alberoprocLeggiDAO = alberoprocLeggiDAO;
    }

    @Override
    protected Class<AlberoprocLeggi> getEntityClass() {

	return AlberoprocLeggi.class;
    }

    @Override
    public void delete(AlberoprocLeggi entity) {

	alberoprocLeggiDAO.delete(entity);
    }

    @Override
    public List<AlberoprocLeggi> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocLeggiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public AlberoprocLeggi findById(PkId id) {

	return alberoprocLeggiDAO.findById(id);
    }

    @Override
    public void insert(AlberoprocLeggi entity) {

	if (isInsertUpdateAllowed(entity)) {
	    if (validateEntity(entity)) {
		alberoprocLeggiDAO.insert(entity);
	    }
	}
    }

    @Override
    public void update(AlberoprocLeggi entity) {

	if (isInsertUpdateAllowed(entity)) {
	    if (validateEntity(entity)) {
		alberoprocLeggiDAO.update(entity);
	    }
	}
    }

    @Override
    public List<AlberoprocLeggi> findByAlberoProc(String idcomune, Integer codice) {

	return alberoprocLeggiDAO.findByAlberoProc(idcomune, codice);
    }

    private boolean isInsertUpdateAllowed(AlberoprocLeggi entity) {

	boolean insertOrUpdate = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Alberoproc alberoproc = alberoprocService.findById(entity.getAlberoproc().getId());
	Set<AlberoprocLeggi> alberoprocLeggis = alberoproc.getAlberoprocLeggis();
	for (AlberoprocLeggi alberoprocLeggi : alberoprocLeggis) {
	    if (alberoprocLeggi.getLegge().getId().getCodice().compareTo(entity.getLegge().getId().getCodice()) == 0) {
		_ivs.add(new InvalidValue("alert.alberoprocLegge.legge_presente", null, "", "", null));
		insertOrUpdate = false;
		break;
	    }
	}
	if (!insertOrUpdate) {
	    this.throwValidationMessages(_ivs);
	}
	return insertOrUpdate;
    }
}
