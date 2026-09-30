package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovStcModelli;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;

import java.util.List;

public interface TipimovStcModelliDAO extends BaseDAO<TipimovStcModelli, PkId> {

    /**
     * Trova una lista di configurazioni di modelli dinamici per il tipomovimento specificato
     * 
     * @param tipimovimentoId
     *            la chiave che identifica il tipo movimento
     * @return List&lt;TipimovStcModelli&gt;
     */
    public List<TipimovStcModelli> findByTipimovimento(TipimovimentoId tipimovimentoId);
}
