package it.gruppoinit.pal.gp.areariservata.service;

import it.gruppoinit.pal.gp.core.domain.Stradario;

import java.util.List;

public interface StradarioARJService {

    public List<Stradario> findByDescrizione(String descrizione, String codiceComune, Integer firstResult, Integer maxResult);
}
