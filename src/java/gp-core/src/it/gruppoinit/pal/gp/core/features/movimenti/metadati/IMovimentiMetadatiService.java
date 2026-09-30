package it.gruppoinit.pal.gp.core.features.movimenti.metadati;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiMetadati;
import it.gruppoinit.pal.gp.core.domain.MovimentiMetadatiId;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface IMovimentiMetadatiService extends BaseService<MovimentiMetadati, MovimentiMetadatiId> {

    Movimenti findMovimentoByUuId(String uuidMovimento);

    boolean isMetadatoPresente(Integer codice, String nomeMetadato);
}
