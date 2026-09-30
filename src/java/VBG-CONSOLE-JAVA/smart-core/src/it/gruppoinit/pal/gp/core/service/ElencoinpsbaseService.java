package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Elencoinpsbase;

import java.util.List;

public interface ElencoinpsbaseService extends BaseService<Elencoinpsbase, String> {

    public List<Elencoinpsbase> findByDescrizione(String descrizione);
}
