/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.StpEndoTipo1DAO;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.helper.CartInfoDizionarioHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.CartInvioSchedaEndo1Service;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.NaturaendoService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.AllegatoRichiesto;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioSchedaEndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ParteRegionaleSchedaEndoTipo1;
import it.gruppoinit.sigepro.cart.service.ero.CartRfc184InvioSchedaEndo1Service;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.List;

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
public class CartInvioSchedaEndo1ServiceImpl extends CartBaseServiceEROImpl implements CartInvioSchedaEndo1Service {

    private static final Logger log = LoggerFactory.getLogger(CartInvioSchedaEndo1ServiceImpl.class);
    private InventarioprocedimentiService inventarioprocedimentiService;
    private NaturaendoService naturaendoService;
    private StpEndoTipo1Service stpEndoTipo1Service;
    private StpEndoTipo1DAO stpEndoTipo1DAO;
    private CartRfc184InvioSchedaEndo1Service cartInvioSchedaEndo1Service;

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setNaturaendoService(NaturaendoService naturaendoService) {

	this.naturaendoService = naturaendoService;
    }

    @Autowired
    public void setStpEndoTipo1DAO(StpEndoTipo1DAO stpEndoTipo1DAO) {

	this.stpEndoTipo1DAO = stpEndoTipo1DAO;
    }

    @Autowired
    public void setStpEndoTipo1Service(StpEndoTipo1Service stpEndoTipo1Service) {

	this.stpEndoTipo1Service = stpEndoTipo1Service;
    }

    @Autowired
    public void setCartInvioSchedaEndo1Service(CartRfc184InvioSchedaEndo1Service cartService) {

	this.cartService = cartService;
	this.cartInvioSchedaEndo1Service = cartService;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.impl.CartBaseServiceEROImpl#elaboraMessaggio(java.lang.String)
     */
    @Override
    public void elaboraMessaggio(String idEgov) throws RemoteException, SPCoopException {

	InvioSchedaEndoTipo1 scheda = cartInvioSchedaEndo1Service.elaboraMessaggio(idEgov);
	log.debug("elaboraMessaggio: recupero il codice STP");
	BigInteger codiceStp = (BigInteger) EntityUtils.getNestedProperty(scheda, "parteRegionaleSchedaEndoTipo1.endoprocedimento");
	if (codiceStp == null) {
	    log.error("elaboraMessaggio: CodiceStp nullo o non valido");
	    throw new RuntimeException("CodiceStp nullo o non valido");
	}
	StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.findbyStpCodice(codiceStp.intValue());
	if (stpEndoTipo1 == null) {
	    log.error("elaboraMessaggio: Non è stata trovato nessun record nella tabella Stp_EndoTipo_1 con codicestp=" + codiceStp.intValue());
	    throw new RuntimeException("Non è stata trovato nessun record nella tabella Stp_EndoTipo_1 con codicestp=" + codiceStp.intValue());
	}
	// Aggiorno la natura endo associato all'endo procedimento per cui abbiamo scaricato la schede di spiegazione
	// (Gianpaolo Todini BUG 700)
	// Recupero la NATURA ENDO da impostare all'endo procedimento
	// Il legame tra InvioSchedaEndoTipo1 e Nature endo si trova sulla tabella STP_MODALITA_APERTURA. E' dato dal valore
	// InvioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.modalitaAperturaStandard.VALUE che viene registrato sulla tabella
	// e legato alla tabella NATURAENDO.
	if (scheda.getParteRegionaleSchedaEndoTipo1() != null && scheda.getParteRegionaleSchedaEndoTipo1().getModalitaAperturaStandard() != null
		&& StringUtils.isNotBlank(scheda.getParteRegionaleSchedaEndoTipo1().getModalitaAperturaStandard().name())) {
	    String nameModalitaAperturaEndo1 = scheda.getParteRegionaleSchedaEndoTipo1().getModalitaAperturaStandard().name();
	    if (log.isDebugEnabled()) {
		log.debug("elaboraMessaggio# Recupero la natura endo associata alla modalità di apertura {}", nameModalitaAperturaEndo1);
	    }
	    Naturaendo naturaendo = naturaendoService.findByStpModalitaAperturaAndTipoScheda(nameModalitaAperturaEndo1,WebConstants.SCHEDA_TIPO_ENDO1);
	    // Se c'è il legame tra la natura endo e la modalita di apertura tipo endo 1
	    //aggiorno la natura dell'endo procedimento, altrimenti ladcio il valore inalterato
	    if (EntityUtils.getNestedProperty(naturaendo, "id.codice") != null) {
		if (log.isDebugEnabled()) {
		    log.debug("elaboraMessaggio# Natura endo trovata :  {}", naturaendo.getNatura());
		    log.debug("elaboraMessaggio# Recupero l'endo procedimento collegato a StpEndoTipo1 con codice :  {}", codiceStp);
		}
		Inventarioprocedimenti inventarioprocedimenti = stpEndoTipo1.getInventarioprocedimenti();
		if (EntityUtils.getNestedProperty(inventarioprocedimenti, "id.codice") != null) {
		    inventarioprocedimenti.setNaturaendo(naturaendo);
		    // Aggiorno l'inventario procedimento con la natura cambiata
		    inventarioprocedimentiService.update(inventarioprocedimenti);
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
	if (!EntityUtils.isNestedPropertyBlank(stpEndoTipo1.getOggetti(), "id.codice")) {
	    log.debug("elaboraMessaggio: La scheda è già presente aggiorno l'oggetto");
	    Integer codiceOggetto = (Integer) EntityUtils.getNestedProperty(stpEndoTipo1.getOggetti(), "id.codice");
	    oggettoScheda = oggettiService.findById(new PkId(codiceOggetto));
	    oggettoScheda.setOggetto(oggetto);
	    oggettiService.update(oggettoScheda);
	} else {
	    log.debug("elaboraMessaggio: La scheda non è presente aggiorno l'inserisco");
	    oggettoScheda = new Oggetti();
	    oggettoScheda.setNomefile("SchedaEndo1-" + scheda.getParteRegionaleSchedaEndoTipo1().getEndoprocedimento() + ".xml");
	    oggettoScheda.setOggetto(oggetto);
	    oggettiService.insert(oggettoScheda);
	}
	stpEndoTipo1.setOggetti(oggettoScheda);
	log.debug("elaboraMessaggio: aggiorno il record di STP_ENDO_TIPO1 {}", stpEndoTipo1.getId());
	stpEndoTipo1Service.update(stpEndoTipo1);
	log.debug("elaboraMessaggio: prima della gestione degli allegati");
	gestioneAllegati(scheda, stpEndoTipo1);
	log.debug("elaboraMessaggio: elaborazione terminata cancello il messaggio egov");
	this.deleteMessage(idEgov);
	stpEndoTipo1DAO.flush();
	stpEndoTipo1DAO.commit();
	// §§§END§§§
    }

    /**
     * Si occupa di recuperare gli allegati dalla scheda di Spiegazione presi da
     * {@link ParteRegionaleSchedaEndoTipo1#getElencoAllegatiRichiesti()} e
     * {@link ParteRegionaleSchedaEndoTipo1#getElencoQuadriStandard5()} e
     * {@link ParteRegionaleSchedaEndoTipo1#getElencoQuadriStandard6()}.<br />
     * Una volta recuperati gli allegati vengono inseriti nella Tabella Allegati dell'endoprocedimento
     * (Inventarioprocedimenti).
     * 
     * @param scheda
     * @param stpEndoTipo2
     */
    private void gestioneAllegati(InvioSchedaEndoTipo1 scheda, StpEndoTipo1 stpEndoTipo1) {

	// §§§BEGIN§§§
	if (scheda == null) {
	    return;
	}
	// Cancellazione allegati già presenti o regola
	Inventarioprocedimenti endo = inventarioprocedimentiService.findById(new PkId(stpEndoTipo1.getInventarioprocedimenti().getId().getCodice()));
	List<Allegati> allegatis = allegatiService.findByInventarioprocedimenti(endo.getId().getCodice());
	for (Allegati allegati : allegatis) {
	    boolean daCancellare = allegati.getFlagInserimentoAut() == null ? false : allegati.getFlagInserimentoAut().booleanValue();
	    if (daCancellare) {
		allegatiService.delete(allegati);
	    }
	}
	if (EntityUtils.getNestedProperty(scheda.getParteRegionaleSchedaEndoTipo1(), "elencoAllegatiRichiesti.allegatoRichiesto") != null) {
	    List<AllegatoRichiesto> allegatiRichiesti = scheda.getParteRegionaleSchedaEndoTipo1().getElencoAllegatiRichiesti().getAllegatoRichiesto();
	    if (allegatiRichiesti.size() > 0) {
		for (AllegatoRichiesto allegatoRichiesto : allegatiRichiesti) {
		    if (allegatoRichiesto != null) {
			inserisciAllegatoEndo1(endo, allegatoRichiesto);
		    }
		}
	    }
	}
	if (scheda.getParteRegionaleSchedaEndoTipo1() != null) {
	    if (scheda.getParteRegionaleSchedaEndoTipo1().getElencoQuadriStandard5() != null) {
		gestisciElencoQuadri(scheda.getParteRegionaleSchedaEndoTipo1().getElencoQuadriStandard5(), endo);
	    }
	    if (scheda.getParteRegionaleSchedaEndoTipo1().getElencoQuadriStandard6() != null) {
		gestisciElencoQuadri(scheda.getParteRegionaleSchedaEndoTipo1().getElencoQuadriStandard6(), endo);
	    }
	}
	// §§§END§§§
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