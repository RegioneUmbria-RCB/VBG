package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AmministrazioniresponsabiliDAO;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazioniresponsabili;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AmministrazioniresponsabiliService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmministrazioniresponsabiliServiceImpl extends BaseServiceImpl<Amministrazioniresponsabili, PkId> implements
	AmministrazioniresponsabiliService {

    private AmministrazioniresponsabiliDAO amministrazioniresponsabiliDAO;

    @Autowired
    public void setAmministrazioniresponsabiliDAO(AmministrazioniresponsabiliDAO amministrazioniresponsabiliDAO) {

	this.amministrazioniresponsabiliDAO = amministrazioniresponsabiliDAO;
    }

    @Override
    protected Class<Amministrazioniresponsabili> getEntityClass() {

	return Amministrazioniresponsabili.class;
    }

    @Override
    public void delete(Amministrazioniresponsabili entity) {

	amministrazioniresponsabiliDAO.delete(entity);
    }

    @Override
    public List<Amministrazioniresponsabili> findAll(Integer firstResult, Integer maxResult) {

	return amministrazioniresponsabiliDAO.findAll(null, null);
    }

    @Override
    public Amministrazioniresponsabili findById(PkId id) {

	return amministrazioniresponsabiliDAO.findById(id);
    }

    @Override
    public void insert(Amministrazioniresponsabili entity) {

	if (validateEntity(entity)) {
	    List<Amministrazioniresponsabili> amministrazioniresponsabiliPresenti = this.findByAmministrazione(entity.getAmministrazioni());
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    for (Iterator iterator = amministrazioniresponsabiliPresenti.iterator(); iterator.hasNext();) {
		Amministrazioniresponsabili amministrazioniresponsabili = (Amministrazioniresponsabili) iterator.next();
		if (amministrazioniresponsabili.getResponsabili().equals(entity.getResponsabili())) {
		    _ivs.add(new InvalidValue("amministrazioni.error.responsabile_presente", entity.getClass(), "", "", entity));
		    this.throwValidationMessages(_ivs);
		}
	    }
	    amministrazioniresponsabiliDAO.insert(entity);
	}
    }

    @Override
    public void update(Amministrazioniresponsabili entity) {

	if (validateEntity(entity))
	    amministrazioniresponsabiliDAO.update(entity);
    }

    @Override
    public List<Amministrazioniresponsabili> findByAmministrazione(Amministrazioni amministrazione) {

	return amministrazioniresponsabiliDAO.findByAmministrazione(amministrazione);
    }
}
