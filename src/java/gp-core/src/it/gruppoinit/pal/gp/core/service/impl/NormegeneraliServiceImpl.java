package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.NormegeneraliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.domain.Normegenerali;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.NormegeneraliBean;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.NormegeneraliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class NormegeneraliServiceImpl extends BaseServiceImpl<Normegenerali, PkId> implements NormegeneraliService {

    private NormegeneraliDAO normegeneraliDAO;
    private OggettiService oggettiService;
    private SoftwareService softwareService;

    @Autowired
    public void setNormegeneraliDAO(NormegeneraliDAO normegeneraliDAO) {

	this.normegeneraliDAO = normegeneraliDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Override
    protected Class<Normegenerali> getEntityClass() {

	return Normegenerali.class;
    }

    @Override
    public List<Normegenerali> findAll(Integer firstResult, Integer maxResult) {

	return normegeneraliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Normegenerali entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    normegeneraliDAO.insert(entity);
	}
    }

    @Override
    public Normegenerali findById(PkId id) {

	return normegeneraliDAO.findById(id);
    }

    @Override
    public void update(Normegenerali entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    normegeneraliDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void delete(Normegenerali entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    normegeneraliDAO.delete(entity);
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
    public List<Normegenerali> findByFilter(Set<Software> softwareList) {

	return normegeneraliDAO.findByFilter(softwareList);
    }

    @Override
    public List<NormegeneraliBean> findNormegenerali(Integer firstResult, Integer maxResults) {

	//FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("titolo"));
	List<Normegenerali> normes = normegeneraliDAO.findByFilterTable(ft, firstResult, maxResults);
	List<NormegeneraliBean> list = new ArrayList<NormegeneraliBean>();
	for (Normegenerali n : normes) {
	    NormegeneraliBean nor = new NormegeneraliBean();
	    nor.setTitolo(n.getTitolo());
	    nor.setDescrizione(n.getDescrizione());
	    if (n.getOggetti() != null) {
		Integer codiceOggetto = n.getOggetti().getId().getCodice();
		if (codiceOggetto != null) {
		    String uid = oggettiService.insertOrGetUID(codiceOggetto);
		    nor.setCodiceOggetto(uid);
		}
	    }
	    list.add(nor);
	}
	return list;
    }

    @Override
    public List<NormegeneraliBean> findNormegeneraliBySoftware(Integer firstResult, Integer maxResults, boolean isRicercaPerTT) {

	FilterTable ft = null;
	FilterRestriction fr = new FilterRestriction();
	if (!isRicercaPerTT) {
	    ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	} else {
	    ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    fr.addFilterField(FilterUtils.in("codice", new String[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }, "software", String.class));
	    ft.addRestriction(fr);
	}
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("titolo"));
	List<Normegenerali> normes = normegeneraliDAO.findByFilterTable(ft, firstResult, maxResults);
	List<NormegeneraliBean> list = new ArrayList<NormegeneraliBean>();
	for (Normegenerali n : normes) {
	    NormegeneraliBean nor = new NormegeneraliBean();
	    nor.setTitolo(n.getTitolo());
	    nor.setDescrizione(n.getDescrizione());
	    if (StringUtils.isNotBlank(n.getIndirizzoweb())) {
		nor.setUrl(n.getIndirizzoweb());
	    }
	    if (n.getOggetti() != null) {
		Integer codiceOggetto = n.getOggetti().getId().getCodice();
		if (codiceOggetto != null) {
		    String uid = oggettiService.insertOrGetUID(codiceOggetto);
		    nor.setCodiceOggetto(uid);
		}
	    }
	    list.add(nor);
	}
	return list;
    }

    //    private Integer controllaCancellaOggetti(Normegenerali entity, boolean isDelete) {
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
    //		if (oggettiService.controllaCancellaOggetto("NORMEGENERALI", "CODICEOGGETTO", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    Normegenerali entityCopy = this.findById(entity.getId());
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
    //			if (oggettiService.controllaCancellaOggetto("NORMEGENERALI", "CODICEOGGETTO", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    private void dataIntegration(Normegenerali entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("La Norma generale passata è nulla");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Normegenerali entity) {

	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggetti(), PkId.class, "id.codice");
	entity.setOggetti(oggetto);
	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
    }
}
