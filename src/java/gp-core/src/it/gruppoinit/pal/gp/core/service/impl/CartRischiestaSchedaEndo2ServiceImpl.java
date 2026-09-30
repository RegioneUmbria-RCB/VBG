/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.CartRichiestaSchedaEndo2Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.sigepro.cart.service.CartRfcBaseService;
import it.gruppoinit.sigepro.cart.service.fru.CartRfc183RichiestaSchedaEndo2ServiceClient;

import java.rmi.RemoteException;
import java.util.List;

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
public class CartRischiestaSchedaEndo2ServiceImpl extends CartBaseServiceImpl implements CartRichiestaSchedaEndo2Service {

    private static final Logger log = LoggerFactory.getLogger(CartRischiestaSchedaEndo2ServiceImpl.class);
    private AlberoprocService alberoprocService;
    private StpEndoTipo2Service stpEndoTipo2Service;
    private CartRfc183RichiestaSchedaEndo2ServiceClient cartRfc183RichiestaSchedaEndo2ServiceClient;

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setStpEndoTipo2Service(StpEndoTipo2Service stpEndoTipo2Service) {

	this.stpEndoTipo2Service = stpEndoTipo2Service;
    }

    @Autowired
    public void setCartRfc183RichiestaSchedaEndo2ServiceClient(CartRfc183RichiestaSchedaEndo2ServiceClient richiestaSchedaEndo2Service) {

	this.cartRfc183RichiestaSchedaEndo2ServiceClient = richiestaSchedaEndo2Service;
	this.cartService = richiestaSchedaEndo2Service;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CartRichiestaSchedaEndo2Service#inviaRichiestaSchedaEndo(java.lang.Integer, it.gruppoinit.pal.gp.core.service.CartBaseService.TipoRichiesta)
     */
    @Override
    public void inviaRichiestaSchedaEndo(Integer codiceAlberoproc, CartRfcBaseService.TipoRichiesta tipoRichiesta) throws RemoteException,
	    SPCoopException {

	// §§§BEGIN§§§
	if (codiceAlberoproc == null) {
	    log.error("inviaRichiestaSchedaEndo: Il parametro codiceAlberoproc non può essere vuoto");
	    throw new IllegalArgumentException("Il parametro codiceAlberoproc non può essere vuoto");
	}
	PkId id = new PkId(codiceAlberoproc);
	Alberoproc alberoproc = alberoprocService.findById(id);
	if (alberoproc == null) {
	    log.error("inviaRichiestaSchedaEndo: Non è stato trovato nessun intervento con codice: " + id.toString());
	    throw new IllegalArgumentException("Non è stato trovato nessun intervento con codice: " + id.toString());
	}
	log.debug("inviaRichiestaSchedaEndo: recupero l'oggetto StpEndoTipo2 dall'alberoproc {}", alberoproc.getId());
	StpEndoTipo2 endotipo2 = stpEndoTipo2Service.findbyAlberoproc(codiceAlberoproc);
	if (endotipo2 != null) {
	    cartRfc183RichiestaSchedaEndo2ServiceClient.inviaRichiestaSchedaEndo(endotipo2.getCodiceStp(), tipoRichiesta);
	} else {
	    log.warn("Non è stato trovato il collegamento con STP_ENDO_TIPO2 per l'albero procedimenti {}. Possibile errore o hacking?",
		    alberoproc.getId());
	}
	// §§§END§§§
    }

    @Override
    public int inviaRichiestaSchedeDizionario(CartRfcBaseService.TipoRichiesta tipoRichiesta) {

	int schedeInviate = 0;
	// §§§BEGIN§§§
	List<StpEndoTipo2> endoTipo2 = stpEndoTipo2Service.findBySoftwareAndTipo(ORMHelper.getSoftware(), StpEndoTipo2Service.TIPO_ENDO);
	for (StpEndoTipo2 stpEndoTipo2 : endoTipo2) {
	    if (!EntityUtils.isNestedPropertyBlank(stpEndoTipo2.getAlberoproc(), "id.codice")) {
		if (stpEndoTipo2.getCodiceStp() != null) { // BOCCI 2012-12-20 NEL CASO CHE NON SIA STATO INDICATO IL CODICE STP (ES DALLA FUNZIONALITA' CHE CREA LE VOCI NON PRESENTI PER LA TIPOLOGIA ENDO
		    String idScheda = "(EndoRegionale=" + stpEndoTipo2.getCodiceStp() + ") Alberoproc("
			    + stpEndoTipo2.getAlberoproc().getId().getCodice() + ":" + stpEndoTipo2.getAlberoproc().getScDescrizione() + " )";
		    try {
			cartRfc183RichiestaSchedaEndo2ServiceClient.inviaRichiestaSchedaEndo(stpEndoTipo2.getCodiceStp(), tipoRichiesta);
		    } catch (SPCoopException e) {
			FlashMessages.getWarnings().add(
				"Non è stato possibile richiedere la scheda [" + idScheda + "] a causa di: ["
					+ ((SPCoopException) e).getCodiceEccezione() + ":" + ((SPCoopException) e).getDescrizioneEccezione() + "]");
		    } catch (RemoteException e) {
			FlashMessages.getWarnings()
				.add("Non è stato possibile richiedere la scheda [" + idScheda + "] a causa di: " + e.getMessage());
		    }
		    schedeInviate++;
		}
	    }
	}
	// §§§END§§§
	return schedeInviate;
    }
}
