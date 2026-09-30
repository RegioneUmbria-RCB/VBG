package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipiorariodettaglioDAO;
import it.gruppoinit.pal.gp.core.domain.Giornisettimana;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiapertura;
import it.gruppoinit.pal.gp.core.domain.Tipiorario;
import it.gruppoinit.pal.gp.core.domain.Tipiorariodettaglio;
import it.gruppoinit.pal.gp.core.service.GiornisectimanaService;
import it.gruppoinit.pal.gp.core.service.TipiaperturaService;
import it.gruppoinit.pal.gp.core.service.TipiorarioService;
import it.gruppoinit.pal.gp.core.service.TipiorariodettaglioService;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author lucap
 */
@Service
public class TipiorariodettaglioServiceImpl extends BaseServiceImpl<Tipiorariodettaglio, PkId> implements TipiorariodettaglioService {

    private TipiorariodettaglioDAO tipiorariodettaglioDAO;
    private TipiaperturaService tipiaperturaService;
    private TipiorarioService tipiorarioService;
    private GiornisectimanaService giornisectimanaService;

    @Autowired
    public void setTipiorariodettaglioDAO(TipiorariodettaglioDAO tipiorariodettaglioDAO) {

	this.tipiorariodettaglioDAO = tipiorariodettaglioDAO;
    }

    @Autowired
    public void setTipiaperturaService(TipiaperturaService tipiaperturaService) {

	this.tipiaperturaService = tipiaperturaService;
    }

    @Autowired
    public void setTipiorarioService(TipiorarioService tipiorarioService) {

	this.tipiorarioService = tipiorarioService;
    }

    @Autowired
    public void setGiornisectimanaService(GiornisectimanaService giornisectimanaService) {

	this.giornisectimanaService = giornisectimanaService;
    }

    @Override
    protected Class<Tipiorariodettaglio> getEntityClass() {

	return Tipiorariodettaglio.class;
    }

    @Override
    public List<Tipiorariodettaglio> findAll(Integer firstResult, Integer maxResult) {

	return tipiorariodettaglioDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Tipiorariodettaglio entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipiorariodettaglioDAO.insert(entity);
	}
    }

    @Override
    public Tipiorariodettaglio findById(PkId id) {

	return tipiorariodettaglioDAO.findById(id);
    }

    @Override
    public void update(Tipiorariodettaglio entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipiorariodettaglioDAO.update(entity);
	}
    }

    @Override
    public void delete(Tipiorariodettaglio entity) {

	if (isDeleteAllowed(entity)) {
	    tipiorariodettaglioDAO.delete(entity);
	}
    }

    @Override
    public void deleteByTipiorario(Tipiorario tipiorario) {

	Set<Tipiorariodettaglio> list = tipiorario.getTipiorariodettaglios();
	for (Tipiorariodettaglio tipiorariodettaglio : list) {
	    this.delete(tipiorariodettaglio);
	}
	tipiorariodettaglioDAO.flush();
    }

    private void dataIntegration(Tipiorariodettaglio entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro tipiorariodettaglio è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Tipiorariodettaglio entity) {

	Tipiapertura tipiapertura = tipiaperturaService.bindDomainObject(entity.getTipiapertura(), PkId.class, "id.codice");
	entity.setTipiapertura(tipiapertura);
	Tipiorario tipiorario = tipiorarioService.bindDomainObject(entity.getTipiorario(), PkId.class, "id.codice");
	entity.setTipiorario(tipiorario);
	Giornisettimana giornisectimana = giornisectimanaService.bindDomainObject(entity.getGiornisectimana(), Integer.class, "id");
	entity.setGiornisectimana(giornisectimana);
    }
}
