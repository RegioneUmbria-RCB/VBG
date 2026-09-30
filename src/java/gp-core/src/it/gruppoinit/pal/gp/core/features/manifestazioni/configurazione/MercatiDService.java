package it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.MercatiDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConti;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDCritass;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioMercatiHelper;
import it.gruppoinit.pal.gp.core.domain.web.MercatiDFilter;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PosteggioMercatoBean;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface MercatiDService extends BaseService<MercatiD, PkId> {

    public List<MercatiD> findAllByMercato(Mercati mercati);

    /**
     * Metodo per determinare i posteggi abilitati o disabilitati di un mercato La lista ritorna ordinata per codice
     * posteggio
     * 
     * @param mercati
     * @param posteggiEnum
     * @return
     */
    public List<MercatiD> findByMercato(Mercati mercati, PosteggiEnum posteggiEnum);

    /**
     * @see MercatiDDAO#findByMercatoOrderByPeso(Mercati mercati, PosteggiEnum posteggiEnum)
     */
    public List<MercatiD> findByMercatoOrderByPeso(Mercati mercati, PosteggiEnum posteggiEnum);

    /**
     * ritorna una lista di tutti i posteggi per il mercato e anno scelto e con almeno un conto settato La lista ritorna
     * ordinata per codice posteggio
     * 
     * @param mercati
     * @return
     */
    public List<MercatiD> findByPosteggiConConti(Mercati mercati, Integer anno);

    /**
     * Ritorna una lista lista di posteggi di un mercato con i costi dell'anno scelto. I costi saranno quelli
     * particolari per quel posteggio uniti a quelli generali per quel mercato. Nel caso in cui siano presenti conti
     * unguali , ma con diverso importo vengono visualizzati quelli relativi al posteggio
     * 
     * @param mercati
     * @param anno
     * @param mercatiContiList
     * @return
     */
    public List<MercatiD> mostraCostoPosteggio(Mercati mercati, Integer anno, List<MercatiConti> mercatiContiList);

    public List<MercatiD> findPosteggioByMercatiMercatoUso(Mercati mercati);

    /**
     * Metodo per recuperare il posteggio a partire dal codiceposteggio invece dell'ID.
     * 
     * @param codiceposteggio
     * @param mercati
     * @return
     */
    public MercatiD findPosteggioByCodicePosteggio(String codiceposteggio, Mercati mercati);

    /**
     * @see MercatiDDAO#findByFilter(MercatiD filter)
     */
    public List<MercatiD> findByMercatiD(MercatiD filter);

    /**
     * Cancella una lista di posteggi
     * 
     * @param arraycodici
     */
    public List<MercatiD> deleteListaPosteggi(String[] arraycodici);

    /**
     * 
     * @return Ritorna la lista dei posteggi passata di un determinato mercato con i campi transient della lista
     *         vwconcessioniattivi popolati: Set<AutorizzazioniSubentri> transientSubentri; Autorizzazioni
     *         transientautConcessioneattiva;
     */
    public List<MercatiD> findMercatiDWithConcessioniAndSubentri(List<MercatiD> listaposteggio, Integer codicemercato);

    /**
     * @see MercatiDDAO#findMercatiDWithConcessioniAndSubentriSQL(Integer codicemercato)
     */
    public List<PosteggioMercatiHelper> findMercatiDWithConcessioniSQL(Integer codicemercato, MercatiD mercatiD, boolean isSingoloPosteggio,
	    Integer codiceMercatoUso);

    //public List<PosteggioMercatiHelper> findMercatiDWithConcessioniSQL(Integer codicemercato, MercatiD mercatiD);
    /**
     * Il metodo aggiorna tutti i posteggi di cui abbiamo passato il codice tramite l'array di stringhe con le
     * informazioni passate tramite l'oggetto MercatiD L'aggiornamento dei campi verrà fatto solo per quelli valorizzati
     * del oggetto MercatiD,quelli non valorizzati saranno tralasciati. Il metodo non andrà mai ad aggiornare il campi :
     * id,codiceposteggio,note.
     * 
     * @param mercatiD
     * @param codiciPosteggio
     */
    public void updateMultiPosteggi(MercatiD mercatiD, String[] codiciPosteggio);

    /**
     * Ritorna una lista di posteggi filtrati per mercato e per codiceposteggio( ilike )
     * 
     * @param textToSearch
     * @param mercati
     * @return
     */
    public List<MercatiD> findByCodicePosteggioAndMercato(String textToSearch, Mercati mercati);

    public List<MercatiD> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult);

    /**
     * Il metodo agiorna il codice posteggio al Posteggio passato e inoltre controlla che non esista un posteggio nel
     * mercato con lo stesso codice passato
     * 
     * @param mercatiD
     * @param codicePosteggi
     */
    public void updateCodicePosteggio(MercatiD mercatiD, String codicePosteggio);
    //    /**
    //     * Il metodo calcola il costo del posteggio in 12 scadenze annuali a partire da Gennaio
    //     * 
    //     * @param posteggio
    //     * @param anno
    //     * @param mercatoContestoConcessionari
    //     * @return
    //     */
    //    public PosteggioImportoHelper calcolaCostoPosteggioAnnuale(MercatiD posteggio, Integer anno, String mercatoContestoConcessionari);

    public List<CodiceDescrizioneBean> findPosteggiNonAssegnatiByMercato(Integer codiceMercato, Integer codiceMercatiUso, Integer posteggioEscluso,
	    PosteggiEnum active);

    /**
     * Verifica se l'istanza e i criteri di assegnazione del posteggio sono compatibili
     * 
     * @param mercatiDCritasses
     * @param codice
     * @return
     */
    public boolean checkCompatibilitaPosteggio(Set<MercatiDCritass> mercatiDCritasses, Integer codiceIstanza);

    public List<MercatiD> findMercatoAndPosizioneNotNullOrderByPos(Integer codiceMercato, OrderTypeEnum orderTypeEnum);

    /**
     * <pre>
     * 	Recupera tutti i posteggi che hanno valorizzato posizione e riodina i valori in modo sequenziale.
     * 	Es. 1,3,5,7 --> 1,2,3,4
     *
     * </pre>
     */
    public void updateCompattaPosizioni(Integer codicemercato);

    /**
     * Recupera la posizione massima associata ai posteggi del mercato passato
     * 
     * @param codice
     * @return
     */
    public Integer findPosizioneMaxByMercato(Integer codice);

    public List<PosteggioMercatiHelper> findMercatiDWithConcessioniAndAvvisi(Integer codicemercato, MercatiD mercatiD, boolean isSingoloPosteggio);

    public List<PosteggioMercatiHelper> findMercatiDWithConcessioniAndAvvisi(Integer codiceMercato, MercatiD mercatiD, boolean isSingoloPosteggio,
	    Integer idGiorno);

    public String exportModalitaPentaho(MercatiD mercatiD, Esportazioni transientEsportazioni, String email, boolean isInvioMail);

    public List<MercatiDDTO> findByMercatiDDTO(MercatiDFilter filter);

    public List<MercatiDDTO> findByMercatiDDTO(Integer codicemercato);

    public int countByMercato(Integer codicemercato, PosteggiEnum posteggiEnum);

    public MercatiDDTO findById(Integer codiceposteggio);

    public List<Integer> findByCodiceByMercato(Integer codicemercato, PosteggiEnum posteggiEnum);

    public List<PosteggioMercatoBean> findByIdGiornata(Integer idGiornata);
}
