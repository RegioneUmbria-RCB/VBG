package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.configurazione;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.FirmeRemote;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface FirmeRemoteDAO extends BaseDAO<FirmeRemote, PkId> {

    List<FirmeRemote> getFirmeAttive();
}
