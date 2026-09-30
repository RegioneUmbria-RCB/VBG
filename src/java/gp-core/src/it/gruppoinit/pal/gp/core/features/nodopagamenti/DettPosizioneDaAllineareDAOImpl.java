package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDaAllineare;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDaAllineareId;

@Repository
public class DettPosizioneDaAllineareDAOImpl extends BaseDAOImpl<DettPosizioneDaAllineare, DettPosizioneDaAllineareId>
	implements DettPosizioneDaAllineareDAO {

    @Override
    public Class<DettPosizioneDaAllineare> getEntityClass() {

	return DettPosizioneDaAllineare.class;
    }
}
