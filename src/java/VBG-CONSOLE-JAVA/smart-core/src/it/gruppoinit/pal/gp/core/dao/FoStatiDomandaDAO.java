/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.FoStatiDomanda;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author francol
 *
 */
public interface FoStatiDomandaDAO extends BaseDAO<FoStatiDomanda, PkId> {

    public List<FoStatiDomanda> findByIdDomanda(String idComuneDomanda, Integer idDomanda);
}
