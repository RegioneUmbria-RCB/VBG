package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.configurazione;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.FirmeRemoteParametri;
import it.gruppoinit.pal.gp.core.domain.FirmeRemoteParametriId;

public interface FirmeRemoteParametriDAO extends BaseDAO<FirmeRemoteParametri, FirmeRemoteParametriId> {

    void deleteByIdFirma(Integer idFirmaRemota);
}
