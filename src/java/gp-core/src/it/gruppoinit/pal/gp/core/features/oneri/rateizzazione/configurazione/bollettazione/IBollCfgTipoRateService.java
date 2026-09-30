package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.bollettazione;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.BollCfgTipoRate;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface IBollCfgTipoRateService extends BaseService<BollCfgTipoRate, PkId> {

    List<BollCfgTipoRate> findByTipo(Integer codice);

    boolean verificaSePresente(Integer idBoll, Integer idRange);
}
