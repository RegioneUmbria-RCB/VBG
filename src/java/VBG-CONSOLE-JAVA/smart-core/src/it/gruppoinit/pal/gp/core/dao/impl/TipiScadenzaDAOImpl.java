/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipiScadenzaDAO;
import it.gruppoinit.pal.gp.core.domain.TipiScadenza;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class TipiScadenzaDAOImpl extends BaseDAOImpl<TipiScadenza, Integer> implements TipiScadenzaDAO {

    @Override
    public Class<TipiScadenza> getEntityClass() {

	return TipiScadenza.class;
    }

    // E' stato fatto l'override del metodo findByExample
    // perchè il metodo di BaseDAOImpl aggiunge idcomune come restriction
    @Override
    @SuppressWarnings("unchecked")
    public List<TipiScadenza> findAll(Integer firstResult, Integer maxResult) {

	if (null != firstResult && null != maxResult) {
	    return (List<TipiScadenza>) getHibernateTemplate().findByExample(new TipiScadenza(), firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<TipiScadenza>) getHibernateTemplate().findByExample(new TipiScadenza());
	}
    }
}
