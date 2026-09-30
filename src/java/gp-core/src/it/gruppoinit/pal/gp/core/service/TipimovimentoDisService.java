package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipimovimentoDisDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoDis;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author
 */
public interface TipimovimentoDisService extends BaseService<TipimovimentoDis, PkId> {

    /**
     * @see TipimovimentoDisDAO#findAll(Integer, Integer)
     */
    public List<TipimovimentoDis> findAll(Integer firstResult, Integer maxResult);

    /**
     * Cerca nella tabella tipimovimento_dis filtrando per i parametri passati
     * 
     * @param codiceistanza
     *            Obbligatorio
     * @param tipomovimento
     *            Obbligatorio
     * @param codiceinventario
     *            Può essere nullo. La ricerca verrà effettuata filtrando come isnull il campo
     * @param codiceamministrazione
     *            Può essere nullo. La ricerca verrà effettuata filtrando come isnull il campo
     * @param datascadenza
     *            Può essere nullo. La ricerca verrà effettuata filtrando come isnull il campo
     * @return
     * @throws IllegalArgumentException
     *             Se i parametri non sono passati correttamente
     */
    public List<TipimovimentoDis> findTipimovimentoDisabilitati(Integer codiceistanza, String tipomovimento, Integer codiceinventario,
	    Integer codiceamministrazione, Date datascadenza);

    /**
     * Torna la lista dei movimenti disabilitati per una determinata istanza, ordinati per tipomovimento,
     * endoprocedimento.ordine, amministrazione, datascadenza ASC
     * 
     * @param istanza
     * @return
     */
    public List<TipimovimentoDis> findByIstanza(Istanze istanza);
}
