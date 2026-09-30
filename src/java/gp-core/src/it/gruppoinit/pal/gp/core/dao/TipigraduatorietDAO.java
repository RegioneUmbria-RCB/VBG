package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipigraduatoriet;

import java.util.List;

public interface TipigraduatorietDAO extends BaseDAO<Tipigraduatoriet, PkId> {

    List<Tipigraduatoriet> findByTipibando(Tipigraduatoriet tipigraduatoriet);
}
