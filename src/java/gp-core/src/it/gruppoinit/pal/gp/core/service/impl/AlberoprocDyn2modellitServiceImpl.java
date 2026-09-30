package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AlberoprocDyn2modellitDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocDyn2modellitService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlberoprocDyn2modellitServiceImpl extends BaseServiceImpl<AlberoprocDyn2modellit, AlberoprocDyn2modellitId> implements
	AlberoprocDyn2modellitService {

    private AlberoprocDyn2modellitDAO alberoprocDyn2modellitDAO;
    private Dyn2ModellitService dyn2ModellitService;

    @Autowired
    public void setAlberoprocDyn2modellitDAO(AlberoprocDyn2modellitDAO alberoprocDyn2modellitDAO) {

	this.alberoprocDyn2modellitDAO = alberoprocDyn2modellitDAO;
    }

    @Autowired
    public void setDyn2ModellitService(Dyn2ModellitService dyn2ModellitService) {

	this.dyn2ModellitService = dyn2ModellitService;
    }

    @Override
    protected Class<AlberoprocDyn2modellit> getEntityClass() {

	return AlberoprocDyn2modellit.class;
    }

    @Override
    public void delete(AlberoprocDyn2modellit entity) {

	alberoprocDyn2modellitDAO.delete(entity);
    }

    @Override
    public List<AlberoprocDyn2modellit> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocDyn2modellitDAO.findAll(firstResult, maxResult);
    }

    @Override
    public AlberoprocDyn2modellit findById(AlberoprocDyn2modellitId id) {

	return alberoprocDyn2modellitDAO.findById(id);
    }

    @Override
    public void insert(AlberoprocDyn2modellit entity) {

	dataintegration(entity);
	if (isInsertUpdateAllowed(entity)) {
	    if (validateEntity(entity)) {
		alberoprocDyn2modellitDAO.insert(entity);
	    }
	}
    }

    @Override
    public void update(AlberoprocDyn2modellit entity) {

	dataintegration(entity);
	if (isInsertUpdateAllowed(entity)) {
	    if (validateEntity(entity)) {
		alberoprocDyn2modellitDAO.update(entity);
	    }
	}
    }

    @Override
    public List<AlberoprocDyn2modellit> findByAlberoProc(Integer codiceAlberoproc) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkScId", codiceAlberoproc, Integer.class));
	// Ordinate per il campo ordine e descrizione del modello
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "dyn2Modellit"));
	return alberoprocDyn2modellitDAO.findByFilterTable(ft);
    }

    @Override
    public AlberoprocDyn2modellit findByAlberoProcAndDyn2modellit(Integer codiceAlberoproc, Integer codiceDyn2modellit) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkScId", codiceAlberoproc, Integer.class));
	fr.addFilterField(FilterUtils.equals("id.fkD2mtId", codiceDyn2modellit, Integer.class));
	ft.addRestriction(fr);
	List<AlberoprocDyn2modellit> list = alberoprocDyn2modellitDAO.findByFilterTable(ft);
	if (list.isEmpty()) {
	    throw new RuntimeException("AlberoprocDyn2modellit non trovato per codiceAlberoproc=" + codiceAlberoproc + " e codiceDyn2modellit="
		    + codiceDyn2modellit);
	}
	return list.get(0);
    }

    private void dataintegration(AlberoprocDyn2modellit entity) {

	if (entity.getFlagFacoltativa() == null) {
	    entity.setFlagFacoltativa(new Boolean(false));
	}
	if (entity.getFlagPubblica() == null) {
	    entity.setFlagPubblica(new Boolean(false));
	}
    }

    protected boolean isInsertUpdateAllowed(AlberoprocDyn2modellit entity) {

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
				    .getDyn2Modellit().getDescrizione() }), null, null, "ALBEROPROC_DYN2MODELLIT", null));
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
		    _ivs.add(new InvalidValue(getMessageFromBundle(WebConstants.ALERT_NON_POSSO_FIRMARE_MODELLI_MULTIPLI, new Object[] { entity.getDyn2Modellit()
			    .getDescrizione() }), null, null, "ALBEROPROC_DYN2MODELLIT", null));
		}
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }
}
