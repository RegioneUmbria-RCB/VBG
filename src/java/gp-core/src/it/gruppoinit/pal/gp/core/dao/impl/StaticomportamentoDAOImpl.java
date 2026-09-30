/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.StaticomportamentoDAO;
import it.gruppoinit.pal.gp.core.domain.Staticomportamento;

import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class StaticomportamentoDAOImpl extends BaseDAOImpl<Staticomportamento, Integer> implements StaticomportamentoDAO {

    @Override
    public Class<Staticomportamento> getEntityClass() {

	return Staticomportamento.class;
    }
}
