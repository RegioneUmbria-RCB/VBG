package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipoMetadati;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface BollCfgTipoMetadatiDAO extends BaseDAO<BollCfgTipoMetadati, PkId> {

    List<BollCfgTipoMetadati> findMetadatiConfigurati(Integer codiceBollcfgTipo);

    void updateMetadato(UpdateMetadatoRequest jsonRequest);

    void deleteMetadato(DeleteMetadatoRequest jsonRequest);

    int insertMetadato(InsertMetadatoRequest jsonRequest);
}
