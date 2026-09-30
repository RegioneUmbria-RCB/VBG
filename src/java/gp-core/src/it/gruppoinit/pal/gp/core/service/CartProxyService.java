package it.gruppoinit.pal.gp.core.service;


import it.gruppoinit.sigepro.cart.schema.rfcsuap.ParteLocaleSchedaEndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ParteLocaleSchedaEndoTipo2;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.openspcoop.pdd.services.SPCoopException;
import org.openspcoop.pdd.services.SPCoopMessage;

/**
 * Interfaccia per il dialogo con i metodi del cart
 * 
 * @author riccardob
 * 
 */
public interface CartProxyService {

    /**
     * Determina se il tipo endo è di tipo 1 (Adempimenti) o di tipo 2 (Attività)
     * 
     * P.S. enum CONTROLLO è utilizzata per le chiamate dal menu del backoffice
     * 
     * @author riccardob
     * 
     */
    enum TipoEndo {
	TIPO_1, TIPO_2, CONTROLLO
    }

    /**
     * Nei messaggi di richiesta delle schede indica se necessario effettuare una richiesta di INVIO o di DISPONBILITA'
     * 
     * @author riccardob
     * 
     */
    enum TipoRichiesta {
	DISPONIBILITA, INVIO
    }

    /**
     * La lista delle possibili azioni/servizi di cui è possibile richiedere i messaggi
     * 
     * @author riccardob
     * 
     */
    enum MessaggiAzioni {
	AttivazioneProcedimento, ConclusioneProcedimento, DisponibilitaDizionario, DisponibilitaSchedaEC, DisponibilitaSchedaEP, DownloadDizionario, DownloadSchedaEC, DownloadSchedaEP, InvioDizionario, InvioLocalizzazioneEC, InvioLocalizzazioneEP, InvioSchedaEC, InvioSchedaEP, Notifica, Parere, RichiestaParere
    }

    public MessaggiAzioni getAzione(String azione);

    /**
     * Ritorna la lista dei messaggi Indicando il servizio
     * 
     * @param servizio
     * @return
     */
    String[] getMessaggiPerServizio(MessaggiAzioni azione);

    /**
     * 
     * @param idMessaggio
     * @return
     */
    String getDescrizioneMessaggio(String idMessaggio);

    /**
     * 
     * @param idMessaggio
     */
    void elaboraMessaggio(String idMessaggio);

    /**
     * cancella il messaggio definito dal parametro
     * 
     * @param idMessaggio
     */
    void deleteMessaggio(String idMessaggio);

    /**
     * cancella tutti i messaggi accodati per l'ente
     * 
     */
    void deleteTuttiMessaggi();

    /**
     * effettua un invio di richiesta dizionario
     * 
     */
    void inviaRichiestaDizionario();

    /**
     * effettua un invio di richesta scheda
     * 
     * 
     * 
     */
    void inviaRichiestaScheda(String tipoEndo, String tipoRichiesta, String idEndo) throws SPCoopException, RemoteException, MalformedURLException,
	    ServiceException;

    SPCoopMessage getMessaggio(String idEGov);

    String getMessaggioBody(SPCoopMessage message);

    String getDescrizioneErrore(SPCoopException e);

    /**
     * elabora tutti i messaggi ricevuti dall'ente
     */
    void elaboraTuttiMessaggi();

    /**
     * elabora il messaggio di inviodizionario ed effettua la richiesta di tutte le schede di spiegazione endo tipo 1 e
     * 2 presenti nella base dati dopo l'aggiornamento del dizionario
     * 
     * @param idMessaggio
     *            l'id del messaggio egov
     */
    void aggiornaDizionario(String idMessaggio) throws SPCoopException, MalformedURLException, RemoteException, ServiceException;

    /**
     * rilegge i parametri di configurazione
     */
    void aggiornaConfigurazioni();

    /**
     * elabora tutti i messaggi di una determinata azione/servizio
     * 
     * @param azione
     */
    void elaboraTuttiMessaggiPerServizio(MessaggiAzioni azione);

    /**
     * Invia la localizzazione di una scheda endo di Tipo 2
     * 
     * @param entity
     * @param idEndo
     */
    public void inviaLocalizzazioneEndo2(ParteLocaleSchedaEndoTipo2 entity, Integer idEndo);

    /**
     * Invia la localizzazione di una scheda endo di Tipo 1
     * 
     * @param entity
     * @param idEndo
     */
    public void inviaLocalizzazioneEndo1(ParteLocaleSchedaEndoTipo1 entity, Integer idEndo);
}
