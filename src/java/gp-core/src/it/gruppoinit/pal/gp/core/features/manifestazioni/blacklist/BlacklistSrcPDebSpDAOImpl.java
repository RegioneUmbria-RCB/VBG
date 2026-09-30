package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BlacklistSrcPDebSp;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class BlacklistSrcPDebSpDAOImpl extends BaseDAOImpl<BlacklistSrcPDebSp, PkId> implements BlacklistSrcPDebSpDAO {

    @Override
    public Class<BlacklistSrcPDebSp> getEntityClass() {

	return BlacklistSrcPDebSp.class;
    }
}
