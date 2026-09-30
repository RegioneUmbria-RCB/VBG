package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.helper.CartInfoDizionarioHelper;

import java.rmi.RemoteException;
import java.util.List;

import org.openspcoop.pdd.services.SPCoopException;
import org.openspcoop.pdd.services.SPCoopMessage;

public interface CartBaseServiceERO extends CartBaseService {

    public void elaboraMessaggio(String idEgov) throws RemoteException, SPCoopException;

    /**
     * Metodo che recupera indfromazioni per l'elaborazione del dizionario per permettere la configurazione
     * prima  di elaborazione dei messaggi del dizionario
     */
    public CartInfoDizionarioHelper preElaboraMessaggio(String idEgov) throws RemoteException, SPCoopException;
    /**
     * Elabora i messaggi del dizionario andando a inserire:
     * <ol>
     * 	<li>Inventario procedimenti</li>
     * 	<li>Albero proc</li>
     * 	<li>Terzo capire</li>
     * </ol>
     * @param cartInfoDizionarioHelper
     * @throws RemoteException
     * @throws SPCoopException
     */
    public void elaboraMessaggio(CartInfoDizionarioHelper cartInfoDizionarioHelper) throws RemoteException, SPCoopException;

    public SPCoopMessage getMessage(String idEGov) throws RemoteException, SPCoopException;

    public void deleteMessage(String idEGov) throws RemoteException, SPCoopException;

    public String[] getAllMessagesId() throws RemoteException, SPCoopException;

    public String[] getNextMessagesId(int numeroDeiMessaggi) throws RemoteException, SPCoopException;

    public void deleteAllMessages() throws RemoteException, SPCoopException;

    public void elaboraTuttiMessaggi();
}