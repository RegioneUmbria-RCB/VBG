/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.MercatiConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazioneId;

/**
 * @author lucap
 * 
 */
public interface MercatiConfigurazioneService extends BaseService<MercatiConfigurazione, MercatiConfigurazioneId> {

    /**
     * ricerca per idcomune e software
     * 
     * @see MercatiConfigurazioneDAO#findAll(Integer, Integer, it.gruppoinit.pal.gp.core.dao.helper.DAOEnum,
     *      String, it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum)
     */
    public List<MercatiConfigurazione> findAll(Integer firstResult, Integer maxResult);

    /**
     * restituisce la configurazione della manifestazione per il software corrente.
     * 
     * @return l'oggetto {@link MercatiConfigurazione} o null
     */
    public MercatiConfigurazione findConfigurazione();
}
