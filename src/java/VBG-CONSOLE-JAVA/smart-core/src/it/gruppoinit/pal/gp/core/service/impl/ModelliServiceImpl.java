package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ModelliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.domain.Modelli;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipimodelli;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ModelliService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipimodelliService;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class ModelliServiceImpl extends BaseServiceImpl<Modelli, PkId> implements ModelliService {

    private ModelliDAO modelliDAO;
    private OggettiService oggettiService;
    private SoftwareService softwareService;
    private TipimodelliService tipimodelliService;

    @Autowired
    public void setModelliDAO(ModelliDAO modelliDAO) {

	this.modelliDAO = modelliDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setTipimodelliService(TipimodelliService tipimodelliService) {

	this.tipimodelliService = tipimodelliService;
    }

    @Override
    protected Class<Modelli> getEntityClass() {

	return Modelli.class;
    }

    @Override
    public List<Modelli> findAll(Integer firstResult, Integer maxResult) {

	return modelliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Modelli entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    modelliDAO.insert(entity);
	}
    }

    @Override
    public Modelli findById(PkId id) {

	return modelliDAO.findById(id);
    }

    @Override
    public void update(Modelli entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    modelliDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void delete(Modelli entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    modelliDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    protected boolean isDeleteAllowed(Faq entity) {

	boolean delete = true;
	return delete;
    }

    @Override
    public List<Modelli> findByFilter(Set<Software> softwareList) {

	return modelliDAO.findByFilter(softwareList);
    }

    //    private Integer controllaCancellaOggetti(Modelli entity, boolean isDelete) {
    //
    //	Integer codiceOggetto = null;
    //	Integer codiceOggettoOld = null;
    //	if (!(null == entity.getOggetti())) {
    //	    if (!(null == entity.getOggetti().getId())) {
    //		if (!(null == entity.getOggetti().getId().getCodice())) {
    //		    codiceOggetto = entity.getOggetti().getId().getCodice();
    //		}
    //	    }
    //	}
    //	if (isDelete) {
    //	    // sono in cancellazione
    //	    if (!(null == codiceOggetto)) {
    //		if (oggettiService.controllaCancellaOggetto("MODELLI", "CODICEOGGETTO", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    Modelli entityCopy = this.findById(entity.getId());
    //	    if (null != entityCopy) {
    //		// se non è nullo allora sono in modifica
    //		// in inserimento non devo fare il controllo
    //		// recupero il vecchio id
    //		if (!(null == entityCopy.getOggetti())) {
    //		    if (!(null == entityCopy.getOggetti().getId())) {
    //			if (!(null == entityCopy.getOggetti().getId().getCodice())) {
    //			    codiceOggettoOld = entityCopy.getOggetti().getId().getCodice();
    //			}
    //		    }
    //		}
    //		if (null != codiceOggettoOld) {
    //		    if (!codiceOggettoOld.equals(codiceOggetto)) {
    //			// cancello solo se sono diversi altrimenti no
    //			if (oggettiService.controllaCancellaOggetto("MODELLI", "CODICEOGGETTO", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    private void dataIntegration(Modelli entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il Modello passato è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Modelli entity) {

	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggetti(), PkId.class, "id.codice");
	entity.setOggetti(oggetto);
	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
	Tipimodelli tipimodelli = tipimodelliService.bindDomainObject(entity.getTipimodelli(), PkId.class, "id.codice");
	entity.setTipimodelli(tipimodelli);
    }

    @Override
    public List<Modelli> findBySoftwareAndTipoModello(String codicesoftware, Integer codiceTipoModello) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (codicesoftware.equals(WebConstants.SOFTWARE_TT)) {
	    fr.addFilterField(FilterUtils.equals("codice", codicesoftware, "software", String.class));
	} else {
	    String[] codici = new String[] { codicesoftware, WebConstants.SOFTWARE_TT };
	    fr.addFilterField(FilterUtils.in("codice", codici, "software", String[].class));
	}
	if (codiceTipoModello != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", codiceTipoModello, "tipimodelli", Integer.class));
	} else {
	    fr.addFilterField(FilterUtils.isNull("id.codice", "tipimodelli"));
	}
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("titolo"));
	filterTable.addOrder(FilterUtils.orderAsc("ordine"));
	return modelliDAO.findByFilterTable(filterTable);
	//return modelliDAO.findBySoftwareAndTipoModello(codicesoftware, codiceTipoModello);
    }
}
