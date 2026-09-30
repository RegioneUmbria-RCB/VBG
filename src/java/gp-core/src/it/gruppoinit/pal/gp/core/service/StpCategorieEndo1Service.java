package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpCategorieEndo1;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;

public interface StpCategorieEndo1Service extends BaseService<StpCategorieEndo1, PkId> {

    public StpCategorieEndo1 findByStpCodice(Integer stpCodice);

    public StpCategorieEndo1 findByTipiendo(Tipiendo tipiendo);

    public StpCategorieEndo1 findByStpCodiceAndSoftwareTipiEndo(Integer stpCodice, String codicesoftware);
}
