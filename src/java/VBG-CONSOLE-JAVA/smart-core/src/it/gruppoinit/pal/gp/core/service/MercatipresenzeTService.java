package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MercatipresenzeTDAO;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiAnnoGiornoDTO;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.CalendariomercatoParametri;
import it.gruppoinit.pal.gp.core.domain.Manifestazioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Riepilogomercato;
import it.gruppoinit.pal.gp.core.security.LoggedUser;

import java.util.Calendar;
import java.util.List;

public interface MercatipresenzeTService extends BaseService<MercatipresenzeT, PkId> {

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
     * metodo per inserire la presenza dello spuntista per quel giorno di mercato su quel posteggio con una specifica
     * autorizzazione e categoria merceologica(opzionale)
     * 
     * @param giorno
     * @param idPosteggio
     * @param idAut
     *            pk autorizzazione
     * @param catMerc
     */
    public void segnaPresenzaSpuntista(MercatipresenzeT giorno, Integer idPosteggio, Anagrafe spuntista, Integer idAut, String catMerc);

    /**
     * metodo per inserire la presenza dello spuntista per quel giorno di mercato ma senza posteggio
     * 
     * @param giorno
     * @param spuntista
     * @param idAut
     *            pk autorizzazione
     * @param catMerc
     */
    public void segnaPresenzaSpuntistaNoPosteggio(MercatipresenzeT giorno, Anagrafe spuntista, Integer idAut, String catMerc);

    /**
     * metodo per inserire tutti i giorni del calendario e per ogni giorno tutti i concessionari dei posteggi
     * 
     * @param calendario
     */
    public void inserisciCalendario(CalendariomercatoParametri calendariomercatoParametri, Mercati mercato, LoggedUser loggedUser);

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
	    MercatiConfigurazione mercatiConfigurazione, Integer tipoManifestazione);

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
    public void recuperaCategoriaMerceologicaConcessionario(MercatipresenzeD posteggio, MercatiConfigurazione mercatiConfigurazione);

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
     * vedi doc del DAO
     * 
     * @see MercatipresenzeTDAO#findUltimoGiornoFieraPerAnno(Mercati, MercatiUso, Short)
     * 
     * @param mercato
     * @param uso
     * @param anno
     * @return
     */
    public List<MercatiAnnoGiornoDTO> findUltimoGiornoFieraPerAnno(Mercati mercato, MercatiUso uso, Integer anno);

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

    /**
     * recupera l'intervento delle istanze dei concessionari attvi
     * 
     * @param mercato
     * @param uso
     * @return
     */
    public Alberoproc findInterventoConcessionari(Mercati mercato, MercatiUso uso);

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
    public List<MercatipresenzeT> findByMercatoAndMercatoUsoAndDateInterval(Integer codiceMercato, Integer codiceUso, Calendar date, Calendar dataFine);
}
