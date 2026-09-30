package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface MercatiUsoDAO extends BaseDAO<MercatiUso, PkId> {

    public List<MercatiUso> findByMercato(Mercati mercati);
}
