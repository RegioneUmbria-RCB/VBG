/**
 * 
 */
package it.gruppoinit.pal.gp.core.features.oneri;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.IstanzeoneriDAO;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.IstoneriDettPosizioni;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Raggruppamentocausalioneri;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.helper.OneriEntrateUsciteAmministrazioneHelper;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DatiPagamento;
import it.gruppoinit.pal.gp.core.features.scadenzario.ScadenzarioOneri;
import it.gruppoinit.pal.gp.core.filters.TipologiaOnere;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.core.service.helper.TipoAccessoEnum;

/**
 * @author francescop
 * 
 */
public interface IstanzeoneriService extends BaseService<Istanzeoneri, PkId> {

    /**
     * metodo per recuperare le ISTANZEONERI secondo i parametri passati. metodo utilizzato per il tipo funzione di
     * Regulus: GET_DEBT_SITUATION
     * 
     * @param codiceFiscale
     * @param DATAINIZIO
     * @param DATAFINE
     * @param annoDocumento
     * @param codiceTributo
     * 
     * @return
     */
    public List<Istanzeoneri> getDebtSituationIstanzeOneri(String codiceFiscale, Date DATAINIZIO, Date DATAFINE, String annoDocumento,
	    String codiceTributo);

    /**
     * Determina il numero delle rate in scadenza per Tipicausalioneri
     * 
     * @param tipicausalioneri
     * @return
     */
    public Integer getNumeroRateInScadenza(Tipicausalioneri tipicausalioneri);

    /**
     * Metodo per recuperare le ISTANZEONERI secondo i parametri passati. metodo utilizzato per il tipo funzione di
     * Regulus: GET_BILL_DETAILS
     * 
     * @param nrDocumento
     * @param codicefiscale
     * @param annoDocumento
     * @param codiceTributo
     * @return
     */
    public List<Istanzeoneri> getBillDetailsIstanzeOneri(String nrDocumento, String codicefiscale, String annoDocumento, String codiceTributo);

    /**
     * determina l'istanza collegata a un numero di documento
     * 
     * @param nrDocumento
     * @return
     */
    public Istanze getIstanzeByNrDocumento(String nrDocumento);

    /**
     * METODO PER RECUPERARE L'ISTANZEONERI CON UN DETERMINATO NUMERO DOCUMENTO E NUMERO RATA.LE ISTANZEONERI SONO
     * FILTRATE PER DATAPAGAMENTO IS NULL.
     * 
     * @param nrDocumento
     * @param nrRata
     * @return
     */
    public Istanzeoneri getOneriByNrDocRata(String nrDocumento, Short nrRata);

    /**
     * Metodo per determinare l'istanza onere identificata come BOLLO
     * 
     * @param codiceCausaleBollo
     * @param istanza
     * @return
     */
    public List<Istanzeoneri> getOnereBollo(Integer codiceCausaleBollo, Istanze istanza);

    /**
     * Metodo per determinare l'istanza onere che ha un bollo non pagato.
     * 
     * @param codiceCausale
     * @param istanza
     * @return
     */
    public Istanzeoneri getOnereBolloByOnere(Integer codiceCausale, Istanze istanza);

    /**
     * Trova il numero progressivo degli oneri di una istanza ed una determinata causale oneri
     * 
     * @param istanza
     * @param tipicausalioneri
     * @return
     */
    public Integer findNumeroRata(Istanze istanza, Tipicausalioneri tipicausalioneri);

    /**
     * Se il tipo movimento ha degli oneri configurati ne ricavo il tipocausaleonere. L'inserimento avviene solamente se
     * l'onere non è stato inserito precedentemente per quel tipocausaleonere. Come data Viene messa la data del
     * movimento. Il numero documento viene calcolato dalla funzione calcolanrdocumento
     * 
     * @param movimento
     */
    public void inserisciOnereDaMovimento(Movimenti movimento);

    /**
     * Cerca la causale onere da tipimovimentooneri where codice comportamento = 2 e tipo movimento
     * 
     * Per ogni causale onere cerca tutti gli oneri inseriti che abbiano quella causale e aggiorna la data_pagamento
     * alla data del movimento
     * 
     * @param movimento
     */
    public void richiedePagamentoOnereDaMovimento(Movimenti movimento);

    /**
     * La funzione verifica se deve essere impostata la scadenza degli oneri collegati al tipomovimento e ai suoi
     * contromovimenti:
     * <ul>
     * <li>in base ai record della tabella TIPIMOVIMENTOONERI con campo COMPORTAMENTO=1 (vedi tabella
     * ONERICOMPORTAMENTO)</li>
     * <li>in base agli oneri non pagati che hanno impostato il campo ISTANZEONERI.TIPOMOVIMENTO uguale a
     * MOVIMENTI.TIPOMOVIMENTO del CODICEMOVIMENTO passato</li>
     * </ul>
     * 
     * @param movimento
     */
    public void settaScadenzeOneri(Movimenti movimento);

    /**
     * Cerca la causale onere da tipimovimentooneri where codice comportamento = 4 e tipo movimento. Per ogni causale
     * cerca gli oneri che hanno quella causale, prezzo > 0, prezzoistruttoria nullo, flentratauscita=1, datapagamento
     * nulla. Se sono stati trovati questi record li aggiorna a prezzoistruttoria = prezzo
     * 
     * @param movimento
     */
    public void spostaImportoOneriDaMovimento(Movimenti movimento);

    /**
     * Torna la lista degli oneri di un endoprocedimento attivato di un'istanza
     * 
     * @param inventarioprocedimento
     *            istanza per la quale si cercano gli allegati
     * @param codiceInventario
     *            endoprocedimento per il quale si cercano gli allegati
     * @return
     */
    public List<Istanzeoneri> findByIstanzaAndEndo(Istanze istanza, Inventarioprocedimenti inventarioprocedimento);

    /**
     * Inserisce una lista di oneri ricavati dalla lista di istanzelavoriT dell'istanza. L'inserimento avviene solamente
     * se l'onere non è stato inserito precedentemente (verificando idcomune, istanza, tipicausalioneri).
     * 
     * @param istanza
     */
    public void inserisciOneriDaIstanzelavoriTs(Istanze istanza, Responsabili responsabile);

    /**
     * <pre>
     * Ritorna il numero di record presenti nella tabella filtrata per l'istanza (parametro codiceIstanza)
     * 
     * &#64;param codiceIstanza
     * &#64;return
     * </pre>
     */
    public int countByIstanza(Integer codiceIstanza);

    /**
     * verifica se sono presenti istanze per quel tipo di causaleonere
     * 
     * @return
     */
    public int countByTipicausalioneri(int codicetipocausalioneri);

    /**
     * 
     * <pre>
     * Ritorna la lista degli oneri filtrati per:
     * 	
     * 	1- l'istanza passata
     * 
     * Raggruppati per :
     * 
     *  se isDateNullAsGruop : false
     *     a- Raggruppamentocausalioneri
     *     b- data se il parametro passato è diverso da null
     * 
     *  se isDateNullAsGruop : true
     *     a- Raggruppamentocausalioneri
     *     b- Data (data nulla è condiderato come un gruppo)
     *  
     * &#64;param istanza
     * &#64;return
     * </pre>
     */
    public List<Istanzeoneri> findByIstanzaAndRaggruppamentiAndData(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri,
	    boolean isDateNullAsGruop, Date date);

    /**
     * <pre>
     * Ritorna una lista di oggetti IstanzeOneriHelper. 
     * Ogni oggetto contiene: 
     * 
     *   1- una lista di istanzeoneri con lo stesso (tipicausalioneri.raggruppamentocausalioneri) 
     *   2- la somma degli oneri di istruttoria con lo stesso raggruppamentocausalioneri 
     *   3- la somma delle causali oneri con lo stesso raggruppamentocausalioneri
     *   4- l'ultimo record in più conterra le somme totali delle causali oneri e degli oneri di istruttoria. 
     * &#64;param istanza
     * &#64;return
     * </pre>
     */
    public Map<ChiavePerCausaleDatPagamentoDataScadenzaTipologia, List<Istanzeoneri>> findDettaglioOneri(Istanze istanza);

    public Map<ChiavePerCausaleDatPagamentoDataScadenzaTipologia, List<Istanzeoneri>> findRaggruppamentoOneri(Istanze istanza);

    /**
     * @see IstanzeoneriDAO#sumOneriCausaliByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri
     *      raggruppamentocausalioneri,TipologiaOnere tipologiaOnere)
     */
    public BigDecimal sumOneriCausaliByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri,
	    TipologiaOnere tipologiaOnere, Boolean isEntrata);

    /**
     * @see IstanzeoneriDAO#sumRibassiOneriByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri
     *      raggruppamentocausalioneri, TipologiaOnere tipologiaOnere)
     */
    public BigDecimal sumRibassiOneriByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri);

    /**
     * @see IstanzeoneriDAO#sumOneriCausaliByIstanza(Istanze istanza, TipologiaOnere tipologiaOnere)
     */
    public BigDecimal sumOneriCausaliByIstanza(Istanze istanza, TipologiaOnere tipologiaOnere, Boolean isEntrata);

    /**
     * @see IstanzeoneriDAO#sumRibassiOneriByIstanzaAndRaggruppamento(Istanze istanza)
     */
    public BigDecimal sumRibassiOneriByIstanza(Istanze istanza);

    /**
     * @see IstanzeoneriDAO#ssumOneriCausaliByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri
     *      raggruppamentocausalioneri, Date datapagamento, TipologiaOnere tipologiaOnere)
     */
    public BigDecimal sumOneriCausaliByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri,
	    Date datapagamento, TipologiaOnere tipologiaOnere);

    /**
     * Il metodo controlla se sono presenti istanze oneri associate all'istanza con l'endo procedimento configurato
     * 
     * @param istanze
     * @return
     */
    public boolean isExistIstanzeOnereWithEndo(Istanze istanze);

    /**
     * Il metodo controlla se sono presenti istanze oneri associate all'istanza con il flag flentratauscita uguale a
     * false (oneri in uscita)
     * 
     * @param istanze
     * @return
     */
    public boolean isExistIstanzeOnereWithUscita(Istanze istanza);

    /**
     * Ritorna una lista di oggetti OneriEntrateUsciteAmministrazioneHelper che contengono le entrate e le uscite per un
     * amministrazione filtrate per istanza.
     * 
     * @param istanze
     * @return
     */
    public List<OneriEntrateUsciteAmministrazioneHelper> findOneriEntrateUsciteForAmministrazione(Istanze istanze);

    /**
     * @see IstanzeoneriDAO#findAmministrazioniInIstanzeOneri(Set<Istanzeoneri> istanzeoneris)
     */
    public List<Amministrazioni> findAmministrazioniInIstanzeOneri(Istanze istanza);

    /**
     * @see IstanzeoneriDAO# sumOneriCausaliByIstanzaAndAmministrazione(Istanze istanza, Amministrazioni
     *      amministrazioni, TipologiaOnere tipologiaOnere, Boolean isEntrata)
     */
    public BigDecimal sumOneriCausaliByIstanzaAndAmministrazione(Istanze istanza, Amministrazioni amministrazioni, TipologiaOnere tipologiaOnere,
	    Boolean isEntrata);

    /**
     * 
     * @param istanzeoneri
     * @param valore
     * @param campo
     */
    public String update(Istanzeoneri istanzeoneri, String valore, String campo);

    /**
     * Aggiorna i campi che riguardano il riferimenti del pagamento di un onere associa a un istanza
     * 
     * @param istanzeoneri
     */
    public void updateRiferimentiPagamento(Istanzeoneri istanzeoneri);

    /**
     * @see IstanzeoneriDAO#sumRibassiOneriByIstanzaAndAmministazioni((Istanze istanza, Amministrazioni amministrazioni)
     */
    public BigDecimal sumRibassiOneriByIstanzaAndAmministazioni(Istanze istanza, Amministrazioni amministrazioni);

    /**
     * 
     * <pre>
     * Controlla se per l'operatore e istanza passata gli oneri sono bloccati, secondo la logica:
     * 
     * 1- Controlla se l'operatore passato ha sempre accesso agli oneri
     *      - true	:	Mostra funzionalità
     *      - false	: 
     * 	1.2 - se non ha questa opzione allora controlla se gli oneri per l'istanza in esame sono bloccati o no
     *            - true	: 	Non mostrare funzionalità
     *            - false	:	Mostra funzionalità
     * &#64;param istanza
     * &#64;param responsabile
     * &#64;return
     * 
     * </pre>
     */
    public TipoAccessoEnum checkAccessoIstanzaOneri(Istanze istanza, Responsabili responsabile);

    /**
     * Torna la lista degli oneri di una istanza ordinati per data asc, nvl(datascadenza,'01/01/0001') asc, id asc
     * 
     * @param istanza
     * @return
     */
    public List<Istanzeoneri> findByIstanza(Integer codiceIstanza);

    /**
     * Ritorna la lista degli oneri non pagati per l'istanza. Un onere non è pagato se l'importopagato è vuoto o 0
     * 
     * @param codiceIstanza
     * @return
     */
    public List<Istanzeoneri> findOneriNonPagatiByIstanza(Integer codiceIstanza);

    /**
     * ordinati per data asc, nvl(datascadenza,'01/01/0001') asc, id asc
     * 
     * @param codiceIstanza
     * @param codiceInventario
     * @param codiceCausale
     * @return
     */
    public List<Istanzeoneri> findByIstanzaAndEndoAndCausale(Integer codiceIstanza, Integer codiceInventario, Integer codiceCausale);

    public List<Istanzeoneri> findByIstanzaAndCausale(Integer codiceIstanza, Integer codiceCausale);

    /**
     * 
     * @param codiceIstanza
     * @param codiceCausale
     * @return
     */
    public List<Istanzeoneri> findRateizzatiByIstanzaAndCausale(Integer codiceIstanza, Integer codiceCausale);

    public List<Istanzeoneri> findByIstanzaCausaleRata(Integer codiceIstanza, Integer codiceCausale, Integer numeroRata);

    /**
     * Ritorna il numero di oneri per l'istanza che sono collegati aun endo procedimento
     * 
     * @param codiceIstanza
     */
    public int countOneriWithEndoByIstanza(Integer codiceIstanza);

    /**
     * <pre>
     * Ritorna il numero di oneri per l'istanza che sono:
     *  se isEntrata == false : in uscita
     *  se isEntrata == true  : in entrata  
     * 
     * &#64;param codiceIstanza
     * &#64;param isEntrata
     * </pre>
     */
    public int countOneriByIstanza(Integer codiceIstanza, boolean isEntrata);

    /**
     * @see IstanzeoneriDAO#sumOneriImportoVersatoByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri
     *      raggruppamentocausalioneri, Boolean isEntrata)
     */
    public BigDecimal sumOneriImportoVersatoByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri,
	    Boolean isEntrata);

    /**
     * @see IstanzeoneriDAO#sumOneriImportoVersatoByIstanza(Istanze istanza, Boolean isEntrata)
     */
    public BigDecimal sumOneriImportoVersatoByIstanza(Istanze istanza, Boolean isEntrata);

    /**
     * Il metodo copia gli oneri non pagati da una istanza ad un'altra. Se si tratta della stessa istanza non fa niente
     * 
     * @param codiceIstanzaSubentrata
     *            istanza sorgente
     * @param codiceIstanzaCheSubentra
     *            istanza di destinazione
     */
    public void copiaOneriNonPagatiDaIstanzaSorgenteADestinazione(Integer codiceIstanzaSubentrata, Integer codiceIstanzaCheSubentra);

    /**
     * La funzionalità registra l'avvenuto pagamento di uno o più oneri collegati ad una posizione debitoria. Se la
     * posizione debitoria non è riferita ad un record di istanzeoneri, non viene aggiornato nulla e non viene sollevata
     * nessuna eccezione
     * 
     * @param idPosizioneDebitoria
     */
    void registraPagamentoAvvenutoByIdPosizioneDebitoria(DatiPagamento datiPagamento, String cfEnteCreditore);

    /**
     * La funzione torna una lista di @IstanzeOneriNodoPagamentiHelper in base all'id ( quello assegnato dal nodo dei
     * pagamenti ) della posizione debitoria
     * 
     * @param idPosizioneDebitoria
     * @return
     */
    List<IstanzeOneriNodoPagamentiHelper> findByIdPosizioneDebitoria(Integer idPosizioneDebitoria, String cfEnteCreditore);

    Istanzeoneri findById(Integer idOnere);

    /**
     * Elimina un onere a partire dal suo id
     * 
     * @param idOnere
     */
    public void delete(int idOnere);

    /**
     * 
     * @param codiceIstanza
     * @param codiceRaggruppamento
     * @param codiceTipoRateizzazione
     * @param dataInizioInteressi
     * @param dataInizioRate
     * @throws IllegalArgumentException
     * @throws InvalidConfigurationException
     */
    public void rateizzaIstanzeOneriRagguppamento(Integer codiceIstanza, Integer codiceRaggruppamento, Integer codiceTipoRateizzazione,
	    Date dataInizioInteressi, Date dataInizioRate) throws IllegalArgumentException, InvalidConfigurationException;

    /**
     * 
     * @param codiceIstanza
     * @param codiceIstanzeOneri
     * @param codice
     * @param dataInizioInteressi
     * @param dataInizioRate
     * @throws IllegalArgumentException
     * @throws InvalidConfigurationException
     */
    public void rateizzaIstanzeOneri(Integer codiceIstanza, Integer codiceIstanzeOneri, Integer codiceTipoRateizzazione, Date dataInizioInteressi,
	    Date dataInizioRate) throws IllegalArgumentException, InvalidConfigurationException;

    /**
     * Metodo per verificare se un'istanzaonere è rateizzata
     * 
     * @param codiceIstanzaOnere
     * @return
     */
    public boolean isRateizzato(Integer codiceIstanzaOnere);

    /**
     * 
     * @param codiceIstanza
     * @param codiceRaggruppamento
     * @throws IllegalArgumentException
     * @throws InvalidConfigurationException
     */
    public void derateizzaIstanzeOneriRagguppamento(Integer codiceIstanza, Integer codiceRaggruppamento)
	    throws IllegalArgumentException, InvalidConfigurationException;

    /**
     * 
     * @param codiceIstanza
     * @param codiceCausaleOneri
     * @throws IllegalArgumentException
     * @throws InvalidConfigurationException
     */
    public void derateizzaIstanzeOneri(Integer codiceIstanza, Integer codiceCausaleOneri)
	    throws IllegalArgumentException, InvalidConfigurationException;

    public IstoneriDettPosizioni inserisciPosizioneDebitoriaSuOnere(Integer idIstanzeoneri, Integer idDettPosizioneDebitoria);

    /**
     * Il metodo restituisce la lista che popola la tabella dello scadenzario oneri
     * 
     * @param dataOdierna
     * @return
     */
    public List<ScadenzarioOneri> findTabellaScadenzario(Date dataOdierna, String[] codiciComune);

    /**
     * Dato un set di string che rappresentano il valore di conti.mappaturanodopag cerco gli identificativi delle
     * causali oneri dell'istanza configurate con quelle mappature
     * 
     * @param codiceIstanza
     * @param listaMappaturePerVersamento
     * @return
     */
    public List<Integer> findCausaliPerMappatureConti(Integer codiceIstanza, Set<String> listaMappaturePerVersamento);

    /**
     * 
     * @param codiceIstanza
     * @param codiciCausali
     * @return
     */
    public List<Istanzeoneri> findOneriNonPagatiESenzaPosizioniDebitoriePerCausali(Integer codiceIstanza, List<Integer> codiciCausali);

    public List<Integer> findOneriConMappaturaNPById(Set<Integer> idIstanzeOneri);

    public Integer findCodiceIstanzaByDettPosDebitoria(Integer codice);

    public String calcolaInteressiDiMora(Integer idIstanzeOneri) throws Exception;
}
