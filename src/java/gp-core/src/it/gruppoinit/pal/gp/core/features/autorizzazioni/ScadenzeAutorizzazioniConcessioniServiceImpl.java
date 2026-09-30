package it.gruppoinit.pal.gp.core.features.autorizzazioni;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniHelper;
import it.gruppoinit.pal.gp.core.domain.helper.SoftwareattiviDTO;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniSubentriCommand;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService.ENUM_COPIA_ONERI;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.auditing.ChiusuraAutomaticaAutorizzazioniLogger;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioniScadenzeAutorizzazioniConcessioniException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri.EsitoElaborazioneSubentri;
import it.gruppoinit.pal.gp.core.service.ConcessionicausaliService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.SoftwareattiviService;

@Service
public class ScadenzeAutorizzazioniConcessioniServiceImpl implements IScadenzeAutorizzazioniConcessioniService {

    private static final String CONCESSIONE_ACQUISIZIONE_RIOTTENIMENTO = "Rientro in possesso";
    private static final String CONCESSIONE_CESSAZIONE_RIOTTENIMENTO = "Scadenza naturale affitto";
    private static final String CONCESSIONE_CESSAZIONE_SISTEMA = "Cessazione di sistema";
    @Autowired
    private SoftwareattiviService softwareattiviService;
    @Autowired
    private AutorizzazioniDAO autorizzazioniDAO;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private MercatiConfigurazioneService mercatiConfigurazioneService;
    @Autowired
    private ConcessionicausaliService concessionicausaliService;

    @Override
    public void sistemaScadenzeDelleAutorizzazioniPerAlias(String alias) throws OperazioniScadenzeAutorizzazioniConcessioniException {

	String software = ORMHelper.getSoftware();
	// PER OGNI ALIAS
	List<SoftwareattiviDTO> softares = softwareattiviService.findAllSoftwareattiviDTO();
	ChiusuraAutomaticaAutorizzazioniLogger auditLogger = new ChiusuraAutomaticaAutorizzazioniLogger("Attività di sistema",
		"Inizio attività per alias: " + alias);
	auditLogger.log();
	for (SoftwareattiviDTO sa : softares) {
	    if (sa.isAttivo()) {
		// PER OGNI SOFTWARE ATTIVO
		processaAutorizzazioniPerSoftware(sa.getCodice(), auditLogger);
	    }
	}
	auditLogger.logFineMetodo();
	//
	//
	//N.B.
	//"non si computa il giorno nel corso del quale cade il momento iniziale del termine e 
	//la prescrizione si verifica con lo spirare dell'ultimo istante del giorno finale. 
	//Se il termine scade in un giorno festivo, è prorogato di diritto al giorno seguente non festivo"
	ORMHelper.setSoftware(software);
    }

    private void processaAutorizzazioniPerSoftware(String software, ChiusuraAutomaticaAutorizzazioniLogger auditLogger) {

	ORMHelper.setSoftware(software);
	// SE CONCESSIONICAUSALI NON VUOTO
	auditLogger.setMessaggio("Processo Autorizzazioni per software " + software).log();
	if (!concessionicausaliService.findAll(null, null).isEmpty()) {
	    MercatiConfigurazione m = checkCausaliRientroPossessoCessazioneSistema();
	    autorizzazioniDAO.commitFlush();
	    autorizzazioniDAO.clear();
	    Date oggi = Calendar.getInstance().getTime();
	    //		RICERCA 1 ==> AUTORIZZAZIONI NON CESSATE
	    //			-> se data attuale > data scadenza la concessione è scaduta ma non cessata -> cessa la concessione alla data scadenza
	    List<Integer> auts = autorizzazioniDAO.findAutorizzazioniScaduteAllaDataENonCessate(oggi);
	    auditLogger.setMessaggio("Autorizzazioni scadute e attive ? " + auts.size()).log();
	    for (Integer idAutorizzazione : auts) {
		Autorizzazioni aut = autorizzazioniDAO.findById(new PkId(idAutorizzazione));
		autorizzazioniService.cessaAutorizzazione(idAutorizzazione, aut.getDatascadenza(), m.getCausaleCessSistema().getId().getCodice());
		auditLogger.setMessaggio("Cessata autorizzazione con id: " + idAutorizzazione + " alla data " + aut.getDatascadenza()).log();
	    }
	    autorizzazioniDAO.commitFlush();
	    autorizzazioniDAO.clear();
	    //		RICERCA 2 ==> AUTORIZZAZIONI CESSATE CON FLAG CESSATA = 0
	    //			-> se data attuale > data cessazione -> cessa la concessione alla data cessazione
	    auts = autorizzazioniDAO.findAutorizzazioniCessateAllaDataEAncoraAttive(oggi);
	    auditLogger.setMessaggio("Autorizzazioni cessate e attive ? " + auts.size()).log();
	    for (Integer idAutorizzazione : auts) {
		Autorizzazioni aut = autorizzazioniDAO.findById(new PkId(idAutorizzazione));
		aut.setFlagAttiva(Boolean.FALSE);
		autorizzazioniService.cessaAutorizzazione(idAutorizzazione, aut.getDataCessazione(), m.getCausaleCessSistema().getId().getCodice());
		auditLogger.setMessaggio("Cessata autorizzazione con id: " + idAutorizzazione + " alla data cessazione" + aut.getDataCessazione())
			.log();
	    }
	    autorizzazioniDAO.commitFlush();
	    autorizzazioniDAO.clear();
	    gestisciFineAffitto(oggi, m, auditLogger);
	    autorizzazioniDAO.commitFlush();
	    autorizzazioniDAO.clear();
	}
    }

    private void gestisciFineAffitto(Date oggi, MercatiConfigurazione m, ChiusuraAutomaticaAutorizzazioniLogger auditLogger) {

	//		RICERCA 3 ==> DATA AFFITTO SCADUTO
	//			-> se data attuale > data fine affitto 				
	//				--> NON sono presenti le causali di sistema Rientro in possesso, Scadenza naturale affitto?
	//					--> Registra le causali e le salva sulle verticalizzazioni ( acq: Rientro in possesso, cess: Scadenza naturale affitto )			
	//				-> Su istanza in cui è stato fatto l'affitto
	//					-> causale acquisizione / causale cessazione
	List<Integer> auts = autorizzazioniDAO.findAutorizzazioniConAffittoScadutoENonRientrateInPossesso(oggi);
	auditLogger.setMessaggio("Autorizzazioni con affitto scaduto ? " + auts.size()).log();
	if (!auts.isEmpty()) {
	    for (Integer idAutorizzazione : auts) {
		AutorizzazioniSubentriCommand c = popolaCommand(m, autorizzazioniService.findById(new PkId(idAutorizzazione)));
		if (c.getIstanzaDiSubentro() == null) {
		    auditLogger
			    .logError("L'AUTORIZZAZIONE con " + autorizzazioniService.findById(new PkId(idAutorizzazione)).getTransientEstremiAut() +
				      ", id  " + idAutorizzazione + " e data " + c.getDataCessazione() + " NON HA ISTANZA ");
		} else {
		    try {
			autorizzazioniService.insertSubentri(c);
			auditLogger.setMessaggio(
				"Subentro per fine affitto effettuato per autorizzazione id " + idAutorizzazione + " e data " + c.getDataCessazione())
				.log();
		    } catch (Exception e) {
			auditLogger.logError("Nel subentro per fine affitto per autorizzazione id " + idAutorizzazione + " e data " +
					     c.getDataCessazione() + ". Dettagli:" + e.getMessage(),
				e);
		    }
		}
	    }
	}
    }

    private AutorizzazioniSubentriCommand popolaCommand(MercatiConfigurazione m, Autorizzazioni aut) {

	AutorizzazioniSubentriCommand c = new AutorizzazioniSubentriCommand();
	c.setCausaleAcquisizione(m.getCausaleAcqRiottenimento());
	c.setCausaleCessazione(m.getCausaleCessRiottenimento());
	c.setDataCessazione(aut.getDataFineAffitto());
	Set<AutorizzazioniHelper> lAuts = new HashSet<AutorizzazioniHelper>(1);
	c.setSubentriComportamentoOneri(ENUM_COPIA_ONERI.NON_COPIARE_ONERI_NON_PAGATI.name());
	AutorizzazioniHelper hlp = new AutorizzazioniHelper();
	hlp.setFlagMantieniNumero(true);
	hlp.setAutorizzazione(aut);
	hlp.setDaSubentrare(true);
	c.setEsito(new EsitoElaborazioneSubentri());
	lAuts.add(hlp);
	if (!hlp.getAutorizzazione().getAutorizzazioniConcessionisForFkAutconcAutatt().isEmpty()) {
	    Autorizzazioni autColl = null;
	    for (AutorizzazioniConcessioni autC : hlp.getAutorizzazione().getAutorizzazioniConcessionisForFkAutconcAutatt()) {
		autColl = autC.getAutorizzazioniByFkAutconcAutcoll();
		if ((autColl != null)) {
		    break;
		}
	    }
	    if (autColl != null) {
		AutorizzazioniHelper hlpC = new AutorizzazioniHelper();
		hlpC.setFlagMantieniNumero(true);
		hlpC.setAutorizzazione(autColl);
		hlpC.setDaSubentrare(true);
		hlp.setAutorizzazioneCollegataHelper(hlpC);
	    }
	}
	c.setIstanzaDiSubentro(hlp.getAutorizzazione().getIstanza());
	c.setListAutDaSubentrare(lAuts);
	return c;
    }

    private MercatiConfigurazione checkCausaliRientroPossessoCessazioneSistema() {

	MercatiConfigurazione m = mercatiConfigurazioneService.findById(new MercatiConfigurazioneId());
	if (m == null) {
	    m = MercatiConfigurazione.emptyEntity();
	    mercatiConfigurazioneService.insert(m);
	}
	Concessionicausali cAcq = m.getCausaleAcqRiottenimento();
	Concessionicausali cCess = m.getCausaleCessRiottenimento();
	Concessionicausali cCessSistema = m.getCausaleCessSistema();
	boolean doUpdate = false;
	if (cAcq == null) {
	    cAcq = new Concessionicausali();
	    cAcq.setCausalestorico(false);
	    cAcq.setDescrizione(CONCESSIONE_ACQUISIZIONE_RIOTTENIMENTO);
	    cAcq.setFlagCausaliAffitto(false);
	    concessionicausaliService.insert(cAcq);
	    m.setCausaleAcqRiottenimento(cAcq);
	    doUpdate = true;
	}
	if (cCess == null) {
	    cCess = new Concessionicausali();
	    cCess.setCausalestorico(true);
	    cCess.setDescrizione(CONCESSIONE_CESSAZIONE_RIOTTENIMENTO);
	    cCess.setFlagCausaliAffitto(false);
	    concessionicausaliService.insert(cCess);
	    m.setCausaleCessRiottenimento(cCess);
	    doUpdate = true;
	}
	if (cCessSistema == null) {
	    cCessSistema = new Concessionicausali();
	    cCessSistema.setCausalestorico(true);
	    cCessSistema.setDescrizione(CONCESSIONE_CESSAZIONE_SISTEMA);
	    cCessSistema.setFlagCausaliAffitto(false);
	    concessionicausaliService.insert(cCessSistema);
	    m.setCausaleCessSistema(cCessSistema);
	    doUpdate = true;
	}
	if (doUpdate) {
	    mercatiConfigurazioneService.update(m);
	}
	return m;
    }
}
