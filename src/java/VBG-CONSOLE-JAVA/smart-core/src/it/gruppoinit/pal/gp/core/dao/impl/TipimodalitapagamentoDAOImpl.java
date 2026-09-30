/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipimodalitapagamentoDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class TipimodalitapagamentoDAOImpl extends BaseDAOImpl<Tipimodalitapagamento, PkId> implements TipimodalitapagamentoDAO {

    @Override
    public Class<Tipimodalitapagamento> getEntityClass() {

	return Tipimodalitapagamento.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipimodalitapagamento> findByMpDescrestesa(String mpDescrestesa) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.ilike("mpDescrestesa", mpDescrestesa, MatchMode.ANYWHERE));
	return (List<Tipimodalitapagamento>) getHibernateTemplate().findByCriteria(det);
    }
}
