package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BollGestDettAutorizz;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface BollGestDettAutorizzDAO extends BaseDAO<BollGestDettAutorizz, PkId> {

    void deleteBollGestAutorizzazioniByIdBollettazione(Integer idBollettazione);
}
