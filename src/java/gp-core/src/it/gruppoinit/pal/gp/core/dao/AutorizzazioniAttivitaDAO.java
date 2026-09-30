package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AutorizzazioniAttivita;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniAttivitaDTO;

import java.util.List;
import java.util.Set;

public interface AutorizzazioniAttivitaDAO extends BaseDAO<AutorizzazioniAttivita, PkId> {

    void deleteByAutorizzazione(Integer codiceAutorizzazione);

    void deleteByAttivita(String codiceAttivita);

    List<AutorizzazioniAttivitaDTO> findByAutorizzazioni(Set<Integer> codiceiAutorizzazione);
}
