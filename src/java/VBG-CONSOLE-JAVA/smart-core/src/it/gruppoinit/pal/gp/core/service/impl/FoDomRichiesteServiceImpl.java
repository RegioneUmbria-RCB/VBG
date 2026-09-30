package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoDomRichiesteDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FoDomRichieste;
import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.FoDomrichAllegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FoDomRichiesteService;
import it.gruppoinit.pal.gp.core.service.FoDomandeService;
import it.gruppoinit.pal.gp.core.service.FoDomrichAllegatiService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoDomRichiesteServiceImpl extends BaseServiceImpl<FoDomRichieste, PkId> implements FoDomRichiesteService {

    private FoDomRichiesteDAO foDomRichiesteDAO;
    private FoDomrichAllegatiService foDomrichAllegatiService;
    private FoDomandeService foDomandeService;

    @Autowired
    public void setFoDomandeService(FoDomandeService foDomandeService) {

	this.foDomandeService = foDomandeService;
    }

    @Autowired
    public void setFoDomrichAllegatiService(FoDomrichAllegatiService foDomrichAllegatiService) {

	this.foDomrichAllegatiService = foDomrichAllegatiService;
    }

    @Autowired
    public void setFoDomRichiesteDAO(FoDomRichiesteDAO foDomRichiesteDAO) {

	this.foDomRichiesteDAO = foDomRichiesteDAO;
    }

    @Override
    public void insert(FoDomRichieste entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    foDomRichiesteDAO.insert(entity);
	}
    }

    @Override
    public void update(FoDomRichieste entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    foDomRichiesteDAO.update(entity);
	}
    }

    private void dataIntegration(FoDomRichieste entity) {

	if (entity.getFlagLetto() == null) {
	    entity.setFlagLetto(Boolean.FALSE);
	}
	if (entity.getProposta() == null) {
	    entity.setProposta(Boolean.FALSE);
	}
	if (entity.getFlagCompletata() == null) {
	    entity.setFlagCompletata(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(FoDomRichieste entity) {

	FoDomande rich = foDomandeService.bindDomainObject(entity.getFoDomande(), PkId.class, "id.codice");
	entity.setFoDomande(rich);
	FoDomRichieste richiestapadre = this.bindDomainObject(entity.getRichiestaPadre(), PkId.class, "id.codice");
	entity.setRichiestaPadre(richiestapadre);
    }

    @Override
    public void delete(FoDomRichieste entity) {

	if (isDeleteAllowed(entity)) {
	    foDomRichiesteDAO.delete(entity);
	    childDelete(entity);
	}
    }

    @Override
    protected void childDelete(FoDomRichieste entity) {

	List<FoDomrichAllegati> s = foDomrichAllegatiService.findByRichiesta(entity.getId().getIdcomune(), entity.getId().getCodice());
	for (FoDomrichAllegati foDomrichAllegati : s) {
	    foDomrichAllegatiService.delete(foDomrichAllegati);
	}
    }

    @Override
    public FoDomRichieste findById(PkId id) {

	return foDomRichiesteDAO.findById(id);
    }

    @Override
    protected Class<FoDomRichieste> getEntityClass() {

	return FoDomRichieste.class;
    }

    @Override
    public List<FoDomRichieste> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public List<FoDomRichieste> findByIdpratica(String identificativoPratica, Integer firstResult, Integer maxResult, VERSO_IN_OUT verso) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("idpratica", identificativoPratica));
	if (verso != null) {
	    switch (verso) {
	    case IN:
		fr.addFilterField(FilterUtils.equals("versoInOut", VERSO_IN_VALUE, String.class));
		break;
	    case OUT:
		fr.addFilterField(FilterUtils.equals("versoInOut", VERSO_OUT_VALUE, String.class));
	    default:
		break;
	    }
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataRichiesta"));
	return foDomRichiesteDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public List<FoDomRichieste> findByFoDomandeId(Integer foDomandeId, String idcomune, Integer firstResult, Integer maxResult, VERSO_IN_OUT verso) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	fr.addFilterField(FilterUtils.equals("foDomandeId", foDomandeId, Integer.class));
	if (verso != null) {
	    switch (verso) {
	    case IN:
		fr.addFilterField(FilterUtils.equals("versoInOut", VERSO_IN_VALUE, String.class));
		break;
	    case OUT:
		fr.addFilterField(FilterUtils.equals("versoInOut", VERSO_OUT_VALUE, String.class));
	    default:
		break;
	    }
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataRichiesta"));
	return foDomRichiesteDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public List<FoDomRichieste> findRichiesteNonLettePerUtente(String cf, String idente, Integer firstResult, Integer maxResult, VERSO_IN_OUT verso,
	    Boolean completate) {

	String[] cfs = new String[] { cf };
	FilterTable ft = getFilterTable(cfs, idente, Boolean.FALSE, verso, completate);
	ft.addOrder(FilterUtils.orderAsc("dataRichiesta"));
	return foDomRichiesteDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    private FilterTable getFilterTable(String[] cfs, String idente, Boolean letto, VERSO_IN_OUT verso, Boolean completate) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("sdeproxy.idente", idente, "foDomande", String.class));
	if (null != letto) {
	    if (letto.booleanValue()) {
		fr.addFilterField(FilterUtils.equals("flagLetto", Boolean.TRUE, Boolean.class));
	    } else {
		fr.addFilterField(FilterUtils.notEquals("flagLetto", Boolean.TRUE, Boolean.class));
	    }
	}
	if (null != completate) {
	    if (completate.booleanValue()) {
		fr.addFilterField(FilterUtils.equals("flagCompletata", Boolean.TRUE, Boolean.class));
	    } else {
		fr.addFilterField(FilterUtils.notEquals("flagCompletata", Boolean.TRUE, Boolean.class));
	    }
	}
	if (verso != null) {
	    switch (verso) {
	    case IN:
		fr.addFilterField(FilterUtils.equals("versoInOut", VERSO_IN_VALUE, String.class));
		break;
	    case OUT:
		fr.addFilterField(FilterUtils.equals("versoInOut", VERSO_OUT_VALUE, String.class));
	    default:
		break;
	    }
	}
	ft.addRestriction(fr);
	FilterRestriction cfr = new FilterRestriction();
	cfr.setAndOrRestriction(AndOrRestriction.OR);
	for (String cf : cfs) {
	    cfr.addFilterField(FilterUtils.equalsIgnoreCase("presentatoreCodfiscale", cf, "foDomande"));
	}
	ft.addRestriction(cfr);
	return ft;
    }

    @Override
    public int countRichiesteNonLettePerUtente(String cf, String idente, VERSO_IN_OUT verso, Boolean completate) {

	String[] cfs = new String[] { cf };
	FilterTable ft = getFilterTable(cfs, idente, Boolean.FALSE, verso, completate);
	return foDomRichiesteDAO.countRecord(ft);
    }

    @Override
    public List<FoDomRichieste> findRisposteByFoDomRichiesteId(Integer foDomrichiestaId, String idcomune, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	fr.addFilterField(FilterUtils.equals("richiestaPadreId", foDomrichiestaId, Integer.class));
	fr.addFilterField(FilterUtils.equals("versoInOut", VERSO_OUT_VALUE, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataRichiesta"));
	return foDomRichiesteDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public void insertInviaRichiesta(FoDomRichieste richas) {

	this.update(richas);
	if (StringUtils.defaultString(richas.getTipoRichiesta()).equalsIgnoreCase(FoDomRichiesteService.INOLTRO_INTEGRAZIONE)) {
	    // semProxyService.inviaStimoloInvioIntegrazione(richas);
	} else if (StringUtils.defaultString(richas.getTipoRichiesta()).equalsIgnoreCase(FoDomRichiesteService.INOLTRO_CONFORMAZIONE)) {
	    // semProxyService.inviaStimoloInvioConformazione(richas);
	} else if (StringUtils.defaultString(richas.getTipoRichiesta()).equalsIgnoreCase(FoDomRichiesteService.INOLTRO_COMUNICAZIONE)) {
	    // semProxyService.inviaStimoloInvioComunicazione(richas);
	} else {
	    throw new NotImplementedException("la tipologia di richiesta non è valida");
	}
    }

    @Override
    public FoDomRichieste findByIdmessaggio(String idMessaggio) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("idRichiestaSistema", idMessaggio));
	ft.addRestriction(fr);
	List<FoDomRichieste> rics = foDomRichiesteDAO.findByFilterTable(ft, 0, 2);
	if (rics.size() == 1) {
	    return rics.get(0);
	}
	return null;
    }
}
