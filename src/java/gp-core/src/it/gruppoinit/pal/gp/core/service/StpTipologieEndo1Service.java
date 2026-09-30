package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo1;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;

public interface StpTipologieEndo1Service extends BaseService<StpTipologieEndo1, PkId> {

    public StpTipologieEndo1 findbyStpCodice(Integer stpCodice);

    public StpTipologieEndo1 findbyTipifamiglieendo(Tipifamiglieendo tipifamiglieendo);

    public StpTipologieEndo1 findbyStpCodiceAndSoftwareTipiFamEndo(int stpCodice, String codiceSoftware);
}
