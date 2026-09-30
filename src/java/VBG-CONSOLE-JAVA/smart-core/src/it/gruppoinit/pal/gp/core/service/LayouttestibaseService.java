package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Layouttestibase;
import it.gruppoinit.pal.gp.core.domain.LayouttestibaseId;

public interface LayouttestibaseService extends BaseService<Layouttestibase, LayouttestibaseId> {

    public String resolveCode(String code, String software);
}
