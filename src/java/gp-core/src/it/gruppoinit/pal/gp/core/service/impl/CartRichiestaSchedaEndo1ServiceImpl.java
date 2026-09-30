/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.CartRichiestaSchedaEndo1Service;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.RichiestaSchedaEndoTipo1;
import it.gruppoinit.sigepro.cart.service.CartRfcBaseService;
import it.gruppoinit.sigepro.cart.service.fru.CartRfc183RichiestaSchedaEndo1ServiceClient;

import java.io.StringWriter;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.openspcoop.pdd.services.SPCoopException;
import org.openspcoop.pdd.services.SPCoopMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author riccardob
 * 
 */
@Service
public class CartRichiestaSchedaEndo1ServiceImpl extends CartBaseServiceImpl implements CartRichiestaSchedaEndo1Service {

    private static final Logger log = LoggerFactory.getLogger(CartRichiestaSchedaEndo1ServiceImpl.class);
    private InventarioprocedimentiService inventarioprocedimentiService;
    private StpEndoTipo1Service stpEndoTipo1Service;
    private CartRfc183RichiestaSchedaEndo1ServiceClient cartRfc183RichiestaSchedaEndo1ServiceClient;

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setStpEndoTipo1Service(StpEndoTipo1Service stpEndoTipo1Service) {

	this.stpEndoTipo1Service = stpEndoTipo1Service;
    }
    
    @Autowired
    public void setCartRfc183RichiestaSchedaEndo1ServiceClient(CartRfc183RichiestaSchedaEndo1ServiceClient richiestaSchedaEndo1Service){
	cartRfc183RichiestaSchedaEndo1ServiceClient = richiestaSchedaEndo1Service;
	this.cartService = richiestaSchedaEndo1Service;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CartRichiestaSchedaEndo1Service#inviaRichiestaSchedaEndo(java.lang.Integer)
     */
    @Override
    public void inviaRichiestaSchedaEndo(Integer codiceInventario, CartRfcBaseService.TipoRichiesta tipoRichiesta) throws RemoteException, SPCoopException {

	// §§§BEGIN§§§
	if (codiceInventario == null) {
	    log.error("inviaRichiestaSchedaEndo: Il parametro codiceInventario non può essere vuoto");
	    throw new IllegalArgumentException("Il parametro codiceInventario non può essere vuoto");
	}
	PkId id = new PkId(codiceInventario);
	Inventarioprocedimenti endo = inventarioprocedimentiService.findById(id);
	if (endo == null) {
	    log.error("inviaRichiestaSchedaEndo: Non è stato trovato nessun endo con codice: " + id.toString());
	    throw new IllegalArgumentException("Non è stato trovato nessun endo con codice: " + id.toString());
	}
	StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.findByInventarioProcedimenti(endo);
	if (stpEndoTipo1 != null) {
	    cartRfc183RichiestaSchedaEndo1ServiceClient.inviaRichiestaSchedaEndo(stpEndoTipo1.getCodiceStp(), tipoRichiesta);
	} else {
	    log.warn("Non è stato trovato il collegamento con STP_ENDO_TIPO1 per l'endoprocedimento {}. Possibile errore o hacking?", endo.getId());
	}
	// §§§END§§§
    }

    @Override
    public int inviaRichiestaSchedeDizionario(CartRfcBaseService.TipoRichiesta tipoRichiesta) {

	int schedeInviate = 0;
	// §§§BEGIN§§§
	List<StpEndoTipo1> endoTipo1 = stpEndoTipo1Service.findAll(null, null);
	for (StpEndoTipo1 stpEndoTipo1 : endoTipo1) {
	    if (!EntityUtils.isNestedPropertyBlank(stpEndoTipo1.getInventarioprocedimenti(), "id.codice")) {
		String idScheda = "(EndoRegionale=" + stpEndoTipo1.getCodiceStp() + ") Endoprocedimento("
			+ stpEndoTipo1.getInventarioprocedimenti().getId().getCodice() + ":"
			+ stpEndoTipo1.getInventarioprocedimenti().getProcedimento() + " )";
		try {
		    cartRfc183RichiestaSchedaEndo1ServiceClient.inviaRichiestaSchedaEndo(stpEndoTipo1.getCodiceStp(), tipoRichiesta);
		} catch (SPCoopException e) {
		    FlashMessages.getWarnings().add(
			    "Non è stato possibile richiedere la scheda [" + idScheda + "] a causa di: ["
				    + ((SPCoopException) e).getCodiceEccezione() + ":" + ((SPCoopException) e).getDescrizioneEccezione() + "]");
		} catch (RemoteException e) {
		    FlashMessages.getWarnings().add("Non è stato possibile richiedere la scheda [" + idScheda + "] a causa di: " + e.getMessage());
		}
		schedeInviate++;
	    }
	}
	// §§§END§§§
	return schedeInviate;
    }
}
