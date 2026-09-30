package it.gruppoinit.pal.gp.core.dao;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.MercatiCfgConti;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface MercatiCfgContiDAO extends BaseDAO<MercatiCfgConti, PkId> {

    public List<MercatiCfgConti> findAttiviInData(Date data);
}
