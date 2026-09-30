package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaStati;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaStatiId;

public interface IAppIoCodaStatiDAO extends BaseDAO<AppIoCodaStati, AppIoCodaStatiId> {

    public List<AppIoCodaStati> findByGuid(String guid);
}
