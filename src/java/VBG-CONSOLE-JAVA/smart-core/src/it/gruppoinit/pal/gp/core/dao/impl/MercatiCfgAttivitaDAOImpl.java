/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiCfgAttivitaDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivita;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivitaId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class MercatiCfgAttivitaDAOImpl extends BaseDAOImpl<MercatiCfgAttivita, MercatiCfgAttivitaId> implements MercatiCfgAttivitaDAO {

    @Override
    public Class<MercatiCfgAttivita> getEntityClass() {

	return MercatiCfgAttivita.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MercatiCfgAttivita> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	return getHibernateTemplate().findByCriteria(det);
    }
}
