package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentioneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.helper.OneriPerCausaleHelper;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentioneriService;
import it.gruppoinit.pal.gp.core.service.TipicausalioneriService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventarioprocedimentioneriServiceImpl extends BaseServiceImpl<Inventarioprocedimentioneri, PkId>
	implements InventarioprocedimentioneriService {

    private InventarioprocedimentioneriDAO inventarioprocedimentioneriDAO;
    private ComuniassociatiService comuniassociatiService;
    private TipicausalioneriService tipicausalioneriService;
    private InventarioprocedimentiService inventarioprocedimentiService;

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setTipicausalioneriService(TipicausalioneriService tipicausalioneriService) {

	this.tipicausalioneriService = tipicausalioneriService;
    }

    @Autowired
    public void setInventarioprocedimentioneriDAO(InventarioprocedimentioneriDAO inventarioprocedimentioneriDAO) {

	this.inventarioprocedimentioneriDAO = inventarioprocedimentioneriDAO;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService caService) {

	this.comuniassociatiService = caService;
    }

    @Override
    protected Class<Inventarioprocedimentioneri> getEntityClass() {

	return Inventarioprocedimentioneri.class;
    }

    @Override
    public void delete(Inventarioprocedimentioneri entity) {

	inventarioprocedimentioneriDAO.delete(entity);
    }

    @Override
    public List<Inventarioprocedimentioneri> findAll(Integer firstResult, Integer maxResult) {

	return inventarioprocedimentioneriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Inventarioprocedimentioneri findById(PkId id) {

	return inventarioprocedimentioneriDAO.findById(id);
    }

    @Override
    public void insert(Inventarioprocedimentioneri entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    inventarioprocedimentioneriDAO.insert(entity);
	}
    }

    @Override
    public void update(Inventarioprocedimentioneri entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    inventarioprocedimentioneriDAO.update(entity);
	}
    }

    private void dataIntegration(Inventarioprocedimentioneri entity) {

	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Inventarioprocedimentioneri entity) {

	Inventarioprocedimenti endo = inventarioprocedimentiService.bindDomainObjectForPkId(entity.getInventarioprocedimenti());
	entity.setInventarioprocedimenti(endo);
	Tipicausalioneri tco = tipicausalioneriService.bindDomainObjectForPkId(entity.getTipicausalioneri());
	entity.setTipicausalioneri(tco);
    }

    @Override
    public Inventarioprocedimentioneri findByCodiceInventarioCausaleCodiceComune(Integer codiceInventario, String idcomunecodiceinventario,
	    Integer codiceCausale, String codiceComune) {

	FilterTable ft = _prepareFilterInventario(codiceInventario, false, idcomunecodiceinventario, codiceComune);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipicausalioneriId", codiceCausale, Integer.class));
	//le causali sono configurate solo a livello egionale
	fr.addFilterField(FilterUtils.equals("tipicausalioneriIdComune", ORMHelper.getIdcomunebase(), Integer.class));
	if (StringUtils.isBlank(codiceComune)) {
	    fr.addFilterField(FilterUtils.isNull("codicecomune", "comune"));
	} else {
	    fr.addFilterField(FilterUtils.equals("codicecomune", codiceComune, "comune", String.class));
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("importo"));
	List<Inventarioprocedimentioneri> results = inventarioprocedimentioneriDAO.findByFilterTable(ft);
	if (results.size() > 0) {
	    return results.get(0);
	} else {
	    return null;
	}
    }

    @Override
    public List<Inventarioprocedimentioneri> findByCodiceInventario(Integer codiceInventario, boolean escludiCausaliDisabilitate,
	    String idcomunecodiceinventario, String codiceComune) {

	FilterTable ft = _prepareFilterInventario(codiceInventario, escludiCausaliDisabilitate, idcomunecodiceinventario, codiceComune);
	ft.addOrder(FilterUtils.orderAsc("tipicausalioneri"));
	return inventarioprocedimentioneriDAO.findByFilterTable(ft);
    }

    private FilterTable _prepareFilterInventario(Integer codiceInventario, boolean escludiCausaliDisabilitate, String idcomunecodiceinventario,
	    String codiceComune) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("inventarioprocedimentiId", codiceInventario, Integer.class));
	fr.addFilterField(FilterUtils.equals("inventarioprocedimentiIdComune", idcomunecodiceinventario, Integer.class));
	ft.addRestriction(fr);
	fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomunebase(), String.class));
	if (ORMHelper.isConsoleLocale()) {
	    fr.setAndOrRestriction(AndOrRestriction.OR);
	    fr.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	}
	ft.addRestriction(fr);
	if (StringUtils.isNotBlank(codiceComune)) {
	    FilterRestriction comuni = new FilterRestriction();
	    comuni.setAndOrRestriction(AndOrRestriction.OR);
	    comuni.addFilterField(FilterUtils.isNull("comune.codicecomune"));
	    comuni.addFilterField(FilterUtils.equals("comune.codicecomune", codiceComune, String.class));
	    ft.addRestriction(comuni);
	}
	if (escludiCausaliDisabilitate) {
	    FilterRestriction dis = new FilterRestriction();
	    dis.setAndOrRestriction(AndOrRestriction.OR);
	    dis.addFilterField(FilterUtils.isNull("coDisabilitato", "tipicausalioneri"));
	    dis.addFilterField(FilterUtils.equals("coDisabilitato", Boolean.FALSE, "tipicausalioneri", Boolean.class));
	    ft.addRestriction(dis);
	}
	return ft;
    }

    @Override
    public List<OneriPerCausaleHelper> findByCodiceInventarioGroupByCausale(Integer codiceInventario, String idcomuneInventario,
	    String codiceComune) {

	List<OneriPerCausaleHelper> oneris = new ArrayList<OneriPerCausaleHelper>();
	FilterTable ft = _prepareFilterInventario(codiceInventario, false, idcomuneInventario, codiceComune);
	ft.addOrder(FilterUtils.orderAsc("coDescrizione", "tipicausalioneri"));
	List<Inventarioprocedimentioneri> rawRows = inventarioprocedimentioneriDAO.findByFilterTable(ft);
	Integer idCausale = null;
	List<Responsabilicomuni> comuniGruppo = this.comuniassociatiService.checkComuniAbilitatiPerResponsabile();
	List<Inventarioprocedimentioneri> coRows = new ArrayList<Inventarioprocedimentioneri>();
	for (int i = 0; i < rawRows.size(); i++) {
	    Inventarioprocedimentioneri onere = rawRows.get(i);
	    if (onere.getTipicausalioneri() != null && !onere.getTipicausalioneri().getId().getCodice().equals(idCausale)) {
		if (idCausale != null) {
		    OneriPerCausaleHelper coh = new OneriPerCausaleHelper(coRows);
		    coh.setComuniGruppo(comuniGruppo);
		    oneris.add(coh);
		    coRows = new ArrayList<Inventarioprocedimentioneri>();
		}
		idCausale = onere.getTipicausalioneri().getId().getCodice();
	    }
	    coRows.add(onere);
	}
	if (coRows.size() > 0) {
	    OneriPerCausaleHelper coh = new OneriPerCausaleHelper(coRows);
	    coh.setComuniGruppo(comuniGruppo);
	    oneris.add(coh);
	}
	return oneris;
    }

    @Override
    public List<Inventarioprocedimentioneri> findOneriPerProcedimenti(String idComune, String codiceComuneGruppo, List<PkId> pkProcedimenti,
	    boolean escludiDisattivi) {

	return this.inventarioprocedimentioneriDAO.findOneriPerProcedimenti(idComune, codiceComuneGruppo, pkProcedimenti, true);
    }
}
