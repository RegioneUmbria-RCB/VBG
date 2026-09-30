/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.sigepro.cart.service.CartRfcBaseService;

import java.rmi.RemoteException;

import org.openspcoop.pdd.services.SPCoopException;

/**
 * @author riccardob
 * 
 */
public interface CartRichiestaSchedaEndo2Service extends CartBaseService {

    public void inviaRichiestaSchedaEndo(Integer codiceAlberoproc, CartRfcBaseService.TipoRichiesta tipoRichiesta) throws RemoteException, SPCoopException;

    /**
     * La funzione richiede tutte le schede di spiegazione per gli endo di tipo 2
     * 
     * @return
     */
    public int inviaRichiestaSchedeDizionario(CartRfcBaseService.TipoRichiesta tipoRichiesta);
}
