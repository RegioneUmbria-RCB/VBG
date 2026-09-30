package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeTempistica;
import it.gruppoinit.pal.gp.core.domain.Istanzereplicate;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeDaChiudereHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ReportIstanzaChiusaHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.IstanzecollegateService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.IstanzeManager;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService.TipoInserimento;
import it.gruppoinit.pal.gp.core.service.IstanzeTempisticaService;
import it.gruppoinit.pal.gp.core.service.IstanzereplicateService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametriService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametribaseService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.EntityValidationException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.rules.OggettiBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.BaseEnvironment;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class IstanzeManagerImpl extends BaseEnvironment implements IstanzeManager {

    private static final Logger log = LoggerFactory.getLogger(IstanzeManagerImpl.class);
    protected InvalidValue[] validationMessages;
    private AlberoprocService alberoprocService;
    private ApplicationContext applicationContext;
    private IAttivitaService iAttivitaService;
    private IstanzecollegateService istanzecollegateService;
    private IstanzereplicateService istanzereplicateService;
    private IstanzeService istanzeService;
    private SoftwareService softwareService;
    private IstanzeTempisticaService istanzeTempisticaService;

    @Autowired
    public void setExternalDBResolver(ExternalDBResolver externalDBResolver) {

	this.externalDBResolver = externalDBResolver;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setIstanzeTempisticaService(IstanzeTempisticaService istanzeTempisticaService) {

	this.istanzeTempisticaService = istanzeTempisticaService;
    }

    @Autowired
    public void setVerticalizzazioniparametriService(VerticalizzazioniparametriService verticalizzazioniparametriService) {

	this.verticalizzazioniparametriService = verticalizzazioniparametriService;
    }

    @Autowired
    public void setVerticalizzazioniparametribaseService(VerticalizzazioniparametribaseService verticalizzazioniparametribaseService) {

	this.verticalizzazioniparametribaseService = verticalizzazioniparametribaseService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {

	this.applicationContext = applicationContext;
    }

    @Autowired
    public void setiAttivitaService(IAttivitaService iAttivitaService) {

	this.iAttivitaService = iAttivitaService;
    }

    @Autowired
    public void setIstanzecollegateService(IstanzecollegateService istanzecollegateService) {

	this.istanzecollegateService = istanzecollegateService;
    }

    @Autowired
    public void setIstanzereplicateService(IstanzereplicateService istanzereplicateService) {

	this.istanzereplicateService = istanzereplicateService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    public IstanzeService getIstanzeService() {

	return istanzeService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Override
    public List<Istanze> creaRepliche(Istanze istanza, List<Integer> codiciIntervento, boolean isGestioneAttivita) {

	log.debug("creaRepliche: INIZIO");
	if (istanza == null) {
	    throw new IllegalArgumentException("L'istanza non pò essere nulla");
	}
	if (codiciIntervento == null) {
	    throw new IllegalArgumentException("La lista degli interventi selezionati non può essere nulla");
	}
	if (codiciIntervento.size() == 0) {
	    throw new IllegalArgumentException("La lista degli interventi selezionati non può essere vuota");
	}
	OggettiBusinessRules oggettiBusinessRules = new OggettiBusinessRules();
	oggettiBusinessRules.setInsert(true);
	SigeproBusinessRules.setClassRules(OggettiBusinessRules.class, oggettiBusinessRules);
	List<Integer> codiciIstanzaInseriti = new ArrayList<Integer>();
	log.debug("creaRepliche: popolo il template dell'istanza per la replica");
	Istanze template = istanzeService.createTemplateFromIstanzaForReplica(istanza);
	log.debug("creaRepliche: dopo il popolamento del template dell'istanza per la replica");
	for (Integer codiceAlberoproc : codiciIntervento) {
	    log.debug("creaRepliche: copio le properties in un nuovo oggetto istanza");
	    Istanze nuovaIstanza = (Istanze) Utilities.copyObjectProperties(template, new Istanze());
	    log.debug("creaRepliche: dopo la copia delle properties in un nuovo oggetto istanza");
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceAlberoproc));
	    nuovaIstanza.setAlberoproc(alberoproc);
	    log.debug("creaRepliche: prima dell'inserimento dell'istanza");
	    try {
		istanzeService.insert(nuovaIstanza, TipoInserimento.PROTOCOLLAZIONE_PARAMETRI_INSERIMENTO_NORMALE);
		// Gestione ricalcolo dati dell'attività.
		// Controllo se la nuova istanza è stata collegata a un attivtà
		if (EntityUtils.getNestedProperty(nuovaIstanza.getAttivita(), "id.codice") != null) {
		    log.debug("creaRepliche: istanza replica collegata a un attività");
		    // Recupero l'attività e tologo la dipendenza sulla istanza replica
		    IAttivita attivita = nuovaIstanza.getAttivita();
		    nuovaIstanza.setAttivita(null);
		    istanzeService.update(nuovaIstanza);
		    if (isGestioneAttivita) {
			log.debug("creaRepliche: funzionamento con gestione dell' attività collegate all'istanza sorgente");
			// Controllare se l' istanza creata è stata collegata ad un attività (istanza sorgente è collegata a un attività) 
			//
			// nuovaIstanza.setAttivita(null);
			// istanzeService.update(nuovaIstanza);
			this.iAttivitaService.collegaIstanza(attivita, nuovaIstanza);
		    } else {
			log.debug("creaRepliche: funzionamento non cosiderando l' attività collegata all'istanza sorgente");
		    }
		}
	    } catch (BusinessValidationException e) {
		SigeproBusinessRules.buildDefaultRules();
		throw e;
	    } catch (EntityValidationException e) {
		SigeproBusinessRules.buildDefaultRules();
		throw e;
	    } catch (Exception e) {
		SigeproBusinessRules.buildDefaultRules();
		throw new RuntimeException(e);
	    }
	    log.debug("creaRepliche: istanza inserita {}", nuovaIstanza.getDescrizioneIstanza());
	    codiciIstanzaInseriti.add(nuovaIstanza.getId().getCodice());
	}
	SigeproBusinessRules.buildDefaultRules();
	///////////////////////////////////
	for (Integer codiceIstanzaFiglia : codiciIstanzaInseriti) {
	    Istanze figlia = istanzeService.findById(new PkId(codiceIstanzaFiglia));
	    Istanzereplicate istanzereplicate = new Istanzereplicate();
	    istanzereplicate.getId().setCodiceistanzapadre(istanza.getId().getCodice());
	    istanzereplicate.getId().setCodiceistanzafiglia(codiceIstanzaFiglia);
	    istanzereplicate.setIstanzafiglia(figlia);
	    istanzereplicate.setIstanzapadre(istanza);
	    log.debug("creaRepliche: prima di inserire in istanze replicate{}");
	    istanzereplicateService.insert(istanzereplicate);
	    log.debug("creaRepliche: cerco le istanze replicate per collegarle");
	    log.debug("creaRepliche: collego l'istanza replicate per collegarle {},{}", istanza.getId(), figlia.getId());
	    istanzecollegateService.insertCollegamento(istanza, figlia, true, isGestioneAttivita);
	    log.debug("creaRepliche: collegamento effettuato");
	}
	/////////////////////////////
	return istanzereplicateService.findIstanzeReplicate(istanza);
    }

    @Override
    public void deleteIstanzeBySoftwareAndIntervento(String psoftware, List<Integer> codiceIntervento) {

	if (StringUtils.isBlank(psoftware)) {
	    throw new IllegalArgumentException("Il modulo software è obbligatorio");
	}
	Software software = softwareService.findById(psoftware);
	if (software == null) {
	    throw new IllegalArgumentException("Il modulo software [" + psoftware + "] non è stato trovato!!");
	}
	List<Integer> codiciIstanza = istanzeService.findTuttiCodiciIstanzaPerSoftwareAndIntervento(psoftware, codiceIntervento);
	Istanze istanza = null;
	PkId idIstanza = null;
	List<String> warnings = new ArrayList<String>();
	List<String> infos = new ArrayList<String>();
	Responsabili userlogged = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	if (userlogged == null) {
	    throw new SecurityException("Nessun operatore trovato nel contesto della sicurezza.");
	}
	if (!StringUtils.defaultIfEmpty(userlogged.getAmministratore(), "").equalsIgnoreCase("1")) {
	    throw new SecurityException("L'operatore [" + userlogged.toString() + "] non è amministratore e non può usare la funzionalità");
	}
	boolean isCancellaIstanze = userlogged.getFlagCancellaistanze() == null ? false : userlogged.getFlagCancellaistanze().booleanValue();
	if (!isCancellaIstanze) {
	    String messaggioErrore = "L'operatore non è abilitato a cancellare le pratiche (RESPONSABILI.FLAG_CANCELLAISTANZE=0).";
	    throw new SecurityException(messaggioErrore);
	}
	String responsabile = (String) EntityUtils.getNestedProperty(userlogged, "responsabile");
	String intestazioneErroriCancellazione = "Non è stato possibile cancellare le istanze: <ul>";
	for (Integer codiceIstanza : codiciIstanza) {
	    idIstanza = new PkId(codiceIstanza);
	    istanza = istanzeService.findById(idIstanza);
	    String identIstanza = istanza.toString();
	    try {
		istanzeService.delete(istanza);
		LoggerCancellazioni.logCancellazioneIstanza(responsabile, identIstanza);
		infos.add("L'istanza [" + identIstanza + "] è stata eliminata.");
	    } catch (Exception e) {
		String errorMessage = "<li><a href=\"../istanze/view.htm?codice=" +
			codiceIstanza +
			"&software=" +
			ORMHelper.getSoftware() +
			"\">" +
			identIstanza +
			"</a> a causa di:<ul>";
		if (e instanceof BusinessValidationException) {
		    List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
		    for (InvalidValue invalidValue : ivs) {
			String descrizioneMessaggio = Utilities.getMessageFromBundle(applicationContext,
				StringUtils.defaultIfEmpty(invalidValue.getMessage(), ""), new Object[] { invalidValue.getValue() });
			if (StringUtils.defaultIfEmpty(descrizioneMessaggio, "").indexOf("??") > 0) {
			    descrizioneMessaggio = invalidValue.getMessage();
			}
			errorMessage = errorMessage.concat("<li>");
			if (!StringUtils.defaultIfEmpty((String) EntityUtils.getNestedProperty(invalidValue, "beanClass"), "").equals("")) {
			    errorMessage = errorMessage.concat("[").concat((String) EntityUtils.getNestedProperty(invalidValue, "beanClass"))
				    .concat("]");
			}
			if (!StringUtils.defaultIfEmpty(invalidValue.getPropertyPath(), "").equalsIgnoreCase("")) {
			    errorMessage = errorMessage.concat(",[").concat(StringUtils.defaultIfEmpty(invalidValue.getPropertyPath(), ""))
				    .concat("]");
			}
			errorMessage = errorMessage.concat(descrizioneMessaggio).concat("</li>");
		    }
		    errorMessage = errorMessage.concat("</ul></li>");
		} else if (e instanceof DataIntegrityViolationException) {
		    String descrizioneMessaggio = "";
		    Throwable cause = e.getCause();
		    if (cause != null) {
			if (cause instanceof ConstraintViolationException) {
			    descrizioneMessaggio = ((ConstraintViolationException) cause).getSQLException().getMessage();
			} else {
			    descrizioneMessaggio = cause.getMessage();
			}
		    } else {
			e.getMessage();
		    }
		    errorMessage = errorMessage.concat("<li>").concat(descrizioneMessaggio).concat("</li></ul></li>");
		} else {
		    errorMessage = errorMessage.concat("<li>").concat(StringUtils.defaultIfEmpty(e.getMessage(), "Errore non definito"))
			    .concat("</li></ul></li>");
		}
		warnings.add(errorMessage);
	    }
	}
	if (warnings.size() > 0) {
	    warnings.add(0, intestazioneErroriCancellazione);
	    warnings.add("</ul>");
	    FlashMessages.setWarnings(warnings);
	}
	if (infos.size() > 0) {
	    FlashMessages.setInfos(infos);
	} else {
	    infos.add("Nessuna istanza cancellata");
	    FlashMessages.setInfos(infos);
	}
    }

    @Override
    public List<ReportIstanzaChiusaHelper> updateProcessaIstanzedaChiudere(String idcomunealias, String software, boolean setORMHelper,
	    boolean chiudiLeInterrotteSospese) {

	if (setORMHelper) {
	    setORMHelperSoftware(idcomunealias, software);
	}
	log.debug("cerco le istanze da chiudere");
	List<IstanzeDaChiudereHelper> list = istanzeService.findIstanzedaChiudere();
	log.debug("trovate {} istanze da chiudere", list.size());
	List<ReportIstanzaChiusaHelper> result = new ArrayList<ReportIstanzaChiusaHelper>(list.size());
	for (IstanzeDaChiudereHelper idch : list) {
	    ReportIstanzaChiusaHelper rih = new ReportIstanzaChiusaHelper();
	    rih.setIstanza(idch);
	    result.add(rih);
	    Istanze i = istanzeService.findById(new PkId(idch.getCodiceIstanza()));
	    try {
		boolean esegui = true;
		if (!chiudiLeInterrotteSospese) {
		    if (i.getIstanzeTempistica() != null && i.getIstanzeTempistica().getStato() != null) {
			if (StringUtils.defaultString(i.getIstanzeTempistica().getStato()).equals("I")
				|| StringUtils.defaultString(i.getIstanzeTempistica().getStato()).equals("S")) {
			    esegui = false;
			}
		    }
		}
		if (esegui) {
		    istanzeService.updateStatoIstanza(i, idch.getStatochiusura());
		    IstanzeTempistica it = istanzeTempisticaService.findById(new PkId(idch.getCodiceIstanza()));
		    if (it != null) {
			// modificato 2019-01-23 BOCCI/TODINI ripristinato il comportamento di far calcolare la data con la data di sistema
			// La modifica era stata introdotta per la chiusura automatica delle istanze tramite JOB schedulato
			// ma ha comportato problemi nella gestione manuale. il Calcolo della data fine effettiva per la chiusura automatica è
			// stato spostato nel manager che chude le istanze
			it.setDatafineeffettiva(it.getDatafine());
			istanzeTempisticaService.update(it);
		    }
		    rih.setChiusa(true);
		    log.debug("istanza con codice {} chiusa correttamente", idch.getCodiceIstanza());
		} else {
		    rih.setChiusa(false);
		    rih.setMessaggioErrore("L'istanza si trova nello stato interrotta o sospesa");
		    log.debug("istanza con codice {} nello stato chiusa o interrotta", idch.getCodiceIstanza());
		}
	    } catch (Exception e) {
		log.error("Errore nella chiusura dell'istanza con codice:{}-{}-{}, numero={}: {}",
			new Object[] { idch.getIdcomune(), idch.getSoftware(), idch.getCodiceIstanza().intValue(), idch.getNumeroistanza(), e });
		rih.setChiusa(false);
		rih.setMessaggioErrore(e.getMessage());
	    }
	    istanzeService.clear();
	}
	StringBuffer s = createReport(result, ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware());
	LoggerCancellazioni.log(s.toString());
	return result;
    }

    private StringBuffer createReport(List<ReportIstanzaChiusaHelper> l, String idcomunealias, String software) {

	StringBuffer sbuf = new StringBuffer("#BEGIN_ESITO_PROCESSAMENTO_CHIUSURA_AUTOMATICA_PRATICHE#");
	sbuf.append("\nProcessate ");
	sbuf.append(l.size()).append(" pratiche per l'idcomunealias ").append(idcomunealias).append(" e software ").append(software);
	for (ReportIstanzaChiusaHelper r : l) {
	    sbuf.append("\n-----------------------------------------------------");
	    sbuf.append("\nistanza codice#[").append(r.getIstanza().getCodiceIstanza());
	    sbuf.append("] numero#[").append(r.getIstanza().getNumeroistanza());
	    sbuf.append("] esito#[").append(r.isChiusa() ? "OK" : "KO");
	    if (StringUtils.isNotBlank(r.getMessaggioErrore())) {
		sbuf.append("] errore#[").append(r.getMessaggioErrore()).append("]");
	    }
	}
	sbuf.append("\n\n#END_ESITO_PROCESSAMENTO_CHIUSURA_AUTOMATICA_PRATICHE#");
	return sbuf;
    }

    @Override
    public List<IstanzeDaChiudereHelper> udpateProcessaValidazioneStradario(boolean settaANulliNonValidi) {

	throw new NotImplementedException("da implementare");
	//	if (settaANulliNonValidi) {
	//	    istanzestradarioService.updateSettaANullNonValidi();
	//	}
	// cerco le istanzestradario con valido == null
	// per ogni istanza effettuo la validazione e setto valido
	// return null;
    }

    @Override
    public void updateContatori(String idcomunealias, String software) {

	istanzeService.updateContatori(true);
    }
}
