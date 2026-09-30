package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.Istanzedyn2modellitDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class Istanzedyn2modellitDAOImpl extends BaseDAOImpl<Istanzedyn2modellit, Istanzedyn2modellitId> implements Istanzedyn2modellitDAO {

    @Override
    public Class<Istanzedyn2modellit> getEntityClass() {

	return Istanzedyn2modellit.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanzedyn2modellit> findByIstanza(PkId idIstanza) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	DetachedCriteria istanza = criteria.createCriteria("istanza");
	istanza.add(Restrictions.eq("id", idIstanza));
	List<Istanzedyn2modellit> lista = (List<Istanzedyn2modellit>) getHibernateTemplate().findByCriteria(criteria);
	return lista;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findIdSchedeByIstanza(Integer idIstanza) {

	if (idIstanza == null) {
	    throw new IllegalArgumentException("Impossibile recuperare la lista delle schede senza passare il riferimento dell'istanza");
	}
	String sql = "select fk_d2mt_id from istanzedyn2modellit where idcomune = ? and codiceistanza = ? order by fk_d2mt_id asc";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanzedyn2modellit.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idIstanza);
	query.addScalar("fk_d2mt_id", Hibernate.INTEGER);
	return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Integer> schedeMancanti(String idComune, Integer codiceIstanza, Set<Integer> idSchedeDaRicercare) {

	if (StringUtils.isBlank(idComune)) {
	    throw new IllegalArgumentException("Impossibile richiamare Istanzedyn2modellitDAO.schedeMancanti senza passare idComune");
	}
	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("Impossibile richiamare Istanzedyn2modellitDAO.schedeMancanti senza passare un codice istanza");
	}
	if (idSchedeDaRicercare == null || idSchedeDaRicercare.isEmpty()) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare Istanzedyn2modellitDAO.schedeMancanti senza passare la lista delle schede da ricercare");
	}
	StringBuilder sql = new StringBuilder(
		"" + "select " + " dyn2_modellit.id " + "from " + "  dyn2_modellit " + "    left join istanzedyn2modellit on " +
					      "      dyn2_modellit.idcomune = istanzedyn2modellit.idcomune and " +
					      "      dyn2_modellit.id = istanzedyn2modellit.fk_d2mt_id and " +
					      "      istanzedyn2modellit.codiceistanza = ? " + "where " + "  dyn2_modellit.idcomune = ? and " +
					      "  dyn2_modellit.id in (");
	for (int i = 0; i < idSchedeDaRicercare.size(); i++) {
	    sql.append("?,");
	}
	sql.deleteCharAt(sql.length() - 1);
	sql.append(") and " + // 
		   "  istanzedyn2modellit.fk_d2mt_id is null " + //
		   "group by " + //
		   "  dyn2_modellit.id " + //
		   "order by " + //
		   "  dyn2_modellit.id");
	SQLQuery query = getSession().createSQLQuery(sql.toString()).addSynchronizedEntityClass(Istanzedyn2modellit.class);
	int index = 0;
	query.setInteger(index, codiceIstanza);
	index++;
	query.setString(index, idComune);
	index++;
	for (Integer idScheda : idSchedeDaRicercare) {
	    query.setInteger(index, idScheda);
	    index++;
	}
	query.addScalar("id", Hibernate.INTEGER);
	return new HashSet<Integer>(query.list());
    }

    @Override
    public void insert(String idComune, Integer codiceIstanza, Set<Integer> idSchedeDaAggiungere) {

	if (StringUtils.isBlank(idComune)) {
	    throw new IllegalArgumentException("Impossibile richiamare Istanzedyn2modellitDAO.insert senza passare idComune");
	}
	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("Impossibile richiamare Istanzedyn2modellitDAO.insert senza passare un codice istanza");
	}
	if (idSchedeDaAggiungere == null || idSchedeDaAggiungere.isEmpty()) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare Istanzedyn2modellitDAO.insert senza passare la lista delle schede da aggiungere");
	}
	String sql = "insert into istanzedyn2modellit(idcomune,codiceistanza,fk_d2mt_id) values (?,?,?)";
	for (Integer idScheda : idSchedeDaAggiungere) {
	    SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanzedyn2modellit.class);
	    query.setString(0, ORMHelper.getIdcomune());
	    query.setInteger(1, codiceIstanza);
	    query.setInteger(2, idScheda);
	    query.executeUpdate();
	}
    }

    @Override
    public List<Integer> findIdModelloByIstanzaAndIdCampo(Integer codiceIstanza, Integer idDyn2Campi) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("Impossibile recuperare la lista delle schede senza passare il riferimento dell'istanza");
	}
	String sql = "select distinct istanzedyn2modellit.fk_d2mt_id as fk_d2mt_id from istanzedyn2modellit " + //
		     "inner join dyn2_modellid on dyn2_modellid.idcomune=istanzedyn2modellit.idcomune " + // 
		     "and dyn2_modellid.fk_d2mt_id=istanzedyn2modellit.fk_d2mt_id where istanzedyn2modellit.idcomune=? " + //
		     "and istanzedyn2modellit.codiceistanza=? " + //
		     "and dyn2_modellid.fk_d2c_id=? and dyn2_modellid.fk_d2c_id is not null";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanzedyn2modellit.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, codiceIstanza);
	query.setInteger(2, idDyn2Campi);
	query.addScalar("fk_d2mt_id", Hibernate.INTEGER);
	return query.list();
    }
}
