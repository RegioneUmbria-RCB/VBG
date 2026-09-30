/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.StpEndoTipo2DAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoId;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.helper.CartInfoDizionarioHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocumentiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AzioniService;
import it.gruppoinit.pal.gp.core.service.CartInvioSchedaEndo2Service;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.NaturaendoService;
import it.gruppoinit.pal.gp.core.service.StpCategorieEndo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.AllegatoRichiesto;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoAllegatiRichiesti;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoQuadri;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoQuadri.Quadro;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.EndoLocale;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.File;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioSchedaEndoTipo2;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ParteRegionaleSchedaEndoTipo2;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.Tabella;
import it.gruppoinit.sigepro.cart.service.ero.CartRfc184InvioSchedaEndo2Service;
import it.gruppoinit.sigepro.cart.service.helper.CartServiceConfigurationHelper;
import it.gruppoinit.sigepro.cart.service.helper.CartServiceConfigurationParameters;

import java.io.Serializable;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openspcoop.pdd.services.SPCoopException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author riccardob
 * 
 */
@Service
public class CartInvioSchedaEndo2ServiceImpl extends CartBaseServiceEROImpl implements CartInvioSchedaEndo2Service {

    private static final Logger log = LoggerFactory.getLogger(CartInvioSchedaEndo2ServiceImpl.class);
    private AzioniService azioniService;
    private AlberoprocService alberoprocService;
    private AlberoprocDocumentiService alberoprocDocumentiService;
    private AlberoprocEndoService alberoprocEndoService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private NaturaendoService naturaendoService;
    private StpCategorieEndo1Service stpCategorieEndo1Service;
    private StpEndoTipo1Service stpEndoTipo1Service;
    private StpEndoTipo2Service stpEndoTipo2Service;
    private StpEndoTipo2DAO stpEndoTipo2DAO;
    private CartRfc184InvioSchedaEndo2Service cartInvioSchedaEndo2Service;

    @Autowired
    public void setAlberoprocDocumentiService(AlberoprocDocumentiService alberoprocDocumentiService) {

	this.alberoprocDocumentiService = alberoprocDocumentiService;
    }

    @Autowired
    public void setAlberoprocEndoService(AlberoprocEndoService alberoprocEndoService) {

	this.alberoprocEndoService = alberoprocEndoService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setAzioniService(AzioniService azioniService) {

	this.azioniService = azioniService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setNaturaendoService(NaturaendoService naturaendoService) {

	this.naturaendoService = naturaendoService;
    }

    @Autowired
    public void setStpCategorieEndo1Service(StpCategorieEndo1Service stpCategorieEndo1Service) {

	this.stpCategorieEndo1Service = stpCategorieEndo1Service;
    }

    @Autowired
    public void setStpEndoTipo1Service(StpEndoTipo1Service stpEndoTipo1Service) {

	this.stpEndoTipo1Service = stpEndoTipo1Service;
    }

    @Autowired
    public void setStpEndoTipo2Service(StpEndoTipo2Service stpEndoTipo2Service) {

	this.stpEndoTipo2Service = stpEndoTipo2Service;
    }

    @Autowired
    public void setStpEndoTipo2DAO(StpEndoTipo2DAO stpEndoTipo2DAO) {

	this.stpEndoTipo2DAO = stpEndoTipo2DAO;
    }

    @Autowired
    public void setCartInvioSchedaEndo2Service(CartRfc184InvioSchedaEndo2Service cartService) {

	this.cartService = cartService;
	this.cartInvioSchedaEndo2Service = cartService;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.impl.CartBaseServiceEROImpl#elaboraMessaggio(java.lang.String)
     */
    @Override
    public void elaboraMessaggio(String idEgov) throws RemoteException, SPCoopException {

	InvioSchedaEndoTipo2 scheda = cartInvioSchedaEndo2Service.elaboraMessaggio(idEgov);
	if (log.isDebugEnabled()) {
	    log.debug("elaboraMessaggio# recupero il codice scheda");
	}
	BigInteger codiceId = (BigInteger) EntityUtils.getNestedProperty(scheda, "parteRegionaleSchedaEndoTipo2.id");
	if (codiceId != null) {
	    if (log.isDebugEnabled()) {
		log.debug("elaboraMessaggio# BigInteger codiceId = {}", codiceId.intValue());
		if (codiceId.intValue() == 9999 || codiceId.intValue() == 9998) {
		    log.error("elaboraMessaggio: identificativo scheda={} ", codiceId.intValue());
		    throw new RuntimeException("Scheda vuota o non valida. rif parteRegionaleSchedaEndoTipo2.id=" + codiceId.intValue());
		}
	    }
	}
	log.debug("elaboraMessaggio: recupero il codice STP");
	BigInteger codiceStp = (BigInteger) EntityUtils.getNestedProperty(scheda, "parteRegionaleSchedaEndoTipo2.endoprocedimento");
	if (codiceStp == null) {
	    log.error("elaboraMessaggio: CodiceStp nullo o non valido");
	    throw new RuntimeException("CodiceStp nullo o non valido");
	}
	StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findbyStpCodice(codiceStp.intValue(), StpEndoTipo2Service.TIPO_ENDO);
	if (stpEndoTipo2 == null) {
	    log.error("elaboraMessaggio: Non è stata trovato nessun record nella tabella Stp_EndoTipo_2 con codicestp=" + codiceStp.intValue()
		    + ", tipologia=" + StpEndoTipo2Service.TIPO_ENDO);
	    throw new RuntimeException("Non è stata trovato nessun record nella tabella Stp_EndoTipo_2 con codicestp=" + codiceStp.intValue()
		    + ", tipologia=" + StpEndoTipo2Service.TIPO_ENDO);
	}
	// Aggiornamento natura endo procedimento collegato alla voce dell'albero dei procedimenti.
	// Aggiorno la natura endo associato all'endo procedimento associato alla voce dell'albero 
	// per cui abbiamo scaricato la schede di spiegazione
	// (Gianpaolo Todini BUG 700)
	// Recupero la NATURA ENDO da impostare all'endo procedimento
	// Il legame tra InvioSchedaEndoTipo2 e Nature endo si trova sulla tabella STP_MODALITA_APERTURA. E' dato dal valore
	// InvioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo1.modalitaAperturaStandard.VALUE che viene registrato sulla tabella
	// e legato alla tabella NATURAENDO.
	if (scheda.getParteRegionaleSchedaEndoTipo2() != null && scheda.getParteRegionaleSchedaEndoTipo2().getModalitaAperturaStandard() != null
		&& StringUtils.isNotBlank(scheda.getParteRegionaleSchedaEndoTipo2().getModalitaAperturaStandard().name())) {
	    String nameModalitaAperturaEndo2 = scheda.getParteRegionaleSchedaEndoTipo2().getModalitaAperturaStandard().name();
	    if (log.isDebugEnabled()) {
		log.debug("elaboraMessaggio# Recupero la natura endo associata alla modalità di apertura {}", nameModalitaAperturaEndo2);
	    }
	    Naturaendo naturaendo = naturaendoService.findByStpModalitaAperturaAndTipoScheda(nameModalitaAperturaEndo2,
		    WebConstants.SCHEDA_TIPO_ENDO2);
	    // Se c'è il legame tra la natura endo e la modalita di apertura tipo endo 1
	    //aggiorno la natura dell'endo procedimento, altrimenti ladcio il valore inalterato
	    if (EntityUtils.getNestedProperty(naturaendo, "id.codice") != null) {
		if (log.isDebugEnabled()) {
		    log.debug("elaboraMessaggio# Natura endo trovata :  {}", naturaendo.getNatura());
		    log.debug("elaboraMessaggio# Recupero l'endo procedimento collegato a StpEndoTipo2 con codice :  {}", codiceStp);
		}
		// controllo se nell'oggetto stpEndoTipo2 il valore STP_ENDO_TIPO2.CODICEINVENTARIO è diverso da NULL, nel caso significa che per la
		// voce dell'albero è configurato anche l'endo procedimento. Andremo a modifcare la natura endo dell'endo procedimento andando a 
		// prenderla dalla configurazione sulla tabella STP_MODALITA_APERTURA
		if (log.isDebugEnabled()) {
		    log.debug("elaboraMessaggio# Controllo se l'oggetto stpEndoTipo2 (codice {}) è associato un endo procedimento ", codiceStp);
		}
		if (EntityUtils.getNestedProperty(stpEndoTipo2.getInventarioprocedimenti(), "id.codice") != null) {
		    if (log.isDebugEnabled()) {
			log.debug("elaboraMessaggio# Alla voce dell'albero {}({}) è associato l'endo procedimento con codice {}", new Object[] {
				stpEndoTipo2.getAlberoproc().getScCodice(), stpEndoTipo2.getAlberoproc().getId().getCodice(),
				stpEndoTipo2.getInventarioprocedimenti().getId().getCodice() });
			Inventarioprocedimenti inventarioprocedimenti = stpEndoTipo2.getInventarioprocedimenti();
			inventarioprocedimenti.setNaturaendo(naturaendo);
			inventarioprocedimentiService.update(inventarioprocedimenti);
		    }
		} else {
		    if (log.isDebugEnabled()) {
			log.debug("elaboraMessaggio# Alla voce dell'albero {} non è stato trovato un endo procedimento associato ");
		    }
		}
	    }
	} else {
	    if (log.isDebugEnabled()) {
		log.debug("elaboraMessaggio# Non è stato possibile recuperare la scheda regionale o la modalità di apertura dal xml scaricato");
	    }
	}
	log.debug("elaboraMessaggio: recupero i byte dal messaggio della scheda di spiegazione");
	String messaggioBody = getCartService().getMessageToString(idEgov);
	byte[] oggetto = null;
	try {
	    oggetto = messaggioBody.getBytes("UTF-8");
	} catch (Exception e) {
	    oggetto = messaggioBody.getBytes();
	}
	log.debug("elaboraMessaggio: cerco la scheda se già salvata nella tabella Oggetti");
	Oggetti oggettoScheda = null;
	if (!EntityUtils.isNestedPropertyBlank(stpEndoTipo2.getOggetti(), "id.codice")) {
	    log.debug("elaboraMessaggio: La scheda è già presente aggiorno l'oggetto");
	    Integer codiceOggetto = (Integer) EntityUtils.getNestedProperty(stpEndoTipo2.getOggetti(), "id.codice");
	    oggettoScheda = oggettiService.findById(new PkId(codiceOggetto));
	    oggettoScheda.setOggetto(oggetto);
	    oggettiService.update(oggettoScheda);
	} else {
	    log.debug("elaboraMessaggio: La scheda non è presente aggiorno l'inserisco");
	    oggettoScheda = new Oggetti();
	    oggettoScheda.setNomefile("SchedaEndo2-" + scheda.getParteRegionaleSchedaEndoTipo2().getEndoprocedimento() + ".xml");
	    oggettoScheda.setOggetto(oggetto);
	    oggettiService.insert(oggettoScheda);
	}
	log.debug("elaboraMessaggio: aggiorno il record di STP_ENDO_TIPO2 {}", stpEndoTipo2.getId());
	stpEndoTipo2.setOggetti(oggettoScheda);
	stpEndoTipo2Service.update(stpEndoTipo2);
	log.debug("elaboraMessaggio: prima della gestione endo");
	// recupero gli endo da associare alla voce dell'albero
	gestioneEndo(scheda, stpEndoTipo2);
	log.debug("elaboraMessaggio: prima della gestione degli allegati");
	gestioneAllegati(scheda, stpEndoTipo2);
	log.debug("elaboraMessaggio: elaborazione terminata cancello il messaggio egov");
	this.deleteMessage(idEgov);
	stpEndoTipo2DAO.flush();
	stpEndoTipo2DAO.commit();
	// §§§END§§§
    }

    /**
     * Si occupa di recuperare gli allegati dalla scheda di Spiegazione presi da
     * {@link ParteRegionaleSchedaEndoTipo2#getElencoAllegatiRichiesti()}.<br />
     * Una volta recuperati se VERTICALIZZAZIONE:CART.ENDO2_INVENTARIOPROCEDIMENTI==S allora l'endo procedimento è anche
     * di tipo 1 e gli allegati vengono inseriti nella Tabella Allegati dell'endoprocedimento (Inventarioprocedimenti)
     * creato per questa voce dell'albero. se VERTICALIZZAZIONE:CART.ENDO2_INVENTARIOPROCEDIMENTI==N allora gli allegati
     * vengono salvati in ALBEROPROC_DOCUMENTI.
     * 
     * @param scheda
     * @param stpEndoTipo2
     */
    private void gestioneAllegati(InvioSchedaEndoTipo2 scheda, StpEndoTipo2 stpEndoTipo2) {

	// §§§BEGIN§§§
	CartServiceConfigurationParameters configParams = CartServiceConfigurationHelper.getConfigurationParameters();
	boolean isEndo2UnEndprocedimento = StringUtils.defaultIfEmpty(configParams.getInventarioProcedimenti(),
		WebConstants.VERTICALIZZAZIONE_CART_VALORE_N_INVENTARIO).equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_CART_VALORE_S_INVENTARIO) ? true
		: false;
	if (isEndo2UnEndprocedimento) {
	    Inventarioprocedimenti endo = stpEndoTipo2.getInventarioprocedimenti();
	    String codiceRegionale = stpEndoTipo2.getCodiceEndoRegionale();
	    if (endo == null) {
		log.warn("Non è stato associato nessun endo di tipo 1 alla voce dell'albero[{}], codice regionale [{}], codice STP [{}]",
			new Object[] { stpEndoTipo2.getAlberoproc().getId(), codiceRegionale, stpEndoTipo2.getCodiceStp() });
		return;
	    }
	    // Cancellazione allegati già presenti con flag_inserimento_aut=true
	    List<Allegati> allegatis = allegatiService.findByInventarioprocedimenti(stpEndoTipo2.getInventarioprocedimenti().getId().getCodice());
	    for (Allegati allegati : allegatis) {
		boolean daCancellare = allegati.getFlagInserimentoAut() == null ? false : allegati.getFlagInserimentoAut().booleanValue();
		if (daCancellare) {
		    allegatiService.delete(allegati);
		}
	    }
	    // estraggo gli allegati da parteregionaleSchedaEndo2
	    if (EntityUtils.getNestedProperty(scheda.getParteRegionaleSchedaEndoTipo2(), "elencoAllegatiRichiesti.allegatoRichiesto") != null) {
		List<AllegatoRichiesto> allegatiRichiesti = scheda.getParteRegionaleSchedaEndoTipo2().getElencoAllegatiRichiesti()
			.getAllegatoRichiesto();
		if (allegatiRichiesti.size() > 0) {
		    for (AllegatoRichiesto allegatoRichiesto : allegatiRichiesti) {
			if (allegatoRichiesto != null) {
			    inserisciAllegatoEndo1(endo, allegatoRichiesto);
			}
		    }
		}
	    }
	    if (scheda.getParteRegionaleSchedaEndoTipo2().getTabella() != null) {
		Tabella tabella = scheda.getParteRegionaleSchedaEndoTipo2().getTabella();
		String formulazione = tabella.getFormulazione();
		File contenuto = tabella.getDichiarazioni();
		if (contenuto != null) {
		    if (contenuto.getDatiFile().length > 0) {
			inserisciAllegatoDaTabellaEndo1(endo, formulazione, contenuto);
		    }
		}
	    }
	    // estraggo gli allegati da parteLocaleSchedaEndo2
	    if (scheda.getParteLocaleSchedaEndoTipo2() != null) {
		if (scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiPrima() != null) {
		    if (scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiPrima().getElencoEndoLocali() != null) {
			if (scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiPrima().getElencoEndoLocali().getEndoLocale() != null) {
			    if (scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiPrima().getElencoEndoLocali().getEndoLocale().size() > 0) {
				List<EndoLocale> endos = scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiPrima().getElencoEndoLocali()
					.getEndoLocale();
				for (EndoLocale endoLocale : endos) {
				    gestisciEndoLocale(endoLocale, endo);
				}
			    }
			}
		    }
		}
		if (scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiDopo() != null) {
		    if (scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiDopo().getElencoEndoLocali() != null) {
			if (scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiDopo().getElencoEndoLocali().getEndoLocale() != null) {
			    if (scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiDopo().getElencoEndoLocali().getEndoLocale().size() > 0) {
				List<EndoLocale> endos = scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiDopo().getElencoEndoLocali()
					.getEndoLocale();
				for (EndoLocale endoLocale : endos) {
				    gestisciEndoLocale(endoLocale, endo);
				}
			    }
			}
		    }
		}
	    }
	} else {
	    // TODO inserire in alberoproc_documenti
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(stpEndoTipo2.getAlberoproc().getId().getCodice()));
	    List<AlberoprocDocumenti> docs = alberoprocDocumentiService.findByAlberoProc(stpEndoTipo2.getAlberoproc().getId().getCodice());
	    for (AlberoprocDocumenti alberoprocDocumenti : docs) {
		boolean daCancellare = alberoprocDocumenti.getFlagInserimentoAut() == null ? false : alberoprocDocumenti.getFlagInserimentoAut()
			.booleanValue();
		if (daCancellare) {
		    alberoprocDocumentiService.delete(alberoprocDocumenti);
		}
	    }
	    if (EntityUtils.getNestedProperty(scheda.getParteRegionaleSchedaEndoTipo2(), "elencoAllegatiRichiesti.allegatoRichiesto") != null) {
		List<AllegatoRichiesto> allegatiRichiesti = scheda.getParteRegionaleSchedaEndoTipo2().getElencoAllegatiRichiesti()
			.getAllegatoRichiesto();
		if (allegatiRichiesti.size() > 0) {
		    for (AllegatoRichiesto allegatoRichiesto : allegatiRichiesti) {
			if (allegatoRichiesto != null) {
			    inserisciDocumentoAlberoproc(stpEndoTipo2.getAlberoproc(), allegatoRichiesto);
			}
		    }
		}
	    }
	    if (scheda.getParteRegionaleSchedaEndoTipo2().getTabella() != null) {
		Tabella tabella = scheda.getParteRegionaleSchedaEndoTipo2().getTabella();
		String formulazione = tabella.getFormulazione();
		File contenuto = tabella.getDichiarazioni();
		if (contenuto != null) {
		    inserisciDocumentoAlberoprocDaTabellaEndo1(stpEndoTipo2.getAlberoproc(), formulazione, contenuto);
		}
	    }
	    // estraggo gli allegati da parteLocaleSchedaEndo2
	    if (scheda.getParteLocaleSchedaEndoTipo2() != null) {
		if (scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiPrima() != null) {
		    if (scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiPrima().getElencoEndoLocali() != null) {
			if (scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiPrima().getElencoEndoLocali().getEndoLocale() != null) {
			    if (scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiPrima().getElencoEndoLocali().getEndoLocale().size() > 0) {
				List<EndoLocale> endos = scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiPrima().getElencoEndoLocali()
					.getEndoLocale();
				for (EndoLocale endoLocale : endos) {
				    gestisciEndoLocaleAlberoproc(endoLocale, alberoproc);
				}
			    }
			}
		    }
		}
		if (scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiDopo() != null) {
		    if (scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiDopo().getElencoEndoLocali() != null) {
			if (scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiDopo().getElencoEndoLocali().getEndoLocale() != null) {
			    if (scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiDopo().getElencoEndoLocali().getEndoLocale().size() > 0) {
				List<EndoLocale> endos = scheda.getParteLocaleSchedaEndoTipo2().getElencoEndoPrevistiDopo().getElencoEndoLocali()
					.getEndoLocale();
				for (EndoLocale endoLocale : endos) {
				    gestisciEndoLocaleAlberoproc(endoLocale, alberoproc);
				}
			    }
			}
		    }
		}
	    }
	}
	// §§§END§§§
    }

    private void gestisciEndoLocaleAlberoproc(EndoLocale endoLocale, Alberoproc alberoproc) {

	// §§§BEGIN§§§
	gestisciElencoQuadriAlberoproc(endoLocale.getElencoQuadriStandard5(), alberoproc);
	gestisciElencoQuadriAlberoproc(endoLocale.getElencoQuadriStandard6(), alberoproc);
	// §§§END§§§
    }

    private void gestisciElencoQuadriAlberoproc(ElencoQuadri elencoquadri, Alberoproc alberoproc) {

	// §§§BEGIN§§§
	if (elencoquadri != null) {
	    if (elencoquadri.getQuadro() != null) {
		if (elencoquadri.getQuadro().size() > 0) {
		    List<ElencoQuadri.Quadro> quadris = elencoquadri.getQuadro();
		    for (Quadro quadro : quadris) {
			File testoQuardo = quadro.getTestoQuadro();
			// TODO CHE CI FO'?
			ElencoAllegatiRichiesti allegati = quadro.getElencoAllegatiRichiestiQuadro();
			if (allegati != null) {
			    if (allegati.getAllegatoRichiesto() != null) {
				List<AllegatoRichiesto> allegatoRichiestoList = allegati.getAllegatoRichiesto();
				for (AllegatoRichiesto allegatoRichiesto : allegatoRichiestoList) {
				    if (allegatoRichiesto != null) {
					inserisciDocumentoAlberoproc(alberoproc, allegatoRichiesto);
				    }
				}
			    }
			}
		    }
		}
	    }
	}
	// §§§END§§§
    }

    private void inserisciDocumentoAlberoprocDaTabellaEndo1(Alberoproc alberoproc, String formulazione, File documento) {

	// §§§BEGIN§§§
	log.debug("inserisciDocumentoAlberoprocDaTabellaEndo1: Trovato l'allegato da tabella formulazione:[{}]", new Object[] { formulazione });
	Oggetti template = null;
	if (documento != null) {
	    if (documento.getDatiFile() != null) {
		if (documento.getDatiFile().length > 0) {
		    template = new Oggetti();
		    template.setNomefile(StringUtils.right(
			    StringUtils.defaultIfEmpty(documento.getNomeFile(),
				    "Documento_non_codificato" + StringUtils.defaultIfEmpty(documento.getContentType(), "") + ".doc"), 255));
		    template.setOggetto(documento.getDatiFile());
		    oggettiService.insert(template);
		    log.debug("inserisciDocumentoAlberoprocDaTabellaEndo1: inserito il file:[{}], cType:[{}]", documento.getNomeFile(),
			    documento.getContentType());
		}
	    }
	}
	String descrizione = formulazione;
	if (StringUtils.isBlank(descrizione)) {
	    if (template == null) {
		log.warn("inserisciDocumentoAlberoprocDaTabellaEndo1: Allegato vuoto non lo inserisco");
		return;
	    }
	    descrizione = StringUtils.defaultIfEmpty(template.getNomefile(), "");
	}
	AlberoprocDocumenti entity = new AlberoprocDocumenti();
	entity.setDescrizione(StringUtils.left(descrizione, 3950));
	entity.setAlberoproc(alberoproc);
	entity.setOggetto(template);
	recuperaSettaggiDocumentoAlberoproc(entity);
	alberoprocDocumentiService.insert(entity);
	log.debug("inserisciDocumentoAlberoprocDaTabellaEndo1: effettuato l'inserimento nella tabella Alberoproc_Document");
	// §§§END§§§
    }

    private void inserisciDocumentoAlberoproc(Alberoproc alberoproc, AllegatoRichiesto allegatoRichiesto) {

	// §§§BEGIN§§§
	String codiceAllegato = allegatoRichiesto.getCodiceAllegato();
	String adempimentoAllegato = allegatoRichiesto.getAdempimentoAllegato();
	String spiegazioneAllegato = allegatoRichiesto.getSpiegazioniAllegato();
	String tipologiaAllegato = allegatoRichiesto.getTipologiaAllegato();
	log.debug(
		"inserisciDocumentoAlberoproc: Trovato l'allegato codiceAllegato:[{}], tipologiaAllegato:[{}], adempimentoAllegato:[{}], spiegazioneAllegato:[{}]",
		new Object[] { codiceAllegato, tipologiaAllegato, adempimentoAllegato, spiegazioneAllegato });
	File documento = allegatoRichiesto.getTemplateAllegato();
	Oggetti template = null;
	if (documento != null) {
	    if (documento.getDatiFile() != null) {
		if (documento.getDatiFile().length > 0) {
		    template = new Oggetti();
		    template.setNomefile(StringUtils.right(
			    StringUtils.defaultIfEmpty(documento.getNomeFile(),
				    "Documento_non_codificato" + StringUtils.defaultIfEmpty(documento.getContentType(), "") + ".doc"), 255));
		    template.setOggetto(allegatoRichiesto.getTemplateAllegato().getDatiFile());
		    oggettiService.insert(template);
		    log.debug("inserisciDocumentoAlberoproc: inserito il file:[{}], cType:[{}]", allegatoRichiesto.getTemplateAllegato()
			    .getNomeFile(), allegatoRichiesto.getTemplateAllegato().getContentType());
		}
	    }
	}
	String descrizione = tipologiaAllegato;
	if (StringUtils.isBlank(descrizione)) {
	    descrizione = spiegazioneAllegato;
	}
	if (StringUtils.isBlank(descrizione)) {
	    if (template == null) {
		log.warn("inserisciDocumentoAlberoproc: Allegato vuoto non lo inserisco");
		return;
	    }
	    descrizione = StringUtils.defaultIfEmpty(template.getNomefile(), "");
	}
	AlberoprocDocumenti entity = new AlberoprocDocumenti();
	entity.setDescrizione(StringUtils.left(descrizione, 3950));
	entity.setAlberoproc(alberoproc);
	entity.setOggetto(template);
	recuperaSettaggiDocumentoAlberoproc(entity);
	alberoprocDocumentiService.insert(entity);
	log.debug("inserisciDocumentoAlberoproc: AlberoprocDocumenti:[{}] inserito correttamente", allegatoRichiesto.getTemplateAllegato()
		.getNomeFile());
	// §§§END§§§
    }

    private void gestisciEndoLocale(EndoLocale endoLocale, Inventarioprocedimenti endo) {

	// §§§BEGIN§§§
	gestisciElencoQuadri(endoLocale.getElencoQuadriStandard5(), endo);
	gestisciElencoQuadri(endoLocale.getElencoQuadriStandard6(), endo);
	// §§§END§§§
    }

    private void inserisciAllegatoDaTabellaEndo1(Inventarioprocedimenti endo, String formulazione, File documento) {

	// §§§BEGIN§§§
	log.debug("inserisciAllegatoDaTabellaEndo1: Trovato l'allegato da tabella formulazione:[{}]", new Object[] { formulazione });
	Oggetti template = null;
	if (StringUtils.isBlank(formulazione)) {
	    if (documento == null) {
		return;
	    }
	    if (documento.getDatiFile() == null) {
		return;
	    }
	}
	if (documento != null) {
	    if (documento.getDatiFile() != null) {
		if (documento.getDatiFile().length > 0) {
		    template = new Oggetti();
		    template.setNomefile(StringUtils.right(
			    StringUtils.defaultIfEmpty(documento.getNomeFile(),
				    "Documento_non_codificato" + StringUtils.defaultIfEmpty(documento.getContentType(), "") + ".doc"), 255));
		    template.setOggetto(documento.getDatiFile());
		    oggettiService.insert(template);
		    log.debug("inserisciAllegatoDaTabellaEndo1: inserito il file:[{}], cType:[{}]", documento.getNomeFile(),
			    documento.getContentType());
		}
	    }
	}
	if (StringUtils.isBlank(formulazione)) {
	    if (template == null) {
		log.warn("inserisciAllegatoDaTabellaEndo1: Allegato vuoto non lo inserisco");
		return;
	    }
	    formulazione = template.getNomefile();
	}
	Allegati allegato = new Allegati();
	allegato.setAllegato(StringUtils.left(formulazione, 500));
	allegato.setInventarioprocedimento(endo);
	allegato.setOggetti(template);
	recuperaSettaggiAllegato(allegato);
	allegatiService.insert(allegato);
	log.debug("inserisciAllegatoDaTabellaEndo1: effettuato l'inserimento nella tabella Allegati");
	// §§§END§§§
    }

    /**
     * Inserisce su alberoprocendo gli endo procedimenti legati alla scheda di tipo 2.
     * 
     * @param scheda
     * @param stpEndoTipo2
     */
    private void gestioneEndo(InvioSchedaEndoTipo2 scheda, StpEndoTipo2 stpEndoTipo2) {

	CartServiceConfigurationParameters configParams = CartServiceConfigurationHelper.getConfigurationParameters();
	Alberoproc alberoproc = stpEndoTipo2.getAlberoproc();
	List<Serializable> endoTipo1List = new ArrayList<Serializable>();
	if (scheda.getParteRegionaleSchedaEndoTipo2() != null) {
	    if (scheda.getParteRegionaleSchedaEndoTipo2().getElencoEndoRegionaliPrevistiDopo() != null) {
		List<Serializable> endoTipo1Dopo = scheda.getParteRegionaleSchedaEndoTipo2().getElencoEndoRegionaliPrevistiDopo()
			.getEndoTipo1AndEndoObbligatorio();
		endoTipo1List.addAll(endoTipo1Dopo);
	    }
	    if (scheda.getParteRegionaleSchedaEndoTipo2().getElencoEndoRegionaliPrevistiPrima() != null) {
		List<Serializable> endoTipo1Prima = scheda.getParteRegionaleSchedaEndoTipo2().getElencoEndoRegionaliPrevistiPrima()
			.getEndoTipo1AndEndoObbligatorio();
		endoTipo1List.addAll(endoTipo1Prima);
	    }
	    if (log.isDebugEnabled()) {
		log.debug("Aggiorno la lista degli endo di AlberoProc sc_id: {}", alberoproc.getId().getCodice().intValue());
	    }
	    Map<Integer, Boolean> listaEndoMap = new HashMap<Integer, Boolean>();
	    for (int i = 0; i < endoTipo1List.size(); i += 2) {
		if (endoTipo1List.get(i) != null && endoTipo1List.get(i + 1) != null) {
		    Integer codiceEndo = ((BigInteger) endoTipo1List.get(i)).intValue();
		    Boolean obbligatorio = ((Boolean) endoTipo1List.get(i + 1));
		    listaEndoMap.put(codiceEndo, obbligatorio);
		}
	    }
	    Iterator<Map.Entry<Integer, Boolean>> it = listaEndoMap.entrySet().iterator();
	    while (it.hasNext()) {
		Map.Entry<Integer, Boolean> pairs = (Map.Entry<Integer, Boolean>) it.next();
		Integer codiceEndo = pairs.getKey();
		StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.findbyStpCodice(codiceEndo);
		if (stpEndoTipo1 != null) {
		    boolean obbligatorio = pairs.getValue() == null ? false : pairs.getValue().booleanValue();
		    AlberoprocEndoId id = new AlberoprocEndoId();
		    id.setFkscid(alberoproc.getId().getCodice());
		    id.setCodiceinventario(stpEndoTipo1.getInventarioprocedimenti().getId().getCodice());
		    id.setIdcomune(ORMHelper.getIdcomune());
		    AlberoprocEndo alberoprocEndo = alberoprocEndoService.findById(id);
		    String codiceazione = configParams.getAzioni();// verticalizzazioniparametriAZIONI.getValore();
		    Azioni azioni = azioniService.findById(new Integer(codiceazione));
		    if (alberoprocEndo == null) {
			alberoprocEndo = new AlberoprocEndo();
			alberoprocEndo.setInventarioprocedimento(stpEndoTipo1.getInventarioprocedimenti());
			alberoprocEndo.setAlberoproc(alberoproc);
			alberoprocEndo.setId(id);
			alberoprocEndo.setFlagRichiesto(obbligatorio);
			alberoprocEndo.setFlagPubblica(Boolean.TRUE);
			alberoprocEndo.setFlagPrincipale(Boolean.FALSE);
			//IN INSERIMENTO L'AZIONE DEVE ESSERE NULL;
			//alberoprocEndo.setAzione(azioni);
			alberoprocEndo.setAzione(null);
			if (log.isDebugEnabled()) {
			    log.debug("Assegno l'endo {}  a AlberoProc sc_id: {}", stpEndoTipo1.getInventarioprocedimenti().getId().getCodice()
				    .intValue(), alberoproc.getId().getCodice().intValue());
			}
			alberoprocEndoService.insert(alberoprocEndo);
		    } else {
			if (log.isDebugEnabled()) {
			    log.debug("Aggiorno l'endo {}  a AlberoProc sc_id: {}", stpEndoTipo1.getInventarioprocedimenti().getId().getCodice()
				    .intValue(), alberoproc.getId().getCodice().intValue());
			}
			alberoprocEndo.setFlagRichiesto(obbligatorio);
			alberoprocEndo.setFlagPrincipale(Boolean.FALSE);
			alberoprocEndo.setAzione(azioni);
			alberoprocEndoService.update(alberoprocEndo);
		    }
		} else {
		    String msgErr = "Errore nell'elaborazione della scheda di tipo 2: non è stato trovato nel dizionario locale l'endo regionale di tipo 1 con codice ["
			    + String.valueOf(codiceEndo)
			    + "]. E' consigliabile aggiornare il dizionario tramite INVIODIZIONARIO. Rif[ALBEROPROC["
			    + alberoproc.getId() + "," + alberoproc.getVwAlberoproc().getScDescrizione() + "]]";
		    log.error(msgErr);
		    // throw new RuntimeException(msgErr);
		}
	    }
	}
    }

    /**
     * Sono utilizzati nel service che gestisce l'elaborazione del dizionario
     */
    @Override
    public void elaboraMessaggio(CartInfoDizionarioHelper cartInfoDizionarioHelper) throws RemoteException, SPCoopException {

	// TODO Medoti che non devo essere implementati
    }

    @Override
    public CartInfoDizionarioHelper preElaboraMessaggio(String idEgov) throws RemoteException, SPCoopException {

	// TODO Medoti che non devo essere implementati
	return null;
    }
}
