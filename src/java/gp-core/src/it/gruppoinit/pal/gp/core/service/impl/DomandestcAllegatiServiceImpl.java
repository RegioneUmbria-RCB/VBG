package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.DomandestcAllegatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Domandestc;
import it.gruppoinit.pal.gp.core.domain.DomandestcAllegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.DomandestcAllegatiService;
import it.gruppoinit.pal.gp.core.service.DomandestcService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomandestcAllegatiServiceImpl extends BaseServiceImpl<DomandestcAllegati, PkId> implements DomandestcAllegatiService {

    private DomandestcAllegatiDAO domandestcAllegatiDAO;
    private DomandestcService domandestcService;
    private OggettiService oggettiService;

    @Autowired
    public void setDomandestcAllegatiDAO(DomandestcAllegatiDAO domandestcAllegatiDAO) {

	this.domandestcAllegatiDAO = domandestcAllegatiDAO;
    }

    @Autowired
    public void setDomandestcService(DomandestcService domandestcService) {

	this.domandestcService = domandestcService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    public void insert(DomandestcAllegati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    domandestcAllegatiDAO.insert(entity);
	    gestCancellaOggetto(codiceOggettoDaCancellare);
	}
    }

    @Override
    public void update(DomandestcAllegati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    domandestcAllegatiDAO.update(entity);
	    gestCancellaOggetto(codiceOggettoDaCancellare);
	}
    }

    private void dataIntegration(DomandestcAllegati entity) {

	if (entity == null) {
	    throw new RuntimeException("L'oggetto DomandestcAllegati non può essere nullo");
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(DomandestcAllegati entity) {

	Oggetti oggetti = oggettiService.bindDomainObject(entity.getOggetti(), PkId.class, "id.codice");
	entity.setOggetti(oggetti);
	Domandestc domandestc = domandestcService.bindDomainObject(entity.getDomandestc(), PkId.class, "id.codice");
	entity.setDomandestc(domandestc);
    }

    private void gestCancellaOggetto(Integer codiceOggettoDaCancellare) {

	if (codiceOggettoDaCancellare != null) {
	    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
	    oggettiService.delete(oggettoDaCancellare);
	}
    }

    @Override
    public void delete(DomandestcAllegati entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    domandestcAllegatiDAO.delete(entity);
	    gestCancellaOggetto(codiceOggettoDaCancellare);
	}
    }

    @Override
    public List<DomandestcAllegati> findAll(Integer firstResult, Integer maxResult) {

	return domandestcAllegatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public DomandestcAllegati findById(PkId id) {

	return domandestcAllegatiDAO.findById(id);
    }

    @Override
    protected Class<DomandestcAllegati> getEntityClass() {

	return DomandestcAllegati.class;
    }

    @Override
    public List<DomandestcAllegati> findByDomandestc(Integer codicedomandaStc) {

	if (codicedomandaStc == null) {
	    throw new IllegalArgumentException("DomandestcAllegatiServiceImpl#findByDomandestc: Il parametro codicedomandaStc non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("domandestcId", codicedomandaStc, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	return domandestcAllegatiDAO.findByFilterTable(ft);
    }
}
