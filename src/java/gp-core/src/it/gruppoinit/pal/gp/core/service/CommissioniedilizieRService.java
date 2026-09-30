package it.gruppoinit.pal.gp.core.service;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.CommissioniedilizieRDAO;
import it.gruppoinit.pal.gp.core.domain.CommedilizieVotazioni;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models.RigaCommissioneModel;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

/**
 * 
 * @author gianpaolot
 */
public interface CommissioniedilizieRService extends BaseService<CommissioniedilizieR, PkId> {

    /**
     * @see CommissioniedilizieRDAO#findAll(Integer, Integer)
     */
    public List<CommissioniedilizieR> findAll(Integer firstResult, Integer maxResult);

    public List<CommissioniedilizieR> findByFilterTable(FilterTable filterTable);

    /**
     * <pre>
     * Il metodo fa un inserimento multiplo di commissioni edilizie r
     * I campi inseriti all'inserimento di un record saranno:
     * 
     *   1- Commissione edilizia t
     *   2- Movimento
     * 
     * &#64;param codiciMovimenti
     * &#64;param entity
     * </pre>
     */
    public void insertMultiploCommissioniedilizieR(List<String> codiciMovimenti, CommissioniedilizieT commissioniedilizieT);

    /**
     * @see CommissioniedilizieRDAO#maxOrdineByCommissioniedilizieT(CommissioniedilizieT commissioniedilizieT)
     */
    public Integer maxOrdineByCommissioniedilizieT(CommissioniedilizieT commissioniedilizieT);

    /**
     * <pre>
     * Il metodo va a fare l'update della commissione edilizia R scelta.
     * Logica di aggiornamento:
     * 
     *  1- Ricerca il movimento di rientro 
     *  2- Aggiornare il movimento trovato nei campi:
     *     2.1 data : campo data commissione dei commissione edilizia t
     *     2.2 tipomovimento : il tipo movimento legato alla tipologia di parere scelta 
     *     2.3 parere : il parere passato dal form
     *  3- Fare l'update di della commissione edilizia r scelta inserendo nel campo movimento di rientro
     *     il movimento per cui si è fatto l'update nel passo precedente
     *  4- Inserire nella tabella commedilizie_votazioni i recodor dei voti dei presenti.    
     * 
     * &#64;param commissioniedilizieR
     * &#64;param commedilizieVotazionis
     * </pre>
     */
    public void updateEsitoCommissioneediliziaR(CommissioniedilizieR commissioniedilizieR, List<CommedilizieVotazioni> commedilizieVotazionis,
	    String parere);

    /**
     * <pre>
     * Il metodo va a fare la delete dell'esito di una commissione (aggiorna alcuni campi della commissione r scelta)
     * Logica di cancellazione:
     * 
     * 1- Mette a null i campi tipoparere,movimentoRientro del record commissioni edili r scelto 2- Sulla tabella
     * movimenti andrà a cancellare il movimento creato alla chiusura dell'esito (movimentoRientro) 3- Andrà ad
     * eliminare tutti i record nella tabella commissioni edilizie votazioni legati alla discussione della commissione
     * edilizia r in esame
     * 
     * @param commissioniedilizieR
     * @param commedilizieVotazionis
     */
    public void deleteEsitoCommissioneediliziaR(CommissioniedilizieR commissioniedilizieR);

    /**
     * <pre>
     * Il metodo deve riordinare secondo il campo ordine i record di commissioni edilizie R.
     * Il metodo deve far si che tutti i record abbiano il campo ordine sequenziale:
     * (Es prima: 1,2,4,6----> Dopo: 1,2,3,4)
     * &#64;param listaCommissioniRInDiscussione
     * </pre>
     */
    public void riordinaEsitoCommissioneediliziaR(Integer idCommissione, String numeroProtocollo,
	    List<RigaCommissioneModel> listaCommissioniRInDiscussione);

    /**
     * Rirorna una oggetto CommissioniedilizieR filtrato per il movimento di rientro passato
     * 
     * @param movimento
     * @return l'oggetto trovato o null se non trova niente
     */
    public CommissioniedilizieR findByMovimentorientro(Movimenti movimento);

    /**
     * Ordinati per ordine asc
     * 
     * @param codiceCommissioniEdilizieT
     * @return
     */
    public List<CommissioniedilizieR> findIstanzeByCommissioneEdiliziaT(Integer codiceCommissioniEdilizieT);

    public void ordinaEsitoCommissioneediliziaR(Integer id, String numeroProtocollo, List<RigaCommissioneModel> righe);

    public Set<Integer> findCodiciCommissioniByMovimento(Integer codiceMovimento);

    public List<CommissioniedilizieT> findCommissioniByIstanza(Integer codiceIstanza);

    public int countCommissioniByIstanza(Integer codiceIstanza);
}
