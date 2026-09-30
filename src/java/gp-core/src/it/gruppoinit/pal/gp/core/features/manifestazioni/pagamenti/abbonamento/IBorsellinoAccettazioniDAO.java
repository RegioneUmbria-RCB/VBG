package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAccettazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface IBorsellinoAccettazioniDAO extends BaseDAO<BorsellinoAccettazioni, PkId> {

    List<BorsellinoAccettazioni> findByBorsellino(Integer codice);
}
