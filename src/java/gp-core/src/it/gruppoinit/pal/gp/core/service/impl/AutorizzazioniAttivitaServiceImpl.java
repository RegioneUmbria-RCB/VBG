package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AutorizzazioniAttivitaDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniAttivita;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniAttivitaDTO;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniAttivitaService;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorizzazioniAttivitaServiceImpl extends BaseServiceImpl<AutorizzazioniAttivita, PkId> implements AutorizzazioniAttivitaService {

    @Autowired
    private AutorizzazioniAttivitaDAO autorizzazioniAttivitaDAO;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private AttivitaService attivitaService;

    @Override
    public void insert(AutorizzazioniAttivita entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    autorizzazioniAttivitaDAO.insert(entity);
	}
    }

    @Override
    public void update(AutorizzazioniAttivita entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    autorizzazioniAttivitaDAO.update(entity);
	}
    }

    private void dataIntegration(AutorizzazioniAttivita entity) {

    }

    @Override
    protected void fixMergeEntityProperties(AutorizzazioniAttivita entity) {

	Autorizzazioni a = autorizzazioniService.bindDomainObject(entity.getAutorizzazioni(), PkId.class, "id.codice");
	entity.setAutorizzazioni(a);
	Attivita attivita = attivitaService.bindDomainObject(entity.getAttivita(), PkId.class, "id.codice");
	entity.setAttivita(attivita);
    }

    @Override
    public void delete(AutorizzazioniAttivita entity) {

	if (isDeleteAllowed(entity)) {
	    autorizzazioniAttivitaDAO.delete(entity);
	}
    }

    @Override
    public List<AutorizzazioniAttivita> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public AutorizzazioniAttivita findById(PkId id) {

	return autorizzazioniAttivitaDAO.findById(id);
    }

    @Override
    protected Class<AutorizzazioniAttivita> getEntityClass() {

	return AutorizzazioniAttivita.class;
    }

    @Override
    public void deleteByAutorizzazione(Integer codiceAutorizzazione) {

	autorizzazioniAttivitaDAO.deleteByAutorizzazione(codiceAutorizzazione);
    }

    @Override
    public void deleteByAttivita(String codiceAttivita) {

	autorizzazioniAttivitaDAO.deleteByAttivita(codiceAttivita);
    }

    @Override
    public List<AutorizzazioniAttivita> findByAutorizzazione(Integer codiceAutorizzazione, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("autorizzazioniId", codiceAutorizzazione, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("attivitaId"));
	return autorizzazioniAttivitaDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public List<AutorizzazioniAttivitaDTO> findByAutorizzazioni(Set<Integer> auts) {

	return autorizzazioniAttivitaDAO.findByAutorizzazioni(auts);
    }
}
