package it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Sorteggidettaglio;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;
import it.gruppoinit.pal.gp.core.domain.helper.SorteggidettaglioDTO;
import it.gruppoinit.pal.gp.core.service.BaseService;

import java.util.List;

/**
 * 
 * @author francescop
 */
public interface SorteggidettaglioService extends BaseService<Sorteggidettaglio, PkId> {

    /**
     * @see {@link SorteggidettaglioDAO#findByIstanza(Istanze)}
     * @param istanza
     * @return
     */
    public Sorteggidettaglio findByIstanza(Istanze istanza);

    /**
     * @see SorteggidettaglioDAO#findBySorteggitestata(Sorteggitestata)
     * @param sorteggitestata
     * @return
     */
    public List<Sorteggidettaglio> findBySorteggitestata(Sorteggitestata sorteggitestata);

    /**
     * Ritorna la la lista dei sorteggi dettaglio collegati all'istanze passata
     * 
     * @param istanza
     * @return
     */
    public List<Sorteggidettaglio> findAllByIstanza(Istanze istanza);

    public List<Integer> findCodiciIstanzaBySorteggitestata(Integer codiceSorteggitestata);

    public List<Integer> findCodiciIstanzaBySorteggicategoria(Integer codiceSorteggiCategoria);

    /**
     * Recupera la lista dei dettagli di un sorteggio (istanze sorteggiate) filtando per la testa. ordinandole per data
     * istanze e numero istanza (desc). Il metodo non ritorna una lista di oggetti di dominio, ma di oggetti
     * SorteggidettaglioDTO
     * 
     * @param codice
     * @return
     */
    public List<SorteggidettaglioDTO> findBySorteggitestata(Integer codice);
}
