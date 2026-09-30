package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.bollettazione.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipoRate;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface IBollCfgTipoRateDAO extends BaseDAO<BollCfgTipoRate, PkId> {

    List<BollCfgTipoRate> findByTipo(Integer codice);

    boolean verificaSePresente(Integer idBoll, Integer idRange);
}
