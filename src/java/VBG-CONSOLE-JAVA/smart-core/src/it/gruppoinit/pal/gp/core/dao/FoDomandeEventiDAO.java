/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.FoDomandeEventi;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * @author francol
 *
 */
public interface FoDomandeEventiDAO extends BaseDAO<FoDomandeEventi, PkId> {

    public List<FoDomandeEventi> findByIdDomanda(String idComuneDomanda, Integer idDomanda);
}
