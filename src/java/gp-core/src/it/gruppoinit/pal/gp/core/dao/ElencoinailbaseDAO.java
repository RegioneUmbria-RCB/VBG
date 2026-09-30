package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Elencoinailbase;

import java.util.List;

public interface ElencoinailbaseDAO extends BaseDAO<Elencoinailbase, String> {

    public List<Elencoinailbase> findByDescrizione(String descrizione);
}
