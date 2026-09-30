package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAltridati;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;

import java.util.List;

public interface TipimovStcAltridatiService extends BaseService<TipimovStcAltridati, PkId> {

    /**
     * Trova una lista di configurazioni altri dati per il tipomovimento specificato
     * 
     * @param tipimovimentoId
     *            la chiave che identifica il tipo movimento
     * @return List&lt;TipimovStcMapping&gt;
     */
    public List<TipimovStcAltridati> findByTipimovimento(TipimovimentoId tipimovimentoId);

    public List<TipimovStcAltridati> findByTipimovimentoAndAmministrazione(String tipoMovimento, Integer codiceAmministrazioneStc);

    /**
     * Torna la lista delle TipimovStcAltridati di un'Amministrazione
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<TipimovStcAltridati> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);
}
