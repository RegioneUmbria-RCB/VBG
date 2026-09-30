package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AmministrazioniruoliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazioniruoli;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniruoliId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AmministrazioniruoliService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmministrazioniruoliServiceImpl extends BaseServiceImpl<Amministrazioniruoli, AmministrazioniruoliId> implements
	AmministrazioniruoliService {

    private AmministrazioniruoliDAO amministrazioniruoliDAO;

    @Autowired
    public void setAmministrazioniruoliDAO(AmministrazioniruoliDAO amministrazioniruoliDAO) {

	this.amministrazioniruoliDAO = amministrazioniruoliDAO;
    }

    @Override
    protected Class<Amministrazioniruoli> getEntityClass() {

	return Amministrazioniruoli.class;
    }

    @Override
    public void delete(Amministrazioniruoli entity) {

	amministrazioniruoliDAO.delete(entity);
    }

    @Override
    public List<Amministrazioniruoli> findAll(Integer firstResult, Integer maxResult) {

	return amministrazioniruoliDAO.findAll(null, null);
    }

    @Override
    public Amministrazioniruoli findById(AmministrazioniruoliId id) {

	return amministrazioniruoliDAO.findById(id);
    }

    @Override
    public void insert(Amministrazioniruoli entity) {

	if (validateEntity(entity)) {
	    List<Amministrazioniruoli> amministrazioniruoliPresenti = this.findByAmministrazione(entity.getAmministrazioni());
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    for (Iterator iterator = amministrazioniruoliPresenti.iterator(); iterator.hasNext();) {
		Amministrazioniruoli amministrazioniruoli = (Amministrazioniruoli) iterator.next();
		if (amministrazioniruoli.getRuoli().equals(entity.getRuoli())) {
		    _ivs.add(new InvalidValue("amministrazioni.error.ruolo_presente", entity.getClass(), "", "", entity));
		    this.throwValidationMessages(_ivs);
		}
	    }
	    amministrazioniruoliDAO.insert(entity);
	}
    }

    @Override
    public void update(Amministrazioniruoli entity) {

	if (validateEntity(entity))
	    amministrazioniruoliDAO.update(entity);
    }

    @Override
    public List<Amministrazioniruoli> findByAmministrazione(Amministrazioni amministrazione) {

	return amministrazioniruoliDAO.findByAmministrazione(amministrazione);
    }

    @Override
    public List<Amministrazioniruoli> findByRuolo(Integer idRuolo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idruolo", idRuolo, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("amministrazione", "amministrazioni"));
	return amministrazioniruoliDAO.findByFilterTable(ft);
    }
}
