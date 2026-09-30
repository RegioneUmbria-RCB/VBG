package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentriConc;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface AutorizzazioniSubentriConcService extends BaseService<AutorizzazioniSubentriConc, PkId> {

    public List<AutorizzazioniSubentriConc> findByIdSubentro(Integer idSubentro);

    public List<AutorizzazioniSubentriConc> findByIdSubentroAutCollegata(Integer idSubentroAutCollegata);
}
