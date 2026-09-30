package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovStcMapping;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.TipiMovimentoStcMappingProtocollo;

public interface TipimovStcMappingDAO extends BaseDAO<TipimovStcMapping, PkId> {

    TipiMovimentoStcMappingProtocollo findDatiProtocolloByTipoMovimento(String tipomovimento);
}
