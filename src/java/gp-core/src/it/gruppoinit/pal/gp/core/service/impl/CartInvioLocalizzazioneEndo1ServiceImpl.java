/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.service.CartInvioLocalizzazioneEndo1Service;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioLocalizzazioneSchedaEndoTipo1;
import it.gruppoinit.sigepro.cart.service.fru.CartRfc183InvioLocalizzazioneEndo1ServiceClient;

import java.rmi.RemoteException;

import org.openspcoop.pdd.services.SPCoopException;
import org.springframework.stereotype.Service;

/**
 * @author riccardob
 * 
 */
@Service
public class CartInvioLocalizzazioneEndo1ServiceImpl extends CartBaseServiceImpl implements CartInvioLocalizzazioneEndo1Service {

    //private static final Logger log = LoggerFactory.getLogger(CartInvioLocalizzazioneEndo1ServiceImpl.class);
    
    private CartRfc183InvioLocalizzazioneEndo1ServiceClient cartInvioLocalizzazioneEndo1ServiceClient;
    
    public void setCartInvioLocalizzazioneEndo1ServiceClient(CartRfc183InvioLocalizzazioneEndo1ServiceClient invioLocEndo1Service){
	this.cartInvioLocalizzazioneEndo1ServiceClient = invioLocEndo1Service;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CartInvioLocalizzazioneEndo1Service#inviaLocalizzazioneSchedaEndo(java.lang.Integer)
     */
    @Override
    public void inviaLocalizzazioneSchedaEndo(Integer codiceInventario) throws RemoteException, SPCoopException {

	this.cartInvioLocalizzazioneEndo1ServiceClient.inviaLocalizzazioneSchedaEndo(preparaMessaggioRichiesta(codiceInventario));
    }

    private InvioLocalizzazioneSchedaEndoTipo1 preparaMessaggioRichiesta(Integer codiceInventario) {

	// TODO implementare la logica che carica l'oggetto con i dati per l'invio della localizzazione dell'endo
	return null;
    }
}
