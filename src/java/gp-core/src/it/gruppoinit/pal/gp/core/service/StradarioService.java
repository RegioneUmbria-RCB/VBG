package it.gruppoinit.pal.gp.core.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.StradarioDAO;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.helper.AllineamentoStradarioHelper;
import it.gruppoinit.pal.gp.core.service.helper.StradarioDTO;

/**
 * Il service lavora in modalità <b>"Comuni associati"</b> <br/>
 * Tutti i metodi devono rilanciare un'eccezione se l'operatore non ha comuni associati (record nella tabella
 * responsabilicomuni).
 * 
 * @author Riccardo Bocci
 * @author gianpaolot
 */
public interface StradarioService extends BaseService<Stradario, PkId> {

    /**
     * Il metodo controlla, nel caso di installazioni in modalità <b>"Comuni associati"</b> che l'operatore abbia i
     * permessi sui comuni. Nel caso contrario rilancia un'eccezione.
     * 
     * @see StradarioDAO#findAll(Integer, Integer)
     */
    public List<Stradario> findAll(Integer firstResult, Integer maxResult);

    /**
     * Il metodo controlla, nel caso di installazioni in modalità <b>"Comuni associati"</b> che l'operatore abbia i
     * permessi sui comuni. Nel caso contrario rilancia un'eccezione.Di defalut cerca anche quelli disabilitati
     * 
     * @param descrizione
     * @param codiceComune
     * 
     * @see StradarioDAO#findByDescrizione(String, String, String[])
     */
    public List<Stradario> findByDescrizione(String descrizione, String codiceComune, Integer firstResult, Integer maxResult);

    /**
     * <pre>
     * ALGORITMO RICERCA STRADARIO PER FRONTEND:
     * Condizioni di ricerca:
     * -	Tabella STRADARIO
     * -	Stradario.idcomune=idcomune della domanda
     * -	Se installazione è comuni associati: stradario.codicecomune = codice comune della domanda or codicecomune=null
     * -	(Stradario.datavalidita == null || stradario.datavalidità>=oggi)
     * 
     * 	1.	Stringa ricerca <= input utente
     * 		a.	Se ricerca==null o Stringa vuota non fa niente
     * 	2.	Set<String> LISTA_TOPONIMI <= Memorizzare lista toponimi (i toponimi vanno memorizzati e non richiesti alla base dati ogni volta con la query “select distinct prefisso from stradario”)
     * 	3.	Array tokenRicerca = ricerca.split(" ")
     * 		a.	Se tokenRicerca.size == 0 allora non fa niente (dovrebbe essere già risolto da 1.a)
     * 		b.	Se tokenRicerca(0) è una voce di LISTA_TOPONIMI elimina (ANDERSEN) l’elemento tokenRicerca(0) dalla lista dei parametri da ricercare
     * 		c.	Per  ogni elemento in tokenRicerca
     * 			i.	Effettua la query aggiungendo alle condizioni di ricerca come condizioni AND lower(descrizione) like lower(tokenRicerca(i)) per ogni elemento iEsimo
     * 			ii.	Se la query torna almeno un risultato esce con il risultato
     * 			iii.	Altrimenti effettua la query aggiungendo alle condizioni di ricerca OR lower(descrizione) like lower(tokenRicerca(i)) per ogni elemento iEsimo
     * La funzione esce con i risultati di questa ultima query
     * </pre>
     * 
     * @param descrizione
     * @param codiceComune
     *            può essere nullo
     * 
     * @see StradarioDAO#findByDescrizione(String, String, String[])
     */
    public List<Stradario> findByMatchParziale(String descrizione, String codiceComune, Integer firstResult, Integer maxResult);

    /**
     * <pre>
     * Il metodo controlla, nel caso di installazioni in modalità <b>"Comuni associati"</b> che l'operatore abbia i
     * permessi sui comuni. Nel caso contrario rilancia un'eccezione. Se il parametro:
     * 
     * 		searchDisabilitati == true cerca anche quelli disabilitati , cioè datavalidita !=null 
     * 		searchDisabilitati == false cerca solo quelli attivi , cioè datavalidita ==null
     * 
     * &#64;param descrizione
     * &#64;param codiceComune
     * &#64;param searchDisabilitati
     * 
     * &#64;see StradarioDAO#findByDescrizione(String, String, String[])
     * 
     * </pre>
     */
    public List<Stradario> findByDescrizione(String descrizione, String codiceComune, Integer firstResult, Integer maxResult,
	    boolean searchDisabilitati);

    /**
     * @see StradarioDAO#findByMercatoAndDescrizione(Mercati mercato, String descrizione)
     */
    public List<Stradario> findByMercatoAndDescrizione(Integer codicemercato, String descrizione);

    /**
     * @see StradarioDAO#findByMercatoAndDescrizione(Mercati mercato, String descrizione,boolean searchDisabilitati)
     */
    public List<Stradario> findByMercatoAndDescrizione(Integer codicemercato, String descrizione, boolean searchDisabilitati);

    @DeletableCacheElements
    public void resetObjectCached();

    /**
     * <pre>
     * Il medodo recupera tutti le strade per ogni codice comune passato e aggiorna la tabella stradario 
     * 		1. Recupera l'elenaco delle strada dal ws getListVie di ws sit
     *          2. Per ogni via controlla se già è nella tabella stradario
     *             Si : aggiorna
     *             No : inserisce
     * 		3. ripete l'operazione per ogno codice comune passato
     * &#64;param codiciComuni
     * &#64;return: ritora Mappa che contiene per ogni comune una struttura che riporta le informazioni dell'aggiornamento dello sradario effettuato
     * </pre>
     */
    public Map<String, Map<String, AllineamentoStradarioHelper>> updateAllineaStradario(Set<String> codiciComuni, Responsabili responsabile);

    /**
     * Ritorna l'oggetto stradario filtrando per codice viario e comune, se non trovato ritorna NULL
     * 
     * @param codiceViario
     * @return
     */
    public Stradario findByCodiceViario(String codiceViario);

    public Stradario findByCodiceViario(String codiceViario, String codiceComune, boolean isCodicecomuneObbligatorio);

    public List<Stradario> findListByCodiceViario(String codiceViario, String codiceComune, boolean isCodicecomuneObbligatorio);

    public StradarioDTO stradarioToDTO(Stradario stradario);

    public List<StradarioDTO> findByMatchParzialeToDTO(String filtroDescrizione, String codiceComune, Integer firstResult, Integer maxResults);

    public void disabilitaStradari(String codiceComune, Integer codiceStradarioIniziale);
}
