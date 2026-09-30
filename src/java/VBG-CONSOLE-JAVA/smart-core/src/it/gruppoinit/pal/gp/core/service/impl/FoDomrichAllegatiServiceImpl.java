package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoDomrichAllegatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FoDomRichieste;
import it.gruppoinit.pal.gp.core.domain.FoDomrichAllegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FoDomRichiesteService;
import it.gruppoinit.pal.gp.core.service.FoDomrichAllegatiService;
import it.gruppoinit.pal.gp.core.service.OggettiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoDomrichAllegatiServiceImpl extends BaseServiceImpl<FoDomrichAllegati, PkId> implements FoDomrichAllegatiService {

    private FoDomrichAllegatiDAO foDomrichAllegatiDAO;
    private OggettiService oggettiService;
    private FoDomRichiesteService foDomRichiesteService;

    @Autowired
    public void setFoDomRichiesteService(FoDomRichiesteService foDomRichiesteService) {

	this.foDomRichiesteService = foDomRichiesteService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setFoDomrichAllegatiDAO(FoDomrichAllegatiDAO foDomrichAllegatiDAO) {

	this.foDomrichAllegatiDAO = foDomrichAllegatiDAO;
    }

    @Override
    public void insert(FoDomrichAllegati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    foDomrichAllegatiDAO.insert(entity);
	}
    }

    private void dataIntegration(FoDomrichAllegati entity) {

	if (entity.getFirmato() == null) {
	    entity.setFirmato(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(FoDomrichAllegati entity) {

	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggetti(), PkId.class, "id.codice");
	entity.setOggetti(oggetto);
	FoDomRichieste rich = foDomRichiesteService.bindDomainObject(entity.getFoDomRichieste(), PkId.class, "id.codice");
	entity.setFoDomRichieste(rich);
    }

    @Override
    public void update(FoDomrichAllegati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    foDomrichAllegatiDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void delete(FoDomrichAllegati entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    foDomrichAllegatiDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public List<FoDomrichAllegati> findAll(Integer firstResult, Integer maxResult) {

	return foDomrichAllegatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public FoDomrichAllegati findById(PkId id) {

	return foDomrichAllegatiDAO.findById(id);
    }

    @Override
    protected Class<FoDomrichAllegati> getEntityClass() {

	return FoDomrichAllegati.class;
    }

    @Override
    public List<FoDomrichAllegati> findByRichiesta(String idcomune, Integer foDomRichiesteId) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	fr.addFilterField(FilterUtils.equals("foDomRichiesteId", foDomRichiesteId, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("modulo"));
	ft.addOrder(FilterUtils.orderAsc("attore"));
	return foDomrichAllegatiDAO.findByFilterTable(ft, null, null);
    }
}
