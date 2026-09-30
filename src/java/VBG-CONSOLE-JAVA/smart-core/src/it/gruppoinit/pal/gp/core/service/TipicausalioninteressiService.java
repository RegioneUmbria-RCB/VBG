package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipicausalioninteressiDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioninteressi;

import java.util.List;

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
}
