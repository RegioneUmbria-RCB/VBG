package it.gruppoinit.pal.gp.core.features.movimenti.istanzecollegate;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AlberoprocMovimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface AlberoprocMovimentiService extends BaseService<AlberoprocMovimenti, PkId> {

    List<AlberoprocMovimenti> findByAlberoprocId(Integer codiceAlberoproc);

    /**
     * Risale alla prima gerarchia di configurazione trovata a partire dalla voce dell'intervento indicata
     * 
     * @param codiceAlberoproc
     * @return
     */
    List<AlberoprocMovimenti> findConfigurazioneAttivaByAlberoprocId(Integer codiceAlberoproc);
}
