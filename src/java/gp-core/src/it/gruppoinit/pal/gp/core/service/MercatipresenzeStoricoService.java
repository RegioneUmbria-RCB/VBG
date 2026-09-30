package it.gruppoinit.pal.gp.core.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.MercatipresenzeStoricoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiPresenzeDTO;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeStorico;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniPresenzeStoricoRestHelper;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.service.helper.MercatiPresenzeStoricoRestHelper;

public interface MercatipresenzeStoricoService extends BaseService<MercatipresenzeStorico, PkId> {

    /**
     * vedi doc del DAO
     * 
     * @see MercatipresenzeStoricoDAO#findAnniDaStorico()
     * @return
     */
    public List<Integer> findAnniDaStorico();

    /**
     * metodo per la creazione di una lista di anni ricavata dall'intersezione dei risultati ricavati da
     * MERCATIPRESENZE_T chiamando {@link MercatipresenzeTService#findAnniMercatiPresenti()} e MERCATIPRESENZE_STORICO
     * chiamando {@link MercatipresenzeStoricoService#findAnniDaStorico()}
     * 
     * @return
     */
    public Set<Integer> findAnni();

    /**
     * metodo per il recupero del totale delle presenze fatte su una manifestazione a partire dall'Istanza con la quale
     * il richiedente ha partecipato al bando. Questo metodo ricava il mercato,uso,categoria merceologica (se in
     * configurazione dei mercati è settata come obbligatioria),autorizzazione da(
     * {@link AutorizzazioniService#trovaOInserisciByIstanzaDyn2Dati(it.gruppoinit.pal.gp.core.domain.Istanze, boolean)}
     * ) e chiama
     * {@link MercatipresenzeStoricoService#findSommaDellePresenze(Autorizzazioni, Mercati, MercatiUso, MercatiD, String, Short)}
     * 
     * @param codiceIstanza
     * @param inserisciAutSeNonTrovata
     * 
     * @return dto contenente il totale delle presenze e quelle fatte come proprietario
     */
    public MercatiPresenzeDTO getSommaDellePresenze(Integer codiceIstanza, Autorizzazioni estremi, String catMerc, boolean inserisciAutSeNonTrovata,
	    Integer codiceMercato, Integer codiceUso, boolean recuperaCodiceUsoSeNull, Integer presenzeDaAggiungere);

    /**
     * metodo per il calcolo del totale delle presenze (dai calendari, cioè MERCATIPRESENZE_T più quelle importate nello
     * storico cioè MERCATIPRESENZE_STORICO).<br />
     * Per le Fiere è recuperato l'ultimo giorno per ogni anno (nel caso di fiere di più di un giorno) vedi
     * {@link MercatipresenzeTService#findUltimoGiornoFieraPerAnno(Mercati, MercatiUso, Short)} , dalla lista ritornata
     * è invocato il metodo
     * {@link MercatipresenzeTService#findSommaDellePresenzeDaiCalendari(Autorizzazioni, Mercati, MercatiUso, MercatiD, String, Short, it.gruppoinit.pal.gp.core.domain.MercatipresenzeT)}
     * per ogni anno o per l'anno richiesto, il totale calcolato è sommato a quello recuperato dallo storico tramite la
     * chiamata a
     * {@link MercatipresenzeStoricoService#findSommaDellePresenzeDaStorico(Autorizzazioni, Mercati, MercatiUso, MercatiD, String, Short)}
     * . Per i Mercati il calcolo delle presenze dai calendari è effettuato chiamando
     * {@link MercatipresenzeTService#findSommaDellePresenzeDaiCalendari(Autorizzazioni, Mercati, MercatiUso, MercatiD, String, Short, it.gruppoinit.pal.gp.core.domain.MercatipresenzeT)}
     * con l'ultimo parametro settato a null.
     * 
     * @param autorizzazione
     * @param mercato
     * @param uso
     * @param posteggio
     * @param catMerc
     * @param anno
     * @return
     */
    public MercatiPresenzeDTO findSommaDellePresenze(Autorizzazioni autorizzazione, Mercati mercato, MercatiUso uso, MercatiD posteggio,
	    String catMerc, Integer anno);

    /**
     * metodo per il calcolo del totale delle presenze del solo sorico. (MERCATIPRESENZE_STORICO)
     * 
     * @see MercatipresenzeStoricoDAO#findSommaDellePresenzeDaStorico(Autorizzazioni, Mercati, MercatiUso, MercatiD,
     *      String, Short)
     * 
     * @param autorizzazione
     * @param mercato
     * @param uso
     * @param posteggio
     * @param catMerc
     * @param anno
     * @return
     */
    public MercatiPresenzeDTO findSommaDellePresenzeDaStorico(Autorizzazioni autorizzazione, Mercati mercato, MercatiUso uso, MercatiD posteggio,
	    String catMerc, Integer anno);

    public List<MercatipresenzeStorico> findSommaDellePresenze(MercatipresenzeStorico mercatipresenzeStorico);

    public List<MercatipresenzeStorico> findByAutorizzazione(Autorizzazioni aut);

    /**
     * Torna la lista delle MercatipresenzeStorico di un'anagrafe
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<MercatipresenzeStorico> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    // public List<MercatiPresenzeStoricoRestHelper> findSommaDellePresenze(Integer codiceMercato, Integer codiceMercatoUso, String cfAnagrafe);
    public List<MercatiPresenzeStoricoRestHelper> findSommaDellePresenze(String cf);

    public List<AutorizzazioniPresenzeStoricoRestHelper> findSommaDellePresenzeSpuntisti(Anagrafe anagrafe);

    public void updateAzzeraPresenzeStoricheByAutorizzazioneAndMercato(Integer codiceAutorizzazione, Integer codiceMercato, Integer codiceuso);

    public Integer findConteggioUltimoAnnoDellePresenzeSpuntisti(Anagrafe r);

    public List<MercatipresenzeStorico> findByAutoMercUsoAndAnno(Autorizzazioni autorizzazione, Mercati mercato, MercatiUso uso, Integer anno);

    /**
     * * Ricerca per l'indice univoco IDCOMUNE, FKCODICEMERCATO, FKIDMERCATIUSO, CODICEANAGRAFE, FK_AUTORIZZAZIONI_ID,
     * DATA
     * 
     * @param codiceMercato
     * @param codiceUso
     * @param codiceAnagrafe
     * @param idAutorizzazione
     * @param dataStorico
     * @return
     */
    public List<MercatipresenzeStorico> findByIndiceUnivoco(Integer codiceMercato, Integer codiceUso, Integer codiceAnagrafe,
	    Integer idAutorizzazione, Date dataStorico);
}
