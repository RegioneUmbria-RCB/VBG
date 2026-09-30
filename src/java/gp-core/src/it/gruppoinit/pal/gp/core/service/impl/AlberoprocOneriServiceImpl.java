package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocOneriDAO;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocOneri;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AlberoprocOneriService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 */
@Service
public class AlberoprocOneriServiceImpl extends BaseServiceImpl<AlberoprocOneri, PkId> implements AlberoprocOneriService {

    private AlberoprocOneriDAO alberoproconeriDAO;
    private AlberoprocService alberoprocService;
    private Dyn2CampiService dyn2CampiService;

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setAlberoprocOneriDAO(AlberoprocOneriDAO alberoproconeriDAO) {

	this.alberoproconeriDAO = alberoproconeriDAO;
    }

    @Autowired
    public void setDyn2CampiService(Dyn2CampiService dyn2CampiService) {

	this.dyn2CampiService = dyn2CampiService;
    }

    @Override
    protected Class<AlberoprocOneri> getEntityClass() {

	return AlberoprocOneri.class;
    }

    @Override
    public List<AlberoprocOneri> findAll(Integer firstResult, Integer maxResult) {

	return alberoproconeriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AlberoprocOneri entity) {

	dataIntegration(entity);
	if (isInsertUpdateAllowed(entity)) {
	    if (validateEntity(entity)) {
		alberoproconeriDAO.insert(entity);
	    }
	}
    }

    @Override
    public AlberoprocOneri findById(PkId id) {

	return alberoproconeriDAO.findById(id);
    }

    @Override
    public void update(AlberoprocOneri entity) {

	dataIntegration(entity);
	if (isInsertUpdateAllowed(entity)) {
	    if (validateEntity(entity)) {
		alberoproconeriDAO.update(entity);
	    }
	}
    }

    @Override
    public void delete(AlberoprocOneri entity) {

	if (isDeleteAllowed(entity)) {
	    alberoproconeriDAO.delete(entity);
	}
    }

    @Override
    public List<AlberoprocOneri> findAllByAlberoproc(Integer codiceAlberoproc) {

	return alberoproconeriDAO.findAllByAlberoproc(codiceAlberoproc);
    }

    private void dataIntegration(AlberoprocOneri entity) {

	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(AlberoprocOneri entity) {

	Dyn2Campi dyn2campi = dyn2CampiService.bindDomainObject(entity.getDyn2Campi(), PkId.class, "id.codice");
	entity.setDyn2Campi(dyn2campi);
    }

    private boolean isInsertUpdateAllowed(AlberoprocOneri entity) {

	boolean insertOrUpdate = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getTipicausalioneri() != null && entity.getTipicausalioneri().getId() != null) {
	    Alberoproc alberoproc = alberoprocService.findById(entity.getAlberoproc().getId());
	    Set<AlberoprocOneri> alberoprocOneriSet = alberoproc.getAlberoprocOneris();
	    for (AlberoprocOneri alberoprocOneri : alberoprocOneriSet) {
		if ((alberoprocOneri.getTipicausalioneri().getId().getCodice().compareTo(entity.getTipicausalioneri().getId().getCodice()) == 0)) {
		    if (entity.getId().getCodice() != null) {
			if (alberoprocOneri.getId().getCodice().compareTo(entity.getId().getCodice()) != 0) {
			    _ivs.add(new InvalidValue("alert.alberoprocOneri.causale_presente", null, "", "", null));
			    insertOrUpdate = false;
			    break;
			}
		    } else {
			_ivs.add(new InvalidValue("alert.alberoprocOneri.causale_presente", null, "", "", null));
			insertOrUpdate = false;
			break;
		    }
		}
	    }
	    if (!insertOrUpdate) {
		this.throwValidationMessages(_ivs);
	    }
	}
	return insertOrUpdate;
    }
}
