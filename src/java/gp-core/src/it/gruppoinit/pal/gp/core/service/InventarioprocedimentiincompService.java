package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentiincompDAO;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentiincomp;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author gianpaolot
 */
public interface InventarioprocedimentiincompService extends BaseService<Inventarioprocedimentiincomp, PkId> {

    /**
     * @see InventarioprocedimentiincompDAO#findAll(Integer, Integer)
     */
    public List<Inventarioprocedimentiincomp> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see InventarioprocedimentiincompDAO#findByEndoprocedimento(Inventarioprocedimenti inventarioprocedimenti)
     */
    public List<Inventarioprocedimentiincomp> findByEndoprocedimento(Inventarioprocedimenti inventarioprocedimenti);

    /**
     * @see InventarioprocedimentiincompDAO#findByEndoprocedimentoAndEndoprocedimentoIncomp(Inventarioprocedimenti
     *      inventarioprocedimenti, Inventarioprocedimenti inventarioprocedimentoIncomp)
     */
    public Inventarioprocedimentiincomp findByEndoprocedimentoAndEndoprocedimentoIncomp(Inventarioprocedimenti inventarioprocedimenti,
	    Inventarioprocedimenti inventarioprocedimentoIncomp);

    /**
     * Il metodo rimuove o inserisce una incompatibilità tra l'endoprocedimento in esame e gli altri che che vengono
     * passati come lista di codici. Logica:
     * 
     * 1- codiciDegliEndoDaLasciareIncompatibili!=null  : 
     * 
     * a- Si configureranno per l'endo procedimento passato tutti gli endo procedimenti ricavabili dalla lista come endo procedimenti Incompatibili
     * (l'iserimento verrà fatto solo se l'endo procedimento non era già incompatibile)
     * 
     * b- Verranno eliminate le incompatibilità tra l'endo procedimento passato e  gli endo procedimenti ricavati dalla lista creata come sottrazione 
     * di tutti gli endo procedimenti per il procediemnto in esame e la lista dei procedimenti incompatibili passati.  
     *
     * 2- codiciDegliEndoDaLasciareIncompatibili==null  
     * 
     * Elimina l'incompatibilità tra tutti gli endo procedimenti configurati per il procedimento in esame.
     * -------------------------------------------------------------------------------------------------------------- 3-
     *
     * 
     * @param inventarioprocedimenti
     *            : è l'endo procediemnto preso in esame
     * @param codiciDegliEndoDaLasciareIncompatibili
     *            : la lista dei codici degli endo che vogliamo che rimangano incompatibili
     * @param codiciDegliEndoIncompatibili
     *            : la lista dei codici degli endo che vogliamo rendere incompatibili
     * @param codiciTotaliDegliEndoPerUnProcedimento
     *            : lista dei codici degli endo configurati per un procedimento
     */
    public void addOrRemoveIncompatibilitaendo(Inventarioprocedimenti inventarioprocedimenti, String codiciDegliEndoIncompatibili,
	    String codiciTotaliDegliEndoPerUnProcedimento);
    
    /**
     * Il metodo verifica le incompatibilità reciproche fra gli endoprocedimenti i cui codici sono passati nell'argomento {@link List}
     * @param idEndos
     * : lista di interi che rappresentano gli id degli endo incompatibili da verificare reciprocamente
     * @return
     */
    public Set<Inventarioprocedimentiincomp> checkEndoIncompatibili(List<Integer> idEndos);
}
