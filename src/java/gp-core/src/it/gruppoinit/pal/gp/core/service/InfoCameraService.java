/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.infocamera.schema.serviziocommercio.Risposta.Messaggio;

/**
 * @author francescop
 * 
 */
public interface InfoCameraService {

    /**
     * Metodo si occupa dell'integrazione con Infocamera. Si occupa di tutta la comunicazione.
     * 
     * @param sToken
     * @param codice
     * @param data
     * @param codicemovimento
     * @return
     */
    public Messaggio invioInfocameraWS(String sToken, String codice, String data, String codicemovimento);
}
