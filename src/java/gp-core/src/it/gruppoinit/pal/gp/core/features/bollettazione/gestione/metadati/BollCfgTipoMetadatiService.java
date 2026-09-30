package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.BollCfgTipoMetadati;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;

public interface BollCfgTipoMetadatiService {

    List<MetadatiBean> findMetadatiConfigurati(Integer codiceBollcfgTipo);

    void updateMetadato(UpdateMetadatoRequest jsonRequest);

    void deleteMetadato(DeleteMetadatoRequest jsonRequest);

    List<MetadatoBollettazione> elencoMetadati(Integer codiceBollcfgTipo);

    int insertMetadato(InsertMetadatoRequest jsonRequest);

    List<BollCfgTipoMetadati> findByBollCfgTipo(Integer bollcfgTipo, Integer firstResult, Integer maxResult);

    void delete(BollCfgTipoMetadati entity);
}
