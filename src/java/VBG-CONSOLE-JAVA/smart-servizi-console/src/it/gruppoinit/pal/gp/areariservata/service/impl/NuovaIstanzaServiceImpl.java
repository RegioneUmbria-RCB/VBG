package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.service.FoArjDomandeHelperService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaAllegatiSchedeService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaAllegatiService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaAnagrafeService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaBenvenutoService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaInformativaService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaInterventoService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaLocalizzazioneService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaOneriService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaProcedimentiService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaSchedeService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaService;
import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;
import it.gruppoinit.pal.gp.areariservata.web.util.StepsHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.FoArjServizi;
import it.gruppoinit.pal.gp.core.domain.FoArjSteps;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsTestata;
import it.gruppoinit.pal.gp.core.domain.helper.StepsEnum;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsTestataService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NuovaIstanzaServiceImpl implements NuovaIstanzaService {

    private static final Logger log = LoggerFactory.getLogger(NuovaIstanzaServiceImpl.class);
    //    @Autowired
    //    private FoArjDomandeService foArjDomandeService;
    @Autowired
    private FoArjStepsTestataService foArjStepsTestataService;
    @Autowired
    private FoArjStepsService foArjStepsService;
    @Autowired
    private FoArjDomandeHelperService foArjDomandeHelperService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private UserSecurityService userSecurityService;
    //    @Autowired
    //    private FoArjDomandeStepsEseguitiService foArjDomandeStepsEseguitiService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private NuovaIstanzaBenvenutoService nuovaIstanzaBenvenutoService;
    @Autowired
    private NuovaIstanzaInformativaService nuovaIstanzaInformativaService;
    @Autowired
    private NuovaIstanzaInterventoService nuovaIstanzaInterventoService;
    @Autowired
    private NuovaIstanzaAnagrafeService nuovaIstanzaAnagrafeService;
    @Autowired
    private NuovaIstanzaLocalizzazioneService nuovaIstanzaLocalizzazioneService;
    @Autowired
    private NuovaIstanzaProcedimentiService nuovaIstanzaProcedimentiService;
    @Autowired
    private NuovaIstanzaAllegatiService nuovaIstanzaAllegatiService;
    @Autowired
    private NuovaIstanzaSchedeService nuovaIstanzaSchedeService;
    @Autowired
    private NuovaIstanzaAllegatiSchedeService nuovaIstanzaAllegatiSchedeService;
    @Autowired
    private NuovaIstanzaOneriService nuovaIstanzaOneriService;

    @Override
    public NuovaIstanzaCommand nuovaDomanda() throws Exception {

	log.debug("nuovaDomanda");
	//estraggo il flusso di default
	FoArjStepsTestata testataDefault = foArjStepsTestataService.findTestataDefault();
	log.debug("nuovaDomanda: flusso di default caricato: [{}]", testataDefault);
	List<FoArjSteps> stepsTestataDefault = foArjStepsService.findAttivi(testataDefault.getId().getIdcomune(), testataDefault.getId().getCodice());
	if (stepsTestataDefault.isEmpty()) {
	    log.error("lista step vuota per la testata [{}]", testataDefault);
	    throw new Exception("No steps");
	}
	StepsHelper stepsHelper = new StepsHelper(stepsTestataDefault);
	return new NuovaIstanzaCommand(stepsHelper, false, null);
    }

    @Override
    public NuovaIstanzaCommand nuovaDomandaDaIntervento(Integer codiceIntervento, FoArjServizi servizio) throws Exception {

	log.debug("nuovaDomandaDaIntervento codiceIntervento=[{}]", codiceIntervento);
	//estraggo il flusso dall'intervento
	Alberoproc alberoproc = alberoprocService.findByIdAndCurrentSoftware(ORMHelper.getIdcomune(), codiceIntervento, true);
	if (alberoproc == null) {
	    log.error("nuovaDomandaDaIntervento: intervento non trovato codiceIntervento=[{}]", codiceIntervento);
	    throw new Exception("Intervento non trovato");
	}
	FoArjStepsTestata testata = foArjStepsTestataService.findTestataIntervento(ORMHelper.getIdcomune(), codiceIntervento);
	if (testata == null) {
	    testata = foArjStepsTestataService.findTestataDefault();
	}
	List<FoArjSteps> stepsTestata = foArjStepsService.findAttivi(testata.getId().getIdcomune(), testata.getId().getCodice());
	if (stepsTestata.isEmpty()) {
	    log.error("lista step vuota per la testata [{}] e l'intervento [{}]", stepsTestata, codiceIntervento);
	    throw new Exception("No steps");
	}
	StepsHelper stepsHelper = new StepsHelper(stepsTestata);
	NuovaIstanzaCommand cmd = new NuovaIstanzaCommand(stepsHelper, true, servizio);
	cmd.getIntervento().setCodice(codiceIntervento.toString());
	cmd.getIntervento().setDescrizione(alberoproc.getVwAlberoproc().getScDescrizione());
	return cmd;
    }

    @Override
    public NuovaIstanzaCommand riprendiDomandaInCompilazione(Integer id) throws Exception {

	//	
	return null;
    }

    @Override
    public FoArjSteps updateDomandaDaCambioIntervento(NuovaIstanzaCommand cmd) throws Exception {

	//	log.debug("updateDomandaDaCambioIntervento");
	//	FoArjSteps primoStepFlussoIntervento = null;
	//	//estraggo gli steps del flusso della configurazione
	//	FoArjStepsTestata testataDefault = foArjStepsTestataService.findTestataDefault();
	//	List<FoArjSteps> stepsTestataDefault = foArjStepsService.findAttivi(testataDefault.getId().getIdcomune(), testataDefault.getId().getCodice());
	//	if (stepsTestataDefault.isEmpty()) {
	//	    log.error("lista step vuota per la testata della configurazione [{}]", testataDefault);
	//	    throw new Exception("No steps");
	//	}
	//	//estraggo gli steps dell'eventuale flusso dell'intervento
	//	List<FoArjSteps> stepsTestataIntervento = new ArrayList<FoArjSteps>();
	//	Integer codiceIntervento = Integer.valueOf(cmd.getIntervento().getCodice());
	//	FoArjStepsTestata testataIntervento = foArjStepsTestataService.findTestataIntervento(ORMHelper.getIdcomune(), codiceIntervento);
	//	if (testataIntervento != null) {
	//	    stepsTestataIntervento = foArjStepsService.findAttivi(testataIntervento.getId().getIdcomune(), testataIntervento.getId().getCodice());
	//	    primoStepFlussoIntervento = stepsTestataIntervento.isEmpty() ? null : stepsTestataIntervento.get(0);
	//	}
	//	//unione delle due liste
	//	List<FoArjSteps> stepsAttuali = this.getListaStepsUnione(stepsTestataDefault, stepsTestataIntervento);
	//	StepsHelper stepsHelper = new StepsHelper(stepsAttuali);
	//	cmd.setStepsHelper(stepsHelper);
	//	FoArjDomande foArjDomande = foArjDomandeService.findById(new PkId(cmd.getId()));
	//	//elimino i dati e gli steps fatti che non sono nella lista unione
	//	List<FoArjDomandeStepsEseguiti> stepsToClear = this.getListaStepsDaCancellare(foArjDomande, stepsAttuali);
	//	foArjDomandeStepsEseguitiService.deleteAll(stepsToClear);
	//	//evict necessario altrimenti hibernate non riesce a sincronizzare l'oggetto foarjdomande in cache con la lista degli step eseguiti
	//	foArjDomandeService.evict(foArjDomande);
	//	this.clear(cmd, stepsToClear);
	//	return primoStepFlussoIntervento;
	return null;
    }

    @Override
    public void saveDomanda(NuovaIstanzaCommand command, FoArjSteps stepEseguito) throws Exception {

	//	log.debug("saveDomanda id=[{}]", command.getId());
	//	//se sono stati rimossi dei file uploadati dai campi dinamici effettuo le cancellazioni da OGGETTI
	//	List<Integer> uploadedDeleted = command.getFileUploadDaCancellare();
	//	if (uploadedDeleted != null && uploadedDeleted.size() > 0) {
	//	    this.oggettiService.deleteAll(uploadedDeleted);
	//	}
	//	NuovaIstanzaType nuovaIstanzaType = foArjDomandeHelperService.populateNuovaIstanzaType(command);
	//	FoArjDomande domanda = null;
	//	if (command.getId() != null) {
	//	    domanda = foArjDomandeService.findById(new PkId(command.getId()));
	//	    nuovaIstanzaType.getDettaglioPratica().setIdPratica(domanda.getIdDomanda());
	//	    nuovaIstanzaType.getDettaglioPratica().setNumeroPratica(domanda.getIdDomanda());
	//	    command.setIdDomanda(domanda.getIdDomanda());
	//	    String praticaXML = StcDomainHelper.marshalObject(nuovaIstanzaType, NuovaIstanzaType.class);
	//	    log.trace("saveDomanda() update\n{}", praticaXML);
	//	    Oggetti ogg = domanda.getOggetti();
	//	    ogg.setOggetto(praticaXML.getBytes("UTF-8"));
	//	    oggettiService.update(ogg);
	//	    domanda.setDataUltimaModifica(new Date());
	//	    this.setInterventoDomanda(domanda, command);
	//	    foArjDomandeService.update(domanda);
	//	    log.debug("saveDomanda(): update, id={}", command.getId());
	//	} else {
	//	    Anagrafe user = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	//	    domanda = new FoArjDomande();
	//	    domanda.setAnagrafe(user);
	//	    domanda.setDataUltimaModifica(new Date());
	//	    String idDomanda = ORMHelper.getIdcomune() + "-" + ORMHelper.getSoftware() + "-" + user.getId().getCodice() + "-" + new Date().getTime();
	//	    domanda.setIdDomanda(idDomanda);
	//	    nuovaIstanzaType.getDettaglioPratica().setIdPratica(idDomanda);
	//	    nuovaIstanzaType.getDettaglioPratica().setNumeroPratica(idDomanda);
	//	    command.setIdDomanda(idDomanda);
	//	    String praticaXML = StcDomainHelper.marshalObject(nuovaIstanzaType, NuovaIstanzaType.class);
	//	    log.trace("saveDomanda() insert\n{}", praticaXML);
	//	    Software software = softwareService.findById(ORMHelper.getSoftware());
	//	    domanda.setSoftware(software);
	//	    Oggetti ogg = new Oggetti();
	//	    ogg.setNomefile(idDomanda + ".xml");
	//	    ogg.setOggetto(praticaXML.getBytes("UTF-8"));
	//	    oggettiService.insert(ogg);
	//	    domanda.setOggetti(ogg);
	//	    this.setInterventoDomanda(domanda, command);
	//	    domanda.setFoArjServizi(command.getServizio());
	//	    domanda.setFlagFlussoDaIntervento(command.isDomandaDaIntervento());
	//	    foArjDomandeService.insert(domanda);
	//	    command.setId(domanda.getId().getCodice());
	//	    log.debug("saveDomanda(): insert, id:{}", command.getId());
	//	}
	//	if (stepEseguito != null) {
	//	    foArjDomandeStepsEseguitiService.insertEseguito(domanda, stepEseguito);
	//	}
    }

    //    private void setInterventoDomanda(FoArjDomande domanda, NuovaIstanzaCommand cmd) {
    //
    //	if (!EntityUtils.isNestedPropertyBlank(cmd, "intervento.codice")) {
    //	    Integer codiceIntervento = Integer.valueOf(cmd.getIntervento().getCodice());
    //	    log.debug("setInterventoDomanda: codiceIntervento={}", codiceIntervento);
    //	    Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceIntervento));
    //	    domanda.setAlberoproc(alberoproc);
    //	}
    //    }
    //
    //    private NuovaIstanzaCommand populateFromDomande(FoArjDomande foArjDomande, StepsHelper stepsHelper, boolean domandaDaIntervento) throws Exception {
    //
    //	log.debug("populateFromDomande id=[{}]", foArjDomande.getId().getCodice());
    //	PkId oggId = foArjDomande.getOggetti().getId();
    //	Oggetti ogg = oggettiService.findById(oggId);
    //	String xml = new String(ogg.getOggetto(), "UTF-8");
    //	NuovaIstanzaType nuovaIstanzaType = (NuovaIstanzaType) StcDomainHelper.unmarshalObject(xml, NuovaIstanzaType.class);
    //	NuovaIstanzaCommand command = new NuovaIstanzaCommand(stepsHelper, domandaDaIntervento, foArjDomande.getFoArjServizi());
    //	foArjDomandeHelperService.populateNuovaIstanzaCommand(command, nuovaIstanzaType);
    //	command.setId(foArjDomande.getId().getCodice());
    //	command.setIdDomanda(foArjDomande.getIdDomanda());
    //	return command;
    //    }
    //
    /**
     * questo metodo crea una lista formata dagli step della testataDefault fino allo step INTERVENTO più tutti quelli
     * della testata collegata all'intervento se però la testata collegata all'intervento non ha steps allora lascio
     * tutti quelli della testata di default
     * 
     * @param stepsTestataDefault
     * @param stepsTestataIntervento
     * @return
     */
    protected List<FoArjSteps> getListaStepsUnione(List<FoArjSteps> stepsTestataDefault, List<FoArjSteps> stepsTestataIntervento) {

	List<FoArjSteps> stepsAttuali = new ArrayList<FoArjSteps>();
	if (stepsTestataIntervento.isEmpty()) {
	    stepsAttuali.addAll(stepsTestataDefault);
	} else {
	    //aggiungo tutti gli step della testata di default fino a quello dell'intervento compreso
	    for (FoArjSteps foArjSteps : stepsTestataDefault) {
		stepsAttuali.add(foArjSteps);
		if (foArjSteps.getFoArjStepsBase().getNomeStep().equals(StepsEnum.INTERVENTO.toString())) {
		    break;
		}
	    }
	    //aggiungo tutti gli step della testata associata all'intervento
	    for (FoArjSteps stepInt : stepsTestataIntervento) {
		stepsAttuali.add(stepInt);
	    }
	}
	return stepsAttuali;
    }

    /**
     * questo metodo crea una lista di step da utilizzare per la cancellazione dei dati dalla tabella
     * FO_ARJ_DOMANDE_STEPS_ESEGUITI e dei dati del command.
     * 
     * il metodo aggiunge alla lista degli step da cancellare tutti quelli presenti nella tabella
     * FO_ARJ_DOMANDE_STEPS_ESEGUITI che non sono presenti nella lista stepsAttuali.
     * 
     * @param foArjDomande
     * @param stepsAttuali
     * @return
     */
    //    protected List<FoArjDomandeStepsEseguiti> getListaStepsDaCancellare(FoArjDomande foArjDomande, List<FoArjSteps> stepsAttuali) {
    //
    //	List<FoArjDomandeStepsEseguiti> stepsToClear = new ArrayList<FoArjDomandeStepsEseguiti>();
    //	Set<FoArjDomandeStepsEseguiti> stepsDiBaseEseguiti = foArjDomande.getFoArjDomandeStepsEseguitis();
    //	for (FoArjDomandeStepsEseguiti stepDiBaseEseguito : stepsDiBaseEseguiti) {
    //	    boolean del = true;
    //	    for (FoArjSteps stepAttuale : stepsAttuali) {
    //		if (stepDiBaseEseguito.getFoArjStepsBase().getNomeStep().equals(stepAttuale.getFoArjStepsBase().getNomeStep())) {
    //		    del = false;
    //		    break;
    //		}
    //	    }
    //	    if (del) {
    //		stepsToClear.add(stepDiBaseEseguito);
    //	    }
    //	}
    //	return stepsToClear;
    //    }
    //    @Override
    //    public void clear(NuovaIstanzaCommand cmd, List<FoArjDomandeStepsEseguiti> stepsToClear) {
    //
    //	log.debug("clear");
    //	for (FoArjDomandeStepsEseguiti stepToClear : stepsToClear) {
    //	    StepsEnum step = StepsEnum.valueOf(stepToClear.getFoArjStepsBase().getNomeStep());
    //	    switch (step) {
    //	    case BENVENUTO:
    //		nuovaIstanzaBenvenutoService.clearStep(cmd);
    //		break;
    //	    case INFORMATIVA:
    //		nuovaIstanzaInformativaService.clearStep(cmd);
    //		break;
    //	    case INTERVENTO:
    //		nuovaIstanzaInterventoService.clearStep(cmd);
    //		break;
    //	    case ANAGRAFE:
    //		nuovaIstanzaAnagrafeService.clearStep(cmd);
    //		break;
    //	    case LOCALIZZAZIONE:
    //		nuovaIstanzaLocalizzazioneService.clearStep(cmd);
    //		break;
    //	    case PROCEDIMENTI:
    //		nuovaIstanzaProcedimentiService.clearStep(cmd);
    //		break;
    //	    case ALLEGATI:
    //		nuovaIstanzaAllegatiService.clearStep(cmd);
    //		break;
    //	    case SCHEDE:
    //		nuovaIstanzaSchedeService.clearStep(cmd);
    //		break;
    //	    case ALLEGATI_SCHEDE:
    //		nuovaIstanzaAllegatiSchedeService.clearStep(cmd);
    //		break;
    //	    case ONERI:
    //		nuovaIstanzaOneriService.clearStep(cmd);
    //		break;
    //	    default:
    //		log.error("clear: cancellazione non configurata per lo step [{}]", step);
    //		break;
    //	    }
    //	}
    //    }
    @Override
    public boolean isCambioIntervento(NuovaIstanzaCommand cmd) {

	Integer codiceIntervento = Integer.valueOf(cmd.getIntervento().getCodice());
	boolean result = false;
	if (!codiceIntervento.equals(cmd.getInterventoProcedimenti())) {
	    result = true;
	}
	log.debug("isCambioIntervento: Intervento={}, InterventoProcedimenti={}, esito={}",
		new Object[] { codiceIntervento, cmd.getInterventoProcedimenti(), result });
	return result;
    }
}
