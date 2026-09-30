package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AppIoCoda;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaId;

@Repository
public class AppIoCodaDAOImpl extends BaseDAOImpl<AppIoCoda, AppIoCodaId> implements IAppIoCodaDAO {

    @Override
    public Class<AppIoCoda> getEntityClass() {

	return AppIoCoda.class;
    }
}
