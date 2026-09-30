package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Layouttestibase;
import it.gruppoinit.pal.gp.core.domain.LayouttestibaseId;

public interface LayouttestibaseDAO extends BaseDAO<Layouttestibase, LayouttestibaseId> {

    public String resolveCode(String code, String software);
}
