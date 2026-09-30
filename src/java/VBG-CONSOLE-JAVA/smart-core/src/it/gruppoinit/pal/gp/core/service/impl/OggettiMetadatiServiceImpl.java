package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.OggettiMetadatiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OggettiMetadatiServiceImpl extends BaseServiceImpl<OggettiMetadati, OggettiMetadatiId> implements OggettiMetadatiService {

    private static Logger log = LoggerFactory.getLogger(OggettiMetadatiServiceImpl.class);
    private OggettiMetadatiDAO oggettiMetadatiDAO;
    private OggettiService oggettiService;

    @Autowired
    public void setOggettiMetadatiDAO(OggettiMetadatiDAO oggettiMetadatiDAO) {

	this.oggettiMetadatiDAO = oggettiMetadatiDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    public void insert(OggettiMetadati entity) {

	if (validateEntity(entity)) {
	    oggettiMetadatiDAO.insert(entity);
	}
    }

    @Override
    public void update(OggettiMetadati entity) {

	if (validateEntity(entity)) {
	    oggettiMetadatiDAO.update(entity);
	}
    }

    @Override
    public void delete(OggettiMetadati entity) {

	if (isDeleteAllowed(entity)) {
	    oggettiMetadatiDAO.delete(entity);
	}
    }

    @Override
    public List<OggettiMetadati> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public OggettiMetadati findById(OggettiMetadatiId id) {

	return oggettiMetadatiDAO.findById(id);
    }

    @Override
    protected Class<OggettiMetadati> getEntityClass() {

	return OggettiMetadati.class;
    }

    @Override
    public void deleteByOggetto(Integer codiceOggetto, String idcomune) {

	oggettiMetadatiDAO.deleteByOggetto(codiceOggetto, idcomune);
    }

    @Override
    public void insertMetadatiPerOggetto(Integer codiceOggetto, List<MetadatiBean> metadati, String idcomune) {

	if (log.isDebugEnabled()) {
	    log.debug("insertMetadatiPerOggetto# entro nel metodo");
	}
	if (codiceOggetto == null) {
	    log.warn("insertMetadatiPerOggetto# parametro codice oggetto = null non faccio niente");
	    return;
	}
	for (ChiaveValoreBean<String, String> md : metadati) {
	    if (md != null) {
		if (StringUtils.isNotBlank(md.getChiave()) && StringUtils.isNotBlank(md.getValore())) {
		    if (log.isDebugEnabled()) {
			log.debug("insertMetadatiPerOggetto# codiceoggetto {}: inserisco aggiorno il metadato con chiave: {}, valore: {}",
				new Object[] { codiceOggetto, md.getChiave(), md.getValore() });
		    }
		    OggettiMetadatiId id = new OggettiMetadatiId(idcomune, codiceOggetto, md.getChiave());
		    OggettiMetadati o = this.findById(id);
		    if (o != null) {
			if (!StringUtils.defaultString(o.getValore()).equals(StringUtils.defaultString(md.getValore()))) {
			    if (log.isDebugEnabled()) {
				log.debug("insertMetadatiPerOggetto# aggiorno il valore da [{}] a [{}]", o.getValore(), md.getValore());
			    }
			    o.setValore(md.getValore());
			    this.update(o);
			}
		    } else {
			if (log.isDebugEnabled()) {
			    log.debug("insertMetadatiPerOggetto# inserisco il nuovo metadato");
			}
			o = new OggettiMetadati();
			o.setId(new OggettiMetadatiId(idcomune, codiceOggetto, md.getChiave()));
			o.setValore(md.getValore());
			this.insert(o);
		    }
		}
	    }
	}
    }

    @Override
    public List<OggettiMetadati> findByOggetto(Integer codiceOggetto) {

	if (codiceOggetto == null) {
	    return new ArrayList<OggettiMetadati>();
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceoggetto", codiceOggetto, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.chiave"));
	return oggettiMetadatiDAO.findByFilterTable(ft);
    }

    @Override
    public void insertInNewTransaction(Integer codiceOggetto, String chiave, String valore, String idcomune) {

	oggettiMetadatiDAO.insertInNewTransaction(codiceOggetto, chiave, valore, idcomune);
    }

    @Override
    public void updateInNewTransaction(Integer codiceOggetto, String chiave, String valore, String idcomune) {

	oggettiMetadatiDAO.updateInNewTransaction(codiceOggetto, chiave, valore, idcomune);
    }

    @Override
    public Integer findByChiaveEValore(String chiave, String valore) {

	if (chiave == null) {
	    throw new RuntimeException("codice chiave nulla");
	}
	if (valore == null) {
	    throw new RuntimeException(chiave + " nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("valore", valore, String.class));
	fr.addFilterField(FilterUtils.equals("id.chiave", chiave, String.class));
	ft.addRestriction(fr);
	List<OggettiMetadati> omds = oggettiMetadatiDAO.findByFilterTable(ft, 0, 3);
	if (omds.size() > 1) {
	    throw new RuntimeException("Sono stati trovati più oggetti per la chiave " + chiave + " e valore " + valore);
	} else {
	    if (omds.size() == 1) {
		return omds.get(0).getId().getCodiceoggetto();
	    }
	}
	return null;
    }

    @Override
    public List<OggettiMetadati> findByOggetto(Integer codiceOggetto, String chiave, String idcomune) {

	if (codiceOggetto == null) {
	    return new ArrayList<OggettiMetadati>();
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	fr.addFilterField(FilterUtils.equals("id.codiceoggetto", codiceOggetto, Integer.class));
	fr.addFilterField(FilterUtils.equals("id.chiave", chiave, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.chiave"));
	List<OggettiMetadati> metadati = oggettiMetadatiDAO.findByFilterTable(ft);
	if (metadati.isEmpty()) {
	    return null;
	} else {
	    return metadati;
	}
    }

    @Override
    public String calcolaMd5(Integer codiceOggetto) {

	String md5Val = "";
	Oggetti o = oggettiService.findById(new PkId(codiceOggetto));
	if (o != null) {
	    try {
		md5Val = Utilities.calcolaMd5SUM(o.getOggetto());
	    } catch (IOException e) {
		log.error("errore nel calcolo dell'MD5SUM PER IL CODICEOGGETTO {}:{}", codiceOggetto, e);
	    }
	}
	return md5Val;
    }

    @Override
    public Oggetti findByGUID(List<String> idcomunes, String guid, boolean isLazyFetch) {

	if (StringUtils.isBlank(guid)) {
	    return null;
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	String[] idcoms = new String[idcomunes.size()];
	idcoms = (String[]) idcomunes.toArray(idcoms);
	fr.addFilterField(FilterUtils.in("id.idcomune", idcoms, String[].class));
	fr.addFilterField(FilterUtils.equals("valore", guid, String.class));
	fr.addFilterField(FilterUtils.equals("id.chiave", WebConstants.OGGETTI_FILE_UID, String.class));
	ft.addRestriction(fr);
	List<OggettiMetadati> omds = oggettiMetadatiDAO.findByFilterTable(ft, 0, 3);
	if (omds.size() >= 1) {
	    if (isLazyFetch) {
		Oggetti o = oggettiService.findByIdLazy(new PkId(omds.get(0).getId().getIdcomune(), omds.get(0).getId().getCodiceoggetto()));
		return o;
	    } else {
		Oggetti o = oggettiService.findById(new PkId(omds.get(0).getId().getIdcomune(), omds.get(0).getId().getCodiceoggetto()));
		return o;
	    }
	}
	return null;
    }
}
