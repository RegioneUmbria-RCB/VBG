/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.openspcoop.pdd.services.SPCoopException;
import org.openspcoop.pdd.services.SPCoopMessage;

/**
 * @author francescop
 * 
 */
public interface CartWsClientService {

    public String[] getAllMessagesId() throws SPCoopException, RemoteException, MalformedURLException, ServiceException;

    public String[] getAllMessagesIdByService(String service) throws SPCoopException, RemoteException, MalformedURLException, ServiceException;

    public String getMessaggioBody(SPCoopMessage messaggio);

    public String getDescrizioneEccezione(SPCoopException e);

    public SPCoopMessage getMessaggio(String idEgov) throws Exception;

    public void deleteMessage(String idMessage) throws MalformedURLException, ServiceException, SPCoopException, RemoteException;

    public void deleteAllMessage() throws MalformedURLException, ServiceException, SPCoopException, RemoteException;

    public SPCoopMessage invocaPortaDelegata(String pddLocation, SPCoopMessage messaggio) throws SPCoopException, RemoteException,
	    MalformedURLException, ServiceException;

    public void reloadPortConfiguration();
}
