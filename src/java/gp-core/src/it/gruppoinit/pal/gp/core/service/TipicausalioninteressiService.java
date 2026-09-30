package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.TipicausalioninteressiDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioninteressi;

/**
 * 
 * @author
 */
public interface TipicausalioninteressiService extends BaseService<Tipicausalioninteressi, PkId> {

    /**
     * @see TipicausalioninteressiDAO#findAll(Integer, Integer)
     */
    public List<Tipicausalioninteressi> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista di Tipi causalion interessi filtrati per Tipi causali oneri e ordinati per il campo giorni
     * ritardo pagamento(ggritardopagamento ) ASC
     * 
     * @param tipicausalioneri
     * @return
     */
    public List<Tipicausalioninteressi> findByTipicausalioneri(Tipicausalioneri tipicausalioneri);

    public List<Tipicausalioninteressi> findByIdEGGpassati(Integer id, Integer ggPassati);
}
