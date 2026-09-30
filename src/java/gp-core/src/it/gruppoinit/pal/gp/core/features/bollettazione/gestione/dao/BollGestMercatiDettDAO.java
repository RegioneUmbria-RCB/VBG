package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BollGestMercatiDett;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.IntervalloDate;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.RiepilogoGiornateNonInizializzateBean;

public interface BollGestMercatiDettDAO extends BaseDAO<BollGestMercatiDett, PkId> {

    void deleteBollGestMercatiDettByIdGestAutorizzazioni(Integer idGestAutorizzazioni);

    void deleteByIdBollettazione(Integer idBollettazione);

    List<RiepilogoGiornateNonInizializzateBean> trovaGiornateNonInizializzateNelPeriodo(IntervalloDate intervalloDate, List<Integer> filtriMercati);
}
