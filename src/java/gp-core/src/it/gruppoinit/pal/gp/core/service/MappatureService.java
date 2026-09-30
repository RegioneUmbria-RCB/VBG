package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MappatureDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Mappature;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.Dyn2ModellitHelper;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface MappatureService extends BaseService<Mappature, PkId> {

    /**
     * @see MappatureDAO#findAll(Integer, Integer)
     */
    public List<Mappature> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see MappatureDAO#findByFilterTable(FilterTable)
     */
    public List<Mappature> findByFilterTable(FilterTable filterTable);

    /**
     * Lista di oggetti Mappature che hanno come nomeTagPeople quello passato
     * 
     * @param nomeTagPeople
     * @return
     */
    public List<Mappature> findByNomeTagPeople(String nomeTagPeople);

    /**
     * Lista di nometagpeople (raggruppati per nometagpeople) contenenti il tagpeople specificato in input
     */
    public List<String> findByNometagpeopleDistinct(String textToSearch);

    /**
     * Lista di software (raggruppati per descrizione) inclusi nelle schede contenenti il tagpeople specificato in input
     */
    public List<Software> findSoftwareByNometagpeople(String textToSearch, String nometagpeople);

    /**
     * Lista di Dyn2Modellit (raggruppati per descrizione) contenenti il tagpeople specificato in input e appartenenti
     * al software specificato in input
     */
    public List<Dyn2Modellit> findSchedaByTagAndSoftware(String textToSearch, String nometagpeople, String codicesoftware);

    /**
     * Inserisce la lista di mappature di una scheda (Dyn2Modellit)
     * 
     * @param dyn2ModellitHelper
     */
    public void updateMapsFromDyn2ModellitHelper(Dyn2ModellitHelper dyn2ModellitHelper);
}
