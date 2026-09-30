package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.LottiDAO;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Lotti;
import it.gruppoinit.pal.gp.core.domain.LottiId;
import it.gruppoinit.pal.gp.core.service.LottiService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Riccardo Bocci
 */
@Service
public class LottiServiceImpl extends BaseServiceImpl<Lotti, LottiId> implements LottiService {

    private LottiDAO lottiDAO;

    @Autowired
    public void setLottiDAO(LottiDAO lottiDAO) {

	this.lottiDAO = lottiDAO;
    }

    @Override
    protected Class<Lotti> getEntityClass() {

	return Lotti.class;
    }

    @Override
    public List<Lotti> findAll(Integer firstResult, Integer maxResult) {

	return lottiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Lotti entity) {

	if (validateEntity(entity)) {
	    List<Lotti> lotti = lottiDAO.findByAree(entity.getAree());
	    int max = 0;
	    for (Lotti lotti2 : lotti) {
		max = lotti2.getId().getCodicelotto();
	    }
	    max++;
	    entity.getId().setCodicelotto(max);
	    if (validateInsert(entity)) {
		lottiDAO.insert(entity);
	    }
	}
    }

    @Override
    public Lotti findById(LottiId id) {

	return lottiDAO.findById(id);
    }

    @Override
    public void update(Lotti entity) {

	if (validateEntity(entity)) {
	    lottiDAO.update(entity);
	}
    }

    @Override
    public void delete(Lotti entity) {

	if (isDeleteAllowed(entity)) {
	    lottiDAO.delete(entity);
	}
    }

    @Override
    public List<Lotti> findByAree(Aree aree) {

	return lottiDAO.findByAree(aree);
    }

    private boolean validateInsert(Lotti entity) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	int codiceLotto = entity.getId().getCodicelotto();
	int codiceArea = entity.getId().getCodicearea();
	LottiId id = new LottiId();
	id.setCodicearea(codiceArea);
	id.setCodicelotto(codiceLotto);
	Lotti lotto = lottiDAO.findById(id);
	if (lotto != null) {
	    _ivs.add(new InvalidValue("service_error.duplicate_codice", null, null, entity.getId().getCodicelotto(), null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }
}
