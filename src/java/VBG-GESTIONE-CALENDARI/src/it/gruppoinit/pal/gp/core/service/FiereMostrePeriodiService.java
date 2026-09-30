package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.FiereMostrePeriodi;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface FiereMostrePeriodiService extends BaseService<FiereMostrePeriodi, PkId> {

    public void evict(FiereMostrePeriodi entity);
}
