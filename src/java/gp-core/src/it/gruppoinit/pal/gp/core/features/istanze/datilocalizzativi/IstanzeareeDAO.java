package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzearee;
import it.gruppoinit.pal.gp.core.domain.IstanzeareeId;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.RicalcoloFilter;

/**
 * 
 * @author francescop
 */
public interface IstanzeareeDAO extends BaseDAO<Istanzearee, IstanzeareeId> {

    /**
     * Torna una area istanza di una determinata istanza e con primario = true
     * 
     * @param istanza
     * @return
     */
    public Istanzearee findByPrimarioIstanza(Istanze istanza);

    /**
     * Torna la lista delle aree istanza di una determinata istanza
     * 
     * @param istanza
     * 
     * @return
     */
    public List<Istanzearee> findByIstanza(Istanze istanza);

    /**
     * 
     * @param codiceistanza
     * @return
     */
    public List<Istanzearee> findByIstanza(Integer codiceistanza);
    
    public List<Integer> findCodiciIstanzaPerRicalcolo(RicalcoloFilter filter, String[] comuniAbilitati);

    /**
     * Il metodo verifica se un'area esiste in un'istanza
     * 
     * @param codiceistanza
     * @param codiceArea
     * @return
     */
    public boolean exists(Integer codiceistanza, Integer codiceArea);

    /**
     * Il metodo verifica se in un'istanza è già presente l'area primaria
     * 
     * @param codiceistanza
     * @return
     */
    public boolean existsPrimario(Integer codiceistanza);
    
    public void eliminaAreeAutoins(Integer codiceistanza);
}
