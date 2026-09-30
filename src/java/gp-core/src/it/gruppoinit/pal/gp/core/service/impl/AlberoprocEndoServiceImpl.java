package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocEndoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoId;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.helper.NatureEndoIncompatibili;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlberoprocEndoServiceImpl extends BaseServiceImpl<AlberoprocEndo, AlberoprocEndoId> implements AlberoprocEndoService {
    
    Logger log = LoggerFactory.getLogger(AlberoprocEndoServiceImpl.class);

    private AlberoprocEndoDAO alberoprocEndoDAO;
    private AlberoprocService alberoprocService;
    private InventarioprocedimentiService inventarioprocedimentiService;

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setAlberoprocEndoDAO(AlberoprocEndoDAO alberoprocEndoDAO) {

	this.alberoprocEndoDAO = alberoprocEndoDAO;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Override
    protected Class<AlberoprocEndo> getEntityClass() {

	return AlberoprocEndo.class;
    }

    @Override
    public void delete(AlberoprocEndo entity) {

	alberoprocEndoDAO.delete(entity);
    }

    @Override
    public List<AlberoprocEndo> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocEndoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public AlberoprocEndo findById(AlberoprocEndoId id) {

	return alberoprocEndoDAO.findById(id);
    }

    @Override
    public void insert(AlberoprocEndo entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    if (isInsertUpdateAllowed(entity, true)) {
		alberoprocEndoDAO.insert(entity);
	    }
	}
    }

    @Override
    public void update(AlberoprocEndo entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    if (isInsertUpdateAllowed(entity, false)) {
		alberoprocEndoDAO.update(entity);
	    }
	}
    }

    private void dataIntegration(AlberoprocEndo entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro alberoprocendo è vuoto");
	}
	if (entity.getFlagPrincipale() == null) {
	    entity.setFlagPrincipale(Boolean.FALSE);
	}
	if (entity.getFlagRichiesto() == null) {
	    entity.setFlagRichiesto(Boolean.FALSE);
	}
	if (entity.getFlagPubblica() == null) {
	    entity.setFlagPubblica(Boolean.FALSE);
	}
	if (entity.getFlagRichiestoBo() == null) {
	    entity.setFlagRichiestoBo(Boolean.FALSE);
	}
	if (entity.getFlagPrincipale() || entity.getFlagRichiesto()) {
	    entity.setFlagRichiestoBo(Boolean.TRUE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(AlberoprocEndo entity) {

	Alberoproc alberoproc = alberoprocService.bindDomainObject(entity.getAlberoproc(), PkId.class, "id.codice");
	entity.setAlberoproc(alberoproc);
	if (alberoproc != null) {
	    entity.getId().setFkscid(alberoproc.getId().getCodice());
	}
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.bindDomainObject(entity.getInventarioprocedimento(),
		PkId.class, "id.codice");
	entity.setInventarioprocedimento(inventarioprocedimenti);
	if (inventarioprocedimenti != null) {
	    entity.getId().setCodiceinventario(inventarioprocedimenti.getId().getCodice());
	}
    }

    @Override
    public List<AlberoprocEndo> findAllByAlberoproc(Integer codice) {

	return findAllByAlberoproc(codice, false);
    }

    @Override
    public List<AlberoprocEndo> findAllByAlberoprocAndFlagPubblica(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction alberoprocRestriction = new FilterRestriction();
	alberoprocRestriction.addFilterField(FilterUtils.equals("id.fkscid", codice, Integer.class));
	ft.addRestriction(alberoprocRestriction);
	FilterRestriction flagPubblicaRestriction = new FilterRestriction();
	flagPubblicaRestriction.addFilterField(FilterUtils.equals("flagPubblica", true, Boolean.class));
	ft.addRestriction(flagPubblicaRestriction);
	FilterRestriction flagDisabilitatoRestriction = new FilterRestriction();
	flagDisabilitatoRestriction.addFilterField(FilterUtils.notEquals("disabilitato", true, "inventarioprocedimento", Boolean.class));
	ft.addRestriction(flagDisabilitatoRestriction);
	ft.addOrder(FilterUtils.orderAsc("ordine", "inventarioprocedimento"));
	List<AlberoprocEndo> list = alberoprocEndoDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<AlberoprocEndo> findEndoprocedimentiHierarchy(Alberoproc alberoproc) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction softwareRestriction = new FilterRestriction();
	softwareRestriction.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "alberoproc.software", String.class));
	ft.addRestriction(softwareRestriction);
	FilterRestriction padri = new FilterRestriction();
	padri.setAndOrRestriction(AndOrRestriction.OR);
	String scCodice = alberoproc.getScCodice();
	for (int i = 0; i < scCodice.length(); i += 2) {
	    padri.addFilterField(FilterUtils.equals("scCodice", scCodice.substring(0, (scCodice.length() - i)), "alberoproc", String.class));
	}
	ft.addRestriction(padri);
	ft.addOrder(FilterUtils.orderDesc("flagPrincipale"));
	ft.addOrder(FilterUtils.orderAsc("procedimento", "inventarioprocedimento"));
	List<AlberoprocEndo> list = alberoprocEndoDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<AlberoprocEndo> findByEndoprocedimentiAndSoftware(List<Integer> listCodiciProcedimenti, String codiceSoftware,
	    boolean escludiAlberoprocDisabilitati) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	Integer[] arrayCodiciProcedimenti = listCodiciProcedimenti.toArray(new Integer[0]);
	restriction.addFilterField(FilterUtils.in("id.codice", arrayCodiciProcedimenti, "inventarioprocedimento", Integer.class));
	restriction.addFilterField(FilterUtils.equals("codice", codiceSoftware, "alberoproc.software", String.class));
	if (escludiAlberoprocDisabilitati) {
	    // BOCCI 2012-02-27 la lista degli endo procedimenti deve essere presa solamente se la voce dell'albero è attiva
	    restriction.addFilterField(FilterUtils.equals("scAttivo", Boolean.FALSE, "alberoproc", Boolean.class));
	}
	restriction.addFilterField(FilterUtils.equals("flagPrincipale", true, Boolean.class));
	filterTable.addRestriction(restriction);
	return alberoprocEndoDAO.findByFilterTable(filterTable);
    }

    private boolean isInsertUpdateAllowed(AlberoprocEndo entity, boolean isInsert) {

	boolean insertOrUpdate = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	List<AlberoprocEndo> alberoprocEndos = this.findAllByAlberoproc(entity.getAlberoproc().getId().getCodice());
	for (AlberoprocEndo alberoprocEndo : alberoprocEndos) {
	    if (isInsert) {
		if (alberoprocEndo.getId().getCodiceinventario().intValue() == entity.getId().getCodiceinventario().intValue()) {
		    _ivs.add(new InvalidValue("alert.alberoprocEndo.inventarioproc_presente", null, "", "", null));
		    insertOrUpdate = false;
		    break;
		}
	    }
	    if (BooleanUtils.toBoolean(entity.getFlagPrincipale()) == true) {
		if (!entity.getId().equals(alberoprocEndo.getId())) {
		    if (BooleanUtils.isTrue(alberoprocEndo.getFlagPrincipale())) {
			_ivs.add(new InvalidValue("alert.alberoprocEndo.flag_principale_presente", null, "", "", null));
			insertOrUpdate = false;
			break;
		    }
		}
	    }
	}
	if (!insertOrUpdate) {
	    this.throwValidationMessages(_ivs);
	}
	return insertOrUpdate;
    }

    @Override
    public List<AlberoprocEndo> findAllByAlberoproc(Integer codice, boolean excludeDisabled) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction alberoprocRestriction = new FilterRestriction();
	alberoprocRestriction.addFilterField(FilterUtils.equals("id.fkscid", codice, Integer.class));
	ft.addRestriction(alberoprocRestriction);
	if (excludeDisabled) {
	    FilterRestriction endoAbilitatiRestriction = new FilterRestriction();
	    endoAbilitatiRestriction.setAndOrRestriction(AndOrRestriction.OR);
	    endoAbilitatiRestriction.addFilterField(FilterUtils.isNull("disabilitato", "inventarioprocedimento"));
	    endoAbilitatiRestriction.addFilterField(FilterUtils.equals("disabilitato", Boolean.FALSE, "inventarioprocedimento", Boolean.class));
	    ft.addRestriction(endoAbilitatiRestriction);
	}
	ft.addOrder(FilterUtils.orderDesc("flagPrincipale"));
	ft.addOrder(FilterUtils.orderAsc("ordine", "inventarioprocedimento"));
	ft.addOrder(FilterUtils.orderAsc("procedimento", "inventarioprocedimento"));
	List<AlberoprocEndo> list = alberoprocEndoDAO.findByFilterTable(ft);
	return list;
    }
    
    @Override
    public List<NatureEndoIncompatibili> checkNatureEndoIncompatibili(Integer idAlberoProc, List<Integer> codiciEndo){
	
	List<NatureEndoIncompatibili> incompats = new ArrayList<NatureEndoIncompatibili>();
	Naturaendo naturaIntervento = null;
	//individuo la natura principale dell'intervento
	//se presente un'endo principale la natura principale dell'intervento sarà quella dell'endo principale
	if(codiciEndo == null){
	    codiciEndo = new ArrayList<Integer>();
	}
	Integer codiceEndoPrincipale = null;
	for (Integer codiceEndo : codiciEndo) {
	    AlberoprocEndo endoProc = alberoprocEndoDAO.findById(new AlberoprocEndoId(ORMHelper.getIdcomune(), idAlberoProc, codiceEndo));
	    if(endoProc != null && BooleanUtils.isTrue(endoProc.getFlagPrincipale())){
		if(endoProc.getInventarioprocedimento().getNaturaendo() != null && endoProc.getInventarioprocedimento().getNaturaendo().getBinariodipendenze() != null){
		    naturaIntervento = endoProc.getInventarioprocedimento().getNaturaendo();
		    codiceEndoPrincipale = codiceEndo;
		    break;
		}
		else{
		    log.warn("checkNatureendoIncompatibili() - l'endo principale (codice = {}) non ha nessuna natura impostata",new Object[]{codiceEndo});
		}
	    }
	}
	if(naturaIntervento == null){
	    Alberoproc treeNode = alberoprocService.findById(new PkId(idAlberoProc));
	    if(treeNode != null){
		Tipiprocedure tProc = treeNode.getTipoProcedura();
		if(tProc != null){
		    if(tProc.getNaturaendo() != null && tProc.getNaturaendo().getBinariodipendenze() != null){
			naturaIntervento = tProc.getNaturaendo();
		    }
		    else{
			log.warn("checkNatureendoIncompatibili() - il tipo procedura {} non ha nessuna natura impostata: impossibile determinare la natura principale dell'intervento",new Object[]{tProc.getProcedura()});
		    }
		}
		else{
		    log.warn("checkNatureendoIncompatibili() - la voce dell'albero avente codice {} non é associata a nessun tipo procedura: impossibile determinare la natura principale dell'intervento", new Object[]{idAlberoProc});
		}
	    }
	    else{
		log.warn("checkNatureendoIncompatibili() - non esiste nessuna voce dell'albero per il codice {}: impossibile determinare la natura principale dell'intervento", new Object[]{idAlberoProc});
	    }
	}
	if(naturaIntervento != null){
	    for (Integer codiceEndo : codiciEndo) {
		if(!codiceEndo.equals(codiceEndoPrincipale)){
		    Inventarioprocedimenti endo = inventarioprocedimentiService.findById(new PkId(codiceEndo));
		    if(endo != null){
			if(endo.getNaturaendo() != null){
			    if((naturaIntervento.getBinariodipendenze() & endo.getNaturaendo().getId().getCodice()) != endo.getNaturaendo().getId().getCodice()){
				NatureEndoIncompatibili incopatEndo = new NatureEndoIncompatibili();
				incopatEndo.setNaturaEndo(naturaIntervento);
				incopatEndo.setNaturaIncompatibile(endo.getNaturaendo());
				incopatEndo.setEndoIncompatibile(endo.getProcedimento());
				incompats.add(incopatEndo);
			    }
			}
		    }
		    else{
			log.warn("checkNatureendoIncompatibili() - non esiste nessun endoprocedimento con il codice {}:", new Object[]{codiceEndo});
		    }
		}
	    }
	}
	return incompats;
    }
}
