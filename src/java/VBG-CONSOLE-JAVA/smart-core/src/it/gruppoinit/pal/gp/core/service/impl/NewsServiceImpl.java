package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.NewsDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.domain.News;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.NewsService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class NewsServiceImpl extends BaseServiceImpl<News, PkId> implements NewsService {

    private NewsDAO newsDAO;
    private OggettiService oggettiService;
    private SoftwareService softwareService;

    @Autowired
    public void setNewsDAO(NewsDAO newsDAO) {

	this.newsDAO = newsDAO;
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
    protected Class<News> getEntityClass() {

	return News.class;
    }

    @Override
    public List<News> findAll(Integer firstResult, Integer maxResult) {

	return newsDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(News entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    newsDAO.insert(entity);
	}
    }

    @Override
    public News findById(PkId id) {

	return newsDAO.findById(id);
    }

    @Override
    public void update(News entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer[] codiceOggettoDaCancellareList = new Integer[4];
	    codiceOggettoDaCancellareList[0] = controllaCancellaOggetti(entity, "oggettiByFkNews1Oggetti", false, entity.getId());
	    codiceOggettoDaCancellareList[1] = controllaCancellaOggetti(entity, "oggettiByFkNews2Oggetti", false, entity.getId());
	    codiceOggettoDaCancellareList[2] = controllaCancellaOggetti(entity, "oggettiByFkNews3Oggetti", false, entity.getId());
	    codiceOggettoDaCancellareList[3] = controllaCancellaOggetti(entity, "oggettiByFkNews4Oggetti", false, entity.getId());
	    newsDAO.update(entity);
	    for (int i = 0; i < codiceOggettoDaCancellareList.length; i++) {
		if (codiceOggettoDaCancellareList[i] != null) {
		    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellareList[i]));
		    oggettiService.delete(oggettoDaCancellare);
		}
	    }
	}
    }

    @Override
    public void delete(News entity) {

	if (isDeleteAllowed(entity)) {
	    Integer[] codiceOggettoDaCancellareList = new Integer[4];
	    codiceOggettoDaCancellareList[0] = controllaCancellaOggetti(entity, "oggettiByFkNews1Oggetti", true, entity.getId());
	    codiceOggettoDaCancellareList[1] = controllaCancellaOggetti(entity, "oggettiByFkNews2Oggetti", true, entity.getId());
	    codiceOggettoDaCancellareList[2] = controllaCancellaOggetti(entity, "oggettiByFkNews3Oggetti", true, entity.getId());
	    codiceOggettoDaCancellareList[3] = controllaCancellaOggetti(entity, "oggettiByFkNews4Oggetti", true, entity.getId());
	    newsDAO.delete(entity);
	    for (int i = 0; i < codiceOggettoDaCancellareList.length; i++) {
		if (codiceOggettoDaCancellareList[i] != null) {
		    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellareList[i]));
		    oggettiService.delete(oggettoDaCancellare);
		}
	    }
	}
    }

    protected boolean isDeleteAllowed(Faq entity) {

	boolean delete = true;
	return delete;
    }

    @Override
    public List<News> findByFilter(Set<Software> softwareList) {

	return newsDAO.findByFilter(softwareList);
    }

    //    private Integer controllaCancellaOggetti(Oggetti oggetto, String foreignKey, News entity, boolean isDelete) {
    //
    //	Integer codiceOggetto = null;
    //	Integer codiceOggettoOld = null;
    //	if (!(null == oggetto)) {
    //	    if (!(null == oggetto.getId())) {
    //		if (!(null == oggetto.getId().getCodice())) {
    //		    codiceOggetto = oggetto.getId().getCodice();
    //		}
    //	    }
    //	}
    //	if (isDelete) {
    //	    // sono in cancellazione
    //	    if (!(null == codiceOggetto)) {
    //		if (oggettiService.controllaCancellaOggetto("NEWS", foreignKey, codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    News entityCopy = this.findById(entity.getId());
    //	    if (oggetto != null) {
    //		Oggetti oggettiCopy = oggettiService.findById(oggetto.getId());
    //		if (null != entityCopy) {
    //		    // se non è nullo allora sono in modifica
    //		    // in inserimento non devo fare il controllo
    //		    // recupero il vecchio id
    //		    if (!(null == oggettiCopy)) {
    //			if (!(null == oggettiCopy.getId())) {
    //			    if (!(null == oggettiCopy.getId().getCodice())) {
    //				codiceOggettoOld = oggettiCopy.getId().getCodice();
    //			    }
    //			}
    //		    }
    //		    if (null != codiceOggettoOld) {
    //			if (!codiceOggettoOld.equals(codiceOggetto)) {
    //			    // cancello solo se sono diversi altrimenti no
    //			    if (oggettiService.controllaCancellaOggetto("NEWS", foreignKey, codiceOggettoOld)) {
    //				return codiceOggettoOld;
    //			    }
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    private void dataIntegration(News entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("La News passata è nulla");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(News entity) {

	Oggetti oggetto1 = oggettiService.bindDomainObject(entity.getOggettiByFkNews1Oggetti(), PkId.class, "id.codice");
	entity.setOggettiByFkNews1Oggetti(oggetto1);
	Oggetti oggetto2 = oggettiService.bindDomainObject(entity.getOggettiByFkNews2Oggetti(), PkId.class, "id.codice");
	entity.setOggettiByFkNews2Oggetti(oggetto2);
	Oggetti oggetto3 = oggettiService.bindDomainObject(entity.getOggettiByFkNews3Oggetti(), PkId.class, "id.codice");
	entity.setOggettiByFkNews3Oggetti(oggetto3);
	Oggetti oggetto4 = oggettiService.bindDomainObject(entity.getOggettiByFkNews4Oggetti(), PkId.class, "id.codice");
	entity.setOggettiByFkNews4Oggetti(oggetto4);
	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
    }

    @Override
    public List<News> findLatest(Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction software = new FilterRestriction();
	if (!ORMHelper.getSoftware().equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
	    software.addFilterField(FilterUtils.in("software.codice", new String[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT },
		    String.class));
	    ft.addRestriction(software);
	} else {
	    software.addFilterField(FilterUtils.equals("software.codice", WebConstants.SOFTWARE_TT, String.class));
	    ft.addRestriction(software);
	}
	ft.addOrder(FilterUtils.orderDesc("data"));
	return newsDAO.findByFilterTable(ft, 0, maxResult);
    }

    @Override
    public List<News> findLatestPrimoPiano(Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (!ORMHelper.getSoftware().equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
	    fr.addFilterField(FilterUtils.in("software.codice", new String[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }, String.class));
	} else {
	    fr.addFilterField(FilterUtils.equals("software.codice", WebConstants.SOFTWARE_TT, String.class));
	}
	fr.addFilterField(FilterUtils.equals("flagInPrimoPiano", true, Boolean.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("data"));
	return newsDAO.findByFilterTable(ft, 0, maxResult);
    }
}
