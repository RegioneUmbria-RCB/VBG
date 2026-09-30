package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.LayoutpagineDAO;
import it.gruppoinit.pal.gp.core.domain.Layoutpagine;
import it.gruppoinit.pal.gp.core.domain.LayoutpagineId;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author
 */
public interface LayoutpagineService extends BaseService<Layoutpagine, LayoutpagineId> {

    /**
     * @see LayoutpagineDAO#findAll(Integer, Integer)
     */
    public List<Layoutpagine> findAll(Integer firstResult, Integer maxResult);

    /**
     * Cerca se per la pagina passata ci sono oggetti disabilitati e ritorna un. set con in nomi degli oggetti
     * disabilitati. I filtri di default sono IDCOMUNE, SOFTWARE, e (pagina = "*" or pagina=nomePagina)
     */
    public Set<String> findOggettiDisabilitatiPerPagina(String nomePagina);
}
