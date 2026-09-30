package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OneritipirateizzazioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.OneritipirateizzazioneService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OneritipirateizzazioneServiceImpl extends BaseServiceImpl<Oneritipirateizzazione, PkId> implements OneritipirateizzazioneService {

    private OneritipirateizzazioneDAO oneritipirateizzazioneDAO;

    @Autowired
    public void setOneritipirateizzazioneDAO(OneritipirateizzazioneDAO oneritipirateizzazioneDAO) {

	this.oneritipirateizzazioneDAO = oneritipirateizzazioneDAO;
    }

    @Override
    public void delete(Oneritipirateizzazione entity) {

	oneritipirateizzazioneDAO.delete(entity);
    }

    @Override
    public List<Oneritipirateizzazione> findAll(Integer firstResult, Integer maxResult) {

	return oneritipirateizzazioneDAO.findAll(null, null);
    }

    @Override
    public Oneritipirateizzazione findById(PkId id) {

	return oneritipirateizzazioneDAO.findById(id);
    }

    @Override
    public void insert(Oneritipirateizzazione entity) {

	if (validateEntity(entity)) {
	    oneritipirateizzazioneDAO.insert(entity);
	}
    }

    @Override
    public void update(Oneritipirateizzazione entity) {

	if (validateEntity(entity)) {
	    oneritipirateizzazioneDAO.update(entity);
	}
    }

    @Override
    protected Class<Oneritipirateizzazione> getEntityClass() {

	return Oneritipirateizzazione.class;
    }

    @Override
    public List<Oneritipirateizzazione> findAllSenzaInteressiLegali() {

	return oneritipirateizzazioneDAO.findAllSenzaInteressiLegali();
    }

    @Override
    public List<Oneritipirateizzazione> findByTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult) {

	if (StringUtils.isBlank(tipomovimento)) {
	    throw new IllegalArgumentException("Il parametro tipomovimento non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("tipimovimentoId", tipomovimento, String.class));
	ft.addRestriction(criterio);
	ft.addOrder(FilterUtils.orderAsc("ordine", "software"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "software"));
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	List<Oneritipirateizzazione> list = oneritipirateizzazioneDAO.findByFilterTable(ft, firstResult, maxResult);
	return list;
    }
}
