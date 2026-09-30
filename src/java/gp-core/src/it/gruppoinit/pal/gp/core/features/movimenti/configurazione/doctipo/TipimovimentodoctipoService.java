/**
 * 
 */
package it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentodoctipo;
import it.gruppoinit.pal.gp.core.domain.TipimovimentodoctipoId;
import it.gruppoinit.pal.gp.core.service.BaseService;

/**
 * @author lucap
 * 
 */
public interface TipimovimentodoctipoService extends BaseService<Tipimovimentodoctipo, TipimovimentodoctipoId> {

    /**
     * @see TipimovimentodoctipoDAO#findByTipoMovimentoAndTipoLettera(String codicemovimento, Integer codicelettera)
     */
    public Tipimovimentodoctipo findByTipoMovimentoAndTipoLettera(String codicemovimento, Integer codicelettera);

    public List<Tipimovimentodoctipo> findByTipoMovimento(String tipoMovimento);

    /**
     * Trova tutti i record legati alla lettera tipo passata come argomento
     * 
     * @param letteretipo
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Tipimovimentodoctipo> findByLetteretipo(Letteretipo letteretipo, int firstResulti, int maxResult);

    public List<Integer> findCodiciLettereAutomaticheByTipoMovimentoAndFase(String tipoMovimento, FasiDiEsecuzioneEnum faseEsecuzione);
}
