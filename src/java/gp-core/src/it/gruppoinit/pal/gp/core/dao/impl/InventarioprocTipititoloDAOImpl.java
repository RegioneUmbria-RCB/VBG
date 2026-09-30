package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocTipititoloDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.InventarioprocTipititolo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class InventarioprocTipititoloDAOImpl extends BaseDAOImpl<InventarioprocTipititolo, PkId> implements InventarioprocTipititoloDAO {

    @Override
    public Class<InventarioprocTipititolo> getEntityClass() {

	return InventarioprocTipititolo.class;
    }

    @Override
    public List<InventarioprocTipititolo> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "tipotitolo", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<InventarioprocTipititolo> findByInventarioproc(Integer codiceInventarioproc) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("inventarioprocedimenti.id.codice", codiceInventarioproc));
	det.addOrder(Order.asc("tipotitolo"));
	return getHibernateTemplate().findByCriteria(det);
    }
}
