package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipimovStcAltridatiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAltridati;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.TipimovStcAltridatiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TipimovStcAltridatiServiceImpl extends BaseServiceImpl<TipimovStcAltridati, PkId> implements TipimovStcAltridatiService {

    private TipimovStcAltridatiDAO tipimovStcAltridatiDAO;

    @Autowired
    public void setTipimovStcAltridatiDAO(TipimovStcAltridatiDAO tipimovStcAltridatiDAO) {

	this.tipimovStcAltridatiDAO = tipimovStcAltridatiDAO;
    }

    @Override
    protected Class<TipimovStcAltridati> getEntityClass() {

	return TipimovStcAltridati.class;
    }

    @Override
    public void delete(TipimovStcAltridati entity) {

	tipimovStcAltridatiDAO.delete(entity);
    }

    @Override
    public List<TipimovStcAltridati> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return tipimovStcAltridatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public TipimovStcAltridati findById(PkId id) {

	return tipimovStcAltridatiDAO.findById(id);
    }

    @Override
    public void insert(TipimovStcAltridati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipimovStcAltridatiDAO.insert(entity);
	}
    }

    @Override
    public void update(TipimovStcAltridati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipimovStcAltridatiDAO.update(entity);
	}
    }

    private void dataIntegration(TipimovStcAltridati entity) {

	if (entity == null) {
	    throw new RuntimeException("Entity nulla");
	}
	if (entity.getFlagHelp() == null) {
	    entity.setFlagHelp(Boolean.FALSE);
	}
    }

    @Override
    public List<TipimovStcAltridati> findByTipimovimento(TipimovimentoId tipimovimentoId) {

	return tipimovStcAltridatiDAO.findByTipimovimento(tipimovimentoId);
    }

    @Override
    public List<TipimovStcAltridati> findByTipimovimentoAndAmministrazione(String tipoMovimento, Integer codiceAmministrazioneStc) {

	Assert.notNull(tipoMovimento, "Il parametro tipoMovimento non può essere nullo");
	Assert.hasLength(tipoMovimento, "Il parametro tipoMovimento non può essere vuoto");
	Assert.notNull(codiceAmministrazioneStc, "Il parametro codiceAmministrazione non può essere nullo");
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipimovimentoId", tipoMovimento, String.class));
	fr.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazioneStc, Integer.class));
	ft.addRestriction(fr);
	List<TipimovStcAltridati> lst = tipimovStcAltridatiDAO.findByFilterTable(ft);
	return lst;
    }

    @Override
    public List<TipimovStcAltridati> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAmministrazione, "amministrazioni", Integer.class));
	filterTable.addRestriction(fr);
	return tipimovStcAltridatiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }
}
