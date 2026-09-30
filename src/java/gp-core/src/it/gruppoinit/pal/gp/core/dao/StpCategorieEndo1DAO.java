package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpCategorieEndo1;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;

public interface StpCategorieEndo1DAO extends BaseDAO<StpCategorieEndo1, PkId> {

    public StpCategorieEndo1 findByStpCodice(Integer stpCodice);

    public StpCategorieEndo1 findByTipiendo(Tipiendo tipiendo);
}