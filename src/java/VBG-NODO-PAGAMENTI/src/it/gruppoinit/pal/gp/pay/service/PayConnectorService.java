/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.command.GenerazioneFattureCommand;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfig;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayRichieste;
import it.gruppoinit.pal.gp.pay.domain.TipiEvento;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;

/**
 * @author francol
 *
 */
public interface PayConnectorService extends BaseService<PayConnectorConfig, String> {

    /**
     * Restituisce l'istanza del connettore per il profilo ente creditore corrente della request
     * 
     * @return
     * @throws PayConfigurationException
     */
    public IPayConnector getPayConnectorInstance() throws PayConfigurationException;

    /**
     * Restituisce l'istanza del connettore per il profilo ente creditore passato come argomento
     * 
     * @return
     * @throws PayConfigurationException
     */
    public IPayConnector getPayConnectorInstance(PayConnectorConfig cfg) throws PayConfigurationException;

    /**
     * metodo wrapper per la gestione dell'inserimento nel connettore
     * 
     * @param datiRegistrazioniCommand
     * @return
     * @throws PayException
     */
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorieInPSP(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException;

    /**
     * metodo wrapper per la gestione dell'annullamento nel connettore
     * 
     * @param datiRegistrazioniCommand
     * @return
     */
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorieInPSP(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagataOffline)
	    throws PayException;

    /**
     * metodo wrapper per la gestione dell'esito della verifica dello stato delle posizioni debitorie
     * 
     * @param cmd
     * @return
     * @throws PayException
     *
     */
    public ElencoStatoPosizioniType verificaStatoPosizioniInPSP(RichiestaSuListaPosizioniCommand cmd) throws PayException;

    /**
     * metodo wrapper per la gestione dell'esito della rendicontazione dei pagamenti
     * 
     * @param cmd
     * @return
     * @throws PayException
     *
     */
    public ElencoStatoPosizioniType rendicontazionePagamentiPSP(RichiestaSuListaPosizioniCommand cmd) throws PayException;

    /**
     * metodo wrapper per la gestione dell'esito della generazione fatture
     * 
     * @param cmd
     * @return
     * @throws PayException
     *
     */
    public ElencoDocumentiEsitoType generaFatturePSP(GenerazioneFattureCommand cmd) throws PayException;

    /**
     * metodo wrapper per la gestione dell'esito dell'invio degli avvisi di pagamento
     * 
     * @param cmd
     * @return
     * @throws PayException
     *
     */
    public ElencoDocumentiEsitoType inviaAvvisiPSP(PosizioniDebitorieCommand cmd) throws PayException;

    /**
     * metodo wrapper per la gestione dell'esito del download delle ricevute telematiche
     * 
     * @param cmd
     * @return
     * @throws PayException
     *
     */
    public ElencoDocumentiEsitoType scaricaRicevutePSP(RichiestaSuListaPosizioniCommand cmd) throws PayException;

    /**
     * Metodo usato dallo scheduler per popolare i command passati come argomento nelle chiamate ai metodi dei
     * connettori
     * 
     * @param richieste
     * @return
     */
    public RichiestaSuListaPosizioniCommand populateRichiestaSuListaPosizioniCommand(List<PayRichieste> richieste);

    /**
     * Metodo usato dallo scheduler per popolare i command passati come argomento nelle chiamate ai metodi dei
     * connettori
     * 
     * @param richieste
     * @return
     */
    public PosizioniDebitorieCommand populatePosizioniDebitorieCommand(List<PayRichieste> richieste);

    /**
     * restituisce i dati di configurazione dell'endpoint associato al tipo di servizio specificato, null se il servizio
     * non è supportato dal connettore
     * 
     * @param tipoServizio
     * @return
     */
    public PayConnectorWsEndpoint getEndpointPerTipoServizio(TipiEvento tipoServizio);

    /**
     * restituisce true se nel connettore attualmente attivo il servizio passato in input è impostato per essere
     * eseguito solo come job schedulato
     * 
     * @param tipoServizio
     * @return
     */
    public boolean isServizioSoloSchedulato(TipiEvento tipoServizio);

    /**
     * restituisce true se nel connettore attualmente attivo è configurato lo scheduler di quartz per il servizio
     * passato in input e il flag_spegni_scheduler != true.
     * 
     * @param tipoServizio
     * @return
     */
    public boolean isSchedulerAttivo(TipiEvento tipoServizio);

    /**
     * Ricarica le configurazioni dei connettori e registra quelli nuovi <br />
     * ATTENZIONE!!! NON ricarica eventuali servizi schedulati. È richiesto per questo un riavvio
     */
    public Map<String, String> reloadConnectorParams();

    /**
     * 
     */
    public void modificaDataScadenzaPosizioneInPsp(Integer posizioneDebitoria, Date nuovaDataScadenza) throws PayException;

    public void modificaDataFineValiditaPosizioneInPsp(Integer posizioneDebitoria, Date nuovaDataFineValidita) throws PayException;

    public void registraCaricamentoMassivoPosizioniDebitorie(PosizioniDebitorieCommand connectorCommand, String identificativoOperazione);
}
