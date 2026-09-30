/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.helper.CartInfoDizionarioHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.CartDisponibilitaSchedaEndo1Service;
import it.gruppoinit.pal.gp.core.service.CartRichiestaSchedaEndo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.DisponibilitaSchedaEndoTipo1;
import it.gruppoinit.sigepro.cart.service.CartRfcBaseService;
import it.gruppoinit.sigepro.cart.service.ero.CartRfc184DisponibilitaSchedaEndo1Service;

import java.math.BigInteger;
import java.rmi.RemoteException;

import org.openspcoop.pdd.services.SPCoopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author riccardob
 * 
 */
@Service
public class CartDisponibilitaSchedaEndo1ServiceImpl extends CartBaseServiceEROImpl implements CartDisponibilitaSchedaEndo1Service {

    private CartRichiestaSchedaEndo1Service cartRichiestaSchedaEndo1Service;
    private CartRfc184DisponibilitaSchedaEndo1Service cartDisponibilitaSchedaEndoService;
    private StpEndoTipo1Service stpEndoTipo1Service;

    @Autowired
    public void setCartRichiestaSchedaEndo1Service(CartRichiestaSchedaEndo1Service cartRichiestaSchedaEndo1Service) {

	this.cartRichiestaSchedaEndo1Service = cartRichiestaSchedaEndo1Service;
    }

    @Autowired
    public void setStpEndoTipo1Service(StpEndoTipo1Service stpEndoTipo1Service) {

	this.stpEndoTipo1Service = stpEndoTipo1Service;
    }

    @Autowired
    public void setCartDisponibilitaSchedaEndoService(CartRfc184DisponibilitaSchedaEndo1Service cartService) {

	this.cartService = cartService;
	this.cartDisponibilitaSchedaEndoService = cartService;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.impl.CartBaseServiceEROImpl#elaboraMessaggio(java.lang.String)
     */
    @Override
    public void elaboraMessaggio(String idEgov) throws RemoteException, SPCoopException {

	DisponibilitaSchedaEndoTipo1 dispo = cartDisponibilitaSchedaEndoService.elaboraMessaggio(idEgov);
	BigInteger codiceStp = dispo.getEndoprocedimento().getValue();
	if (codiceStp != null) {
	    StpEndoTipo1 endotipo1 = stpEndoTipo1Service.findbyStpCodice(codiceStp.intValue());
	    if (endotipo1 != null) {
		if (EntityUtils.getNestedProperty(endotipo1.getInventarioprocedimenti(), "id.codice") != null) {
		    boolean isEffettaValidazione = cartRichiestaSchedaEndo1Service.isEffettuavalidazioneSchema();
		    cartRichiestaSchedaEndo1Service.setEffettuaValidazioneSchema(false);
		    cartRichiestaSchedaEndo1Service.inviaRichiestaSchedaEndo(endotipo1.getInventarioprocedimenti().getId().getCodice(),
			    CartRfcBaseService.TipoRichiesta.Invio);
		    cartRichiestaSchedaEndo1Service.setEffettuaValidazioneSchema(isEffettaValidazione);
		}
	    }
	}
	this.deleteMessage(idEgov);
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
