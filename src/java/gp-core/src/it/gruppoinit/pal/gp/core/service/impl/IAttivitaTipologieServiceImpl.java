package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.IAttivitaTipologieDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.IAttivitaTipologie;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IAttivitaSnapshotService;
import it.gruppoinit.pal.gp.core.service.IAttivitaTipologieService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class IAttivitaTipologieServiceImpl extends BaseServiceImpl<IAttivitaTipologie, PkId> implements IAttivitaTipologieService {

    private IAttivitaTipologieDAO iattivitatipologieDAO;
    private IAttivitaService iAttivitaService;
    private IAttivitaSnapshotService iAttivitaSnapshotService;

    @Autowired
    public void setiAttivitaSnapshotService(IAttivitaSnapshotService iAttivitaSnapshotService) {

	this.iAttivitaSnapshotService = iAttivitaSnapshotService;
    }

    @Autowired
    public void setiAttivitaService(IAttivitaService iAttivitaService) {

	this.iAttivitaService = iAttivitaService;
    }

    @Autowired
    public void setIAttivitaTipologieDAO(IAttivitaTipologieDAO iattivitatipologieDAO) {

	this.iattivitatipologieDAO = iattivitatipologieDAO;
    }

    @Override
    protected Class<IAttivitaTipologie> getEntityClass() {

	return IAttivitaTipologie.class;
    }

    @Override
    public List<IAttivitaTipologie> findAll(Integer firstResult, Integer maxResult) {

	return iattivitatipologieDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IAttivitaTipologie entity) {

	if (validateEntity(entity)) {
	    iattivitatipologieDAO.insert(entity);
	}
    }

    @Override
    public IAttivitaTipologie findById(PkId id) {

	return iattivitatipologieDAO.findById(id);
    }

    @Override
    public void update(IAttivitaTipologie entity) {

	if (validateEntity(entity)) {
	    iattivitatipologieDAO.update(entity);
	}
    }

    @Override
    public void delete(IAttivitaTipologie entity) {

	if (isDeleteAllowed(entity)) {
	    iattivitatipologieDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(IAttivitaTipologie entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (iAttivitaService.findByIattivitaTipologie(entity.getId().getCodice(), 0, 2).size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "I_ATTIVITA", null));
	}
	if (iAttivitaSnapshotService.findByIattivitaTipologie(entity.getId().getCodice(), 0, 2).size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "I_ATTIVITA_SNAPSHOT", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<IAttivitaTipologie> findByDescrizione(String descrizione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.like("descrizione", descrizione));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return iattivitatipologieDAO.findByFilterTable(ft);
    }

    @Override
    public List<IAttivitaTipologie> findByDescrizioneAndSoftware(String descrizione, String paramSoftware) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.like("descrizione", descrizione));
	fr.addFilterField(FilterUtils.equals("software.codice", paramSoftware, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return iattivitatipologieDAO.findByFilterTable(ft);
    }
}
