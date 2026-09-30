package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Ricerche;

import java.util.List;

public interface RicercheDAO extends BaseDAO<Ricerche, PkId> {

    public List<Ricerche> findByFilter(Integer codiceresponsabile, String chiave);
}
