package it.gruppoinit.pal.gp.core.features.movimenti.metadati;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiMetadati;
import it.gruppoinit.pal.gp.core.domain.MovimentiMetadatiId;

public interface IMovimentiMetadatiDAO extends BaseDAO<MovimentiMetadati, MovimentiMetadatiId> {

    String getUuid(Integer codiceMovimento);

    void deleteByCodiceMovimento(Integer codiceMovimento);

    Movimenti findMovimentoByUuId(String uuidMovimento);

    boolean isMetadatoPresente(Integer codice, String nomeMetadato);
}
