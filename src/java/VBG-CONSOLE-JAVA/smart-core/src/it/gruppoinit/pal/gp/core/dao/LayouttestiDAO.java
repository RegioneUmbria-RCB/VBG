package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Layouttesti;
import it.gruppoinit.pal.gp.core.domain.LayouttestiId;

public interface LayouttestiDAO extends BaseDAO<Layouttesti, LayouttestiId> {

    String resolveCode(String code, String software);
}
