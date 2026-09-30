package it.gruppoinit.pal.gp.core.features.stradario.allineamento.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.helper.AllineamentoStradarioHelper;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.SitService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.exception.RemoteCallException;
import it.gruppoinit.pal.gp.core.utils.BaseEnvironment;
import it.gruppoinit.pal.gp.core.utils.LoggerAllineamentostradario;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.FiltroRicercaListaVie;

@Component
public class StradarioManagerV2Impl extends BaseEnvironment implements IStradarioManagerV2 {

    private static final Logger log = LoggerFactory.getLogger(StradarioManagerV2Impl.class);
    private ComuniassociatiService comuniassociatiService;
    private ComuniService comuniService;
    private SitService sitService;
    private StradarioService stradarioService;

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setStradarioService(StradarioService stradarioService) {

	this.stradarioService = stradarioService;
    }

    @Autowired
    public void setSitService(SitService sitService) {

	this.sitService = sitService;
    }

    @Override
    public synchronized void allineaStradario(Set<String> codiciComune, Integer codiceStradarioIniziale) {

	try {
	    boolean isAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO);
	    if (isAttiva) {
		log.debug("allineaStradario# Allineamento stradario per l'installazione {}", ORMHelper.getIdcomune());
		if (codiciComune == null || codiciComune.isEmpty()) {
		    List<Comuniassociati> comuniassociatis = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
		    codiciComune = new HashSet<String>();
		    for (Comuniassociati comuniassociati : comuniassociatis) {
			codiciComune.add(comuniassociati.getComune().getCodicecomune());
		    }
		}
		this.updateAllineaStradario(codiciComune, codiceStradarioIniziale);
	    } else {
		log.debug("allineaStradario# Sit non attivo per l'installazione {}", ORMHelper.getIdcomuneAlias());
	    }
	} catch (Exception e) {
	    log.error("allineaStradario# Errore durante l'allineamento dello stradario per l'installazione {}", ORMHelper.getIdcomuneAlias(), e);
	} finally {
	    resetThreadLocalVars();
	}
	log.info("allineaStradario# termine processo di allineamento stradario");
    }

    private Map<String, Map<String, AllineamentoStradarioHelper>> updateAllineaStradario(Set<String> codiciComuni, Integer codiceStradarioIniziale) {

	Map<String, Map<String, AllineamentoStradarioHelper>> risultatoMap = new HashMap<String, Map<String, AllineamentoStradarioHelper>>();
	Map<String, AllineamentoStradarioHelper> helpers = new HashMap<String, AllineamentoStradarioHelper>();
	List<Comuni> listComuni = new ArrayList<Comuni>();
	for (String codiceComune : codiciComuni) {
	    Comuni comune = comuniService.findById(codiceComune);
	    listComuni.add(comune);
	    AllineamentoStradarioHelper helper = this.processaComune(comune, codiceStradarioIniziale);
	    helpers.put(helper.getCodicecomune(), helper);
	}
	risultatoMap.put("RISULTATO", helpers);
	this.logAllineamento(listComuni, risultatoMap);
	return risultatoMap;
    }

    private AllineamentoStradarioHelper processaComune(Comuni comune, Integer codiceStradarioIniziale) {

	AllineamentoStradarioHelper helper = AllineamentoStradarioHelper.fromComune(comune);
	int inserito = 0;
	int aggiornato = 0;
	List<String> errori = new ArrayList<String>();
	try {
	    //1. Disabilito tutti gli stradari
	    List<Stradario> vie = recuperaVieDaSit(comune.getCodicecomune());
	    if (vie.isEmpty()) {
		throw new FunzioneBusinessRemotaException(
			"Non è stato possibile recuperare l'elenco delle vie dal servizio sit dell'ente " + comune.getCodicecomune());
	    }
	    this.disabilitaStradari(comune.getCodicecomune(), codiceStradarioIniziale);
	    for (Stradario stradario : vie) {
		if (processaStradario(stradario, comune.getCodicecomune(), errori)) {
		    aggiornato++;
		} else {
		    inserito++;
		}
	    }
	} catch (Exception e) {
	    gestisciErroreComune(comune.getCodicecomune(), helper, errori, e);
	}
	helper.setNumAggiornati(aggiornato);
	helper.setNumAggiunti(inserito);
	helper.setErrori(errori);
	return helper;
    }

    private void disabilitaStradari(String codiceComune, Integer codiceStradarioIniziale) {

	this.stradarioService.disabilitaStradari(codiceComune, codiceStradarioIniziale);
    }

    private boolean processaStradario(Stradario stradario, String codComune, List<String> errori) {

	try {
	    String codViario = stradario.getCodviario();
	    String codiceComune = getCodiceComune(stradario);
	    Stradario aggiornato = recuperaDatiAggiornati(codViario, codiceComune, stradario);
	    if (aggiornato == null) {
		stradarioService.insert(stradario);
		return false;
	    }
	    stradarioService.update(aggiornato);
	    return true;
	} catch (Exception e) {
	    gestisciErroreStradario(stradario.getCodviario(), codComune, errori, e);
	    return false;
	}
    }

    private String getCodiceComune(Stradario stradario) {

	if (stradario.getComune() != null && StringUtils.isNotBlank(stradario.getComune().getCodicecomune())) {
	    return stradario.getComune().getCodicecomune();
	}
	return null;
    }

    private void gestisciErroreStradario(String codViario, String codCom, List<String> errori, Exception e) {

	log.error("Errore inserimento/aggiornamento stradario {} comune {}", new Object[] { codViario, codCom, e });
	errori.add("Errore durante aggiornamento stradario " + codViario + " per comune " + codCom + "<br />");
    }

    private void gestisciErroreComune(String codCom, AllineamentoStradarioHelper helper, List<String> errori, Exception e) {

	log.error("Errore chiamata ws per comune {}", codCom, e);
	helper.setIsErrore(true);
	errori.add("Errore durante chiamata ws per comune " + codCom + "<br />");
    }

    private void logAllineamento(List<Comuni> comuni, Map<String, Map<String, AllineamentoStradarioHelper>> risultato) {

	LoggerAllineamentostradario.logAllineamentoStradario(comuni, risultato);
    }

    private List<Stradario> recuperaVieDaSit(String codiceComune) throws RemoteCallException {

	List<String> list = new ArrayList<String>();
	list.add(codiceComune);
	log.debug("Invoco il ws getListaVie per il comune : {}..........", codiceComune);
	return sitService.getListaVie(ORMHelper.getToken(), FiltroRicercaListaVie.Tutte, list);
    }

    private Stradario recuperaDatiAggiornati(String codViario, String codiceComune, Stradario stradarioSIT) {

	Stradario stradarioDb = this.stradarioService.findByCodiceViario(codViario, codiceComune, true);
	if (stradarioDb != null) {
	    stradarioDb.setCodviario(stradarioSIT.getCodviario());
	    if (StringUtils.isNotBlank(stradarioDb.getPrefisso())) {
		if (StringUtils.isNotBlank(stradarioSIT.getPrefisso())) {
		    stradarioDb.setPrefisso(stradarioSIT.getPrefisso());
		}
	    } else {
		stradarioDb.setPrefisso(StringUtils.defaultString(stradarioSIT.getPrefisso(), "-"));
	    }
	    stradarioDb.setDescrizione(stradarioSIT.getDescrizione());
	    if (StringUtils.isNotBlank(stradarioSIT.getLocfraz())) {
		stradarioDb.setLocfraz(stradarioSIT.getLocfraz());
	    }
	    stradarioDb.setDatavalidita(stradarioSIT.getDatavalidita());
	}
	return stradarioDb;
    }
}
