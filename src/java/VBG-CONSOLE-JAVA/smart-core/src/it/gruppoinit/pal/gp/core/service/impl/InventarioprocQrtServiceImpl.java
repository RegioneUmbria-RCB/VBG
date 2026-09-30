package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocQrtDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.InventarioprocQrt;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.InventarioprocQrtService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.OggettiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventarioprocQrtServiceImpl extends BaseServiceImpl<InventarioprocQrt, PkId> implements InventarioprocQrtService {

    private InventarioprocQrtDAO inventarioprocQrtDAO;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private OggettiService oggettiService;

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setInventarioprocQrtDAO(InventarioprocQrtDAO inventarioprocQrtDAO) {

	this.inventarioprocQrtDAO = inventarioprocQrtDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    public void insert(InventarioprocQrt entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    inventarioprocQrtDAO.insert(entity);
	}
    }

    private void dataIntegration(InventarioprocQrt entity) {

	if (entity == null) {
	    throw new RuntimeException("entity nulla InventarioprocQrt#dataIntegration");
	}
	if (entity.getFlagPubblica() == null) {
	    entity.setFlagPubblica(Boolean.FALSE);
	}
	if (entity.getOrdine() == null) {
	    entity.setOrdine(Integer.valueOf(0));
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(InventarioprocQrt entity) {

	Oggetti o = oggettiService.bindDomainObject(entity.getOggetti(), PkId.class, "id.codice");
	entity.setOggetti(o);
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.bindDomainObject(entity.getInventarioprocedimento(),
		PkId.class, "id.codice");
	entity.setInventarioprocedimento(inventarioprocedimenti);
    }

    @Override
    public void update(InventarioprocQrt entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    inventarioprocQrtDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void delete(InventarioprocQrt entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    inventarioprocQrtDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public List<InventarioprocQrt> findAll(Integer firstResult, Integer maxResult) {

	return inventarioprocQrtDAO.findAll(firstResult, maxResult);
    }

    @Override
    public InventarioprocQrt findById(PkId id) {

	return inventarioprocQrtDAO.findById(id);
    }

    @Override
    public List<InventarioprocQrt> findByCodiceInventario(String idcomuneCodiceInventario, Integer codiceinventario, STATO_PUBBLICAZIONE stato) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomuneCodiceInventario, "inventarioprocedimento", String.class));
	fr.addFilterField(FilterUtils.equals("inventarioprocedimentoId", codiceinventario, Integer.class));
	if (stato != null) {
	    switch (stato) {
	    case PUBBLICATI:
		fr.addFilterField(FilterUtils.equals("flagPubblica", Boolean.TRUE, Boolean.class));
		break;
	    case NON_PUBBLICATI:
		fr.addFilterField(FilterUtils.equals("flagPubblica", Boolean.FALSE, Boolean.class));
		break;
	    default:
		break;
	    }
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("titolo"));
	return inventarioprocQrtDAO.findByFilterTable(ft);
    }

    @Override
    public List<InventarioprocQrt> findPubblicatiByCodiceInventario(String idcomuneCodiceInventario, Integer codiceinventario) {

	return findByCodiceInventario(idcomuneCodiceInventario, codiceinventario, STATO_PUBBLICAZIONE.PUBBLICATI);
    }

    @Override
    protected Class<InventarioprocQrt> getEntityClass() {

	return InventarioprocQrt.class;
    }
}
