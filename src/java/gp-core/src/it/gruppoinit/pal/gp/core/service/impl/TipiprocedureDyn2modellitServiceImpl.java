package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipiprocedureDyn2modellitDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureDyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureDyn2modellitService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class TipiprocedureDyn2modellitServiceImpl extends BaseServiceImpl<TipiprocedureDyn2modellit, TipiprocedureDyn2modellitId> implements
	TipiprocedureDyn2modellitService {

    private TipiprocedureDyn2modellitDAO tipiproceduredyn2modellitDAO;
    private Dyn2ModellitService dyn2ModellitService;

    @Autowired
    public void setTipiprocedureDyn2modellitDAO(TipiprocedureDyn2modellitDAO tipiproceduredyn2modellitDAO) {

	this.tipiproceduredyn2modellitDAO = tipiproceduredyn2modellitDAO;
    }

    @Autowired
    public void setDyn2ModellitService(Dyn2ModellitService dyn2ModellitService) {

	this.dyn2ModellitService = dyn2ModellitService;
    }

    @Override
    protected Class<TipiprocedureDyn2modellit> getEntityClass() {

	return TipiprocedureDyn2modellit.class;
    }

    @Override
    public List<TipiprocedureDyn2modellit> findAll(Integer firstResult, Integer maxResult) {

	return tipiproceduredyn2modellitDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(TipiprocedureDyn2modellit entity) {

	dataintegration(entity);
	if (isInsertUpdateAllowed(entity)) {
	    if (validateEntity(entity)) {
		tipiproceduredyn2modellitDAO.insert(entity);
	    }
	}
    }

    @Override
    public TipiprocedureDyn2modellit findById(TipiprocedureDyn2modellitId id) {

	return tipiproceduredyn2modellitDAO.findById(id);
    }

    @Override
    public void update(TipiprocedureDyn2modellit entity) {

	dataintegration(entity);
	if (isInsertUpdateAllowed(entity)) {
	    if (validateEntity(entity)) {
		tipiproceduredyn2modellitDAO.update(entity);
	    }
	}
    }

    @Override
    public void delete(TipiprocedureDyn2modellit entity) {

	if (isDeleteAllowed(entity)) {
	    tipiproceduredyn2modellitDAO.delete(entity);
	}
    }

    @Override
    public List<TipiprocedureDyn2modellit> findByTipoprocedura(Integer codicetipoprocedura) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codicetipoprocedura, "tipiprocedure", Integer.class));
	// Ordinate per il campo ordine e descrizione del modello
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "dyn2Modellit"));
	return tipiproceduredyn2modellitDAO.findByFilterTable(ft);
    }

    private void dataintegration(TipiprocedureDyn2modellit entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro TipiprocedureDyn2modellit è nullo");
	}
	if (entity.getFlagFacoltativa() == null) {
	    entity.setFlagFacoltativa(new Boolean(false));
	}
	if (entity.getFlagPubblica() == null) {
	    entity.setFlagPubblica(new Boolean(false));
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(TipiprocedureDyn2modellit entity) {

	if (entity.getTipiprocedure() != null && entity.getTipiprocedure().getId() != null && entity.getTipiprocedure().getId().getCodice() == null) {
	    entity.setTipiprocedure(null);
	}
	if (entity.getDyn2Modellit() != null && entity.getDyn2Modellit().getId() != null && entity.getDyn2Modellit().getId().getCodice() == null) {
	    entity.setDyn2Modellit(null);
	}
    }

    protected boolean isDeleteAllowed(TipiprocedureDyn2modellit entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    protected boolean isInsertUpdateAllowed(TipiprocedureDyn2modellit entity) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getFlagTipofirma() != null && entity.getFlagTipofirma().equals(2)) {
	    if (EntityUtils.getNestedProperty(entity.getDyn2Modellit(), "id.codice") != null) {
		Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(entity.getDyn2Modellit().getId());
		if (dyn2Modellit.getDyn2Modellids() != null && !dyn2Modellit.getDyn2Modellids().isEmpty()) {
		    Set<Dyn2Modellid> dyn2Modellids = dyn2Modellit.getDyn2Modellids();
		    for (Dyn2Modellid dyn2Modellid : dyn2Modellids) {
			if (dyn2Modellid.getFlgMultiplo() == null || dyn2Modellid.getFlgMultiplo().equals(Boolean.FALSE)) {
			    insert = Boolean.FALSE;
			}
		    }
		}
	    }
	}
	if (!insert) {
	    _ivs.add(new InvalidValue(getMessageFromBundle(WebConstants.ALERT_NO_FLAG_MULTIPLO, new Object[] { entity.getDyn2Modellit()
		    .getDescrizione() }), null, null, "TIPIPROCEDURE_DYN2MODELLIT", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }
}
