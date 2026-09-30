package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TempLinkallegati;

import java.util.List;

public interface TempLinkallegatiService extends BaseService<TempLinkallegati, PkId> {

    public List<TempLinkallegati> findByUuid(String uuid);
}
