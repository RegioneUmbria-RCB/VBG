package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Elencoinailbase;

public interface ElencoinailbaseService extends BaseService<Elencoinailbase, String> {

    public List<Elencoinailbase> findByDescrizione(String descrizione);
}
