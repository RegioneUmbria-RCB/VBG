package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatipresenzeDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiElabpresenze;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.MercatiDService;
import it.gruppoinit.pal.gp.core.service.MercatiElabpresenzeService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.PresenzeDaConsolidareHelper;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MercatipresenzeDServiceImpl extends BaseServiceImpl<MercatipresenzeD, PkId> implements MercatipresenzeDService {

    private MercatipresenzeDDAO mercatipresenzeDDAO;
    private MercatiDService mercatiDService;
    private AnagrafeService anagrafeService;
    private MercatipresenzeTService mercatipresenzeTService;
    private MercatiElabpresenzeService mercatiElabpresenzeService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setMercatiElabpresenzeService(MercatiElabpresenzeService mercatiElabpresenzeService) {

	this.mercatiElabpresenzeService = mercatiElabpresenzeService;
    }

    @Autowired
    public void setMercatipresenzeTService(MercatipresenzeTService mercatipresenzeTService) {

	this.mercatipresenzeTService = mercatipresenzeTService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setMercatiDService(MercatiDService mercatiDService) {

	this.mercatiDService = mercatiDService;
    }

    @Autowired
    public void setMercatipresenzeDDAO(MercatipresenzeDDAO mercatipresenzeDDAO) {

	this.mercatipresenzeDDAO = mercatipresenzeDDAO;
    }

    @Override
    protected Class<MercatipresenzeD> getEntityClass() {

	return MercatipresenzeD.class;
    }

    @Override
    public void delete(MercatipresenzeD entity) {

	this._delete(entity, true);
    }

    @Override
    public List<MercatipresenzeD> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return mercatipresenzeDDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public MercatipresenzeD findById(PkId id) {

	// §§§BEGIN§§§
	return mercatipresenzeDDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(MercatipresenzeD entity) {

	this._insert(entity, true);
    }

    @Override
    public void update(MercatipresenzeD entity) {

	this._update(entity, true);
    }

    private void dataIntegration(MercatipresenzeD entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il Bean MercatipresenzeD non può essere nullo");
	}
	if (entity.getNumeropresenze() == null) {
	    entity.setNumeropresenze(Integer.valueOf(0));
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(MercatipresenzeD entity) {

	MercatiD posteggio = mercatiDService.bindDomainObject(entity.getPosteggio(), PkId.class, "id.codice");
	entity.setPosteggio(posteggio);
	Anagrafe concessionario = anagrafeService.bindDomainObject(entity.getConcessionario(), PkId.class, "id.codice");
	entity.setConcessionario(concessionario);
	Anagrafe occupante = anagrafeService.bindDomainObject(entity.getOccupante(), PkId.class, "id.codice");
	entity.setOccupante(occupante);
	MercatipresenzeT giorno = mercatipresenzeTService.bindDomainObject(entity.getMercatiPresenzeT(), PkId.class, "id.codice");
	entity.setMercatiPresenzeT(giorno);
    }

    @Override
    public MercatipresenzeD findByMercatiPresenzeTAndPosteggio(MercatipresenzeT giornoMercato, MercatiD posteggio) {

	// §§§BEGIN§§§
	return mercatipresenzeDDAO.findByMercatiPresenzeTAndPosteggio(giornoMercato, posteggio);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<MercatipresenzeDDTO> findListaPosteggi(MercatipresenzeT giorno) {

	// §§§BEGIN§§§
	return mercatipresenzeDDAO.findListaPosteggi(giorno);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public MercatipresenzeD findByMercatiPosteggio(MercatiD posteggio) {

	// §§§BEGIN§§§
	return mercatipresenzeDDAO.findByMercatiPosteggio(posteggio);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<MercatipresenzeD> findByAnagrafeOccupante(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafeOccupante: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("concessionarioId", codiceAnagrafe, Integer.class));
	filterTable.addRestriction(fr);
	return mercatipresenzeDDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<MercatipresenzeD> findByAnagrafeConcessionario(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafeConcessionario: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("concessionarioId", codiceAnagrafe, Integer.class));
	filterTable.addRestriction(fr);
	return mercatipresenzeDDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public MercatipresenzeD findSpuntistaNoPosteggio(MercatipresenzeT giorno, Integer codiceAnagrafe) {

	// §§§BEGIN§§§
	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafeOccupante: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiPresenzeTId", giorno.getId().getCodice(), Integer.class));
	fr.addFilterField(FilterUtils.equals("occupanteId", codiceAnagrafe, Integer.class));
	fr.addFilterField(FilterUtils.isNull("posteggioId"));
	filterTable.addRestriction(fr);
	List<MercatipresenzeD> list = mercatipresenzeDDAO.findByFilterTable(filterTable);
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<MercatipresenzeD> findSpuntisti(MercatipresenzeT giorno) {

	// §§§BEGIN§§§
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiPresenzeTId", giorno.getId().getCodice(), Integer.class));
	fr.addFilterField(FilterUtils.equals("spuntista", "true", Boolean.class));
	fr.addFilterField(FilterUtils.isNotNull("occupante"));
	filterTable.addRestriction(fr);
	return mercatipresenzeDDAO.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public boolean isFirstAccess(MercatipresenzeT mercatipresenzeT) {

	boolean success = false;
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiPresenzeT", mercatipresenzeT, MercatipresenzeT.class));
	filterTable.addRestriction(fr);
	success = mercatipresenzeDDAO.existsRecords(filterTable);
	return !success;
    }

    @Override
    public boolean isCloseMarketDayAllowed(MercatipresenzeT mercatipresenzeT) {

	boolean success = false;
	// Set<MercatipresenzeD> presenze = mercatipresenzeT.getListaPresenze();
	// controllo se il giorno di mercato è stato chiuso
	if (!mercatipresenzeT.getFlagPresenze()) {
	    // controlla se almeno un posteggio è stato assegnato
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("mercatiPresenzeTId", mercatipresenzeT.getId().getCodice(), Integer.class));
	    fr.addFilterField(FilterUtils.isNotNull("occupante"));
	    ft.addRestriction(fr);
	    return mercatipresenzeDDAO.existsRecords(ft);
	}
	return success;
    }

    @Override
    public List<MercatipresenzeD> findListaPresentiSenzaPosteggio(MercatipresenzeT giorno) {

	return mercatipresenzeDDAO.findListaPresentiSenzaPosteggio(giorno);
    }

    @Override
    public List<MercatipresenzeD> findByMercatiPresenzeT(Integer codiceMercatopresenzaT) {

	// §§§BEGIN§§§
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiPresenzeTId", codiceMercatopresenzaT, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("codiceposteggio", "posteggio"));
	return mercatipresenzeDDAO.findByFilterTable(ft);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public boolean updateConsolidaPresenzePerAnno(Integer codiceMercato, Integer anno) {

	List<PresenzeDaConsolidareHelper> dtos = mercatipresenzeDDAO.findPresenzeDaConsolidarePerAnno(codiceMercato, anno);
	mercatipresenzeDDAO.updateAggiornaAZeroTutteLePresenze(codiceMercato, anno);
	for (PresenzeDaConsolidareHelper helper : dtos) {
	    MercatipresenzeD presenza = mercatipresenzeDDAO.findUltimaPresenzaPerMercatoAnagrafeAndAutorizzazione(codiceMercato, anno,
		    helper.getCodiceanagrafe(), helper.getCodiceautorizzazione());
	    if (presenza != null) {
		presenza.setNumeropresenze(1);
		presenza.setProprietario(0);
		if (helper.getNumeropresenzeproprietario() != null) {
		    if (helper.getNumeropresenzeproprietario().intValue() > 0) {
			presenza.setProprietario(1);
		    }
		}
		this._update(presenza, false);
		mercatipresenzeDDAO.flush();
	    }
	}
	// AGGIORNA I RECORD DI MERCATI_ELAB_PRESENZE
	mercatiElabpresenzeService.updateConsolidaAnnoMercato(codiceMercato, anno);
	return true;
    }

    private void _update(MercatipresenzeD entity, boolean eliminaElaborazioneAnnioConsolidati) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    if (eliminaElaborazioneAnnioConsolidati) {
		eliminaAnniElaborazione(entity);
	    }
	    mercatipresenzeDDAO.update(entity);
	}
	// §§§END§§§
    }

    private void _insert(MercatipresenzeD entity, boolean eliminaElaborazioneAnnioConsolidati) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatipresenzeDDAO.insert(entity);
	    if (eliminaElaborazioneAnnioConsolidati) {
		eliminaAnniElaborazione(entity);
	    }
	}
	// §§§END§§§
    }

    private void eliminaAnniElaborazione(MercatipresenzeD entity) {

	MercatipresenzeT mt = entity.getMercatiPresenzeT();
	List<MercatiElabpresenze> melps = mercatiElabpresenzeService.findByMercatiAndAnno(mt.getMercato().getId().getCodice(), mt.getAnno());
	for (MercatiElabpresenze melp : melps) {
	    Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    if (r != null) {
		String messaggio = "MERCATI_CONSOLIDA_PRESENZE: Il responsabile " + r.toString()
			+ " ha aggiornato le presenze di un mercato che era stato consolidato con codice: " + mt.getMercato().getId().getCodice()
			+ ", anno: " + mt.getAnno();
		LoggerCancellazioni.log(messaggio);
	    }
	    mercatiElabpresenzeService.delete(melp);
	}
    }

    private void _delete(MercatipresenzeD entity, boolean eliminaElaborazioneAnnioConsolidati) {

	// §§§BEGIN§§§
	if (eliminaElaborazioneAnnioConsolidati) {
	    eliminaAnniElaborazione(entity);
	}
	mercatipresenzeDDAO.delete(entity);
	// §§§END§§§
    }
}
