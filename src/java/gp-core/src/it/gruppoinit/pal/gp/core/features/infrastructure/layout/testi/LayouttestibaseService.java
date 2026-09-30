package it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Layouttestibase;
import it.gruppoinit.pal.gp.core.domain.LayouttestibaseId;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface LayouttestibaseService extends BaseService<Layouttestibase, LayouttestibaseId> {

    public String resolveCode(String code, String software);

    public List<Layouttestibase> findByPrefissoOrderBySoftware(String prefissoEtichette);
}
