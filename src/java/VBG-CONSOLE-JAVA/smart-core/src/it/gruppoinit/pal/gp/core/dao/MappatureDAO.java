package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Mappature;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface MappatureDAO extends BaseDAO<Mappature, PkId> {

    /**
     * Lista di mappature filtrate per idcomune e ordinate per nometagpeople
     * 
     */
    public List<Mappature> findAll(Integer firstResult, Integer maxResult);

    /**
     * Lista di nometagpeople, con nometagpeople like textToSearch, raggruppati per nometagpeople
     */
    public List<String> findByNometagpeopleDistinct(String textToSearch);

    /**
     * Lista di codici software (raggruppati per descrizione) inclusi nelle schede contenenti il tagpeople specificato
     * in input
     */
    public List<String> findSoftwareByNometagpeople(String textToSearch, String nometagpeople);

    /**
     * Lista di codici Dyn2Modellit contenenti il tagpeople specificato e appartenenti al software specificato
     */
    public List<Integer> findSchedaByTagAndSoftware(String textToSearch, String nometagpeople, String codicesoftware);
}
