package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Elencoinpsbase;

import java.util.List;

public interface ElencoinpsbaseDAO extends BaseDAO<Elencoinpsbase, String> {

    public List<Elencoinpsbase> findByDescrizione(String descrizione);
}
