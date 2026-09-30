package it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Layouttestibase;
import it.gruppoinit.pal.gp.core.domain.LayouttestibaseId;

public interface LayouttestibaseDAO extends BaseDAO<Layouttestibase, LayouttestibaseId> {

    public String resolveCode(String code, String software);

    public List<Layouttestibase> findByPrefissoOrderBySoftware(String prefissoEtichette);
}
