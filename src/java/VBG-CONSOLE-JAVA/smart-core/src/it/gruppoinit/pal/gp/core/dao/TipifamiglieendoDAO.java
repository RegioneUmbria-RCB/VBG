package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;

import java.util.List;

public interface TipifamiglieendoDAO extends BaseDAO<Tipifamiglieendo, PkId> {

    public List<Tipifamiglieendo> findByFilter(Tipifamiglieendo entity, String idcomune);

    public List<Tipifamiglieendo> findByDescSWeTT(String textToSearch);
}
