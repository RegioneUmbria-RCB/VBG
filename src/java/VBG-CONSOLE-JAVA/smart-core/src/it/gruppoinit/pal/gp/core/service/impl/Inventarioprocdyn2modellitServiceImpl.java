package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.Inventarioprocdyn2modellitDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipiLocalizzazioni;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.Inventarioprocdyn2modellitService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.TipiLocalizzazioniService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Inventarioprocdyn2modellitServiceImpl extends BaseServiceImpl<Inventarioprocdyn2modellit, Inventarioprocdyn2modellitId> implements
	Inventarioprocdyn2modellitService {

    private Inventarioprocdyn2modellitDAO inventarioprocdyn2modellitDAO;
    private Dyn2ModellitService dyn2ModellitService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private TipiLocalizzazioniService tipiLocalizzazioniService;

    @Autowired
    public void setInventarioprocdyn2modellitDAO(Inventarioprocdyn2modellitDAO inventarioprocdyn2modellitDAO) {

	this.inventarioprocdyn2modellitDAO = inventarioprocdyn2modellitDAO;
    }

    @Autowired
    public void setDyn2ModellitService(Dyn2ModellitService dyn2ModellitService) {

	this.dyn2ModellitService = dyn2ModellitService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setTipiLocalizzazioniService(TipiLocalizzazioniService tipiLocalizzazioniService) {

	this.tipiLocalizzazioniService = tipiLocalizzazioniService;
    }

    @Override
    protected Class<Inventarioprocdyn2modellit> getEntityClass() {

	return Inventarioprocdyn2modellit.class;
    }

    @Override
    public void delete(Inventarioprocdyn2modellit entity) {

	inventarioprocdyn2modellitDAO.delete(entity);
    }

    @Override
    public List<Inventarioprocdyn2modellit> findAll(Integer firstResult, Integer maxResult) {

	return inventarioprocdyn2modellitDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Inventarioprocdyn2modellit findById(Inventarioprocdyn2modellitId id) {

	return inventarioprocdyn2modellitDAO.findById(id);
    }

    @Override
    public void insert(Inventarioprocdyn2modellit entity) {

	dataintegration(entity);
	if (isInsertUpdateAllowed(entity) && validateEntity(entity)) {
	    inventarioprocdyn2modellitDAO.insert(entity);
	}
    }

    @Override
    public void update(Inventarioprocdyn2modellit entity) {

	dataintegration(entity);
	if (isInsertUpdateAllowed(entity) && validateEntity(entity)) {
	    inventarioprocdyn2modellitDAO.update(entity);
	}
    }

    @Override
    public List<Inventarioprocdyn2modellit> findByInventarioprocedimento(String idcomune, Integer codiceendo) {
	return findByInventarioprocedimento(idcomune, codiceendo, false);
    }

    public List<Inventarioprocdyn2modellit> findByInventarioprocedimento(String idcomune, Integer codiceendo, boolean soloPubblicati){
	
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, "inventarioprocedimenti", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceendo, "inventarioprocedimenti", Integer.class));
	if(soloPubblicati){
	    fr.addFilterField(FilterUtils.equals("flagPubblica", true, Boolean.class));
	}
	// Ordinate per il campo ordine e descrizione del modello
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "dyn2Modellit"));
	return inventarioprocdyn2modellitDAO.findByFilterTable(ft);
    }

    private void dataintegration(Inventarioprocdyn2modellit entity) {

	if (entity.getFlagFacoltativa() == null) {
	    entity.setFlagFacoltativa(new Boolean(false));
	}
	if (entity.getFlagPubblica() == null) {
	    entity.setFlagPubblica(new Boolean(false));
	}
	fixMergeEntityProperties(entity);
    }

    protected boolean isInsertUpdateAllowed(Inventarioprocdyn2modellit entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Integer tipoFirma = entity.getFlagTipofirma() == null ? Integer.valueOf(0) : entity.getFlagTipofirma();
	if (tipoFirma.equals(Integer.valueOf(2))) {
	    if (EntityUtils.getNestedProperty(entity.getDyn2Modellit(), "id.codice") != null) {
		Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(entity.getDyn2Modellit().getId());
		if (dyn2Modellit.getDyn2Modellids() != null && !dyn2Modellit.getDyn2Modellids().isEmpty()) {
		    Set<Dyn2Modellid> dyn2Modellids = dyn2Modellit.getDyn2Modellids();
		    for (Dyn2Modellid dyn2Modellid : dyn2Modellids) {
			if (dyn2Modellid.getFlgMultiplo() == null || dyn2Modellid.getFlgMultiplo().equals(Boolean.FALSE)) {
			    _ivs.add(new InvalidValue(getMessageFromBundle(WebConstants.ALERT_NO_FLAG_MULTIPLO, new Object[] { entity
				    .getDyn2Modellit().getDescrizione() }), null, null, "INVENTARIOPROC_DYN2MODELLIT", null));
			    break;
			}
		    }
		}
	    }
	}
	// Se una scheda ha il flag di molteplicità attivo (dyn2_modellit.modellomultiplo==1)
	if (!tipoFirma.equals(Integer.valueOf(0))) {
	    if (EntityUtils.getNestedProperty(entity.getDyn2Modellit(), "id.codice") != null) {
		Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(entity.getDyn2Modellit().getId());
		if (BooleanUtils.isTrue(dyn2Modellit.getModellomultiplo())) {
		    _ivs.add(new InvalidValue(getMessageFromBundle(WebConstants.ALERT_NON_POSSO_FIRMARE_MODELLI_MULTIPLI, new Object[] { entity
			    .getDyn2Modellit().getDescrizione() }), null, null, "INVENTARIOPROC_DYN2MODELLIT", null));
		}
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }

    @Override
    public List<Inventarioprocdyn2modellit> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	return inventarioprocdyn2modellitDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    protected void fixMergeEntityProperties(Inventarioprocdyn2modellit entity) {

	TipiLocalizzazioni tipiLocalizzazioni = tipiLocalizzazioniService.bindDomainObject(entity.getTipiLocalizzazioni(), PkId.class, "id.codice");
	entity.setTipiLocalizzazioni(tipiLocalizzazioni);
	Dyn2Modellit dyn2Modellit = dyn2ModellitService.bindDomainObject(entity.getDyn2Modellit(), PkId.class, "id.codice");
	entity.setDyn2Modellit(dyn2Modellit);
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.bindDomainObject(entity.getInventarioprocedimenti(),
		PkId.class, "id.codice");
	entity.setInventarioprocedimenti(inventarioprocedimenti);
    }
}
