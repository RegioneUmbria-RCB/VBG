package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibandocampigraduat;

import java.util.List;

public interface TipibandocampigraduatDAO extends BaseDAO<Tipibandocampigraduat, PkId> {

    List<Tipibandocampigraduat> findByTipigraduatoriet(Tipibandocampigraduat tipibandocampigraduat);
}
