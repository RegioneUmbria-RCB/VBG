/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service;

import java.util.List;

import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.TipiEvento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.TipoCaricamentoPosizionidebitorie;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaPagamentoOnTheFlyResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaPagamentoOnTheFlyType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiFatturaType;
import it.gruppoinit.pal.gp.pay.ws.schema.DocumentiPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPagamentiOfflineType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieType;
import it.gruppoinit.pal.gp.pay.ws.schema.ModificaDataFineValiditaType;
import it.gruppoinit.pal.gp.pay.ws.schema.ModificaDataScadenzaType;
import it.gruppoinit.pal.gp.pay.ws.schema.OperazionePosizioniDebitorieResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileWsInType;
import it.gruppoinit.pal.gp.pay.ws.schema.RiferimentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.VerificaStatoPosizioniResponseType;

/**
 * Service che contiene le logiche di business per la gestione di tutte le operazioni sulle posizioni debitorie. Nei
 * casi in cui è previsto (caricamento, annullamento, verifica stato) il service gestisce la creazione delle richieste
 * in PAY_RICHIESTE
 * 
 * @author francol
 *
 */
public interface AttivazionePagamentiService {

    /**
     * metodo che gestisce le richieste di caricamento di nuove posizioni debitorie
     * 
     * @param regContabili
     * @param payCfg
     * @return
     * @throws PayException
     */
    public OperazionePosizioniDebitorieResponseType caricaPosizioniDebitorie(TipoCaricamentoPosizionidebitorie tipoCaricamento,
	    List<RegistrazioneContabileWsInType> regContabili, PayConfigurationHelper payCfg, boolean accorpaPosizioni, String oggettoPagamento) throws PayException;

    /**
     * metodo che gestisce le richieste di annullamento di posizioni debitorie
     * 
     * @param daAnnullare
     * @param payCfg
     * @return
     * @throws PayException
     */
    public OperazionePosizioniDebitorieResponseType annullaPosizioniDebitorie(ElencoPosizioniDebitorieType daAnnullare, PayConfigurationHelper payCfg)
	    throws PayException;

    /**
     * metodo che gestisce le richieste di attivazione di un pagamento on the fly. Il pagamento on the fly prevede
     * comunque la registrazione nel nodo pagamenti di una posizione debitoria e contestualmente la creazione della
     * sessione per l'immediato pagamento della posizione caricata. Nel caso in cui il connettore non supporti il
     * pagamento on the fly l'oggetto AttivaPagamentoOnTheFlyResponseType avrà l'attributo sessionePagamento valorizzato
     * a null. Il flusso applicativo del client che utilizza questo servizio deve tenere conto che la sessione di
     * pagamento potrebbe non essere immediatamente disponibile ed invocare comunque verificaStatoPagamenti fino a che
     * il nodo restituisce lo stato ATTIVA_IN_PSP.
     * 
     * @param parameters
     * @param cfg
     * @return
     * @throws PayException
     */
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(AttivaPagamentoOnTheFlyType parameters, PayConfigurationHelper cfg)
	    throws PayException;

    /**
     * metodo che gestisce le richieste di verifica dello stato di una o più posizioni debitorie
     * 
     * @param posizioni
     * @param cfg
     * @return
     * @throws PayException
     */
    public VerificaStatoPosizioniResponseType verificaStatoPagamenti(ElencoPosizioniDebitorieType posizioni, PayConfigurationHelper cfg)
	    throws PayException;

    /**
     * metodo che gestisce le richieste di annullamento di posizioni caricate nel PSP ma pagate dall'utente attraverso
     * canali tradizionali. Il metodo prevede anche l'invio al nodo e la registrazione dei dati relativi al pagamento
     * avvenuto
     * 
     * @param daAnnullare
     * @param payCfg
     * @return
     * @throws PayException
     */
    public OperazionePosizioniDebitorieResponseType registraPagamentiOfflineEAnnulla(ElencoPagamentiOfflineType daAnnullare,
	    PayConfigurationHelper payCfg) throws PayException;

    /**
     * metodo che gestisce le richieste di attivazione di una nuova sessione di pagamento per una posizione
     * precedentemente caricata nel PSP. Può anche essere invocato su posizioni caricate per il pagamento on the fly.
     * 
     * @param sesRequest
     *            TODO
     * @return
     * @throws PayException
     */
    public AttivaSessionePagamentoResponseType attivaSessionePagamento(AttivaSessionePagamentoType sesRequest) throws PayException;

    /**
     * metodo che registra nel DB del nodo pagamenti tutte le informazioni necessarie relative ad una nuova
     * registrazione contabile. Si intende per registrazione contabile un entità logica che aggrega una o più posizioni
     * debitorie che costituiscono la rateizzazione di un singolo debito dell'utente verso la PA. vengono registrati in
     * una singola transazione sepoarata: - un record in PAY_REGISTRAZIONI_CONTABILI - da 1 a N record in
     * PAY_POSIZIONI_DEBITORIE - da 1 a N record in PAY_DETTAGLIO_IMPORTI per ciascuna posizione debitoria - 1 record in
     * PAY_SOGGETTI_DEBITORI per ciascuna posizione debitoria - 1 record in PAY_STATO_PAGAMENTI per ciascuna posizione
     * debitoria con stato impostato su ACQUISITA - 1 record in PAY_RICHIESTE per ciascuna posizione debitoria
     * 
     * @param regCont
     * @param tipoRichiesta
     * @param payCfg
     * @param otf
     * @return
     * @throws PayException
     */
    public PayRegistrazioniContabili richiestaCaricamentoPosizioniECommittaDati(RegistrazioneContabileType regCont, TipiEvento tipoRichiesta,
	    PayConfigurationHelper payCfg, boolean otf) throws PayException;

    public ElencoDocumentiEsitoType generaFatture(List<DatiFatturaType> datiFatture) throws PayException;

    public ElencoDocumentiEsitoType inviaAvvisi(List<RiferimentoPosizioneDebitoriaType> refPosizioni) throws PayException;

    public ElencoDocumentiEsitoType scaricaRicevute(List<RiferimentoPosizioneDebitoriaType> refPosizioni) throws PayException;

    public ElencoDocumentiType getElencoTipiDocumenti(DocumentiPosizioneDebitoriaType documentiPosizioneDebitorieType) throws PayException;

    /**
     * La data di scadenza può essere modificata solamente se la posizione debitoria non è in uno dei seguenti stati:
     * 
     * {@link StatoPagamentoType#ANNULLAMENTO_RICHIESTO} <br />
     * {@link StatoPagamentoType#RENDICONTATO_DA_IC} <br />
     * {@link StatoPagamentoType#NOTIFICATO_DA_PSP} <br />
     * {@link StatoPagamentoType#PAGATO_OFFLINE_DA_ANNULLARE} <br />
     * {@link StatoPagamentoType#PAGATO_OFFLINE_ANNULLATO} <br />
     * {@link StatoPagamentoType#ANNULLATO} <br />
     * {@link StatoPagamentoType#CON_ERRORE} <br />
     * {@link PayException} <br />
     * Qualora il connettore supporti la modifica della data di scadenza viene invocato il relativo metodo ed in caso di
     * errore viene rilanciata una PayException
     * 
     * @param modificaDataScadenzaType
     * @return
     */
    public void modificaDataScadenzaPosizioneDebitoria(ModificaDataScadenzaType modificaDataScadenzaType) throws PayException;

    public void modificaDataFineValiditaPosizioneDebitoria(ModificaDataFineValiditaType modificaDataFineValiditaType) throws PayException;
}
