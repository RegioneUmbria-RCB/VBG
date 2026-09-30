package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.TipicausalioneridettaglioDAO;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneridettaglio;
import it.gruppoinit.pal.gp.core.domain.TipicausalioneridettaglioId;

/**
 * 
 * @author
 */
public interface TipicausalioneridettaglioService extends BaseService<Tipicausalioneridettaglio, TipicausalioneridettaglioId> {

    /**
     * @see TipicausalioneridettaglioDAO#findAll(Integer, Integer)
     */
    public List<Tipicausalioneridettaglio> findAll(Integer firstResult, Integer maxResult);

    /**
     * Il metodo aggiorna tutti i riferimenti con i conti, della causale onere passata, impostandone il flag_attivo = 0
     * Successivamente aggiorna o inserisce il recordo per la causale onoere passata e il conto passato, impostando il
     * flag attivo = 0 Per una causale di onere può esserci solamente 1 conto attivo
     * 
     * @param idCausaleOnere
     * @param idConto
     */
    void impostaContoAttivo(Integer idCausaleOnere, Integer idConto);

    /**
     * @see TipicausalioneridettaglioDAO#findIdContoAttivoByCausaleOneri(Integer)
     *
     */
    public Integer findIdContoAttivoByCausaleOneri(Integer codice);

    public Conti findContoAttivoByCausaleOneri(Integer codice);

    /**
     * 
     * @see TipicausalioneridettaglioDAO#deleteByIdOnere(Integer)
     */
    public void deleteByIdOnere(Integer codice);
}
