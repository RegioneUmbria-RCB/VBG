package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Borsellino;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAccettazioni;
import it.gruppoinit.pal.gp.core.domain.BorsellinoConfigComune;
import it.gruppoinit.pal.gp.core.domain.BorsellinoConfigurazione;
import it.gruppoinit.pal.gp.core.domain.BorsellinoRicariche;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.FoArconfigurazione;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.areariservata.IVerticalizzazioneAreaRiservataService;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi.LayouttestiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IBorsellinoAccettazioniDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IBorsellinoDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.IBorsellinoConfigComuneDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.IBorsellinoConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.IBorsellinoInformativeDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.IBorsellinoRicaricheDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.BorsellinoException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.ConfigurazioneAppAmbulanti;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.ConfigurazioneAutorizzazioniAppAmbulanti;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.ConfigurazioneBorsellino;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.ConfigurazioneRicaricaComune;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.ConfigurazioneRicaricaComuneImportoLibero;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.ConfigurazioneRicaricheApp;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EtichettaApp;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.InformativeAbbonamento;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.TagliRicaricaAbbonamento;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioneComportamentiMercatiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioniAnagrafeVerificaMailService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.FoArconfigurazioneService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

@Service
public class ConfigurazioneAppAmbulantiServiceImpl implements IConfigurazioneAppAmbulantiService {

    private static final String ETICHETTE_APP_AMBULANTI = "app-ambulanti.";
    @Autowired
    private IBorsellinoConfigurazioneDAO borsellinoConfigurazioneDAO;
    @Autowired
    private LayouttestiService layouttestiService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private IBorsellinoRicaricheDAO borsellinoRicaricheDAO;
    @Autowired
    private IBorsellinoInformativeDAO borsellinoInformativeDAO;
    @Autowired
    private IBorsellinoAccettazioniDAO borsellinoAccettazioniDAO;
    @Autowired
    private IBorsellinoConfigComuneDAO borsellinoConfigComuneDAO;
    @Autowired
    private IBorsellinoDAO borsellinoDAO;
    @Autowired
    private IVerticalizzazioniAnagrafeVerificaMailService verticalizzazioniAnagrafeVerificaMailService;
    @Autowired
    private IVerticalizzazioneComportamentiMercatiService mVerticalizzazioneComportamentiMercatiService;
    @Autowired
    private IVerticalizzazioneAreaRiservataService vertAreaRiservataService;
    @Autowired
    private FoArconfigurazioneService foArconfigurazioneService;
    @Autowired
    private SoftwareService softwareService;

    @Override
    public ConfigurazioneAppAmbulanti getConfigurazione() throws InvalidConfigurationException {

	ConfigurazioneAppAmbulanti c = new ConfigurazioneAppAmbulanti();
	loginUrl(c);
	setBorsellino(c);
	setAutorizzazioni(c);
	c.setAttivaVerificaMail(attivaVerificaMail());
	setEtichette(c);
	return c;
    }

    private void loginUrl(ConfigurazioneAppAmbulanti c) {

	FoArconfigurazione conf = foArconfigurazioneService.findBySoftware(softwareService.findById(ORMHelper.getSoftware()));
	SecurityParams tipoLogin = WebConstants.SecurityParams.AUTHENTICATION_GATEWAY_FO_URL;
	String param = conf == null ? null : conf.getNomeParametroLoginUrl();
	if (StringUtils.isNotBlank(param) && param.equals(WebConstants.SecurityParams.AUTHENTICATION_GATEWAY_URLCIE.name())) {
	    tipoLogin = WebConstants.SecurityParams.AUTHENTICATION_GATEWAY_URLCIE;
	}
	String externalAuthUrl = WebConstants.getSecurityParamValue(tipoLogin);
	String urlOverride = vertAreaRiservataService.getUrlAuthenticationOverride();
	if (StringUtils.isNotBlank(urlOverride)) {
	    externalAuthUrl = urlOverride;
	}
	c.setLoginUrl(externalAuthUrl + "?" + WebConstants.IDCOMUNE_ALIAS + "=" + ORMHelper.getIdcomuneAlias() + "&" + WebConstants.SOFTWARE + "=" +
		      ORMHelper.getSoftware() + "&contesto=UTE");
    }

    private void setEtichette(ConfigurazioneAppAmbulanti c) {

	List<EtichettaApp> l = new ArrayList<EtichettaApp>();
	List<EtichettaApp> etichette = layouttestiService.findEtichetteConPrefisso(ETICHETTE_APP_AMBULANTI);
	if (!etichette.isEmpty()) {
	    l.addAll(etichette);
	}
	c.setEtichette(l);
    }

    private void setBorsellino(ConfigurazioneAppAmbulanti c) {

	BorsellinoConfigurazione cfg = this.borsellinoConfigurazioneDAO.findConfigurazione();
	if (cfg != null) {
	    c.setBorsellino(ConfigurazioneBorsellino.fromBorsellinoConfigurazione(cfg));
	}
    }

    @Override
    public ConfigurazioneRicaricheApp getConfigurazioneRicariche(Integer codiceAnagrafe) {

	ConfigurazioneRicaricheApp ret = new ConfigurazioneRicaricheApp();
	
	BorsellinoConfigurazione configurazione = borsellinoConfigurazioneDAO.findConfigurazione();
	ret.setImportoMassimoBorsellino(configurazione.getImportomassimo());
	
	Map<String, ConfigurazioneRicaricaComune> mRicariche = new HashMap<String, ConfigurazioneRicaricaComune>();
	List<Comuniassociati> cas = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	for (Comuniassociati ca : cas) {
	    mRicariche.put(ca.getId().getCodicecomune(), forCodiceComune(ca.getId().getCodicecomune(), ca.getComune().getComune(), codiceAnagrafe));
	}
	ret.getComuni().addAll(new ArrayList<ConfigurazioneRicaricaComune>(mRicariche.values()));
	return ret;
    }

    private ConfigurazioneRicaricaComune forCodiceComune(String codiceComune, String comune, Integer codiceAnagrafe) {

	ConfigurazioneRicaricaComune ret = new ConfigurazioneRicaricaComune(codiceComune, comune);
	List<BorsellinoRicariche> ricariche = borsellinoRicaricheDAO.findByCodiceComune(codiceComune);
	boolean ricaricabile = false;
	try {
	    ricaricabile = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune).isAttiva() //
		    && !ricariche.isEmpty();
	    // ANCHE SE NON SONO DISPONIBILI TAGLI DI RICARICHE 	    
	} catch (Exception e) {
	    ricaricabile = false;
	}
	ret.setRicaricabile(ricaricabile);
	List<InformativeAbbonamento> informative = InformativeAbbonamento
		.fromInformative(borsellinoInformativeDAO.findByCodiceComuneAttive(codiceComune));
	ret.setInformative(informative);
	try {
	    Borsellino borsellino = borsellinoDAO.findBorsellinoAttivoByCodiceAnagrafe(codiceAnagrafe);
	    if (borsellino != null && !informative.isEmpty()) {
		//  escludere le informative già approvate
		ret.setInformative(getInformativeDaAccettare(borsellino.getId().getCodice(), informative));
	    }
	} catch (BorsellinoException e) {
	    // NON FACCIO NIENTE
	}
	BorsellinoConfigComune trovato = borsellinoConfigComuneDAO.findByCodiceComune(codiceComune);
	if (trovato != null) {
	    ret.setMessaggioNonRicaricabile(trovato.getMsgNodoPagNonDisp());
	}
	ret.setPagamentoLibero(ConfigurazioneRicaricaComuneImportoLibero.fromRicaricheLibere(ricariche));
	ret.setTagliRicariche(TagliRicaricaAbbonamento.fromRicaricheFisse(ricariche));
	return ret;
    }

    private List<InformativeAbbonamento> getInformativeDaAccettare(Integer idBorsellino, List<InformativeAbbonamento> informative) {

	List<BorsellinoAccettazioni> accettazioni = borsellinoAccettazioniDAO.findByBorsellino(idBorsellino);
	List<InformativeAbbonamento> informativeFinale = new ArrayList<InformativeAbbonamento>();
	for (InformativeAbbonamento informativeAbbonamento : informative) {
	    boolean trovata = false;
	    for (BorsellinoAccettazioni acct : accettazioni) {
		if (acct.getInformativaId().equals(informativeAbbonamento.getId())) {
		    trovata = true;
		    break;
		}
	    }
	    if (!trovata) {
		informativeFinale.add(informativeAbbonamento);
	    }
	}
	return informativeFinale;
    }

    private boolean attivaVerificaMail() {

	return verticalizzazioniAnagrafeVerificaMailService.isAttiva();
    }

    private void setAutorizzazioni(ConfigurazioneAppAmbulanti c) {

	ConfigurazioneAutorizzazioniAppAmbulanti cAut = new ConfigurazioneAutorizzazioniAppAmbulanti();
	Integer codDocStampa = mVerticalizzazioneComportamentiMercatiService.codTipodocStampa();
	cAut.setAttivaStampPdf(codDocStampa != null);
	c.setAutorizzazioni(cAut);
    }
}
