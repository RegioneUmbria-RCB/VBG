package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.sigepro.cart.service.CartRfcBaseService;

import java.rmi.RemoteException;

import org.openspcoop.pdd.services.SPCoopException;

public interface CartRichiestaSchedaEndo1Service extends CartBaseService {

    public void inviaRichiestaSchedaEndo(Integer codiceInventario, CartRfcBaseService.TipoRichiesta tipoRichiesta) throws RemoteException, SPCoopException;

    /**
     * La funzione richiede tutte le schede di spiegazione per gli endo di tipo 1
     * 
     * @return
     */
    public int inviaRichiestaSchedeDizionario(CartRfcBaseService.TipoRichiesta tipoRichiesta);
}
