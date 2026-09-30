package it.gruppoinit.pal.gp.core.features.suapxml.upgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.dao.VerticalizzazioniDAO;
import it.gruppoinit.pal.gp.core.dao.VerticalizzazioniparametriDAO;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazionibase;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.VerticalizzazioniparametribaseId;
import it.gruppoinit.pal.gp.core.features.suapxml.ISuapXmlService;
import it.gruppoinit.pal.gp.core.features.suapxml.VerticalizzazioneSuapXmlServiceImpl;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

@Component("upgrSuapXml")
public class UpgrSuapXmlTask extends BaseJavaTask {

    private static final Logger logger = LoggerFactory.getLogger(UpgrSuapXmlTask.class);
    private ISuapXmlService suapXmlService;
    //Utilizzo i DAO e non i Service per non avere il controllo dell'utente loggato che in fase di setup non ho
    private VerticalizzazioniDAO verticalizzazioniDAO;
    private VerticalizzazioniparametriDAO verticalizzazioniparametriDAO;

    @Autowired
    public void setSuapXmlService(ISuapXmlService suapXmlService) {

	this.suapXmlService = suapXmlService;
    }

    @Autowired
    public void setVerticalizzazioniDAO(VerticalizzazioniDAO verticalizzazioniDAO) {

	this.verticalizzazioniDAO = verticalizzazioniDAO;
    }

    @Autowired
    public void setVerticalizzazioniparametriDAO(VerticalizzazioniparametriDAO verticalizzazioniparametriDAO) {

	this.verticalizzazioniparametriDAO = verticalizzazioniparametriDAO;
    }

    @Override
    public void initialize() throws SetupRunException {

	//implementazione non necessaria
    }

    @Override
    public int run(Session arg0) throws SetupRunException {

	logger.debug("1. Verifico la presenza di configurazioni riguardanti il SUAP xml sulla verticalizzazione COMPORTAMENTI_ISTANZE");
	//1. Verifico la presenza di configurazioni riguardanti il SUAP xml sulla verticalizzazione COMPORTAMENTI_ISTANZE
	List<VecchioParametroSuapXml> vecchiParametri = this.suapXmlService.recuperaVecchiParametriPerUPGR();
	logger.debug("2. Ciclo i risultati e sposto sulla nuova verticalizzazione");
	//2. Ciclo i risultati e sposto sulla nuova verticalizzazione
	Map<String, List<String>> verticalizzazioniAttivate = new HashMap<String, List<String>>();
	for (VecchioParametroSuapXml vecchioParametro : vecchiParametri) {
	    String idComune = vecchioParametro.getIdComune();
	    String software = vecchioParametro.getSoftware();
	    String nomeParametro = vecchioParametro.getNomeParametro();
	    String valore = vecchioParametro.getValore();
	    logger.debug("2.1 Verifico se per l'idcomune corrente ho già attivato la verticalizzazione");
	    //2.1 Verifico se per l'idcomune corrente ho già attivato la verticalizzazione
	    List<String> softwareAttivati = verticalizzazioniAttivate.get(idComune);
	    if (softwareAttivati == null) {
		softwareAttivati = new ArrayList<String>();
	    }
	    //2.2 Se non attivata, la attivo e aggiorno la mappa
	    logger.debug("2.2 Se non attivata, la attivo e aggiorno la mappa");
	    if (!softwareAttivati.contains(software)) {
		Verticalizzazioni nuovaVerticalizzazione = new Verticalizzazioni();
		nuovaVerticalizzazione.getId().setIdcomune(idComune);
		nuovaVerticalizzazione.setAttivo(1);
		nuovaVerticalizzazione.setSoftware(new Software(software));
		nuovaVerticalizzazione
			.setVerticalizzazionibase(new Verticalizzazionibase(VerticalizzazioneSuapXmlServiceImpl.NOME_VERTICALIZZAZIONE));
		nuovaVerticalizzazione.setComune(null);
		logger.info("2.2 Inserimento della verticalizzazione {}", VerticalizzazioneSuapXmlServiceImpl.NOME_VERTICALIZZAZIONE);
		this.verticalizzazioniDAO.insert(nuovaVerticalizzazione);
		verticalizzazioniAttivate.put(idComune, softwareAttivati);
	    }
	    logger.debug("2.3 Verifico i parametri da inserire");
	    //2.3 Verifico i parametri da inserire
	    if ("GENERA_PRATICA_SUAP".equalsIgnoreCase(nomeParametro)) {
		String nuovoValore = "S".equalsIgnoreCase(valore) ? "1" : "0";
		Verticalizzazioniparametri nuovoParametro = new Verticalizzazioniparametri();
		nuovoParametro.getId().setIdcomune(idComune);
		nuovoParametro.setValore(nuovoValore);
		nuovoParametro.setSoftware(new Software(software));
		nuovoParametro.getVerticalizzazioniparametribase()
			.setId(new VerticalizzazioniparametribaseId(VerticalizzazioneSuapXmlServiceImpl.NOME_VERTICALIZZAZIONE,
				VerticalizzazioneSuapXmlServiceImpl.PAR_VIS_BOTTONE_SU_PROT_MOVIMENTO));
		nuovoParametro.setComune(null);
		logger.debug("2.4 Inserimento  del parametro {}", VerticalizzazioneSuapXmlServiceImpl.PAR_VIS_BOTTONE_SU_PROT_MOVIMENTO);
		this.verticalizzazioniparametriDAO.insert(nuovoParametro);
	    } else if ("GENERA_PRATICA_SUAP_URL".equalsIgnoreCase(nomeParametro)) {
		Verticalizzazioniparametri nuovoParametro = new Verticalizzazioniparametri();
		nuovoParametro.getId().setIdcomune(idComune);
		nuovoParametro.setValore(valore);
		nuovoParametro.setSoftware(new Software(software));
		nuovoParametro.getVerticalizzazioniparametribase().setId(new VerticalizzazioniparametribaseId(
			VerticalizzazioneSuapXmlServiceImpl.NOME_VERTICALIZZAZIONE, VerticalizzazioneSuapXmlServiceImpl.PAR_URL));
		nuovoParametro.setComune(null);
		logger.debug("2.4 Inserimento del parametro {}", VerticalizzazioneSuapXmlServiceImpl.PAR_URL);
		this.verticalizzazioniparametriDAO.insert(nuovoParametro);
	    } else if ("GENERA_PRATICA_SUAP_VALIDA".equalsIgnoreCase(nomeParametro)) {
		String nuovoValore = "S".equalsIgnoreCase(valore) ? "1" : "0";
		Verticalizzazioniparametri nuovoParametro = new Verticalizzazioniparametri();
		nuovoParametro.getId().setIdcomune(idComune);
		nuovoParametro.setValore(nuovoValore);
		nuovoParametro.setSoftware(new Software(software));
		nuovoParametro.getVerticalizzazioniparametribase().setId(new VerticalizzazioniparametribaseId(
			VerticalizzazioneSuapXmlServiceImpl.NOME_VERTICALIZZAZIONE, VerticalizzazioneSuapXmlServiceImpl.PAR_VALIDA));
		nuovoParametro.setComune(null);
		logger.debug("2.4 Inserimento del parametro {}", VerticalizzazioneSuapXmlServiceImpl.PAR_VALIDA);
		this.verticalizzazioniparametriDAO.insert(nuovoParametro);
	    }
	}
	logger.debug("3. Termine spostamento parametri verticalizzazione");
	this.verticalizzazioniparametriDAO.commitFlush();
	return 0;
    }
}
