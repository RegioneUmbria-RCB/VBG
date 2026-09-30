package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.AutorizzazioniAttivita;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniAttivitaDTO;

import java.util.List;
import java.util.Set;

public interface AutorizzazioniAttivitaService extends BaseService<AutorizzazioniAttivita, PkId> {

    public void deleteByAutorizzazione(Integer codiceAutorizzazione);

    public void deleteByAttivita(String codiceAttivita);

    public List<AutorizzazioniAttivita> findByAutorizzazione(Integer codiceAutorizzazione, Integer firstResult, Integer maxResult);

    public List<AutorizzazioniAttivitaDTO> findByAutorizzazioni(Set<Integer> auts);
}
