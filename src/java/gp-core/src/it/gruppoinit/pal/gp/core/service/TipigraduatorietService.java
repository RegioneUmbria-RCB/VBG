package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibando;
import it.gruppoinit.pal.gp.core.domain.Tipigraduatoriet;

import java.util.List;

public interface TipigraduatorietService extends BaseService<Tipigraduatoriet, PkId> {

    public List<Tipigraduatoriet> findByTipibando(Tipigraduatoriet tipigraduatoriet);

    public List<Tipigraduatoriet> findByTipibando(Tipibando tipibando);
}
