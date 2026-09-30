/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipologieoggettoDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologieoggetto;

import org.springframework.stereotype.Repository;

/**
 * @author lucap
 * 
 */
@Repository
public class TipologieoggettoDAOImpl extends BaseDAOImpl<Tipologieoggetto, PkId> implements TipologieoggettoDAO {

    @Override
    public Class<Tipologieoggetto> getEntityClass() {

	return Tipologieoggetto.class;
    }
}
