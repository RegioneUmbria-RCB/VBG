package it.gruppoinit.pal.gp.core.features.istanze.cambiointervento;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoli;
import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeMetadati;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.istanze.cambiointervento.metadati.SpostamentoPraticheMetadato;
import it.gruppoinit.pal.gp.core.features.istanze.rest.IstanzaRestBean;
import it.gruppoinit.pal.gp.core.features.istanze.rest.RicercaIstanzeIstanzeResult;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

@Service
public class SpostamentoPraticheServiceImpl implements ISpostamentoPraticheService {

    private static final Logger log = LoggerFactory.getLogger(SpostamentoPraticheServiceImpl.class);
    private ISpostamentoPraticheDAO spostamentoPraticheDAO;
    private AlberoprocService alberoprocService;
    private IstanzeService istanzeService;
    private Dyn2ModellitService dyn2ModellitService;
    private UserSecurityService userSecurityService;

    @Autowired
    public SpostamentoPraticheServiceImpl(ISpostamentoPraticheDAO spostamentoPraticheDAO, AlberoprocService alberoprocService,
	    IstanzeService istanzeService, Dyn2ModellitService dyn2ModellitService, UserSecurityService userSecurityService) {

	this.spostamentoPraticheDAO = spostamentoPraticheDAO;
	this.alberoprocService = alberoprocService;
	this.istanzeService = istanzeService;
	this.dyn2ModellitService = dyn2ModellitService;
	this.userSecurityService = userSecurityService;
    }

    @Override
    public EsitoSpostamentoPratiche spostaPraticheDaInterventoAIntervento(SpostamentoPraticheParams parametri) {

	try {
	    EsitoSpostamentoPratiche esito = new EsitoSpostamentoPratiche();
	    this.validaParametri(parametri);
	    Set<Integer> elencoIstanze = this.spostaPratiche(parametri.getCodiceInterventoOrigine(), parametri.getCodiceInterventoDestinazione());
	    esito.setIstanzeSpostate(elencoIstanze);
	    if (elencoIstanze.isEmpty()) {
		return esito;
	    }
	    if (parametri.sonoPresentiRuoliDaAggiungere()) {
		this.aggiungiRuoli(elencoIstanze, parametri.getIdRuoliDaAggiungere());
	    }
	    if (parametri.sonoPresentiSchedeDaAggiungere()) {
		List<Dyn2ModelliTIstanze> modelliAggiunti = this.aggiungiSchede(elencoIstanze, parametri.getIdSchedeDaAggiungere());
		this.elaboraModelliAggiunti(modelliAggiunti);
	    }
	    this.tracciaSpostamentoPratiche(parametri, elencoIstanze.size());
	    return esito;
	} catch (Exception e) {
	    log.error("Errore BLOCCANTE nello spostamento delle pratiche da intervento a intervento: {}", e);
	    throw new RuntimeException("Impossibile invocare il metodo spostaPraticheDaInterventoAIntervento a causa di:" + e.getMessage());
	}
    }

    private void tracciaSpostamentoPratiche(SpostamentoPraticheParams parametri, Integer conteggioIstanzeSpostate) {

	Responsabili responsabile = (Responsabili) this.userSecurityService.getCurrentlyAuthenticatedUserDetails();
	AuditSpostamentoPratiche.tracciaSpostamento(responsabile.getResponsabile(), parametri.getCodiceInterventoOrigine(),
		parametri.getCodiceInterventoDestinazione(), conteggioIstanzeSpostate);
    }

    @Override
    public VerificaSpostamentoPratiche checkInterventoSelezionabile(Integer codiceInterventoOrigine, Integer codiceInterventoDestinazione) {

	SpostamentoPraticheParams params = new SpostamentoPraticheParams(codiceInterventoOrigine, codiceInterventoDestinazione, null, null);
	try {
	    this.validaParametri(params);
	} catch (Exception e) {
	    return new VerificaSpostamentoPratiche(e.getMessage());
	}
	VerificaSpostamentoPratiche ret = new VerificaSpostamentoPratiche();
	AlberoprocHelper alberoprocHelperNew = alberoprocService.findAlberoprocHelper(codiceInterventoDestinazione);
	for (AlberoprocDyn2modellit alberoprocDyn2modellit : alberoprocHelperNew.getAlberoprocDyn2modellits()) {
	    IdentificativoDescrizioneBean cdb = new IdentificativoDescrizioneBean(alberoprocDyn2modellit.getId().getFkD2mtId(),
		    alberoprocDyn2modellit.getDyn2Modellit().getDescrizioneEstesa());
	    ret.getListaSchedeDinamiche().add(cdb);
	}
	for (AlberoprocRuoli alberoprocRuoli : alberoprocHelperNew.getAlberoprocRuolis()) {
	    IdentificativoDescrizioneBean cdb = new IdentificativoDescrizioneBean(alberoprocRuoli.getId().getFkIdruolo(),
		    alberoprocRuoli.getRuoli().getRuolo());
	    ret.getListaRuoli().add(cdb);
	}
	ret.setNumeroPraticheDaSpostare(istanzeService.countByAlberoproc(codiceInterventoOrigine));
	return ret;
    }

    @Override
    public RicercaIstanzeIstanzeResult cercaPratichePerIntervento(Integer codiceInterventoOrigine, int offset, int limit) {

	List<Istanze> istanzes = istanzeService.findByAlberoproc(codiceInterventoOrigine, offset, limit);
	RicercaIstanzeIstanzeResult result = new RicercaIstanzeIstanzeResult();
	result.setListaIstanze(IstanzaRestBean.fromIstanzeList(istanzes));
	result.setLimit(limit);
	result.setOffset(offset);
	result.setTotal(istanzeService.countByAlberoproc(codiceInterventoOrigine));
	return result;
    }

    @SuppressWarnings("unchecked")
    private void elaboraModelliAggiunti(List<Dyn2ModelliTIstanze> modelliAggiunti) {

	for (Dyn2ModelliTIstanze modelliIstanza : modelliAggiunti) {
	    for (Integer idScheda : modelliIstanza.getIdModelli()) {
		try {
		    String[] error = this.dyn2ModellitService.eseguiScriptAggiornamentoSchedaIstanza(modelliIstanza.getCodiceIstanza(), idScheda);
		    if (error != null && error.length > 0) {
			StringBuilder messaggioErrore = new StringBuilder();
			for (int i = 0; i < error.length; i++) {
			    messaggioErrore.append(error[i]).append(",");
			}
			messaggioErrore.deleteCharAt(messaggioErrore.length() - 1);
			Istanzeeventi istanzeeventi = Istanzeeventi.fromErroreElaborazioneScheda(
				this.istanzeService.findById(new PkId(modelliIstanza.getCodiceIstanza())), messaggioErrore.toString());
			this.spostamentoPraticheDAO.saveEntity(istanzeeventi);
		    }
		} catch (Exception e) {
		    log.error("Errore NON BLOCCANTE nello spostamento delle pratiche da intervento a intervento: {}", e);
		}
	    }
	}
    }

    private List<Dyn2ModelliTIstanze> aggiungiSchede(Set<Integer> elencoIstanze, Set<Integer> idSchedeDaAggiungere) {

	List<Dyn2ModelliTIstanze> retVal = new ArrayList<Dyn2ModelliTIstanze>();
	for (Integer codiceIstanza : elencoIstanze) {
	    Istanze istanza = this.istanzeService.findById(new PkId(codiceIstanza));
	    if (istanza.getChiusura().isStatoChiusura()) {
		continue;
	    }
	    Set<Integer> elenco = this.spostamentoPraticheDAO.schedeMancanti(ORMHelper.getIdcomune(), codiceIstanza, idSchedeDaAggiungere);
	    if (elenco.isEmpty()) {
		continue;
	    }
	    this.spostamentoPraticheDAO.insertSchedeMancanti(ORMHelper.getIdcomune(), codiceIstanza, elenco);
	    retVal.add(new Dyn2ModelliTIstanze(codiceIstanza, elenco));
	}
	return retVal;
    }

    private void aggiungiRuoli(Set<Integer> elencoIstanze, Set<Integer> idRuoliDaAggiungere) {

	this.spostamentoPraticheDAO.aggiungiRuoli(elencoIstanze, idRuoliDaAggiungere);
    }

    @SuppressWarnings("unchecked")
    private Set<Integer> spostaPratiche(Integer codiceInterventoOrigine, Integer codiceInterventoDestinazione) {

	Set<Integer> elenco = this.spostamentoPraticheDAO.findIstanzeDaSpostare(ORMHelper.getIdcomune(), codiceInterventoOrigine);
	//1. Salvo nei metadati il vecchio codiceintervento
	for (Integer codiceIstanza : elenco) {
	    IstanzeMetadati metaDato = IstanzeMetadati.fromMetadati(new SpostamentoPraticheMetadato(codiceIstanza, codiceInterventoOrigine));
	    this.spostamentoPraticheDAO.saveEntity(metaDato);
	}
	//2. Sposto l'intervento
	this.spostamentoPraticheDAO.spostaIntervento(ORMHelper.getIdcomune(), codiceInterventoOrigine, codiceInterventoDestinazione);
	return elenco;
    }

    private Azioni findAzioneIntervento(Integer codiceIntervento) {

	AlberoprocHelper alberoprocHelper = alberoprocService.findAlberoprocHelper(codiceIntervento);
	List<Integer> codiciEndo = new ArrayList<Integer>();
	Set<AlberoprocEndo> alberoprocEndos = alberoprocHelper.getAlberoprocEndos();
	for (AlberoprocEndo alberoprocEndo : alberoprocEndos) {
	    if (BooleanUtils.isTrue(alberoprocEndo.getFlagPrincipale())) {
		codiciEndo.add(alberoprocEndo.getId().getCodiceinventario());
	    }
	}
	return alberoprocService.findAzioniDaEndoOAlberoproc(alberoprocHelper.getCurrentAlberoproc(), codiciEndo);
    }

    private void validaParametri(SpostamentoPraticheParams parametri) {

	if (parametri == null) {
	    throw new IllegalArgumentException("I parametri sono null");
	}
	if (parametri.getCodiceInterventoOrigine() == null) {
	    throw new IllegalArgumentException("Il parametro codiceInterventoOrigine non è valorizzato");
	}
	if (parametri.getCodiceInterventoDestinazione() == null) {
	    throw new IllegalArgumentException("Il parametro codiceInterventoDestinazione non è valorizzato");
	}
	Alberoproc interventoOrigine = this.alberoprocService.findById(new PkId(parametri.getCodiceInterventoOrigine()));
	if (interventoOrigine == null) {
	    throw new IllegalArgumentException("Il parametro codiceInterventoOrigine non è valorizzato");
	}
	if (Boolean.TRUE.equals(interventoOrigine.getScPadre())) {
	    throw new IllegalArgumentException("L'intervento " + interventoOrigine.getDescrizioneCompleta() + " è una cartella");
	}
	Alberoproc interventoDestinazione = this.alberoprocService.findById(new PkId(parametri.getCodiceInterventoDestinazione()));
	if (interventoDestinazione == null) {
	    throw new IllegalArgumentException("Il parametro codiceInterventoDestinazione non è valorizzato");
	}
	if (Boolean.TRUE.equals(interventoDestinazione.getScPadre())) {
	    throw new IllegalArgumentException("L'intervento " + interventoDestinazione.getDescrizioneCompleta() + " è una cartella");
	}
	Azioni azioneDestinazione = this.findAzioneIntervento(parametri.getCodiceInterventoOrigine());
	Azioni azioneSorgente = this.findAzioneIntervento(parametri.getCodiceInterventoDestinazione());
	if (!azioneDestinazione.getAzAzione().equals(azioneSorgente.getAzAzione())) {
	    throw new IllegalArgumentException("Le voci di intervento definiscono azioni differenti. L'intervento \"" +
		    interventoOrigine.getDescrizioneCompleta() +
		    "\" definisce l'azione " +
		    azioneSorgente.getAzAzione() +
		    " mentre l'intervento \"" +
		    interventoDestinazione.getDescrizioneCompleta() +
		    "\" definisce l'azione " +
		    azioneDestinazione.getAzAzione());
	}
    }
}
