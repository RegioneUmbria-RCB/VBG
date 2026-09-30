/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.CartInvioLocalizzazioneEndo2Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioLocalizzazioneSchedaEndoTipo2;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioSchedaEndoTipo2;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ParteLocaleSchedaEndoTipo2;
import it.gruppoinit.sigepro.cart.service.fru.CartRfc183InvioLocalizzazioneEndo2ServiceClient;

import java.rmi.RemoteException;

import org.openspcoop.pdd.services.SPCoopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author riccardob
 * 
 */
@Service
public class CartInvioLocalizzazioneEndo2ServiceImpl extends CartBaseServiceImpl implements CartInvioLocalizzazioneEndo2Service {

    //private static final Logger log = LoggerFactory.getLogger(CartInvioLocalizzazioneEndo2ServiceImpl.class);
    private CartRfc183InvioLocalizzazioneEndo2ServiceClient cartInvioLocalizzazioneEndo2ServiceClient;
    private StpEndoTipo2Service stpEndoTipo2Service;
    private AlberoprocService alberoprocService;
    private OggettiService oggettiService;

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CartInvioLocalizzazioneEndo2Service#inviaLocalizzazioneSchedaEndo(java.lang.Integer)
     */
    @Override
    public void inviaLocalizzazioneSchedaEndo(Integer codiceAlberoproc) throws RemoteException, SPCoopException {

	cartInvioLocalizzazioneEndo2ServiceClient.inviaLocalizzazioneSchedaEndo(preparaMessaggioRichiesta(codiceAlberoproc));
    }

    private InvioLocalizzazioneSchedaEndoTipo2 preparaMessaggioRichiesta(Integer codiceAlberoproc) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceAlberoproc));
	String errMsg = "Errore nel recupero della scheda di localizzazione: ";
	if (alberoproc != null) {
	    StpEndoTipo2 stp2 = stpEndoTipo2Service.findbyAlberoproc(codiceAlberoproc);
	    if (stp2.getOggetti() != null) {
		if (stp2.getOggetti().getId() != null) {
		    if (stp2.getOggetti().getId().getCodice() != null) {
			Oggetti schedaEndo = oggettiService.findById(new PkId(stp2.getOggetti().getId().getCodice()));
			if (schedaEndo != null) {
			    byte[] oggetto = schedaEndo.getOggetto();
			    if (oggetto != null && oggetto.length > 0) {
				InvioSchedaEndoTipo2 is2 = (InvioSchedaEndoTipo2) Utilities.unMarshallString(new String(oggetto),
					InvioSchedaEndoTipo2.class);
				if (is2 != null) {
				    ParteLocaleSchedaEndoTipo2 pls2 = is2.getParteLocaleSchedaEndoTipo2();
				    if (pls2 != null) {
					InvioLocalizzazioneSchedaEndoTipo2 result = new InvioLocalizzazioneSchedaEndoTipo2();
					result.setParteLocaleSchedaEndoTipo2(pls2);
					return result;
				    } else {
					errMsg += "il contenuto della parte locale della scheda di spiegazione al record STP_ENDO_TIPO2 per l'intervento "
						+ codiceAlberoproc
						+ " è vuoto. RIF["
						+ stp2.getOggetti().getId().getCodice()
						+ "-"
						+ ORMHelper.getIdcomune() + "]";
				    }
				}
			    } else {
				errMsg += "il contenuto del file associato al record STP_ENDO_TIPO2 per l'intervento " + codiceAlberoproc
					+ " è vuoto.";
			    }
			}
		    } else {
			errMsg += "il file associato al record STP_ENDO_TIPO2 per l'intervento " + codiceAlberoproc
				+ " non è presente. Non è stata trovata la scheda regionale";
		    }
		}
	    } else {
		errMsg += "non è stato trovato il file associato al record STP_ENDO_TIPO2 per l'intervento " + codiceAlberoproc;
	    }
	} else {
	    errMsg += "non è stato trovato l'intervento " + codiceAlberoproc;
	}
	throw new RuntimeException(errMsg);
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setStpEndoTipo2Service(StpEndoTipo2Service stpEndoTipo2Service) {

	this.stpEndoTipo2Service = stpEndoTipo2Service;
    }

    @Autowired
    public void setCartInvioLocalizzazioneEndo2ServiceClient(CartRfc183InvioLocalizzazioneEndo2ServiceClient cartInvioLocalizzazioneEndo2ServiceClient) {

	this.cartInvioLocalizzazioneEndo2ServiceClient = cartInvioLocalizzazioneEndo2ServiceClient;
	this.cartService = cartInvioLocalizzazioneEndo2ServiceClient;
    }
}
