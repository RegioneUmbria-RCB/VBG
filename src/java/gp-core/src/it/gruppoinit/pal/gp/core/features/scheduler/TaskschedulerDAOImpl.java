package it.gruppoinit.pal.gp.core.features.scheduler;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskscheduler;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class TaskschedulerDAOImpl extends BaseDAOImpl<Taskscheduler, PkId> implements TaskschedulerDAO {

    @Override
    public Class<Taskscheduler> getEntityClass() {

	return Taskscheduler.class;
    }

    @Override
    public List<Taskscheduler> findAttivi() {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("attivo", true, Boolean.class));
	filterTable.addRestriction(filterRestriction);
	return findByFilterTable(filterTable);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TaskBean> findAll() {

	DetachedCriteria det = getIdcomuneCriteria() //
		.setProjection(Projections //
			.projectionList() //
			.add(Projections.property("id.codice"), "id") //
			.add(Projections.property("descrizione"), "descrizione") //
			.add(Projections.property("taskbase.task"), "operazione") //
			.add(Projections.property("intervallo"), "intervallo") //
			.add(Projections.property("prossimaesecuzione"), "prossimaEsecuzione") //
			.add(Projections.property("attivo"), "attivo") //
			.add(Projections.property("inesecuzione"), "inEsecuzione")) //
		.setResultTransformer(Transformers.aliasToBean(TaskBean.class));
	List<TaskBean> retVal = getHibernateTemplate().findByCriteria(det);
	return retVal;
    }

    @Override
    public List<Taskscheduler> findDaEseguire() {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("attivo", true, Boolean.class));
	Calendar calendar = new GregorianCalendar();
	calendar.setTime(new Date());
	calendar.set(Calendar.HOUR, 23);
	calendar.set(Calendar.MINUTE, 59);
	calendar.set(Calendar.SECOND, 59);
	filterRestriction.addFilterField(FilterUtils.smallerEqual("prossimaesecuzione", calendar.getTime(), Timestamp.class));
	filterTable.addRestriction(filterRestriction);
	return findByFilterTable(filterTable);
    }
}
