package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocEndoLocDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoLoc;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoLocService;
import it.gruppoinit.pal.gp.core.service.ComuniService;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlberoprocEndoLocServiceImpl extends BaseServiceImpl<AlberoprocEndoLoc, PkId> implements AlberoprocEndoLocService {

    private AlberoprocEndoLocDAO alberoprocEndoLocDAO;
    private ComuniService comuniService;

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setAlberoprocEndoLocDAO(AlberoprocEndoLocDAO alberoprocEndoLocDAO) {

	this.alberoprocEndoLocDAO = alberoprocEndoLocDAO;
    }

    @Override
    public void insert(AlberoprocEndoLoc entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    alberoprocEndoLocDAO.insert(entity);
	}
    }

    //    @Override
    //    public void insert(AlberoprocEndoLoc entity,String isComuneBase) {
    //
    //	dataIntegration(entity);
    //	if (validateEntity(entity)) {
    //	    alberoprocEndoLocDAO.insert(entity);
    //	}
    //    }
    @Override
    public void update(AlberoprocEndoLoc entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    alberoprocEndoLocDAO.insert(entity);
	}
    }

    private void dataIntegration(AlberoprocEndoLoc entity) {

	if (entity == null) {
	    throw new RuntimeException("Entity nulla");
	}
	if (entity.getFlagIntervento() == null) {
	    entity.setFlagIntervento(Boolean.FALSE);
	}
	if (entity.getFlagNecessario() == null) {
	    entity.setFlagNecessario(Boolean.FALSE);
	}
	if (entity.getFlagPubblica() == null) {
	    entity.setFlagPubblica(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(AlberoprocEndoLoc entity) {

	Comuni c = comuniService.bindDomainObject(entity.getComune(), String.class, "codicecomune");
	entity.setComune(c);
    }

    @Override
    public void delete(AlberoprocEndoLoc entity) {

	if (isDeleteAllowed(entity)) {
	    alberoprocEndoLocDAO.delete(entity);
	}
    }

    @Override
    public List<AlberoprocEndoLoc> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException("METODO NON IMPLEMETATO");
    }

    @Override
    public AlberoprocEndoLoc findById(PkId id) {

	return alberoprocEndoLocDAO.findById(id);
    }

    @Override
    protected Class<AlberoprocEndoLoc> getEntityClass() {

	return AlberoprocEndoLoc.class;
    }

    @Override
    public List<AlberoprocEndoLoc> findInterventiFromAlbero(String idcomuneAlberoproc, Integer codiceAlbero) {

	FilterTable ft = getAlberoprocPerAlberoAndComune(idcomuneAlberoproc, codiceAlbero, Boolean.TRUE, null, null, null, null);
	return alberoprocEndoLocDAO.findByFilterTable(ft);
    }

    @Override
    public List<AlberoprocEndoLoc> findInterventiPubblicatiFromAlbero(String idcomuneAlberoproc, Integer codiceAlbero) {

	FilterTable ft = getAlberoprocPerAlberoAndComune(idcomuneAlberoproc, codiceAlbero, Boolean.TRUE, Boolean.TRUE, null, null, null);
	return alberoprocEndoLocDAO.findByFilterTable(ft);
    }

    @Override
    public List<AlberoprocEndoLoc> findInterventiPubblicatiFromAlbero(String idcomuneAlberoproc, Integer codiceAlbero, Integer codiceEndoIntervento,
	    String idComuneEndoIntervento) {

	FilterTable ft = getAlberoprocPerAlberoAndComune(idcomuneAlberoproc, codiceAlbero, Boolean.TRUE, Boolean.TRUE, codiceEndoIntervento,
		idComuneEndoIntervento, null);
	return alberoprocEndoLocDAO.findByFilterTable(ft);
    }

    @Override
    public List<AlberoprocEndoLoc> findEndoprocedimentiFromAlbero(String idcomuneAlberoproc, Integer codiceAlbero, String codiceComune) {

	FilterTable ft = getAlberoprocPerAlberoAndComune(idcomuneAlberoproc, codiceAlbero, Boolean.FALSE, null, null, null, codiceComune);
	return alberoprocEndoLocDAO.findByFilterTable(ft);
    }

    private FilterTable getAlberoprocPerAlberoAndComune(String idcomuneAlberoproc, Integer codiceAlbero, Boolean flagIntervento,
	    Boolean flagPubblica, Integer codiceEndoIntervento, String idComuneEndoIntervento, String codiceComune) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction albero = new FilterRestriction();
	albero.addFilterField(FilterUtils.equals("id.idcomune", idcomuneAlberoproc, "alberoproc", String.class));
	albero.addFilterField(FilterUtils.equals("id.codice", codiceAlbero, "alberoproc", Integer.class));
	ft.addRestriction(albero);
	if (flagIntervento != null) {
	    FilterRestriction intervento = new FilterRestriction();
	    if (flagIntervento.booleanValue()) {
		intervento.addFilterField(FilterUtils.equals("flagIntervento", flagIntervento, Boolean.class));
	    } else {
		intervento.setAndOrRestriction(AndOrRestriction.OR);
		intervento.addFilterField(FilterUtils.equals("flagIntervento", flagIntervento, Boolean.class));
		intervento.addFilterField(FilterUtils.isNull("flagIntervento"));
	    }
	    ft.addRestriction(intervento);
	}
	if (flagPubblica != null) {
	    FilterRestriction pubblica = new FilterRestriction();
	    if (flagPubblica.booleanValue()) {
		pubblica.addFilterField(FilterUtils.equals("flagPubblica", flagPubblica, Boolean.class));
	    } else {
		pubblica.setAndOrRestriction(AndOrRestriction.OR);
		pubblica.addFilterField(FilterUtils.equals("flagPubblica", flagPubblica, Boolean.class));
		pubblica.addFilterField(FilterUtils.isNull("flagPubblica"));
	    }
	    ft.addRestriction(pubblica);
	}
	if (StringUtils.isNotBlank(codiceComune)) {
	    FilterRestriction comunerestriction = new FilterRestriction();
	    comunerestriction.setAndOrRestriction(AndOrRestriction.OR);
	    comunerestriction.addFilterField(FilterUtils.equals("comune.codicecomune", codiceComune, String.class));
	    comunerestriction.addFilterField(FilterUtils.isNull("comune.codicecomune"));
	    ft.addRestriction(comunerestriction);
	}
	if (codiceEndoIntervento != null) {
	    FilterRestriction codiceEndoprocedimento = new FilterRestriction();
	    codiceEndoprocedimento.addFilterField(FilterUtils.equals("id.idcomune", idComuneEndoIntervento, "inventarioprocedimenti", String.class));
	    codiceEndoprocedimento.addFilterField(FilterUtils.equals("id.codice", codiceEndoIntervento, "inventarioprocedimenti", Integer.class));
	    ft.addRestriction(codiceEndoprocedimento);
	}
	ft.addOrder(FilterUtils.orderAsc("descrizione", FunctionsEnum.NVL_FUNCTION, "'ZZZZZZZZZZZZZZZZZZZZ'"));
	ft.addOrder(FilterUtils.orderAsc("procedimento", "inventarioprocedimenti"));
	return ft;
    }

    @Override
    public void insert(AlberoprocEndoLoc entity, Boolean isEndoIdComuneBase) {

	if (BooleanUtils.toBoolean(isEndoIdComuneBase) == true) {
	    entity.getInventarioprocedimenti().getId().setIdcomune(ORMHelper.getIdcomunebase());
	    this.insert(entity);
	} else {
	    this.insert(entity);
	}
    }
}