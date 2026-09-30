package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.CommedilizieTipolRuoliDAO;
import it.gruppoinit.pal.gp.core.dao.CommedilizieTipologieDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipolRuoli;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipolRuoliId;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologie;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedett;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipologieService;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipologiedettService;

/**
 * 
 * @author
 */
@Service
public class CommedilizieTipologieServiceImpl extends BaseServiceImpl<CommedilizieTipologie, PkId> implements CommedilizieTipologieService {

    private CommedilizieTipologieDAO commedilizietipologieDAO;
    private CommedilizieTipologiedettService commedilizieTipologiedettService;
    private CommedilizieTipolRuoliDAO commedilizieTipolRuoliDAO;

    @Autowired
    public void setCommedilizieTipolRuoliDAO(CommedilizieTipolRuoliDAO commedilizieTipolRuoliDAO) {

	this.commedilizieTipolRuoliDAO = commedilizieTipolRuoliDAO;
    }

    @Autowired
    public void setCommedilizieTipologieDAO(CommedilizieTipologieDAO commedilizietipologieDAO) {

	this.commedilizietipologieDAO = commedilizietipologieDAO;
    }

    @Autowired
    public void setCommedilizieTipologiedettService(CommedilizieTipologiedettService commedilizieTipologiedettService) {

	this.commedilizieTipologiedettService = commedilizieTipologiedettService;
    }

    @Override
    protected Class<CommedilizieTipologie> getEntityClass() {

	return CommedilizieTipologie.class;
    }

    @Override
    public List<CommedilizieTipologie> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return commedilizietipologieDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(CommedilizieTipologie entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    commedilizietipologieDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public CommedilizieTipologie findById(PkId id) {

	// §§§BEGIN§§§
	return commedilizietipologieDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(CommedilizieTipologie entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    commedilizietipologieDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(CommedilizieTipologie entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    List<CommedilizieTipologiedett> dettagli = commedilizieTipologiedettService.findByTipologia(entity);
	    for (CommedilizieTipologiedett commedilizieTipologiedett : dettagli) {
		commedilizieTipologiedettService.delete(commedilizieTipologiedett);
	    }
	    List<CommedilizieTipolRuoli> ruolis = commedilizieTipolRuoliDAO.findByTipologia(entity.getId().getCodice());
	    for (CommedilizieTipolRuoli commedilizieTipolRuoli : ruolis) {
		commedilizieTipolRuoliDAO.delete(commedilizieTipolRuoli);
	    }
	    commedilizietipologieDAO.delete(entity);
	}
	// §§§END§§§
    }

    @Override
    public List<CommedilizieTipologie> findByFilterTable(FilterTable filterTable) {

	// §§§BEGIN§§§
	return commedilizietipologieDAO.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    protected boolean isDeleteAllowed(CommedilizieTipologie entity) {

	boolean delete = true;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getCommissioniedilizieTs().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "COMMISSIONIEDILIZIE_T", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return delete;
    }

    @Override
    public List<CommedilizieTipologie> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAmministrazione, "amministrazione", Integer.class));
	filterTable.addRestriction(fr);
	return commedilizietipologieDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public boolean aggiungiRuolo(Integer codiceTipologia, Integer idRuolo) {

	CommedilizieTipolRuoliId id = new CommedilizieTipolRuoliId(codiceTipologia, idRuolo);
	CommedilizieTipolRuoli esiste = commedilizieTipolRuoliDAO.findById(id);
	if (esiste == null) {
	    esiste = new CommedilizieTipolRuoli();
	    esiste.setId(id);
	    commedilizieTipolRuoliDAO.insert(esiste);
	    return true;
	}
	return false;
    }

    @Override
    public boolean eliminaRuolo(Integer codiceTipologia, Integer idRuolo) {

	CommedilizieTipolRuoliId id = new CommedilizieTipolRuoliId(codiceTipologia, idRuolo);
	CommedilizieTipolRuoli esiste = commedilizieTipolRuoliDAO.findById(id);
	if (esiste != null) {
	    commedilizieTipolRuoliDAO.delete(esiste);
	    return true;
	}
	return false;
    }

    @Override
    public List<CommedilizieTipologie> findByRuolo(Integer idRuolo, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkRuoliId", idRuolo, "ruolis", Integer.class));
	ft.addRestriction(fr);
	return commedilizietipologieDAO.findByFilterTable(ft, firstResult, maxResult);
    }
}
