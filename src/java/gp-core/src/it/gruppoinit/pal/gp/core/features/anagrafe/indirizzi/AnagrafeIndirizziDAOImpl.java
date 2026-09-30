package it.gruppoinit.pal.gp.core.features.anagrafe.indirizzi;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AnagrafeIndirizzi;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class AnagrafeIndirizziDAOImpl extends BaseDAOImpl<AnagrafeIndirizzi, PkId> implements AnagrafeIndirizziDAO {

    @Override
    public Class<AnagrafeIndirizzi> getEntityClass() {

	return AnagrafeIndirizzi.class;
    }
}
