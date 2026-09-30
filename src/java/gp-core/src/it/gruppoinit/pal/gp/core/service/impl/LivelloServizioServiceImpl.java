package it.gruppoinit.pal.gp.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.LivelloServizioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.LivelloServizioService;

/**
 * 
 * @author
 */
@Service
public class LivelloServizioServiceImpl extends BaseServiceImpl<LivelloServizio, PkId> implements LivelloServizioService {

    private LivelloServizioDAO livelloservizioDAO;

    @Autowired
    public void setLivelloServizioDAO(LivelloServizioDAO livelloservizioDAO) {

	this.livelloservizioDAO = livelloservizioDAO;
    }

    @Override
    protected Class<LivelloServizio> getEntityClass() {

	return LivelloServizio.class;
    }

    @Override
    public List<LivelloServizio> findAll(Integer firstResult, Integer maxResult) {

	return livelloservizioDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(LivelloServizio entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity)) {
	    if (controlloEsistenzaSegnaposto(entity.getSegnaposto())) {
		throw new RuntimeException("Segnaposto già presente");
	    } else {
		livelloservizioDAO.insert(entity);
	    }
	}
	resetObjectCached();
    }

    @Override
    public LivelloServizio findById(PkId id) {

	return livelloservizioDAO.findById(id);
    }

    @Override
    public void update(LivelloServizio entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity)) {
	    livelloservizioDAO.update(entity);
	}
	resetObjectCached();
    }

    @Override
    public void delete(LivelloServizio entity) {

	if (isDeleteAllowed(entity)) {
	    livelloservizioDAO.delete(entity);
	} else {
	    throw new RuntimeException("Il segnaposto è utilizzato nella tabella MERCATI_LIVELLO_SERVIZIO");
	}
	resetObjectCached();
    }

    @Override
    public List<LivelloServizio> findByDescrizione(String textToSearch) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction a = new FilterRestriction();
	a.addFilterField(FilterUtils.like("descrizione", "%" + textToSearch + "%"));
	ft.addRestriction(a);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return livelloservizioDAO.findByFilterTable(ft);
    }

    @Override
    public List<LivelloServizio> findByMercatoUso(Integer codiceuso, boolean isSoloAttive) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction a = new FilterRestriction();
	a.addFilterField(FilterUtils.equals("id.codice", codiceuso, "mercatiLivelloServizios.mercatiUso", Integer.class));
	if (isSoloAttive) {
	    a.addFilterField(FilterUtils.equals("attivo", isSoloAttive, "mercatiLivelloServizios", Boolean.class));
	}
	ft.addRestriction(a);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return livelloservizioDAO.findByFilterTable(ft);
    }

    private void dataIntegration(LivelloServizio entity, boolean isUpdate) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro ComunicazioniT da validare è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(LivelloServizio entity) {

    }

    protected boolean isDeleteAllowed(LivelloServizio entity) {

	boolean delete = false;
	Set<MercatiLivelloServizio> mercatiLivelloServizio = entity.getMercatiLivelloServizios();
	if (mercatiLivelloServizio.isEmpty()) {
	    delete = true;
	} else {
	    delete = false;
	}
	return delete;
    }

    /*
     *  Metodo per verificare se è già presente un segnaposto
     */
    private boolean controlloEsistenzaSegnaposto(String segnaposto) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("segnaposto", segnaposto, String.class));
	ft.addRestriction(fr);
	if (livelloservizioDAO.findByFilterTable(ft).size() > 0) {
	    return true;
	} else {
	    return false;
	}
    }

    private Map<String, List<LivelloServizio>> getChachedMap() {

	if (this.chached == null) {
	    this.chached = new HashMap<String, List<LivelloServizio>>();
	}
	return this.chached;
    }

    Map<String, List<LivelloServizio>> chached = null;

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	chached = new HashMap<String, List<LivelloServizio>>();
    }

    @Override
    public List<LivelloServizio> findServiziDisponibili() {

	List<LivelloServizio> ret = getChachedMap().get(ORMHelper.getIdcomune());
	if (ret == null) {
	    ret = this.livelloservizioDAO.findAll(null, null, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
	    getChachedMap().put(ORMHelper.getIdcomune(), ret);
	}
	return ret;
    }
}
