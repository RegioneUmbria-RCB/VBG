package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AssenzeFilter;
import it.gruppoinit.pal.gp.core.domain.VwAssenzeconcessionari;
import it.gruppoinit.pal.gp.core.domain.VwAssenzeconcessionariId;

import java.util.List;

public interface VwAssenzeconsessionariDAO extends BaseDAO<VwAssenzeconcessionari, VwAssenzeconcessionariId> {

    public List<VwAssenzeconcessionari> findByAssenzeFilter(AssenzeFilter assenzeFilter);
}
