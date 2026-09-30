package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.Dyn2CampiDAO;
import it.gruppoinit.pal.gp.core.dao.IAttivitaSnapshotDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.dao.helper.QueryIAttivitaHelper;
import it.gruppoinit.pal.gp.core.dao.helper.TipoQueryHelperEnum;
import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.WsExportBean;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitaFilter;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.SnapshotIdData;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class IAttivitaSnapshotDAOImpl extends BaseDAOImpl<IAttivitaSnapshot, PkId> implements IAttivitaSnapshotDAO {

    private static final Logger log = LoggerFactory.getLogger(IAttivitaSnapshotDAOImpl.class);
    private Dyn2CampiDAO dyn2CampiDAO;

    @Autowired
    public void setDyn2CampiDAO(Dyn2CampiDAO dyn2CampiDAO) {

	this.dyn2CampiDAO = dyn2CampiDAO;
    }

    @Override
    public Class<IAttivitaSnapshot> getEntityClass() {

	return IAttivitaSnapshot.class;
    }

    @Override
    public List<IAttivitaSnapshot> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public IAttivitaSnapshot findBeforeData(Integer codiceAttivita, Date dataValidita) {

	if (dataValidita == null) {
	    throw new IllegalArgumentException("IAttivitaSnapshotDAOImpl.findBeforeData: la dataValidita non può essere null");
	}
	if (codiceAttivita == null) {
	    throw new IllegalArgumentException("IAttivitaSnapshotDAOImpl.findBeforeData: codice attività non può essere nullo ");
	}
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("istanza", "_istanza");
	criteria.add(Restrictions.eq("attivitaId", codiceAttivita));
	criteria.add(Restrictions.le("data", dataValidita));
	criteria.addOrder(OrderBySqlFormula.desc("data", FunctionsEnum.NVL_FUNCTION, "'01/01/2999'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	criteria.addOrder(OrderBySqlFormula.asc("_istanza.attivitaOrdine", FunctionsEnum.NVL_FUNCTION, "'0'"));
	criteria.addOrder(Order.desc("id.codice"));
	List<IAttivitaSnapshot> list = getHibernateTemplate().findByCriteria(criteria, 0, 1);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IAttivitaSnapshot> findAfterData(Integer codiceAttivita, Date dataValidita) {

	if (dataValidita == null) {
	    throw new IllegalArgumentException("IAttivitaSnapshotDAOImpl.findAfterData: la dataValidita non può essere null");
	}
	if (codiceAttivita == null) {
	    throw new IllegalArgumentException("IAttivitaSnapshotDAOImpl.findAfterData: codice attività non può essere nullo ");
	}
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("attivitaId", codiceAttivita));
	criteria.add(Restrictions.gt("data", dataValidita));
	criteria.addOrder(Order.asc("data"));
	List<IAttivitaSnapshot> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public IAttivitaSnapshot findBeforeDataAndOrdine(Integer codiceAttivita, Date dataValidita, Integer ordine) {

	if (dataValidita == null) {
	    throw new IllegalArgumentException("IAttivitaSnapshotDAOImpl.findBeforeData: la dataValidita non può essere null");
	}
	if (codiceAttivita == null) {
	    throw new IllegalArgumentException("IAttivitaSnapshotDAOImpl.findBeforeData: codice attività non può essere nullo ");
	}
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("istanza", "_istanza");
	criteria.add(Restrictions.eq("attivitaId", codiceAttivita));
	criteria.add(Restrictions.le("data", dataValidita));
	criteria.addOrder(OrderBySqlFormula.desc("data", FunctionsEnum.NVL_FUNCTION, "'01/01/2999'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	criteria.addOrder(OrderBySqlFormula.asc("_istanza.attivitaOrdine", FunctionsEnum.NVL_FUNCTION, "'0'"));
	criteria.addOrder(Order.desc("id.codice"));
	List<IAttivitaSnapshot> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    for (IAttivitaSnapshot iAttivitaSnapshot : list) {
		// Se le due date sono diverse è sicuramente il record che cercavo in quanto la data iAttivitaSnapshot.getIstanza().getDatavalidita()
		// sarà minore di quella bassata in quanto sono ordinate per data desc
		if (Utilities.compareDates(iAttivitaSnapshot.getIstanza().getDatavalidita(), dataValidita) != 0) {
		    return iAttivitaSnapshot;
		} else {
		    // Le due date sono uguali quandi devo controllare che che il campo iAttivitaSnapshot.getIstanza().getAttivitaOrdine()>
		    if (iAttivitaSnapshot.getIstanza().getAttivitaOrdine() > ordine) {
			return iAttivitaSnapshot;
		    }
		}
	    }
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IAttivitaSnapshot> findByAttivita(Integer codiceAttivita) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("attivitaId", codiceAttivita));
	criteria.addOrder(OrderBySqlFormula.desc("data", FunctionsEnum.NVL_FUNCTION, "'01/01/2999'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	List<IAttivitaSnapshot> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findListaSchede(Integer codiceAttivita) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = "SELECT i_attivitadyn2mod_t_snapshot.idcomune as idcomune, dyn2_modellit.id as id_modello, dyn2_modellit.codice_scheda as codice_scheda, " +
		" dyn2_modellit.descrizione as descrizione FROM " +
		schemaName +
		".i_attivitadyn2mod_t_snapshot join " +
		schemaName +
		".dyn2_modellit on " +
		" dyn2_modellit.idcomune=i_attivitadyn2mod_t_snapshot.idcomune and dyn2_modellit.id=i_attivitadyn2mod_t_snapshot.fk_d2mt_id" +
		" WHERE i_attivitadyn2mod_t_snapshot.idcomune=? AND i_attivitadyn2mod_t_snapshot.fk_ia_id =? " +
		" GROUP BY i_attivitadyn2mod_t_snapshot.idcomune, dyn2_modellit.id,dyn2_modellit.codice_scheda ,dyn2_modellit.descrizione " +
		" order by dyn2_modellit.descrizione";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("id_modello", Hibernate.INTEGER);
	q.addScalar("codice_scheda", Hibernate.STRING);
	q.addScalar("descrizione", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceAttivita);
	List<Object[]> rs = q.list();
	return rs;
    }

    @SuppressWarnings("unchecked")
    @Override
    public IAttivitaSnapshot findSnapshotPrecedente(IAttivitaSnapshot iAttivitaSnapshot) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("attivitaId", iAttivitaSnapshot.getAttivita().getId().getCodice()));
	criteria.add(Restrictions.lt("data", iAttivitaSnapshot.getData()));
	criteria.addOrder(Order.desc("data"));
	List<IAttivitaSnapshot> list = getHibernateTemplate().findByCriteria(criteria, 0, 1);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IAttivitaSnapshot> findFromData(Integer codiceAttivita, Date dataValidita) {

	if (dataValidita == null) {
	    throw new IllegalArgumentException("IAttivitaSnapshotDAOImpl.findFromData: la dataValidita non può essere null");
	}
	if (codiceAttivita == null) {
	    throw new IllegalArgumentException("IAttivitaSnapshotDAOImpl.findFromData: codice attività non può essere nullo ");
	}
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("attivitaId", codiceAttivita));
	criteria.add(Restrictions.ge("data", dataValidita));
	criteria.addOrder(Order.asc("data"));
	List<IAttivitaSnapshot> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WsExportBean> findIAttivitaSnapshotByFilter(IAttivitaFilter filter, Date dataEsportazione) {

	log.debug("findIAttivitaSnapshotByFilter: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryIAttivitaHelper qih = new QueryIAttivitaHelper(filter, sessimpl, dyn2CampiDAO, false, dataEsportazione,
		TipoQueryHelperEnum.EXPORT_IN_DATA);
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	qih.setFilterValues(q);
	qih.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(WsExportBean.class));
	List<WsExportBean> result = (List<WsExportBean>) q.list();
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findTuttiICodici(Integer codiceAttivitaDaCuiPartire) {

	if (codiceAttivitaDaCuiPartire == null) {
	    codiceAttivitaDaCuiPartire = 0;
	}
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = "SELECT id FROM " + schemaName + ".i_attivita_snapshot  WHERE idcomune=? and fk_ia_id>=? order by fk_ia_id asc,data asc";
	SQLQuery q = getSession().createSQLQuery(sql);
	// q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("id", Hibernate.INTEGER);
	// q.addScalar("fk_ia_id", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceAttivitaDaCuiPartire);
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<Date, Integer> findSnapshots(Integer idAttivita, Date dataRicalcolo) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo findSnapshots senza passare il parametro idAttivita");
	}
	if (dataRicalcolo == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo findSnapshots senza passare il parametro dataRicalcolo");
	}
	String sql = "select " +
		" i_attivita_snapshot.id,  " +
		" i_attivita_snapshot.data " +
		"from " +
		" i_attivita_snapshot " +
		"where " +
		" i_attivita_snapshot.idcomune = ? and " +
		" i_attivita_snapshot.fk_ia_id = ? " +
		"order by " +
		" i_attivita_snapshot.data asc";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitaSnapshot.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAttivita);
	//query.setDate(2, dataRicalcolo);
	query.addScalar("id", Hibernate.INTEGER);
	query.addScalar("data", Hibernate.DATE);
	query.setResultTransformer(Transformers.aliasToBean(SnapshotIdData.class));
	List<SnapshotIdData> elenco = query.list();
	Map<Date, Integer> retVal = new HashMap<Date, Integer>();
	for (SnapshotIdData snapshotIdData : elenco) {
	    retVal.put(snapshotIdData.getData(), snapshotIdData.getId());
	}
	return retVal;
    }
}
