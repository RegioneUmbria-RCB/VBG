package it.gruppoinit.pal.gp.core.features.alberoproc.esclusioni;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.opensaml.artifact.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocComuniEsclusi;
import it.gruppoinit.pal.gp.core.domain.AlberoprocComuniEsclusiId;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class AlberoprocComuniEsclusiServiceImpl extends BaseServiceImpl<AlberoprocComuniEsclusi, AlberoprocComuniEsclusiId>
	implements AlberoprocComuniEsclusiService {

    private AlberoprocComuniEsclusiDAO alberoprocComuniEsclusiDAO;

    @Autowired
    public void setAlberoprocComuniEsclusiDAO(AlberoprocComuniEsclusiDAO alberoprocComuniEsclusiDAO) {

	this.alberoprocComuniEsclusiDAO = alberoprocComuniEsclusiDAO;
    }

    @Override
    public void insert(AlberoprocComuniEsclusi entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    this.alberoprocComuniEsclusiDAO.insert(entity);
	}
    }

    private void insert(Integer codiceInterventoProc, String codiceComune) {

	AlberoprocComuniEsclusi entity = new AlberoprocComuniEsclusi();
	entity.setId(new AlberoprocComuniEsclusiId(codiceInterventoProc, codiceComune));
	entity.setComune(new Comuni(codiceComune));
	entity.setAlberoproc(new Alberoproc(codiceInterventoProc));
	this.insert(entity);
    }

    @Override
    public void update(AlberoprocComuniEsclusi entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    this.alberoprocComuniEsclusiDAO.update(entity);
	}
    }

    @Override
    public void delete(AlberoprocComuniEsclusi entity) {

	if (isDeleteAllowed(entity)) {
	    this.alberoprocComuniEsclusiDAO.delete(entity);
	}
    }

    @Override
    public List<AlberoprocComuniEsclusi> findAll(Integer firstResult, Integer maxResult) {

	return this.alberoprocComuniEsclusiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public AlberoprocComuniEsclusi findById(AlberoprocComuniEsclusiId id) {

	return this.alberoprocComuniEsclusiDAO.findById(id);
    }

    @Override
    public List<ComuniEsclusi> findByAlberoProc(String scCodice) {

	List<AlberoprocComuniEsclusi> alberoprocComuniEsclusi = this.findByScCodice(scCodice);
	if (alberoprocComuniEsclusi.isEmpty()) {
	    return new ArrayList<ComuniEsclusi>();
	}
	List<ComuniEsclusi> comuniEsclusi = new ArrayList<ComuniEsclusi>();
	for (AlberoprocComuniEsclusi alberoprocComuneEscluso : alberoprocComuniEsclusi) {
	    comuniEsclusi.add(ComuniEsclusi.fromAlberoprocComuniEsclusi(alberoprocComuneEscluso));
	}
	Collections.sort(comuniEsclusi, new Comparator<ComuniEsclusi>() {

	    @Override
	    public int compare(ComuniEsclusi o1, ComuniEsclusi o2) {

		if (o1.getComune() == null && o2.getComune() == null) {
		    return 0;
		}
		if (o1.getComune() == null) {
		    return 1;
		}
		if (o2.getComune() == null) {
		    return -1;
		}
		return o1.getComune().compareToIgnoreCase(o2.getComune());
	    }
	});
	return comuniEsclusi;
    }

    @Override
    protected Class<AlberoprocComuniEsclusi> getEntityClass() {

	return AlberoprocComuniEsclusi.class;
    }

    private void dataIntegration(AlberoprocComuniEsclusi entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("l'oggetto AlberoprocComuniEsclusi passato è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected boolean isInsertAllowed(AlberoprocComuniEsclusi entity) {

	boolean insert = true;
	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	if (this.findById(entity.getId()) != null) {
	    ivs.add(new InvalidValue("service_error.stesso_comune_escluso", null, null, entity.getId(), null));
	    this.throwValidationMessages(ivs);
	}
	return insert;
    }

    @Override
    public void delete(Integer codiceInterventoProc, String codiceComune) {

	AlberoprocComuniEsclusi entity = new AlberoprocComuniEsclusi();
	entity.setId(new AlberoprocComuniEsclusiId(codiceInterventoProc, codiceComune));
	this.delete(entity);
    }

    @Override
    public void aggiungiEnti(Integer codiceInterventoProc, String[] comuni) {

	if (codiceInterventoProc == null) {
	    throw new InvalidArgumentException("Non è stato passato l'id dell'intervento");
	}
	if (comuni == null || comuni.length == 0) {
	    throw new InvalidArgumentException("Non sono stati passati gli enti da escludere");
	}
	for (String codiceComune : comuni) {
	    this.delete(codiceInterventoProc, codiceComune);
	    this.insert(codiceInterventoProc, codiceComune);
	}
    }

    @Override
    public boolean entiEsclusiPresenti(String scCodice) {

	return !this.findByScCodice(scCodice).isEmpty();
    }

    private List<AlberoprocComuniEsclusi> findByScCodice(String scCodice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction softwareRestriction = new FilterRestriction();
	softwareRestriction.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), "alberoproc", String.class));
	softwareRestriction.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "alberoproc.software", String.class));
	ft.addRestriction(softwareRestriction);
	FilterRestriction padri = new FilterRestriction();
	padri.setAndOrRestriction(AndOrRestriction.OR);
	for (int i = 0; i < scCodice.length(); i += 2) {
	    padri.addFilterField(FilterUtils.equals("scCodice", scCodice.substring(0, (scCodice.length() - i)), "alberoproc", String.class));
	}
	ft.addRestriction(padri);
	ft.addOrder(FilterUtils.orderAsc("comune", "comune"));
	return this.alberoprocComuniEsclusiDAO.findByFilterTable(ft);
    }
}
