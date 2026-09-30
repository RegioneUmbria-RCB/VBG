package it.gruppoinit.pal.gp.core.features.oggetti;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.OggettiStoricoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.OggettiStorico;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.TipoDocumentoPratica;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author
 */
@Service
public class OggettiStoricoServiceImpl extends BaseServiceImpl<OggettiStorico, PkId> implements OggettiStoricoService {

    private static final Logger log = LoggerFactory.getLogger(OggettiStoricoServiceImpl.class);
    private OggettiStoricoDAO oggettistoricoDAO;
    private IstanzeallegatiService istanzeallegatiService;
    private DocumentiistanzaService documentiistanzaService;
    private OggettiService oggettiService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setOggettiStoricoDAO(OggettiStoricoDAO oggettistoricoDAO) {

	this.oggettistoricoDAO = oggettistoricoDAO;
    }

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setIstanzeallegatiService(IstanzeallegatiService istanzeallegatiService) {

	this.istanzeallegatiService = istanzeallegatiService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Override
    protected Class<OggettiStorico> getEntityClass() {

	return OggettiStorico.class;
    }

    @Override
    public List<OggettiStorico> findAll(Integer firstResult, Integer maxResult) {

	return oggettistoricoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OggettiStorico entity) {

	if (validateEntity(entity)) {
	    oggettistoricoDAO.insert(entity);
	}
    }

    @Override
    public OggettiStorico findById(PkId id) {

	return oggettistoricoDAO.findById(id);
    }

    @Override
    public void update(OggettiStorico entity) {

	if (validateEntity(entity)) {
	    oggettistoricoDAO.update(entity);
	}
    }

    @Override
    public void delete(OggettiStorico entity) {

	if (isDeleteAllowed(entity)) {
	    oggettistoricoDAO.delete(entity);
	}
    }

    @Override
    public void updateSostituisciOggetto(Integer codiceIstanza, Integer codiceOggettoDaSostituire, Integer codiceOggettoNuovo,
	    TipoDocumentoPratica tipoDocumentoPratica) {

	// FASE DI RICERA
	List<Istanzeallegati> istanzeallegatis = new ArrayList<Istanzeallegati>();
	List<Documentiistanza> documentiistanzas = new ArrayList<Documentiistanza>();
	switch (tipoDocumentoPratica) {
	case DOC_ENDO:
	    istanzeallegatis = istanzeallegatiService.findByIstanzaAndOggetto(codiceIstanza, codiceOggettoDaSostituire);
	    break;
	case DOC_ISTANZA:
	    documentiistanzas = documentiistanzaService.findByIstanzaAndOggetto(codiceIstanza, codiceOggettoDaSostituire);
	    break;
	default:
	    break;
	}
	// FASE DI SOSTITUZIONE E STORICIZZAZIONE
	if (!istanzeallegatis.isEmpty()) {
	    Oggetti oggettoNuovoDocEndo = oggettiService.findById(new PkId(codiceOggettoNuovo));
	    Oggetti oggettoVecchioDocEndo = null;
	    OggettiStorico oggettiStorico = null;
	    for (Istanzeallegati istanzeallegati : istanzeallegatis) {
		// Sostituisci
		oggettoVecchioDocEndo = istanzeallegati.getOggetto();
		istanzeallegati.setOggetto(oggettoNuovoDocEndo);
		//Setto il documento come da verificare
		istanzeallegati.setControllook(null);
		log.debug("updateSostituisciOggetto#Allegati endo: Vecchio oggetto {}, nuovo oggetto {}",
			new Object[] { oggettoNuovoDocEndo.getId().getCodice(), oggettoNuovoDocEndo.getId().getCodice() });
		istanzeallegatiService.update(istanzeallegati);
		//Storicizzazione
		oggettiStorico = new OggettiStorico();
		oggettiStorico.setOggettoNuovo(oggettoNuovoDocEndo);
		oggettiStorico.setOggettoVecchio(oggettoVecchioDocEndo);
		oggettiStorico.setDataSostituzione(new Date());
		//		// devo settare una nota di default?
		//		oggettiStorico.setNote("");
		this.insert(oggettiStorico);
	    }
	} else {
	    if (!documentiistanzas.isEmpty()) {
		Oggetti oggettoNuovoDocIstanza = oggettiService.findById(new PkId(codiceOggettoNuovo));
		Oggetti oggettoVecchioDocIstanza = null;
		OggettiStorico oggettiStoricoIstanza = null;
		for (Documentiistanza documentiistanza : documentiistanzas) {
		    // Sostituisci
		    oggettoVecchioDocIstanza = documentiistanza.getOggetto();
		    documentiistanza.setOggetto(oggettoNuovoDocIstanza);
		    //Setto il documento come da verificare
		    documentiistanza.setControllook(null);
		    log.debug("updateSostituisciOggetto#Documento istanza: Vecchio oggetto {}, nuovo oggetto {}",
			    new Object[] { oggettoVecchioDocIstanza.getId().getCodice(), oggettoNuovoDocIstanza.getId().getCodice() });
		    documentiistanzaService.update(documentiistanza);
		    //Storicizzazione
		    oggettiStoricoIstanza = new OggettiStorico();
		    oggettiStoricoIstanza.setOggettoNuovo(oggettoNuovoDocIstanza);
		    oggettiStoricoIstanza.setOggettoVecchio(oggettoVecchioDocIstanza);
		    oggettiStoricoIstanza.setDataSostituzione(new Date());
		    //			// devo settare una nota di default?
		    //			oggettiStorico.setNote("");
		    this.insert(oggettiStoricoIstanza);
		}
	    }
	}
    }

    @Override
    public List<OggettiStorico> findbyCodiceOggetto(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "oggettoNuovo", Integer.class));
	ft.addRestriction(fr);
	return oggettistoricoDAO.findByFilterTable(ft);
    }

    @Override
    public boolean isStoricizzato(Integer codiceOggetto) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceOggetto, "oggettoNuovo", Integer.class));
	ft.addRestriction(fr);
	return oggettistoricoDAO.countRecord(ft) > 0;
    }

    @Override
    public List<OggettoStoricoBean> findStoricoBy(Integer codiceOggetto) {

	List<OggettoStoricoBean> oggettiStorici = new ArrayList<OggettoStoricoBean>();
	FilterTable ft = getFilterByOggettoNuovo(codiceOggetto);
	List<OggettiStorico> list = oggettistoricoDAO.findByFilterTable(ft);
	if (!list.isEmpty()) {
	    for (OggettiStorico oggetto : list) {
		oggettiStorici.add(new OggettoStoricoBean(oggetto));
		List<OggettoStoricoBean> oggettiCollegati = this.findStoricoBy(list.get(0).getOggettoVecchio().getId().getCodice());
		if (!oggettiCollegati.isEmpty()) {
		    oggettiStorici.addAll(oggettiCollegati);
		}
	    }
	}
	return oggettiStorici;
    }

    private FilterTable getFilterByOggettoNuovo(Integer codiceOggetto) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceOggetto, "oggettoNuovo", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("dataSostituzione"));
	ft.addOrder(FilterUtils.orderDesc("id.codice", "oggettoVecchio"));
	return ft;
    }

    @Override
    public Integer ripristina(Integer idOggettiStorico) {

	OggettiStorico storico = this.findById(new PkId(idOggettiStorico));
	Oggetti oggettoNuovo = this.oggettiService.findById(new PkId(storico.getOggettoNuovoId()));
	Oggetti oggettoVecchio = this.oggettiService.findById(new PkId(storico.getOggettoVecchioId()));
	//
	Responsabili r = (Responsabili) this.userSecurityService.getCurrentlyAuthenticatedUserDetails();
	LoggerRipristinoOggetto
		.log(Utilities.formatMessage("Ripristino del file {0} [codiceoggetto: {1}] rileggendo il contenuto dal file {2} [codiceoggetto: {3}]",
			storico.getOggettoNuovo().getNomefile(), storico.getOggettoNuovoId(), storico.getOggettoVecchio().getNomefile(),
			storico.getOggettoVecchioId()), r);
	//
	oggettoNuovo.setDimensioneFile(oggettoVecchio.getDimensioneFile());
	oggettoNuovo.setMetadatiTransient(oggettoVecchio.getMetadatiTransient());
	oggettoNuovo.setNomefile(oggettoVecchio.getNomefile());
	oggettoNuovo.setOggetto(oggettoVecchio.getOggetto());
	oggettoNuovo.setPercorso(oggettoVecchio.getPercorso());
	this.oggettiService.aggiornaSenzaStoricizzare(oggettoNuovo);
	// cerco se era presente un ulteriore passaggio storico
	List<OggettiStorico> passaggi = this.findbyCodiceOggetto(oggettoVecchio.getId().getCodice());
	for (OggettiStorico passaggio : passaggi) {
	    passaggio.setOggettoNuovo(oggettoNuovo);
	    this.update(passaggio);
	}
	// cancello il passaggio storico ripristinato
	this.delete(storico);
	//cancello dalla tabella oggetti l'oggetto sostituito
	this.oggettiService.delete(oggettoVecchio);
	return oggettoNuovo.getId().getCodice();
    }
}
