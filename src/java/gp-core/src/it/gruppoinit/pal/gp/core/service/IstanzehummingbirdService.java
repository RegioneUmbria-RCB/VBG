package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzehummingbirdDAO;
import it.gruppoinit.pal.gp.core.domain.Istanzehummingbird;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzehummingbirdService extends BaseService<Istanzehummingbird, PkId> {

    /**
     * @see IstanzehummingbirdDAO#findAll(Integer, Integer)
     */
    public List<Istanzehummingbird> findAll(Integer firstResult, Integer maxResult);

    /**
     * <ol>
     * <li>crea file datixente.xsd e datiendo.xsd</li>
     * <li>inserisce i file in movimenti allegati</li>
     * </ol>
     * 
     * @param movimento
     * @return la lista degli allegati al movimento creati
     */
    public List<Movimentiallegati> insertAllegatiDocArea(Movimenti movimento);
}
