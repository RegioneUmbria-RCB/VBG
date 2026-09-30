package it.gruppoinit.pal.gp.core.features.scheduler;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class TaskschedulerparametriDAOImpl extends BaseDAOImpl<Taskschedulerparametri, PkId> implements TaskschedulerparametriDAO {

    @Override
    public Class<Taskschedulerparametri> getEntityClass() {

	return Taskschedulerparametri.class;
    }

    @Override
    public List<Taskschedulerparametri> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.parametro", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Taskschedulerparametri> findByTaskId(Integer codice) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("taskscheduler.id.codice", codice));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
