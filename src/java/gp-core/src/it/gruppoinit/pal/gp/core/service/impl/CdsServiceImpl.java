package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CdsDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Cds;
import it.gruppoinit.pal.gp.core.domain.Cdsatti;
import it.gruppoinit.pal.gp.core.domain.Cdsconvocazioni;
import it.gruppoinit.pal.gp.core.domain.Cdsinvitati;
import it.gruppoinit.pal.gp.core.domain.Cdsinvitati2;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.CdsFilter;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CdsService;
import it.gruppoinit.pal.gp.core.service.CdsattiService;
import it.gruppoinit.pal.gp.core.service.CdsconvocazioniService;
import it.gruppoinit.pal.gp.core.service.Cdsinvitati2Service;
import it.gruppoinit.pal.gp.core.service.CdsinvitatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocedimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class CdsServiceImpl extends BaseServiceImpl<Cds, PkId> implements CdsService {

    private CdsattiService cdsattiService;
    private CdsconvocazioniService cdsconvocazioniService;
    private CdsDAO cdsDAO;
    private CdsinvitatiService cdsinvitatiService;
    private Cdsinvitati2Service cdsinvitati2Service;
    private IstanzeprocedimentiService istanzeprocedimentiService;
    private IstanzeService istanzeService;
    private MovimentiService movimentiService;

    @Autowired
    public void setCdsattiService(CdsattiService cdsattiService) {

	this.cdsattiService = cdsattiService;
    }

    @Autowired
    public void setCdsconvocazioniService(CdsconvocazioniService cdsconvocazioniService) {

	this.cdsconvocazioniService = cdsconvocazioniService;
    }

    @Autowired
    public void setCdsDAO(CdsDAO cdsDAO) {

	this.cdsDAO = cdsDAO;
    }

    @Autowired
    public void setCdsinvitatiService(CdsinvitatiService cdsinvitatiService) {

	this.cdsinvitatiService = cdsinvitatiService;
    }

    @Autowired
    public void setCdsinvitati2Service(Cdsinvitati2Service cdsinvitati2Service) {

	this.cdsinvitati2Service = cdsinvitati2Service;
    }

    @Autowired
    public void setIstanzeprocedimentiService(IstanzeprocedimentiService istanzeprocedimentiService) {

	this.istanzeprocedimentiService = istanzeprocedimentiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Override
    protected Class<Cds> getEntityClass() {

	return Cds.class;
    }

    @Override
    public List<Cds> findAll(Integer firstResult, Integer maxResult) {

	return cdsDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Cds entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Set<Cdsconvocazioni> cdsconvocazionis = entity.getCdsconvocazionis();
	    entity.setCdsconvocazionis(null);
	    cdsDAO.insert(entity);
	    childDataInsert(entity, cdsconvocazionis);
	}
    }

    @Override
    public Cds findById(PkId id) {

	return cdsDAO.findById(id);
    }

    @Override
    public void update(Cds entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    cdsDAO.update(entity);
	}
    }

    @Override
    public void delete(Cds entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    Integer codMovimento = entity.getMovimento().getId().getCodice();
	    cdsDAO.delete(entity);
	    cdsDAO.flush();
	    if (codMovimento != null) {
		Movimenti movimento = movimentiService.findById(new PkId(codMovimento));
		movimentiService.delete(movimento);
	    }
	}
    }

    @Override
    protected void childDelete(Cds entity) {

	Set<Cdsconvocazioni> cdsconvocazionis = entity.getCdsconvocazionis();
	for (Cdsconvocazioni cdsconvocazioni : cdsconvocazionis) {
	    cdsconvocazioniService.delete(cdsconvocazioni);
	}
	Set<Cdsatti> cdsattis = entity.getCdsattis();
	for (Cdsatti cdsatti : cdsattis) {
	    cdsattiService.delete(cdsatti);
	}
	Set<Cdsinvitati2> cdsinvitati2s = entity.getCdsinvitati2s();
	for (Cdsinvitati2 cdsinvitati2 : cdsinvitati2s) {
	    cdsinvitati2Service.delete(cdsinvitati2);
	}
	Set<Cdsinvitati> cdsinvitatis = entity.getCdsinvitatis();
	for (Cdsinvitati cdsinvitati : cdsinvitatis) {
	    cdsinvitatiService.delete(cdsinvitati);
	}
    }

    private void dataIntegration(Cds entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro CDS è nullo");
	}
	fixMergeEntityProperties(entity);
	if (entity.getFlagvia() == null) {
	    entity.setFlagvia(Boolean.FALSE);
	}
	if (entity.getInvitorichiedente() == null) {
	    entity.setInvitorichiedente(Boolean.FALSE);
	}
	if (entity.getDataconvocazione() == null) {
	    entity.setDataconvocazione(Calendar.getInstance().getTime());
	}
    }

    private void childDataInsert(Cds entity, Set<Cdsconvocazioni> cdsconvocazionis) {

	Istanze istanza = entity.getIstanze();
	List<Istanzeprocedimenti> istanzeprocedimentis = istanzeprocedimentiService.findByIstanze(istanza);
	for (Istanzeprocedimenti istanzeprocedimenti : istanzeprocedimentis) {
	    Inventarioprocedimenti endo = istanzeprocedimenti.getInventarioprocedimenti();
	    if (BooleanUtils.isFalse(endo.getDisabilitato())) {
		if (endo.getAmministrazioni() != null) {
		    Cdsinvitati cdsinvitati = new Cdsinvitati();
		    cdsinvitati.setIstanze(istanza);
		    cdsinvitati.setAmministrazioni(endo.getAmministrazioni());
		    cdsinvitati.setCds(entity);
		    cdsinvitatiService.insert(cdsinvitati);
		}
	    }
	}
	if (cdsconvocazionis != null) {
	    if (cdsconvocazionis.size() > 0) {
		for (Cdsconvocazioni cdsconvocazioni : cdsconvocazionis) {
		    cdsconvocazioni.setCds(entity);
		    cdsconvocazioni.setIstanze(istanza);
		    cdsconvocazioniService.insert(cdsconvocazioni);
		}
	    }
	}
    }

    @Override
    protected void fixMergeEntityProperties(Cds entity) {

	Istanze istanza = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanza);
	Movimenti movimento = movimentiService.bindDomainObject(entity.getMovimento(), PkId.class, "id.codice");
	entity.setMovimento(movimento);
    }

    protected boolean isDeleteAllowed(Cds entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Cds> findByIstanza(Istanze istanza) {

	if (EntityUtils.getNestedProperty(istanza, "id.codice") == null) {
	    throw new IllegalArgumentException("Il parametro istanza è nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzeId", istanza.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("dataconvocazione"));
	return cdsDAO.findByFilterTable(ft);
    }

    @Override
    public List<Cds> findByMovimento(Movimenti movimento) {

	if (EntityUtils.getNestedProperty(movimento, "id.codice") == null) {
	    throw new IllegalArgumentException("Il parametro MOVIMENTO è nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("movimentoId", movimento.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("dataconvocazione"));
	return cdsDAO.findByFilterTable(ft);
    }

    @Override
    public List<Cds> findByFilterTable(FilterTable filterTable) {

	return cdsDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Cds> findByFilter(CdsFilter cdsFilter) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (StringUtils.isNotBlank(cdsFilter.getComune().getCodicecomune())) {
	    fr.addFilterField(FilterUtils.startsWith("codicecomune", cdsFilter.getComune().getCodicecomune(), "istanze.comune"));
	}
	if (StringUtils.isNotBlank(cdsFilter.getNumeroistanza())) {
	    fr.addFilterField(FilterUtils.startsWith("numeroistanza", cdsFilter.getNumeroistanza(), "istanze"));
	}
	if (cdsFilter.getDallaData() != null) {
	    fr.addFilterField(FilterUtils.greaterEqual("dataconvocazione", cdsFilter.getDallaData(), "cdsconvocazionis", Date.class));
	}
	if (cdsFilter.getAllaData() != null) {
	    fr.addFilterField(FilterUtils.smallerEqual("dataconvocazione", cdsFilter.getAllaData(), "cdsconvocazionis", Date.class));
	}
	if (StringUtils.isNotBlank(cdsFilter.getNominativo())) {
	    FilterRestriction richiedente = new FilterRestriction();
	    richiedente.setAndOrRestriction(AndOrRestriction.OR);
	    richiedente.addFilterField(FilterUtils.like("nominativo", cdsFilter.getNominativo(), "istanze.richiedente"));
	    richiedente.addFilterField(FilterUtils.like("nome", cdsFilter.getNominativo(), "istanze.richiedente"));
	    ft.addRestriction(richiedente);
	}
	if (fr.getFilterFields().size() > 0) {
	    ft.addRestriction(fr);
	}
	ft.addOrder(FilterUtils.orderDesc("dataconvocazione", "cdsconvocazionis"));
	return cdsDAO.findByFilterTable(ft);
    }

    @Override
    public int countByIstanza(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("CdsService#countByIstanza: Il parametro codiceIstanza è nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzeId", codiceIstanza, Integer.class));
	ft.addRestriction(fr);
	return cdsDAO.countRecord(ft);
    }

    @Override
    public int countByMovimento(Integer codiceMovimento) {

	if (codiceMovimento == null) {
	    throw new IllegalArgumentException("CdsService#countByMovimento: Il parametro codiceMovimento è nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("movimentoId", codiceMovimento, Integer.class));
	ft.addRestriction(fr);
	return cdsDAO.countRecord(ft);
    }
}
