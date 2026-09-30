package it.gruppoinit.pal.gp.core.features.rubrica.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Rubrica;

public interface IRubricaDAO extends BaseDAO<Rubrica, PkId> {

    List<Rubrica> ricercaIndirizzo(String partial, int resultCount);
}
