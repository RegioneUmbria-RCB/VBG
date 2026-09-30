package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneridettaglio;
import it.gruppoinit.pal.gp.core.domain.TipicausalioneridettaglioId;

/**
 * 
 * @author
 */
public interface TipicausalioneridettaglioDAO extends BaseDAO<Tipicausalioneridettaglio, TipicausalioneridettaglioId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Tipicausalioneridettaglio> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna tutti i record associati al codice di TipoCausaleOnere passato.
     * 
     * @param idCausaleOnere
     * @return
     */
    public List<Tipicausalioneridettaglio> findByCausaleOnere(Integer idCausaleOnere);

    /**
     * Recupera l'ID del Conto attivo in base alla Causale passata.
     * 
     * @param codice
     * @return
     */
    public Integer findIdContoAttivoByCausaleOneri(Integer codice);

    /**
     * Recupera il conto attivo in base alla Causale passata.
     * 
     * @param codice
     * @return
     */
    public Conti findContoAttivoByCausaleOneri(Integer codice);

    /**
     * Elimina tutti i dettagli legati alla CausaleOnere passata
     * 
     * @param codice
     */
    public void deleteByIdOnere(Integer codice);
}
