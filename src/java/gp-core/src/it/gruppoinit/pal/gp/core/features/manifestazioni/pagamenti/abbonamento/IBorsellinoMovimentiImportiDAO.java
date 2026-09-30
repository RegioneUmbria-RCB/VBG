package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimentiImporti;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface IBorsellinoMovimentiImportiDAO extends BaseDAO<BorsellinoMovimentiImporti, PkId> {

    void deleteByIdMovimento(Integer codiceMovimento);
}
