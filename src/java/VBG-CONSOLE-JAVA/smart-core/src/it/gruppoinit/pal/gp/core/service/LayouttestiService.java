package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Layouttesti;
import it.gruppoinit.pal.gp.core.domain.LayouttestiId;

public interface LayouttestiService extends BaseService<Layouttesti, LayouttestiId> {

    public String resolveCode(String code, String software);
}
