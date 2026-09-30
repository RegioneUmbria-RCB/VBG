package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Elencocassaedilebase;

import java.util.List;

public interface ElencocassaedilebaseDAO extends BaseDAO<Elencocassaedilebase, String> {

    public List<Elencocassaedilebase> findByDescrizione(String descrizione);
}
