package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.AssenzeFilter;
import it.gruppoinit.pal.gp.core.domain.VwAssenzeconcessionari;
import it.gruppoinit.pal.gp.core.domain.VwAssenzeconcessionariId;

import java.util.List;

public interface VwAssenzeconcessionariService extends BaseService<VwAssenzeconcessionari, VwAssenzeconcessionariId> {

    public List<VwAssenzeconcessionari> findByAssenzeFilter(AssenzeFilter assenzeFilter);
}
