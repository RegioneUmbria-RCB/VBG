package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoVisuraCampiDAO;
import it.gruppoinit.pal.gp.core.domain.FoVisuraCampi;
import it.gruppoinit.pal.gp.core.domain.FoVisuraCampiId;
import it.gruppoinit.pal.gp.core.domain.FoVisuraContestiBase;
import it.gruppoinit.pal.gp.core.domain.helper.FoVisuraCampiHelper;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoVisuraCampiService extends BaseService<FoVisuraCampi, FoVisuraCampiId> {

    /**
     * @see FoVisuraCampiDAO#findAll(Integer, Integer)
     */
    public List<FoVisuraCampi> findAll(Integer firstResult, Integer maxResult);

    /**
     * Restituisce la lista di campi filtrando per idcomune, software e per il Contesto fornito in input
     * 
     * @param foVisuraContestiBase
     * @return
     */
    public List<FoVisuraCampi> findByContestiBase(FoVisuraContestiBase foVisuraContestiBase);

    /**
     * Aggiorna i campi di un contesto recuperandoli da un Helper (FoVisuraCampiHelper)
     * 
     * @param FoVisuraCampiHelper
     */
    public void updateCampiFromFoVisuraCampiHelper(FoVisuraCampiHelper foVisuraCampiHelper);
}
