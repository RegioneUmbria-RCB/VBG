package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Elencocassaedilebase;

import java.util.List;

public interface ElencocassaedilebaseService extends BaseService<Elencocassaedilebase, String> {

    public List<Elencocassaedilebase> findByDescrizione(String descrizione);
}
