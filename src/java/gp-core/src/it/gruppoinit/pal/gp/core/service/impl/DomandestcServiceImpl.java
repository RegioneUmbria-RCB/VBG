package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.DomandestcDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Domandestc;
import it.gruppoinit.pal.gp.core.domain.DomandestcAllegati;
import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.helper.DomandeSTCScadenzarioDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DomandestcHelper;
import it.gruppoinit.pal.gp.core.domain.web.DomandeStcFilter;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.DomandestcAllegatiService;
import it.gruppoinit.pal.gp.core.service.DomandestcService;
import it.gruppoinit.pal.gp.core.service.FoArconfigurazioneService;
import it.gruppoinit.pal.gp.core.service.FoDomandeService;

/**
 * 
 * @author gianpaolot
 */
@Service
public class DomandestcServiceImpl extends BaseServiceImpl<Domandestc, PkId> implements DomandestcService {

    private static final Logger log = LoggerFactory.getLogger(DomandestcServiceImpl.class);
    private DomandestcDAO domandestcDAO;
    private DomandestcAllegatiService domandestcAllegatiService;
    private FoArconfigurazioneService foArconfigurazioneService;
    private FoDomandeService foDomandeService;
    private OggettiService oggettiService;
    private ComuniassociatiService comuniassociatiService;

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setDomandestcDAO(DomandestcDAO domandestcDAO) {

	this.domandestcDAO = domandestcDAO;
    }

    @Autowired
    public void setDomandestcAllegatiService(DomandestcAllegatiService domandestcAllegatiService) {

	this.domandestcAllegatiService = domandestcAllegatiService;
    }

    @Autowired
    public void setFoArconfigurazioneService(FoArconfigurazioneService foArconfigurazioneService) {

	this.foArconfigurazioneService = foArconfigurazioneService;
    }

    @Autowired
    public void setFoDomandeService(FoDomandeService foDomandeService) {

	this.foDomandeService = foDomandeService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    protected Class<Domandestc> getEntityClass() {

	return Domandestc.class;
    }

    @Override
    public List<Domandestc> findAll(Integer firstResult, Integer maxResult) {

	return domandestcDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Domandestc entity) {

	if (validateEntity(entity)) {
	    domandestcDAO.insert(entity);
	}
    }

    @Override
    public Domandestc findById(PkId id) {

	return domandestcDAO.findById(id);
    }

    @Override
    public void update(Domandestc entity) {

	if (validateEntity(entity)) {
	    domandestcDAO.update(entity);
	}
    }

    @Override
    public void delete(Domandestc entity) {

	this.delete(entity, true);
    }

    @Override
    public void delete(Domandestc entity, boolean cancellaFoDomande) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity, cancellaFoDomande);
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    domandestcDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public List<Domandestc> findByFilterTable(FilterTable filterTable) {

	return domandestcDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Domandestc> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	return domandestcDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public int countRecord(FilterTable filterTable) {

	return domandestcDAO.countRecord(filterTable);
    }

    @Override
    public FilterTable createFilterTableByEntity(Domandestc domandestc) {

	if (domandestc == null) {
	    throw new IllegalArgumentException("Domandestc non può essere null");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction restriction = new FilterRestriction();
	// Criterio di ricerca da applicare ad entrambe le query, conteggio record e ricerca record
	if (StringUtils.isNotBlank(domandestc.getNumeroistanza())) {
	    restriction.addFilterField(FilterUtils.equals("numeroistanza", domandestc.getNumeroistanza(), String.class));
	}
	if (StringUtils.isNotBlank(domandestc.getRichiedente())) {
	    restriction.addFilterField(FilterUtils.like("richiedente", domandestc.getRichiedente()));
	}
	if (StringUtils.isNotBlank(domandestc.getUltimoerrore())) {
	    restriction.addFilterField(FilterUtils.like("ultimoerrore", domandestc.getUltimoerrore()));
	}
	if (domandestc.getDataUltimoerrore() != null) {
	    restriction.addFilterField(FilterUtils.equals("dataUltimoerrore", domandestc.getDataUltimoerrore(), Date.class));
	}
	if (domandestc.getIdEntemitt() != null) {
	    restriction.addFilterField(FilterUtils.like("idEntemitt", domandestc.getIdEntemitt()));
	}
	if (domandestc.getIdSportellomitt() != null) {
	    restriction.addFilterField(FilterUtils.like("idSportellomitt", domandestc.getIdSportellomitt()));
	}
	if (domandestc.getIdNodo() != null) {
	    restriction.addFilterField(FilterUtils.like("idNodo", domandestc.getIdNodo()));
	}
	if (domandestc.getFlagImport() != null) {
	    restriction.addFilterField(FilterUtils.equals("flagImport", domandestc.getFlagImport(), Boolean.class));
	}
	filterTable.addRestriction(restriction);
	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	if (isComuniAssociati) {
	    if (log.isDebugEnabled()) {
		log.debug("createFilterTableByEntity# E' un installazione multicomune, recupero i comuni configurati per l'operatore)");
	    }
	    FilterRestriction frr = new FilterRestriction();
	    frr.setAndOrRestriction(AndOrRestriction.OR);
	    frr.addFilterField(FilterUtils.isNull("comune.codicecomune"));
	    List<Responsabilicomuni> responsabilicomunis = comuniassociatiService.checkComuniAbilitatiPerResponsabile(false);
	    if (responsabilicomunis != null && !responsabilicomunis.isEmpty()) {
		String[] codiceComune = new String[responsabilicomunis.size()];
		frr.addFilterField(FilterUtils.in("comune.codicecomune", codiceComune, String.class));
		int i = 0;
		for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
		    codiceComune[i] = responsabilicomuni.getId().getCodicecomune();
		    i++;
		}
		filterTable.addRestriction(frr);
	    }
	}
	return filterTable;
    }

    @Override
    public List<Domandestc> findByIstanza(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("Il parametro codiceistanza è nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	ft.addRestriction(restriction);
	ft.addOrder(FilterUtils.orderDesc("dataricezione"));
	return this.findByFilterTable(ft);
    }

    @Override
    public List<Domandestc> findByCodiceIstanzaPrenotato(Integer codice) {

	if (codice == null) {
	    throw new IllegalArgumentException("Il parametro codiceistanza è nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("codiceistanzaprenotata", codice, Integer.class));
	ft.addRestriction(restriction);
	ft.addOrder(FilterUtils.orderDesc("dataricezione"));
	return this.findByFilterTable(ft);
    }

    @Override
    public List<Domandestc> findDomandeConErrore(Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("flagImport", Boolean.FALSE, Boolean.class));
	ft.addOrder(FilterUtils.orderAsc("dataricezione"));
	// Filtro per codice comune se ci troviamo in un installazione multi comune
	if (log.isDebugEnabled()) {
	    log.debug("findDomandeConErrore# Controllo se si tratta di un installazione con idcomune {} è multi comune", ORMHelper.getIdcomune());
	}
	ft.addRestriction(fr);
	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	if (isComuniAssociati) {
	    if (log.isDebugEnabled()) {
		log.debug("findDomandeConErrore# E' un installazione multicomune, recupero i comuni configurati per l'operatore)");
	    }
	    FilterRestriction frr = new FilterRestriction();
	    frr.setAndOrRestriction(AndOrRestriction.OR);
	    frr.addFilterField(FilterUtils.isNull("comune.codicecomune"));
	    List<Responsabilicomuni> responsabilicomunis = comuniassociatiService.checkComuniAbilitatiPerResponsabile(false);
	    if (responsabilicomunis != null && !responsabilicomunis.isEmpty()) {
		String[] codiceComune = new String[responsabilicomunis.size()];
		frr.addFilterField(FilterUtils.in("comune.codicecomune", codiceComune, String.class));
		int i = 0;
		for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
		    codiceComune[i] = responsabilicomuni.getId().getCodicecomune();
		    i++;
		}
		ft.addRestriction(frr);
	    }
	}
	return this.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public List<Domandestc> findDomandePervenute(Integer firstResult, Integer maxResult) {

	Statiistanza stato = foArconfigurazioneService.findStatoInizialeIstanzaOnline();
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("flagImport", Boolean.TRUE, Boolean.class));
	fr.addFilterField(FilterUtils.isNotNull("istanza.id.codice"));
	if (stato != null) {
	    fr.addFilterField(FilterUtils.equals("id.codicestato", stato.getId().getCodicestato(), "istanza.chiusura", String.class));
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataricezione"));
	return this.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    protected boolean isDeleteAllowed(Domandestc entity) {

	boolean delete = true;
	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	if (!ivs.isEmpty()) {
	    this.throwValidationMessages(ivs);
	}
	return delete;
    }

    protected void childDelete(Domandestc entity, boolean cancellaFoDomande) {

	// Cancellazione di FoDomande
	if (cancellaFoDomande) {
	    List<FoDomande> foDomandes = foDomandeService.findByIdentificativodomanda(entity.getIdDomandamitt());
	    for (FoDomande foDomande : foDomandes) {
		foDomandeService.delete(foDomande);
	    }
	}
	List<DomandestcAllegati> allegatis = domandestcAllegatiService.findByDomandestc(entity.getId().getCodice());
	for (DomandestcAllegati domandestcAllegati : allegatis) {
	    domandestcAllegatiService.delete(domandestcAllegati);
	}
    }

    @Override
    public int countDomandeConErrore() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("flagImport", Boolean.FALSE, Boolean.class));
	ft.addRestriction(fr);
	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	if (isComuniAssociati) {
	    if (log.isDebugEnabled()) {
		log.debug("findDomandeConErrore# E' un installazione multicomune, recupero i comuni configurati per l'operatore)");
	    }
	    FilterRestriction frr = new FilterRestriction();
	    frr.setAndOrRestriction(AndOrRestriction.OR);
	    frr.addFilterField(FilterUtils.isNull("comune.codicecomune"));
	    List<Responsabilicomuni> responsabilicomunis = comuniassociatiService.checkComuniAbilitatiPerResponsabile(false);
	    if (responsabilicomunis != null && !responsabilicomunis.isEmpty()) {
		String[] codiceComune = new String[responsabilicomunis.size()];
		frr.addFilterField(FilterUtils.in("comune.codicecomune", codiceComune, String.class));
		int i = 0;
		for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
		    codiceComune[i] = responsabilicomuni.getId().getCodicecomune();
		    i++;
		}
		ft.addRestriction(frr);
	    }
	}
	ft.addOrder(FilterUtils.orderAsc("dataricezione"));
	return this.countRecord(ft);
    }

    @Override
    public List<DomandestcHelper> countDomandestc(String codicesoftware, String stato) {

	return domandestcDAO.countDomandestc(codicesoftware, stato);
    }

    @Override
    public List<DomandeSTCScadenzarioDTO> findDomandePervenuteSTC(DomandeStcFilter domandeStcFilter, boolean isImportate, String codiceSoftwareScad,
	    Integer firstResult, Integer maxResult) {

	return domandestcDAO.findDomandePervenuteSTC(domandeStcFilter, isImportate, codiceSoftwareScad, firstResult, maxResult);
    }

    @Override
    public int countScadenzarioDomandePervenuteSTC(boolean isImportate, String codiceSoftwareScad, DomandeStcFilter domandeStcFilter) {

	return domandestcDAO.countScadenzarioDomandePervenuteSTC(isImportate, codiceSoftwareScad, domandeStcFilter);
    }
}
