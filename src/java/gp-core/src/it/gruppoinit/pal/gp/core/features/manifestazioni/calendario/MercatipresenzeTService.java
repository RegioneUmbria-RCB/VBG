// MercatipresenzeTService
package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.MercatipresenzeTDAO;
import it.gruppoinit.pal.gp.core.dao.helper.AutorizzazioneSpuntistaHelper;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiAnnoGiornoDTO;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiPresenzeDTO;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiConcessioniHelper;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.CalendariomercatoParametri;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Manifestazioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Riepilogomercato;
import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessioni;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.core.service.exception.MercatiAppException;
import it.gruppoinit.pal.gp.core.service.helper.GraduatorieMercatiBeanHelper;

public interface MercatipresenzeTService extends BaseService<MercatipresenzeT, PkId> {

    public static enum ManifestazioniStatoPosteggio {
	ConcessionarioPresente,
	PostoAssegnatoASpuntista,
	PosteggioLibero
    }

    public static enum ManifestazioniStatoSpuntista {
	Presente,
	NonPresente,
	RinunciaAPosteggio
    }

    /**
     * recupera la lista dei giorni del mercato dell'anno specificato
     * 
     * @param mercati
     * @param mercatiUso
     * @param anno
     * @return
     */
    public List<MercatipresenzeT> findMercatipresenzeTByMercatiAndMercatiUso(Mercati mercati, MercatiUso mercatiUso, Integer anno);
    /**
     * recupera la lista dei giorni del mercato con data uguale o successiva a quella specificata
     * 
     * @param mercati
     * @param mercatiUso
     * @param dallaData
     * @return
     */
    // public List<CodiceDescrizioneBean> findMercatipresenzeTByMercatiAndMercatiUsoAndDaData(Integer codiceMercato, Integer codiceMercatiUso,
    //     Date dallaData);

    /**
     * Metodo che verifica se una data corrisponde ad un giorno del calendario mercato.
     * 
     * @param list
     * @param date
     * @return ritorna un boolean: false: giorno non presente true: giorno presente
     * 
     */
    public boolean findDay(List<MercatipresenzeT> list, Calendar date);

    /**
     * metodo per il recupero di un giorno del calendario mercato
     * 
     * @param date
     * @param mercati
     * @param mercatiUso
     * @return
     */
    public MercatipresenzeT findByDataregistrazioneAndMercatoAndMercatoUso(Calendar date, Mercati mercati, MercatiUso mercatiUso);

    /**
     * 
     * @param mercati
     * @return ritorna una lista di mercatipresenze_t (una tupla per ogni mercato,mercatouso,anno)
     */
    public List<MercatipresenzeT> findByMercatoAndGroupByAnnoAndMercatoUso(Mercati mercati);

    /**
     * @see MercatipresenzeTDAO#findByMercatoAndAnnoGroupByMercatoUso(Mercati, Integer)
     */
    public List<MercatipresenzeT> findByMercatoAndAnnoGroupByMercatoUso(Mercati mercati, Integer anno);

    /**
     * metodo per inserire la presenza di un concessionario per quel giorno di mercato sul posteggio specificato
     * 
     * @param giorno
     *            l'oggetto MercatipresenzeT che identifica il giorno del mercato
     * @param idPosteggio
     *            (campo IDPOSTEGGIO della tabella MERCATI_D)
     * @param autorizzazioni
     *            l'autorizzazione scelta
     * @param catMerc
     *            TODO
     */
    public boolean segnaPresenzaConcessionario(MercatipresenzeT giorno, Integer idPosteggio, Autorizzazioni autorizzazioni, String catMerc,
	    PosteggiConcessioniHelper vwPosteggiconcessioni);

    public boolean segnaAssenzaGiustificataConcessionario(MercatipresenzeT giorno, Integer idPosteggio, Autorizzazioni autorizzazioni, String catMerc,
	    boolean flagAssenza, String motivazione, PosteggiConcessioniHelper vwPosteggiconcessioni);

    /**
     * metodo per inserire tutti i posteggi con i relativi concessionari per quel giorno di mercato <br/>
     * <b>L'INSERIMENTO FUNZIONA SOLO SE PER QUEL GIORNO NON CI SONO RECORD INSERITI</b>
     * 
     * @param giorno
     *            l'oggetto MercatipresenzeT che identifica il giorno del mercato
     */
    public void inserisciTuttiConcessionari(MercatipresenzeT giorno);

    /**
     * metodo per segnare presenti tutti i concessionari di tutti i posteggi questo significa aggiornare tutti i record
     * della tabella MERCATIPRESENZE_D copiando nel campo CODICECOANAGRAFE il codice presente in CODICECONCESSIONARIO.
     * <b>LA COPIA FUNZIONA SOLO SE NON C'E' UN OCCUPANTE</b>
     * 
     * @param giorno
     *            l'oggetto MercatipresenzeT che identifica il giorno del mercato
     */
    public void segnaPresentiTuttiConcessionari(MercatipresenzeT giorno);

    /**
     * metodo per eliminare la presenza dell'occupante (se è un concessionario o spuntista con posteggio allora aggiorno
     * il record, se è uno spuntista senza posteggio elimino il record)
     * 
     * @param idPresenza
     */
    public void eliminaPresenzaOccupante(Integer idPresenza) throws MercatiAppException;

    /**
     * metodo per inserire la presenza dello spuntista per quel giorno di mercato ma senza posteggio
     * 
     * @param giorno
     * @param spuntista
     * @param idAut
     *            pk autorizzazione
     * @param catMerc
     */
    public MercatipresenzeD segnaPresenzaSpuntistaNoPosteggio(MercatipresenzeT giorno, Integer idAut, String catMerc) throws MercatiAppException;

    /**
     * metodo per inserire tutti i giorni del calendario e per ogni giorno tutti i concessionari dei posteggi Se
     * presente inserisce anche i dati dell'utente loggato
     * 
     * @param calendariomercatoParametri
     * @param mercato
     * @param utenteLoggato
     */
    public void inserisciCalendario(CalendariomercatoParametri calendariomercatoParametri, Mercati mercato, Responsabili utenteLoggato,
	    boolean bloccaPrimoGennaio);

    /**
     * metodo per eliminare un intero calendario
     * 
     * @param mercato
     * @param uso
     * @param anno
     */
    public void deleteCalendario(Mercati mercato, MercatiUso uso, Integer anno);

    /**
     * Metodo per verificare se un mercato può essere storicizzabile.
     * 
     * @param mercati
     * @param mercatiUso
     * @param anno
     * @return boolean = true se tutti i giorni per un mercato, mercato uso e anno sono stati gestiti(FLAG_PRESENZE=1)
     *         boolean = false se almeno un giorno del mercato non è stato gestito(FLAG_PRESENZE=0)
     */
    public boolean verificaGestionePresenze(Mercati mercati, MercatiUso mercatiUso, Integer anno);

    /**
     * Metodo per verificare se un mercato è già stato storicizzato
     * 
     * @param mercati
     * @param mercatiUso
     * @param anno
     * @return boolean = true se il mercato è già storicizzato(FLAG_PRESENZE_ARCHIVIO=1) boolean = false se il mercato
     *         non è stato storicizzato(FLAG_PRESENZE_ARCHIVIO=0) per tutti i giorni del mercato
     */
    public boolean verificaMercatoStoricizzato(Mercati mercati, MercatiUso mercatiUso, Integer anno);

    /**
     * Chiude il giorno di mercato (Pone il campoFLAG_PRESENZE=1) se c'è almeno un posto assegnato al mercato
     * 
     * @param mercatipresenzeT
     * 
     */
    public void closeMarketDay(MercatipresenzeT mercatipresenzeT);

    /**
     * Riapre il giorno di mercato (Pone il campoFLAG_PRESENZE=0)
     * 
     * @param mercatipresenzeT
     */
    public void apriGiornoMercato(MercatipresenzeT giorno);

    /**
     * Metodo per la ricerca dei mercati filtrati per anno e/o per mercati
     * 
     * @param mercati
     * @param anno
     * @return
     */
    public List<Riepilogomercato> findByFilterMercatoOrAnnoGroupByMercatoUso(Mercati mercati, Integer anno);

    /**
     * Metodo che trova tutti gli anni per cui ci sono state presenze almeno in un mercato configurato
     * 
     * @return una lista di MercatipresenzeT con il campo anno popolato
     */
    public List<MercatipresenzeT> findAnniMercatiPresenti();

    /**
     * Metodo per verificare se per un mercato e un mercato uso sono presenti dei concessionari.
     * 
     * @param listPosteggi
     * @return
     */
    public boolean validateConcessionari(List<VwPosteggiconcessioni> listPosteggi);

    /**
     * inserisce nel campo transient transientAutDaSchedaDyn di Mercatipresenzed l'autorizzazione ricavata da
     * {@link AutorizzazioniService#trovaOInserisciByIstanzaDyn2Dati(Istanze, boolean)}
     * 
     * @param giorno
     * @param listaPosteggi
     */
    // public void recuperaAutorizzazioniConcessionari(MercatipresenzeT giorno, List<MercatipresenzeD> listaPosteggi);
    // public void recuperaAutorizzazioneConcessionario(MercatipresenzeT giorno, MercatipresenzeD mercatipresenzeD,
    // MercatiConfigurazione mercatiConfigurazione, List<VwPosteggiconcessioni> listaPosteggiConcessioni);
    //    
    public void recuperaAutorizzazioneConcessionario(MercatipresenzeT giorno, MercatipresenzeD mercatipresenzeD,
	    MercatiConfigurazione mercatiConfigurazione, Integer tipoManifestazione, PosteggiConcessioniHelper vwPosteggiconcessioni);

    /**
     * TODO descrizione metodo
     * 
     * @param giorno
     * @param listaPosteggi
     */
    // public void recuperaAutorizzazioniSpuntisti(MercatipresenzeT giorno, List<MercatipresenzeD> listaPosteggi);
    // public void recuperaAutorizzazioniSpuntistiNoPosteggio(MercatipresenzeT giorno, List<MercatipresenzeD>
    // listaSpuntistiNoPosteggio);
    /**
     * recupera (se torna true il metodo
     * {@link MercatipresenzeTService#checkSeUsareCatMerc(Manifestazioni, MercatiConfigurazione)}) dai dyn2dati
     * dell'istanza ricavata dalla concessione attiva, la categoria merceologica specificata in configurazione
     * 
     * @param posteggio
     */
    public void recuperaCategoriaMerceologicaConcessionario(MercatipresenzeD posteggio, MercatiConfigurazione mercatiConfigurazione,
	    PosteggiConcessioniHelper vwPosteggiconcessioni);

    /**
     * verifica se è stato configurato nella configurazione dei mercati il dyn2campi che contiene la categoria
     * merceologica. Se non è stato configurato ritorna false. Se è stato configurato ritorna true se il
     * tipoManifestazione corrisponde al valore cat_merc_uso scelto dalla list box della configurazione dei mercati.
     * 
     * @param tipoManifestazione
     * @param mercatiConfigurazione
     * @return
     */
    public boolean checkSeUsareCatMerc(Manifestazioni tipoManifestazione);

    /**
     * recupera (se torna true il metodo
     * {@link MercatipresenzeTService#checkSeUsareCatMerc(Manifestazioni, MercatiConfigurazione)}) dai dyn2dati
     * dell'istanza la categoria merceologica specificata in configurazione
     * 
     * @param istanza
     */
    public String recuperaCategoriaMerceologicaDaIstanza(Istanze istanza, MercatiConfigurazione mercatiConfigurazione);

    /**
     * metodo per il recupero della somma delle sole presenze fatte in MERCATIPRESENZE_T<br />
     * vedi doc del DAO
     * 
     * @see MercatipresenzeTDAO#findSommaDellePresenzeDaiCalendari(Autorizzazioni, Mercati, MercatiUso, MercatiD,
     *      String, Short, MercatipresenzeT, boolean)
     * 
     * @param autorizzazione
     * @param mercato
     * @param uso
     * @param posteggio
     * @param catMerc
     * @param anno
     * @param giorno
     * @return
     */
    public MercatiPresenzeDTO findSommaDellePresenzeDaiCalendari(Autorizzazioni autorizzazione, Mercati mercato, MercatiUso uso, MercatiD posteggio,
	    String catMerc, Integer anno, MercatipresenzeT giorno);

    /**
     * vedi doc del DAO
     * 
     * @see MercatipresenzeTDAO#findUltimoGiornoFieraPerAnno(Mercati, MercatiUso, Short)
     * 
     * @param mercato
     * @param uso
     * @param anno
     * @param groupByDataRegistrazione
     *            se true allora raggruppa anche per data registrazione ltre che per anno
     * @return
     */
    public List<MercatiAnnoGiornoDTO> findUltimoGiornoFieraPerAnno(Mercati mercato, MercatiUso uso, Integer anno, boolean groupByDataRegistrazione);

    /**
     * recupera dalla configurazione il dyn2campi che contiente la lista delle categorie merceologiche e da questo la
     * lista in forma di array di stringhe.
     * 
     * @return
     */
    public String[] findComboCategorieMerceologiche();

    /**
     * aggiorna tutti i giorni MERCATIPRESENZE_T della manifestazione per l'anno scelto con FLAG_PRESENZE_ARCHIVIO=1
     * 
     * @param mercato
     * @param uso
     * @param anno
     */
    public void chiudiAnnoMercato(Mercati mercato, MercatiUso uso, Integer anno);

    public List<Integer> findAnniDaConsolidare(Integer codiceMercato);

    /**
     * cerca i record di mercatipresenzet di un mercato / uso
     * 
     * @param mercati
     * @param mercatiUso
     * @param date
     * @param dataFine
     * @return
     */
    public List<MercatipresenzeT> findByMercatoAndMercatoUsoAndDateInterval(Integer codiceMercato, Integer codiceUso, Calendar date,
	    Calendar dataFine);

    public List<MercatipresenzeT> findByMercatoAndMercatoUsoAndDate(Integer codiceMercato, Integer codiceUso, Date date);

    public List<AutorizzazioneSpuntistaHelper> graduatoriaSpuntistiManifestazione(Integer codiceMercato, Integer codiceUso,
	    Integer idGiornataRiferimento);

    public List<MercatipresenzeT> findByMercatoAndAnnoAndResponsabileGroupByMercatoUso(Mercati mercati, Integer anno, Integer codiceResponsabile);

    boolean segnaPresenzaConcessionarioSuPresenza(MercatipresenzeD presenza, Autorizzazioni autorizzazioni, String catMerc,
	    MercatiConfigurazione mercatiConfigurazione, PosteggiConcessioniHelper vwPosteggiconcessioni);

    String getDescrizioneMercato(Integer codiceMercato, Integer codiceUso);

    String getDescrizioneMercato(Mercati m, MercatiUso mercatiUso);

    String getDescrizioneMercato(String descrizioneMercato, String descrizioneUso);

    /**
     * 
     * @param codiceMercato
     * @param codiceUso
     * @param date
     *            data>=
     * @param dataFine
     *            <=
     * @return Ordinati per data descrizione mercato descrizione uso
     */
    public List<MercatipresenzeT> findByMercatoAndMercatoUsoAndDateInterval(Set<Integer> codiceMercato, Set<Integer> codiceUso, Date date,
	    Date dataFine);

    /**
     * @see #closeMarketDay(MercatipresenzeT) verifica se la giornata è chiusa
     * 
     * @param idGiornataMercato
     * @return
     */
    public boolean isGiornataMercatoChiusa(Integer idGiornataMercato);

    /**
     * @see #closeMarketDay(MercatipresenzeT) verifica se la giornata è chiusa
     * 
     * @param idGiornataMercato
     * @return
     */
    public boolean isGiornataMercatoChiusa(MercatipresenzeT giornataMercato);

    public GraduatorieMercatiBeanHelper graduatoriaMercati(String mercato, String giorno) throws MercatiAppException;

    /**
     * cerca l'ultima giornata chiusa per mercato / uso a partire dalla data odierna (<=)
     * 
     * <pre>
     * idcomune = 'L219'
     *     AND   fkcodicemercato = 8
     *     and 	 fkidmercatouso	 = 118
     *     AND   dataregistrazione <= sysdate()
     *     AND   flag_presenze = 1
     * ORDER BY DATAREGISTRAZIONE DESC
     * </pre>
     * 
     * @return
     */
    public MercatipresenzeT findUltimagiornataChiusaMercatoUso(Integer codiceMercato, Integer codiceMercatoUso);

    public List<MercatipresenzeT> findByData(Date d);

    public List<MercatipresenzeT> findGiornateOdierne();

    public void inizializzaGiornateMercatoOdierne(String idcomunealias, String software);

    public void updateNoteGiornata(Integer codice, String note);

    /**
     * Ritorna il conteggio delle giornate per Mercato, uso, anno
     * 
     * @param codiceMercato
     * @param codiceUso
     * @param anno
     * @return
     */
    public int countByMercatoUsoAnno(Integer codiceMercato, Integer codiceUso, Integer anno);

    /**
     * Aggiorna i valore del flag flagConteggiaPresAss e salva la riga su log
     * 
     * @param idGiornata
     * @param valoreSelezionato
     */
    public void aggiornaFlagConteggiaPresAss(Integer idGiornata, boolean valoreSelezionato);

    /**
     * * Aggiorna i valore del flag FlagPopolaconcessionari e salva la riga su log
     * 
     * @param idGiornata
     * @param valoreSelezionato
     */
    public void aggiornaFlagPopolaconcessionari(Integer idGiornata, boolean valoreSelezionato);

    /**
     * Aggiorna le informazioni della giornata di mercato
     * 
     * @param idGiornata
     * @param concUsoId
     * @param flagConteggiaPresAss
     * @param flagPopolaConcessionari
     */
    public void aggiornaGiornataMercato(Integer idGiornata, Integer concUsoId, Boolean flagConteggiaPresAss, Boolean flagPopolaConcessionari);

    /**
     * Inserisce una nuova giornata di mercato con i dati passati
     * 
     * @param codicemercato
     * @param codiceuso
     * @param anno
     * @param mese
     * @param giorno
     * @param concUsoId
     * @param flagConteggiaPresAss
     * @param flagPopolaConcessionari
     * @param currentlyAuthenticatedUserDetails
     */
    public void inserisceNuovaGiornataMercato(Integer codicemercato, Integer codiceuso, Integer anno, Integer mese, Integer giorno, Integer concUsoId,
	    Boolean flagConteggiaPresAss, Boolean flagPopolaConcessionari, Responsabili currentlyAuthenticatedUserDetails);

    /**
     * Il metodo verifica che per i mercati della giornata indicata in data siano state create le posizioni debitorie, e
     * siano presenti gli iuv. Torna un report di tipo testo con le problematicità riscontrate. Il metodo verifica anche
     * se non sono stati inseriti movimenti di borsellino (ovvero la presenza NON ha la posizione debitoria ma ha un movimento di borsellino).
     * 
     * @param giornataDaControllare
     * @return
     */
    public List<IdentificativoDescrizioneBean> checkPosizioniDebitorieCreatePerConcessionari(Date giornataDaControllare);

    /**
     * Il metodo recupera le mercati presenze_t per la lista dei mercati passati e dalla data alla data e invoca il
     * metodo inserisciPresenzeConcessionario
     * 
     * @param codiciMercato
     * @param dallaData
     * @param allaData
     */
    public void inizializzaGiornateMercati(List<Integer> codiciMercato, Date dallaData, Date allaData);

    /**
     * Il metodo verifica se possibile inserire i concessionari nella giornata. Se si allora torna un bean con codice
     * 200 altrimenti 500 e la descrizione dell'errore
     * 
     * @param giornata
     * @return
     */
    public IdentificativoDescrizioneBean checkInserimentoConcessionariPerGiornata(MercatipresenzeT giornata);

    /**
     * Il metodo segna la giornata identificata con idGiornata come nulla cioè la giornata non verrà considerata nel
     * calcolo della bollettazione
     * 
     * @param idGiornata
     * @param note
     * @return
     */
    boolean segnaGiornataNulla(Integer idGiornata, String note);

    /**
     * Il metodo segna la giornata identificata con idGiornata come nulla cioè la giornata verrà considerata nel calcolo
     * della bollettazione
     * 
     * @param idGiornata
     * @param note
     * @return
     */
    boolean segnaGiornataNonNulla(Integer idGiornata);
}
