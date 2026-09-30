package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;


import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAutStorico;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface IBorsellinoAutStoricoDAO extends BaseDAO<BorsellinoAutStorico, PkId> {

    List<BorsellinoAutorizzazioniHistModel> findAutStoricoByBorsellino(Integer idborsellino);

}
