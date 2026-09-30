package it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Sorteggidettaglio;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;
import it.gruppoinit.pal.gp.core.domain.helper.SorteggidettaglioDTO;

import java.util.List;

/**
 * 
 * @author francescop
 */
public interface SorteggidettaglioDAO extends BaseDAO<Sorteggidettaglio, PkId> {

    /**
     * Torna il sorteggio dettaglio per l'ultima estrazione (Sorteggitestata) ordinati per stDatasorteggio desc,
     * id.codice desc
     * 
     * @param istanza
     * @return
     */
    public Sorteggidettaglio findByIstanza(Istanze istanza);

    /**
     * Torna la lista di Sorteggidettaglio di una Sorteggitestata ordinati per data e numeroistanza
     * 
     * @param sorteggitestata
     * @return
     */
    public List<Sorteggidettaglio> findBySorteggitestata(Sorteggitestata sorteggitestata);

    public List<Integer> findCodiciIstanzaBySorteggitestata(Integer codiceSorteggitestata);

    public List<Integer> findCodiciIstanzaBySorteggicategoria(Integer codiceSorteggiCategoria);

    public List<SorteggidettaglioDTO> findBySorteggitestata(Integer codice);
}
