/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.helper.CartInfoDizionarioHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.CartDisponibilitaSchedaEndo2Service;
import it.gruppoinit.pal.gp.core.service.CartRichiestaSchedaEndo2Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.DisponibilitaSchedaEndoTipo2;
import it.gruppoinit.sigepro.cart.service.CartRfcBaseService;
import it.gruppoinit.sigepro.cart.service.ero.CartRfc184DisponibilitaSchedaEndo2Service;

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
public class CartDisponibilitaSchedaEndo2ServiceImpl extends CartBaseServiceEROImpl implements CartDisponibilitaSchedaEndo2Service {

    private CartRichiestaSchedaEndo2Service cartRichiestaSchedaEndo2Service;
    private CartRfc184DisponibilitaSchedaEndo2Service cartDisponibilitaSchedaEndo2Service;
    private StpEndoTipo2Service stpEndoTipo2Service;

    @Autowired
    public void setCartRichiestaSchedaEndo2Service(CartRichiestaSchedaEndo2Service cartRichiestaSchedaEndo2Service) {

	this.cartRichiestaSchedaEndo2Service = cartRichiestaSchedaEndo2Service;
    }

    @Autowired
    public void setStpEndoTipo2Service(StpEndoTipo2Service stpEndoTipo2Service) {

	this.stpEndoTipo2Service = stpEndoTipo2Service;
    }

    @Autowired
    public void setCartDisponibilitaSchedaEndo2Service(CartRfc184DisponibilitaSchedaEndo2Service cartService) {

	this.cartService = cartService;
	this.cartDisponibilitaSchedaEndo2Service = cartService;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.impl.CartBaseServiceEROImpl#elaboraMessaggio(java.lang.String)
     */
    @Override
    public void elaboraMessaggio(String idEgov) throws RemoteException, SPCoopException {

	DisponibilitaSchedaEndoTipo2 dispo = cartDisponibilitaSchedaEndo2Service.elaboraMessaggio(idEgov);
	BigInteger codiceStp = dispo.getEndoprocedimento().getValue();
	if (codiceStp != null) {
	    StpEndoTipo2 endotipo2 = stpEndoTipo2Service.findbyStpCodice(codiceStp.intValue(), StpEndoTipo2Service.TIPO_ENDO);
	    if (endotipo2 != null) {
		if (EntityUtils.getNestedProperty(endotipo2.getAlberoproc(), "id.codice") != null) {
		    boolean isEffettaValidazione = cartRichiestaSchedaEndo2Service.isEffettuavalidazioneSchema();
		    cartRichiestaSchedaEndo2Service.setEffettuaValidazioneSchema(false);
		    cartRichiestaSchedaEndo2Service.inviaRichiestaSchedaEndo(endotipo2.getAlberoproc().getId().getCodice(),
			    CartRfcBaseService.TipoRichiesta.Invio);
		    cartRichiestaSchedaEndo2Service.setEffettuaValidazioneSchema(isEffettaValidazione);
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
