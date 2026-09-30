/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import java.rmi.RemoteException;

import org.openspcoop.pdd.services.SPCoopException;

/**
 * @author riccardob
 * 
 */
public interface CartInvioLocalizzazioneEndo1Service extends CartBaseService {

    public void inviaLocalizzazioneSchedaEndo(Integer codiceInventario) throws RemoteException, SPCoopException;
}
