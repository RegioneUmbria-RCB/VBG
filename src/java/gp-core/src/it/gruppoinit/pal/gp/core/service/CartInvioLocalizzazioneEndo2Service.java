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
public interface CartInvioLocalizzazioneEndo2Service extends CartBaseService {

    public void inviaLocalizzazioneSchedaEndo(Integer codiceAlberoproc) throws RemoteException, SPCoopException;
}
