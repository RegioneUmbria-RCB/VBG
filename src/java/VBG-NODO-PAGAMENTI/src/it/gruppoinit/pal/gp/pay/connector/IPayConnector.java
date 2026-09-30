/**
 * 
 */
package it.gruppoinit.pal.gp.pay.connector;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.pay.command.GenerazioneFattureCommand;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfig;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.scheduler.IServizioSchedulato;
import it.gruppoinit.pal.gp.pay.scheduler.ServiziSchedulatiEnum;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaPagamentoOnTheFlyResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoDocumentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;

/**
 * Interfaccia che definisce le API di comunicazione fra il nodo dei pagamenti e i connettori di pagamento specifici dei
 * singoli PSP. Per ogni PSP che deve essere integrato nel nodo pagamenti dovrà essere realizzata una implementazione di
 * IPayConnector che si occupa di interagire con i servizi specific del PSP. Per ogni PSP dovrà essere creato un package
 * distinto denominato it.gruppoinit.pal.gp.pay.connector.<nome_psp> che conterrà il connector ed eventuali classi
 * necessarie al suo funzionamento. I servizi (soap o rest) che il connector espone saranno messi nel sottopackage
 * it.gruppoinit.pal.gp.pay.connector.<nome_psp>.ws.server. Le classi che utilizza per consumare i servizi del PSP
 * saranno nel package it.gruppoinit.pal.gp.pay.connector.<nome_psp>.ws.client.
 * 
 * @author francol
 *
 */
public interface IPayConnector extends IPayEndpointConfigurable {

    /**
     * Metodo che viene invocato dal nodo pagamenti nel momento in cui si devono effettivamente trasmettere al PSP le
     * posizioni debitorie da registrare e mettere in pagamento. L'implementazione si deve occupare di invocare i
     * servizi del PSP per la messa in pagamento delle posizioni debitorie passate in input. L'esito dell'invocazione
     * del servizio deve essere gestito a livello della singola posizione debitoria e va riportato nella lista di
     * {@link EsitoOperazionePosizioneDebitoriaType} contenuta nel secondo argomento Ogni volta che il connector invoca
     * un servizio del PSP (o che un suo servizio viene invocato dal PSP) deve registrare un record in PY_IO_EVENTI con
     * CODICE_COMUNICAZIONE uguale all' idRichiesta del command passato come argomento.
     * 
     * @param datiRegistrazioni
     *            contiene l'identificativo univoco della richiesta e l'elenco delle posizioni debitorie da registrare
     *            nel PSP, le posizioni debitorie sono aggregate per registrazione contabile. Più posizioni debitorie in
     *            una stessa registrazione contabile rappresentano la rateizzazione di un pagamento. Gli oggetti di
     *            dominio che contengono i dati delle registrazioni contabili e delle posizioni debitorie contenuti nel
     *            command sono l'esatta rappresentazione dello stato delle informazioni relative alla richiesta servita
     *            dal nodo. Prima della chiamata al connettore il nodo ha già scritto e committato nel DB gli oggetti di
     *            dominio passati nel command. Se il nodo ha fallito l'inserimento delle registrazioni contabili e delle
     *            posizioni debitorie collegato gli oggetti di dominio corrispondenti non saranno presenti
     * 
     * @return ElencoPosizioniDebitorieEsitoType che contiene gli esiti delle operazioni eseguite dal connettore su
     *         ciascuna posizione debitoria
     * @throws PayException
     */
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException;

    /**
     * Metodo che viene invocato dal nodo pagamenti nel momento in cui si devono annullare sul PSP le posizioni
     * debitorie già mettesse in pagamento. L'implementazione si deve occupare di invocare i servizi del PSP per
     * l'annullamento delle posizioni debitorie passate in input. Ogni volta che il connector invoca un servizio del PSP
     * (o che un suo servizio viene invocato dal PSP) deve registrare un record in PAY_IO_EVENTI con
     * CODICE_COMUNICAZIONE uguale all' idRichiesta del command passato come argomento.
     * 
     * @param pagatoOffline
     *            TODO
     * @param datiRegistrazioni
     *            contiene l'identificativo univoco della richiesta e l'elenco delle posizioni debitorie da annullare
     *            nel PSP, le posizioni debitorie sono aggregate per registrazione contabile. Più posizioni debitorie in
     *            una stessa registrazione contabile rappresentano la rateizzazione di un pagamento. Gli oggetti di
     *            dominio che contengono i dati delle registrazioni contabili e delle posizioni debitorie contenuti nel
     *            command sono l'esatta rappresentazione dello stato delle informazioni relative alla richiesta servita
     *            dal nodo. Prima della chiamata al connettore il nodo ha già aggiornato nel DB gli oggetti di dominio
     *            passati nel command.
     * 
     * @return ElencoPosizioniDebitorieEsitoType che contiene gli esiti delle operazioni eseguite dal connettore su
     *         ciascuna posizione debitoria
     */
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayException;

    /**
     * Metodo invocato dal nodo pagamenti quando riceve una ricchiesta di verifica dello stato di una o più posizioni
     * debitorie e il loro stato non risulta ancora pagato o annullato nel nodo pagamenti.
     * 
     * @param cmd
     * @return
     * @throws PayException
     */
    public ElencoStatoPosizioniType verificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException;

    /**
     * Metodo invocato dal nodo pagamenti per sincronizzare i dati relativi alla rendicontazione delle trasazioni di
     * pagamento delle posizioni debitorie. Non è invocato automaticamente dal nodo pagamenti in risposta a richieste
     * che riceve e serve solo nel caso in cui i dati dei pagamenti non siano restituiti dal servizio di verifica dello
     * stato delle posizioni verificaStatoPagamenti. Il connettore può invocare direttamente il metodo
     * rendicontazioneDatiPagamenti dall'interno del metodo verificaStatoPagamenti se vuole/può completare la chiamata
     * in modo sincrono oppure può memorizzare nel nodo una richiesta in sosspeso di sincronizzazione della
     * rendicontazione per la posizione che sta elaborando. La richiesta in sospeso viene registrata inserendo un record
     * in PAY_RICHIESTE del tipo RENDICONTAZIONE_PAGAMENTO_PSP per la posizione che si desidera sincronizzare. Se il
     * servizio di rendicontazione del connettore è configurato per essere schedulato
     * (PAY_WS_ENDPOINT_CONFIG.QUANTZ_SCHEDULE impostato con espressione CRON valida) il nodo effetuterà automaticamente
     * una ricerca dei dati di pagamento per le richieste in sospeso ad intervalli regolari definiti appunto nella
     * configurazione del servizio. Spesso i servizi di rendicontazione dei dati bancari prevedono l'accesso a cartelle
     * FTP o servizi SOAP i cui contenuti sono aggiornati ad intervalli regolari e non ha quindi senso invocare il
     * servizio di rendicontazione in modo sincrono in risposta ad ogni chiamata cheil nodo riceve dall'utente. In
     * questi casi conviene appunto impostare le richieste in sospeso per le posizioni che in verificaStatoPagamenti
     * risultano pagate ed aspettare che il nodo sincronizzi i dati di pagamento sulla base della schedulazione
     * programmata.
     * 
     * @param cmd
     * @return
     * @throws PayException
     */
    public ElencoStatoPosizioniType rendicontazionePagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException;

    /**
     * metodo che si occupa di inviare gli avvisi di pagamento PagoPA ai soggetti debitori di un elenco di posizioni
     * debitorie. L'oggetto restituito conterrà una {@link List} di {@link EsitoDocumentoPosizioneDebitoriaType}. Le
     * istanze di questa classe che estende {@link EsitoOperazionePosizioneDebitoriaType} dovranno rappresentare l'esito
     * dell'operazione di generazione e invio dell'avviso di pagamento. Oltre alle informazioni sull'esito
     * dell'operazione come lo stato della posizione e i dettagli di eventuali errori che possono essersi verificati le
     * istanze di questa classe che vengono restiuite devono contenere anche informazioni sul documento prodotto durante
     * l'elaborazione della richiesta: il tipo di documento prodotto (deve essere uguale a TipoDocumentoType.AVVISO), lo
     * stato di effettiva disponibilità del documento nel nodo al termine dell'operazione e, se il documento è
     * disponibile, un {@link DataHandler} da cui il nodo pagamenti potrà recuperare i dati binari del documento per
     * memorizzarli in PAY_DOCUMENTI. Come nel caso delle altre operazioni il connettore deve limitarsi a restituire le
     * informazioni richieste per ogni posizione debitoria in input, l'effettivo salvataggio nel DB del documento
     * prodotto sarà effettuato dal nodo pagamenti in base ai valori restituiti dal connettore al termine della chiamata
     * di questo metodo. Se il servizio di invio degli avvisi è schedulato in PAY_WS_ENDPOINT_CONFIG questo metodo è
     * invocato dallo scheduler con l'elenco di tutte delle richieste ancora da elaborare, se invece non è schedulato il
     * metodo è invocato una volta per ogni richiesta ricevuta dal client e conterrà una sola posizione debitoria nel
     * command passato come argomento.
     * 
     * @param cmd
     * @return
     * @throws PayException
     */
    public ElencoDocumentiEsitoType inviaAvvisiPagamento(PosizioniDebitorieCommand cmd) throws PayException;

    /**
     * metodo che si occupa di generare le fatture per un elenco di posizioni debitorie e di integrarsi con eventuali
     * software gestionali di contabilità esterni a cui, nella maggior parte dei casi, sarà delegata la generazione
     * delle fatture stesse. L'oggetto restituito conterrà una {@link List} di
     * {@link EsitoDocumentoPosizioneDebitoriaType}. Le istanze di questa classe che estende
     * {@link EsitoOperazionePosizioneDebitoriaType} dovranno rappresentare l'esito dell'operazione di generazione della
     * fattura. Oltre alle informazioni sull'esito dell'operazione come lo stato della posizione e i dettagli di
     * eventuali errori che possono essersi verificati le istanze di questa classe che vengono restiuite devono
     * contenere anche informazioni sul documento prodotto durante l'elaborazione della richiesta: il tipo di documento
     * prodotto (deve essere uguale a TipoDocumentoType.FATTURA), lo stato di effettiva disponibilità del documento nel
     * nodo al termine dell'operazione e, se il documento è disponibile, un {@link DataHandler} da cui il nodo pagamenti
     * potrà recuperare i dati binari del documento per memorizzarli in PAY_DOCUMENTI. Come nel caso delle altre
     * operazioni il connettore deve limitarsi a restituire le informazioni richieste per ogni posizione debitoria in
     * input, l'effettivo salvataggio nel DB del documento prodotto sarà effettuato dal nodo pagamenti in base ai valori
     * restituiti dal connettore al termine della chiamata di questo metodo. Se il servizio di generazione delle fatture
     * è schedulato in PAY_WS_ENDPOINT_CONFIG questo metodo è invocato dallo scheduler con l'elenco di tutte delle
     * richieste ancora da elaborare, se invece non è schedulato il metodo è invocato una volta per ogni richiesta
     * ricevuta dal client e conterrà una sola posizione debitoria nel command passato come argomento.
     * 
     * @param cmd
     * @return
     * @throws PayException
     */
    public ElencoDocumentiEsitoType generaFatture(GenerazioneFattureCommand cmd) throws PayException;

    /**
     * metodo che si occupa di scaricare le ricevute telematiche di pagamento per un elenco di posizioni debitorie.
     * L'oggetto restituito conterrà una {@link List} di {@link EsitoDocumentoPosizioneDebitoriaType}. Le istanze di
     * questa classe che estende {@link EsitoOperazionePosizioneDebitoriaType} dovranno rappresentare l'esito
     * dell'operazione di download della ricevuta. Oltre alle informazioni sull'esito dell'operazione come lo stato
     * della posizione e i dettagli di eventuali errori che possono essersi verificati le istanze di questa classe che
     * vengono restiuite devono contenere anche informazioni sul documento prodotto durante l'elaborazione della
     * richiesta: il tipo di documento prodotto (deve essere uguale a TipoDocumentoType.RICEVUTA), lo stato di effettiva
     * disponibilità del documento nel nodo al termine dell'operazione e, se il documento è disponibile, un
     * {@link DataHandler} da cui il nodo pagamenti potrà recuperare i dati binari del documento per memorizzarli in
     * PAY_DOCUMENTI. Come nel caso delle altre operazioni il connettore deve limitarsi a restituire le informazioni
     * richieste per ogni posizione debitoria in input, l'effettivo salvataggio nel DB del documento prodotto sarà
     * effettuato dal nodo pagamenti in base ai valori restituiti dal connettore al termine della chiamata di questo
     * metodo. Se il servizio di download delle ricevute è schedulato in PAY_WS_ENDPOINT_CONFIG questo metodo è invocato
     * dallo scheduler con l'elenco di tutte delle richieste ancora da elaborare, se invece non è schedulato il metodo è
     * invocato una volta per ogni richiesta ricevuta dal client e conterrà una sola posizione debitoria nel command
     * passato come argomento.
     * 
     * @param cmd
     * @return
     * @throws PayException
     */
    public ElencoDocumentiEsitoType scaricaRicevuteTelematiche(RichiestaSuListaPosizioniCommand cmd) throws PayException;

    /**
     * Metodo che indica al nodo dei pagamenti e ai client se è possibile attivare uyn pagamento on the fly nel PSP
     * integrato dal connettore. Restituire false se il PSP non espone un servizio per questa funzionalità
     * 
     * @return
     */
    public boolean supportaPagamentoOnTheFly();

    /**
     * Metodo che indica al nodo dei pagamenti se esiste uno specifico servizio del PSP per la verifica dello stato di
     * un pagamento
     * 
     * @return
     */
    public boolean supportaVerificaPagamento();

    /**
     * Metodo che indica al nodo dei pagamenti se esiste uno specifico servizio del PSP per la rendicontazione dei
     * pagamenti avvenuti
     * 
     * @return
     */
    public boolean supportaRendicontazionePagamenti();

    /**
     * Metodo che indica al nodo dei pagamenti e ai client se il connettore supporta la generazione dell'avviso di
     * pagamento PagoPA pagamenti avvenuti
     * 
     * @return
     */
    public boolean supportaAvvisoPagamento();

    /**
     * Metodo che indica al nodo dei pagamenti e ai client se il connettore consente il download della ricevuta
     * telematica pagamenti avvenuti
     * 
     * @return
     */
    public boolean supportaRicevutaTelematica();

    /**
     * Metodo che indica al nodo dei pagamenti e ai client se il connettore supporta la generazione della fattura per le
     * posizioni debitorie pagamenti avvenuti
     * 
     * @return
     */
    public boolean supportaGenerazioneFattura();

    /**
     * Metodo che indica ai client se il connettore richiede la generazione della fattura per poter caricare
     * correttamente le posizioni debitorie nel sistema di pagamenti esterno. Se il metodo restituisce true i client
     * devono invocare generaFattura successivamente a InserisciPosizioniDebitorie affinchè il nodo pagamenti possa
     * portare a termine il caricamento della posizione
     * 
     * @return
     */
    public boolean isGenerazioneFatturaObbligatoria();

    /**
     * Il metodo riceve in input i dati di una posizione debitoria già registrata nel nodo che deve essere ancora creata
     * nel PSP in modalità on the fly. Restituisce i riferimenti della nuova posizione appena creata e i dati della
     * sessione di pagamento che consentiranno al chiamante di procedere immmediatamente al pagamento online della
     * posizione caricata. Se supportaPagamentoOnTheFly restituisce false questo metodo non viene neppure invocato dal
     * nodo pagamenti.
     * 
     * @param cmd
     * @return
     */
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(PosizioniDebitorieCommand cmd) throws PayException;

    public AttivaSessionePagamentoResponseType attivaSessionePagamento(PayPosizioniDebitorie payPosizioneDebitoria) throws PayException;

    /**
     * generazione dell'id messaggio secondo logiche specifiche del PSP
     * 
     * @return
     */
    public String generaIdMessaggio();

    /**
     * generazione dell'id della posizione debitoria così come trasmesso al PSP
     * 
     * @param pos
     *            TODO
     * @return
     */
    public String generaIdPosizioneDebitoria(PayPosizioniDebitorie pos);

    /**
     * generazione dello IUV secondo specifiche AGID.
     * 
     * @param pos
     *            TODO
     * @return
     */
    public IUVHelper generaIUV(PayPosizioniDebitorie pos, String identificativoCausalePerCalcolo);

    /**
     * parsing dell'id di una posizione debitoria registrata nel PSP
     * 
     * @param posId
     * @return
     */
    public PkId parseIdPosizioneDebitoria(String posId);

    /**
     * il metodo riceve in input la mappa dei paramnetri request che vengono passati dal PSP quando l'utente torna
     * indietro dal portale dei pagamenti dopo aver concluso con successo una trannsazione online. Nelle implementazioni
     * il connettore deve recuperare i dati di suo interesse dalla {@link Map} e gestire eventuali logiche relative allo
     * stato della posizione debitoria e/o della sessione di pagamento ad essa collegata.
     * 
     * @param reqParams
     * @return {@link PaySessioniPagamento} la sessione di pagamento attivata dall'utente per effettuare il pagamento
     *         online, eventualmente già aggiornata dal connettore.
     */
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams);

    /**
     * Restituisce il nome del connettore che descrive agli utenti il sistema di pagamento con cui il nodo è integrato
     * 
     * @return String
     */
    public String getConnectorName();

    /**
     * Imposta il nome del connettore che descrive agli utenti il sistema di pagamento con cui il nodo è integrato.
     * Usato solo dal nodo in fase di inizializzazione dei connettori
     * 
     * @return void
     */
    public void setConnectorName(String conName);

    /**
     * Restituisce il codice del connettore che serve al nodo pagamenti per recuperare i parametri di configurazione
     * specifiche del connettore
     * 
     * @return String
     */
    public String getConnectorCode();

    /**
     * Imposta il codice del connettore che serve al nodo pagamenti per recuperare i parametri di configurazione
     * specifiche del connettore Usato solo dal nodo in fase di inizializzazione dei connettori
     * 
     * @return String
     */
    public void setConnectorCode(String code);

    /**
     * Effettua la validazione formale delle chiamate. lo specifico connettore dovrà implementare la logica di
     * prevalidazione
     * 
     * @param registrazioneContabile
     * @throws ValidazionePosizioniDebitorieException
     */
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException;

    /**
     * Ricarica le configurazioni degli pay_connector_ws_endpoint e registra i nuovi connettori
     */
    public void refreshWs(PayConnectorConfig cfg);

    /**
     * Specifica se il connettore supporta ls funzionalità di attivaSessionePagamento ovvero il pagamento sullo WISP
     * delle posizioni debitorie predeterminate.
     * 
     * @return
     */
    public boolean supportaAttivaSessionePagamento();

    /**
     * Specifica se il connettore supporta la modifica della data di scadenza delle posizioni debitorie. È possibile
     * modificare la data di scadenza di posizioni nello stato #StatoPagamentoType e non pagate (ovvero)
     * 
     * @return
     */
    public boolean supportaModificaDataScadenza();

    /**
     * Specifica se il connettore supporta la modifica della data di fine validita delle posizioni debitorie. È
     * possibile modificare la data di fine validita di posizioni nello stato #StatoPagamentoType e non pagate (ovvero)
     * 
     * @return
     */
    public boolean supportaModificaDataFineValidita();

    /**
     * Specifica se il connettore supporta il pagamentoOffLine. Di default è implementato.
     * 
     * @return
     */
    public boolean supportaPagamentoOffLine();

    void modificaDataScadenzaPosizioneDebitoria(Integer idPosizioneDebitoria, Date nuovaDataScadenza) throws PayException;

    void modificaDataFineValiditaPosizioneDebitoria(Integer idPosizioneDebitoria, Date nuovaDataFineValidita) throws PayException;

    /**
     * Specifica se il connettore supporta il pagamento traminte rata unica. Di default è false
     * 
     * @return
     */
    public boolean supportaRataUnica();

    /**
     * Sepcifica se il connettore supporta l'invio di più di una causale raggruppata
     * 
     * @return
     */
    public boolean supportaMolteCausaliRaggruppate();

    /***
     * Ritorna la lista dei parametri che il connettore richiede
     * 
     * @return
     */
    public List<IParameter> getListaParametriRichiesti();

    /**
     * In base a <b>modalitaGenerazioneIUV()</b> <br>
     * - se CONTESTUALE_APERTURA_POSIZIONE ritorna null<br>
     * - se METODO_DEDICATO Richiama IUVHelper.generaIUV<br>
     * - se INTERNA richiama IUVHelper.generaIUV
     * 
     * @param payPos
     *            la posizione debitoria per la quale calcolare lo iuv
     * @param identificativoCausalePerCalcolo
     *            ParametroPagoPAServiceIdCausaleIUV
     * @return
     */
    public String getIUV(PayPosizioniDebitorie payPos, String identificativoCausalePerCalcolo);

    /**
     * Ogni connettore può supportare una serie di servizi schedulati identificati dall'enumeration
     * {@link ServiziSchedulatiEnum}. Ad esempio {@link ServiziSchedulatiEnum#ELABORAZIONE_TRACCIATI}
     * 
     * @return
     */
    public List<ServiziSchedulatiEnum> getListaServiziSchedulatiSupportati();

    public IServizioSchedulato getServizioSchedulatoPerTipo(ServiziSchedulatiEnum tipoServizio);

    /**
     * Specifica se il connettore supporta l'operazione di caricamento massivo Es. GENOVA Tracciato NEXI
     * 
     * @return
     */
    public boolean supportaCaricamentoMassivo();
    
    /**
     * Specifica se il connettore supporta l'operazione di verifica/allineamento delle posizioni debitorie di un determinato CF
     *
     * @return
     */
    public boolean supportaSincronizzaDebitiPerSoggetto();
}
