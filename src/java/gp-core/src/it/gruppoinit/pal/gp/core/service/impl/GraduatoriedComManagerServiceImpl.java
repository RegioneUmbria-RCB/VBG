package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.helper.PassoCreazioneComunicazionePerGraduatoriaEnum;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.GraduatoriedCom;
import it.gruppoinit.pal.gp.core.domain.GraduatorietCom;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeeventiFilter;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaFilter;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaRigheFilter;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.GraduatoriedComManagerService;
import it.gruppoinit.pal.gp.core.service.GraduatoriedComService;
import it.gruppoinit.pal.gp.core.service.GraduatoriedService;
import it.gruppoinit.pal.gp.core.service.GraduatorietComService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GraduatoriedComManagerServiceImpl implements GraduatoriedComManagerService {

    private static final Logger log = LoggerFactory.getLogger(GraduatoriedComManagerServiceImpl.class);
    private Dyn2CampiService dyn2CampiService;
    private GraduatoriedService graduatoriedService;
    private GraduatoriedComService graduatoriedComService;
    private GraduatorietComService graduatorietComService;
    private IstanzeService istanzeService;
    private IstanzeeventiService istanzeeventiService;
    private MovimentiService movimentiService;
    private DocumentiDaFirmareService documentiDaFirmareService;
    private OggettiService oggettiService;

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setDocumentiDaFirmareService(DocumentiDaFirmareService documentiDaFirmareService) {

	this.documentiDaFirmareService = documentiDaFirmareService;
    }

    @Autowired
    public void setGraduatoriedService(GraduatoriedService graduatoriedService) {

	this.graduatoriedService = graduatoriedService;
    }

    @Autowired
    public void setGraduatorietComService(GraduatorietComService graduatorietComService) {

	this.graduatorietComService = graduatorietComService;
    }

    @Autowired
    public void setDyn2CampiService(Dyn2CampiService dyn2CampiService) {

	this.dyn2CampiService = dyn2CampiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setGraduatoriedComService(GraduatoriedComService graduatoriedComService) {

	this.graduatoriedComService = graduatoriedComService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Override
    public PassoCreazioneComunicazionePerGraduatoriaEnum elabora(GraduatoriedCom graduatoriedCom,
	    PassoCreazioneComunicazionePerGraduatoriaEnum passoCreazioneComunicazionePerGraduatoriaEnum) {

	Istanze istanza = istanzeService.findById(new PkId(graduatoriedCom.getGraduatoried().getIstanza().getId().getCodice()));
	Movimenti movimento = null;
	if (graduatoriedCom.getMovimenti() != null) {
	    if (graduatoriedCom.getMovimenti().getId() != null) {
		movimento = movimentiService.findById(new PkId(graduatoriedCom.getMovimenti().getId().getCodice()));
	    }
	}
	setTrueEventi(graduatoriedCom, istanza);
	flussoElaborazione(graduatoriedCom, istanza, movimento, passoCreazioneComunicazionePerGraduatoriaEnum);
	return passoCreazioneComunicazionePerGraduatoriaEnum;
    }

    /**
     * Il metodo dovrà essere implementato per ogni classe specifica in base al numero di passi che deve compiere
     * l'elaborazione
     */
    private void flussoElaborazione(GraduatoriedCom graduatoriedCom, Istanze istanza, Movimenti movimento,
	    PassoCreazioneComunicazionePerGraduatoriaEnum passoCreazioneComunicazionePerGraduatoriaEnum) {

	if (log.isDebugEnabled()) {
	    log.debug("flussoElaborazione# {}", passoCreazioneComunicazionePerGraduatoriaEnum.name());	    
	}
	int ris = 0;
	switch (passoCreazioneComunicazionePerGraduatoriaEnum) {
	case INSERT_MOVIMENTO:
	    //Adesso suppongo che l'amministrazione sia NULL
	    log.debug("flussoElaborazione# eseguito INSERT_MOVIMENTO");
	    ris = graduatoriedComService.inserimentoMovimentoPerLaComunicazione(graduatoriedCom.getGraduatorietCom(), graduatoriedCom, null, istanza);
	    log.debug("flussoElaborazione# eseguito INSERT_MOVIMENTO ris {}", ris);
	    if (ris == 0) {
		if (graduatoriedCom.getGraduatorietCom().getProtDopoCreazioneAllegato() != null
			&& graduatoriedCom.getGraduatorietCom().getProtDopoCreazioneAllegato().equals(1)) {
		    log.debug("flussoElaborazione# eseguito INSERT_MOVIMENTO ProtDopoCreazioneAllegato = 1");
		    flussoElaborazione(graduatoriedCom, istanza, graduatoriedCom.getMovimenti(),
			    PassoCreazioneComunicazionePerGraduatoriaEnum.CREAZIONE_ALLEGATO);
		} else {
		    log.debug("flussoElaborazione# eseguito INSERT_MOVIMENTO ProtDopoCreazioneAllegato = 0");
		    flussoElaborazione(graduatoriedCom, istanza, graduatoriedCom.getMovimenti(),
			    PassoCreazioneComunicazionePerGraduatoriaEnum.PROTOCOLLAZIONE_MOVIMENTO_PRIMA_CREAZIONE_ALLEGATO);
		}
	    }
	    break;
	case PROTOCOLLAZIONE_MOVIMENTO:
	    log.debug("flussoElaborazione# eseguito PROTOCOLLAZIONE_MOVIMENTO ");
	    ris = graduatoriedComService.inserimentoProtocolloMovimentoPerLaComunicazione(graduatoriedCom.getGraduatorietCom(), graduatoriedCom,
		    istanza, movimento);
	    log.debug("flussoElaborazione# eseguito PROTOCOLLAZIONE_MOVIMENTO ris {}", ris);
	    if (ris == 0) {
		flussoElaborazione(graduatoriedCom, istanza, movimento, PassoCreazioneComunicazionePerGraduatoriaEnum.INVIO_MAIL);
	    }
	    break;
	case CREAZIONE_ALLEGATO:
	    log.debug("flussoElaborazione# eseguito CREAZIONE_ALLEGATO");
	    ris = graduatoriedComService.inserimentoAllegatoMovimentoPerLaComunicazione(graduatoriedCom.getGraduatorietCom(), graduatoriedCom,
		    istanza, movimento);
	    log.debug("flussoElaborazione# eseguito CREAZIONE_ALLEGATO ris {}", ris);
	    if (ris == 0) {
		flussoElaborazione(graduatoriedCom, istanza, movimento, PassoCreazioneComunicazionePerGraduatoriaEnum.CONVERSIONE_PDF_ALLEGATO);
	    }
	    break;
	case CONVERSIONE_PDF_ALLEGATO:
	    log.debug("flussoElaborazione# eseguito CONVERSIONE_PDF_ALLEGATO");
	    ris = graduatoriedComService.conversioneInPDFAllegatoMovimentoPerLaComunicazione(graduatoriedCom.getGraduatorietCom(), graduatoriedCom,
		    istanza, movimento);
	    log.debug("flussoElaborazione# eseguito CONVERSIONE_PDF_ALLEGATO ris {}", ris);
	    if (ris == 0) {
		flussoElaborazione(graduatoriedCom, istanza, movimento, PassoCreazioneComunicazionePerGraduatoriaEnum.FIRMA_DOCUMENTI);
	    }
	    break;
	case FIRMA_DOCUMENTI:
	    log.debug("flussoElaborazione# eseguito FIRMA_DOCUMENTI");
	    ris = graduatoriedComService.inserimentoMettiAllaFirmaPerLaComunicazione(graduatoriedCom.getGraduatorietCom(), graduatoriedCom, istanza,
		    movimento);
	    log.debug("flussoElaborazione# eseguito FIRMA_DOCUMENTI ris {}", ris);
	    if (ris == 0) {
		log.debug("flussoElaborazione# eseguito FIRMA_DOCUMENTI ris {}", ris);
		if (graduatoriedCom.getGraduatorietCom().getProtDopoCreazioneAllegato() != null
			&& graduatoriedCom.getGraduatorietCom().getProtDopoCreazioneAllegato().equals(1)) {
		    log.debug("flussoElaborazione# eseguito FIRMA_DOCUMENTI ProtDopoCreazioneAllegato = 1");
		    flussoElaborazione(graduatoriedCom, istanza, graduatoriedCom.getMovimenti(),
			    PassoCreazioneComunicazionePerGraduatoriaEnum.PROTOCOLLAZIONE_MOVIMENTO);
		} else {
		    log.debug("flussoElaborazione# eseguito FIRMA_DOCUMENTI ProtDopoCreazioneAllegato = 0");
		    flussoElaborazione(graduatoriedCom, istanza, graduatoriedCom.getMovimenti(),
			    PassoCreazioneComunicazionePerGraduatoriaEnum.INVIO_MAIL);
		}
	    }
	    break;
	case INVIO_MAIL:
	    log.debug("flussoElaborazione# eseguito INVIO_MAIL");
	    ris = graduatoriedComService.inserimentoMailInviataDalMovimentoPerLaComunicazione(graduatoriedCom.getGraduatorietCom(), graduatoriedCom,
		    istanza, movimento);
	    log.debug("flussoElaborazione# eseguito INVIO_MAIL ris {}", ris);
	    if (ris == 0) {
		flussoElaborazione(graduatoriedCom, istanza, movimento, PassoCreazioneComunicazionePerGraduatoriaEnum.ELABORAZIONE_COMPLETA);
	    }
	    break;
	case PROTOCOLLAZIONE_MOVIMENTO_PRIMA_CREAZIONE_ALLEGATO:
	    log.debug("flussoElaborazione# eseguito PROTOCOLLAZIONE_MOVIMENTO_PRIMA_CREAZIONE_ALLEGATO");
	    ris = graduatoriedComService.inserimentoProtocolloMovimentoPerLaComunicazione(graduatoriedCom.getGraduatorietCom(), graduatoriedCom,
		    istanza, movimento);
	    log.debug("flussoElaborazione# eseguito PROTOCOLLAZIONE_MOVIMENTO_PRIMA_CREAZIONE_ALLEGATO ris {}", ris);
	    if (ris == 0) {
		flussoElaborazione(graduatoriedCom, istanza, movimento, PassoCreazioneComunicazionePerGraduatoriaEnum.CREAZIONE_ALLEGATO);
	    }
	    break;
	case ELABORAZIONE_COMPLETA:
	    log.debug("flussoElaborazione# elaborazione completa");
	    break;
	default:
	    break;
	}
    }

    private void setTrueEventi(GraduatoriedCom graduatoriedCom, Istanze istanza) {

	log.debug("setTrueEventi# Controllo se l'oggetto comunicazione, ha eventi dell'istanza non letti");
	IstanzeeventiFilter istanzeeventiFilter = new IstanzeeventiFilter();
	istanzeeventiFilter.setIstanze(istanza);
	istanzeeventiFilter.setFlagLetto(false);
	List<Istanzeeventi> eventiIstanza = istanzeeventiService.findByFilter(istanzeeventiFilter, null, null);
	if (!eventiIstanza.isEmpty()) {
	    log.debug("setTrueEventi# Eventi per l'istanza non letti trovati");
	    log.debug("setTrueEventi# Setto eventi istanza trovati come letti....");
	    for (Istanzeeventi istanzeeventi : eventiIstanza) {
		istanzeeventi.setFlagLetto(true);
		istanzeeventiService.update(istanzeeventi);
	    }
	}
	if (EntityUtils.getNestedProperty(graduatoriedCom.getMovimenti(), "id.codice") != null) {
	    Movimenti movimento = movimentiService.findById(new PkId(graduatoriedCom.getMovimenti().getId().getCodice()));
	    istanzeeventiFilter.setMovimenti(movimento);
	    istanzeeventiFilter.setIstanze(movimento.getIstanza());
	    istanzeeventiFilter.setFlagLetto(false);
	    List<Istanzeeventi> eventiMovimenti = istanzeeventiService.findByFilter(istanzeeventiFilter, 0, 1);
	    if (!eventiMovimenti.isEmpty()) {
		log.debug("setTrueEventi# Eventi per il movimento  non letti trovati");
		log.debug("setTrueEventi# Setto eventi per il movimento  trovati come letti....");
		for (Istanzeeventi istanzeeventi : eventiIstanza) {
		    istanzeeventi.setFlagLetto(true);
		    istanzeeventiService.update(istanzeeventi);
		}
	    }
	}
    }

    @Override
    public void insertComunicazioni(GraduatorietCom entity, SchedaDinamicaFilter dinamicaFilter) {

	// Costruisco il campo "dyn2filtri"
	log.debug("insert# Creo la stringa per popolare il campo GraduatorietCom.dyn2Filtri");
	StringBuffer dyn2Filtri = new StringBuffer("");
	//		SchedaDinamicaFilter dinamicaFilter = graduatorietcom.getSchedaDinamicaFilter();
	List<SchedaDinamicaRigheFilter> dinamicaRigheFilters = dinamicaFilter.getRighe();
	for (SchedaDinamicaRigheFilter schedaDinamicaRigheFilter : dinamicaRigheFilters) {
	    if (schedaDinamicaRigheFilter.getAndOr() != null) {
		dyn2Filtri.append(" ");
		dyn2Filtri.append(schedaDinamicaRigheFilter.getAndOr().getStatusCode()).append(" ");
	    }
	    if (StringUtils.isNotBlank(schedaDinamicaRigheFilter.getParentesiSx())) {
		dyn2Filtri.append(schedaDinamicaRigheFilter.getParentesiSx());
	    }
	    if (EntityUtils.getNestedProperty(schedaDinamicaRigheFilter.getCampo(), "id.codice") != null) {
		Dyn2Campi dyn2Campi = dyn2CampiService.findById(new PkId(schedaDinamicaRigheFilter.getCampo().getId().getCodice()));
		dyn2Filtri.append(dyn2Campi.getEtichetta()).append(" ");
	    }
	    if (schedaDinamicaRigheFilter.getTipoConfronto() != null) {
		dyn2Filtri.append(schedaDinamicaRigheFilter.getTipoConfronto().getStatusCode()).append(" ");
	    }
	    if (StringUtils.isNotBlank(schedaDinamicaRigheFilter.getValore())) {
		dyn2Filtri.append(schedaDinamicaRigheFilter.getValore());
	    }
	    if (StringUtils.isNotBlank(schedaDinamicaRigheFilter.getParentesiDx())) {
		dyn2Filtri.append(schedaDinamicaRigheFilter.getParentesiDx());
	    }
	}
	entity.setDyn2filtri(dyn2Filtri.toString());
	log.debug("insert#Inserisco l'oggetto testata delle comunicazioni : GraduatorietCom.....  ");
	graduatorietComService.insert(entity);
	log.debug("insert#Inserito.....  ");
	log.debug("insert# Recupero i record di Graduatoried compatibili con i filtri impostatati sulla testata GraduatorietCom");
	// recupero il set di GraduatoriedDTO compatibili per i filtri impstati nella testata GraduatorietCom, andrò a filtare
	// per i campi GraduatorietCom.posizione, Riservato a (può selezionare Graduatoried con: concessioni,senza concessione, entrambe )
	// e per il campo dyn2Filtri impostato. 
	Set<GraduatoriedDTO> graduatoriedDTOs = graduatoriedService.findGraduatoriedPerComunuicazione(entity.getGraduatoriet().getId().getCodice(),
		entity.getPosizioneDa(), entity.getPosizioneAl(), entity.getDestinatari(), dinamicaFilter);
	log.debug("insert#Inizio inserimento oggetti del dettaglio delle comunicazioni:GraduatoriedCom..... ");
	for (GraduatoriedDTO graduatoriedDTO : graduatoriedDTOs) {
	    // Inserisco l'oggetto comunicazioni:GraduatoriedCom
	    GraduatoriedCom graduatoriedCom = graduatoriedComService.insertComunicazioneDettaglio(entity, graduatoriedDTO);
	    this.elaboroGraduatoriedCom(graduatoriedCom);
	}
	log.debug("insert#Inserimento terminato........ ");
    }

    @Override
    public void elaboroGraduatoriedCom(GraduatoriedCom graduatoriedCom) {

	PassoCreazioneComunicazionePerGraduatoriaEnum passoCreazioneComunicazionePerGraduatoriaEnum = calcolaPassoStopElaborazione(graduatoriedCom);
	this.elabora(graduatoriedCom, passoCreazioneComunicazionePerGraduatoriaEnum);
    }

    /**
     * Il metodo a partire dalla comunicazione passata (graduatoridCom) calcola a quale passo si è fermata
     * l'elaborazione (Passi: Inserimento movimento, protocollazione movimento,crea allegato , invio mail)
     */
    private PassoCreazioneComunicazionePerGraduatoriaEnum calcolaPassoStopElaborazione(GraduatoriedCom graduatoriedCom) {

	PassoCreazioneComunicazionePerGraduatoriaEnum res = PassoCreazioneComunicazionePerGraduatoriaEnum.ELABORAZIONE_COMPLETA;
	// Al primo passo che trovo non svolto esco dal metodo
	log.debug(
		"calcolaPassoStopElaborazione# Inzio ricerca passo di interruzione elaborazione comunicazione per l'istanza {}[{}]. Codice comunicazione : {} ",
		new Object[] { graduatoriedCom.getGraduatoried().getIstanza().getNumeroistanza(),
			graduatoriedCom.getGraduatoried().getIstanza().getId().getCodice(), graduatoriedCom.getId().getCodice() });
	log.debug("calcolaPassoStopElaborazione# Controllo il movimento è stato creato.....");
	if (EntityUtils.getNestedProperty(graduatoriedCom.getMovimenti(), "id.codice") == null) {
	    log.debug("calcolaPassoStopElaborazione# Elaborazione stop al passo: {}",
		    PassoCreazioneComunicazionePerGraduatoriaEnum.INSERT_MOVIMENTO.name());
	    return PassoCreazioneComunicazionePerGraduatoriaEnum.INSERT_MOVIMENTO;
	}
	// Controlliamo se ci siamo fermato a questo passo solo se nella configurazione della comunicazione graduatoriet_com ha configurato il valore del campo 
	//protDopoCreazioneAllegato null o 0, cioè abbiamo configurato la comunicazione che prima deve protocollare e poi creare l'allegato
	if (graduatoriedCom.getGraduatorietCom().getFlgProtocolla().equals(true) && graduatoriedCom.getMovimenti().getDataprotocollo() == null) {
	    if (graduatoriedCom.getGraduatorietCom().getProtDopoCreazioneAllegato() == null
		    || graduatoriedCom.getGraduatorietCom().getProtDopoCreazioneAllegato() == 0) {
		log.debug("calcolaPassoStopElaborazione# Elaborazione stop al passo: {}",
			PassoCreazioneComunicazionePerGraduatoriaEnum.PROTOCOLLAZIONE_MOVIMENTO_PRIMA_CREAZIONE_ALLEGATO.name());
		return PassoCreazioneComunicazionePerGraduatoriaEnum.PROTOCOLLAZIONE_MOVIMENTO_PRIMA_CREAZIONE_ALLEGATO;
	    }
	}
	if (graduatoriedCom.getGraduatorietCom().getLetteretipo() != null && graduatoriedCom.getGraduatorietCom().getLetteretipo().getId() != null
		&& graduatoriedCom.getGraduatorietCom().getLetteretipo().getId().getCodice() != null) {
	    if (EntityUtils.getNestedProperty(graduatoriedCom.getOggetti(), "id.codice") == null) {
		log.debug("calcolaPassoStopElaborazione# Elaborazione stop al passo: {}",
			PassoCreazioneComunicazionePerGraduatoriaEnum.CREAZIONE_ALLEGATO.name());
		return PassoCreazioneComunicazionePerGraduatoriaEnum.CREAZIONE_ALLEGATO;
	    }
	    if (BooleanUtils.isTrue(graduatoriedCom.getGraduatorietCom().getFlgTrasformaPdf())) {
		if (EntityUtils.getNestedProperty(graduatoriedCom.getOggetti(), "id.codice") != null) {
		    Oggetti o = oggettiService.findByIdLazy(new PkId(graduatoriedCom.getOggetti().getId().getCodice()));
		    if (o.getNomefile().toLowerCase().endsWith(".rtf") || o.getNomefile().toLowerCase().endsWith(".odt")) {
			return PassoCreazioneComunicazionePerGraduatoriaEnum.CONVERSIONE_PDF_ALLEGATO;
		    }
		}
	    }
	    if (BooleanUtils.isTrue(graduatoriedCom.getGraduatorietCom().getFlagMettiallafirma())) {
		Integer codiceOggettoDaFirmare = null;
		// verifico se sono state apposte le firme per quell'oggetto
		if (graduatoriedCom.getOggetti() != null) {
		    if (graduatoriedCom.getOggetti().getId() != null) {
			if (graduatoriedCom.getOggetti().getId().getCodice() != null) {
			    codiceOggettoDaFirmare = graduatoriedCom.getOggetti().getId().getCodice();
			}
		    }
		}
		if (codiceOggettoDaFirmare == null) {
		    throw new RuntimeException("Attenzione!!! non è stato generato l'allegato da firmare");
		}
		///////////////////////////////////////
		///////////////////////////////////////
		List<Responsabili> resp = graduatorietComService.findResponsabiliFirmatari(graduatoriedCom.getGraduatorietCom().getListaFirmatari());
		if (resp.isEmpty()) {
		    throw new RuntimeException("La comunicazione prevede la firma degli allegati generati ma non sono stati trovati i firmatari");
		}
		boolean success = true;
		if (resp.isEmpty()) {
		    success = false;
		}
		for (Responsabili responsabili : resp) {
		    List<DocumentiDaFirmare> docs = documentiDaFirmareService.findByIdOggettoAndFirmatarioAndIstanza(codiceOggettoDaFirmare,
			    responsabili.getId().getCodice(), graduatoriedCom.getGraduatoried().getIstanza().getId().getCodice());
		    if (!docs.isEmpty()) {
			for (DocumentiDaFirmare doc : docs) {
			    if (!documentiDaFirmareService.isFirmaCompleta(doc)) {
				success = false;
			    }
			}
		    } else {
			success = false;
		    }
		}
		//////////////////////////////////////
		//////////////////////////////////////
		if (success) {
		    // 1. ricerca su documenti da firmare con quel codice oggetto
		    List<DocumentiDaFirmare> docs = documentiDaFirmareService.findByIdOggetto(codiceOggettoDaFirmare);
		    // 2. per ogni riga verifico se è stat firmata con successo	   
		    if (docs == null || docs.size() == 0) {
			success = false;
		    } else {
			for (DocumentiDaFirmare doc : docs) {
			    if (!documentiDaFirmareService.isFirmaCompleta(doc)) {
				success = false;
				break;
			    }
			}
		    }
		    // 3. se non firmata torno lo step
		}
		if (!success) {
		    log.debug("calcolaPassoStopElaborazione# Elaborazione stop al passo: {}",
			    PassoCreazioneComunicazionePerGraduatoriaEnum.FIRMA_DOCUMENTI.name());
		    return PassoCreazioneComunicazionePerGraduatoriaEnum.FIRMA_DOCUMENTI;
		}
	    }
	}
	// Controlliamo se ci siamo fermato a questo passo solo se nella configurazione della comunicazione graduatoriet_com ha configurato il valore del campo 
	//protDopoCreazioneAllegato 1, cioè abbiamo configurato la comunicazione  prima crea l'allegato e poi protocolla
	if (graduatoriedCom.getGraduatorietCom().getFlgProtocolla().equals(true) && graduatoriedCom.getMovimenti().getDataprotocollo() == null) {
	    if (graduatoriedCom.getGraduatorietCom().getProtDopoCreazioneAllegato() != null
		    && graduatoriedCom.getGraduatorietCom().getProtDopoCreazioneAllegato() == 1) {
		log.debug("calcolaPassoStopElaborazione# Elaborazione stop al passo: {}",
			PassoCreazioneComunicazionePerGraduatoriaEnum.PROTOCOLLAZIONE_MOVIMENTO.name());
		return PassoCreazioneComunicazionePerGraduatoriaEnum.PROTOCOLLAZIONE_MOVIMENTO;
	    }
	}
	if (EntityUtils.getNestedProperty(graduatoriedCom.getMovimentimail(), "id.codice") == null) {
	    log.debug("calcolaPassoStopElaborazione# Elaborazione stop al passo: {}",
		    PassoCreazioneComunicazionePerGraduatoriaEnum.INVIO_MAIL.name());
	    return PassoCreazioneComunicazionePerGraduatoriaEnum.INVIO_MAIL;
	}
	log.debug("calcolaPassoStopElaborazione# {}", res.name());
	return res;
    }
}
