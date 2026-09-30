package it.gruppoinit.pal.gp.core.features.autorizzazioni;

import java.util.Date;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.helper.AutorizzazioneSpuntistaHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniDTO;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniExportHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.EstremiAutDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzaAutConcHelper;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniCommand;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniFilter;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniSubentriCommand;
import it.gruppoinit.pal.gp.core.domain.web.ValidaEliminazioneAutConcCommand;
import it.gruppoinit.pal.gp.core.domain.web.ValidazioneDataCessazioneSubentroCommand;
import it.gruppoinit.pal.gp.core.domain.web.ValidazioneOccupanteCommand;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.RuoloAutorizzazioneEnum;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.MercatoSrvRequest;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoCancellazioneAutOConc;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoModificaDataCessazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoModificaOccupante;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioneCancellazioneAutConcException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioniSubentriException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.model.AutorizzazioniMercatoSrvBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri.EsitoElaborazioneSubentri;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.AutorizzazioniComposteSpostaPresenzeDTO;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriService;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniConcessioniRestHelper;

/**
 * 
 * @author fabrizioc
 */
public interface AutorizzazioniService extends BaseService<Autorizzazioni, PkId> {

    public enum ENUM_COPIA_ONERI {
	COPIARE_ONERI_NON_PAGATI,
	NON_COPIARE_ONERI_NON_PAGATI
    }

    /**
     * insert senza gestione del numero autorizzazione da registro. utilizza la classica validateEntity e la
     * validateInsert che permette l'inserimento solo se non presente e se il registro è manuale
     */
    public void insert(Autorizzazioni autorizzazioni);

    @Override
    @Deprecated
    /**
     * Usare delete Autorizzazione
     */
    void delete(Autorizzazioni entity);

    /**
     * Si occupa della cancellazione di una concessione
     * 
     * @param entity
     *            il record nella tabella autorizzazioni che rappresenta una concessione
     * @param codiceIstanza
     */
    public void deleteAutorizzazione(ValidaEliminazioneAutConcCommand cmd) throws OperazioneCancellazioneAutConcException;

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniDAO#findByIstanzaRegistro(Istanze, Integer)
     */
    public List<Autorizzazioni> findByIstanzaRegistro(Istanze istanze, Integer codiceregistro);

    public List<Autorizzazioni> findByIstanzaRegistro(Integer codIstanze, Integer codiceregistro);

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniDAO#findByIstanzaMovimento(Istanze, Movimenti)
     */
    public List<Autorizzazioni> findByIstanzaMovimento(Istanze istanze, Movimenti movimento);

    /**
     * Ricerco l'autorizzazione prima nella tabella AUTORIZZAZIONI, se non la trovo la ricerco in
     * AUTORIZZAZIONI_SUBENTRI.<br />
     * Se la trovo in AUTORIZZAZIONI_SUBENTRI allora restituisco l'autorizzazione referenziata in FK_IDAUT_ATTUALE se
     * attiva. <br />
     */
    public Autorizzazioni findAutOConcAttivaByEstremi(String autoriznumero, Date autorizdata, String codicecomune, Integer codiceregistro);

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniDAO#findAutOConcByEstremi(String, Date, String, Integer)
     * @param autoriznumero
     * @param autorizdata
     * @param codicecomune
     * @param codiceregistro
     * @return
     */
    public Autorizzazioni findAutOConcByEstremi(String autoriznumero, Date autorizdata, String codicecomune, Integer codiceregistro);

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniDAO#findByAnagrafe(Integer)
     */
    public List<Autorizzazioni> findByAnagrafe(Integer codiceAnagrafe);

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniDAO#findConcessioniByAnagrafe(Integer)
     */
    public List<Autorizzazioni> findConcessioniByAnagrafe(Integer codiceAnagrafe);

    /**
     * FIXME modificare javadoc questo metodo ricerca tutte le aut e conc nella sola tabella AUTORIZZAZIONI a partire
     * dagli estremi della scheda dinamica dell'istanza. il metodo restituisce l'autorizzazione solo se la trova ed è
     * attiva. in tutti gli altri casi rilancia un'eccezione runtime. I casi sono: <br />
     * 1. estremi della scheda non completi <br />
     * 2. aut trovata ma cessata <br />
     * 3. estremi completi ma aut non trovata
     * 
     * @param istanza
     * @return
     */
    public Autorizzazioni findAutOConcPerMercatiWS(Autorizzazioni estremi);

    /**
     * questo metodo esegue prima una ricerca per estremi e se non trova l'aut la inserisce. il metodo rilancia una
     * eccezione runtime nei seguenti casi:<br />
     * 1. registro con numerazione automatica<br />
     * 2. aut trovata ma cessata<br />
     * 3. estremi incompleti<br />
     * 4. errore di inserimento<br />
     * 
     * @param istanza
     * @param estremi
     * @param attivitaIstatIdCatMerc
     * @return
     */
    public Autorizzazioni insertAutOConcPerMercatiWS(Istanze istanza, Autorizzazioni estremi, String attivitaIstatIdCatMerc);

    /**
     * metodo per il recupero dell'autorizzazione corretta per effettuare la presenza. il metodo priva verifica se gli
     * estremi dell'aut sono completi e poi chiama
     * {@link AutorizzazioniService#findAutOConcAttivaByEstremi(String, Date, String, Integer)}. <br />
     * Se l'autorizzazione è cessata o non esiste torna null.
     * 
     * @param istanza
     * @return
     */
    public Autorizzazioni findAutOConcPerPresenze(Istanze istanza);

    public Autorizzazioni findAutOConcPerPresenze(Istanze istanza, MercatiConfigurazione mercatiConfigurazione);

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniDAO#findByFilter(AutorizzazioniFilter filter, Integer firstResult, Integer maxResult)
     * @param filter
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Autorizzazioni> findByFilter(AutorizzazioniFilter filter, Integer firstResult, Integer maxResult);

    /**
     * Precompila i dati di una nuova concessione partendo dal codice dell'istanza per la quale la si intende
     * rilasciare. La precompilazione usa le configurazioni dei mercati del modulo software correntemente attivo per
     * prepopolare i registri, i progressivi e altre informazioni (Titolari).
     * 
     * @param codiceIstanza
     * @return
     */
    public AutorizzazioniConcessioni precompilaConcessione(Integer codiceIstanza);

    /**
     * Modifica i dati relativi al registro dell'autorizzazione passata. Serve per associare all'autorizzazione nuovi
     * valori di <b>autoriznumero</b> e <b>tipologiaregistro</b>, se numerazione legata a protocollo o progressivo.
     * 
     * @param autorizzazione
     *            l'autorizzazione della quale si intende modificare i dati di <b>autoriznumero</b> e
     *            <b>tipologiaregistro</b>
     * @param nuovoCodiceRegistro
     *            il nuovo registro da associare
     */
    public void updateRegistro(Autorizzazioni autorizzazione, Integer nuovoCodiceRegistro);

    /**
     * Modifica i dati relativi al registro della concessione passata. Serve per associare alla concessione nuovi valori
     * di <b>autoriznumero</b> e <b>tipologiaregistro</b>, se numerazione legata a protocollo o progressivo.
     * 
     * @param concessione
     *            concessione della quale si intende modificare i dati di <b>autoriznumero</b> e
     *            <b>tipologiaregistro</b>
     * @param nuovoCodiceRegistro
     *            il nuovo registro da associare
     */
    public void updateRegistroConcessione(Autorizzazioni autorizzazione, Integer nuovoCodiceRegistro);

    /**
     * Il metodo effettua una validazione di preverifica per inserire i subentri
     * 
     * @param command
     * @return
     * @throws OperazioniSubentriException
     * @throws PreCheckSubentriException
     */
    EsitoElaborazioneSubentri validaInserimentoSubentri(AutorizzazioniSubentriCommand command) throws OperazioniSubentriException;

    /**
     * metodo per eseguire il subentro a tutte le autorizzazioni e concessioni passate come argomento.
     * 
     * @param autorizzazioniSubentriCommand
     */
    public Set<Integer> insertSubentri(AutorizzazioniSubentriCommand autorizzazioniSubentriCommand) throws OperazioniSubentriException;

    /**
     * metodo per il recupero dei dettagli della concessione (tabella AUTORIZZAZIONI_CONCESSIONI) a partire
     * dall'autorizzazione
     * 
     * @param aut
     * @return
     */
    public AutorizzazioniConcessioni findConcessione(Autorizzazioni aut);

    /**
     * L'insert ha bisogno del Token e della webservice base url per invocare la protocollazione a partire dalla
     * tipologia del registro
     * 
     * @param entity
     * @param sessionDetails
     */
    public void insertConcessione(AutorizzazioniConcessioni entity);

    /**
     * l'update ha bisogno del Token e della webservice base url per invocare la protocollazione a partire dalla
     * tipologia del registro
     * 
     * @param entity
     * @param codiceIstanza
     * @param sessionDetails
     */
    public void updateConcessione(AutorizzazioniConcessioni entity, Integer codiceIstanza);

    /**
     * popola le liste autorizzazioni e autorizzazioniSubentri dell'oggetto IstanzaAutConcHelper tramite chiamata ai
     * metodi indicati sotto.
     * 
     * @see AutorizzazioniDAO#findByIstanza(Istanze)
     * @see AutorizzazioniSubentriService#findAutorizzazioniSubentriByIstanza(Istanze)
     * @param istanza
     * @return
     */
    public IstanzaAutConcHelper findByIstanza(Istanze istanza);

    /**
     * popola le liste concessioni e concessioniSubentri dell'oggetto IstanzaAutConcHelper tramite chiamata ai metodi
     * indicati sotto.
     * 
     * @see AutorizzazioniConcessioniService#findConcessioniByIstanza(Istanze)
     * @see AutorizzazioniSubentriService#findConcessioniSubentriByIstanza(Istanze)
     * 
     * @param istanza
     * @return
     */
    public IstanzaAutConcHelper findConcESubByIstanza(Integer codiceIstanza);

    /**
     * popola tutte le liste dell'oggetto IstanzaAutConcHelper tramite chiamata ai metodi indicati sotto.
     * 
     * @see AutorizzazioniService#findByIstanza(Istanze)
     * @see AutorizzazioniService#findConcESubByIstanza(Istanze)
     * 
     * @param istanza
     * @return
     */
    public IstanzaAutConcHelper findAutEConcESubByIstanza(Istanze istanza);

    /**
     * recupera dai dati dinamici gli estremi dell'autorizzazione e li inserisce in un oggetto Autorizzazioni. se i dati
     * non sono completi ritorna NULL.
     * 
     * @param istanza
     * @return
     */
    public EstremiAutDTO populateEstremiByIstanzaDyn2Dati(Integer codiceIstanza);

    public Autorizzazioni populateEstremiByIstanzaDyn2Dati(Istanze istanza, MercatiConfigurazione mercatiConfigurazione);

    /**
     * Si occupa della cancellazione di una concessione
     * 
     * @param entity
     *            il record nella tabella autorizzazioni che rappresenta una concessione
     * @param codiceIstanza
     */
    public void deleteConcessione(ValidaEliminazioneAutConcCommand cmd) throws OperazioneCancellazioneAutConcException;

    /**
     * L'insert ha bisogno del Token e della webservice base url per invocare la protocollazione a partire dalla
     * tipologia del registro
     * 
     * @param entity
     * @param sessionDetails
     */
    public void insertAutorizzazione(Autorizzazioni entity);

    /**
     * Permette di inserire un autorizzazione utilizzando i dati aggiuntivi presenti nel command. Es. se abbiamo un
     * autorizzazione di tipo dehors, dopo aver inserito l'autorizzazione dobbiamo fare delle operarazioni insert/modify
     * su tabelle collegate, le informazioni necessarie saranno presnti nel commnad
     * 
     * @param entity
     * @param standardModeInsert
     *            : se true il metodo si comporta come insertAutorizzazione(Autorizzazioni entity), inserisce solo
     *            l'autorizzazione
     */
    public void insertAutorizzazione(AutorizzazioniCommand entity, boolean standardModeInsert);

    /**
     * Permette fare l'update di un autorizzazione utilizzando i dati aggiuntivi presenti nel command. Es. se abbiamo un
     * autorizzazione di tipo dehors, dopo aver inserito l'autorizzazione dobbiamo fare delle operarazioni insert/modify
     * su tabelle collegate, le informazioni necessarie saranno presnti nel commnad
     * 
     * @param entity
     * @param standardModeInsert
     *            : se true il metodo si comporta come insertAutorizzazione(Autorizzazioni entity), inserisce solo
     *            l'autorizzazione
     */
    public void updateAutorizzazione(AutorizzazioniCommand entity, boolean standardModeInsert);

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniDAO#findByEstremi(String)
     * @param estremi
     * @param maxResult
     * @return
     */
    public List<Autorizzazioni> findByEstremi(String estremi, Integer maxResult);

    public Istanze findIstanzaInizialePerPresenzeManifestazione(Integer idAutorizzazione, Integer codiceIstanza);

    public void cessaConcessioniDellaManifestazione(Mercati mercato, MercatiUso uso, Concessionicausali causale, Date dataCessazione,
	    boolean escludiNuoveDaSubentro);

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniDAO#countByFilter(AutorizzazioniFilter filter)
     * @param filter
     * @return
     */
    public int countByFilter(AutorizzazioniFilter filter);

    /**
     * Torna la lista delle Autorizzazioni di un'anagrafe
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Autorizzazioni> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista di IstanzaAutConcHelper, uno per ogni istanza passata. L'oggetto IstanzaAutConcHelper viene
     * popolato tramite le chiamate ai metodi indicati sotto.
     * 
     * @see AutorizzazioniService#findByIstanza(Istanze)
     * @see AutorizzazioniService#findConcESubByIstanza(Istanze)
     * 
     * @param istanza
     * @return
     */
    public List<IstanzaAutConcHelper> findAutEConcESubByIstanzas(List<Istanze> listIstanze);

    public List<AutorizzazioniDTO> findConcESub(Integer codiceIstanza);

    public void clear();

    public List<Autorizzazioni> findByAutorizzazioniFilter(AutorizzazioniFilter filter, Integer firstResult, Integer maxResult);

    public int countByAutorizzazioniFilter(AutorizzazioniFilter filter);

    /**
     * 
     * 
     * @see AutorizzazioniDAO#findConcESub(Integer codiceIstanza, Boolean escludiCessate)
     */
    public List<AutorizzazioniDTO> findConcESub(Integer codiceIstanza, Boolean escludiCessate);

    /**
     * Il medoto effettua una pre-autorizzazione per l'inserimento di un Autorizzazione.
     * 
     * @param entity
     * @return
     */
    public boolean validateInsertAutorizzazione(Autorizzazioni entity);

    /**
     * Ritrorna le autorizzazione di tipo dehors con datascadenza minore uguale di quella passata
     * 
     * @param date
     * @return
     */
    public List<Autorizzazioni> findByAutorizzazioniDehorsBeforeData(Date date);

    /**
     * Ritrorna le autorizzazione di tipo dehors con datascadenza minore uguale di quella odierna
     * 
     * @param date
     * @return
     */
    public List<Autorizzazioni> findByAutorizzazioniDehorsBeforeData();

    /**
     * Controlla tutte le autorizzazione che hanno data <= a quella passata e le cessa
     * 
     * @param date
     */
    public void updateCessaAutorizzazioniDehorsScaduteDallaData(Date date);

    /**
     * Controlla tutte le autorizzazione che hanno data <= a quella odierna e le cessa
     */
    public void updateCessaAutorizzazioniDehorsScadute();

    /**
     * Ritorna il tipo di autorizzazione
     * 
     * @param autorizzazioni
     * @return
     */
    public String tipoAutorizzazione(Integer codiceRegistro);

    /**
     * Ritorna il numero di autorizzazioni collegata all'istanza
     */
    public int countByIstanza(Integer codiceIstanza);

    /**
     * Ritorna una lista di autorizzazioni per l'istanza passata
     * 
     * @param codice
     * @return
     */
    public List<Autorizzazioni> findByIstanza(Integer codice);

    /**
     * Verifica se esistono record per quel registro
     * 
     * @param codiceRegistro
     * @return
     */
    public int countByTipologiaRegistri(Integer codiceRegistro);

    /**
     * <pre>
     * Il metodo deve scambiare l'id dei posteggi nella concessione associata:
     * <b>CASO 1: Posteggio destinazione occupato</b>
     * 	1. Crea una riga in subentri per le informazioni della concessione del posteggio di partenza
     * 	2. Crea una riga in subentri per le informazioni della concessione del posteggio di destinazione
     * 	3. Scambia i riferimenti dei posteggi nelle due concessioni
     * 
     * <b>CASO 2: Posteggio destinazione libero</b>
     * 	1. Crea una riga in subentri per le informazioni della concessione del posteggio di partenza
     * 	3. Aggiorna il riferimento del posteggio nells concessione
     * 
     * &#64;param codiceConcPartenza
     * &#64;param codiceConcDestinazione
     * </pre>
     */
    public void updateScambiaPosteggio(Integer codiceConcPartenza, Integer codiceConcDestinazione, Integer codiceCausaleCessazione,
	    Integer codiceCausaleAcquisizione, Integer codicePosteggioDestinazione);

    public List<AutorizzazioniConcessioniRestHelper> findByCodiceFiscaleAnagrafe(String cf, Integer firstResult, Integer maxResults);

    public List<AutorizzazioniConcessioniRestHelper> newAutorizzazioniHelper(Autorizzazioni autorizzazione);

    /**
     * Ritorna i codici delle autorizzazioni/concessioni degli spuntisti per le quali fare la lista delle presenze
     * 
     * @param codiceMercato
     * @param codiceUso
     * @return
     */
    public List<AutorizzazioneSpuntistaHelper> findAutorizzazioniSpuntisti(Integer codiceMercato, Integer codiceUso, Integer idGiornataRiferimento);

    public List<AutorizzazioniRestHelper> findRestHelper(Set<Integer> auts, Integer idGiornata, Integer codiceMercato, Integer codiceUso);

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniDAO# findAttivitaInAutorizzazioni()
     */
    public List<CodiceDescrizioneBean> findAttivitaInAutorizzazioni();

    public void updateNoteAutorizzazione(Integer idAutorizzazione, String note);

    public List<AutorizzazioniRestHelper> findAnagraficheConAutorizzazione(String testo, Integer firstResult, Integer maxResults);

    public Autorizzazioni findByNumeroEDataEAzienda(String numeroAutorizzazione, Date dataAutorizzazione, Integer codiceAnagrafeAzienda);

    public CodiceDescrizioneBean aggiornaAutorizzazioneCollegataProroga(Integer codicePraticaOrigine, Integer codiceMovimentoPraticaOrigine);

    public CodiceDescrizioneBean aggiornaAutorizzazioneCollegataPreavviso(Integer codicePraticaOrigine, Integer codiceMovimentoPraticaOrigine);

    /**
     * Nel caso di modifica dei dati dell'autorizzazione verranno copiati nell'istanza che contiene l'autorizzazione, i
     * dati dinamici salvati nell'istanza di modifica. I dati dell'altra istanza saranno storicizzati e sostituiti con
     * quelli dell'istanza di modifica
     * 
     * @param codicePraticaOrigine
     * @param codiceMovimentoPraticaOrigine
     * @return
     */
    public CodiceDescrizioneBean aggiornaAutorizzazioneCollegataModifica(Integer codicePraticaOrigine, Integer codiceMovimentoPraticaOrigine);

    public CodiceDescrizioneBean aggiornaAutorizzazioneCollegataRinnovo(Integer codicePraticaOrigine, Integer codiceMovimentoPraticaOrigine);

    /**
     * verifica se attivo il parametro che permette di saltare il controllo di univocità su una autorizzazione con
     * numero/data/registro/comune. A Torino CSI l'univocità è data per numero/comune
     * 
     * @return
     */
    public boolean overrideUniqueConstraint();

    public Autorizzazioni findByNumeroAndComune(String autoriznumero, String codicecomune);

    /**
     * Verifica se l'istanza corrente ha almeno un'autorizzazione. Se non le ha verifca se ci sono autorizzazioni nelle
     * istanze ad essa collegata.
     * 
     * @param codiceIstanza
     * @return
     */
    public boolean existAutorizzazioniInIstanzeCollegate(Integer codiceIstanza);

    public CodiceDescrizioneBean rollbackAutorizzazioneCollegataProroga(Integer codicePraticaOrigine, Integer codiceMovimentoPraticaOrigine);

    public CodiceDescrizioneBean rollbackAutorizzazioneCollegataPreavviso(Integer codicePraticaOrigine, Integer codiceMovimentoPraticaOrigine);

    public CodiceDescrizioneBean rollbackAutorizzazioneCollegataModifica(Integer codicePraticaOrigine, Integer codiceMovimentoPraticaOrigine);

    public CodiceDescrizioneBean rollbackAutorizzazioneCollegataRinnovo(Integer codicePraticaOrigine, Integer codiceMovimentoPraticaOrigine);

    public List<AutorizzazioniRestHelper> findAutorizzazioniAnagrafiche(Set<Integer> codiciAnagrafe,
	    boolean consideraAncheIlProprietarioTraLeAnagrafiche, RuoloAutorizzazioneEnum ruolo, boolean soloAutorizzazioniAttive);

    public AutorizzazioniRestHelper findAutorizzazioneRestHelper(Integer idAutorizzazione);

    /**
     * Il metodo torna l'anagrafe principale dell'autorizzazione secondo questa regola. Se presente
     * autorizzazioni_csi.codiceanagrafe (gerente) allora torna questa altrimenti torna autorizzazioni.codiceanagrafe
     * 
     * @param idAutorizzazione
     * @return
     */
    public Anagrafe findAnagrafeAutorizzazione(Integer idAutorizzazione);

    public String exportModalitaPentaho(AutorizzazioniExportHelper autorizzazioniExportHelper, Esportazioni esportazioni, Date data, String email,
	    String contestoExport, boolean isInvioMail);

    public void cessaAutorizzazione(Integer idAutorizzazione, Date dataCessazione, int idCausaleCessazione);

    public void completaAutorizzazione(Integer codiceAutorizzazione, Boolean completa);

    public void aggiornaEstremiAutorizzazioni(Integer codiceAutorizzazione, String numero, Date data);

    public EsitoModificaOccupante validaModificaOccupante(Integer idAutConc, Integer codiceAnagrafeNuovoOccupante);

    public void updateModificaOccupante(ValidazioneOccupanteCommand command) throws EventAbortedException;

    public void updateModificaDataCessazioneSubentro(ValidazioneDataCessazioneSubentroCommand command) throws EventAbortedException;

    public EsitoModificaDataCessazione validaModificaDataCessazioneSubentro(Integer idAutorizzazioniSubentri, Date nuovaDataCessazione)
	    throws OperazioniSubentriException;

    public EsitoCancellazioneAutOConc validaCancellazioneAutConc(ValidaEliminazioneAutConcCommand cmd) throws OperazioneCancellazioneAutConcException;

    public List<AutorizzazioniMercatoSrvBean> findAutorizzazioniMercatoSrvBean(MercatoSrvRequest req);

    /**
     * Recupera l'elenco di autorizzazioni attive relative a una specifica istanza e comune, escludendo quelle
     * "collegate" (ovvero con {@code FK_IDAUT_COLLEGATA} in {@code autorizzazioni_concessioni}).
     * <p>
     * Per le autorizzazioni aventi un'autorizzazione collegata, la colonna {@code autorizNumeroFull} includerà il
     * numero dell'autorizzazione collegata ({@code AUTORIZNUMERO}) nel formato: "AutorizNumeroAttuale (Aut. Coll.
     * AutorizNumeroCollegata)". Per le altre autorizzazioni, i dati non verranno alterati.
     *
     * @param codice
     *            Il codice identificativo dell'istanza ({@code FKIDISTANZA}).
     * @param idComune
     *            L'ID del comune associato all'autorizzazione ({@code IDCOMUNE}).
     * @return Una lista di record di autorizzazioni filtrate
     */
    public List<AutorizzazioniComposteSpostaPresenzeDTO> findAutorizzazioniComposteSpostaPresenze(Integer codice, String idComune);
}
