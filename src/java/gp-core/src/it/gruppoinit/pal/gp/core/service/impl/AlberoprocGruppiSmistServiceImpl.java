package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocGruppiSmistDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocGruppiSmist;
import it.gruppoinit.pal.gp.core.domain.GruppiEndoprocedimentiT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.helper.GruppiSmistHelper;
import it.gruppoinit.pal.gp.core.domain.helper.GruppiSmistamentoClassiHelper;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocGruppiSmistService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.GruppiEndoprocedimentiTService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlberoprocGruppiSmistServiceImpl extends BaseServiceImpl<AlberoprocGruppiSmist, PkId> implements AlberoprocGruppiSmistService {

    private AlberoprocGruppiSmistDAO alberoprocGruppiSmistDAO;
    private TipiprocedureService tipiprocedureService;
    private GruppiEndoprocedimentiTService gruppiEndoprocedimentiTService;
    private AlberoprocService alberoprocService;
    private SoftwareService softwareService;

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setGruppiEndoprocedimentiTService(GruppiEndoprocedimentiTService gruppiEndoprocedimentiTService) {

	this.gruppiEndoprocedimentiTService = gruppiEndoprocedimentiTService;
    }

    @Autowired
    public void setTipiprocedureService(TipiprocedureService tipiprocedureService) {

	this.tipiprocedureService = tipiprocedureService;
    }

    @Autowired
    public void setAlberoprocGruppiSmistDAO(AlberoprocGruppiSmistDAO alberoprocGruppiSmistDAO) {

	this.alberoprocGruppiSmistDAO = alberoprocGruppiSmistDAO;
    }

    @Override
    public void insert(AlberoprocGruppiSmist entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    alberoprocGruppiSmistDAO.insert(entity);
	}
    }

    private void dataIntegration(AlberoprocGruppiSmist entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(AlberoprocGruppiSmist entity) {

	Tipiprocedure scia = tipiprocedureService.bindDomainObject(entity.getTipiprocedureSCIA(), PkId.class, "id.codice");
	entity.setTipiprocedureSCIA(scia);
	Tipiprocedure ordinario = tipiprocedureService.bindDomainObject(entity.getTipiprocedureOrdinario(), PkId.class, "id.codice");
	entity.setTipiprocedureOrdinario(ordinario);
	GruppiEndoprocedimentiT gruppo1 = gruppiEndoprocedimentiTService.bindDomainObject(entity.getGruppo1(), PkId.class, "id.codice");
	entity.setGruppo1(gruppo1);
	GruppiEndoprocedimentiT gruppo2 = gruppiEndoprocedimentiTService.bindDomainObject(entity.getGruppo2(), PkId.class, "id.codice");
	entity.setGruppo2(gruppo2);
	GruppiEndoprocedimentiT gruppo3 = gruppiEndoprocedimentiTService.bindDomainObject(entity.getGruppo3(), PkId.class, "id.codice");
	entity.setGruppo3(gruppo3);
	Alberoproc alberoproc = alberoprocService.bindDomainObject(entity.getAlberoproc(), PkId.class, "id.codice");
	entity.setAlberoproc(alberoproc);
    }

    @Override
    public void update(AlberoprocGruppiSmist entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    alberoprocGruppiSmistDAO.update(entity);
	}
    }

    @Override
    public void delete(AlberoprocGruppiSmist entity) {

	if (isDeleteAllowed(entity)) {
	    alberoprocGruppiSmistDAO.delete(entity);
	}
    }

    @Override
    public List<AlberoprocGruppiSmist> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocGruppiSmistDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "id.codice", DAOOrderTypeEnum.ASC);
    }

    @Override
    public AlberoprocGruppiSmist findById(PkId id) {

	return alberoprocGruppiSmistDAO.findById(id);
    }

    @Override
    protected Class<AlberoprocGruppiSmist> getEntityClass() {

	return AlberoprocGruppiSmist.class;
    }

    private void validateInsertConfigurazione(Set<Integer> codicigruppo, Integer codiceAlberoproc, Integer codiceProceduraScia,
	    Integer codiceProceduraOrdinario) {

	List<String> ivs = new ArrayList<String>();
	if (codicigruppo.isEmpty()) {
	    ivs.add("E' necessario indicare almeno un gruppo");
	} else {
	    boolean almenoUno = false;
	    for (Integer c : codicigruppo) {
		if (c != null) {
		    almenoUno = true;
		    break;
		}
	    }
	    if (!almenoUno) {
		ivs.add("E' necessario indicare almeno un gruppo");
	    }
	}
	if (codiceAlberoproc == null) {
	    ivs.add("E' necessario indicare almeno un intervento");
	}
	if (ivs.size() > 0) {
	    String msg = "Errore di validazione! Verificare le seguenti informazioni richieste: ";
	    for (String iv : ivs) {
		msg += "\n\t- " + iv;
	    }
	    throw new BusinessValidationException(msg);
	}
    }

    @Override
    public void insertConfigurazione(Set<Integer> codicigruppo, Integer codiceAlberoproc, Integer codiceProceduraScia,
	    Integer codiceProceduraOrdinario) {

	this.validateInsertConfigurazione(codicigruppo, codiceAlberoproc, codiceProceduraScia, codiceProceduraOrdinario);
	AlberoprocGruppiSmist entity = new AlberoprocGruppiSmist();
	Integer[] g = new Integer[codicigruppo.size()];
	g = codicigruppo.toArray(g);
	if (g.length >= 1 && g[0] != null) {
	    GruppiEndoprocedimentiT gruppo1 = gruppiEndoprocedimentiTService.findById(new PkId(g[0]));
	    entity.setGruppo1(gruppo1);
	}
	if (g.length >= 2 && g[1] != null) {
	    GruppiEndoprocedimentiT gruppo2 = gruppiEndoprocedimentiTService.findById(new PkId(g[1]));
	    entity.setGruppo2(gruppo2);
	}
	if (g.length == 3 && g[2] != null) {
	    GruppiEndoprocedimentiT gruppo3 = gruppiEndoprocedimentiTService.findById(new PkId(g[2]));
	    entity.setGruppo3(gruppo3);
	}
	if (codiceAlberoproc != null) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceAlberoproc));
	    entity.setAlberoproc(alberoproc);
	}
	if (codiceProceduraScia != null) {
	    Tipiprocedure tipiprocedureSCIA = tipiprocedureService.findById(new PkId(codiceProceduraScia));
	    entity.setTipiprocedureSCIA(tipiprocedureSCIA);
	}
	if (codiceProceduraOrdinario != null) {
	    Tipiprocedure tipiprocedureOrdinario = tipiprocedureService.findById(new PkId(codiceProceduraOrdinario));
	    entity.setTipiprocedureOrdinario(tipiprocedureOrdinario);
	}
	// verifica se già presenti configurazioni di questo tipo
	this.verificaConfigurazioneEsistente(codicigruppo);
	this.insert(entity);
    }

    private void verificaConfigurazioneEsistente(Set<Integer> codicigruppo) {

	if (codicigruppo.size() == 0) {
	    return;
	}
	GruppiSmistamentoClassiHelper helper = this.getGruppiSmistamentoClassiHelper();
	Map<Integer, Set<Integer>> mappaGruppiClassi = helper.getMappaGruppiClassi();
	Set<Integer> gruppiTrovati = mappaGruppiClassi.get(codicigruppo.size());
	if (gruppiTrovati != null && gruppiTrovati.size() > 0) {
	    if (helper.checkGruppiConCodici(gruppiTrovati, codicigruppo)) {
		throw new RuntimeException("Configurazione già esistente per i gruppi selezionati");
	    }
	}
    }

    @Override
    public List<AlberoprocGruppiSmist> findByGruppiEndot(Integer codiceGruppo, Integer firstResult, Integer maxResults) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.equals("gruppo1Id", codiceGruppo, Integer.class));
	fr.addFilterField(FilterUtils.equals("gruppo2Id", codiceGruppo, Integer.class));
	fr.addFilterField(FilterUtils.equals("gruppo3Id", codiceGruppo, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	return alberoprocGruppiSmistDAO.findByFilterTable(ft, firstResult, maxResults);
    }

    @Override
    public List<AlberoprocGruppiSmist> findByAlberoproc(Integer codiceAlberoproc, Integer firstResult, Integer maxResults) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("alberoprocId", codiceAlberoproc, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	return alberoprocGruppiSmistDAO.findByFilterTable(ft, firstResult, maxResults);
    }

    @Override
    public List<AlberoprocGruppiSmist> findByprocedura(Integer codiceProcedura, Integer firstResult, Integer maxResults) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.equals("tipiprocedureSCIAId", codiceProcedura, Integer.class));
	fr.addFilterField(FilterUtils.equals("tipiprocedureOrdinarioId", codiceProcedura, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	return alberoprocGruppiSmistDAO.findByFilterTable(ft, firstResult, maxResults);
    }

    @Override
    public int countBySoftware() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	return alberoprocGruppiSmistDAO.countRecord(ft);
    }

    @Override
    public GruppiSmistamentoClassiHelper getGruppiSmistamentoClassiHelper() {

	List<GruppiSmistHelper> conf = alberoprocGruppiSmistDAO.getConfigurazioni();
	GruppiSmistamentoClassiHelper helper = new GruppiSmistamentoClassiHelper(conf);
	return helper;
    }
}
