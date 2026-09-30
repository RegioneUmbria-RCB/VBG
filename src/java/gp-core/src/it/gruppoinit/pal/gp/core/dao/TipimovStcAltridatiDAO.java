package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAltridati;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;

import java.util.List;

public interface TipimovStcAltridatiDAO extends BaseDAO<TipimovStcAltridati, PkId> {

    /**
     * Trova una lista di configurazioni altri dati per il tipomovimento specificato
     * 
     * @param tipimovimentoId
     *            la chiave che identifica il tipo movimento
     * @return List&lt;TipimovStcAltridati&gt;
     */
    public List<TipimovStcAltridati> findByTipimovimento(TipimovimentoId tipimovimentoId);
}
