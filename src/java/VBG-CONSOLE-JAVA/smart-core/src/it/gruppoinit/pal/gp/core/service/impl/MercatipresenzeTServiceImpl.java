package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MercatipresenzeTDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiAnnoGiornoDTO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.CalendariomercatoParametri;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.Giorno;
import it.gruppoinit.pal.gp.core.domain.Manifestazioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Riepilogomercato;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MercatiDService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MercatipresenzeTServiceImpl extends BaseServiceImpl<MercatipresenzeT, PkId> implements MercatipresenzeTService {

    private static final Logger log = LoggerFactory.getLogger(MercatipresenzeTServiceImpl.class);
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatipresenzeTDAO mercatipresenzeTDAO;
    @Autowired
    private MercatipresenzeDService mercatipresenzeDService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private MercatiConfigurazioneService mercatiConfigurazioneService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private MercatiDService mercatiDService;

    @Override
    protected Class<MercatipresenzeT> getEntityClass() {

	return MercatipresenzeT.class;
    }

    @Override
    public void delete(MercatipresenzeT entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    entity = this.findById(new PkId(entity.getId().getCodice()));
	    mercatipresenzeTDAO.delete(entity);
	}
    }

    protected void childDelete(MercatipresenzeT entity) {

	List<MercatipresenzeDDTO> mds = mercatipresenzeDService.findListaPosteggi(entity);
	int i = 0;
	for (MercatipresenzeDDTO mddto : mds) {
	    MercatipresenzeD md = mercatipresenzeDService.findById(new PkId(mddto.getId().getCodice()));
	    mercatipresenzeDService.delete(md);
	    if (i == 20) {
		i = 0;
		mercatipresenzeTDAO.flush();
		mercatipresenzeTDAO.clear();
	    }
	    i++;
	}
    }

    /**
     * metodo per verificare se la cancellazione di un giorno di mercato è permessa.<br />
     * la cancellazione di un giorno è permessa solo se il giorno non è storicizzato e se il mercato non è storicizzato
     * 
     * @param entity
     * @return
     */
    protected boolean isDeleteAllowed(MercatipresenzeT entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// una giornata di calendario è cancellabile solo se non ho gestito le presenze
	// FIXME modificare controllando solo che nono sia storicizzata o non abbia inserito le reg cont degli
	// spuntisti
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String day = sdf.format(entity.getDataRegistrazione());
	//	List<MercatipresenzeDDTO> set = mercatipresenzeDService.findListaPosteggi(entity);
	//	for (MercatipresenzeDDTO mercatipresenzeD : set) {
	//	    AnagrafeDTO occupante = mercatipresenzeD.getOccupante();
	//	    if (occupante != null) {
	//		if (occupante.getId().getCodice() != null) {
	//		    delete = false;
	//		    _ivs.add(new InvalidValue("errors.mercatipresenzet.cancellazione.presenze", entity.getClass(), "", day, entity));
	//		    delete = false;
	//		}
	//	    }
	//	}
	if (entity.getFlagPresenze() || entity.getFlagPresenzeArchivio()) {
	    _ivs.add(new InvalidValue("errors.mercatipresenzet.cancellazione.giorno", entity.getClass(), "", day, entity));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<MercatipresenzeT> findAll(Integer firstResult, Integer maxResult) {

	return mercatipresenzeTDAO.findAll(null, null);
    }

    @Override
    public MercatipresenzeT findById(PkId id) {

	return mercatipresenzeTDAO.findById(id);
    }

    @Override
    public void insert(MercatipresenzeT entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatipresenzeTDAO.insert(entity);
	}
    }

    @Override
    public void update(MercatipresenzeT entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatipresenzeTDAO.update(entity);
	}
    }

    private void dataIntegration(MercatipresenzeT entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il Documento passato è nullo");
	}
	if (entity.getFlagPresenze() == null) {
	    entity.setFlagPresenze(false);
	}
	if (entity.getFlagPresenzeArchivio() == null) {
	    entity.setFlagPresenzeArchivio(false);
	}
	if (entity.getFlagRegfatte() == null) {
	    entity.setFlagRegfatte(false);
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(MercatipresenzeT entity) {

	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
	Responsabili responsabile = responsabiliService.bindDomainObject(entity.getResponsabile(), PkId.class, "id.codice");
	entity.setResponsabile(responsabile);
	Responsabili utenteSistema = responsabiliService.bindDomainObject(entity.getUtenteSistema(), PkId.class, "id.codice");
	entity.setUtenteSistema(utenteSistema);
	Mercati mercati = mercatiService.bindDomainObject(entity.getMercato(), PkId.class, "id.codice");
	entity.setMercato(mercati);
	MercatiUso mercatiUso = mercatiUsoService.bindDomainObject(entity.getMercatoUso(), PkId.class, "id.codice");
	entity.setMercatoUso(mercatiUso);
    }

    @Override
    public List<MercatipresenzeT> findMercatipresenzeTByMercatiAndMercatiUso(Mercati mercati, MercatiUso mercatiUso, Integer anno) {

	return mercatipresenzeTDAO.findMercatipresenzeTByMercatiAndMercatiUso(mercati, mercatiUso, anno);
    }

    public List<MercatipresenzeT> findMercatipresenzetFiereByMercatiAndMercatiUso(Mercati mercati, MercatiUso mercatiUso, Integer anno) {

	return mercatipresenzeTDAO.findMercatipresenzetFiereByMercatiAndMercatiUso(mercati, mercatiUso, anno);
    }

    @Override
    public boolean findDay(List<MercatipresenzeT> list, Calendar date) {

	return mercatipresenzeTDAO.findDay(list, date);
    }

    @Override
    public MercatipresenzeT findByDataregistrazioneAndMercatoAndMercatoUso(Calendar date, Mercati mercati, MercatiUso mercatiUso) {

	return mercatipresenzeTDAO.findByDataregistrazioneAndMercatoAndMercatoUso(date, mercati, mercatiUso);
    }

    @Override
    public List<MercatipresenzeT> findByMercatoAndGroupByAnnoAndMercatoUso(Mercati mercati) {

	return mercatipresenzeTDAO.findByMercatoAndGroupByAnnoAndMercatoUso(mercati);
    }

    @Override
    public List<MercatipresenzeT> findByMercatoAndAnnoGroupByMercatoUso(Mercati mercati, Integer anno) {

	return mercatipresenzeTDAO.findByMercatoAndAnnoGroupByMercatoUso(mercati, anno);
    }

    @Override
    public void segnaPresentiTuttiConcessionari(MercatipresenzeT giorno) {

	// §§§BEGIN§§§
	// §§§END§§§
    }

    private double pageSize = 50;

    @Override
    public void inserisciTuttiConcessionari(MercatipresenzeT giorno) {

	// §§§BEGIN§§§
	// recupero la lista delle presenze
	// §§§END§§§
    }

    @Override
    public void recuperaCategoriaMerceologicaConcessionario(MercatipresenzeD posteggio, MercatiConfigurazione mercatiConfigurazione) {

	// §§§BEGIN§§§
	// §§§END§§§
    }

    @Override
    public boolean checkSeUsareCatMerc(Manifestazioni tipoManifestazione) {

	// §§§BEGIN§§§
	log.debug("checkSeUsareCatMerc: cerco la configurazione dei mercati per il software {}", ORMHelper.getSoftware());
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findConfigurazione();
	log.debug("checkSeUsareCatMerc: mercatiConfigurazione={}", mercatiConfigurazione);
	String catMercUso = "";
	if (StringUtils.isNotBlank(mercatiConfigurazione.getCatMercUso())) {
	    catMercUso = mercatiConfigurazione.getCatMercUso();
	} else {
	    log.debug("checkSeUsareCatMerc:Il mercato non ha configurata una categoria merceologica ");
	    return false;
	}
	Integer tipoMan = tipoManifestazione.getCodice();
	log.debug("checkSeUsareCatMerc: tipoMan={}", tipoMan);
	Integer tipoManRef = -1;
	Dyn2Campi dynCatMerc = mercatiConfigurazione.getDyn2CampiByFkMerconfDyn2campiCm();
	if (dynCatMerc != null) {
	    if (StringUtils.isNotBlank(catMercUso)) {
		log.debug("checkSeUsareCatMerc: categoria Merceologica Uso={}", catMercUso);
		if (catMercUso.equals(WebConstants.MERCATI_CAT_MERC_USO_MERCATI)) {
		    tipoManRef = WebConstants.MANIFESTAZIONE_MERCATO;
		}
		if (catMercUso.equals(WebConstants.MERCATI_CAT_MERC_USO_FIERE)) {
		    tipoManRef = WebConstants.MANIFESTAZIONE_FIERA;
		}
		if (catMercUso.equals(WebConstants.MERCATI_CAT_MERC_USO_ENTRAMBI)) {
		    tipoManRef = tipoMan;
		}
	    }
	}
	return tipoMan.intValue() == tipoManRef.intValue();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return false;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void segnaPresenzaSpuntista(MercatipresenzeT giorno, Integer idPosteggio, Anagrafe spuntista, Integer idAut, String catMerc) {

	// §§§BEGIN§§§
	// §§§END§§§
    }

    @Override
    public void segnaPresenzaSpuntistaNoPosteggio(MercatipresenzeT giorno, Anagrafe spuntista, Integer idAut, String catMerc) {

	// §§§BEGIN§§§
	// §§§END§§§
    }

    @Override
    public void inserisciCalendario(CalendariomercatoParametri calendariomercatoParametri, Mercati mercato, LoggedUser loggedUser) {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("Inserimento calendario...");
	}
	Giorno data = null;
	MercatipresenzeT giornoMercato = null;
	Responsabili utenteLoggato = null;
	List<Giorno> calendarioMercato = calendariomercatoParametri.getGiorniMercato();
	for (Iterator<Giorno> iterator = calendarioMercato.iterator(); iterator.hasNext();) {
	    data = (Giorno) iterator.next();
	    giornoMercato = new MercatipresenzeT();
	    giornoMercato.setAnno(calendariomercatoParametri.getAnno());
	    giornoMercato.setDataRegistrazione(data.getData().getTime());
	    giornoMercato.setMercatoUso(calendariomercatoParametri.getMercatiUso());
	    giornoMercato.setMercato(mercato);
	    giornoMercato.setDescrizione(calendariomercatoParametri.getAnno().toString() + " " + mercato.getDescrizione() + " "
		    + calendariomercatoParametri.getMercatiUso().getDescrizione());
	    giornoMercato.setSoftware(mercato.getSoftware());
	    utenteLoggato = new Responsabili();
	    utenteLoggato.getId().setCodice(loggedUser.getCodiceResponsabile());
	    utenteLoggato.setResponsabile(loggedUser.getResponsabile());
	    giornoMercato.setResponsabile(utenteLoggato);
	    this.insert(giornoMercato);
	}
	if (log.isDebugEnabled()) {
	    log.debug("Inserimento calendario...done!");
	}
	// §§§END§§§
    }

    @Override
    public void deleteCalendario(Mercati mercato, MercatiUso uso, Integer anno) {

	// §§§BEGIN§§§
	// recupero i giorni del calendario mercato
	List<MercatipresenzeT> calendarioMercato = this.findMercatipresenzeTByMercatiAndMercatiUso(mercato, uso, anno);
	// elimino i giorni del calendario mercato
	for (MercatipresenzeT giornoMercato : calendarioMercato) {
	    this.delete(giornoMercato);
	}
	// §§§END§§§
    }

    @Override
    public boolean verificaGestionePresenze(Mercati mercati, MercatiUso mercatiUso, Integer anno) {

	return mercatipresenzeTDAO.verificaGestionePresenze(mercati, mercatiUso, anno);
    }

    @Override
    public boolean verificaMercatoStoricizzato(Mercati mercati, MercatiUso mercatiUso, Integer anno) {

	// §§§BEGIN§§§
	return mercatipresenzeTDAO.verificaMercatoStoricizzato(mercati, mercatiUso, anno);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return false;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void closeMarketDay(MercatipresenzeT mercatipresenzeT) {

	// §§§BEGIN§§§
	if (!mercatipresenzeDService.isCloseMarketDayAllowed(mercatipresenzeT)) {
	    throw new RuntimeException("Impossibile chiudere il giorno di mercato");
	} else {
	    mercatipresenzeT.setFlagPresenze(true);
	    mercatipresenzeTDAO.update(mercatipresenzeT);
	}
	// §§§END§§§
    }

    @Override
    public List<Riepilogomercato> findByFilterMercatoOrAnnoGroupByMercatoUso(Mercati mercati, Integer anno) {

	// §§§BEGIN§§§
	return mercatipresenzeTDAO.findByMercatoOrAnnoGroupByMercatoUso(mercati, anno);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<MercatipresenzeT> findAnniMercatiPresenti() {

	// §§§BEGIN§§§
	return mercatipresenzeTDAO.findAnniMercatiPresenti();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void apriGiornoMercato(MercatipresenzeT giorno) {

	// §§§BEGIN§§§
	giorno.setFlagPresenze(false);
	mercatipresenzeTDAO.update(giorno);
	// §§§END§§§
    }

    @Override
    public List<MercatiAnnoGiornoDTO> findUltimoGiornoFieraPerAnno(Mercati mercato, MercatiUso uso, Integer anno) {

	// §§§BEGIN§§§
	return mercatipresenzeTDAO.findUltimoGiornoFieraPerAnno(mercato, uso, anno);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public String[] findComboCategorieMerceologiche() {

	// §§§BEGIN§§§
	String[] catMercList = null;
	MercatiConfigurazioneId mercatiConfigurazioneId = new MercatiConfigurazioneId(softwareService.findById(ORMHelper.getSoftware()).getCodice());
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findById(mercatiConfigurazioneId);
	Dyn2Campi dyn2CatMerc = mercatiConfigurazione.getDyn2CampiByFkMerconfDyn2campiCm();
	if (dyn2CatMerc != null) {
	    String tipoDato = dyn2CatMerc.getTipodato();
	    Set<Dyn2Campiproprieta> dyn2CatMercProps = dyn2CatMerc.getDyn2Campiproprietas();
	    String catMercElementiLista = "";
	    if (tipoDato.equals("Lista")) {
		for (Dyn2Campiproprieta dyn2Campiproprieta : dyn2CatMercProps) {
		    String p = dyn2Campiproprieta.getId().getProprieta();
		    if (p.equals("ElementiLista")) {
			catMercElementiLista = dyn2Campiproprieta.getValore();
		    }
		}
	    }
	    catMercList = catMercElementiLista.split(";");
	}
	return catMercList;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void chiudiAnnoMercato(Mercati mercato, MercatiUso uso, Integer anno) {

	// §§§BEGIN§§§
	if (this.verificaGestionePresenze(mercato, uso, anno)) {
	    if (!this.verificaMercatoStoricizzato(mercato, uso, anno)) {
		// itero su tutte le giornate di mercato dell'anno e aggiorno il campo FLAG_PRESENZE_ARCHIVIO
		List<MercatipresenzeT> giorni = this.findMercatipresenzeTByMercatiAndMercatiUso(mercato, uso, anno);
		for (MercatipresenzeT giorno : giorni) {
		    giorno.setFlagPresenzeArchivio(true);
		    this.update(giorno);
		}
	    } else {
		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
		InvalidValue iv = new InvalidValue(getMessageFromBundle("service_error.mercato_chiuso", null), MercatipresenzeT.class, "", null, null);
		_ivs.add(iv);
		this.throwValidationMessages(_ivs);
	    }
	} else {
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    InvalidValue iv = new InvalidValue(getMessageFromBundle("service_error.giorni_mercato_non_chiusi", null), MercatipresenzeT.class, "",
		    null, null);
	    _ivs.add(iv);
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
    }

    @Override
    public Alberoproc findInterventoConcessionari(Mercati mercato, MercatiUso uso) {

	// §§§BEGIN§§§
	return null;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void recuperaAutorizzazioneConcessionario(MercatipresenzeT giorno, MercatipresenzeD mercatipresenzeD,
	    MercatiConfigurazione mercatiConfigurazione, Integer tipoManifestazione) {

	// §§§BEGIN§§§
	// §§§END§§§
    }

    @Override
    public List<Integer> findAnniDaConsolidare(Integer codiceMercato) {

	return mercatipresenzeTDAO.findAnniDaConsolidare(codiceMercato);
    }

    @Override
    public List<MercatipresenzeT> findByMercatoAndMercatoUsoAndDateInterval(Integer codiceMercato, Integer codiceUso, Calendar date, Calendar dataFine) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatoId", codiceMercato, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatoUsoId", codiceUso, Integer.class));
	fr.addFilterField(FilterUtils.greater("dataRegistrazione", date.getTime(), Date.class));
	fr.addFilterField(FilterUtils.smallerEqual("dataRegistrazione", dataFine.getTime(), Date.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataRegistrazione"));
	return mercatipresenzeTDAO.findByFilterTable(ft);
    }
}
