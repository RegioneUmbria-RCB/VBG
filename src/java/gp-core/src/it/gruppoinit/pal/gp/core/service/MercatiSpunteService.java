package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.MercatiSpunte;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface MercatiSpunteService extends BaseService<MercatiSpunte, PkId> {

    public List<MercatiSpunte> findByMercato(Integer codiceMercato);
}
